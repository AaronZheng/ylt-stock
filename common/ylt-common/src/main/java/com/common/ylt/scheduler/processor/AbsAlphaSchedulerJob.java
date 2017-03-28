package com.common.ylt.scheduler.processor;

import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.common.ylt.scheduler.util.Constant;


public abstract class AbsAlphaSchedulerJob implements Job{

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
	
		context.getJobDetail().getJobDataMap().put(Constant.JOB_NAME, context.getJobDetail().getKey().getName());
		context.getJobDetail().getJobDataMap().put(Constant.JOB_GROUP, context.getJobDetail().getKey().getGroup());
		processor(context.getJobDetail().getJobDataMap());

	}
	
	
	/**
	 * @description the processor
	 * 
	 * @param args
	 */
	public abstract void processor(Map<?,?> args);

}
