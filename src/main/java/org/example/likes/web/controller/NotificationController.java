package org.example.likes.web.controller;

import org.example.likes.model.Notification;
import org.example.likes.service.NotificationService;
import org.example.likes.web.dto.LikeProgramResponse;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;
import org.example.likes.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        notificationService.notifyUserForLikedProgram(notifyUserRequest);
        NotifyUserResponse response = NotifyUserResponse.builder()
                .message("User notified successfully")
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
