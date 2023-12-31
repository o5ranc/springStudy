package com.keduit.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoDTO {
	private String title;
	
	// SampleController 에서 InitBinder 에서 포맷 설정하거나 여기서 하거나 둘 중 하나
	// @DateTimeFormat(pattern="yyy/mm/dd")
	private Date dueDate;
}
