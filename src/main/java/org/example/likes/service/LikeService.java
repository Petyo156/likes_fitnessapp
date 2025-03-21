package org.example.likes.service;

import lombok.extern.slf4j.Slf4j;
import org.example.likes.exception.ExceptionMessages;
import org.example.likes.exception.UserCannotLikeOwnProgramException;
import org.example.likes.exception.UserHasAlreadyLikedProgramException;
import org.example.likes.model.Like;
import org.example.likes.repository.LikeRepository;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.LikeProgramResponse;
import org.example.likes.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public LikeProgramResponse likeProgram(LikeProgramRequest likeProgramRequest) {
        if (likeProgramRequest.getLikedByUserId().equals(likeProgramRequest.getProgramOwnerId())) {
            throw new UserCannotLikeOwnProgramException(ExceptionMessages.USER_CANNOT_LIKE_OWN_PROGRAM);
        }
        if (userHasAlreadyLikedThatProgram(likeProgramRequest.getLikedByUserId(), likeProgramRequest.getProgramId())) {
            throw new UserHasAlreadyLikedProgramException(ExceptionMessages.USER_HAS_ALREADY_LIKED_PROGRAM);
        }

        Like like = initializeLike(likeProgramRequest);
        likeRepository.save(like);

        LikeProgramResponse response = DtoMapper.fromLike(like);
        log.info("Program successfully liked");
        return response;
    }

    public List<String> getAllLikedProgramIdsForUser(String userId) {
        List<String> programIds = new ArrayList<>();
        List<Like> likes;

        try {
            likes = likeRepository.getAllByLikedByUserId(UUID.fromString(userId));
        } catch (Exception e) {
            likes = new ArrayList<>();
        }

        for (Like like : likes) {
            programIds.add(like.getProgramId().toString());
        }
        return programIds;
    }

    private boolean userHasAlreadyLikedThatProgram(String userId, String programId) {
        return likeRepository.existsByLikedByUserIdAndProgramId(UUID.fromString(userId), UUID.fromString(programId));
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
