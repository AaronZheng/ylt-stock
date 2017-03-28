package com.common.ylt.scheduler.dispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.ylt.scheduler.logger.SchedulerLogger;
import com.common.ylt.scheduler.model.NodeInfo;
import com.common.ylt.scheduler.model.SchedulerConf;
import com.common.ylt.scheduler.model.SchedulerExecuteResult;
import com.common.ylt.scheduler.model.SchedulerTask;
import com.common.ylt.scheduler.processor.AbsSchedulerBridging;
import com.common.ylt.scheduler.processor.JimRedisClient;


/**
 * Created by zhengchenglei on 2017/3/5.
 */
public class SchedulerBridging extends AbsSchedulerBridging {


    public void registerHeartbeat(AtomicInteger atomicInteger ,String name) {

        try {
            if (StringUtils.isBlank(name))
                return;
            redisClient.set(HEARTBEAT_KEY + "." + name, HEART_EXPIRES_TIME, name);
            if(redisClient.get(MASTER_KEY) == null || "null".equals(redisClient.get(MASTER_KEY))){
                redisClient.setNX(MASTER_KEY, name, MASTER_EXPIRES_TIME);
            }
            if(isMaster()){
                redisClient.expire(MASTER_KEY,MASTER_EXPIRES_TIME);
            }
            int cycle = atomicInteger.incrementAndGet();
            if (cycle == 1 || cycle == 10) {
                atomicInteger.set(1);
                registerNodeInfo();
                refresh();
            }
        }catch (Exception e){
            SchedulerLogger.getLogger().error("Heartbeat failed",e);
        }

    }


    public SchedulerBridging() {

        if(StringUtils.isBlank(SchedulerConf.getSchedulerConfInstance().getClusterName()))
            return;
        MASTER_KEY = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ MASTER_KEY;
        HEARTBEAT_KEY = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ HEARTBEAT_KEY;
        ALPHA_SCHEDULER_CLUSTER_INFO = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ ALPHA_SCHEDULER_CLUSTER_INFO;
        SCHEDULER_TASK_QUEUE = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ SCHEDULER_TASK_QUEUE;
        SCHEDULER_RESPONSE_QUEUE = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ SCHEDULER_RESPONSE_QUEUE;
        STATIC_TASK_LIST = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ STATIC_TASK_LIST;
        DYNAMIC_TASK_LIST = SchedulerConf.getSchedulerConfInstance().getClusterName() +"."+ DYNAMIC_TASK_LIST;
        NODE_SEQUENCE = SchedulerConf.getSchedulerConfInstance().getClusterName() + "." + NODE_SEQUENCE;
    }

    public boolean isMaster() {

        if(StringUtils.isBlank(SchedulerConf.getSchedulerConfInstance().getNodeName()))
            return false;
        if(redisClient.get(MASTER_KEY) == null || "null".equals(redisClient.get(MASTER_KEY))){
            redisClient.setNX(MASTER_KEY, SchedulerConf.getSchedulerConfInstance().getNodeName(), MASTER_EXPIRES_TIME);
        }
        return SchedulerConf.getSchedulerConfInstance().getNodeName().equals(redisClient.get(MASTER_KEY));
    }


    public SchedulerTask getTask() {

        String data = redisClient.rPop(SCHEDULER_TASK_QUEUE);
        if(StringUtils.isBlank(data)){
            return null;
        }
        SchedulerTask schedulerTask = JSONObject.parseObject(data,SchedulerTask.class);
        return schedulerTask;
    }


    public void callBack(String result) {
        redisClient.lPush(SCHEDULER_RESPONSE_QUEUE,300,result);
    }


    private void registerNodeInfo(){

        try {
            NodeInfo nodeInfo = new NodeInfo();
            nodeInfo.setNodeName(SchedulerConf.getSchedulerConfInstance().getNodeName());
            nodeInfo.setMaster(this.isMaster());
            nodeInfo.setIpAddress(SchedulerConf.getSchedulerConfInstance().getIpAddress());
            registerNode(nodeInfo);
        }catch (Exception e){
            SchedulerLogger.getLogger().error("register node information failure ..",e);
        }
    }

    public void registerRedisClientResource(String urlResource) throws Exception {

        ReloadableJimClientFactoryBean jimClientFactoryBean = new ReloadableJimClientFactoryBean();
        jimClientFactoryBean.setJimUrl(urlResource);
        JimRedisClient redisClient = new JimRedisClient();
        redisClient.setJimClient(jimClientFactoryBean.getClient());
        this.redisClient = redisClient;
    }

    public Long getTaskLength() {
        return redisClient.lLen(SCHEDULER_TASK_QUEUE);
    }

    public SchedulerTask getDynamicTaskCommand() {
        String data = redisClient.rPop(DYNAMIC_TASK_LIST);
        return data == null ? null : JSONObject.parseObject(data,SchedulerTask.class);
    }

