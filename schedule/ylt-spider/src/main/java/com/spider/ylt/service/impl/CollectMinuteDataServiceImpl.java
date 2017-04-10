package com.spider.ylt.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.common.ylt.cache.Cache;
import com.common.ylt.cache.KeyGenerator;
import com.common.ylt.net.NetHandler;
import com.common.ylt.scheduler.processor.ISchedulerTask;
import com.common.ylt.util.Sequeuce;
import com.spider.ylt.dao.StockDayInfoDao;
import com.spider.ylt.dao.StockMinuteInfoDao;
import com.spider.ylt.model.StockDayInfo;
import com.spider.ylt.model.StockInfo;
import com.spider.ylt.model.StockMinuteInfo;


public class CollectMinuteDataServiceImpl implements ISchedulerTask{

	@Value("${realTimeUrl}")
	private String realTimeUrl;
	private String timeStamp;
	private Double openPlatePrice;
	private Double closePlatePrice;
	private Double yClosePlatePrice;
	private Double maxPrice;
	private Double lowPrice;
	private String dayId;
	@Autowired
	private StockMinuteInfoDao stockMinuteInfoDao;
	@Autowired
	private StockDayInfoDao stockDayInfoDao;
	
	
	private void saveTradeData(String stockCode,String[] content){
		
		StockMinuteInfo stockMinuteInfo = generateStockMinuteInfo(stockCode,content);
		if(stockMinuteInfo == null)
			return ;
		
		if(StringUtils.isBlank(dayId)){
			openPlateDataHandler(stockMinuteInfo,content);
		}
		
		if(StringUtils.isBlank(timeStamp) || !timeStamp.equals(stockMinuteInfo.getTradeTime())){
			timeStamp = stockMinuteInfo.getTradeTime();
			
			if(maxPrice == null || lowPrice == null ||
					maxPrice < Double.parseDouble(content[4]) || lowPrice > Double.parseDouble(content[5])){
				middlePlateDataHandler(stockMinuteInfo,content);
			}
			
			stockMinuteInfoDao.saveStockMinuteInfo(stockMinuteInfo);
		}else{
			if(closePlatePrice != null)
				return;
			closePlatePrice = stockMinuteInfo.getStockPrice();
			closePlateDataHandler(stockMinuteInfo,content);
		}
	}
	

	
	private void openPlateDataHandler(StockMinuteInfo stockMinuteInfo,String[] content){
		dayId = Sequeuce.genereateRandomStr();
		StockDayInfo stockDayInfo = new StockDayInfo();
		stockDayInfo.setId(dayId);
		stockDayInfo.setStockCode(stockMinuteInfo.getStockCode());
		if(openPlatePrice == null){
			openPlatePrice = Double.parseDouble(content[1]);
		}
		stockDayInfo.setOpenPlatePrice(openPlatePrice);
		if(yClosePlatePrice == null){
			yClosePlatePrice = Double.parseDouble(content[2]);
		}
		stockDayInfo.setyClosePlatePrice(yClosePlatePrice);
		if(maxPrice == null || maxPrice < Double.parseDouble(content[4])){
			maxPrice = Double.parseDouble(content[4]);
		}
		stockDayInfo.setMaxPrice(maxPrice);
		if(lowPrice == null || lowPrice > Double.parseDouble(content[5])){
			lowPrice = Double.parseDouble(content[5]);
		}
		stockDayInfo.setLowPrice(lowPrice);
		stockDayInfo.setDealStockNum(stockMinuteInfo.getTradeNum());
		stockDayInfo.setDealStockPrice(stockMinuteInfo.getTradePrice());
		stockDayInfo.setLrrq(new Date());
		stockDayInfo.setYxbj("1");
		stockDayInfoDao.saveStockDayInfo(stockDayInfo);
	}
	
	
	private void closePlateDataHandler(StockMinuteInfo stockMinuteInfo,String[] content){
		StockDayInfo stockDayInfo = new StockDayInfo();
		stockDayInfo.setId(dayId);
		stockDayInfo.setStockCode(stockMinuteInfo.getStockCode());
		if(openPlatePrice == null){
			openPlatePrice = Double.parseDouble(content[1]);
		}
		stockDayInfo.setOpenPlatePrice(openPlatePrice);
		if(closePlatePrice == null){
			closePlatePrice = stockMinuteInfo.getStockPrice();
		}
		stockDayInfo.setClosePlatePrice(closePlatePrice);
		if(yClosePlatePrice == null){
			yClosePlatePrice = Double.parseDouble(content[2]);
		}
		stockDayInfo.setyClosePlatePrice(yClosePlatePrice);
		if(maxPrice == null || maxPrice < Double.parseDouble(content[4])){
			maxPrice = Double.parseDouble(content[4]);
		}
		stockDayInfo.setMaxPrice(maxPrice);
		if(lowPrice == null || lowPrice > Double.parseDouble(content[5])){
			lowPrice = Double.parseDouble(content[5]);
		}
		stockDayInfo.setLowPrice(lowPrice);
		stockDayInfo.setDealStockNum(stockMinuteInfo.getTradeNum());
		stockDayInfo.setDealStockPrice(stockMinuteInfo.getTradePrice());
		stockDayInfo.setXgrq(new Date());
		stockDayInfo.setYxbj("1");
		stockDayInfoDao.updateStockDayInfo(stockDayInfo);
	}
	
	
	private void middlePlateDataHandler(StockMinuteInfo stockMinuteInfo,String[] content){
		
		StockDayInfo stockDayInfo = new StockDayInfo();
		stockDayInfo.setId(dayId);
		stockDayInfo.setStockCode(stockMinuteInfo.getStockCode());
		if(openPlatePrice == null){
			openPlatePrice = Double.parseDouble(content[1]);
		}
		stockDayInfo.setOpenPlatePrice(openPlatePrice);
		if(yClosePlatePrice == null){
			yClosePlatePrice = Double.parseDouble(content[2]);
		}
		stockDayInfo.setyClosePlatePrice(yClosePlatePrice);
		if(maxPrice == null || maxPrice < Double.parseDouble(content[4])){
			maxPrice = Double.parseDouble(content[4]);
		}
		stockDayInfo.setMaxPrice(maxPrice);
		if(lowPrice == null || lowPrice > Double.parseDouble(content[5])){
			lowPrice = Double.parseDouble(content[5]);
		}
		stockDayInfo.setLowPrice(lowPrice);
		stockDayInfo.setDealStockNum(stockMinuteInfo.getTradeNum());
		stockDayInfo.setDealStockPrice(stockMinuteInfo.getTradePrice());
		stockDayInfo.setXgrq(new Date());
		stockDayInfo.setYxbj("1");
		stockDayInfoDao.updateStockDayInfo(stockDayInfo);
		
	}
	
	
	
