package com.common.ylt.scheduler.model;

import java.io.Serializable;

/**
 * Created by zhengchenglei on 2017/3/8.
 */
public class NodeInfo implements Serializable {


	private static final long serialVersionUID = 5605427048686309806L;

	private String nodeName;

    private String ipAddress;

    private boolean isMaster;

    private boolean isDead;


    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}


