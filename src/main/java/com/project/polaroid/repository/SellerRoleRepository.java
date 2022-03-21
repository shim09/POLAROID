package com.project.polaroid.repository;

import com.project.polaroid.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SellerRoleRepository extends JpaRepository<SellerEntity,Long> {

    // 권한신청 목록
    @Query(value = "SELECT a FROM SellerEntity a WHERE a.memberId.id=:memberId order by a.sellerId asc ")
    public List<SellerEntity> sellerRoleDuplicate(Long memberId);

    // 권한부여된 컬럼 삭제
    @Transactional
    @Modifying
    @Query(value = "delete from seller_table a where a.seller_boolean=false", nativeQuery=true)
    public void deleteRole();

    // 판매자 권한 부여후 권한부여 된것을 알림
    @Modifying
    @Query("update SellerEntity p set p.sellerBoolean = false where p.memberId.id = :memberId")
    public void giveRole(Long memberId);

}
