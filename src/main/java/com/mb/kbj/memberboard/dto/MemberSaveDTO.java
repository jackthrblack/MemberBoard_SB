package com.mb.kbj.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {

    @NotBlank(message = "이메일 꼭 입력해주세요.")
    private String memberEmail;
    @NotBlank
    @Length(min = 2, max = 8, message ="2~8자로 입력해주세요." )
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private MultipartFile memberFile;
    private String memberFileName;

}
