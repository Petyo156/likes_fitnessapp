package org.example.likes.web.controller;

import org.example.likes.model.Notification;
import org.example.likes.service.NotificationService;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;
import org.example.likes.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping()
    public ResponseEntity<NotifyUserResponse> notifyUserForLikedProgram(@RequestBody NotifyUserRequest notifyUserRequest) {
        NotifyUserResponse response = notificationService.notifyUserForLikedProgram(notifyUserRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotifyUserResponse>> getNotificationsForUser(@PathVariable String userId) {

        List<Notification> notifications = notificationService.getNotificationsForUser(userId);
        List<NotifyUserResponse> response = DtoMapper.fromNotifications(notifications);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
