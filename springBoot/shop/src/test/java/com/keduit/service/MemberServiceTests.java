package com.keduit.service;

import com.keduit.dto.MemberFormDTO;
import com.keduit.entity.Member;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTests {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDTO memberFormDTO = new MemberFormDTO();

        memberFormDTO.setEmail("abc@abc.com");
        memberFormDTO.setAddress("서울시 관악구 신림동");
        memberFormDTO.setName("한정교");
        memberFormDTO.setPassword("1111");

        return Member.createMember(memberFormDTO, passwordEncoder);
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

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void testDuplicateMember(){
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);
        // Throwable 에러 던지기!
        // IllegalStateException.class
        Throwable err = assertThrows(IllegalStateException.class, ()->{memberService.saveMember(member2);});

        //같을 경우 통과.
        assertEquals("이미 가입된 회원입니다.", err.getMessage());

    }


}
