package com.keduit.controller;

import com.keduit.dto.ItemFormDTO;
import com.keduit.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    @GetMapping("/admin/item/new")
    public String itemForm (Model model){
        model.addAttribute("itemFormDTO", new ItemFormDTO());
        return "/item/itemForm";
    }
    @GetMapping("/admin/items")
    public  String items(){
        return "/item/itemsForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDTO itemFormDTO,
                          BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
        // Valid한 값들을 가진 bindingResult 결과를 가져옴
        if(bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDTO.getId() == null) {
            model.addAttribute("errMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDTO, itemImgFileList);
        } catch(Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping("/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            // 못 읽은 경우에 대한 예외처리 추가함
            ItemFormDTO itemFormDTO = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDTO", itemFormDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품입니다.");
        }

        return "item/itemForm";
    }
}
