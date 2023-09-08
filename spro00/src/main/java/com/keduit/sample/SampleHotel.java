package com.keduit.sample;

import org.springframework.stereotype.Component;

import lombok.*;

@Component
@ToString
@Getter
@RequiredArgsConstructor
public class SampleHotel {

	private final Chef chef;
	
}
