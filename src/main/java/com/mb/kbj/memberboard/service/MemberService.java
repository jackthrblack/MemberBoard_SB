package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.MemberDetailDTO;
import com.mb.kbj.memberboard.dto.MemberLoginDTO;
import com.mb.kbj.memberboard.dto.MemberSaveDTO;
import com.mb.kbj.memberboard.dto.MemberUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO) throws IllegalStateException, IOException;

    String emailDp(String memberEmail);

    boolean login(MemberLoginDTO memberLoginDTO);

    List<MemberDetailDTO> findAll();

    void deleteById(Long memberId);

    Long findByMemberId(String memberEmail);

    MemberDetailDTO findById(Long memberId);

    MemberDetailDTO findByEmail(String memberEmail);

    Long update(MemberUpdateDTO memberUpdateDTO) throws IllegalStateException, IOException;
}
