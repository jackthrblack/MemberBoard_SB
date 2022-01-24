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

import static com.mb.kbj.memberboard.common.PagingConst.BLOCK_LIMIT;

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

    @GetMapping
    public String paging(@PageableDefault(page=1)Pageable pageable, Model model){
        // @PageableDefault(page=1)Pageable => 페이지 요청값이 없을때는 기본적으로 1페이지를 띄우겠다. 디폴트값 설정

        //Page라는 객체가 있다.
        Page<BoardDetailDTO> boardList = bs.paging(pageable);
        model.addAttribute("boardList",boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = ((startPage + BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + BLOCK_LIMIT - 1 : boardList.getTotalPages();
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
        bs.hits(boardId);
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

    /*@GetMapping("/search")
    public String search(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
                            Model model){
        System.out.println("searchType1 = " + searchType);
        System.out.println("keyword1 = " + keyword);

        List<BoardDetailDTO> boardList = bs.search(searchType,keyword);
        System.out.println("boardList = " + boardList);

        System.out.println("searchType2 = " + searchType);
        System.out.println("keyword2 = " + keyword);

        model.addAttribute("boardList",boardList);
        System.out.println("boardList2 = " + boardList);

        return "/board/search";
        *//*model.addAttribute("searchType",searchType);
        model.addAttribute("keyword",keyword);*//*
    }*/

    @GetMapping("/search")
    public String search(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
                         Model model, @PageableDefault(page=0, size = 5, sort ="id", direction = Sort.Direction.DESC)Pageable pageable){
        System.out.println("searchType1 = " + searchType);
        System.out.println("keyword1 = " + keyword);

        Page<BoardDetailDTO> boardList = bs.findAll(searchType,keyword,pageable);
        System.out.println("boardList = " + boardList);

        int startPage = (((int) (Math.ceil((double) (pageable.getPageNumber()+1) /BLOCK_LIMIT))) - 1) * BLOCK_LIMIT + 1;
        int endPage = ((startPage + BLOCK_LIMIT - 1) < (boardList.getTotalPages()+1)) ? startPage + BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boardList",boardList);
        model.addAttribute("searchType",searchType);
        model.addAttribute("keyword",keyword);

        System.out.println("searchType2 = " + searchType);
        System.out.println("keyword2 = " + keyword);
        System.out.println("boardList2 = " + boardList);
        return "/board/search";
    }
}
