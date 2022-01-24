package com.mb.kbj.memberboard.repository;

import com.mb.kbj.memberboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Page<BoardEntity> findByBoardTitleContaining(String keyword, Pageable pageable);

    Page<BoardEntity> findByBoardWriterContaining(String keyword, Pageable pageable);

    Page<BoardEntity> findByBoardContentsContaining(String keyword, Pageable pageable);

    @Modifying
    @Query("update BoardEntity as b set b.boardHits = b.boardHits+1 where b.id= :boardId")
    void hits(Long boardId);



    /*List<BoardEntity> findByBoardTitleContaining(String keyword);

    List<BoardEntity> findByBoardWriterContaining(String keyword);

    List<BoardEntity> findByBoardContentsContaining(String keyword);*/
}
