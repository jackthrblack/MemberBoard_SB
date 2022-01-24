package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.common.PagingConst;
import com.mb.kbj.memberboard.dto.BoardDetailDTO;
import com.mb.kbj.memberboard.dto.BoardSaveDTO;
import com.mb.kbj.memberboard.dto.BoardUpdateDTO;
import com.mb.kbj.memberboard.entity.BoardEntity;
import com.mb.kbj.memberboard.entity.MemberEntity;
import com.mb.kbj.memberboard.repository.BoardRepository;
import com.mb.kbj.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /*@Override
    public Page<BoardDetailDTO> paging(Pageable pageable) {
        System.out.println("pageable = " + pageable);
        Page<BoardEntity> boardEntityPage = br.findAll(pageable);
        System.out.println("boardEntityPage.getContent() = " + boardEntityPage.getContent());
        Page<BoardDetailDTO> boardList = boardEntityPage.map(
                board -> new BoardDetailDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardFileName(),
                        board.getCreateTime())
        );
        System.out.println("sboList:"+boardList);
        return boardList;
    }*/

    @Override
    public Page<BoardDetailDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.

        // page = page-1;
        page=(page==1)? 0:(page-1);
        // PageRequest=> 페이지요청 / page => 몇번째? / PagingConst.PAGE_LIMIT => 몇개씩?
        // Sort.by(Sort.Direction.DESC,"id") => 어떤식으로 볼거고 어떤걸 기준으로("id"는 Entity필드 이름으로 와야한다.)
        Page<BoardEntity> boardEntities =
                br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        // Page<BoardEntity> => Page<BoardPagingDTO>
        // 기존 방식대로하면 안된다. -> 페이지 객체가 제공하는 메서드드를 못 쓴다! 이렇게 단순하게 옮기면
        //map(): 엔티티가 담긴 페이지 객체를 dto가 담긴 페이지객체로 변환해주는 역할
        Page<BoardDetailDTO> boardList = boardEntities.map(
                board -> new BoardDetailDTO(board.getId(),
                        board.getMemberEntity().getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardFileName(),
                        board.getCreateTime(),
                        board.getBoardHits())

        );
        return boardList;
    }

    @Override
    public void deleteById(Long boardId) {
        br.deleteById(boardId);
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        BoardEntity boardEntity = br.findById(boardId).get();
        BoardDetailDTO boardDetailDTO = BoardDetailDTO.toBoardDetail(boardEntity);
        return boardDetailDTO;
    }

    @Override
    public Long update(BoardUpdateDTO boardUpdateDTO) throws IllegalStateException, IOException {
        // dto에 담긴 파일을 가져옴
        MultipartFile boardFile = boardUpdateDTO.getBoardFile();
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
        boardUpdateDTO.setBoardFileName(boardFileName);

        MemberEntity memberEntity = mr.findByMemberEmail(boardUpdateDTO.getBoardWriter());
        BoardEntity boardEntity = BoardEntity.toUpdateBoard(boardUpdateDTO,memberEntity);

        return br.save(boardEntity).getId();
    }

    @Override
    public Page<BoardDetailDTO> findAll(String searchType, String keyword, Pageable pageable) {
        Page<BoardEntity> boardEntities = null;
                /*br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));*/

        if(searchType.equals("boardTitle")){
            System.out.println("title");
            boardEntities = br.findByBoardTitleContaining(keyword,pageable);
        }else if(searchType.equals("boardWriter")){
            System.out.println("writer");
            boardEntities=br.findByBoardWriterContaining(keyword,pageable);
        }else{
            System.out.println("contents");
            boardEntities=br.findByBoardContentsContaining(keyword,pageable);
            System.out.println("asdfsadf="+boardEntities);
        }

        Page<BoardDetailDTO> boardList = boardEntities.map(
                board -> new BoardDetailDTO(board.getId(),
                        board.getMemberEntity().getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardContents(),
                        board.getBoardFileName(),
                        board.getCreateTime(),
                        board.getBoardHits())
        );
        return boardList;
    }

    @Transactional
    @Override
    public void hits(Long boardId) {
        br.hits(boardId);
    }

   /* @Override
    public List<BoardDetailDTO> search(String searchType, String keyword) {

        List<BoardEntity> boardEntity = null;

        if(searchType.equals("boardTitle")){
            System.out.println("title");
            boardEntity = br.findByBoardTitleContaining(keyword);
        }else if(searchType.equals("boardWriter")){
            System.out.println("writer");
            boardEntity=br.findByBoardWriterContaining(keyword);
        }else{
            System.out.println("contents");
            boardEntity=br.findByBoardContentsContaining(keyword);
            System.out.println("asdfsadf="+boardEntity);
        }

        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for(BoardEntity b: boardEntity){
            boardDetailDTOList.add(BoardDetailDTO.toBoardDetail(b));
        }
        return boardDetailDTOList;
    }*/


}
