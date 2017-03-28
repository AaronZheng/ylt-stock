package com.common.ylt.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class NetHandler {
	
	
	/**
	 * @param URL
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String getInformationFromInternet(String URL) throws IOException{
		URL urlGet = new URL(URL);
		HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
		http.setRequestMethod("POST"); 
		http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		http.setDoOutput(true);
		http.setDoInput(true);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");
		http.connect();
		InputStream is = null;
		ByteOutputStream bos = null;
		try{
			is = http.getInputStream();
			byte[] _b = new byte[1024];
			int l = 0 ;
		    bos = new ByteOutputStream();
			while((l= is.read(_b))>0){
				bos.write(_b, 0, l);
				l = 0 ;
			}
			return new String(bos.toByteArray(), "UTF-8");
		}finally{
			if(is != null)
				is.close();
			if(bos != null)
				bos.close();
		}
	}
	
	/**
	 * 
	 * @param URL
	 * @return
	 * @throws IOException
	 */
	public static void StoreInformationFromInternet(String URL,String filePath) throws IOException{
		URL urlGet = new URL(URL);
		HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
		http.setRequestMethod("GET"); 
		http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		http.setDoOutput(true);
		http.setDoInput(true);
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
		System.setProperty("sun.net.client.defaultReadTimeout", "30000");
		http.connect();
		File file = new File(filePath);
		if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
		InputStream is = null; OutputStream out = null;
		try{
			is = http.getInputStream();
			byte[] _b = new byte[1024];
			int l = 0 ; out = new FileOutputStream(file);
			while((l= is.read(_b))>0){
				out.write(_b, 0, l);
				l = 0 ;
			}
		}finally{
			if(is != null)
				is.close();
			if(out != null)
				out.close();
		}
	}
	
	
	
	/**
	 * 
	 * @param url
	 * @param message
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public static String sendMessageToInternet(String url,String message) throws IOException{
		InputStream in =null;  OutputStream out = null;
		try{
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			out = http.getOutputStream();
			out.write(message.getBytes("UTF-8"));
			in = http.getInputStream();
			ByteOutputStream bos = new ByteOutputStream();
			byte[] b = new byte[1024];
			int l;
			while((l=in.read(b))>0){
				bos.write(b,0,l);
			}
			bos.close();
			return new String(bos.toByteArray(),"UTF-8");
		}finally{
			if(in != null)
				in.close();
			if(out != null)
				out.close();
		}
	}
	
	
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	public static String sendMessageToInternet(String url) throws IOException{
		InputStream in =null;
		try{
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			in = http.getInputStream();
			String encode = "UTF-8";
			String content = http.getHeaderField("Content-Type");
			if(StringUtils.isNotBlank(content)){
				for(String charset : content.split(";")){
					if(charset.indexOf("charset") < 0)
						continue;
					String cst[] = charset.split("=");
					if(cst.length > 1){
						encode = cst[1].trim();
					}
				}
			}
			ByteOutputStream bos = new ByteOutputStream();
			byte[] b = new byte[1024];
			int l;
			while((l=in.read(b))>0){
				bos.write(b,0,l);
			}
			bos.close();
			return new String(bos.toByteArray(),encode);
		}finally{
			if(in != null)
				in.close();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println(sendMessageToInternet("http://hq.sinajs.cn/list=sh600111"));
		
	}
	
	
	

}
