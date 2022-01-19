package com.mb.kbj.memberboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;



@Data
public class BoardSaveDTO {

    private Long memberId;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private MultipartFile boardFile;
    private String boardFileName;


}
