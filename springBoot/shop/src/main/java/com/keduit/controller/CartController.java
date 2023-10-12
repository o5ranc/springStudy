package com.keduit.controller;

import com.keduit.dto.CartDetailDTO;
import com.keduit.dto.CartItemDTO;
import com.keduit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDTO cartItemDTO,
                                              BindingResult bindingResult, Principal principal) {

        // 데이터 바인딩 에러 체크
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDTO, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    // cart ui가 있는 html 파일 경로로 던지기
    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model) {
        List<CartDetailDTO> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem
            (@PathVariable("cartItemId") Long cartItemId, int count, Principal principal) {
        // 수정 권한 확인하는 함수

        if(count <= 0) {
            return new ResponseEntity<String>(
                "최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST
            );
        } else if(!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>(
                    "수정 권한이 업습니다.", HttpStatus.FORBIDDEN
            );
        }

        cartService.updateCartItemCount(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
