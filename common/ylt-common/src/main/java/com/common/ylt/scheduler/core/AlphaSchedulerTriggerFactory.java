package com.common.ylt.scheduler.core;

import com.common.ylt.scheduler.processor.AbsESFactory;

public class AlphaSchedulerTriggerFactory extends AbsESFactory {

	public static AbsESFactory getAlphaJobTrigger() {

		if (factory == null)
			factory = new AlphaSchedulerTriggerFactory();
		return factory;
	}

	private static AbsESFactory factory;


	@Override
	public <T> T getInstance(Class<T> clazz) {
		return null;
	}

	@Override
	public <T> T getInstance(String type, Class<T> clazz) throws Exception {
		if(Boolean.parseBoolean(type))
			return (T)new AlphaSimpleTaskTrigger();
		else
			return (T)new AlphaCronTaskTrigger();
	}
}
