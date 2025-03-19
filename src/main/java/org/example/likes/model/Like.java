package org.example.likes.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID programId;

    @Column(nullable = false)
    private UUID likedByUserId;

    @Column(nullable = false)
    private UUID programOwnerId;

    @Column(nullable = false)
    private LocalDateTime likedAt;
}
