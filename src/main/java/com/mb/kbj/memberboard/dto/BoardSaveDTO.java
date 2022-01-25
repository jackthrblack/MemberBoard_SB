package com.mb.kbj.memberboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


@Data
public class BoardSaveDTO {

    private Long memberId;
    @NotBlank(message = "제목은 꼭 입력해주세요.ㄹㄹㄹ")
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private MultipartFile boardFile;
    private String boardFileName;


}
