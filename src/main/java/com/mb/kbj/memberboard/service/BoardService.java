package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.BoardSaveDTO;

import java.io.IOException;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO) throws IllegalStateException, IOException;
}