	private StockMinuteInfo generateStockMinuteInfo(String stockCode,String[] content){
		
		if(content.length <1)
			return null;
		
		StockMinuteInfo stockMinuteInfo = new StockMinuteInfo();
		stockMinuteInfo.setId(Sequeuce.genereateRandomStr());
		stockMinuteInfo.setStockCode(stockCode);
		stockMinuteInfo.setStockPrice(Double.parseDouble(content[3]));
		stockMinuteInfo.setTradeNum(Integer.parseInt(content[8]));
		stockMinuteInfo.setTradePrice(Long.parseLong(content[9].split("\\.")[0]));
		stockMinuteInfo.setBuyOneNum(Integer.parseInt(content[10]));
		stockMinuteInfo.setBuyOnePrice(Double.parseDouble(content[11]));
		stockMinuteInfo.setBuyTowNum(Integer.parseInt(content[12]));
		stockMinuteInfo.setBuyTowPrice(Double.parseDouble(content[13]));
		stockMinuteInfo.setBuyThreeNum(Integer.parseInt(content[14]));
		stockMinuteInfo.setBuyThreePrice(Double.parseDouble(content[15]));
		stockMinuteInfo.setBuyFourNum(Integer.parseInt(content[16]));
		stockMinuteInfo.setBuyFourPrice(Double.parseDouble(content[17]));
		stockMinuteInfo.setBuyFiveNum(Integer.parseInt(content[18]));
		stockMinuteInfo.setBuyFivePrice(Double.parseDouble(content[19]));
		stockMinuteInfo.setSaleOneNum(Integer.parseInt(content[20]));
		stockMinuteInfo.setSaleOnePrice(Double.parseDouble(content[21]));
		stockMinuteInfo.setSaleTowNum(Integer.parseInt(content[22]));
		stockMinuteInfo.setSaleTowPrice(Double.parseDouble(content[23]));
		stockMinuteInfo.setSaleThreeNum(Integer.parseInt(content[24]));
		stockMinuteInfo.setSaleThreePrice(Double.parseDouble(content[25]));
		stockMinuteInfo.setSaleFourNum(Integer.parseInt(content[26]));
		stockMinuteInfo.setSaleFourPrice(Double.parseDouble(content[27]));
		stockMinuteInfo.setSaleFiveNum(Integer.parseInt(content[28]));
		stockMinuteInfo.setSaleFivePrice(Double.parseDouble(content[29]));
		stockMinuteInfo.setTradeTime(content[30]+" "+content[31]);
		stockMinuteInfo.setLrrq(new Date());
		stockMinuteInfo.setYxbj("1");
		return stockMinuteInfo;
	}
	
	private String generateUrl(String stockCode,String blockId){
		
		if("sh".equals(blockId)){
			return realTimeUrl.replace("code", "sh"+stockCode);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<StockInfo> getStockCode(){
		
		return (List<StockInfo>) Cache.get(KeyGenerator.getStockInfoKey());
	}


	@Override
	public void execute(Long tid, Map<?, ?> param) throws Throwable {
		
		List<StockInfo> stockInfos = getStockCode();
		if(CollectionUtils.isEmpty(stockInfos))
			return;
		for(StockInfo stockInfo : stockInfos){
			String info = NetHandler.sendMessageToInternet(generateUrl(stockInfo.getStockCode(),stockInfo.getBlockId()));
		
			if(StringUtils.isBlank(info))
				continue;
			String[] content = info.split("=")[1].replace("\"", "").replace(" ", "").trim().split(",");
			saveTradeData(stockInfo.getStockCode(),content);
		}
	}
	

}
