package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.MemberSaveDTO;

import java.io.IOException;

public interface MemberService {
    Long save(MemberSaveDTO memberSaveDTO) throws IllegalStateException, IOException;

    String emailDp(String memberEmail);
}
