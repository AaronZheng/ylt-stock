package com.spider.ylt.dao;

import java.util.List;

import javax.annotation.Resource;

import com.common.ylt.model.StockInfo;


@Resource
public interface StockBaseInfoDao {
	
	
	/**
	 * get stock code
	 * 
	 * @return
	 */
	List<StockInfo> queryStockInfo();
	
	

}
