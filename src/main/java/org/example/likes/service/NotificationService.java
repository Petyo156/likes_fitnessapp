package org.example.likes.service;

import lombok.extern.slf4j.Slf4j;
import org.example.likes.model.Notification;
import org.example.likes.repository.NotificationRepository;
import org.example.likes.web.dto.NotifyUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void notifyUserForLikedProgram(NotifyUserRequest notifyUserRequest) {
        Notification notification = initializeNotification(notifyUserRequest);
        notificationRepository.save(notification);

        log.info("Notification sent");
    }

    private Notification initializeNotification(NotifyUserRequest notifyUserRequest) {
        return Notification.builder()
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .receiverId(UUID.fromString(notifyUserRequest.getReceiverId()))
                .message(notifyUserRequest.getMessage())
                .build();
    }
}

