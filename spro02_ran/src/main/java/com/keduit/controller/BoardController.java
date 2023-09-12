package com.keduit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.domain.BoardVO;
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
		log.info("...list...");
		model.addAttribute("list", service.getList());
	}
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("...register..." + board);
		long bno = service.register(board);
		log.info("...register_bno..." + bno);
		rttr.addFlashAttribute("result", bno); // 일회용으로 쓰는
		return "redirect:/board/list";
	}
	
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, Model model) {
		// void 타입이긴하나, model에 담아 보내기
		log.info("...get...");
		model.addAttribute("board", service.get(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("...modify..." + board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	@PostMapping("/remove")
	//public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
	public String remove(Long bno, RedirectAttributes rttr) {
		// 전달 받은 bno
		log.info("...remove..." + bno);
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "sucess");
		}
		return "redirect:/board/list";
	}
}
