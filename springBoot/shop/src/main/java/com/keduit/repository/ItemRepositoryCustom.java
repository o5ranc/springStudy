package com.keduit.repository;

import com.keduit.dto.ItemSearchDTO;
import com.keduit.dto.MainItemDTO;
import com.keduit.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    //상품 조회 조건 itemSearchDto, 페이징 정보 pageable
    //getAdminItemPage 메소드,  Page<Item> 객체를 반환
    Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDto, Pageable pageable);
    Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDto, Pageable pageable);
}
