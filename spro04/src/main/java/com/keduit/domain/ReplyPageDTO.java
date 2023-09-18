package com.keduit.domain;

import java.util.List;

import lombok.Data;

@Data
public class ReplyPageDTO {

	private int replyCnt;
	private List<ReplyVO> list;
	
	public ReplyPageDTO(int countByBno, List<ReplyVO> listWithPaging) {
		this.replyCnt =countByBno;
		this.list = listWithPaging;
	}

}