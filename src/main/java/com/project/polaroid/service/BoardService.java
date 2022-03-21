package com.project.polaroid.service;

import com.project.polaroid.dto.BoardDetailDTO;
import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.dto.BoardSaveDTO;
import com.project.polaroid.dto.BoardUpdateDTO;
import com.project.polaroid.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {

    Long save(BoardSaveDTO boardSaveDTO);

    Page<BoardPagingDTO> paging(Pageable pageable);

    void saveFile(Long boardId, MultipartFile boardFile) throws IOException;

    BoardDetailDTO findById(Long boardId);

    Page<BoardPagingDTO> search(String keyword, Pageable pageable);

    int findLike(Long boardId, Long memberId);

    int saveLike(Long boardId, Long memberId);

    void deleteById(Long boardId);

    Long update(BoardUpdateDTO boardUpdateDTO);

    // 마이페이지 보드 리스트
    List<BoardDetailDTO> myPage(Long id);

    // 보드 숫자
    List<BoardEntity> boardCount(Long id);

    // 3.13 hsw 추가 좋아요 게시글
    List<BoardDetailDTO>  likeList(Long id);
}