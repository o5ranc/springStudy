package com.keduit.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="cart_item")
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    private int count;

    public static CartItem createCartItem(Cart cart, Item item, int count) {
        // 장바구니에 담을 상품 Entity 생성 반환
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public void addCount(int count) {
        // 기존 담겨있는 상품인데, 해당 상품을 추가로 장바구니에 담을때 수량 더해줌
        this.count += count;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}
