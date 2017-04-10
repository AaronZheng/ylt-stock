package com.spider.ylt.model;

import java.io.Serializable;
import java.util.Date;

public class StockInfo implements Serializable{


	private static final long serialVersionUID = 1L;

	  private String stockCode;
	  private String stockName;
	  private String blockName;
	  private String blockId;
	  private String zxbs;
	  private String stockOrgCode;
	  private String stockOrgName;
	  private Date lrrq;
	  private Date xgrq;
	  private String yxbj;
	  
	  
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public String getZxbs() {
		return zxbs;
	}
	public void setZxbs(String zxbs) {
		this.zxbs = zxbs;
	}
	public String getStockOrgCode() {
		return stockOrgCode;
	}
	public void setStockOrgCode(String stockOrgCode) {
		this.stockOrgCode = stockOrgCode;
	}
	public String getStockOrgName() {
		return stockOrgName;
	}
	public void setStockOrgName(String stockOrgName) {
		this.stockOrgName = stockOrgName;
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
