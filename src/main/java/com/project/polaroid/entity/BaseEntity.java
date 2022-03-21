package com.project.polaroid.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    // 생성시간
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;

    // 업데이트 시간
    @UpdateTimestamp
    @Column(insertable = false)
    private LocalDateTime updateTime;

}
