package com.project.polaroid.service;


import com.project.polaroid.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GoodsService {

    Page<GoodsPagingDTO> paging(Pageable pageable);

    GoodsDetailDTO findById(Long goodsId);

    //    Long save(GoodsSaveDTO goodsSaveDTO, Long memberId);
    Long save(GoodsSaveDTO goodsSaveDTO);

    void saveFile(Long goodsId, MultipartFile g) throws IOException;

    Page<GoodsPagingDTO> list(Long memberId, Pageable pageable);

    Page<GoodsPagingDTO> search(GoodsSearchDTO goodsSearchDTO, Pageable pageable);

    int findLike(Long goodsId, Long memberId);

    int saveLike(Long goodsId, Long memberId);

    Long update(GoodsUpdateDTO goodsUpdateDTO);

    void deleteById(Long goodsId);

    void viewUp(Long goodsId);

    void pay(Long goodsId, int count);

    List<GoodsDetailDTO> pick(Long memberId);

    void paySuccess(Long goodsId, Long memberId, int count);

    PayDetailDTO payFind(Long goodsId, Long memberId);

    List<PayDetailDTO> payList(Long memberId);
    // 3.13 hsw 추가 찜 게시글
    List<GoodsDetailDTO> pickList(Long id);
}
