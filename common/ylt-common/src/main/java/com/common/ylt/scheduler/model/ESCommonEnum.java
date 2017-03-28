package com.common.ylt.scheduler.model;

/**
 * Created by zhengchenglei on 2016/11/4.
 */
public enum  ESCommonEnum {

    DEFALUT_ES_DS("defalut es persistence datasource","1"),
    PROPERTIES_CONF("properties config es datasource","properties_conf");



     ESCommonEnum(String comment,String value){
        this.comment = comment;
         this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public String getComment(){
        return this.comment;
    }

    private String comment;

    private String value;


}
