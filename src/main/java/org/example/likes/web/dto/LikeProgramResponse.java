package org.example.likes.web.dto;

import lombok.*;

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
