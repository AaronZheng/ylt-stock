package com.common.ylt.scheduler.configuration;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;

import com.common.ylt.scheduler.core.AlphaDefaultScheduler;
import com.common.ylt.scheduler.core.AlphaSchedulerJobGenerator;
import com.common.ylt.scheduler.core.AlphaSchedulerRegister;
import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.SchedulerConf;
import com.common.ylt.scheduler.processor.IAlphaScheduler;
import com.common.ylt.scheduler.processor.ISchedulerConf;
import com.common.ylt.scheduler.processor.ISchedulerExecuteResult;
import com.common.ylt.scheduler.processor.ISchedulerRegisterResult;


public class AlphaSchedulerComponent {

	
	public void start() throws SchedulerException {
		
		try {
			SchedulerLogger.getLogger().info("Is beginning to load alpha scheduler component !");

			initEnvironment();

			IAlphaScheduler scheduler = AlphaDefaultScheduler.getDefaultInstance();

			((AlphaSchedulerRegister)AlphaSchedulerRegister.getDefaultInstance()).registerContext(scheduler, new AlphaSchedulerJobGenerator());

			scheduler.start();

			SchedulerLogger.getLogger().info("Loading alpha scheduler completed !");

		} catch (Throwable e) {
		     throw new SchedulerException("Starting alpha scheduler failure !",e);
		}
	}


	private void initEnvironment() throws SchedulerException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

		Properties properties = new Properties();
		properties.load(this.getClass().getResourceAsStream("/scheduler.properties"));
		SchedulerConf.getSchedulerConfInstance().setClusterName(properties.getProperty("clusterName"));
		SchedulerConf.getSchedulerConfInstance().setNodeName(properties.getProperty("nodeName"));
		SchedulerConf.getSchedulerConfInstance().setWorkerThread(properties.getProperty("workerThread") == null ? 5 : Integer.parseInt(properties.getProperty("workerThread")));
		SchedulerConf.getSchedulerConfInstance().setJimdbUrl(properties.getProperty("jimdbUrl"));
		SchedulerConf.getSchedulerConfInstance().setConfClazz(properties.getProperty("confClass") == null ? null : (ISchedulerConf) Class.forName(properties.getProperty("confClass")).newInstance());
		SchedulerConf.getSchedulerConfInstance().setTempBean(properties.getProperty("confBean"));
		SchedulerConf.getSchedulerConfInstance().setResultHandler(properties.getProperty("responseClass") == null ? null : (ISchedulerExecuteResult) Class.forName(properties.getProperty("responseClass")).newInstance());
		SchedulerConf.getSchedulerConfInstance().setTempRespBean(properties.getProperty("responseBean"));
		SchedulerConf.getSchedulerConfInstance().setClusterPattern(properties.getProperty("clusterPattern") == null ? true : Boolean.parseBoolean(properties.getProperty("clusterPattern")));
		SchedulerConf.getSchedulerConfInstance().setSchedulerRegisterResult(properties.getProperty("taskRegisterResultClass") == null ? null :(ISchedulerRegisterResult) Class.forName(properties.getProperty("taskRegisterResultClass")).newInstance());
		SchedulerConf.getSchedulerConfInstance().setTempResultRegisterBean(properties.getProperty("taskRegisterResultBean"));


		if(StringUtils.isBlank(SchedulerConf.getSchedulerConfInstance().getNodeName())){
			throw new SchedulerException("Please configure node name in scheduler config file");
		}
/*		if(SchedulerConf.getSchedulerConfInstance().getConfClazz() == null){
			SchedulerLogger.getLogger().warn("Please check the confClazz or confBean attribute can't be null");
		}*/
/*		if(SchedulerConf.getSchedulerConfInstance().getResultHandler() == null){
			SchedulerLogger.getLogger().warn("Please check the responseClazz or responseBean attribute can't be null");
		}*/
	}

}
