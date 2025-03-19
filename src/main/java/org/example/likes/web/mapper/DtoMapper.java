package org.example.likes.web.mapper;

import org.example.likes.model.Like;
import org.example.likes.web.dto.LikeProgramResponse;

public class DtoMapper {
    public static LikeProgramResponse fromLike(Like like){
        return LikeProgramResponse.builder()
                .programId(like.getProgramId())
                .likedByUserId(like.getLikedByUserId())
                .programOwnerId(like.getProgramOwnerId())
                .build();
    }
}
