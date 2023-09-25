package com.keduit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/") // root로 접근시의 처리를 위해
    public String main() {
        return "main";
    }
}
