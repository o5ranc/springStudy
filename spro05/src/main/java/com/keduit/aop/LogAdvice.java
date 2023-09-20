package com.keduit.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component
public class LogAdvice {
	
//	execution에서 ..을 붙힌 이유는 매개변수도 포함하겠다는 의미
//  첫 번째 *은 접근제한자를 의미(*은 모두 접근 가능)
//  Before : Join Point가 됨
	@Before("execution(* com.keduit.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("================= 동작 전");
	}
	
//	args(str1, str2)는 logBeforeWithParam의 매개변수
	@Before("execution(* com.keduit.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* com.keduit.service.SampleService*.*(..))", 
			throwing = "exception")
	public void logException(Exception exception) {
		log.info("Exception...!!!");
		log.info(exception);
	}
	
	// @Around 전체 다?
	@Around("execution(* com.keduit.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		log.info("...Target : " + pjp.getTarget());
		log.info("...Param : " + Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		
		try {
			result = pjp.proceed();
		} catch(Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		log.info("...Time : " + (end - start));
		
		return result;
	}
}
