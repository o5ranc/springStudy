package com.keduit.service;

import java.util.List;

import com.keduit.domain.BoardVO;
import com.keduit.domain.Criteria;

public interface BoardService {
	public Long register(BoardVO boardVO);
	
	public BoardVO get(Long bno);
	
	public boolean modify(BoardVO boardVo);
	
	public boolean remove(Long bno);
	
//	public List<BoardVO> getList();
	public List<BoardVO> getList(Criteria criteria);
}
