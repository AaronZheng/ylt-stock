package com.view.ylt.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.ylt.model.LowManyNumInfo;
import com.common.ylt.model.YLTResponse;
import com.view.ylt.service.LowManyNumSerivce;

@Controller
@RequestMapping("/lowManyNum")
public class LowManyNumController {
	
	
	private LowManyNumSerivce lowManyNumService;
	
	private ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>(){

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		@Override
		public SimpleDateFormat get() {
			return format;
		}
	};
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/initPage.do")
	public ModelAndView initPage(){
		
		ModelAndView mv = new ModelAndView("/lowMuchNum");
		return mv;
	}
	
	
	@RequestMapping("queryLowManyNum.json")
	@ResponseBody
	public YLTResponse<?> queryLowManyNum(@RequestParam("lowPrice")double lowPrice,@RequestParam("hightPrice")double hightPrice,
                                     			 @RequestParam("numType")int numType,@RequestParam("stockType")int stockType,@RequestParam("variance")double variance,
			                                     @RequestParam("times")int times,@RequestParam("startTime")String startTime,@RequestParam("calcDate")String calcDate){
		
		try{
			List<LowManyNumInfo> lowManyNumInfos = lowManyNumService.queryLowManyNumData(lowPrice, hightPrice, numType, stockType, variance, times, 
					format.get().parse(startTime), format.get().parse(calcDate));
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("total", lowManyNumInfos == null ? 0 : lowManyNumInfos.size());
			map.put("rows", lowManyNumInfos);
			return YLTResponse.successResponse(map);
		}catch(Exception e){
			logger.error("查询地位放量异常",e);
			return YLTResponse.errorResponse(e.getMessage());
		}
	}

}
