package org.example.likes.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class NotifyUserRequest {
    @NotNull
    private String receiverId;

    @NotNull
    private String senderId;

    @NotNull
    private String message;
}

