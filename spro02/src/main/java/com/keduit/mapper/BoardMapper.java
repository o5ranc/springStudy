package com.keduit.mapper;

import java.util.List;

//import org.apache.ibatis.annotations.Select;

import com.keduit.domain.BoardVO;
import com.keduit.domain.Criteria;

public interface BoardMapper {
	
//	@Select("select * from board where bno > 0")
	public List<BoardVO> getList();
	
	public void insertBoard(BoardVO boardVO);
	public void insertSelectKey(BoardVO boardVO);
	
	public BoardVO readBoard(Long bno);
	public int deleteBoard(Long bno);
	public int updateBoard(BoardVO board);

	public List<BoardVO> getListWithPaging(Criteria cri);

}
