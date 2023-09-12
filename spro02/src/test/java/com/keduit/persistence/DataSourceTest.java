package com.keduit.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;

//import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTest {
	
//	// DataSource Setter 주입
//	@Setter(onMethod_ = {@Autowired})
//	private DataSource dataSource;
	
	// bean이 등록되어 있기 때문에 자동 주입이 가능하다!
	@Setter(onMethod_ = @Autowired)
	private SqlSessionFactory sqlSessionFactory;
	
	@Test
	public void testMybatis() {
		try(
			SqlSession session = sqlSessionFactory.openSession();
				Connection conn = session.getConnection();
				) {
			log.info("session = " + session);
			log.info("conn = " + conn);
			
		} catch (Exception e) {
			// TODO: handle exception
			fail(e.getMessage());
		}
	}
}
