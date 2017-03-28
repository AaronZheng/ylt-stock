package com.common.ylt.scheduler.processor;

/**
 * Created by zhengchenglei on 2016/11/4.
 */
public abstract class AbsESFactory {


    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public abstract <T> T getInstance(Class<T> clazz);


    /**
     *
     * @param type
     * @param clazz
     * @param <T>
     * @return
     */
    public abstract <T> T getInstance(String type,Class<T> clazz) throws Exception;


}
