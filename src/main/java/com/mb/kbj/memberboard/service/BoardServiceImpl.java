package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.BoardSaveDTO;
import com.mb.kbj.memberboard.entity.BoardEntity;
import com.mb.kbj.memberboard.entity.MemberEntity;
import com.mb.kbj.memberboard.repository.BoardRepository;
import com.mb.kbj.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository br;
    private final MemberRepository mr;


    @Override
    public Long save(BoardSaveDTO boardSaveDTO) throws IllegalStateException, IOException {
        // dto에 담긴 파일을 가져옴
        MultipartFile boardFile = boardSaveDTO.getBoardFile();
        // 파일 이름을 가져옴(파일이름을 DB에 저장하기 위해) / 파일의 이름을 가져옴
        String boardFileName = boardFile.getOriginalFilename();
        // 파일명 중복을 피하기 위해 파일이름앞에 현재 시간값을 붙임.
        boardFileName = System.currentTimeMillis() + "-" + boardFileName;
        // 파일 저장 경로 세팅
        String savePath = "C:\\devleopment\\source\\springboot\\MemberBoardProject\\src\\main\\resources\\static\\boardImg\\" + boardFileName;
        // bfile이 비어있지 않다면(즉 파일이 있으면) savePath에 저장을 하겠다.
        if (!boardFile.isEmpty()) {
            boardFile.transferTo(new File(savePath));
        }
        // 여기까지의 내용은 파일을 저장하는 과정
        boardSaveDTO.setBoardFileName(boardFileName);

        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toSaveBoard(boardSaveDTO, memberEntity);
        return br.save(boardEntity).getId();
    }
}
