package com.project.polaroid.repository;

import com.project.polaroid.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Long> {

    @Query(value = "SELECT count(a) FROM NoticeEntity a WHERE a.noticeMember= :memberId and a.noticeMessage= :message")
    int countMessage(Long memberId,String message);

    @Transactional
    @Modifying
    @Query(value = "delete from notice_table a where a.notice_member= :memberId and a.notice_room= :chatRoomId and notice_message=:message", nativeQuery=true)
    void deleteCount(Long chatRoomId, Long memberId, String message);

    @Query(value = "SELECT count(a) FROM NoticeEntity a WHERE a.noticeMember= :memberId and a.noticeRoom= :roomId")
    int countRoom(Long memberId, Long roomId);
}
