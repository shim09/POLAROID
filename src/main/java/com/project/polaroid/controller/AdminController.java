package com.project.polaroid.controller;

import com.project.polaroid.entity.BoardEntity;
import com.project.polaroid.entity.GoodsEntity;
import com.project.polaroid.entity.MemberEntity;
import com.project.polaroid.entity.SellerEntity;
import com.project.polaroid.repository.BoardRepository;
import com.project.polaroid.repository.GoodsRepository;
import com.project.polaroid.repository.MemberRepository;
import com.project.polaroid.service.MemberService;
import com.project.polaroid.service.SellerRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    public final SellerRoleService sellerRoleService;
    public final MemberRepository mr;
    public final BoardRepository br;
    public final GoodsRepository gr;

    @GetMapping("admin/giveRole/{memberId}")
    public String giveRole(@PathVariable Long memberId){
        sellerRoleService.giveRole(memberId);
        return "redirect:/admin/sellerList";
    }

    @GetMapping("admin/memberList")
    public String memberList(Model model) {
        List<MemberEntity> memberEntityList = mr.findAllByOrderByIdDesc();
        model.addAttribute("member", memberEntityList);
        return "admin/memberList";
    }

    @GetMapping("admin/boardList")
    public String boardList(Model model) {
        List<BoardEntity> boardEntityList = br.findAllByOrderByIdDesc();
        model.addAttribute("board", boardEntityList);
        return "admin/boardList";
    }
    @GetMapping("admin/goodsList")
    public String goodsList(Model model) {
        List<GoodsEntity> goodsEntityList = gr.findAllByOrderByIdDesc();
        model.addAttribute("goods", goodsEntityList);
        return "admin/goodsList";
    }

    @GetMapping("admin/sellerList")
    public String sellerList(Model model) {
        List <SellerEntity> sellerEntityList = sellerRoleService.findAll();
        model.addAttribute("sellerList",sellerEntityList);
        return "admin/sellerList";
    }

    @DeleteMapping("admin/board/{boardId}")
    public ResponseEntity boardDelete(@PathVariable Long boardId) {
        br.deleteById(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("admin/goods/{goodsId}")
    public ResponseEntity goodsDelete(@PathVariable Long goodsId) {
        gr.deleteById(goodsId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
