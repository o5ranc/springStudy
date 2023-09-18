package com.keduit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keduit.domain.Criteria;
import com.keduit.domain.ReplyVO;
import com.keduit.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

@Service
@ToString
@RequiredArgsConstructor
@Log4j
public class ReplyService_ran implements ReplyServiceImpl_ran {

	private final ReplyMapper mapper;
	
	@Override
	public Long createReply(ReplyVO replyVO) {
		log.info("...createReply..." + replyVO);
		mapper.insert(replyVO);
		return replyVO.getRno();
	}

	@Override
	public List<ReplyVO> getListWithPaging(Criteria cri, Long bno) {
		log.info("...getReplyList..." + cri);
		return mapper.getListWithPaging(cri, null);
	}

	@Override
	public boolean deleteReply(Long bno) {
		log.info("...deleteReply..." + bno);
		return (mapper.delete(bno) > 0);
	}

	@Override
	public ReplyVO getReplyOne(Long bno) {
		log.info("...getReplyOne..." + bno);
		return mapper.read(bno);
	}

	@Override
	public boolean updateReply(ReplyVO replyVO) {
		log.info("...updateReply..." + replyVO);
		return (mapper.update(replyVO) > 0);
	}
}
