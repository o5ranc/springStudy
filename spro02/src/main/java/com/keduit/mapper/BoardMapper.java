package com.keduit.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.keduit.domain.BoardVO;

public interface BoardMapper {

	// 	@Select("select * from board where bno > 0")
	// mapper 연결을 하고나면, 위 부분을 주석해야함
	public List<BoardVO> getList();
	public void insert(BoardVO board);
	public void insertSelectKey(BoardVO board);
	public BoardVO read(Long bno);
	public int delete(Long bno);
	public int update(BoardVO board);
}
