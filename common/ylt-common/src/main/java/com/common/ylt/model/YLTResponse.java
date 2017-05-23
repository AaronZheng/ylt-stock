package com.common.ylt.model;

import java.io.Serializable;

public class YLTResponse<T> implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private int code;
	
	private String msg;
	
	private T t;
	
	
	
	public static YLTResponse<?> successResponse(Object obj){
		
		YLTResponse<Object> response = new YLTResponse<Object>();
		response.setT(obj);
		response.setCode(1);
		response.setMsg("ok");
		return response;
	}
	
	
	public static YLTResponse<?> errorResponse(String msg){
		
		YLTResponse<Object> response = new YLTResponse<Object>();
		response.setCode(-1);
		response.setMsg(msg);
		return response;
	}
	
	
	
	

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	

}
