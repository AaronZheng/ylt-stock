package com.spider.ylt.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spider.ylt.service.ICollectMinuteDataService;


@Service
public class CollectMinuteDataServiceImpl implements ICollectMinuteDataService{

	@Value("${realTimeUrl}")
	private String realTimeUrl;
	
	public void collectMinuteData() {
		
		List<String> stockCodes = getStockCode();
		while(CollectionUtils.isNotEmpty(stockCodes)){
			
		}
	}
	
	private List<String> getStockCode(){
		
		return null;
	}
	

}
