package com.spider.ylt.model;

import java.io.Serializable;
import java.util.Date;

public class StockMinuteInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String stockCode;
	private double stockPrice;
	private double buyOnePrice;
	private double buyTowPrice;
	private double buyThreePrice;
	private double buyFourPrice;
	private double buyFivePrice;
	private Integer buyOneNum;
	private Integer buyTowNum;
	private Integer buyThreeNum;
	private Integer buyFourNum;
	private Integer buyFiveNum;
	private double saleOnePrice;
	private double saleTowPrice;
	private double saleThreePrice;
	private double saleFourPrice;
	private double saleFivePrice;
	private Integer saleOneNum;
	private Integer saleTowNum;
	private Integer saleThreeNum;
	private Integer saleFourNum;
	private Integer saleFiveNum;
	private Integer tradeNum;
	private Long tradePrice;
	private String tradeTime;
	private Date lrrq;
	private Date xgrq;
	private String yxbj;
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public double getStockPrice() {
		return stockPrice;
	}
	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}
	public double getBuyOnePrice() {
		return buyOnePrice;
	}
	public void setBuyOnePrice(double buyOnePrice) {
		this.buyOnePrice = buyOnePrice;
	}
	public double getBuyTowPrice() {
		return buyTowPrice;
	}
	public void setBuyTowPrice(double buyTowPrice) {
		this.buyTowPrice = buyTowPrice;
	}
	public double getBuyThreePrice() {
		return buyThreePrice;
	}
	public void setBuyThreePrice(double buyThreePrice) {
		this.buyThreePrice = buyThreePrice;
	}
	public double getBuyFourPrice() {
		return buyFourPrice;
	}
	public void setBuyFourPrice(double buyFourPrice) {
		this.buyFourPrice = buyFourPrice;
	}
	public double getBuyFivePrice() {
		return buyFivePrice;
	}
	public void setBuyFivePrice(double buyFivePrice) {
		this.buyFivePrice = buyFivePrice;
	}
	public Integer getBuyOneNum() {
		return buyOneNum;
	}
	public void setBuyOneNum(Integer buyOneNum) {
		this.buyOneNum = buyOneNum;
	}
	public Integer getBuyTowNum() {
		return buyTowNum;
	}
	public void setBuyTowNum(Integer buyTowNum) {
		this.buyTowNum = buyTowNum;
	}
	public Integer getBuyThreeNum() {
		return buyThreeNum;
	}
	public void setBuyThreeNum(Integer buyThreeNum) {
		this.buyThreeNum = buyThreeNum;
	}
	public Integer getBuyFourNum() {
		return buyFourNum;
	}
	public void setBuyFourNum(Integer buyFourNum) {
		this.buyFourNum = buyFourNum;
	}
	public Integer getBuyFiveNum() {
		return buyFiveNum;
	}
	public void setBuyFiveNum(Integer buyFiveNum) {
		this.buyFiveNum = buyFiveNum;
	}
	public double getSaleOnePrice() {
		return saleOnePrice;
	}
	public void setSaleOnePrice(double saleOnePrice) {
		this.saleOnePrice = saleOnePrice;
	}
	public double getSaleTowPrice() {
		return saleTowPrice;
	}
	public void setSaleTowPrice(double saleTowPrice) {
		this.saleTowPrice = saleTowPrice;
	}
	public double getSaleThreePrice() {
		return saleThreePrice;
	}
	public void setSaleThreePrice(double saleThreePrice) {
		this.saleThreePrice = saleThreePrice;
	}
	public double getSaleFourPrice() {
		return saleFourPrice;
	}
	public void setSaleFourPrice(double saleFourPrice) {
		this.saleFourPrice = saleFourPrice;
	}
	public double getSaleFivePrice() {
		return saleFivePrice;
	}
	public void setSaleFivePrice(double saleFivePrice) {
		this.saleFivePrice = saleFivePrice;
	}
	public Integer getSaleOneNum() {
		return saleOneNum;
	}
	public void setSaleOneNum(Integer saleOneNum) {
		this.saleOneNum = saleOneNum;
	}
	public Integer getSaleTowNum() {
		return saleTowNum;
	}
	public void setSaleTowNum(Integer saleTowNum) {
		this.saleTowNum = saleTowNum;
	}
	public Integer getSaleThreeNum() {
		return saleThreeNum;
	}
	public void setSaleThreeNum(Integer saleThreeNum) {
		this.saleThreeNum = saleThreeNum;
	}
	public Integer getSaleFourNum() {
		return saleFourNum;
	}
	public void setSaleFourNum(Integer saleFourNum) {
		this.saleFourNum = saleFourNum;
	}
	public Integer getSaleFiveNum() {
		return saleFiveNum;
	}
	public void setSaleFiveNum(Integer saleFiveNum) {
		this.saleFiveNum = saleFiveNum;
	}
	public Integer getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(Integer tradeNum) {
		this.tradeNum = tradeNum;
	}

	
	public Long getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(Long tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
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
