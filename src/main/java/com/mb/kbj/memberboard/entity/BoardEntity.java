package com.mb.kbj.memberboard.entity;

import com.mb.kbj.memberboard.dto.BoardSaveDTO;
import com.mb.kbj.memberboard.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="board_table")
public class BoardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column
    private String boardTitle;

    @Column
    private String boardWriter;

    @Column
    private String boardContents;

    @Column
    private String boardFileName;

    @Column
    private int boardHits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "boardEntity", cascade =CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();


    public static BoardEntity toSaveBoard(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        boardEntity.setBoardFileName(boardSaveDTO.getBoardFileName());
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardHits(0);
        return boardEntity;

    }

    public static BoardEntity toUpdateBoard(BoardUpdateDTO boardUpdateDTO, MemberEntity memberEntity) {

        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardUpdateDTO.getBoardId());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        boardEntity.setBoardFileName(boardUpdateDTO.getBoardFileName());
        boardEntity.setMemberEntity(memberEntity);
        boardEntity.setBoardHits(boardUpdateDTO.getBoardHits());
        return boardEntity;
    }
}
