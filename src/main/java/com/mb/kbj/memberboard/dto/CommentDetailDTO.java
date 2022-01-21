package com.mb.kbj.memberboard.dto;

import com.mb.kbj.memberboard.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDetailDTO {

    private Long commentId;
    private Long memberId;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static CommentDetailDTO toCommentDetailDTO(CommentEntity c) {
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        commentDetailDTO.setCommentId(c.getId());
        commentDetailDTO.setCommentWriter(c.getCommentWriter());
        commentDetailDTO.setCommentContents(c.getCommentContents());
        commentDetailDTO.setCreateTime(c.getCreateTime());
        commentDetailDTO.setUpdateTime(c.getUpdateTime());
        commentDetailDTO.setMemberId(c.getMemberEntity().getId());
        commentDetailDTO.setBoardId(c.getBoardEntity().getId());
        return commentDetailDTO;

    }
}
