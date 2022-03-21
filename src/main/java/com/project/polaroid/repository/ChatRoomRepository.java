package com.project.polaroid.repository;

import com.project.polaroid.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity,Long> {

    public Optional<ChatRoomEntity> findById(Long id);

}
