package com.mb.kbj.memberboard.controller;

import com.mb.kbj.memberboard.common.PagingConst;
import com.mb.kbj.memberboard.dto.BoardDetailDTO;
import com.mb.kbj.memberboard.dto.BoardSaveDTO;
import com.mb.kbj.memberboard.dto.BoardUpdateDTO;
import com.mb.kbj.memberboard.dto.CommentDetailDTO;
import com.mb.kbj.memberboard.service.BoardService;
import com.mb.kbj.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService bs;
    private final CommentService cs;

    @GetMapping("/save")
    public String save_form(){

        return "/board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) throws IllegalStateException, IOException{
        System.out.println("boardSaveDTO = " + boardSaveDTO);
        Long boardId = bs.save(boardSaveDTO);
        System.out.println("boardSaveDTO2 = " + boardSaveDTO);
        return "index";
    }

/*    @GetMapping
    public String paging(@PageableDefault(page = 1, size = 5, sort = "id", direction = Sort.Direction.DESC)Pageable pageable, Model model){

        Page<BoardDetailDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList",boardList);
        System.out.println("c:"+boardList);
        int startPage = (((int)(Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT)))-1)*PagingConst.BLOCK_LIMIT+1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT-1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT-1:boardList.getTotalPages();
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "/board/findAll";
    }*/

    @GetMapping
    public String paging(@PageableDefault(page=1)Pageable pageable, Model model){
        // @PageableDefault(page=1)Pageable => 페이지 요청값이 없을때는 기본적으로 1페이지를 띄우겠다. 디폴트값 설정

        //Page라는 객체가 있다.
        Page<BoardDetailDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList",boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);
        return  "board/findAll";
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteById(@PathVariable("boardId") Long boardId){
        bs.deleteById(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public String findById(@PathVariable("boardId") Long boardId, Model model){
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);
        List<CommentDetailDTO> commentList = cs.findAll(boardId);
        model.addAttribute("board", boardDetailDTO);
        model.addAttribute("commentList",commentList);
        return "/board/findById";
    }

    @GetMapping("/update/{boardId}")
    public String update_form(Model model, @PathVariable Long boardId){
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board",board);
        return "/board/update";
    }

    @PutMapping("{boardId}")
    public ResponseEntity boardUpdate(@ModelAttribute BoardUpdateDTO boardUpdateDTO)throws IllegalStateException, IOException{
        Long boardId = bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
