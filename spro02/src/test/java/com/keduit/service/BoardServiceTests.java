package com.keduit.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

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
public class BoardServiceTests {
	@Autowired
	private BoardService service;
	
	@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service); // null check - If it is an {@link AssertionError}
	}
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("오오오 제목");
		board.setContent("오오오 내요오오옹");
		board.setWriter("user02");
		
		long rBno = service.register(board);
		
		log.info("...testRegister...");
		log.info("...bno : " + rBno);
	}
	
	@Test
	public void testGetList() {
		List<BoardVO> boardList = service.getList();
		log.info("...testGetList...");
		log.info("...boardList : " + boardList);
	}
	
	@Test
	public void testGet() {
		BoardVO board = service.get(9L);
		log.info("...testGet...");
		log.info("...board : " + board);
	}
	
	@Test
	public void testModify() {
		BoardVO board = new BoardVO();
		board.setTitle("혼자서 서비스 생성하고 제목 수정수정");
		board.setContent("혼자서 서비스 생성하고 내요요오오옹 수정수정");
		board.setWriter("user02");
		board.setBno(9L);
		
		boolean result = service.modify(board);
		
		log.info("...testModify...");
		if(result) {
			log.info("...board modify SUCESS! - " + board);
		} else {
			log.info("...board modify FAIL! - " + board);
		}
	}
	
	@Test
	public void testRemove() {
		boolean result = service.remove(1L);
		
		log.info("...testRemove...");
		if(result) {
			log.info("...board remove SUCESS! - " + 5L);
		} else {
			log.info("...board remove FAIL! - " + 5L);
		}
	}
}
