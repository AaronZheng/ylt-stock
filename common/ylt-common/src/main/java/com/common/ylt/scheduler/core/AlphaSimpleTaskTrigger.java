package com.common.ylt.scheduler.core;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quartz.JobDataMap;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.SimpleTriggerImpl;

import com.common.ylt.scheduler.processor.IAlphaJobTrigger;

public class AlphaSimpleTaskTrigger extends SimpleTriggerImpl implements IAlphaJobTrigger {
	

	private static final long serialVersionUID = -8291453685827390565L;

	
	public IAlphaJobTrigger withSchedule(String timerExpression,TimeUnit timeunit,Integer repeatCount) {
	        this.setRepeatInterval(timeunit.toMillis(Integer.parseInt(timerExpression)));
	        if(repeatCount == null)
	        	this.setRepeatCount(REPEAT_INDEFINITELY);
	        else 
	        	this.setRepeatCount(repeatCount);
	        
	        this.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY);
	        return this;
	}

	@Override
	public IAlphaJobTrigger startAt(Date startTime) {
		this.setStartTime(startTime);
		return this;
	}

	@Override
	public IAlphaJobTrigger endAt(Date endTime) {
		this.setEndTime(endTime);
		return this;
	}

	@Override
	public IAlphaJobTrigger bind() {
		return this;
	}
	

	@Override
	public void registerGroupAndName(String name, String group) {
		TriggerKey key = new TriggerKey(name, group);
		this.setKey(key);
	}

	@Override
	public IAlphaJobTrigger withPriority(int priority) {
		this.withPriority(priority);
		return this;
	}

	@Override
	public IAlphaJobTrigger addDescription(String description) {
		this.setDescription(description);
		return this;
	}

	@Override
	public void addPropertiesValue(String key, Object value) {
		this.getJobDataMap().put(key, value);
	}

	@Override
	public void addPropertiesMap(Map<String, Object> argMap) {
	
		if(argMap != null)
			this.setJobDataMap(new JobDataMap(argMap));
	}


	@Override
	public IAlphaJobTrigger setJobKey(String name, String group) {
		super.setKey(new TriggerKey(name,group));
		return this;
	}

}
