package com.mb.kbj.memberboard.entity;

import com.mb.kbj.memberboard.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="commentId")
    private Long id;

    @Column
    private String commentWriter;
    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private BoardEntity boardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


    public static CommentEntity toSaveComment(CommentSaveDTO commentSaveDTO, MemberEntity memberEntity, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;
    }
}
