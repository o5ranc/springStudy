package com.keduit.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keduit.domain.Criteria;
import com.keduit.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	//private Long[] bnoArr = new Long()[111L, 222L, 333L, 444L, 555L];

	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testMApper() {
		log.info("-----testMApper-----" + mapper);
	}
	
	@Test
	public void testCreate() {
		IntStream.rangeClosed(30, 40).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			vo.setBno(Long.valueOf(i));
			vo.setReply("댓글 테스트" + i);
			vo.setReplyer("writer" + i);
			
			log.info("======vo " + vo);
			mapper.insert(vo);
		});
	}
	
	@Test
	public void testReadOne() {
		Long rno = 1L;
		ReplyVO vo = mapper.read(rno);
		
		log.info("======vo : " + vo);
	}
	
	@Test
	public void testDelete() {
		Long rno = 2L;
		//mapper.delete(rno);
		log.info("======delete!!!" + mapper.delete(rno));
	}
	
	@Test
	public void testUpdate() {
		ReplyVO vo = new ReplyVO();
		vo.setRno(2L);
		vo.setReply("댓글 수정 테스트111");
		//vo.setReplyer("writer1"); // 작성자는 수정 안함
		
		log.info("======vo " + vo);
		mapper.update(vo);
	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 31L);
		replies.forEach(reply -> log.info(reply));
	}
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1, 10);
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 125L);
		replies.forEach(reply -> log.info(reply));
	}
}
