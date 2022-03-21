package com.project.polaroid.controller;

import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/scroll/*")
public class ScrollController {

    private final BoardService bs;

    @GetMapping
    public @ResponseBody
    Page<BoardPagingDTO> paging(Pageable pageable, Model model) {
        Page<BoardPagingDTO> boardList = bs.paging(pageable);

        System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청 페이지에 들어있는 데이터, toString이 없기 때문에 주소값이 출력
        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글 갯수
        System.out.println("boardList.getNumber() = " + boardList.getNumber()); // JPA 기준 요청 페이지
        System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardList.getSize() = " + boardList.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫 페이지인지 여부
        System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막 페이지인지 여부

        Long boardId = 1000000000000L;

        for(BoardPagingDTO b: boardList) {
            if(b.getBoardId() <= boardId) {
                boardId = b.getBoardId();
            }
        }

        System.out.println("boardId = " + boardId);

        model.addAttribute("boardId", boardId);

        return boardList;
    }

}

