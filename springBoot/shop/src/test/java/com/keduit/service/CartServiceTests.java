package com.keduit.service;

import com.keduit.constant.ItemSellStatus;
import com.keduit.dto.CartItemDTO;
import com.keduit.entity.CartItem;
import com.keduit.entity.Item;
import com.keduit.entity.Member;
import com.keduit.repository.CartItemRepository;
import com.keduit.repository.ItemRepository;
import com.keduit.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class CartServiceTests {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    public Item saveItem() {
        Item item = new Item();
        item.setItemNm("test");
        item.setItemDetail("test detail");
        item.setPrice(3000);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public Member saveMember() {
        // 현재 이메일 정보만 필요
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart() {
        Item item = saveItem();
        Member member = saveMember();

        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setCount(5);
        cartItemDTO.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDTO, member.getEmail());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDTO.getCount(), cartItem.getCount());
    }
}
