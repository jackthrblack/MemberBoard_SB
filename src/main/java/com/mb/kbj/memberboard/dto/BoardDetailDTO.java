package com.mb.kbj.memberboard.dto;

import com.mb.kbj.memberboard.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO {
    private Long boardId;
    private Long memberId;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private String boardFileName;
    private LocalDateTime boardDate;


    public static BoardDetailDTO toBoardDetail(BoardEntity boardEntity) {

        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBoardFileName(boardEntity.getBoardFileName());
        if(boardEntity.getUpdateTime()==null){
            boardDetailDTO.setBoardDate(boardEntity.getCreateTime());
        }else{
            boardDetailDTO.setBoardDate(boardEntity.getUpdateTime());
        }
        boardDetailDTO.setMemberId(boardEntity.getId());
        return boardDetailDTO;
    }
}
