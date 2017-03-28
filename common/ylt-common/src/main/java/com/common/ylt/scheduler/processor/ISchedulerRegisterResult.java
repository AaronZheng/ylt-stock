package com.common.ylt.scheduler.processor;

import com.common.ylt.scheduler.dispatcher.SchedulerTaskRegister;

/**
 * Created by zhengchenglei on 2017/3/10.
 */
public interface ISchedulerRegisterResult {


    /**
     * 通知任务注册状态
     * @param schedulerTaskRegister
     */
    void noticeTaskRegisterState(SchedulerTaskRegister schedulerTaskRegister);


}
