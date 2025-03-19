package org.example.likes.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotifyUserRequest {
    @NotNull
    private String receiverId;

    @NotNull
    private String message;

    @NotNull
    private boolean read;

    @NotNull
    private LocalDateTime createdAt;
}

