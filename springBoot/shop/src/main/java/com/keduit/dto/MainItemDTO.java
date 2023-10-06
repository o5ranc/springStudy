package com.keduit.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDTO {
    private Long id;

    private String itemNm;

    private String itemDetail;

    private  String imgUrl;

    private Integer price;

    @QueryProjection //QueryProjection : QueryDSL에서 사용되는 어노테이션 중 하나.
    public MainItemDTO(Long id, String itemNm, String itemDetail, String imgUrl, Integer price){
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }

}
