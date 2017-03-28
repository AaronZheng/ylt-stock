package com.common.ylt.scheduler.dispatcher;

import java.util.Date;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.SchedulerConf;
import com.common.ylt.scheduler.model.SchedulerExecuteResult;
import com.common.ylt.scheduler.model.SchedulerTask;
import com.common.ylt.scheduler.processor.AbsSchedulerBridging;
import com.common.ylt.scheduler.processor.AbsThreadManager;
import com.common.ylt.scheduler.processor.ISchedulerTask;

/**
 * Created by zhengchenglei on 2017/3/6.
 */
public class ThreadManager extends AbsThreadManager {

    private AbsSchedulerBridging schedulerBridging;

    public WebApplicationContext wac;


    public ThreadManager(int threadSize,AbsSchedulerBridging schedulerBridging) {
        super(threadSize);
        this.schedulerBridging = schedulerBridging;
    }

    @Override
    public void noticeWorker() {
        initial();
    }

    @Override
    public void process() throws InterruptedException {

        int endFlag = 0;
        SchedulerTask schedulerTask = null;
        while (true) {
            Long tid = System.currentTimeMillis();
            try {
                schedulerTask = schedulerBridging.getTask();
                if (schedulerTask == null) {
                    Thread.sleep(1000);
                    if((++endFlag)> 30){
                        this.threadMap.remove(Thread.currentThread().getName());
                        break;
                    }
                } else {
                    execute(tid,schedulerTask);
                    endFlag = 0;
                }
            }catch (Throwable e){
                SchedulerLogger.getLogger().error("tid:{} 澶勭悊浠诲姟澶辫触 taskCommand:{},e",tid,schedulerTask == null ? null : schedulerTask.toString(),e);
            }
        }
    }


    private void execute(Long tid,SchedulerTask schedulerTask) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        SchedulerExecuteResult result = taskHandler(tid,schedulerTask);
        if(SchedulerConf.getSchedulerConfInstance().getResultHandler() == null) {
            //TODO 鍚庢湡鎵╁睍
         //   schedulerBridging.callBack(result == null ? null : JSONObject.toJSONString(result));
        }else{
            SchedulerConf.getSchedulerConfInstance().getResultHandler().noticeExecuteResult(result);
        }
    }


    private SchedulerExecuteResult taskHandler(Long tid,SchedulerTask schedulerTask){

        Date startTime = new Date();
        try{
            SchedulerLogger.getLogger().info("tid:{} 寮�濮嬫墽琛屼换鍔� taskName:{},beanName:{},cronExpress:{}",tid,schedulerTask.getTaskName(),schedulerTask.getBeanName(),schedulerTask.getCronExpress());
            if(wac == null)
                wac= ContextLoader.getCurrentWebApplicationContext();
            if(wac == null){
                throw new IllegalStateException("Please check whether the spring context initial or not");
            }
            ISchedulerTask task = (ISchedulerTask) wac.getBean(schedulerTask.getBeanName());
            task.execute(tid,schedulerTask.getParam() == null ? null : JSONObject.parseObject(schedulerTask.getParam(),Map.class));
            return generateResultMap(tid,startTime,schedulerTask,1,"success");
        }catch (Throwable e){
            SchedulerLogger.getLogger().error("tid:{} execute task failure",tid,e);
            return generateResultMap(tid,startTime,schedulerTask,-1,e == null ? "failed" : e.getMessage());
        }
    }

    /**
     * @param tid
     * @param schedulerTask
     * @param executeState
     * @param message
     * @return
     */
    private SchedulerExecuteResult generateResultMap(Long tid,Date date,SchedulerTask schedulerTask,Integer executeState,String message){

        if(SchedulerConf.getSchedulerConfInstance().getResultHandler() == null)
            return null;
        SchedulerExecuteResult schedulerExecuteResult = new SchedulerExecuteResult();
        schedulerExecuteResult.setTid(tid);
        schedulerExecuteResult.setTaskId(schedulerTask.getTaskId());
        schedulerExecuteResult.setExecuteNode(SchedulerConf.getSchedulerConfInstance().getNodeName());
        schedulerExecuteResult.setTaskName(schedulerTask.getTaskName());
        schedulerExecuteResult.setExecuteStartTime(date);
        schedulerExecuteResult.setExecuteEndTime(new Date());
        schedulerExecuteResult.setMsg("tid:"+tid+",  "+message);
        schedulerExecuteResult.setExecuteState(executeState);
        return schedulerExecuteResult;

    }

    @Override
    public int getCurrentStatus() {
        return 0;
    }

   /* public void noticeWorker(){}*/


}
