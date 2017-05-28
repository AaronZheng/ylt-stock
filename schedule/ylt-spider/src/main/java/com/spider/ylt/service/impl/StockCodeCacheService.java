package com.spider.ylt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.common.ylt.cache.Cache;
import com.common.ylt.cache.KeyGenerator;
import com.common.ylt.model.StockInfo;
import com.common.ylt.scheduler.processor.ISchedulerTask;
import com.spider.ylt.dao.StockBaseInfoDao;

public class StockCodeCacheService implements ISchedulerTask{
	
	@Autowired
	private StockBaseInfoDao stockBaseInfoDao;
	

	public void execute(Long tid, Map<?, ?> param) throws Throwable {
		
		List<StockInfo> stockInfos = stockBaseInfoDao.queryStockInfo();
		Cache.put(KeyGenerator.getStockInfoKey(), stockInfos);
	}
	
	

}
