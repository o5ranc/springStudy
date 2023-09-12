package com.keduit.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keduit.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTest {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	@Test
	public void testGetList() {
		boardMapper.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("게시글6");
		board.setContent("게시글 내용6");
		board.setWriter("그녀석");
		
		boardMapper.insertBoard(board);
	}
	
	@Test
	public void testInsertSelect() {
		BoardVO board = new BoardVO();
		board.setTitle("게시글7");
		board.setContent("게시글 내용7");
		board.setWriter("이녀석");
		
		boardMapper.insertSelectKey(board);		
	}
	
	@Test
	public void testRead() {
		BoardVO board = boardMapper.readBoard(26L);
		log.info("board = " + board);
	}
	
	@Test
	public void testDelete() {
		int num = boardMapper.deleteBoard(30L);
		log.info("delete 개수 = " + num);
	}
	
	@Test
	public void updateBoard() {
		BoardVO board = new BoardVO();
		board.setBno(31L);
		board.setTitle("게시글22222");
		board.setContent("게시글 내용2222");
		board.setWriter("자자자");
		int num =boardMapper.updateBoard(board);
		log.info("update 개수 = " + num);
		boardMapper.getList();
	}
}
