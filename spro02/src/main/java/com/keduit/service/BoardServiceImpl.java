package com.keduit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keduit.domain.BoardVO;
import com.keduit.domain.Criteria;
import com.keduit.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@RequiredArgsConstructor
@ToString
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;
	
	@Override
	public Long register(BoardVO boardVO) {
		log.info("... register ..." + boardVO);
		mapper.insertSelectKey(boardVO);
		// TODO Auto-generated method stub
		return boardVO.getBno();
	}

	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		BoardVO selectVo = mapper.readBoard(bno);
		return selectVo;
	}

	@Override
	public boolean modify(BoardVO boardVo) {
		// TODO Auto-generated method stub
		int result = mapper.updateBoard(boardVo);
		
		return (result != 0);
	}

	@Override
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		int result = mapper.deleteBoard(bno);
				
		return (result != 0);
		
	}

//	@Override
//	public List<BoardVO> getList() {
//		// TODO Auto-generated method stub
//		
//		return mapper.getList();
//	}
	@Override
	public List<BoardVO> getList(Criteria criteria) {
		// TODO Auto-generated method stub
		
		return mapper.getListWithPaging(criteria);
	}

}
