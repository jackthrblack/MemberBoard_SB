package com.mb.kbj.memberboard.dto;

import com.mb.kbj.memberboard.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDTO {

    private Long memberId;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberPhone;
    private String memberFileName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static MemberDetailDTO toMemberDetailDTO(MemberEntity e) {
        MemberDetailDTO memberDetailDTO = new MemberDetailDTO();
        memberDetailDTO.setMemberId(e.getId());
        memberDetailDTO.setMemberEmail(e.getMemberEmail());
        memberDetailDTO.setMemberPassword(e.getMemberPassword());
        memberDetailDTO.setMemberName(e.getMemberName());
        memberDetailDTO.setMemberPhone(e.getMemberPhone());
        memberDetailDTO.setMemberFileName(e.getMemberFileName());
        memberDetailDTO.setCreateTime(e.getCreateTime());
        memberDetailDTO.setUpdateTime(e.getUpdateTime());
        return memberDetailDTO;
    }
}
