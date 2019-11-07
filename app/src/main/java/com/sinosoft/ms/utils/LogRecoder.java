package com.sinosoft.ms.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

/**
 * 系统名：移动查勘定损 
 * 子系统名：日志记录
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午1:13:49
 */

public class LogRecoder {
   private static String errorPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/mobileSurvey/error/";
   
   public static void writeError(String error){
	   writeError("LOG",error);
   }
   
   public static void writeError(String type,String error){
	   try {
		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//以天为日志单位
		 String fileName=formatter.format(new Date())+"-error.log";
		 File dir=new File(errorPath);
		 if(!dir.exists()){
			dir.mkdirs();
		 }
		 RandomAccessFile file = new RandomAccessFile(errorPath+fileName,"rw");
		 long currentLength=file.length();
		 file.seek(currentLength);
		 DateFormat timerFormater = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		 String time=timerFormater.format(new Date());
		 file.write(("["+type+"] "+time+": "+error+"\r\n").getBytes());
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}  
   }
   
   public static void wirteException(Exception e){
	   wirteException("EXP",e);
   }
   public static void wirteException(String type,Exception e){
	   if(e!=null){
		 PrintWriter writer=null;
		 DateFormat timerFormater = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		 String time=timerFormater.format(new Date());  
		 File dir=new File(errorPath);
		 if(!dir.exists()){
			dir.mkdirs();
		 }
		try {
			writer = new PrintWriter(new File(errorPath+time+"-"+type+".log"));
			e.printStackTrace(writer);
		   
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}finally{
			if(writer!=null){
				 writer.close();
			}
		}
	  }  
   }
   
}

