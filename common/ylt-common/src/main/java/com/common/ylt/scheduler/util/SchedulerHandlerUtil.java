package com.common.ylt.scheduler.util;

import org.quartz.SchedulerException;

import com.common.ylt.scheduler.model.SchedulerExecuteResult;
import com.common.ylt.scheduler.model.SchedulerTask;
import com.common.ylt.scheduler.processor.ISchedulerBridging;

/**
 * Created by zhengchenglei on 2017/3/6.
 */
public class SchedulerHandlerUtil {

    public static ISchedulerBridging schedulerBridging;


    public static boolean registerTask(SchedulerTask task){

        if(schedulerBridging == null){
            new SchedulerException("The scheduler component don't initial");
        }
        return schedulerBridging.pushDynamicTask(task);
    }

    public static SchedulerExecuteResult getExecuteResult(){

        if(schedulerBridging == null){
            new SchedulerException("The scheduler component don't initial");
        }
        return schedulerBridging.getExecuteResult();
    }


}
