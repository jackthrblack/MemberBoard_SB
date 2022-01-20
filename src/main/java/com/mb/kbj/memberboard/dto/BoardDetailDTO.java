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
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private String boardFileName;
    private LocalDateTime boardDate;


}
