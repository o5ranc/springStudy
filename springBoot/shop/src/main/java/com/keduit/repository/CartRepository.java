package com.keduit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.keduit.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 현재 로그인한 회
    // 원의 Cart Entity를 찾기 위한 메서드
    Cart findByMemberId(Long memberId);
}
