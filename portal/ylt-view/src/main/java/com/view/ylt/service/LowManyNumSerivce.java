package com.view.ylt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.common.ylt.model.LowManyNumInfo;
import com.dao.common.ylt.StockDayInfoDao;

public class LowManyNumSerivce {
	
	@Autowired
	private StockDayInfoDao stockDayInfoDao;
	
	
	
	public List<LowManyNumInfo> queryLowManyNumData(double lowPrice,double hightPrice,int numType,int stockType,
			double variance,int times,Date startTime,Date calcDate){
		
		lowPrice = lowPrice == 0.0 ? 10 : lowPrice;
		hightPrice = hightPrice == 0.0 ? 50 : hightPrice;
		List<LowManyNumInfo> lowManyNumInfos = null;
		if(numType == 0){
			lowManyNumInfos = stockDayInfoDao.analysisAlwaysLowManyNum(lowPrice, hightPrice, stockType, variance, times, startTime, calcDate);
		}else{
			lowManyNumInfos = stockDayInfoDao.analysisUpAndDownManyNum(lowPrice, hightPrice, stockType, variance, times, startTime, calcDate);
		}
		return lowManyNumInfos;
	}

}
