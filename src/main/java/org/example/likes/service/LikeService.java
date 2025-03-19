package org.example.likes.service;

import lombok.extern.slf4j.Slf4j;
import org.example.likes.model.Like;
import org.example.likes.repository.LikeRepository;
import org.example.likes.web.dto.LikeProgramRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like likeProgram(LikeProgramRequest likeProgramRequest) {
        Like like = initializeLike(likeProgramRequest);
        likeRepository.save(like);

        log.info("Program successfully liked");
        return like;
    }

    private Like initializeLike(LikeProgramRequest likeProgramRequest) {
        return Like.builder()
                .likedByUserId(UUID.fromString(likeProgramRequest.getLikedByUserId()))
                .likedAt(LocalDateTime.now())
                .programId(UUID.fromString(likeProgramRequest.getProgramId()))
                .programOwnerId(UUID.fromString(likeProgramRequest.getProgramOwnerId()))
                .build();
    }
}
