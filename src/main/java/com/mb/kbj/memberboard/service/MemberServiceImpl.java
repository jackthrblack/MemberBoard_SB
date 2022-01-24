package com.mb.kbj.memberboard.service;

import com.mb.kbj.memberboard.dto.MemberDetailDTO;
import com.mb.kbj.memberboard.dto.MemberLoginDTO;
import com.mb.kbj.memberboard.dto.MemberSaveDTO;
import com.mb.kbj.memberboard.dto.MemberUpdateDTO;
import com.mb.kbj.memberboard.entity.MemberEntity;
import com.mb.kbj.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository mr;

    @Override
    public Long save(MemberSaveDTO memberSaveDTO) throws IllegalStateException, IOException {

        // dto에 담긴 파일을 가져옴
        MultipartFile memberFile = memberSaveDTO.getMemberFile();
        // 파일 이름을 가져옴(파일이름을 DB에 저장하기 위해) / 파일의 이름을 가져옴
        String memberFileName = memberFile.getOriginalFilename();
        // 파일명 중복을 피하기 위해 파일이름앞에 현재 시간값을 붙임.
        memberFileName = System.currentTimeMillis() + "-" + memberFileName;
        // 파일 저장 경로 세팅
        String savePath = "C:\\devleopment\\source\\springboot\\MemberBoardProject\\src\\main\\resources\\static\\img\\" + memberFileName;
        // bfile이 비어있지 않다면(즉 파일이 있으면) savePath에 저장을 하겠다.
        if (!memberFile.isEmpty()) {
            memberFile.transferTo(new File(savePath));
        }
        // 여기까지의 내용은 파일을 저장하는 과정
        memberSaveDTO.setMemberFileName(memberFileName);

        MemberEntity memberEntity = MemberEntity.toSaveMember(memberSaveDTO);
        return mr.save(memberEntity).getId();
    }

    @Override
    public String emailDp(String memberEmail) {

        MemberEntity emailCheckResult = mr.findByMemberEmail(memberEmail);
        if (emailCheckResult == null) {
            return "ok";
        } else {
            return "no";
        }
    }

    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if(memberEntity != null){
            if (memberEntity.getMemberPassword().equals(memberLoginDTO.getMemberPassword())){
                return true;
            }else {
                return false;
            }}
        else {
            return false;
        }
    }

    @Override
    public List<MemberDetailDTO> findAll() {

        List<MemberEntity> memberEntityList=mr.findAll();
        List<MemberDetailDTO> memberList = new ArrayList<>();
        for(MemberEntity e : memberEntityList){
            memberList.add(MemberDetailDTO.toMemberDetailDTO(e));
        }
        return memberList;
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }

    @Override
    public Long findByMemberId(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        Long memberId = memberEntity.getId();
        return memberId;
    }

    @Override
    public MemberDetailDTO findById(Long memberId) {
        MemberEntity memberEntity = mr.findById(memberId).get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDTO(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public Long update(MemberUpdateDTO memberUpdateDTO) throws IllegalStateException, IOException {

        // dto에 담긴 파일을 가져옴
        MultipartFile memberFile = memberUpdateDTO.getMemberFile();
        // 파일 이름을 가져옴(파일이름을 DB에 저장하기 위해) / 파일의 이름을 가져옴
        String memberFileName = memberFile.getOriginalFilename();
        // 파일명 중복을 피하기 위해 파일이름앞에 현재 시간값을 붙임.
        memberFileName = System.currentTimeMillis() + "-" + memberFileName;
        // 파일 저장 경로 세팅
        String savePath = "C:\\devleopment\\source\\springboot\\MemberBoardProject\\src\\main\\resources\\static\\img\\" + memberFileName;
        // bfile이 비어있지 않다면(즉 파일이 있으면) savePath에 저장을 하겠다.
        if (!memberFile.isEmpty()) {
            memberFile.transferTo(new File(savePath));
        }
        // 여기까지의 내용은 파일을 저장하는 과정
        memberUpdateDTO.setMemberFileName(memberFileName);

        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberUpdateDTO);

        return mr.save(memberEntity).getId();
    }


}
