package ch;

import java.util.Calendar;

public class TT {
	
	volatile int a = 4;
	
	public void userInfo(int b){
		
		b = b +4;
		
	}
	
	public static void main(String[] args) {
		TT tt= new TT();
		tt.userInfo(tt.a);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		System.out.println(calendar.getTimeInMillis() - System.currentTimeMillis());
		
		
		
	}
	
	

}
