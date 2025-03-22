package org.example.likes.mapper;


import org.example.likes.model.Notification;
import org.example.likes.web.dto.NotifyUserResponse;
import org.example.likes.web.mapper.DtoMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DtoMapperUTest {
    @Test
    void fromNotifications_whenValidList_thenMapsCorrectly() {
        UUID senderId = UUID.randomUUID();
        UUID receiverId = UUID.randomUUID();
        String message = "You received a like!";
        LocalDateTime createdAt = LocalDateTime.now();
        String formattedDate = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Notification notification = Notification.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .message(message)
                .createdAt(createdAt)
                .build();

        List<Notification> notifications = List.of(notification);

        List<NotifyUserResponse> responses = DtoMapper.fromNotifications(notifications);

        assertNotNull(responses);
        assertEquals(1, responses.size());

        NotifyUserResponse response = responses.getFirst();
        assertEquals(message, response.getMessage());
        assertEquals(senderId.toString(), response.getSenderId());
        assertEquals(receiverId.toString(), response.getReceiverId());
        assertEquals(formattedDate, response.getDate());
    }

    @Test
    void fromNotifications_whenEmptyList_thenReturnsEmptyList() {
        List<Notification> emptyList = List.of();

        List<NotifyUserResponse> responses = DtoMapper.fromNotifications(emptyList);

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }
}
