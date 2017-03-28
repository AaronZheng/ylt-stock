package com.common.ylt.scheduler.processor;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.common.ylt.scheduler.dispatcher.SchedulerBridging;


public interface IAlphaScheduler extends Scheduler{
	

	/**
	 * @Description destroy all services
	 * 
	 * @Time 2015
	 * 
	 * @author zcl
	 */
	 void destroyAllServices();
	
	/**
	 * @Description destroy all services
	 * 
	 * @Time 2015
	 * 
	 * @author zcl
	 */
	 void printAllRegisterInfo();


	/**
	 *
	 */
	void registerSchedulerBridging(SchedulerBridging schedulerBridging);


	void removeAllJob() throws SchedulerException;


}
