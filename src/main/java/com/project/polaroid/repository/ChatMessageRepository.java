package com.project.polaroid.repository;

import com.project.polaroid.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,Long> {
}
