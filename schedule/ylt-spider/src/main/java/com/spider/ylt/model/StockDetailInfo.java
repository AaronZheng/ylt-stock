package com.spider.ylt.model;

import java.io.Serializable;
import java.util.Date;

public class StockDetailInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String stockCode;
	private Integer flowStockNum;
	private double flowStockPrice;
	private Integer totalStockNum;
	private double totalStockPrice;
	private Date lrrq;
	private Date xgrq;
	private String yxbj;
	  
	  
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public Integer getFlowStockNum() {
		return flowStockNum;
	}
	public void setFlowStockNum(Integer flowStockNum) {
		this.flowStockNum = flowStockNum;
	}
	public double getFlowStockPrice() {
		return flowStockPrice;
	}
	public void setFlowStockPrice(double flowStockPrice) {
		this.flowStockPrice = flowStockPrice;
	}
	public Integer getTotalStockNum() {
		return totalStockNum;
	}
	public void setTotalStockNum(Integer totalStockNum) {
		this.totalStockNum = totalStockNum;
	}
	public double getTotalStockPrice() {
		return totalStockPrice;
	}
	public void setTotalStockPrice(double totalStockPrice) {
		this.totalStockPrice = totalStockPrice;
	}
	public Date getLrrq() {
		return lrrq;
	}
	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}
	public Date getXgrq() {
		return xgrq;
	}
	public void setXgrq(Date xgrq) {
		this.xgrq = xgrq;
	}
	public String getYxbj() {
		return yxbj;
	}
	public void setYxbj(String yxbj) {
		this.yxbj = yxbj;
	}

}
