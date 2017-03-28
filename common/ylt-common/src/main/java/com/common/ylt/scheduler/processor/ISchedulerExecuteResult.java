package com.common.ylt.scheduler.processor;

import com.common.ylt.scheduler.model.SchedulerExecuteResult;

/**
 * Created by zhengchenglei on 2017/3/7.
 */
public interface ISchedulerExecuteResult {


    /**
     * 通知执行结果
     * @param schedulerExecuteResult
     */
    void noticeExecuteResult(SchedulerExecuteResult schedulerExecuteResult);


}
