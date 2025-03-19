package org.example.likes.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeProgramResponse {
    private UUID programId;

    private UUID likedByUserId;

    private UUID programOwnerId;
}
