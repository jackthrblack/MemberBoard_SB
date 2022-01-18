package com.mb.kbj.memberboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
public class MemberTest {

    @Test
    @Transactional
    @Rollback
    @DisplayName("멤버가입 조회")
    public void MemberSaveTest(){

    }
}
