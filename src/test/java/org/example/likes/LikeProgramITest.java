package org.example.likes;

import org.example.likes.model.Like;
import org.example.likes.repository.LikeRepository;
import org.example.likes.service.LikeService;
import org.example.likes.web.dto.LikeProgramRequest;
import org.example.likes.web.dto.LikeProgramResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class LikeProgramITest {
    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepository likeRepository;

    @Test
    void likeProgram_happyPath() {
        LikeProgramRequest likeProgramRequest = LikeProgramRequest.builder()
                .programId(UUID.randomUUID().toString())
                .likedByUserId(UUID.randomUUID().toString())
                .programOwnerId(UUID.randomUUID().toString())
                .build();

        likeService.likeProgram(likeProgramRequest);

        List<Like> likes = likeRepository.findAll();
        assertThat(likes).hasSize(1);

        Like like = likes.getFirst();
        assertThat(like.getProgramId().toString()).isEqualTo(likeProgramRequest.getProgramId());
        assertThat(like.getLikedByUserId().toString()).isEqualTo(likeProgramRequest.getLikedByUserId());
        assertThat(like.getProgramOwnerId().toString()).isEqualTo(likeProgramRequest.getProgramOwnerId());
    }
}
