package com.keduit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.domain.BoardVO;
import com.keduit.domain.Criteria;
import com.keduit.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;

	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list.......");
		model.addAttribute("list", service.getList(cri));
	}

	@PostMapping("/register")
	public String register(BoardVO bvo, RedirectAttributes rttr) {
		log.info("register" + bvo);
		long bno = service.register(bvo);
		log.info("bno = " + bno);
		rttr.addFlashAttribute("result", bno);

		return "redirect:/board/list";
	}

	@GetMapping("/register")
	public void register() {
	}

	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("get...........");
		model.addAttribute("board", service.get(bno));
	}

	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("...modify : " + board);
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}

		return "redirect:/board/list";
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("...remove..." + bno);

		if (service.remove(bno)) {
			rttr.addAttribute("result", "success");
		}

		return "redirect:/board/list";

	}
}
