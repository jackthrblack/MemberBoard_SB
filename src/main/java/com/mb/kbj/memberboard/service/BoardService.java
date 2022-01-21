package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.BoardDetailDTO;
import com.mb.kbj.memberboard.dto.BoardSaveDTO;
import com.mb.kbj.memberboard.dto.BoardUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO) throws IllegalStateException, IOException;

    Page<BoardDetailDTO> paging(Pageable pageable);

    void deleteById(Long boardId);

    BoardDetailDTO findById(Long boardId);

    Long update(BoardUpdateDTO boardUpdateDTO) throws IllegalStateException, IOException;
}
