package com.common.ylt.scheduler.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhengchenglei on 2017/3/7.
 */
public class SchedulerExecuteResult implements Serializable {

 
	private static final long serialVersionUID = -4100779053080031046L;

	private Long tid;

    private String taskId;

    private String taskName;

    private String executeNode;

    private Date executeStartTime;

    private Date executeEndTime;

    private Integer executeState;

    private String msg;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getExecuteNode() {
        return executeNode;
    }

    public void setExecuteNode(String executeNode) {
        this.executeNode = executeNode;
    }

    public Date getExecuteStartTime() {
        return executeStartTime;
    }

    public void setExecuteStartTime(Date executeStartTime) {
        this.executeStartTime = executeStartTime;
    }

    public Date getExecuteEndTime() {
        return executeEndTime;
    }

    public void setExecuteEndTime(Date executeEndTime) {
        this.executeEndTime = executeEndTime;
    }


    public Integer getExecuteState() {
        return executeState;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "SchedulerExecuteResult{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", executeNode='" + executeNode + '\'' +
                ", executeStartTime=" + executeStartTime +
                ", executeEndTime=" + executeEndTime +
                ", executeState=" + executeState +
                ", msg='" + msg + '\'' +
                '}';
    }
}
