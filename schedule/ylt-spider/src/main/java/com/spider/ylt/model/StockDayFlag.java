package com.spider.ylt.model;

public class StockDayFlag {
	
	private String timeStamp;
	private Double openPlatePrice;
	private Double closePlatePrice;
	private Double yClosePlatePrice;
	private Double maxPrice;
	private Double lowPrice;
	private String dayId;
	
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Double getOpenPlatePrice() {
		return openPlatePrice;
	}
	public void setOpenPlatePrice(Double openPlatePrice) {
		this.openPlatePrice = openPlatePrice;
	}
	public Double getClosePlatePrice() {
		return closePlatePrice;
	}
	public void setClosePlatePrice(Double closePlatePrice) {
		this.closePlatePrice = closePlatePrice;
	}
	public Double getyClosePlatePrice() {
		return yClosePlatePrice;
	}
	public void setyClosePlatePrice(Double yClosePlatePrice) {
		this.yClosePlatePrice = yClosePlatePrice;
	}
	public Double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public Double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getDayId() {
		return dayId;
	}
	public void setDayId(String dayId) {
		this.dayId = dayId;
	}
	
	
	

}
