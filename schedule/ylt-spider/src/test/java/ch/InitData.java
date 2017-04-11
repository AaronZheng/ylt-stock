package ch;

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
		
		BufferedReader reader = new BufferedReader(new FileReader("e:/stock/sh.txt"));
		String temp;
		
		OracleDriver.class.newInstance();
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "ylt", "ylt");
		PreparedStatement state = conn.prepareStatement("insert into stock_info(ID,STOCK_CODE,STOCK_NAME,STOCK_ORG_CODE,LRRQ,YXBJ) values(?,?,?,?,?,?)");
		
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

}
