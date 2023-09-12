package com.keduit.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.keduit.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

/*
 * MockMvc 를 사용하기 위해서는,
 * @WebAppConfiguration, servlet-context.xml을 @ContextConfiguration 추가로 넣어줘야함
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ 
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
@Log4j
public class BoardControllerTest {
	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;

	// request를 만들어서 보내주는 역활을 함
	private MockMvc mockMvc;

	@Before
	public void setup() {
		// webAppContextSetup을 이용해 mockMvc을 가져오는 함수
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void testList() throws Exception {
		// MockMvc는 Exception 처리가 필요함!!!
		// 주소표시줄에서의 호출된 효과
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
				.andReturn().getModelAndView().getModelMap());
	}

	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/register")
						.param("title", "mockvnc test title")
						.param("content", "mockvnc test content")
						.param("writer", "test11"))
				.andReturn().getModelAndView().getViewName();
		log.info("...resultPage..." + resultPage);
	}
	
	@Test
	public void testGet() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "20"))
				.andReturn().getModelAndView().getModelMap());
	}
	
	@Test
	public void testModify() throws Exception {
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/modify")
						.param("bno", "20")
						.param("title", "수정된 글제목")
						.param("content", "수정된 글내용")
						.param("writer", "tester22"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
	}
	
	@Test
	public void testRemove() throws Exception {
		String resultPage = mockMvc
				.perform(MockMvcRequestBuilders.post("/board/remove")
						.param("bno", "20"))
				.andReturn().getModelAndView().getViewName();
		log.info(resultPage);
 
	}
}
