package org.example.likes.web.mapper;

import org.example.likes.model.Like;
import org.example.likes.model.Notification;
import org.example.likes.web.dto.LikeProgramResponse;
import org.example.likes.web.dto.NotifyUserRequest;
import org.example.likes.web.dto.NotifyUserResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DtoMapper {
    public static LikeProgramResponse fromLike(Like like) {
        return LikeProgramResponse.builder()
                .programId(like.getProgramId())
                .likedByUserId(like.getLikedByUserId())
                .programOwnerId(like.getProgramOwnerId())
                .build();
    }

    public static List<NotifyUserResponse> fromNotifications(List<Notification> notifications) {
        List<NotifyUserResponse> responses = new ArrayList<>();
        for (Notification notification : notifications) {
            NotifyUserResponse response = NotifyUserResponse.builder()
                    .message(notification.getMessage())
                    .receiverId(notification.getReceiverId().toString())
                    .senderId(notification.getSenderId().toString())
                    .date(notification.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();

            responses.add(response);
        }
        return responses;
    }

    public static NotifyUserResponse fromNotifyUserRequest(NotifyUserRequest notifyUserRequest) {
        return NotifyUserResponse.builder()
                .message("User notified successfully")
                .receiverId(notifyUserRequest.getReceiverId())
                .senderId(notifyUserRequest.getSenderId())
                .build();
    }
}
