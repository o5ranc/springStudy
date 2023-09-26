package com.keduit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {


    @GetMapping("/admin/item/new")
    public String itemForm (){
        return "/item/itemForm";
    }
    @GetMapping("/admin/items")
    public  String items(){
        return "/item/itemsForm";
    }
}
