package com.keduit.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleServiceImplTest {
	@Autowired
	private SampleService service;
	@Autowired
	private SampleTxService txService;

	@Test
	public void testClass() {
		log.info(service);
		log.info(service.getClass().getName());
	}
	
	@Test
	public void testAdd() throws Exception {
		log.info(service.doAdd("567", "500"));
	}
	
	@Test
	public void testException() throws Exception {
		log.info(service.doAdd("123", "abc"));
	}
	
	@Test
	public void testLong() {
		String str="Lorem ipsum dolor sit amet, consectetur adipiscing "
                + "elit, sed do eiusmod tempor incididunt ut labore et "
                + "dolore magna aliqua. Ut enim ad minim veniam, quis ";
		
		log.info(str.getBytes().length);
		txService.addData(str);
	}
}
