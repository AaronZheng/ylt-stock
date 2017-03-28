package com.spider.ylt.analyser.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class StockQueue {
	
	private static LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();
	
	
	public static void put(Object msg) throws InterruptedException{
		queue.offer(msg, 5, TimeUnit.SECONDS);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T>T get(Class<T> t) throws InterruptedException{
		return (T) queue.poll(5, TimeUnit.SECONDS);
	}
	
	
	

}
