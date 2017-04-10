package com.common.ylt.scheduler.core;

import java.util.Date;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.common.ylt.scheduler.dispatcher.SchedulerTaskRegister;
import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.SchedulerConf;
import com.common.ylt.scheduler.processor.AbsAlphaSchedulerJob;
import com.common.ylt.scheduler.processor.IAlphaScheduler;
import com.common.ylt.scheduler.processor.IAlphaSchedulerJobGenerator;
import com.common.ylt.scheduler.processor.IAlphaSchedulerRegister;


public class AlphaSchedulerRegister implements IAlphaSchedulerRegister {
	
	
	private static IAlphaSchedulerRegister register;
	
	private IAlphaSchedulerJobGenerator generator;
	
	private IAlphaScheduler scheduler;

	
	

	@Override
	public boolean registerSchedulerByBeanName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,String jobName,Map<Object,Object> args) throws SchedulerException, com.common.ylt.scheduler.exception.SchedulerException {

		try {

			if (scheduler == null)
				throw new SchedulerException("The alpha scheduler component haven bean not started , can't register scheduler job !");

			boolean state =  generator.generateAlphaScheduler(scheduler, AlphaSchedulerExpressionGenerator.constructConnExpression(timerExpression, times, startAt, endAt, jobName, args, serviceName, true));
			noticeRegisterState(jobName,2,serviceName,true,"success");
			return state;
		}catch (Exception e){
			SchedulerLogger.getLogger().error("Register task failed beanName:{},expression:{},jobName:{}",serviceName,timerExpression,jobName,e);
			noticeRegisterState(jobName,2,serviceName,false,e == null ? null : e.getMessage());
			return false;
		}
	}

	@Override
	public boolean registerSchedulerBySchedulerJob(AbsAlphaSchedulerJob job, String timerExpression, Integer times,
												   Date startAt, Date endAt, String jobName, Map<Object,Object> args) throws SchedulerException, SchedulerException, com.common.ylt.scheduler.exception.SchedulerException{
		try {
			if (scheduler == null)
				throw new SchedulerException("The alpha scheduler component haven been not started , can't register scheduler job !");

			boolean state = generator.generateAlphaScheduler(scheduler, AlphaSchedulerExpressionGenerator.constructConnExpression(timerExpression, times, startAt, endAt, jobName, args, job, true));
			noticeRegisterState(jobName,2,"custom",true,"success");

			return state;
		}catch (Exception e){
			SchedulerLogger.getLogger().error("Register task failed beanName:{},expression:{},jobName:{}","custom",timerExpression,jobName,e);
			noticeRegisterState(jobName,2,"custom",false,e == null ? null : e.getMessage());
			return false;
		}
	}
	

	/**
	 * @return
	 */
	public synchronized static IAlphaSchedulerRegister getDefaultInstance(){
		
		if(register == null)
			register = new AlphaSchedulerRegister();
		
		return register;
	}
	

	@Override
	public boolean registerSchedulerByBeanName(String serviceName,String timerExpression,Integer times,
			Date startAt,Date endAt,String jobName,Map<Object,Object> args,boolean isAllowSameNameRegister) throws SchedulerException, com.common.ylt.scheduler.exception.SchedulerException {

		try {
			if (scheduler == null)
				throw new SchedulerException("The alpha scheduler component haven bean not started , can't register scheduler job !");

			boolean state = generator.generateAlphaScheduler(scheduler, AlphaSchedulerExpressionGenerator.constructConnExpression(timerExpression, times, startAt, endAt, jobName, args, serviceName, isAllowSameNameRegister));
			noticeRegisterState(jobName,2,serviceName,true,"success");
			return state;
		}catch (Exception e){
			SchedulerLogger.getLogger().error("Register task failed beanName:{},expression:{},jobName:{}","custom",timerExpression,jobName,e);
			noticeRegisterState(jobName,2,serviceName,false,e == null ? null : e.getMessage());
			return false;
		}
	}

	
	
	/**
	 * @description register application context information ....
	 * 
	 * @param scheduler
	 * 
	 * @param generator
	 */
	public void registerContext(IAlphaScheduler scheduler,IAlphaSchedulerJobGenerator generator){
		this.scheduler = scheduler;
		this.generator = generator;
	}


	@Override
	public void pauseJob(String jobName) throws SchedulerException {
		
		if(scheduler == null)
			throw new SchedulerException("The alpha scheduler component haven't started , can't register scheduler job !");
	
		try {
			scheduler.pauseJob(new JobKey(jobName,jobName));
			noticeRegisterState(jobName,3,null,true,"success");
		} catch (SchedulerException e) {
			SchedulerLogger.getLogger().error("pauseJob failed jobName:{}",jobName,e);
			noticeRegisterState(jobName,3,null,false,e == null ? null : e.getMessage());
			throw new SchedulerException("PauseJob scheduler job failure !",e);
		}
	}

	@Override
	public void resumeJob(String jobName) throws SchedulerException {

		if(scheduler == null)
			throw new SchedulerException("The alpha scheduler component haven't started , can't register scheduler job !");

		try {
			scheduler.resumeJob(new JobKey(jobName,jobName));
			noticeRegisterState(jobName,5,null,true,"success");
		} catch (SchedulerException e) {
			SchedulerLogger.getLogger().error("resumeJob failed jobName:{}",jobName,e);
			noticeRegisterState(jobName,5,null,false,e == null ? null : e.getMessage());
			throw new SchedulerException("PauseJob scheduler job failure !",e);
		}

	}


	@Override
	public void removeJob(String jobName,boolean synState) throws SchedulerException {
		
		try {
			scheduler.deleteJob(new JobKey(jobName,jobName));
			if(synState)
				noticeRegisterState(jobName,4,null,true,"success");
		} catch (SchedulerException e) {
			SchedulerLogger.getLogger().error("removeJob jobName:{}",jobName,e);
			if(synState)
				noticeRegisterState(jobName,4,null,false,e == null ? null : e.getMessage());
			throw new SchedulerException("Remove scheduler job failure !",e);
		}
	}

	@Override
	public void removeAllJob() throws SchedulerException {
		scheduler.removeAllJob();
	}


	private void noticeRegisterState(String taskId,Integer taskState,String beanName,boolean isSuccess,String msg){

		try {
			if (SchedulerConf.getSchedulerConfInstance().getSchedulerRegisterResult() == null)
				return;
			SchedulerTaskRegister schedulerTaskRegister = new SchedulerTaskRegister();
			schedulerTaskRegister.setTaskId(taskId);
			schedulerTaskRegister.setTaskState(taskState);
			schedulerTaskRegister.setRegisterState(isSuccess);
			schedulerTaskRegister.setRegisterTime(new Date());
			schedulerTaskRegister.setBeanName(beanName);
			schedulerTaskRegister.setMsg(msg);
			SchedulerConf.getSchedulerConfInstance().getSchedulerRegisterResult().noticeTaskRegisterState(schedulerTaskRegister);
		}catch (Exception e){
			SchedulerLogger.getLogger().error("Notice task execute state failed",e);
		}

	}


}
