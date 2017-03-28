package com.common.ylt.scheduler.processor;


import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.SchedulerConf;

/**
 * Created by zhengchenglei on 2017/3/6.
 */
public abstract class AbsThreadManager implements IThreadManager {

    protected int threadSize;
    protected Map<String,Thread> threadMap = new ConcurrentHashMap<String,Thread>();

    public AbsThreadManager(int threadSize){
        this.threadSize = threadSize;
    }


    public synchronized void initial(){

        int size = SchedulerConf.getSchedulerConfInstance().getWorkerThread() - threadMap.size();
        String threadName = UUID.randomUUID().toString().replace("-", "");
        for(int i = 0;i < size ;i ++ ) {
            threadMap.put(threadName, new SchedulerThread(threadName,new Runnable() {
                private String threadName;

                @Override
                public void run() {
                    try {
                        process();
                    } catch (Throwable e) {
                        SchedulerLogger.getLogger().error("Task execute failed", e);
                    }
                }

                public Runnable setThreadName(String threadName) {
                    this.threadName = threadName;
                    return this;
                }
            }.setThreadName(threadName)).startWorker());
        }

    }


    public synchronized void destroy() {

        boolean isNeedDestory = true;
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if (!Thread.State.TERMINATED.equals(entry.getValue().getState()) && !entry.getKey().equals(Thread.currentThread().getName())) {
                isNeedDestory = false;
            }
        }
        if (!isNeedDestory)
            return;
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            entry.getValue().interrupt();
        }
        threadMap.clear();
    }


    class SchedulerThread extends Thread{

        public SchedulerThread(String threadName,Runnable target) {
            super(target);
            this.setName(threadName);
        }

        public Thread startWorker(){
            super.start();
            return this;
        }
    }


}
