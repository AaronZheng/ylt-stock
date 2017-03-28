package com.common.ylt.scheduler.processor;

import java.util.Map;

/**
 * Created by zhengchenglei on 2017/3/3.
 */
public interface ISchedulerTask {


    void execute(Long tid,Map<?,?> param) throws Throwable;

}
