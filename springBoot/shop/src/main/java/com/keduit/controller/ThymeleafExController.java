package com.keduit.controller;

import com.keduit.dto.ItemDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {
    @GetMapping(value = "/ex01")
    public String thymeleafEx01(Model model) {
        model.addAttribute("data", "타임리프 예제입니다.");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ItemDTO itemDto = new ItemDTO();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping("/ex03")
    public String thymeleafExample03(Model model) {
        List<ItemDTO> itemDtoList = new ArrayList<>();

        for(int i  = 0;i < 10; i++) {
            ItemDTO itemDto = new ItemDTO();
            itemDto.setId((long)i);
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(50000 + i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex04")
    public String thymeleafExample04(Model model) {
        List<ItemDTO> itemDtoList = new ArrayList<>();

        for(int i  = 0;i < 10; i++) {
            ItemDTO itemDto = new ItemDTO();
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(50000 + i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping("/ex05")
    public String thymeleafEx05() {
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping("/ex06")
    public String thymeleafEx06(String param1, String param2, Model model) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping("/ex07")
    public String thymeleafEx07() {
        return "thymeleafEx/thymeleafEx07";
    }

    @GetMapping("/exInline")
    public String exInline(RedirectAttributes redirectAttributes) {
        System.out.println(".....exinline.....");

        ItemDTO itemDto = new ItemDTO();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());


        redirectAttributes.addFlashAttribute("result", "sucess");
        redirectAttributes.addFlashAttribute("dto", itemDto);

        return "redirect:/thymeleaf/ex08";
    }

    @GetMapping("/ex08")
    public String ex08() {
        System.out.println("ex08");
        return "thymeleafEx/thymeleafEx08";
    }
}