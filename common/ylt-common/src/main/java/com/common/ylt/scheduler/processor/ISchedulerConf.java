package com.common.ylt.scheduler.processor;


import java.util.List;

import com.common.ylt.scheduler.model.SchedulerTask;

/**
 * Created by zhengchenglei on 2017/3/7.
 */
public interface ISchedulerConf {

    /**
     * 获取定时任务配置信息
     * @return
     */
    List<SchedulerTask> getSchedulerConf();

}
