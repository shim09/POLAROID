package com.project.polaroid.service;

import com.project.polaroid.entity.SellerEntity;

import java.util.List;

public interface SellerRoleService {
    // 판매자 권한신청
    String save(Long memberId);

    // 판매자 권한 리스트
    List<SellerEntity> findAll();

    // 판매자 권한 부여
    void giveRole(Long memberId);
}
