package com.keduit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keduit.domain.BoardVO;
import com.keduit.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service // 서비스로 Container가 관리하겠다는 의미
@Log4j
@RequiredArgsConstructor // 생성자 주입 사용
@ToString
public class BoardServiceImpl implements BoardService {
	private final BoardMapper mapper;

	@Override
	public Long register(BoardVO board) {
		log.info("...BoardServiceImpl(register)..." + board);
		mapper.insertSelectKey(board);
		return board.getBno(); // 등록하면서 bno 리턴
	}

	@Override
	public BoardVO get(Long bno) {
		log.info("...BoardServiceImpl(get)..." + bno);
		BoardVO bVO = mapper.read(bno);
		return bVO;
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("...BoardServiceImpl(modify)..." + board);
		int cnt = mapper.update(board);
		return (cnt == 1) ? true : false;
	}

	@Override
	public boolean remove(Long bno) {
		log.info("...BoardServiceImpl(remove)..." + bno);
		int cnt = mapper.delete(bno);
		return (cnt == 1) ? true : false;
	}

	@Override
	public List<BoardVO> getList() {
		List<BoardVO> boardList;
		log.info("...BoardServiceImpl(getList)...");
		boardList = mapper.getList();
		return boardList;
	}

}
