package com.common.ylt.util;

import java.util.UUID;

public class Sequeuce {
	
	
	
	public static String genereateRandomStr(){
		
		return UUID.randomUUID().toString().replace("-", "");
		
	}
	
	

}
