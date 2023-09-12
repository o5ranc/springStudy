package com.keduit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keduit.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController { 
	// Controller는 Service 호출해서 사용하면 됨, Service(주입)가 꼭 필요함!!	
	private final BoardService service;
	
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list......");
		model.addAttribute("list", service.getList());
	}
}
