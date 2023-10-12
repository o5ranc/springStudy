package com.keduit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDTO {
    private Long cartItemId; // 장바구니 상품 아이디
    private String itemNm; // 상품명
    private int price; // 상품금액
    private int count; // 수량
    private String imgUrl; // 상품 이미지 경로

    // Repository에서 지연로딩을 위해 생성자를 이용한 반환값을 제공하기 위해
    public CartDetailDTO(Long cartItemId, String itemNm, int price, int count, String imgUrl) {
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
