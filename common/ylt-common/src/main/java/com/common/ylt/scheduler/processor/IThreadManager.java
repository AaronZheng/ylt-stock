package com.common.ylt.scheduler.processor;

/**
 * Created by zhengchenglei on 2017/3/6.
 */
public interface IThreadManager {


    void noticeWorker();

    void initial();

    void process() throws InterruptedException;

    void destroy();

    int getCurrentStatus();

}
