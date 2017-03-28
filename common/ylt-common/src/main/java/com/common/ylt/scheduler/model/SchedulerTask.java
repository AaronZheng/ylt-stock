package com.common.ylt.scheduler.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhengchenglei on 2017/3/6.
 */
public class SchedulerTask implements Serializable {


	private static final long serialVersionUID = 9072900893541300094L;

	private String taskId;

    private String taskName;

    private String cronExpress;
    
    private Integer taskStatus;

    private boolean isCustomTask;

    private String beanName;

    private String customClass;

    private Date validStart;

    private Date validEnd;

    private Long executeNum;

    private String  param;

    private Integer noticeType;

    private String masterNodeName;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCronExpress() {
        return cronExpress;
    }

    public void setCronExpress(String cronExpress) {
        this.cronExpress = cronExpress;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getMasterNodeName() {
        return masterNodeName;
    }

    public void setMasterNodeName(String masterNodeName) {
        this.masterNodeName = masterNodeName;
    }

    public boolean isCustomTask() {
        return isCustomTask;
    }

    public void setCustomTask(boolean customTask) {
        isCustomTask = customTask;
    }

    public String getCustomClass() {
        return customClass;
    }

    public void setCustomClass(String customClass) {
        this.customClass = customClass;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getValidStart() {
        if(validStart == null)
            return new Date();
        return validStart;
    }

    public void setValidStart(Date validStart) {

        this.validStart = validStart;
    }

    public Date getValidEnd() {
        if(validEnd == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR,20);
            return calendar.getTime();
        }
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    public Long getExecuteNum() {
        return executeNum;
    }

    public void setExecuteNum(Long executeNum) {
        this.executeNum = executeNum;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    @Override
    public String toString() {
        return "SchedulerTask{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", cronExpress='" + cronExpress + '\'' +
                ", taskStatus=" + taskStatus +
                ", isCustomTask=" + isCustomTask +
                ", beanName='" + beanName + '\'' +
                ", customClass='" + customClass + '\'' +
                ", validStart=" + validStart +
                ", validEnd=" + validEnd +
                ", executeNum=" + executeNum +
                ", param='" + param + '\'' +
                ", noticeType=" + noticeType +
                ", masterNodeName='" + masterNodeName + '\'' +
                '}';
    }
}
