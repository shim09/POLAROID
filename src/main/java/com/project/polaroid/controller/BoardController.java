package com.project.polaroid.controller;

import com.project.polaroid.auth.PrincipalDetails;
import com.project.polaroid.dto.BoardDetailDTO;
import com.project.polaroid.dto.BoardPagingDTO;
import com.project.polaroid.dto.BoardSaveDTO;
import com.project.polaroid.dto.BoardUpdateDTO;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.service.BoardService;
import com.project.polaroid.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
public class BoardController {

    private final BoardService bs;
    private final MemberService memberService;
    private final MemberController memberController;

    @GetMapping
    public String main(@PageableDefault(page = 1) Pageable pageable, HttpSession session, Model model) {
        Page<BoardPagingDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList", boardList);

        if(session.getAttribute("LoginNumber") != null) {
            memberController.notice((Long) session.getAttribute("LoginNumber"));
            MemberEntity member = memberService.findById((Long) session.getAttribute("LoginNumber"));
            model.addAttribute("member", member);
            return "board/main";
        }
        else
            return "board/main";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @GetMapping("save")
    public String saveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        memberController.notice(principalDetails.getMember().getId());
        MemberEntity member=memberService.findById(principalDetails.getMember().getId());
        model.addAttribute("member", member);
        return "board/save";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @PostMapping("save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IOException {
        Long boardId = bs.save(boardSaveDTO);
        for (MultipartFile b: boardSaveDTO.getBoardFile()) {
            bs.saveFile(boardId, b);
        }
        return "redirect:/board/";
    }

    @GetMapping("{boardId}")
    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    public String findById(@PathVariable Long boardId, Model model, HttpSession session) {

        BoardDetailDTO boardDetailDTO = bs.findById(boardId);

        // 헤더
        Long memberId = (Long)session.getAttribute("LoginNumber");
        if(memberId != null) {
            memberController.notice(memberId);
            model.addAttribute("member", memberService.findById(memberId));
        }

        int likeStatus = bs.findLike(boardId, memberId);
        model.addAttribute("like",likeStatus);

        model.addAttribute("board", boardDetailDTO);
        model.addAttribute("imageSize", boardDetailDTO.getPhoto().size());
        model.addAttribute("commentList", boardDetailDTO.getCommentList());

        return "board/findById";
    }

    @GetMapping("search/{keyword}")
    public String search(@PathVariable String keyword, @PageableDefault(page =1) Pageable pageable, Model model,HttpSession session) {

        // 헤더
        Long memberId = (Long)session.getAttribute("LoginNumber");
        if(memberId != null) {
            memberController.notice(memberId);
            model.addAttribute("member", memberService.findById(memberId));
        }

        keyword = "#"+keyword;
        System.out.println("keyword = " + keyword);
        Page<BoardPagingDTO> boardList = bs.search(keyword, pageable);
        model.addAttribute("keyword", keyword);

        model.addAttribute("boardList", boardList);

        return "board/search";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @PostMapping("like")
    public @ResponseBody int like(Long boardId, Long memberId) {
        int result = bs.saveLike(boardId,memberId);
        return result;
    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @GetMapping("update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model,HttpSession session) {
        // 헤더
        Long memberId = (Long)session.getAttribute("LoginNumber");
        memberController.notice(memberId);
        model.addAttribute("member",memberService.findById(memberId));

        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        model.addAttribute("board", boardDetailDTO);
        return "board/update";
    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @PutMapping("{boardId}")
    public ResponseEntity update(@RequestBody BoardUpdateDTO boardUpdateDTO) {
        System.out.println("boardUpdateDTO = " + boardUpdateDTO);
        bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_MEMBER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_SELLER')" )
    @DeleteMapping("{boardId}")
    public ResponseEntity deleteById(@PathVariable Long boardId) {
        bs.deleteById(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
