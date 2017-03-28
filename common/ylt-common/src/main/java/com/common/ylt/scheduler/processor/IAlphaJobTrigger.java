package com.common.ylt.scheduler.processor;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

import org.quartz.Trigger;


public interface IAlphaJobTrigger extends Trigger{

	/**
	 *
	 * @param timerExpression
	 * @param timeunit
	 * @param repeatCount
	 * @return
	 * @throws ParseException
     */
	IAlphaJobTrigger withSchedule(String timerExpression, TimeUnit timeunit, Integer repeatCount)throws ParseException;
	
	/**
	 * @param date
	 */
	IAlphaJobTrigger startAt(Date date);
	
	/**
	 * @param date
	 */
	IAlphaJobTrigger endAt(Date date);
	

	IAlphaJobTrigger bind();
    
    /**
     * @param name
     * @param group
     */
    void registerGroupAndName(String name, String group);
    
    /**
     * @param priority
     */
	IAlphaJobTrigger withPriority(int priority);
    
    /**
     * @param description
     */
	IAlphaJobTrigger addDescription(String description);
    
    /**
     * @param key
     * @param value
     */
    void addPropertiesValue(String key, Object value);
    
    /**
     * @param argMap
     */
    void addPropertiesMap(Map<String, Object> argMap);
    
    /**
     * @param name
     * @param group
     * @return
     */
	public IAlphaJobTrigger setJobKey(String name, String group);
	
	
}
