package com.mb.kbj.memberboard.controller;

import com.mb.kbj.memberboard.dto.MemberSaveDTO;
import com.mb.kbj.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService ms;

   @GetMapping("/save")
    public String save_form(){
       return "/member/save";
   }

   @PostMapping("/save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) throws IllegalStateException, IOException {
       Long memberId=ms.save(memberSaveDTO);
       return "index";
   }

   @PostMapping("/emailDuplicate")
    public @ResponseBody String emailDp(@RequestParam("memberEmail") String memberEmail){
       String result = ms.emailDp(memberEmail);
       return result;
   }

   @GetMapping("/login")
    public String login_form(){
       return "/member/login";
   }

   @PostMapping("")
}
