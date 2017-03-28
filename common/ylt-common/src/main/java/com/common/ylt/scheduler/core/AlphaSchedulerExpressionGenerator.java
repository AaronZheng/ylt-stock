package com.common.ylt.scheduler.core;

import java.util.Date;
import java.util.Map;

import com.common.ylt.scheduler.exception.SchedulerException;
import com.common.ylt.scheduler.logger.SchedulerLogger;


public class AlphaSchedulerExpressionGenerator {
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @return
	 * 
	 */
	public static CronExpressEntity constructConnExpression(String timeExpression,Integer times,Date vaildTimeStart,Date vaildTimeEnd,String jobName,
			Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws SchedulerException, org.quartz.SchedulerException {

		return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,jobName,args,job,isAllowSameNameRegister);

		// TODO 后期扩展
/*
		switch(policy){
			case AlphaSchedulerPolicy.SECOND_FREQUENCY:{
				return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			case AlphaSchedulerPolicy.MINUTES_FREQUENCY:{
				return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			case AlphaSchedulerPolicy.HOUR_FREQUENCY:{
				return registerCommonCronExpress(timeExpression,times,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			case AlphaSchedulerPolicy.HOUR_DAY:{
				return parseHourOfDayCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			case AlphaSchedulerPolicy.HOUR_DAY_MONTH:{
				return parseHourOfDayMonthCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			case AlphaSchedulerPolicy.HOUR_DAY_MONTH_YEAR:{
				return parseHourOfDayMonthYearCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			case AlphaSchedulerPolicy.DAY_WEEK:{
				return parseWeekCronExpress(timeExpression,vaildTimeStart,vaildTimeEnd,policy,jobName,args,job,isAllowSameNameRegister);
			}
			default:{
				throw new SchedulerException("Invalid frequency type ["+policy+"]");
			}


		}*/
	}
	
	
	private static void validateTimerExpress(String timeExpression,String regex) throws SchedulerException{
	
	}
	
	
	
	/**
	 * 
	 * @param timeExpression
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @throws SchedulerException
	 */
	private static CronExpressEntity parseHourOfDayCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,
															   String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws SchedulerException, org.quartz.SchedulerException {

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setValidTimeStart(vaildTimeStart);
		express.setValidTimeEnd(vaildTimeEnd);
		express.setExecuteExpres(constructCommonCronExpress(timeExpression).append(" * * ?").substring(1));
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		return express;
	}
	
	
	
	
	
	
	
	/**
	 * @param timeExpression
	 *
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @throws SchedulerException
	 */
	private static CronExpressEntity parseHourOfDayMonthCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws SchedulerException, org.quartz.SchedulerException {

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setValidTimeStart(vaildTimeStart);
		express.setValidTimeEnd(vaildTimeEnd);
		express.setExecuteExpres(constructCommonCronExpress(timeExpression).append(" ? *").substring(1));
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		return express;
	}
	
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param vaildTimeStart
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @param policy
	 * 
	 * @throws SchedulerException
	 */
	private static CronExpressEntity parseHourOfDayMonthYearCronExpress(String timeExpression,Date vaildTimeStart,Date vaildTimeEnd,
																		int policy,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws SchedulerException, org.quartz.SchedulerException {

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setValidTimeStart(vaildTimeStart);
		express.setValidTimeEnd(vaildTimeEnd);
		express.setExecuteExpres(constructCommonCronExpress(timeExpression).substring(1));
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		return express;
	
	}
	
	
	/**
	 * 
	 * @param timerExpression
	 * @return
	 */
	private static StringBuilder constructCommonCronExpress(String timerExpression){
		
		String[] allRegex = timerExpression.split(" ");
		String[] time = allRegex[allRegex.length-1].split(":");
		StringBuilder sb = new StringBuilder();
		for(int j = time.length-1;j>=0;j--){
			sb.append(" "+time[j]);
		}
		if(allRegex.length>1){
			String[] month = allRegex[0].split("/");
			for(int j = month.length-1;j>=0;j--){
				if(month.length == 3&&j == 0){
					sb.append(" ?");
				}
				sb.append(" "+month[j]);
			}
		}
		return sb;
	}
	
	
	
	/**
	 * @param timeExpression
	 * 
	 * @param validTimeStart
	 * 
	 * @param validTimeEnd
	 * 
	 * @param policy
	 * @throws SchedulerException
	 */
	private static CronExpressEntity parseWeekCronExpress(String timeExpression,Date validTimeStart,Date validTimeEnd,int policy,String jobName,
														  Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws SchedulerException, org.quartz.SchedulerException {

		validateTimerExpress(timeExpression,"");
		CronExpressEntity express = new CronExpressEntity();
		express.setPolicyType(policy);
		express.setValidTimeStart(validTimeStart);
		express.setValidTimeEnd(validTimeEnd);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		String[] time = timeExpression.split(":");
		StringBuilder sb = new StringBuilder();
			
		for(int j = time.length-1;j>=0;j--){
			sb.append(" "+time);
		}
		sb.append(" ? *");
		express.setExecuteExpres(sb.substring(1));
		return express;
	
	}
	
	
	/**
	 * 
	 * @param timeExpression
	 * 
	 * @param times
	 * 
	 * @param vaildTimeEnd
	 * 
	 * @return
	 * 
	 * @throws SchedulerException
	 */
	private static CronExpressEntity registerCommonCronExpress(String timeExpression,Integer times,
			Date validTimeStart,Date vaildTimeEnd,String jobName,Map<Object,Object> args,Object job,boolean isAllowSameNameRegister) throws SchedulerException, org.quartz.SchedulerException {
		
		if(timeExpression == null||"".equals(timeExpression)){
			SchedulerLogger.getLogger().warn("Don't find scheduler expression, System will ignore current timer register !");
			SchedulerLogger.getLogger().error("Register scheduler task failure !");
			return null;
		}
		
		CronExpressEntity express = new CronExpressEntity();
		express.setExecuteExpres(timeExpression);
		//express.setPolicyType(policy);
		express.setSimpleType(false);
		express.setJob(job);
		express.setJobDataMap(args);
		express.setAllowRegisterSameService(isAllowSameNameRegister);
		if(!isAllowSameNameRegister)
			express.setJobName(jobName);
		
		if(validTimeStart == null)
			validTimeStart = new Date();
		express.setValidTimeStart(validTimeStart);
		if(vaildTimeEnd == null)
			express.setExecuteForever(false);
		else
			express.setValidTimeEnd(vaildTimeEnd);
		
		express.setExecuteTimes(times);
		return express;
		
	}
	
	

}
