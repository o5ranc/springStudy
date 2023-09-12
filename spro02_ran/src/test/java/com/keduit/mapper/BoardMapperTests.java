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
public class BoardMapperTests {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Test
	public void testGetList() {
		mapper.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("insert test 코드를 통한 입력");
		board.setContent("insert test 코드를 통한 입력 내용임");
		board.setWriter("newbie");
		mapper.insert(board);
		log.info(board);
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("select key를 활용한 입력");
		board.setContent("select key 하고 insert");
		board.setWriter("user01");
		mapper.insertSelectKey(board);
		
		log.info("......번호 : " + board.getBno());
		log.info(board);
	}
	
	@Test
	public void testRead() {
		BoardVO board = mapper.read(3L);
		log.info(board);
	}
	
	@Test
	public void testDelete() {
		int result = mapper.delete(4L);
		log.info("delete......");
		log.info("deleted 갯수 : " + result);
	}
	
	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		board.setTitle("update를 활용한 수정 입력");
		board.setContent("update를 활용한 내용 내용 수정 입력");
		board.setWriter("user03");
		board.setBno(2L); // where 해당되는 테스트 요기서 직접 줌
		
		int result = mapper.update(board);
		log.info("update......");
		log.info("updated 갯수 : " + result);
	}
}
