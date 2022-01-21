package com.mb.kbj.memberboard.controller;

import com.mb.kbj.memberboard.dto.CommentDetailDTO;
import com.mb.kbj.memberboard.dto.CommentSaveDTO;
import com.mb.kbj.memberboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService cs;

    @PostMapping ("/save")
        public @ResponseBody
        List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO){
        cs.save(commentSaveDTO);
        List<CommentDetailDTO> commentList=cs.findAll(commentSaveDTO.getBoardId());
        return commentList;
        }
}
