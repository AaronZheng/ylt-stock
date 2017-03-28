package com.common.ylt.scheduler.core;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.quartz.SchedulerException;

import com.common.ylt.scheduler.processor.AbsAlphaSchedulerJob;
import com.common.ylt.scheduler.util.Constant;


public class CronExpressEntity implements Serializable{

	private static final long serialVersionUID = -4898185866251004276L;
	
	private int policyType;
	
	private boolean isSimpleType;
	
	private String executeExpres;
	
	private Date validTimeStart; 
	
	private Date validTimeEnd;
	
	private Integer executeTimes;
	
	private String jobName;
	
	private Map<Object,Object> jobDataMap;
	
	private boolean isExecuteForever;
	
	private String serviceName;
	
	private Class<? extends AbsAlphaSchedulerJob> job;
	
	private boolean isDefaultJob;
	
	private boolean allowRegisterSameService;
	
	
	/**
	 * 
	 * @param job
	 * 
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public void setJob(Object job) throws SchedulerException{
		if(job instanceof Class){
			this.job =  (Class<? extends AbsAlphaSchedulerJob>) job;
			isDefaultJob = true;
		}else if(job instanceof String){
			if(jobDataMap == null)
				jobDataMap = new HashMap<Object,Object>();
			jobDataMap.put(Constant.BEAN_NAME, job);
			this.serviceName = (String) job;
			this.job = AlphaDefaultSchedulerJob.class;
			isDefaultJob = false;
		}else{
			throw new SchedulerException("Unknown alpha scheduler job type !");
		}
		
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public Class<? extends AbsAlphaSchedulerJob> getJob() {
		return job;
	}

	public int getPolicyType() {
		return policyType;
	}

	public void setPolicyType(int policyType) {
		this.policyType = policyType;
	}

	public boolean isSimpleType() {
		return isSimpleType;
	}

	public void setSimpleType(boolean isSimpleType) {
		this.isSimpleType = isSimpleType;
	}

	public String getExecuteExpres() {
		return executeExpres;
	}

	public void setExecuteExpres(String executeExpres) {
		this.executeExpres = executeExpres;
	}

	public Date getValidTimeStart() {
		return validTimeStart;
	}

	public boolean isAllowRegisterSameService() {
		return allowRegisterSameService;
	}

	public void setAllowRegisterSameService(boolean allowRegisterSameService) {
		this.allowRegisterSameService = allowRegisterSameService;
	}

	public void setValidTimeStart(Date validTimeStart) {
		this.validTimeStart = validTimeStart;
	}

	public Date getValidTimeEnd() {
		return validTimeEnd;
	}

	public void setValidTimeEnd(Date validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}

	public Integer getExecuteTimes() {
		return executeTimes;
	}

	public void setExecuteTimes(Integer executeTimes) {
		this.executeTimes = executeTimes;
	}
	
	public boolean isExecuteForever() {
		return isExecuteForever;
	}

	public void setExecuteForever(boolean isExecuteForever) {
		this.isExecuteForever = isExecuteForever;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Map<?, ?> getJobDataMap() {
		if(jobDataMap == null)
			jobDataMap = new HashMap<Object,Object>();
		return jobDataMap;
	}

	public void setJobDataMap(Map<Object, Object> jobDataMap) {
		
		if(this.jobDataMap == null)
			this.jobDataMap = new HashMap<Object,Object>();
		
		this.jobDataMap.put(Constant.CUSTOM_JOB_DATA, jobDataMap);
	}

	public boolean isDefaultJob() {
		return isDefaultJob;
	}
}
