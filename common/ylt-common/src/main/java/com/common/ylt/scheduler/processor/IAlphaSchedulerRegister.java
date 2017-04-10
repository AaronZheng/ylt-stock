package com.common.ylt.scheduler.processor;

import java.util.Date;
import java.util.Map;
import org.quartz.SchedulerException;


public interface IAlphaSchedulerRegister {
	
	
	/**
	 * @param serviceName 调用服务名称
	 * 
	 * @param timerExpression 定时任务表达式
	 * 
	 * @param times 执行次数
	 * 
	 * @param startAt 任务有效期起
	 * 
	 * @param endAt 任务有效期至
	 * 
	 * @param policy 策略
	 * 
	 * @param jobName 定时任务名称
	 * 
	 * @param args 定时任务参数
	 * 
	 * @throws SchedulerException
	 * @throws com.common.ylt.scheduler.exception.SchedulerException 
	 */
	boolean registerSchedulerByBeanName(String serviceName, String timerExpression, Integer times,
										   Date startAt, Date endAt, String jobName, Map<Object, Object> args) throws SchedulerException, com.common.ylt.scheduler.exception.SchedulerException;
	
	
	/**
	 * @param job 定时任务处理类 需要实现粗象类 AbsAlphaSchedulerJob
	 * 
	 * @param timerExpression 定时任务表达式
	 * 
	 * @param times 定时任务执行次数
	 * 
	 * @param startAt 定时任务有效期起
	 * 
	 * @param endAt 定时任务有效期至
	 * 
	 * @param jobName 定时任务名称
	 * 
	 * @param args 定时任务执行参数
	 * 
	 * @return
	 * 
	 * @throws SchedulerException
	 * 
	 * @throws SchedulerException
	 * @throws com.common.ylt.scheduler.exception.SchedulerException 
	 */
	boolean registerSchedulerBySchedulerJob(AbsAlphaSchedulerJob job, String timerExpression, Integer times,
											Date startAt, Date endAt, String jobName, Map<Object, Object> args) throws SchedulerException, SchedulerException, com.common.ylt.scheduler.exception.SchedulerException;



	/**
	 * @param serviceName 定时任务调用服务名称
	 *
	 * @param timerExpression 定时任务执行时间表达式
	 *
	 * @param times 定时任务执行次数
	 *
	 * @param startAt 定时任务有效期起
	 *
	 * @param endAt 定时任务有效期至
	 *
	 * @param jobName 定时任务名称
	 *
	 * @param args 定时任务执行参数
	 *
	 * @param isAllowSameNameRegister 是否允许注册同名服务
	 *
	 * @return
	 *
	 * @throws SchedulerException
	 *
	 * @throws SchedulerException
	 * @throws com.common.ylt.scheduler.exception.SchedulerException 
	 */
	 boolean registerSchedulerByBeanName(String serviceName, String timerExpression, Integer times,
												  Date startAt, Date endAt, String jobName, Map<Object, Object> args, boolean isAllowSameNameRegister) throws  SchedulerException,SchedulerException, com.common.ylt.scheduler.exception.SchedulerException;




	/**
	 * @param jobName
	 * 
	 * @throws SchedulerException
	 */
	void pauseJob(String jobName)throws SchedulerException;

	/**
	 * 恢复任务
	 * @param jobName
	 * @throws SchedulerException
     */
	void resumeJob(String jobName)throws SchedulerException;
	
	
	/**
	 * 
	 * @param jobName
	 * 
	 * @throws SchedulerException
	 */
	void removeJob(String jobName,boolean synState)throws SchedulerException;


	void removeAllJob() throws SchedulerException;
	

}
