package com.spider.ylt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.common.ylt.cache.Cache;
import com.common.ylt.cache.KeyGenerator;
import com.common.ylt.net.NetHandler;
import com.common.ylt.scheduler.processor.ISchedulerTask;
import com.common.ylt.util.Sequeuce;
import com.spider.ylt.dao.StockDayInfoDao;
import com.spider.ylt.dao.StockMinuteInfoDao;
import com.spider.ylt.model.StockDayFlag;
import com.spider.ylt.model.StockDayInfo;
import com.spider.ylt.model.StockInfo;
import com.spider.ylt.model.StockMinuteInfo;


public class CollectMinuteDataServiceImpl implements ISchedulerTask{

	@Value("${realTimeUrl}")
	private String realTimeUrl;
	
	private Map<String,StockDayFlag> flagType = new HashMap<String,StockDayFlag>();
	
	@Autowired
	private StockMinuteInfoDao stockMinuteInfoDao;
	@Autowired
	private StockDayInfoDao stockDayInfoDao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private void tradeDataHandler(String stockCode,String[] content,Date collectTime){
		
		StockMinuteInfo stockMinuteInfo = generateStockMinuteInfo(stockCode,content);
		
		if(stockMinuteInfo == null)
			return ;
		stockMinuteInfo.setCollectTime(collectTime);
		if(!flagType.containsKey(stockMinuteInfo.getStockCode()) || StringUtils.isBlank(flagType.get(stockMinuteInfo.getStockCode()).getDayId())){
			openPlateDataHandler(stockMinuteInfo,content);
		}
		
		if(StringUtils.isBlank(flagType.get(stockCode).getTimeStamp()) || !flagType.get(stockCode).getTimeStamp().equals(stockMinuteInfo.getTradeTime())){
			flagType.get(stockCode).setTimeStamp(stockMinuteInfo.getTradeTime());
			
			if(flagType.get(stockCode).getMaxPrice() == null || flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() ==0.0 ||
					flagType.get(stockCode).getLowPrice() == null ||flagType.get(stockCode).getLowPrice() == 0.0 ||
					flagType.get(stockCode).getMaxPrice() < Double.parseDouble(content[4]) || flagType.get(stockCode).getLowPrice() > Double.parseDouble(content[5])){
				middlePlateDataHandler(stockMinuteInfo,content);
			}
			
			stockMinuteInfoDao.saveStockMinuteInfo(stockMinuteInfo);
			flagType.get(stockCode).setClosePlatePrice(null);
		}else{
			if(flagType.get(stockCode).getClosePlatePrice() != null)
				return;
			closePlateDataHandler(stockMinuteInfo,content);
		}
	}
	

	
	private void openPlateDataHandler(StockMinuteInfo stockMinuteInfo,String[] content){
		try{
			StockDayInfo stockDayInfo = stockDayInfoDao.queryStockDayInfo(stockMinuteInfo.getStockCode(), content[30]);
			String dayId = Sequeuce.genereateRandomStr();
			StockDayFlag stockDayFlag = new StockDayFlag();
			if(stockDayInfo != null){
				stockDayFlag.setDayId(stockDayInfo.getId());
				stockDayFlag.setOpenPlatePrice(stockDayInfo.getOpenPlatePrice());
				stockDayFlag.setyClosePlatePrice(stockDayInfo.getyClosePlatePrice());
				stockDayFlag.setLowPrice(stockDayInfo.getLowPrice());
				stockDayFlag.setMaxPrice(stockDayInfo.getMaxPrice());
				flagType.put(stockMinuteInfo.getStockCode(), stockDayFlag);
				return;
			}
			stockDayInfo = new StockDayInfo();
			stockDayFlag.setDayId(dayId);
			stockDayInfo.setId(dayId);
			stockDayInfo.setStockCode(stockMinuteInfo.getStockCode());
			stockDayFlag.setOpenPlatePrice(Double.parseDouble(content[1]));
			stockDayInfo.setOpenPlatePrice(stockDayFlag.getOpenPlatePrice());
			stockDayFlag.setyClosePlatePrice(Double.parseDouble(content[2]));
			stockDayInfo.setyClosePlatePrice(stockDayFlag.getyClosePlatePrice());
			stockDayFlag.setMaxPrice(Double.parseDouble(content[4]));
			stockDayInfo.setMaxPrice(stockDayFlag.getMaxPrice());
			stockDayFlag.setLowPrice(Double.parseDouble(content[5]));
			stockDayInfo.setLowPrice(stockDayFlag.getLowPrice());
			stockDayInfo.setDealStockNum(stockMinuteInfo.getTradeNum());
			stockDayInfo.setDealStockPrice(stockMinuteInfo.getTradePrice());
			stockDayInfo.setLrrq(new Date());
			stockDayInfo.setTradeTime(content[30]);
			stockDayInfo.setYxbj("1");
			stockDayInfoDao.saveStockDayInfo(stockDayInfo);
			flagType.put(stockMinuteInfo.getStockCode(), stockDayFlag);
		}catch(Exception e){
			logger.error("处理日数据异常 stockInfo:{}",stockMinuteInfo,e);
		}
	}
	
	
	private void closePlateDataHandler(StockMinuteInfo stockMinuteInfo,String[] content){
	
		StockDayInfo stockDayInfo = new StockDayInfo();
		try{
			stockDayInfo.setId(flagType.get(stockMinuteInfo.getStockCode()).getDayId());
			stockDayInfo.setStockCode(stockMinuteInfo.getStockCode());
			flagType.get(stockMinuteInfo.getStockCode()).setOpenPlatePrice(Double.parseDouble(content[1]));
			stockDayInfo.setOpenPlatePrice(flagType.get(stockMinuteInfo.getStockCode()).getOpenPlatePrice());
			flagType.get(stockMinuteInfo.getStockCode()).setClosePlatePrice(stockMinuteInfo.getStockPrice());
			stockDayInfo.setClosePlatePrice(flagType.get(stockMinuteInfo.getStockCode()).getClosePlatePrice());
			flagType.get(stockMinuteInfo.getStockCode()).setyClosePlatePrice(Double.parseDouble(content[2]));
			stockDayInfo.setyClosePlatePrice(flagType.get(stockMinuteInfo.getStockCode()).getyClosePlatePrice());
			if(flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() == null ||flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() == 0.0 || flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() < Double.parseDouble(content[4])){
				flagType.get(stockMinuteInfo.getStockCode()).setMaxPrice(Double.parseDouble(content[4]));
			}
			stockDayInfo.setMaxPrice(flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice());
			if(flagType.get(stockMinuteInfo.getStockCode()).getLowPrice() == null ||flagType.get(stockMinuteInfo.getStockCode()).getLowPrice() == 0.0 || flagType.get(stockMinuteInfo.getStockCode()).getLowPrice() > Double.parseDouble(content[5])){
				flagType.get(stockMinuteInfo.getStockCode()).setLowPrice(Double.parseDouble(content[5]));
			}
			stockDayInfo.setLowPrice(flagType.get(stockMinuteInfo.getStockCode()).getLowPrice());
			stockDayInfo.setDealStockNum(stockMinuteInfo.getTradeNum());
			stockDayInfo.setDealStockPrice(stockMinuteInfo.getTradePrice());
			stockDayInfo.setXgrq(new Date());
			stockDayInfo.setYxbj("1");
			stockDayInfoDao.updateStockDayInfo(stockDayInfo);
		}catch(Exception e){
			logger.error("处理日数据异常 stockInfo:{}",stockDayInfo,e);
		}
	}
	
	
	private void middlePlateDataHandler(StockMinuteInfo stockMinuteInfo,String[] content){
		    StockDayInfo stockDayInfo = new StockDayInfo();
		try{
			stockDayInfo.setId(flagType.get(stockMinuteInfo.getStockCode()).getDayId());
			stockDayInfo.setStockCode(stockMinuteInfo.getStockCode());
			flagType.get(stockMinuteInfo.getStockCode()).setOpenPlatePrice(Double.parseDouble(content[1]));
			stockDayInfo.setOpenPlatePrice(flagType.get(stockMinuteInfo.getStockCode()).getOpenPlatePrice());
			flagType.get(stockMinuteInfo.getStockCode()).setyClosePlatePrice(Double.parseDouble(content[2]));
			stockDayInfo.setyClosePlatePrice(flagType.get(stockMinuteInfo.getStockCode()).getyClosePlatePrice());
			if(flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() == null || flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() == 0.0 ||
					flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice() < Double.parseDouble(content[4])){
				flagType.get(stockMinuteInfo.getStockCode()).setMaxPrice(Double.parseDouble(content[4]));
			}
			stockDayInfo.setMaxPrice(flagType.get(stockMinuteInfo.getStockCode()).getMaxPrice());
			if(flagType.get(stockMinuteInfo.getStockCode()).getLowPrice() == null || flagType.get(stockMinuteInfo.getStockCode()).getLowPrice() == 0.0 ||
					flagType.get(stockMinuteInfo.getStockCode()).getLowPrice() > Double.parseDouble(content[5])){
				flagType.get(stockMinuteInfo.getStockCode()).setLowPrice(Double.parseDouble(content[5]));
			}
			stockDayInfo.setLowPrice(flagType.get(stockMinuteInfo.getStockCode()).getLowPrice());
			flagType.get(stockMinuteInfo.getStockCode()).setClosePlatePrice(stockMinuteInfo.getStockPrice());
			stockDayInfo.setClosePlatePrice(stockMinuteInfo.getStockPrice());
			stockDayInfo.setDealStockNum(stockMinuteInfo.getTradeNum());
			stockDayInfo.setDealStockPrice(stockMinuteInfo.getTradePrice());
			stockDayInfo.setXgrq(new Date());
			stockDayInfo.setYxbj("1");
		stockDayInfoDao.updateStockDayInfo(stockDayInfo);
		}catch(Exception e){
			logger.error("处理日数据异常 stockInfo:{}",stockDayInfo,e);
		}
		
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
		}else if("sz".equals(blockId)){
			return realTimeUrl.replace("code", "sz"+stockCode);
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
		Date collectTime = new Date();
		if(CollectionUtils.isEmpty(stockInfos))
			return;
		for(StockInfo stockInfo : stockInfos){
			String info = null;
			try{
				info = NetHandler.sendMessageToInternet(generateUrl(stockInfo.getStockCode(),stockInfo.getStockOrgCode()));
				if(StringUtils.isBlank(info)||"\"\";".equals(info.trim().split("\\=")[1])){
					logger.warn("为获取到数据 stockInfo:{}",info);
					continue;
				}
				String[] content = info.split("=")[1].replace("\"", "").replace(" ", "").trim().split(",");
				tradeDataHandler(stockInfo.getStockCode(),content,collectTime);
			}catch(Exception e){
				logger.error("处理异常stockInfo:{}",info,e);
			}
		}
	}
	

}
