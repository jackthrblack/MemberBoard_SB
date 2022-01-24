package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.CommentDetailDTO;
import com.mb.kbj.memberboard.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);

    void deleteById(Long commentId);
}
