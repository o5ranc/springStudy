package com.keduit.dto;

import com.keduit.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDTO {

    //현재 시간과 상품 등록일을 비교, 조회 시간
    private String searchDateType;

    //enum 타입으로 준 검색 판매 상태
    private ItemSellStatus searchSellStatus;

    //상품을 조회할 때 어떤 유형으로 조회할지 선택
    private String searchBy;

    //조회할 검색어 저장할 변수
    private String searchQuery= "";

}
