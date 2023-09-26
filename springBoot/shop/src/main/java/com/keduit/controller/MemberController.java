package com.keduit.controller;

import com.keduit.dto.MemberFormDTO;
import com.keduit.entity.Member;
import com.keduit.service.MemberService;
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
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDTO", new MemberFormDTO());
        return "member/memberForm";
    }

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDTO memberFormDTO, BindingResult bindingResult, Model model) {

        System.out.println("memberForm11111 : " + memberFormDTO + ", bindingResult : " + bindingResult);
        if(bindingResult.hasErrors()) {
            System.out.println("memberForm2222");
            return "member/memberForm";
        }

        try {
            //System.out.println("memberFormDTO : " + memberFormDTO);
            Member member = Member.createMember(memberFormDTO, passwordEncoder);
            //System.out.println("member : " + member.getRole());
            memberService.saveMember(member);
        } catch(IllegalStateException e) { // IllegalAccessError e
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginMember(){
        return "member/memberLoginForm";
    }

    @GetMapping("login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/memberLoginForm";
    }

}
