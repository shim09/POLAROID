package com.project.polaroid.repository;

import com.project.polaroid.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
}
