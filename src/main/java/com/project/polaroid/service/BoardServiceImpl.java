package com.project.polaroid.service;

import com.project.polaroid.dto.*;
import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.LikeEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.entity.PhotoEntity;
import com.project.polaroid.page.PagingConst;
import com.project.polaroid.page.PagingConstBoard;
import com.project.polaroid.page.PagingConstGoods;
import com.project.polaroid.repository.BoardRepository;
import com.project.polaroid.repository.LikeRepository;
import com.project.polaroid.repository.MemberRepository;
import com.project.polaroid.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository br;
    private final PhotoRepository pr;
    private final MemberRepository mr;
    private final LikeRepository lr;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) {
        MemberEntity memberEntity = mr.findById(boardSaveDTO.getMemberId()).get();
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardSaveDTO, memberEntity);
        Long boardId = br.save(boardEntity).getId();
        return boardId;
    }

    @Override
    public void saveFile(Long boardId, MultipartFile boardFile) throws IOException {
        String boardFilename = boardFile.getOriginalFilename();
        boardFilename = System.currentTimeMillis() + "-" + boardFilename;
        String savePath = System.getProperty("user.dir") + "/src/main/resources/static/upload/" + boardFilename;
        //String savePath = "/Users/seongwookheo/source/files/" + boardFilename;

//        String savePath = "/Users/seongwookheo/source/springboot/Polaroid/src/main/resources/static/upload/" + boardFilename;

        if (!boardFile.isEmpty()) {
            boardFile.transferTo(new File(savePath));
        }
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setBoardId(br.findById(boardId).get());
        photoEntity.setBoardFilename(boardFilename);
        pr.save(photoEntity);
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        return BoardDetailDTO.toBoardDetailDTO(br.findById(boardId).get());
    }

    @Override
    public Page<BoardPagingDTO> search(String keyword, Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);
        Page<BoardEntity> boardEntityList = br.findByBoardContentsContaining(keyword, PageRequest.of(page, PagingConst.SEARCH_PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardPagingDTO> boardList = boardEntityList.map(
                board -> new BoardPagingDTO(
                        board.getId(),
                        board.getMemberId().getMemberNickname(),
                        board.getBoardContents(),
                        PhotoDetailDTO.toPhotoDetailDTOList(board.getPhotoEntity()))
        );
        return boardList;
    }

    @Override
    public int findLike(Long b_id, Long m_id) {
        BoardEntity boardId = br.findById(b_id).get();
        MemberEntity memberId = mr.findById(m_id).get();
        LikeEntity likeStatus = lr.findByBoardIdAndMemberId(boardId, memberId);
        if (likeStatus!=null) {
            int like = 1;
            return like;
        } else {
            int like = 0;
            return like;
        }
    }

    @Transactional
    @Override
    public int saveLike(Long b_id, Long m_id) {

        BoardEntity boardId = br.findById(b_id).get();
        MemberEntity memberId = mr.findById(m_id).get();
        LikeEntity likeStatus = lr.findByBoardIdAndMemberId(boardId, memberId);

        if (likeStatus==null) {
            MemberEntity memberEntity = mr.findById(m_id).get();
            BoardEntity boardEntity = br.findById(b_id).get();

            LikeEntity likeEntity = LikeEntity.toLikeEntity(memberEntity, boardEntity);
            lr.save(likeEntity);
            return 1;
        } else {
            lr.deleteByBoardIdAndMemberId(boardId, memberId);
            return 0;

        }

    }

    @Override
    public void deleteById(Long boardId) {
        br.deleteById(boardId);
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) {
        MemberEntity memberEntity = mr.findById(boardUpdateDTO.getMemberId()).get();
        System.out.println("memberEntity = " + memberEntity);
        BoardEntity boardEntity = br.findById(boardUpdateDTO.getBoardId()).get();
        BoardEntity updateBoardEntity = BoardEntity.toUpdateBoardEntity(boardUpdateDTO, boardEntity,memberEntity);
        return br.save(updateBoardEntity).getId();
    }

    @Transactional
    @Override
    public List<BoardDetailDTO> myPage(Long memberId) {
        List<BoardEntity> boardEntityList = br.mypage(memberId);
        List<BoardDetailDTO> boardList=BoardDetailDTO.toBoardDetailDTOList(boardEntityList);
        return boardList;
    }


    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(
                        board.getId(),
                        board.getMemberId().getMemberNickname(),
                        board.getBoardContents(),
                        PhotoDetailDTO.toPhotoDetailDTOList(board.getPhotoEntity()))
        );
        return boardList;
    }

    // 보드 숫자
    @Override
    public List<BoardEntity> boardCount(Long id) {
        return br.boardCount(id);
    }

    // 3.13 hsw 추가 좋아요 목록
    @Override
    @Transactional
    public List<BoardDetailDTO>  likeList(Long id) {
        List<LikeEntity> likeEntityList=lr.likeList(id);
        List<BoardEntity> boardList=new ArrayList<>();
        for(LikeEntity l:likeEntityList){
            boardList.add(l.getBoardId());
        }
        List<BoardDetailDTO> boardLikeList=BoardDetailDTO.toBoardDetailDTOList(boardList);

        return boardLikeList;
    }



}
