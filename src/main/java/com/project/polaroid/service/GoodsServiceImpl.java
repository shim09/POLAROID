package com.project.polaroid.service;


import com.project.polaroid.page.PagingConstGoods;
import com.project.polaroid.dto.*;
import com.project.polaroid.entity.*;
import com.project.polaroid.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {
    private final GoodsRepository gr;
    private final GoodsPhotoRepository gpr;
    private final MemberService ms;
    private final GoodsLikeRepository glr;
    private final MemberRepository mr;
    private final PayRepository pr;

    // 페이징
    @Override
    public Page<GoodsPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        page = (page == 1) ? 0 : (page - 1);
        //                        몇페이지? / 몇개씩 볼껀지       / 무슨 기준으로 정렬할지 (내림차순)/ 기준 컬럼 (Entity 필드이름) /
        Page<GoodsEntity> goodsEntities = gr.findAll(PageRequest.of(page, PagingConstGoods.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Entity는 서비스 밖으로 나가면 안됨
        // Page<BoardEntity> => Page(BoardPagingDTO) 로 변환시켜야하지만 페이징은 안된다.
        Page<GoodsPagingDTO> goodsList = goodsEntities.map(
                // 엔티티 객체를 담기위한 반복용 변수 goods
                goods -> new GoodsPagingDTO(
                        goods.getId(),
                        goods.getGoodsWriter().getId(),
                        goods.getGoodsWriter().getMemberNickname(),
                        goods.getGoodsTitle(),
                        goods.getGoodsContents(),
                        goods.getGoodsPrice(),
                        goods.getGoodsView(),
                        goods.getGoodsLikeEntityList().size(),
                        goods.getGoodsStock(),
                        goods.getCreateTime(),
                        goods.getUpdateTime(),
                        goods.getGoodsWriter().getMemberFilename(),
                        GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))
        );
        return goodsList;
    }
    // 디테일
    @Override
    @Transactional
    public GoodsDetailDTO findById(Long goodsId) {

        Optional<GoodsEntity> optionalGoodsEntity = gr.findById(goodsId);
        GoodsDetailDTO goodsDetailDTO=null;
        if(optionalGoodsEntity.isPresent()) {
            GoodsEntity goodsEntity = optionalGoodsEntity.get();
            goodsDetailDTO = GoodsDetailDTO.toGoodsDetailDTO(goodsEntity);
        }
        return goodsDetailDTO;
    }

//    // 글쓰기 기능
//    @Override
//    public Long save(GoodsSaveDTO goodsSaveDTO, Long memberId) {
//        GoodsEntity goodsEntity = GoodsEntity.toGoodsEntitySave(goodsSaveDTO, ms.findById(memberId));
//        Long goodsId = gr.save(goodsEntity).getId();
//        return goodsId;
//    }

    // 글쓰기 기능
    @Override
    public Long save(GoodsSaveDTO goodsSaveDTO) {
        GoodsEntity goodsEntity = GoodsEntity.toGoodsEntitySave(goodsSaveDTO, ms.findById(goodsSaveDTO.getMemberId()));
        Long goodsId = gr.save(goodsEntity).getId();
        return goodsId;
    }

    // 파일저장 기능
    @Override
    public void saveFile(Long goodsId, MultipartFile goodsFile) throws IOException {
        String goodsFilename = goodsFile.getOriginalFilename();
        goodsFilename = System.currentTimeMillis() + "-" + goodsFilename;
        // 윤성경로
        //String savePath = "C:\\Development\\source\\springboot\\Polariod_Integrated\\src\\main\\resources\\static\\goodsFile\\" + goodsFilename;
        String savePath = System.getProperty("user.dir") + "/src/main/resources/static/goodsFile/" + goodsFilename;;


        if (!goodsFile.isEmpty()) {
            goodsFile.transferTo(new File(savePath));
        }
        GoodsPhotoEntity goodsPhotoEntity = new GoodsPhotoEntity();
        goodsPhotoEntity.setGoodsEntity(gr.findById(goodsId).get());
        goodsPhotoEntity.setGoodsFilename(goodsFilename);
        gpr.save(goodsPhotoEntity);

    }


    @Override
    public Page<GoodsPagingDTO> search(GoodsSearchDTO goodsSearchDTO, Pageable pageable) {
        if(goodsSearchDTO.getSelect().equals("goodsTitle")){
            Page<GoodsEntity> goodsEntityList = gr.findByGoodsTitleContaining(goodsSearchDTO.getSearch(), PageRequest.of(pageable.getPageNumber()-1, PagingConstGoods.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<GoodsPagingDTO> goodsList = goodsEntityList.map(
                    goods -> new GoodsPagingDTO(
                            goods.getId(),
                            goods.getGoodsWriter().getId(),
                            goods.getGoodsWriter().getMemberNickname(),
                            goods.getGoodsTitle(),
                            goods.getGoodsContents(),
                            goods.getGoodsPrice(),
                            goods.getGoodsView(),
                            goods.getGoodsLikeEntityList().size(),
                            goods.getGoodsStock(),
                            goods.getCreateTime(),
                            goods.getUpdateTime(),
                            goods.getGoodsWriter().getMemberFilename(),
                            GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))
                    );
            return goodsList;
        } else {
            Page<GoodsEntity> goodsEntities =  gr.searchWriter(goodsSearchDTO.getSearch(), PageRequest.of(pageable.getPageNumber() - 1, PagingConstGoods.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<GoodsPagingDTO> goodsList = goodsEntities.map(
                    goods -> new GoodsPagingDTO(
                            goods.getId(),
                            goods.getGoodsWriter().getId(),
                            goods.getGoodsWriter().getMemberNickname(),
                            goods.getGoodsTitle(),
                            goods.getGoodsContents(),
                            goods.getGoodsPrice(),
                            goods.getGoodsView(),
                            goods.getGoodsLikeEntityList().size(),
                            goods.getGoodsStock(),
                            goods.getCreateTime(),
                            goods.getUpdateTime(),
                            goods.getGoodsWriter().getMemberFilename(),
                            GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))

            );
            return goodsList;
        }
    }

    @Override
    public int findLike(Long goodsId, Long memberId) {
        GoodsEntity goodsEntity = gr.findById(goodsId).get();
        MemberEntity memberEntity = mr.findById(memberId).get();
        GoodsLikeEntity likeStatus = glr.findByGoodsEntityAndMemberEntity(goodsEntity, memberEntity);
        if (likeStatus != null){
            int like = 1;
            System.out.println("좋아요가 넘어오나요4");
            return like;
        } else {
            int like = 0;
            System.out.println("좋아요가 넘어오나요5");
            return like;
        }
    }

    @Transactional
    @Override
    public int saveLike(Long goodsId, Long memberId) {

        GoodsEntity goodsEntity = gr.findById(goodsId).get();
        MemberEntity memberEntity = mr.findById(memberId).get();
        GoodsLikeEntity likeStatus = glr.findByGoodsEntityAndMemberEntity(goodsEntity, memberEntity);

        if (likeStatus == null){
            GoodsEntity goods = gr.findById(goodsId).get();
            MemberEntity member = mr.findById(memberId).get();

            GoodsLikeEntity goodsLikeEntity = GoodsLikeEntity.toGoodsLikeEntity(goods, member);
            glr.save(goodsLikeEntity);
            System.out.println("좋아요가 넘어오나요2");
            return 1;
        }else {
            glr.deleteByGoodsEntityAndMemberEntity(goodsEntity, memberEntity);
            System.out.println("좋아요가 넘어오나요3");
            return 0;
        }
    }

    @Override
    public Long update(GoodsUpdateDTO goodsUpdateDTO) {
        MemberEntity memberEntity = mr.findById(goodsUpdateDTO.getMemberId()).get();
        GoodsEntity goodsEntity = gr.findById(goodsUpdateDTO.getGoodsId()).get();
        GoodsEntity goods = GoodsEntity.toUpdateGoodsEntity(goodsUpdateDTO, goodsEntity,memberEntity);
        return gr.save(goods).getId();
    }

    @Override
    public void deleteById(Long goodsId) {
        gr.deleteById(goodsId);
    }

    @Transactional
    @Override
    public void viewUp(Long goodsId) {
        gr.viewUp(goodsId);
    }

    // 결제
    @Transactional
    @Override
    public void pay(Long goodsId, int count) {
        gr.stockDown(goodsId,count);
    }

    @Override
    public List<GoodsDetailDTO> pick(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        List<GoodsLikeEntity> goodsLikeEntityList = glr.findAllByMemberEntity(memberEntity);
        List<GoodsEntity> goodsEntityList = new ArrayList<>();
        for (GoodsLikeEntity g: goodsLikeEntityList) {
            goodsEntityList.add(gr.findById(g.getGoodsEntity().getId()).get());
        }
        List<GoodsDetailDTO> goodsDetailDTOList = new ArrayList<>();
        for (GoodsEntity g: goodsEntityList) {
            goodsDetailDTOList.add(GoodsDetailDTO.toGoodsDetailDTO(g));
        }
        return goodsDetailDTOList;
    }

    // 결제 성공 정보 저장
    @Override
    public void paySuccess(Long goodsId, Long memberId, int count) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        GoodsEntity goodsEntity = gr.findById(goodsId).get();
        PayEntity payEntity = PayEntity.toPayEntity(memberEntity, goodsEntity, count);
        pr.save(payEntity);
    }

    // 결제정보
    @Override
    public PayDetailDTO payFind(Long goodsId, Long memberId) {
        GoodsEntity goodsEntity = gr.findById(goodsId).get();
        MemberEntity memberEntity = mr.findById(memberId).get();
        PayEntity payEntity = pr.findTop1ByGoodsEntityAndMemberEntityOrderByIdDesc(goodsEntity, memberEntity);
        PayDetailDTO payDetailDTO = PayDetailDTO.toPayDetailDTO(payEntity);
        return payDetailDTO;
    }

    // 결제 리스트
    @Override
    public List<PayDetailDTO> payList(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        List<PayEntity> payEntityList = pr.findAllByMemberEntityOrderByIdDesc(memberEntity);
        List<PayDetailDTO> payDetailDTOList = PayDetailDTO.toPayDetailDTOList(payEntityList);
        return payDetailDTOList;
    }

    // 굿즈 내글 리스트

    @Override
    @Transactional
    public Page<GoodsPagingDTO> list(Long memberId, Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);
        //                        몇페이지? / 몇개씩 볼껀지       / 무슨 기준으로 정렬할지 (내림차순)/ 기준 컬럼 (Entity 필드이름) /
        Page<GoodsEntity> goodsDetailDTO = gr.findByIdGoodsWriter(memberId, PageRequest.of(pageable.getPageNumber() - 1, PagingConstGoods.LIST_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<GoodsPagingDTO> goodsList = goodsDetailDTO.map(
                goods -> new GoodsPagingDTO(
                        goods.getId(),
                        goods.getGoodsWriter().getId(),
                        goods.getGoodsWriter().getMemberNickname(),
                        goods.getGoodsTitle(),
                        goods.getGoodsContents(),
                        goods.getGoodsPrice(),
                        goods.getGoodsView(),
                        goods.getGoodsLikeEntityList().size(),
                        goods.getGoodsStock(),
                        goods.getCreateTime(),
                        goods.getUpdateTime(),
                        goods.getGoodsWriter().getMemberFilename(),
                        GoodsPhotoDetailDTO.toGoodsPhotoDetailDTOList(goods.getGoodsPhotoEntity()))

        );
        return goodsList;
    }

    // 3.13 hsw 추가 좋아요 게시글
    @Override
    public List<GoodsDetailDTO> pickList(Long id) {
        List<GoodsLikeEntity> pickList= glr.pickList(id);
        List<GoodsEntity> goodsList=new ArrayList<>();

        for(GoodsLikeEntity g: pickList){
            goodsList.add(g.getGoodsEntity());
        }

        List<GoodsDetailDTO> pickGoodsList=GoodsDetailDTO.toChangeDTOList(goodsList);

        return pickGoodsList;
    }

}

