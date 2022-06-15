package com.shopping.controller;

import com.shopping.dto.MemberFormDto;
import com.shopping.entity.Member;
import com.shopping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/members")
public class MemberController {
    @GetMapping(value = "/login") // form 태그와 SecurityConfig 파일에 정의 되어 있습니다.
    public String loginMember(){
        return "/member/memberLoginForm" ;
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        // "loginErrorMsg"와 관련된 내용은 파일 memberLoginForm.html 안에 구현 되어 있습니다.
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀 번호를 확인해 주세요.") ;
        return "member/memberLoginForm" ;
    }

    // Get 방식) http://localhost:8989/members/new
    @GetMapping(value = "/new")
    public String memberForm(Model model){
        // dto 객체(화면을 통하여 넘겨 주거나 받는 객체)를 모델에 바인딩하면 실제 request 영역에 데이터가 들어 갑니다.
        model.addAttribute("memberFormDto", new MemberFormDto()) ;
        return "member/memberForm" ;
    }

    private final MemberService memberService ;
    private final PasswordEncoder passwordEncoder ;

    /*
    @Valid는 유효성 검사를 수행해주는 어노테이션
     */

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto,
                            BindingResult bindingResult,
                            Model model){
        if(bindingResult.hasErrors()){ // 필드에 문제가 있으면
            return "/member/memberForm" ;
        }

        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member) ;
            return "redirect:/" ; // 메인 페이지로 이동
        }catch (IllegalStateException e){
            return "/member/memberForm" ;
        }
    }
}











