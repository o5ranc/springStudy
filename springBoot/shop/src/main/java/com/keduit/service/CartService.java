package com.keduit.service;

import com.keduit.dto.CartItemDTO;
import com.keduit.entity.Cart;
import com.keduit.entity.CartItem;
import com.keduit.entity.Item;
import com.keduit.entity.Member;
import com.keduit.repository.CartItemRepository;
import com.keduit.repository.CartRepository;
import com.keduit.repository.ItemRepository;
import com.keduit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDTO cartItemDTO, String email) {
        // item 정보 조회하고
        Item item = itemRepository.findById(cartItemDTO.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        // member 정보 조회하고
        Member member = memberRepository.findByEmail(email);

        // Cart 정보 조회
        Cart cart = cartRepository.findByMemberId(member.getId());

        if(cart == null) { // null 이므로 만들어서 save 하고,
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem =
                cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(savedCartItem != null) { // 저장된 정보가 있다면 count 더함
            savedCartItem.addCount(cartItemDTO.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem =
                    CartItem.createCartItem(cart, item, cartItemDTO.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
