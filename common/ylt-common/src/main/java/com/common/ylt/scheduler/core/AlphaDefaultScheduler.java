package com.common.ylt.scheduler.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

import com.alibaba.fastjson.JSONObject;
import com.common.ylt.scheduler.dispatcher.SchedulerBridging;
import com.common.ylt.scheduler.dispatcher.ThreadManager;
import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.SchedulerConf;
import com.common.ylt.scheduler.model.SchedulerTask;
import com.common.ylt.scheduler.processor.IAlphaScheduler;
import com.common.ylt.scheduler.processor.IThreadManager;
import com.common.ylt.scheduler.util.Constant;
import com.common.ylt.scheduler.util.SchedulerHandlerUtil;

/**
 * @author zhengchenglei
 */
public class AlphaDefaultScheduler implements IAlphaScheduler {
	
	
	private static AlphaDefaultScheduler alphaScheduler ;

	private SchedulerBridging schedulerBridging;
	
	private Scheduler scheduler;

	private IThreadManager threadManager;


	@Override
	public String getSchedulerName() throws SchedulerException {
		return scheduler.getSchedulerName();
	}

	@Override
	public String getSchedulerInstanceId() throws SchedulerException {
		return scheduler.getSchedulerInstanceId();
	}

	@Override
	public SchedulerContext getContext() throws SchedulerException {
		return scheduler.getContext();
	}

	@Override
	public void start() throws SchedulerException {
		scheduler.start();
		startDaemon();
	}

	@Override
	public void startDelayed(int seconds) throws SchedulerException {
		scheduler.startDelayed(seconds);
	}

	@Override
	public boolean isStarted() throws SchedulerException {
		return scheduler.isStarted();
	}

	@Override
	public void standby() throws SchedulerException {
		scheduler.standby();
	}

	@Override
	public boolean isInStandbyMode() throws SchedulerException {
		return scheduler.isInStandbyMode();
	}

	@Override
	public void shutdown() throws SchedulerException {
		scheduler.shutdown();
	}

	@Override
	public void shutdown(boolean waitForJobsToComplete)throws SchedulerException {
		scheduler.shutdown(waitForJobsToComplete);
	}

	@Override
	public boolean isShutdown() throws SchedulerException {
		return scheduler.isShutdown();
	}

	@Override
	public SchedulerMetaData getMetaData() throws SchedulerException {
		return scheduler.getMetaData();
	}

	@Override
	public List<JobExecutionContext> getCurrentlyExecutingJobs()throws SchedulerException {
		return scheduler.getCurrentlyExecutingJobs();
	}

	@Override
	public void setJobFactory(JobFactory factory) throws SchedulerException {
		scheduler.setJobFactory(factory);
	}

	@Override
	public ListenerManager getListenerManager() throws SchedulerException {
		return scheduler.getListenerManager();
	}

	@Override
	public Date scheduleJob(JobDetail jobDetail, Trigger trigger)throws SchedulerException {
		return scheduler.scheduleJob(jobDetail, trigger);
	}

	@Override
	public Date scheduleJob(Trigger trigger) throws SchedulerException {
		return scheduler.scheduleJob(trigger);
	}

