package com.common.ylt.scheduler.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  ES 配置解析 (ALPHA ELASTIC SEARCH PERSISTENCE HANDLER INTERFACE)
 * <p>alpha.jd.com
 * <p>File: IESPersistenceService.java create time:2016-10-04下午07:57:37</p>
 * <p>Title: Elastic search persistence ... </p>
 * <p>Description: elastic search persistence handler interface </p>
 * <p>Copyright: Copyright (c) 2016 alpha.jd.com</p>
 * <p>Company: alpha.jd.com</p>
 * <p>module: elastic search data handler </p>
 * @author zhengchenglei
 * @version 1.0
 * @history 修订历史（历次修订内容、修订人、修订时间等）
 */
public abstract class AbsESConfigFileHandler<T,E> extends DefaultHandler{

	protected String preTag;

	/**
	  * @description parser document
	  * 
	  * @param in
	  * 
	  * @throws Exception
	  */
	 protected void setParseDocumentStream(InputStream in) throws Exception{
		 
		 try{
			 SAXParserFactory factory = SAXParserFactory.newInstance();  
			 SAXParser parser = factory.newSAXParser();  
			 parser.parse(in, this);
		 }catch(Exception e){
			 throw new Exception("Parse configuration file exception!",e);
		 }
	 }
	 
	 
	 /**
	  * @description 文档处理
	  * 
	  * @param file
	  * 
	  * @throws Exception
	  */
	 protected void setParseDocumentFile(File file)throws Exception{
		 try{
		   setParseDocumentStream(new FileInputStream(file));
		 }catch(IOException e){
			 throw new Exception("Open file stream exception, Please check whether the file is used or not exist!",e);
		 }
	 }
	
	 protected List<E> configs;
	 
	 protected List<T> entitys;
	 
	 protected T entity;
	 
	 
	 /**
	  * @param e
	  * 
	  * @return
	  * 
	  * @throws Exception
	  */
	 public abstract T processor(E e) throws Exception;
	 
	 
	 /**
	  * @throws Exception
	  */
	 public void handler() throws Exception{
		   if(configs == null|| configs.size()<1)
			   return;
		
		   for(E config:configs){
			   processor(config);
		   }
	 }

	/**
	 * @description Setting configuration file information
	 * 
	 * @param configs
	 * 
	 * @return
	 */
	public AbsESConfigFileHandler<T,E> setConfigFiles(List<E> configs) {
	
		this.configs = configs;
		return this;
	}
	
	
	/**
	 * @description Collecting result information
	 * 
	 * @param entitys
	 * 
	 * @return
	 */
	public AbsESConfigFileHandler<T,E> setEntitys(List<T> entitys){
		this.entitys = entitys;
		return this;
	}
	
	/**
	 * @return
	 */
	public List<T> getEntitys(){
		return this.entitys;
	}

	public T getEntity() {
		return entity;
	}

	public AbsESConfigFileHandler<T,E> setEntity(T entity) {
		this.entity = entity;
		return this;
	}
	 
}
