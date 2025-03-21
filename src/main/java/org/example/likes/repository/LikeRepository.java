package org.example.likes.repository;

import org.example.likes.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID> {

    boolean existsByLikedByUserIdAndProgramId(UUID likedByUserId, UUID programId);

    List<Like> getAllByLikedByUserId(UUID userId);
}
