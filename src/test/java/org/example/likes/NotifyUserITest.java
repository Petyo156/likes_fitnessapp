package org.example.likes;

import org.example.likes.model.Like;
import org.example.likes.model.Notification;
import org.example.likes.repository.NotificationRepository;
import org.example.likes.service.LikeService;
import org.example.likes.service.NotificationService;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.NotifyUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class NotifyUserITest {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void notifyUserForLikedProgram_happyPath() {
        NotifyUserRequest notifyUserRequest = NotifyUserRequest.builder()
                .senderId(UUID.randomUUID().toString())
                .receiverId(UUID.randomUUID().toString())
                .message("Hello World")
                .build();

        notificationService.notifyUserForLikedProgram(notifyUserRequest);

        List<Notification> notifications = notificationRepository.findAll();
        assertThat(notifications).hasSize(1);

        Notification notification = notifications.getFirst();
        assertThat(notification.getSenderId().toString()).isEqualTo(notifyUserRequest.getSenderId());
        assertThat(notification.getReceiverId().toString()).isEqualTo(notifyUserRequest.getReceiverId());
        assertThat(notification.getMessage()).isEqualTo(notifyUserRequest.getMessage());
    }
}
