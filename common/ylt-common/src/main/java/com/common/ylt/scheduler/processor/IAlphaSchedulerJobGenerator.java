package com.common.ylt.scheduler.processor;

import org.quartz.SchedulerException;

public interface IAlphaSchedulerJobGenerator {
	
	
	
	/**
	 * @description Generate alpha scheduler ....
	 * 
	 * @param scheduler
	 * 
	 * @param expression
	 * 
	 * @throws Throwable
	 * 
	 * @author zcl
	 */
	boolean generateAlphaScheduler(IAlphaScheduler scheduler, Object expression)throws SchedulerException;
	

}
