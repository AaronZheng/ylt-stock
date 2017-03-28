package com.common.ylt.scheduler.dispatcher;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhengchenglei on 2017/3/10.
 */
public class SchedulerTaskRegister implements Serializable {

	private static final long serialVersionUID = 6057846258311978820L;

	private String taskId;

    private Integer taskState;

    private boolean registerState;

    private String beanName;

    private Date registerTime;

    private String msg;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isRegisterState() {
        return registerState;
    }

    public void setRegisterState(boolean registerState) {
        this.registerState = registerState;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }
}
