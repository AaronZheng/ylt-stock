package com.common.ylt.model;

import java.io.Serializable;

public class LowManyNumInfo implements Serializable{

	private static final long serialVersionUID = 536512912460444908L;
	
	
	private String stockCode;
	
	private String StockName;
	
	private String yesterdayPrice;
	
	private Double currentPrice;
	
	private Long yestardayNum;
	
	private Long currentNum;
	
	private Integer times;

	
	
	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return StockName;
	}

	public void setStockName(String stockName) {
		StockName = stockName;
	}

	public String getYesterdayPrice() {
		return yesterdayPrice;
	}

	public void setYesterdayPrice(String yesterdayPrice) {
		this.yesterdayPrice = yesterdayPrice;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getYestardayNum() {
		return yestardayNum;
	}

	public void setYestardayNum(Long yestardayNum) {
		this.yestardayNum = yestardayNum;
	}

	public Long getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Long currentNum) {
		this.currentNum = currentNum;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
}
