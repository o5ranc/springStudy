package com.keduit.service;

import com.keduit.entity.Member;
import com.keduit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // 문제 발생시 롤백되도록
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; // memberRepository 주입

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
