package com.stackroute.keepnote.aspectj;

import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Annotate this class with @Aspect and @Component */
@Aspect
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of User controller, any particular
	 * method will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(com.stackroute.keepnote.controller.*)")
	public void logBefore(){
		logger.info("@Before:"+new Date());
	}

	@After("execution(com.stackroute.keepnote.controller.*)")
	public void logAfter(){
		logger.info("@After:"+new Date());
	}

	@AfterThrowing(pointcut = "execution(com.stackroute.keepnote.controller.*)",
			throwing="exception")
	public void logAfterThrowing(Exception exception){
		logger.info("@AfterReturning:"+new Date());
		logger.error("\"Exception caught:\"+ exception.getMessage()");
	}

	@AfterReturning(pointcut = "execution(com.stackroute.keepnote.controller.*)",
			returning="val")
	public void logAfterReturning(Object val){

		logger.error("Method return value:"+ val);
		logger.error("@AfterReturning:"+new Date());
	}
}