	@Override
	public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs,boolean replace) throws SchedulerException {
		scheduler.scheduleJobs(triggersAndJobs, replace);
	}

	@Override
	public void scheduleJob(JobDetail jobDetail,Set<? extends Trigger> triggersForJob, boolean replace)
			throws SchedulerException {
		scheduler.scheduleJob(jobDetail, triggersForJob, replace);
	}

	@Override
	public boolean unscheduleJob(TriggerKey triggerKey)throws SchedulerException {
		return scheduler.unscheduleJob(triggerKey);
	}

	@Override
	public boolean unscheduleJobs(List<TriggerKey> triggerKeys)throws SchedulerException {
		return scheduler.unscheduleJobs(triggerKeys);
	}

	@Override
	public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger)throws SchedulerException {
		return scheduler.rescheduleJob(triggerKey, newTrigger);
	}

	@Override
	public void addJob(JobDetail jobDetail, boolean replace)throws SchedulerException {
		scheduler.addJob(jobDetail, replace);
	}

	@Override
	public void addJob(JobDetail jobDetail, boolean replace,boolean storeNonDurableWhileAwaitingScheduling)
			throws SchedulerException {
		scheduler.addJob(jobDetail, replace, storeNonDurableWhileAwaitingScheduling);
	}

	@Override
	public boolean deleteJob(JobKey jobKey) throws SchedulerException {
		return scheduler.deleteJob(jobKey);
	}

	@Override
	public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
		return scheduler.deleteJobs(jobKeys);
	}

	@Override
	public void triggerJob(JobKey jobKey) throws SchedulerException {
		scheduler.triggerJob(jobKey);
	}

	@Override
	public void triggerJob(JobKey jobKey, JobDataMap data)throws SchedulerException {
		scheduler.triggerJob(jobKey, data);
	}

	@Override
	public void pauseJob(JobKey jobKey) throws SchedulerException {
		scheduler.pauseJob(jobKey);
	}

	@Override
	public void pauseJobs(GroupMatcher<JobKey> matcher)throws SchedulerException {
		scheduler.pauseJobs(matcher);
	}

	private AlphaDefaultScheduler() throws SchedulerException {
		super();
		Properties properties = new Properties();
		properties.setProperty("org.quartz.scheduler.instanceName","AlphaScheduler");
		properties.setProperty("org.quartz.scheduler.rmi.export","false");
		properties.setProperty("org.quartz.scheduler.rmi.proxy","false");
		properties.setProperty("org.quartz.scheduler.wrapJobExecutionInUserTransaction","false");
		properties.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
		properties.setProperty("org.quartz.threadPool.threadCount","10");
		properties.setProperty("org.quartz.threadPool.threadPriority","5");
		properties.setProperty("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread","true");
		properties.setProperty("org.quartz.jobStore.misfireThreshold","60000");
		properties.setProperty("org.quartz.jobStore.class","org.quartz.simpl.RAMJobStore");
		scheduler = new StdSchedulerFactory(properties).getScheduler();
	}

	@Override
	public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.resumeTrigger(triggerKey);
	}

	@Override
	public void pauseTriggers(GroupMatcher<TriggerKey> matcher)throws SchedulerException {
		scheduler.resumeTriggers(matcher);
	}

	@Override
	public void resumeJob(JobKey jobKey) throws SchedulerException {
		scheduler.resumeJob(jobKey);
	}

	@Override
	public void resumeJobs(GroupMatcher<JobKey> matcher)throws SchedulerException {
		scheduler.resumeJobs(matcher);
	}

	@Override
	public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
		scheduler.resumeTrigger(triggerKey);
	}

	@Override
	public void resumeTriggers(GroupMatcher<TriggerKey> matcher)throws SchedulerException {
		scheduler.resumeTriggers(matcher);
	}

	@Override
	public void pauseAll() throws SchedulerException {
		scheduler.pauseAll();
	}

	@Override
	public void resumeAll() throws SchedulerException {
		scheduler.resumeAll();
	}

	@Override
	public List<String> getJobGroupNames() throws SchedulerException {
		return scheduler.getJobGroupNames();
	}

	@Override
	public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher)throws SchedulerException {
		return scheduler.getJobKeys(matcher);
	}

	@Override
	public List<? extends Trigger> getTriggersOfJob(JobKey jobKey)throws SchedulerException {
		return scheduler.getTriggersOfJob(jobKey);
	}

	@Override
	public List<String> getTriggerGroupNames() throws SchedulerException {
		return scheduler.getTriggerGroupNames();
	}

	@Override
	public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher)throws SchedulerException {
		return scheduler.getTriggerKeys(matcher);
	}

	@Override
	public Set<String> getPausedTriggerGroups() throws SchedulerException {
		return scheduler.getPausedTriggerGroups();
	}

	@Override
	public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
		return scheduler.getJobDetail(jobKey);
	}

	@Override
	public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
		return scheduler.getTrigger(triggerKey);
	}

	@Override
	public TriggerState getTriggerState(TriggerKey triggerKey)throws SchedulerException {
		return scheduler.getTriggerState(triggerKey);
	}

	@Override
	public void addCalendar(String calName, Calendar calendar, boolean replace,boolean updateTriggers) throws SchedulerException {
		scheduler.addCalendar(calName, calendar, replace, updateTriggers);
	}

	@Override
	public boolean deleteCalendar(String calName) throws SchedulerException {
		return scheduler.deleteCalendar(calName);
	}

	@Override
	public Calendar getCalendar(String calName) throws SchedulerException {
		return scheduler.getCalendar(calName);
	}

	@Override
	public List<String> getCalendarNames() throws SchedulerException {
		return scheduler.getCalendarNames();
	}

	@Override
	public boolean interrupt(JobKey jobKey)throws UnableToInterruptJobException {
		return scheduler.interrupt(jobKey);
	}

	@Override
	public boolean interrupt(String fireInstanceId)throws UnableToInterruptJobException {
		return scheduler.interrupt(fireInstanceId);
	}


	@Override
	public void removeAllJob() throws SchedulerException {
		scheduler.shutdown();
		scheduler.start();
	}


	@Override
	public boolean checkExists(JobKey jobKey) throws SchedulerException {
		return scheduler.checkExists(jobKey);
	}

	@Override
	public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
		return scheduler.checkExists(triggerKey);
	}

	@Override
	public void clear() throws SchedulerException {
		scheduler.clear();
	}

	@Override
	public void destroyAllServices() {
		
	}

	@Override
	public void printAllRegisterInfo() {
		
	}


	private void updateNodeInfo(){
		Long nodeNo = schedulerBridging.getSequence();
		if(nodeNo != null)
			SchedulerConf.getSchedulerConfInstance().updateNodeName(nodeNo);
	}

	@Override
	public void registerSchedulerBridging(SchedulerBridging schedulerBridging) {

		this.schedulerBridging = schedulerBridging;
	}


	/**
	 * @description Generate default scheduler instance ...
	 * 
	 * @return
	 * 
	 * @throws SchedulerException 
	 */
	public synchronized static IAlphaScheduler getDefaultInstance() throws Exception {
		
		if(alphaScheduler == null) {
			alphaScheduler = new AlphaDefaultScheduler();

			alphaScheduler.schedulerBridging = new SchedulerBridging();
			alphaScheduler.schedulerBridging.registerRedisClientResource(SchedulerConf.getSchedulerConfInstance().getJimdbUrl());
			alphaScheduler.threadManager = new ThreadManager(SchedulerConf.getSchedulerConfInstance().getWorkerThread(),alphaScheduler.schedulerBridging);
			SchedulerHandlerUtil.schedulerBridging = alphaScheduler.schedulerBridging;
		}
		return alphaScheduler;
	}


	private void startDaemon(){

		updateNodeInfo();
		if(!SchedulerConf.getSchedulerConfInstance().getClusterPattern()){
			registerTask();
			return ;
		}
		heartbeatThread();
	}


	private void heartbeatThread(){

			new Timer().schedule(new TimerTask() {
				private AtomicInteger atomicInteger = new AtomicInteger(0);
				private boolean isMaster = false;

				@Override
				public void run() {
					try {
						schedulerBridging.registerHeartbeat(atomicInteger,SchedulerConf.getSchedulerConfInstance().getNodeName());
						if (!isMaster && schedulerBridging.isMaster()) {
							schedulerBridging.clear();
							List<SchedulerTask> schedulerTasks = registerTask();
							schedulerBridging.pushStaticTaskList(schedulerTasks);
						}
						if((isMaster = schedulerBridging.isMaster())) {
							handleNewWorker();
						}
						if (schedulerBridging.getTaskLength() > 0) {
							threadManager.noticeWorker();
						}
					}catch (Throwable e){
						SchedulerLogger.getLogger().error("scheduler state check failed",e);
					}
				}
			},10000,1000);
			{
				Thread.currentThread().setName(Constant.ALPHA_SCHEDULER_DAEMON);
			}
	}

	private void handleNewWorker() {

		if (schedulerBridging.getDynamicTaskLength() == null || schedulerBridging.getDynamicTaskLength() <= 0) {
			return;
		}
		SchedulerTask schedulerTask = schedulerBridging.getDynamicTaskCommand();
		while (schedulerTask != null) {
			try {
				List<SchedulerTask> schedulerTasks= new ArrayList<SchedulerTask>();
				schedulerTasks.add(schedulerTask);
				if (schedulerTask.getTaskStatus() == 2) {
					AlphaSchedulerRegister.getDefaultInstance().removeJob(schedulerTask.getTaskId(),false);
					Map<Object, Object> map = (schedulerTask.getParam() == null ? null : JSONObject.parseObject(schedulerTask.getParam(), Map.class));
					AlphaSchedulerRegister.getDefaultInstance().registerSchedulerByBeanName(schedulerTask.getBeanName(), schedulerTask.getCronExpress(),
							schedulerTask.getExecuteNum() == null ? null : schedulerTask.getExecuteNum().intValue(), schedulerTask.getValidStart(), schedulerTask.getValidEnd(), schedulerTask.getTaskId(), map, false);
					schedulerBridging.pushStaticTaskList(schedulerTasks);
				} else if (schedulerTask.getTaskStatus() == 3) {
					AlphaSchedulerRegister.getDefaultInstance().pauseJob(schedulerTask.getTaskId());
					schedulerBridging.pushStaticTaskList(schedulerTasks);
				} else if (schedulerTask.getTaskStatus() == 4) {
					AlphaSchedulerRegister.getDefaultInstance().removeJob(schedulerTask.getTaskId(),true);
					schedulerBridging.pushStaticTaskList(schedulerTasks);
				}
			} catch (Throwable e) {
				SchedulerLogger.getLogger().error("Register task failed task:{}", schedulerTask == null ? null : schedulerTask.toString(),e);
			}
			schedulerTask = schedulerBridging.getDynamicTaskCommand();
		}
	}

	private List<SchedulerTask> registerTask() {
		List<SchedulerTask> tasks = null;
		if(SchedulerConf.getSchedulerConfInstance().getConfClazz() != null) {
			tasks = SchedulerConf.getSchedulerConfInstance().getConfClazz().getSchedulerConf();
		}else{
			tasks = schedulerBridging.getStaticTaskCommand();
		}
		if (CollectionUtils.isEmpty(tasks) || !schedulerBridging.isMaster())
			return null;
		for (SchedulerTask schedulerTask : tasks) {
			try {
				if(schedulerTask.getTaskStatus() == null || schedulerTask.getTaskStatus() == 3 || schedulerTask.getTaskStatus() == 4)
					continue;
				AlphaSchedulerRegister.getDefaultInstance().removeJob(schedulerTask.getTaskId(),false);
				Map<Object, Object> map = (schedulerTask.getParam() == null ? null : JSONObject.parseObject(schedulerTask.getParam(), Map.class));
				AlphaSchedulerRegister.getDefaultInstance().registerSchedulerByBeanName(schedulerTask.getBeanName(), schedulerTask.getCronExpress(),
						schedulerTask.getExecuteNum() == null ? null : schedulerTask.getExecuteNum().intValue(), schedulerTask.getValidStart(), schedulerTask.getValidEnd(), schedulerTask.getTaskId(), map, false);
			} catch (Throwable e) {
				SchedulerLogger.getLogger().error("注册任务失败 schedulerTask:{}", schedulerTask.toString(), e);
			}
		}
		return tasks;
	}

}
