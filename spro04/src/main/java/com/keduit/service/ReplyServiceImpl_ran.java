package com.keduit.service;

import java.util.List;

import com.keduit.domain.Criteria;
import com.keduit.domain.ReplyVO;

public interface ReplyServiceImpl_ran {
	// 덧글 등록
	public Long createReply(ReplyVO rvo);
	// 덧글 목록
	List<ReplyVO> getListWithPaging(Criteria cri, Long bno);
	// 덧글 하나만
	public ReplyVO getReplyOne(Long bno);
	// 덧글 삭제
	public boolean deleteReply(Long bno);
	// 덧글 수정
	public boolean updateReply(ReplyVO ReplyVO);
}
