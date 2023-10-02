package com.keduit.dto;

import com.keduit.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

/*
상품 저장 후 상품 이미지에 대한 데이터를 전달하는 클래스
 */
@Getter
@Setter
public class ItemImgDTO {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDTO of(ItemImg itemImg) {
        // itemImg 객체의 자료형과 맴버변수의 이름이 같을 때 ItemImgDTO로 값을 복사해서 반환
        // 생성안하고 바로 호출 되게 static으로!!
        // 변환해주는 함수
        return modelMapper.map(itemImg, ItemImgDTO.class);
    }
}
