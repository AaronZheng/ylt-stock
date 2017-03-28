package com.common.ylt.scheduler.core;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.processor.IAlphaJobTrigger;
import com.common.ylt.scheduler.processor.IAlphaScheduler;
import com.common.ylt.scheduler.processor.IAlphaSchedulerJobGenerator;


public class AlphaSchedulerJobGenerator implements IAlphaSchedulerJobGenerator {

	@Override
	public boolean generateAlphaScheduler(IAlphaScheduler scheduler, Object express) throws SchedulerException {
		try{
			CronExpressEntity expression =  (CronExpressEntity) express;
			JobKey jobkey = null;
			if(expression.getJobName() != null){
				jobkey = new JobKey(expression.getJobName(),expression.getJobName());
				if(scheduler.checkExists(jobkey)&&!expression.isAllowRegisterSameService()){
					SchedulerLogger.getLogger().warn("The jobName ["+expression.getJobName()+"] already exist , Don't allow to register same jobName in the same scheduler instance !");
					return false;
				}
			}else{
				expression.setJobName(UUID.randomUUID().toString().replace("-", ""));
			}
			JobDetail jobDetail = null;
			if(expression.getJobDataMap() == null)
				jobDetail = JobBuilder.newJob(expression.getJob()).withIdentity(expression.getJobName(),expression.getServiceName()==null?
						UUID.randomUUID().toString().replace("-", "") : expression.getServiceName()).build();
			else
				jobDetail = JobBuilder.newJob(expression.getJob()).setJobData(new JobDataMap(expression.getJobDataMap())).withIdentity(expression.getJobName(),
						expression.isAllowRegisterSameService()?UUID.randomUUID().toString().replace("-", ""):expression.getJobName()).build();
			TimeUnit timeUnit = getTimeUnit(expression);
			scheduler.scheduleJob(jobDetail, AlphaSchedulerTriggerFactory.getAlphaJobTrigger().getInstance(expression.isSimpleType()+"", IAlphaJobTrigger.class).
					startAt(expression.getValidTimeStart()).endAt(expression.getValidTimeEnd()).setJobKey(expression.getJobName(),
							expression.isAllowRegisterSameService()?UUID.randomUUID().toString().replace("-", ""):expression.getJobName()).
							withSchedule(expression.getExecuteExpres(), timeUnit, expression.getExecuteTimes()));
			return true;
		}catch(Throwable ex){
			throw new SchedulerException("Register alpha scheduler job failure !",ex);
		}
	}
	
	

	/**
	 * @param expression
	 * 
	 * @return
	 */
	private TimeUnit getTimeUnit(CronExpressEntity expression){
		
		switch(expression.getPolicyType()){
		case AlphaSchedulerPolicy.SECOND_FREQUENCY :{
			return TimeUnit.SECONDS;
		}
		case AlphaSchedulerPolicy.MINUTES_FREQUENCY :{
			return TimeUnit.MINUTES;
		}
		case AlphaSchedulerPolicy.HOUR_FREQUENCY :{
			return TimeUnit.HOURS;
		}
		default:{
			return null;
		}
		}
	}
	

}
