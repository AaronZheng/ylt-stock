package com.common.ylt.scheduler.model;


import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.processor.ISchedulerConf;
import com.common.ylt.scheduler.processor.ISchedulerExecuteResult;
import com.common.ylt.scheduler.processor.ISchedulerRegisterResult;


/**
 * Created by zhengchenglei on 2017/3/6.
 */
public class SchedulerConf {

    private String clusterName;

    private String nodeName;

    private Integer workerThread = 5;

    private Boolean isClusterPattern = false;

    private String ipAddress;

    private static SchedulerConf schedulerConf;

    private String jimdbUrl;

    private ISchedulerExecuteResult resultHandler;

    private ISchedulerConf confClazz;

    private String tempBean;

    private String tempRespBean;

    private ISchedulerRegisterResult schedulerRegisterResult;

    private String tempResultRegisterBean;

    private static WebApplicationContext wac;


    public String getNodeName() {
        return nodeName;
    }

    public void updateNodeName(Long prefix){
        this.nodeName = prefix + "-"+ this.nodeName;
    }

    public void setNodeName(String nodeName) throws UnknownHostException {

        this.nodeName = getNodeName(nodeName);
    }


    private String getNodeName(String nodeName){

        String firstPrefix = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            firstPrefix = addr.getHostAddress();
            if(StringUtils.isBlank(firstPrefix))
                firstPrefix = addr.getHostName();
        }catch (Throwable e){
            SchedulerLogger.getLogger().error("Getting host information failure",e);
        }

        if(StringUtils.isBlank(nodeName) && StringUtils.isNotBlank(firstPrefix)){
            return firstPrefix;
        }else if(StringUtils.isNotBlank(nodeName) && StringUtils.isNotBlank(firstPrefix)){
            return nodeName + "." + firstPrefix;
        }else if(StringUtils.isNotBlank(nodeName) && StringUtils.isBlank(firstPrefix)){
            return nodeName;
        }else{
            return "";
        }
    }

    public Integer getWorkerThread() {
        return workerThread;
    }

    public void setWorkerThread(Integer workerThread) {
        this.workerThread = workerThread;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Boolean getClusterPattern() {
        return isClusterPattern;
    }

    public void setClusterPattern(Boolean clusterPattern) {
        isClusterPattern = clusterPattern;
    }

    public String getTempResultRegisterBean() {

        return tempResultRegisterBean;
    }

    public void setTempResultRegisterBean(String tempResultRegisterBean) {
        this.tempResultRegisterBean = tempResultRegisterBean;
    }

    public ISchedulerConf getConfClazz() {
        if(this.confClazz != null)
            return confClazz;
        else if(this.tempBean != null){
            confClazz = loadRespBean(tempBean,ISchedulerConf.class);
        }
        return confClazz;
    }

    public void setConfClazz(ISchedulerConf confClazz) {
        if(confClazz == null)
            return ;
        this.confClazz = confClazz;
    }

    public String getTempRespBean() {
        return tempRespBean;
    }

    public void setTempRespBean(String tempRespBean) {
        this.tempRespBean = tempRespBean;
    }

    public ISchedulerExecuteResult getResultHandler() {

        if(resultHandler != null)
            return resultHandler;
        else if(StringUtils.isNotBlank(tempRespBean)){
            resultHandler = loadRespBean(tempRespBean,ISchedulerExecuteResult.class);
        }
        return resultHandler;
    }

    public void setResultHandler(ISchedulerExecuteResult resultHandler) {
        if(resultHandler == null)
            return ;
        this.resultHandler = resultHandler;
    }

    public String getTempBean() {
        return tempBean;
    }

    public void setTempBean(String tempBean) {
        this.tempBean = tempBean;
    }

    public String getJimdbUrl() {
        return jimdbUrl;
    }

    public void setJimdbUrl(String jimdbUrl) {
        this.jimdbUrl = jimdbUrl;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

   /* public ISchedulerConf loadConfBean(String confBean){

        if(StringUtils.isBlank(confBean))
            return null;
        if (wac == null)
            wac = ContextLoader.getCurrentWebApplicationContext();
        if (wac == null) {
            throw new IllegalStateException("Please check whether the spring context initial");
        }
        return (ISchedulerConf) wac.getBean(confBean);
    }*/

    public ISchedulerRegisterResult getSchedulerRegisterResult() {
        if(schedulerRegisterResult != null)
            return schedulerRegisterResult;
        else if(tempResultRegisterBean != null){
            schedulerRegisterResult = loadRespBean(tempResultRegisterBean,ISchedulerRegisterResult.class);
        }
        return schedulerRegisterResult;
    }

    public void setSchedulerRegisterResult(ISchedulerRegisterResult schedulerRegisterResult) {
        if(schedulerRegisterResult == null)
            return ;
        this.schedulerRegisterResult = schedulerRegisterResult;
    }

    @SuppressWarnings("unchecked")
	public <T> T loadRespBean(String respBean,Class<T> clazz) {

        if(StringUtils.isBlank(respBean))
            return null;
        if (wac == null)
            wac = ContextLoader.getCurrentWebApplicationContext();
        int i = 0;
        if(wac == null) {
            while (i > 60) {
                wac = ContextLoader.getCurrentWebApplicationContext();
                i ++ ;
                if (wac == null)
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        SchedulerLogger.getLogger().error("Waiting for the spring context initialize failed",e);
                    }
                else
                    break;
            }
        }
        if (wac == null) {
            throw new IllegalStateException("Please check whether the spring context initial");
        }
        return (T) wac.getBean(respBean);
    }



    private SchedulerConf() {
    }

    public static synchronized SchedulerConf getSchedulerConfInstance(){
        if(schedulerConf == null) {
            schedulerConf = new SchedulerConf();
        }
        return schedulerConf;
    }

}
