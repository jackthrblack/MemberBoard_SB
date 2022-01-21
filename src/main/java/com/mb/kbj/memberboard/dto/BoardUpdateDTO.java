package com.mb.kbj.memberboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardUpdateDTO {

    private Long boardId;
    private Long memberdId;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private MultipartFile boardFile;
    private String boardFileName;
}
