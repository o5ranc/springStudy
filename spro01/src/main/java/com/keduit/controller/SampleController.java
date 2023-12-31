package com.keduit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.keduit.domain.SampleDTOList;
import com.keduit.domain.SampleDTO;
import com.keduit.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

	// SampleController 에서 InitBinder 에서 포맷 설정하거나 여기서 하거나 둘 중 하나
	// @DateTimeFormat(pattern="yyy/mm/dd")
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		binder.registerCustomEditor(java.util.Date.class,
				new CustomDateEditor(dateFormat, false));
	}
	
    @RequestMapping("")
    public void basic() {
        log.info("basic.......");
    }

//    @GetMapping("/basicGet")
    @RequestMapping(value = "/basicGet", method = {RequestMethod.GET, RequestMethod.POST})
    public void basicGet() {
        log.info("basic post..........");
    } 

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto) {
        log.info("ex01....." + dto);
        return "ex012345";
    }

    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") String age) {
        log.info("name = " + name);
        log.info("age = " + age);

        return "ex02";
    }

    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
        log.info(".............ids : " + ids);

        return "ex02List";
    }

    @GetMapping("/ex02Array")
    public void ex02Array(@RequestParam("ids") String[] ids) {
        log.info("array ids : " + Arrays.toString(ids));
    }
    
    // %5B = '[', %5D = ']' 
    @GetMapping("/ex02Bean")
    public void ex02Bean(SampleDTOList list) {
        log.info("..... list dtos : " + list);
    }
    
    @PostMapping("/ex03")
    public void ex03(TodoDTO todo) {
    	log.info("..... todo : " + todo);
    }
    
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
    	log.info("..... ex04 - dto : " + dto);
    	log.info("..... ex04 - page : " + page);
    	return "/sample/ex04";
    }
    
    // 페이지 에러 추가 처리
    @GetMapping("/error/{statusCode}")
    public String getErrorPage(@PathVariable String StatusCode) {
    	return "custom404";
    }
    
    // 바로 아래의 re1 통한 re2 접근과 아래의 re2 접근의 방식이 다름
    @GetMapping("/re1")
    public String rel() {
    	log.info("......re1......");
    	return "redirect:/sample/re2";
    }
    
    @GetMapping("/re2")
    public void re2() {
    	log.info("......re2......");
    }
    
    // Json으로 처리하기위한 라이브러리 추가
    // Jackson Databind » 2.14.2
    // ex06 웹에서 접속하면 json 형태로 보여줌
    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06() {
    	log.info("/ex06.......");
    	SampleDTO dto = new SampleDTO();
    	dto.setAge(25);
    	dto.setName("홍길동");
		return dto;
    }
    
    @GetMapping("/ex07")
    public ResponseEntity<String> ex07() {
    	log.info("/ex07......");
    	String msg = "{\"name\" : \"홍길동\"}";
    	// import org.springframework.http.HttpHeaders; 필요
    	HttpHeaders header = new HttpHeaders();
    	header.add("Content-type", "application/json;charset=UTF-8");
    	//return new ResponseEntity<String>(msg, header, HttpStatus.OK); String 타입 생략 가능
    	return new ResponseEntity<>(msg, header, HttpStatus.OK);
    }
    
    @GetMapping("/exUpload")
    public void exUpload() {
    	log.info("..... /exUpload......");
    }
    
    @PostMapping
    public void exUploadPost(ArrayList<MultipartFile> files) {
    	files.forEach(file -> {
    		log.info("-------------------------------");
    		log.info("name : " + file.getOriginalFilename());
    		log.info("size : " + file.getSize());
    		log.info("contentType : " + file.getContentType());
    	});
    }
}
