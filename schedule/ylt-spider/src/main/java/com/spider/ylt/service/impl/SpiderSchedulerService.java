package com.spider.ylt.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import com.common.ylt.scheduler.core.AlphaSchedulerRegister;


public class SpiderSchedulerService {
	
	private Map<String,String> paramMap;
	
	
	
	public void initScheduelrTask() throws Throwable{
		
		if(paramMap == null || paramMap.isEmpty())
			return;

		for(Entry<String,String> entry : paramMap.entrySet()){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 20);
			AlphaSchedulerRegister.getDefaultInstance().registerSchedulerByBeanName(entry.getKey(), entry.getValue(), null, new Date(), calendar.getTime(), entry.getKey(), null);
		}
	}
	

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	
}
