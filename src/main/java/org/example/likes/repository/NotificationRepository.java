package org.example.likes.repository;

import org.example.likes.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> getAllByReceiverIdOrderByCreatedAt(UUID receiverId);
}
