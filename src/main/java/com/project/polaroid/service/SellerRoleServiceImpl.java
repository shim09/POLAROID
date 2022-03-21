package com.project.polaroid.service;

import com.project.polaroid.entity.SellerEntity;
import com.project.polaroid.repository.MemberRepository;
import com.project.polaroid.repository.SellerRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerRoleServiceImpl implements SellerRoleService{
    private final SellerRoleRepository sellerRoleRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 판매자 권한신청
    @Override
    public String save(Long memberId) {
        String duplicate=null;
        List<SellerEntity> sellerEntityList = sellerRoleRepository.sellerRoleDuplicate(memberId);

        if(sellerEntityList.isEmpty()) {
            SellerEntity sellerEntity=new SellerEntity();
            sellerEntity.setMemberId(memberService.findById(memberId));
            // true 권한부여 안된 상태
            sellerEntity.setSellerBoolean(true);
            sellerRoleRepository.save(sellerEntity);
            duplicate="ok";
        }
        else{
            duplicate="no";
        }
        return duplicate;
    }

    // 판매자 권한 리스트
    @Override
    @Transactional
    public List<SellerEntity> findAll() {
        sellerRoleRepository.deleteRole();
        return sellerRoleRepository.findAll();
    }

    // 판매자 권한 부여
    @Override
    @Transactional
    public void giveRole(Long memberId) {
        memberRepository.giveRole(memberId);
        sellerRoleRepository.giveRole(memberId);
    }

}
