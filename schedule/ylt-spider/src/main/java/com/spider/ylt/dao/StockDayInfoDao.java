package com.spider.ylt.dao;

import com.spider.ylt.model.StockDayInfo;

public interface StockDayInfoDao {
	
	
	/**
	 * 
	 * @param stockDayInfo
	 */
	void saveStockDayInfo(StockDayInfo stockDayInfo);
	
	/**
	 * 
	 * @param stockDayInfo
	 */
	void updateStockDayInfo(StockDayInfo stockDayInfo);
	

}
