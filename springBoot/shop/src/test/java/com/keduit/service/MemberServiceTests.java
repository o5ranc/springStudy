package com.keduit.service;

import com.keduit.dto.MemberFormDTO;
import com.keduit.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // test시 all-in-one 어노테이션
@Transactional
public class MemberServiceTests {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDTO memberFormDTO = new MemberFormDTO();
        memberFormDTO.setEmail("test@email.com");
        memberFormDTO.setName("한정교");
        memberFormDTO.setAddress("서울시 관악구 신림동");
        memberFormDTO.setPassword("1111");
        return Member.CreateMember(memberFormDTO, passwordEncoder);
    }
    
    @Test
    @DisplayName("회원가입 테스트")
    public void testSaveMember() {
        Member member = createMember();
        Member saveMember = memberService.saveMember(member);

        assertEquals(member.getEmail(), saveMember.getEmail());
        assertEquals(member.getName(), saveMember.getName());
        assertEquals(member.getAddress(), saveMember.getAddress());
        assertEquals(member.getPassword(), saveMember.getPassword());
        assertEquals(member.getRole(), saveMember.getRole());
    }
}
