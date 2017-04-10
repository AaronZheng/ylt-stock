package com.common.ylt.cache;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	
	
	private static Map<String,Object> cacheMap = new HashMap<String,Object>();
	
	
	public static void put(String key,Object value){
		cacheMap.put(key, value);
	}
	
	public static Object get(String key){
		
	 return cacheMap.get(key);
	}
	
	

}
