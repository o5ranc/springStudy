package com.keduit.domain;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private Long bno; // long 아님?
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private Date updatedate;
}
