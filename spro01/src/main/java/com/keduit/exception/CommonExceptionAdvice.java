package com.keduit.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		log.error("Exception........" + ex.getMessage());
		model.addAttribute("exception", ex); // Exception ex 객체를 들고 감
		log.error(model);
		return "error_page"; // 에러 페이지를 리턴하면서 Exception ex 객체를 들고 감
	}
}
