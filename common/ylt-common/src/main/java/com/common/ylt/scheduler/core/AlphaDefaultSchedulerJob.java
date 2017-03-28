package com.common.ylt.scheduler.core;

import java.util.Date;
import java.util.Map;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.SchedulerConf;
import com.common.ylt.scheduler.model.SchedulerExecuteResult;
import com.common.ylt.scheduler.model.SchedulerTask;
import com.common.ylt.scheduler.processor.AbsAlphaSchedulerJob;
import com.common.ylt.scheduler.processor.AbsSchedulerBridging;
import com.common.ylt.scheduler.processor.ISchedulerTask;
import com.common.ylt.scheduler.util.Constant;
import com.common.ylt.scheduler.util.SchedulerHandlerUtil;

public class AlphaDefaultSchedulerJob extends AbsAlphaSchedulerJob {

	private static WebApplicationContext wac;
	public final static Logger logger = SchedulerLogger.getLogger();

	@Override
	public void processor(Map<?, ?> param) {

		if(SchedulerConf.getSchedulerConfInstance().getClusterPattern() != null && SchedulerConf.getSchedulerConfInstance().getClusterPattern()) {
			if(!((AbsSchedulerBridging)SchedulerHandlerUtil.schedulerBridging).isMaster()){
				try {
					SchedulerLogger.getLogger().info("Don't be master . can't distribute task");
					AlphaSchedulerRegister.getDefaultInstance().removeAllJob();
				} catch (SchedulerException e) {
					SchedulerLogger.getLogger().error("Don't be master . can't distribute task",e);
				}
				return ;
			}
			SchedulerTask task = new SchedulerTask();
			task.setBeanName((String)param.get(Constant.BEAN_NAME));
			task.setTaskId((String)param.get(Constant.JOB_NAME));
			task.setParam(param.get(Constant.CUSTOM_JOB_DATA) == null ? null : JSONObject.toJSONString(param.get(Constant.CUSTOM_JOB_DATA)));
			task.setMasterNodeName(SchedulerConf.getSchedulerConfInstance().getNodeName());
			task.setCustomTask(false);
			task.setTaskStatus(2);
			((AbsSchedulerBridging) SchedulerHandlerUtil.schedulerBridging).registerJob(task);
		}else{
			SchedulerExecuteResult result = localPattern(param);
			result.setTaskId((String)param.get(Constant.JOB_NAME));
			if(SchedulerConf.getSchedulerConfInstance().getResultHandler() != null)
				callback(result);
		}
	}


	private SchedulerExecuteResult localPattern(Map<?, ?> param) {

		Long tid = System.currentTimeMillis();
		if (param.get(Constant.BEAN_NAME) == null || "".equals(param.get(Constant.BEAN_NAME))) {
			logger.warn("Nothing to do !");
			return null;
		}
		try {
			logger.info("The alpha scheduler is beginning to execute scheduler job. The job NAME is {} and job group is {}", param.get(Constant.JOB_NAME), param.get(Constant.JOB_GROUP));
			if (wac == null)
				wac = ContextLoader.getCurrentWebApplicationContext();
			if (wac == null) {
				throw new IllegalStateException("Please check whether the spring context initial");
			}
			ISchedulerTask task = (ISchedulerTask) wac.getBean((String) param.get(Constant.BEAN_NAME));
			task.execute(tid,(Map<?, ?>) param.get(Constant.CUSTOM_JOB_DATA));
			SchedulerLogger.getLogger().info("The job {}  group {} execute completed !", param.get(Constant.JOB_NAME), param.get(Constant.JOB_GROUP));
			return genereateSchedulerExecuteResult(tid,1,"success",tid,new Date());
		} catch (Throwable e) {
			logger.error("scheduler call service {} exception !", param.get(Constant.BEAN_NAME), e);
			return genereateSchedulerExecuteResult(tid,-1,(e == null ? "failed" : e.getMessage()),tid,new Date());
		}
	}


	private SchedulerExecuteResult genereateSchedulerExecuteResult(Long tid,Integer code,String message,Long startTime,Date endTime){
		if(SchedulerConf.getSchedulerConfInstance().getResultHandler() == null)
			return null;
		SchedulerExecuteResult result = new SchedulerExecuteResult();
		result.setExecuteStartTime(new Date(startTime));
		result.setTid(tid);
		result.setExecuteState(code);
		result.setMsg(message);
		result.setExecuteNode(SchedulerConf.getSchedulerConfInstance().getNodeName());
		result.setExecuteEndTime(endTime);
		return result;
	}

	/**
	 *
	 * @param result
     */
	private void callback(SchedulerExecuteResult result){

		SchedulerConf.getSchedulerConfInstance().getResultHandler().noticeExecuteResult(result);
	}
}
