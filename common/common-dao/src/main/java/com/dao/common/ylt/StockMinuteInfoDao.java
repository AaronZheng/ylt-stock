package com.dao.common.ylt;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.ylt.model.LowManyNumInfo;

public interface StockMinuteInfoDao {
	
	
	/**
	 * 统计地位放量
	 * @param startPrice
	 * @param endPrice
	 * @param numType
	 * @param rangeType
	 * @param variance
	 * @param times
	 * @param startTime
	 * @param calcDate
	 * @return
	 */
	List<LowManyNumInfo> analysisAlwaysLowManyNum(@Param("lowPrice")double lowPrice,@Param("hightPrice")double hightPrice,
			@Param("stockType")int stockType,@Param("variance")double variance,@Param("times")int times,@Param("startTime")Date startTime,@Param("calcDate")Date calcDate);
	
	
	/**
	 * 震荡放量
	 * @param lowPrice
	 * @param hightPrice
	 * @param stockType
	 * @param variance
	 * @param times
	 * @param startTime
	 * @param calcDate
	 * @return
	 */
	List<LowManyNumInfo> analysisUpAndDownManyNum(@Param("lowPrice")double lowPrice,@Param("hightPrice")double hightPrice,
			@Param("stockType")int stockType,@Param("variance")double variance,@Param("times")int times,@Param("startTime")Date startTime,@Param("calcDate")Date calcDate);
	
	

}
