package com.common.ylt.scheduler.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.ylt.scheduler.model.NodeInfo;
import com.common.ylt.scheduler.model.SchedulerTask;

/**
 * Created by zhengchenglei on 2017/3/7.
 */
public abstract class AbsSchedulerBridging implements ISchedulerBridging{

    protected JimRedisClient redisClient;

    protected String MASTER_KEY = "alpha_scheduler_master_key";

    protected String HEARTBEAT_KEY = "alpha_scheduler_heartbeat_key";

    protected String ALPHA_SCHEDULER_CLUSTER_INFO = "alpha_scheduler_cluster_info";

    protected String SCHEDULER_TASK_QUEUE = "alpha_scheduler_task_queue";

    protected String SCHEDULER_RESPONSE_QUEUE = "alpha_scheduler_task_queue";

    protected String STATIC_TASK_LIST = "alpha_static_task_list";

    protected String DYNAMIC_TASK_LIST = "alpha_dynamic_task_list";

    protected String NODE_SEQUENCE = "alpha_node_sequence";

    protected Long MASTER_EXPIRES_TIME = 10000L;

    protected Long HEART_EXPIRES_TIME = 10000L;


    public abstract void registerHeartbeat(AtomicInteger atomicInteger , String name);

    public abstract boolean isMaster();

    public abstract SchedulerTask getTask();

    public abstract void callBack(String result);

    public abstract void registerRedisClientResource(String urlResource) throws Exception;

    public abstract List<SchedulerTask> getStaticTaskCommand();

    public abstract void removeStaticTaskList(SchedulerTask schedulerTasks);

    public abstract Long getTaskLength();

    public abstract SchedulerTask getDynamicTaskCommand();

    public abstract void pushStaticTaskList(List<SchedulerTask> schedulerTasks);

    public abstract void registerJob(SchedulerTask task);

    protected  List<NodeInfo> getRegisterNodeInfo(){

        String data =  redisClient.get(ALPHA_SCHEDULER_CLUSTER_INFO);
        if(StringUtils.isBlank(data) || "null".equals(data)||"true".equals(data))
            return null;
        List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
        for(Object nodeStr : JSONArray.parseObject(data,List.class)){
            if(nodeStr == null)
                continue;
            nodeInfos.add(JSONObject.parseObject(nodeStr.toString(),NodeInfo.class));
        }
        return nodeInfos;
    }


    public void registerNode(NodeInfo nodeInfo){

        List<NodeInfo> nodeInfos = getRegisterNodeInfo();
        if(CollectionUtils.isEmpty(nodeInfos)){
            List<NodeInfo> nis = new ArrayList<NodeInfo>();
            nis.add(nodeInfo);
            redisClient.set(ALPHA_SCHEDULER_CLUSTER_INFO, 300, JSONObject.toJSONString(nis));
            return ;
        }
        boolean exits = true;
        for(NodeInfo ni : nodeInfos){
            if(nodeInfo.getNodeName().equals(ni.getNodeName())) {
                exits = false;
                break;
            }
        }
        if(!exits){
            nodeInfos.add(nodeInfo);
        }
        redisClient.set(ALPHA_SCHEDULER_CLUSTER_INFO, 300, JSONObject.toJSONString(nodeInfos));
    }

    public Long getSequence(){

        Long nodeNo = redisClient.incr(this.NODE_SEQUENCE);
        redisClient.expire(this.NODE_SEQUENCE,24*3600*1000);
        return nodeNo;
    }


    public List<NodeInfo> getNodeInfo() {

        List<NodeInfo> nodeInfos = getRegisterNodeInfo();
        if (CollectionUtils.isEmpty(nodeInfos))
            return null;

        for (NodeInfo nodeInfo : nodeInfos) {
            String node = redisClient.get(MASTER_KEY);
            if (node != null && node.equals(nodeInfo.getNodeName())) {
                nodeInfo.setMaster(true);
            }
            if (redisClient.get(HEARTBEAT_KEY + "." + nodeInfo.getNodeName()) == null) {
                nodeInfo.setDead(true);
            } else {
                nodeInfo.setDead(false);
            }
        }
        return nodeInfos;
    }


}