    public List<SchedulerTask> getStaticTaskCommand() {

        String data = redisClient.get(STATIC_TASK_LIST);
        if(StringUtils.isBlank(data))
            return null;

        List<SchedulerTask> schedulerTasks = new ArrayList<SchedulerTask>();
        for(Object task : JSONArray.parseObject(data,List.class)){
            if(task == null)
                continue;
            schedulerTasks.add(JSONObject.parseObject(task.toString(),SchedulerTask.class));
        }
        return schedulerTasks;
    }

    public void pushStaticTaskList(List<SchedulerTask> schedulerTasks) {

        if(CollectionUtils.isEmpty(schedulerTasks))
            return;
        String data = redisClient.get(STATIC_TASK_LIST);
        List<SchedulerTask> tasks = new ArrayList<SchedulerTask>();
        if(StringUtils.isNotBlank(data)) {
            for (Object task : JSONArray.parseObject(data, List.class)) {
                if (task == null || StringUtils.isBlank(task.toString()))
                    continue;
                SchedulerTask stask = JSONObject.parseObject(task.toString(), SchedulerTask.class);
                if(checkSameTask(stask.getTaskId(),schedulerTasks)){
                    continue;
                }
                tasks.add(JSONObject.parseObject(task.toString(), SchedulerTask.class));
            }
        }
        List<SchedulerTask> expiresTask = new ArrayList<SchedulerTask>();
        for(SchedulerTask task :schedulerTasks){
            if(task.getTaskStatus() == 4){
                expiresTask.add(task);
            }
        }
        schedulerTasks.remove(expiresTask);
        tasks.addAll(schedulerTasks);
        redisClient.set(STATIC_TASK_LIST,3600,JSONObject.toJSONString(tasks));
    }

    @Override
    public void registerJob(SchedulerTask task) {
        redisClient.lPush(SCHEDULER_TASK_QUEUE,300,JSONObject.toJSONString(task));
    }

    public void removeStaticTaskList(SchedulerTask schedulerTask) {

        String data = redisClient.get(STATIC_TASK_LIST);
        List<SchedulerTask> tasks = new ArrayList<SchedulerTask>();
        if(StringUtils.isNotBlank(data)) {
            for (Object task : JSONArray.parseObject(data, List.class)) {
                if (task == null)
                    continue;
                SchedulerTask stask = JSONObject.parseObject(task.toString(), SchedulerTask.class);
                if(stask.getTaskId() != null || stask.getTaskId().equals(schedulerTask.getTaskId())){
                    continue;
                }
                tasks.add(JSONObject.parseObject(task.toString(), SchedulerTask.class));
            }
        }
        redisClient.set(STATIC_TASK_LIST,3600,JSONObject.toJSONString(tasks));
    }

    public Long getDynamicTaskLength(){
        return redisClient.lLen(DYNAMIC_TASK_LIST);
    }


    private boolean checkSameTask(String taskId,List<SchedulerTask> schedulerTasks){

        if(taskId == null || CollectionUtils.isEmpty(schedulerTasks))
            return false;
        for(SchedulerTask task : schedulerTasks) {
            if (taskId.equals(task.getTaskId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pushDynamicTask(SchedulerTask schedulerTask) {

        List<SchedulerTask> schedulerTasks = this.getStaticTaskCommand();
        if(schedulerTask == null || schedulerTask.getTaskStatus() == null || schedulerTask.getTaskStatus() == 1)
            return false;
        if(CollectionUtils.isEmpty(schedulerTasks) && (schedulerTask.getTaskStatus() == 3 || schedulerTask.getTaskStatus() ==4)){
            return false;
        }else if(CollectionUtils.isEmpty(schedulerTasks)){
            redisClient.lPush(DYNAMIC_TASK_LIST,300,JSONObject.toJSONString(schedulerTask));
            return true;
        }
        boolean isExist = false;

        for(SchedulerTask task : schedulerTasks){
            if(schedulerTask.getTaskId().equals(task.getTaskId())){
                if(schedulerTask.getTaskStatus().equals(task.getTaskStatus()))
                    return false;
                isExist = true;
            }
        }
        if(!isExist){
            if(schedulerTask.getTaskStatus() == 3 || schedulerTask.getTaskStatus() == 4){
                return false;
            }
        }
        redisClient.lPush(DYNAMIC_TASK_LIST,300,JSONObject.toJSONString(schedulerTask));
        return true;
    }


    @Override
    public SchedulerExecuteResult getExecuteResult() {
        String response =  redisClient.rPop(SCHEDULER_RESPONSE_QUEUE);
        if(response == null)
            return null;
        return JSONObject.parseObject(response,SchedulerExecuteResult.class);
    }

    public void clear(){
        redisClient.expire(STATIC_TASK_LIST,1);
        redisClient.expire(DYNAMIC_TASK_LIST,1);
    }

    public void refresh(){
        try {
            if (this.isMaster()) {
                redisClient.expire(STATIC_TASK_LIST, 3600000);
            }
        }catch (Exception e){
            SchedulerLogger.getLogger().error("fresh STATIC_TASK_LIST EXCEPTION");
        }
    }

}
