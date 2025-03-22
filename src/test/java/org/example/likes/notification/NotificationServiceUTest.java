package org.example.likes.notification;

import org.example.likes.exception.UserCannotSendNotificationToHimselfException;
import org.example.likes.model.Notification;
import org.example.likes.repository.NotificationRepository;
import org.example.likes.service.NotificationService;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceUTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void notifyUserForLikedProgram_whenValidRequest_thenSuccess() {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        String message = "User notified successfully";

        NotifyUserRequest notifyUserRequest = NotifyUserRequest.builder()
                .senderId(senderId.toString())
                .receiverId(receiverId.toString())
                .message(message)
                .build();

        Notification notification = Notification.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        NotifyUserResponse response = notificationService.notifyUserForLikedProgram(notifyUserRequest);

        assertNotNull(response);
        assertEquals(senderId.toString(), response.getSenderId());
        assertEquals(receiverId.toString(), response.getReceiverId());
        assertEquals(message, response.getMessage());
        verify(notificationRepository).save(any(Notification.class));
    }

    @Test
    void notifyUserForLikedProgram_whenSenderIsReceiver_thenThrowsException() {
        UUID userId = UUID.randomUUID();
        NotifyUserRequest invalidRequest = NotifyUserRequest.builder()
                .senderId(userId.toString())
                .receiverId(userId.toString())
                .message("You liked your own post")
                .build();

        assertThrows(UserCannotSendNotificationToHimselfException.class, () ->
                notificationService.notifyUserForLikedProgram(invalidRequest));
        verify(notificationRepository, never()).save(any());
    }

    @Test
    void getNotificationsForUser_whenUserHasNotifications_thenReturnsList() {
        UUID userId = UUID.randomUUID();
        Notification notification = Notification.builder()
                .receiverId(userId)
                .senderId(UUID.randomUUID())
                .message("New like received")
                .createdAt(LocalDateTime.now())
                .build();

        when(notificationRepository.getAllByReceiverIdOrderByCreatedAtDesc(userId)).thenReturn(List.of(notification));

        List<Notification> notifications = notificationService.getNotificationsForUser(userId.toString());

        assertNotNull(notifications);
        assertEquals(1, notifications.size());
        assertEquals("New like received", notifications.getFirst().getMessage());
    }

    @Test
    void getNotificationsForUser_whenUserHasNoNotifications_thenReturnsEmptyList() {
        UUID userId = UUID.randomUUID();
        when(notificationRepository.getAllByReceiverIdOrderByCreatedAtDesc(userId)).thenReturn(Collections.emptyList());

        List<Notification> notifications = notificationService.getNotificationsForUser(userId.toString());

        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
    }

    @Test
    void getNotificationsForUser_whenRepositoryThrowsException_thenReturnsEmptyList() {
        String userId = UUID.randomUUID().toString();

        when(notificationRepository.getAllByReceiverIdOrderByCreatedAtDesc(any(UUID.class)))
                .thenThrow(new RuntimeException("Database error"));

        List<Notification> notifications = notificationService.getNotificationsForUser(userId);

        assertNotNull(notifications);
        assertTrue(notifications.isEmpty());
    }
}
