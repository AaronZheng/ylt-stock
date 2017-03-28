package com.common.ylt.scheduler.processor;


import java.util.List;

import com.common.ylt.scheduler.model.NodeInfo;
import com.common.ylt.scheduler.model.SchedulerExecuteResult;
import com.common.ylt.scheduler.model.SchedulerTask;

/**
 * Created by zhengchenglei on 2017/3/5.
 */
public interface ISchedulerBridging {


    boolean pushDynamicTask(SchedulerTask schedulerTask);

    SchedulerExecuteResult getExecuteResult();

    Long getDynamicTaskLength();

    List<NodeInfo> getNodeInfo();



}
