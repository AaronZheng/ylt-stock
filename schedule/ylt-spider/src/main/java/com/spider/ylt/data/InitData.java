package com.spider.ylt.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import com.common.ylt.util.Sequeuce;
import oracle.jdbc.driver.OracleDriver;

public class InitData {
	
	public static void main(String[] args) throws Exception {
		
		initMarkingInfo();
	}
	
	
	public static void initBlock() throws Exception{

		BufferedReader reader = new BufferedReader(new FileReader("e:/stock/sh.txt"));
		String temp;
		
		OracleDriver.class.newInstance();
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "ylt", "ylt");
		PreparedStatement state = conn.prepareStatement("insert into stock_marking(stock_code,stock_name,v1) values(?,?,?)");
		
		while((temp = reader.readLine())!= null){
			String[] stockAll = temp.split(" ");
			for(int i = 0 ; i<stockAll.length; i++){
				state.setString(1, Sequeuce.genereateRandomStr());
				String[] stockspilt = stockAll[i].replaceAll("\\)", "").split("\\(");
				state.setString(2, stockspilt[1]);
				state.setString(3, stockspilt[0]);
				state.setString(4, "sh");
				state.setDate(5, new Date(System.currentTimeMillis()));
				state.setString(6, "1");
				state.execute();
			}
			
		}
		state.close();
		conn.close();
		reader.close();
	}
	
	
	
	
	public static void initMarkingInfo() throws Exception{
		
		BufferedReader reader = new BufferedReader(new FileReader("e:/stock/XSB.txt"));
		String temp;
		
		OracleDriver.class.newInstance();
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "ylt_p", "ylt_p");
		PreparedStatement state = conn.prepareStatement("insert into stock_marking(stock_code,stock_name,v_1) values(?,?,?)");
		
		while((temp = reader.readLine())!= null){
			String[] stockAll = temp.split(" ");
			if(stockAll == null || stockAll.length == 0)
				continue;
			for(int i = 0 ; i<stockAll.length; i++){
				String[] stockspilt = stockAll[i].replaceAll("\\)", "").split("\\(");
				System.out.println(stockspilt[1] != null ? stockspilt[1].trim() : "");
				state.setString(1, stockspilt[1] != null ? stockspilt[1].trim() : "");
				state.setString(2, stockspilt[0] != null ? stockspilt[0].trim() : "");
				state.setString(3, "XSB");
				try{
				state.execute();
				}catch(Exception e){
					e.printStackTrace();
					throw e;
				}
			}
			
		}
		state.close();
		conn.close();
		reader.close();
	}
	

}
