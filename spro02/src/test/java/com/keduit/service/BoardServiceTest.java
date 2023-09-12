package com.keduit.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keduit.domain.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {
	
	@Autowired
	private BoardService service;
	
	@Test
	public void testExist() {
		log.info("service = " + service);
		assertNotNull(service);
	}
	
	@Test
	public void testRegister() {
		BoardVO boardVO = new BoardVO();
		boardVO.setTitle("테스트");
		boardVO.setContent("테스트 내용임");
		boardVO.setWriter("테스터");
		
		long bLong = service.register(boardVO);
		
		log.info("등록 번호는? " + bLong);
	}
	
	@Test
	public void testGet() {
		service.get(32L);
	}
	
	@Test
	public void testGetList() {
		service.getList();
		
	}
	
	@Test
	public void testModify() {
		BoardVO bVo = new BoardVO();
		bVo.setBno(34L);
		bVo.setTitle("내용 변경");
		bVo.setContent("내용 변경임");
		bVo.setWriter("찐이다");
		
		boolean result =service.modify(bVo);
		
		log.info("result = " + result);
		service.getList();
	}
	
	@Test
	public void testDelete() {
		boolean result = service.remove(32L);
		
		log.info("result = " + result);
	}
	
}
