package com.common.ylt.scheduler.model;

import java.io.Serializable;

/**
 * Created by zhengchenglei on 2016/12/13.
 */
public class TypeStats implements Serializable{


	private static final long serialVersionUID = -3380892623170584231L;

	private String clusterName;

    private String indexName;

    private String typeName;

    private Long usedSize;

    private String id;

    private String routing;

    private String Mapping;

    private Long docNum;



    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMapping() {
        return Mapping;
    }

    public void setMapping(String mapping) {
        Mapping = mapping;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public Long getDocNum() {
        return docNum;
    }

    public void setDocNum(Long docNum) {
        this.docNum = docNum;
    }

    public Long getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(Long usedSize) {
        this.usedSize = usedSize;
    }
}
