package com.keduit.repository;

import com.keduit.entity.CartItem;
import com.keduit.dto.CartDetailDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회합니다.
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    // 생성자를 활용한 DTO 반환은 아래처럼 DTO클래스에 명시한 순서로 맞춰 줘야함
    @Query("select new com.keduit.dto.CartDetailDTO(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
        "from CartItem ci, ItemImg im " +
        "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " + // 장바구니에 담겨져 있는
            "and im.repImgYn = 'Y' " + // 대표 이미지만 가져오게
            "order by ci.regTime desc"
    )
    List<CartDetailDTO> findCartDetailDTOList(Long cartId);
}
