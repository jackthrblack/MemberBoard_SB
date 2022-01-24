package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.CommentDetailDTO;
import com.mb.kbj.memberboard.dto.CommentSaveDTO;
import com.mb.kbj.memberboard.entity.BoardEntity;
import com.mb.kbj.memberboard.entity.CommentEntity;
import com.mb.kbj.memberboard.entity.MemberEntity;
import com.mb.kbj.memberboard.repository.BoardRepository;
import com.mb.kbj.memberboard.repository.CommentRepository;
import com.mb.kbj.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository cr;
    private final BoardRepository br;
    private final MemberRepository mr;

    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(commentSaveDTO.getCommentWriter());
        BoardEntity boardEntity = br.findById(commentSaveDTO.getBoardId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveComment(commentSaveDTO,memberEntity,boardEntity);
        cr.save(commentEntity).getId();
        return cr.save(commentEntity).getId();
    }

    @Override
    public List<CommentDetailDTO> findAll(Long boardId) {

        BoardEntity boardEntity = br.findById(boardId).get();
        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        List<CommentDetailDTO> commentList = new ArrayList<>();
        for(CommentEntity c:commentEntityList){
            CommentDetailDTO commentDetailDTO = CommentDetailDTO.toCommentDetailDTO(c);
            commentList.add(commentDetailDTO);
        }
        return commentList;
    }

    @Override
    public void deleteById(Long commentId) {
        cr.deleteById(commentId);
    }
}
