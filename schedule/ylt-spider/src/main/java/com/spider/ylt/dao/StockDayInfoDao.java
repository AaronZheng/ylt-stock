package com.spider.ylt.dao;

import org.apache.ibatis.annotations.Param;

import com.common.ylt.model.StockDayInfo;


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
	
	/**
	 * 
	 * @param stockCode
	 * @param tradeTime
	 */
	StockDayInfo queryStockDayInfo(@Param("stockCode")String stockCode,@Param("tradeTime")String tradeTime);
	

}
