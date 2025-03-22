package org.example.likes.like;

import org.example.likes.exception.UserCannotLikeOwnProgramException;
import org.example.likes.exception.UserHasAlreadyLikedProgramException;
import org.example.likes.model.Like;
import org.example.likes.repository.LikeRepository;
import org.example.likes.service.LikeService;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.LikeProgramResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LikeServiceUTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    @Test
    void likeProgram_whenValidRequest_thenSuccess() {
        UUID userId = UUID.randomUUID();
        UUID programId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        LikeProgramRequest likeProgramRequest = LikeProgramRequest.builder()
                .likedByUserId(userId.toString())
                .programId(programId.toString())
                .programOwnerId(ownerId.toString())
                .build();

        Like like = Like.builder()
                .likedByUserId(userId)
                .programId(programId)
                .programOwnerId(ownerId)
                .likedAt(LocalDateTime.now())
                .build();

        when(likeRepository.existsByLikedByUserIdAndProgramId(userId, programId)).thenReturn(false);
        when(likeRepository.save(any(Like.class))).thenReturn(like);

        LikeProgramResponse response = likeService.likeProgram(likeProgramRequest);

        assertNotNull(response);
        assertEquals(userId.toString(), response.getLikedByUserId().toString());
        assertEquals(programId.toString(), response.getProgramId().toString());
        verify(likeRepository).save(any(Like.class));
    }

    @Test
    void likeProgram_whenUserLikesOwnProgram_thenThrowsException() {
        UUID userId = UUID.randomUUID();
        UUID programId = UUID.randomUUID();

        LikeProgramRequest invalidRequest = LikeProgramRequest.builder()
                .likedByUserId(userId.toString())
                .programId(programId.toString())
                .programOwnerId(userId.toString())
                .build();

        assertThrows(UserCannotLikeOwnProgramException.class, () -> likeService.likeProgram(invalidRequest));
        verify(likeRepository, never()).save(any());
    }

    @Test
    void likeProgram_whenUserAlreadyLikedProgram_thenThrowsException() {
        UUID userId = UUID.randomUUID();
        UUID programId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        LikeProgramRequest likeProgramRequest = LikeProgramRequest.builder()
                .likedByUserId(userId.toString())
                .programId(programId.toString())
                .programOwnerId(ownerId.toString())
                .build();

        when(likeRepository.existsByLikedByUserIdAndProgramId(userId, programId)).thenReturn(true);

        assertThrows(UserHasAlreadyLikedProgramException.class, () -> likeService.likeProgram(likeProgramRequest));
        verify(likeRepository, never()).save(any());
    }

    @Test
    void getAllLikedProgramIdsForUser_whenUserHasLikes_thenReturnsProgramIds() {
        UUID userId = UUID.randomUUID();
        UUID programId = UUID.randomUUID();

        Like like = Like.builder()
                .likedByUserId(userId)
                .programId(programId)
                .likedAt(LocalDateTime.now())
                .build();

        when(likeRepository.getAllByLikedByUserId(userId)).thenReturn(List.of(like));
        List<String> likedPrograms = likeService.getAllLikedProgramIdsForUser(userId.toString());

        assertEquals(1, likedPrograms.size());
        assertEquals(programId.toString(), likedPrograms.getFirst());
    }

    @Test
    void getAllLikedProgramIdsForUser_whenRepositoryThrowsException_thenReturnsEmptyList() {
        String userId = UUID.randomUUID().toString();

        when(likeRepository.getAllByLikedByUserId(any(UUID.class))).thenThrow(new RuntimeException("Invalid UUID"));

        List<String> likedPrograms = likeService.getAllLikedProgramIdsForUser(userId);

        assertNotNull(likedPrograms);
        assertTrue(likedPrograms.isEmpty(), "Expected an empty list when exception occurs");
    }

    @Test
    void getAllLikedProgramIdsForUser_whenUserHasNoLikes_thenReturnsEmptyList() {
        UUID userId = UUID.randomUUID();
        when(likeRepository.getAllByLikedByUserId(userId)).thenReturn(Collections.emptyList());

        List<String> likedPrograms = likeService.getAllLikedProgramIdsForUser(userId.toString());

        assertTrue(likedPrograms.isEmpty());
    }
}
