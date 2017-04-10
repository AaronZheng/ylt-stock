package com.spider.ylt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DataCollectController {
	
	
	@RequestMapping("initPage.do")
	public String initPage(){
		return "index";
	}
	
	
	
	
	

}
