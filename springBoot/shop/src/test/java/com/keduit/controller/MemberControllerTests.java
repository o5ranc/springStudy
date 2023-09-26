package com.keduit.controller;

import com.keduit.dto.MemberFormDTO;
import com.keduit.entity.Member;
import com.keduit.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTests<MemberFormDto> {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password) {
        MemberFormDTO memberFormDTO = new MemberFormDTO();

        memberFormDTO.setEmail(email);
        memberFormDTO.setName("한정교");
        memberFormDTO.setAddress("서울시 관악구 신림동");
        memberFormDTO.setPassword(password);

        Member member = Member.createMember(memberFormDTO, passwordEncoder);

        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email ="test@abc.kr";
        String password="1111";

        this.createMember(email, password);
        
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email)
                .password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
                
    }
    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email ="abc.kr";
        String password="1111";

        this.createMember(email, password);
        
        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email)
                .password("pa"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
                
    }

    @Test
    @DisplayName("상품 등록 페이지 권한 테스트 하기")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void itemFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("상품 등록 페이지 권한 테스트 하기")
    @WithMockUser(username = "user", roles = "USER")
    public void itemFormNotAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new")) // 이 경로에 403 Forbidden 결과를 받기를 기대함
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
