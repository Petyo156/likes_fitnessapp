package org.example.likes.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeProgramRequest {
    @NotNull
    private String programId;

    @NotNull
    private String likedByUserId;

    @NotNull
    private String programOwnerId;
}
