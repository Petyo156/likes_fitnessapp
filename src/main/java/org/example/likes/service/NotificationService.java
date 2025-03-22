package org.example.likes.service;

import lombok.extern.slf4j.Slf4j;
import org.example.likes.exception.ExceptionMessages;
import org.example.likes.exception.UserCannotSendNotificationToHimselfException;
import org.example.likes.model.Notification;
import org.example.likes.repository.NotificationRepository;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;
import org.example.likes.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotifyUserResponse notifyUserForLikedProgram(NotifyUserRequest notifyUserRequest) {
        if (notifyUserRequest.getSenderId().equals(notifyUserRequest.getReceiverId())) {
            throw new UserCannotSendNotificationToHimselfException(ExceptionMessages.USER_CANNOT_SEND_NOTIFICATION_TO_HIMSELF);
        }

        Notification notification = initializeNotification(notifyUserRequest);
        notificationRepository.save(notification);
        log.info("Notification sent");

        return DtoMapper.fromNotifyUserRequest(notifyUserRequest);
    }

    public List<Notification> getNotificationsForUser(String userId) {
        try {
            return notificationRepository.getAllByReceiverIdOrderByCreatedAtDesc(UUID.fromString(userId));
        } catch (Exception e) {
            log.error("Couldn't parse String to UUID: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private Notification initializeNotification(NotifyUserRequest notifyUserRequest) {
        return Notification.builder()
                .createdAt(LocalDateTime.now())
                .receiverId(UUID.fromString(notifyUserRequest.getReceiverId()))
                .message(notifyUserRequest.getMessage())
                .senderId(UUID.fromString(notifyUserRequest.getSenderId()))
                .build();
    }
}

