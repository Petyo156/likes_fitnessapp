package org.example.likes.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class NotifyUserRequest {
    @NotNull
    private String receiverId;

    @NotNull
    private String senderId;

    @NotNull
    private String message;
}

