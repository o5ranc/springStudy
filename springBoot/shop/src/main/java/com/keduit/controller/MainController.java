package com.keduit.controller;

import com.keduit.dto.ItemSearchDTO;
import com.keduit.dto.MainItemDTO;
import com.keduit.dto.MainItemDTO;
import com.keduit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ItemService itemService;



    @GetMapping("/")
    public String main(ItemSearchDTO itemSearchDTO, Optional<Integer> page, Model model){
        //매개변수가 존재하면 해당 페이지 번호를 가져오고, 그렇지 않으면 0으로 설정
        Pageable pageable = PageRequest.of(page.isPresent()? page.get() : 0, 6);
        //데이터 조회
        Page<MainItemDTO> items = itemService.getMainItemPage(itemSearchDTO,pageable);
        //모델에 데이터 추가
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDTO);
        model.addAttribute("maxPage", 5);
        return "main";
    }
}
