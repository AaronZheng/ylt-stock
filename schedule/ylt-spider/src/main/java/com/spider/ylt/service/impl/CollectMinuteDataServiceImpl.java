package com.spider.ylt.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.ylt.net.NetHandler;
import com.spider.ylt.analyser.queue.StockQueue;
import com.spider.ylt.service.ICollectMinuteDataService;


@Service
public class CollectMinuteDataServiceImpl implements ICollectMinuteDataService{

	@Value("${realTimeUrl}")
	private String realTimeUrl;
	
	public void collectMinuteData() throws Exception {
		
		List<String> stockCodes = getStockCode();
		while(CollectionUtils.isNotEmpty(stockCodes)){
			StockQueue.put(NetHandler.sendMessageToInternet(generateUrl()));
		}
	}
	
	
	private String generateUrl(){
		
		return "500008";
	}
	
	private List<String> getStockCode(){
		
		return null;
	}
	

}
