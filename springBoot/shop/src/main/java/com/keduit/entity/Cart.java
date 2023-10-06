package com.keduit.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter @Setter
@ToString
public class Cart extends BaseEntity {
    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 일대일 매핑, 지연로딩(조인해서 가져올때 필요한 경우에 가져오기)
    @JoinColumn(name="member_id") // 외래키 이름 작성으로 외래키 매핑
    private Member member;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}

