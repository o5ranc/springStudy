package com.keduit.dto;

import com.keduit.constant.ItemSellStatus;
import com.keduit.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/*
상품 데이터정보를 전달하는 클래스
 */
@Getter
@Setter
public class ItemFormDTO {
    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemNm;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;
    private ItemSellStatus itemSellStatus;
    private List<ItemImgDTO> itemImgDTOList = new ArrayList<>();
    private List<Long> itemImgIds = new ArrayList<>();
    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        // 서로 다른 클래스의 값을 필드의 이름과 자료형이 같으면 getter, setter를 통해 값을 복사해서 객체 반환
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDTO of(Item item) {
        return modelMapper.map(item, ItemFormDTO.class);
    }

}
