package com.keduit.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	// 페이징 처리 관련 변수 선언
	private int pageNum;
	private int amount;
	
	// 검색관련 변수 선언
	// T, TC, TWC, CW
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1,10);
	}

	public Criteria(int pageNum, int amount) {
		// TODO Auto-generated constructor stub
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	public String[] getTypeArr() {
		
		return type == null? new String[]{}: type.split("");
	}
}
