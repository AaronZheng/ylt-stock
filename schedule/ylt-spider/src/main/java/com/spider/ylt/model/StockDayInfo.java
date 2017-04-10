package com.spider.ylt.model;

import java.io.Serializable;
import java.util.Date;

public class StockDayInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	  
	private String stockCode;
	private double  openPlatePrice;
	private double closePlatePrice;
	private double yClosePlatePrice;
	private double maxPrice;
	private double lowPrice;
	private double dealStockNum;
	private double dealStockPrice;
	private String kPicture;
	private String tPicture;
	private Date lrrq;
	private Date xgrq;
	private String yxbj;
	
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public double getOpenPlatePrice() {
		return openPlatePrice;
	}
	public void setOpenPlatePrice(double openPlatePrice) {
		this.openPlatePrice = openPlatePrice;
	}
	public double getClosePlatePrice() {
		return closePlatePrice;
	}
	public void setClosePlatePrice(double closePlatePrice) {
		this.closePlatePrice = closePlatePrice;
	}
	public double getyClosePlatePrice() {
		return yClosePlatePrice;
	}
	public void setyClosePlatePrice(double yClosePlatePrice) {
		this.yClosePlatePrice = yClosePlatePrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getDealStockNum() {
		return dealStockNum;
	}
	public void setDealStockNum(double dealStockNum) {
		this.dealStockNum = dealStockNum;
	}
	public double getDealStockPrice() {
		return dealStockPrice;
	}
	public void setDealStockPrice(double dealStockPrice) {
		this.dealStockPrice = dealStockPrice;
	}
	public String getkPicture() {
		return kPicture;
	}
	public void setkPicture(String kPicture) {
		this.kPicture = kPicture;
	}
	public String gettPicture() {
		return tPicture;
	}
	public void settPicture(String tPicture) {
		this.tPicture = tPicture;
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
