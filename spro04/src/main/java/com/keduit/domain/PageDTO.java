package com.keduit.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private int startPage;
	private int endPage;
	private boolean prev, next;
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		// endPage 계산하기(ceil - 올림계산)
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0)) * 10;
		
		// startPage 계산하기
		this.startPage = this.endPage - 9;
		
		// 계산상 endPage 번호와 
		int realEnd = (int)(Math.ceil((total/1.0)/cri.getAmount()));
		
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
