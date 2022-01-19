package com.mb.kbj.memberboard.controller;

import com.mb.kbj.memberboard.dto.MemberDetailDTO;
import com.mb.kbj.memberboard.dto.MemberLoginDTO;
import com.mb.kbj.memberboard.dto.MemberSaveDTO;
import com.mb.kbj.memberboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.mb.kbj.memberboard.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService ms;

   @GetMapping("/save")
    public String save_form(){
       return "member/save";
   }

   @PostMapping("/save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO) throws IllegalStateException, IOException {
       Long memberId=ms.save(memberSaveDTO);
       return "index";
   }

   @PostMapping("/emailDuplicate")
    public @ResponseBody String emailDp(@RequestParam("memberEmail") String memberEmail){
       System.out.println("memberEmail = " + memberEmail);
       String result = ms.emailDp(memberEmail);
       System.out.println("result = " + result);
       return result;
   }

   @GetMapping("/login")
    public String login_form(){
       return "/member/login";
   }

   @PostMapping("/login")
    public String login(@ModelAttribute MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpSession session){

       boolean loginResult = ms.login(memberLoginDTO);

      /* if(ms.findByEmail(memberLoginDTO)){
           session.setAttribute("loginEmail",memberLoginDTO.getMemberEmail());
           Long loginId = ms.findByMemberId(memberLoginDTO.getMemberEmail());
           session.setAttribute("loginId",loginId);
           System.out.println(loginId);
           return "index";
       }else{
           return "/member/login";
       }*/

       if(loginResult){
           session.setAttribute(LOGIN_EMAIL,memberLoginDTO.getMemberEmail());
           Long loginId = ms.findByMemberId(memberLoginDTO.getMemberEmail());
           session.setAttribute("loginId",loginId);
           return "index";
       }else{
           return "/member/login";
       }


       /*if(loginResult){
           session.setAttribute(LOGIN_EMAIL,memberLoginDTO.getMemberEmail());
           String redirectURL = (String) session.getAttribute("redirectURL");
           if(redirectURL != null) {
               return "redirect:"+redirectURL;

           }else{
               return "redirect:/";
           }
       }else{
           // 로그인이 안됐을경우
           return "/member/login";
       }*/
   }

   @GetMapping("/logout")
    public String logout(HttpSession session){
       session.invalidate();
       return "index";
   }

   @GetMapping("/")
    public String findAll(Model model){
       List<MemberDetailDTO> memberList = ms.findAll();
       model.addAttribute("memberList",memberList);
       return "/member/findAll";

   }

   @DeleteMapping("/{memberId}")
    public ResponseEntity deleteById(@PathVariable("memberId") Long memberId){
       ms.deleteById(memberId);
       return new ResponseEntity(HttpStatus.OK);
   }

   @GetMapping("/${loginEmail}")
    public String findById(@PathVariable("memberId") Long memberId, Model model){
       MemberDetailDTO member = ms.findByEMail(memberId);
       model.addAttribute("member",member);
       return "/member/mypage";
   }

  /*  @PostMapping("/login")
    public String login(@Validated @ModelAttribute("member") MemberLoginDTO memberLoginDTO,
                        BindingResult bindingResult,HttpSession session){
        if (bindingResult.hasErrors()){
            return "/member/login";
        }

        if(ms.findByEmail(memberLoginDTO)){
            session.setAttribute("loginEmail",memberLoginDTO.getMemberEmail());
            Long loginId = ms.findByMemberId(memberLoginDTO.getMemberEmail());
            session.setAttribute("loginId",loginId);
            System.out.println(loginId);
            return "index";
        }else{
            return "/member/login";
        }

    }*/
}
