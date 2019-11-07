package com.sinosoft.ms.utils.db;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sinosoft.ms.utils.FileUtil;
/**
 * 数据库帮助基类
 * @className CustomSQLiteOpenHelper
 * @classFunction 
 * 1.构造函数中传入当前版本号version,目标绝对路径absolutePath,数据库资源文件resId
 * 2.根据resId，会将资源文件复制到absolutePath的路径下
 * 3.用户需要继承该类，并重载onCreate,onUpgrade方法
 * @author hijack
 * @createTime 4:27:39 PM May 19, 2014
 */
public class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
	private Context context;
	/** 数据库绝对路径 **/
	private String absolutePath;
	private int newVersion;
	/** 数据库资源文件 **/
	private int resId;

	/**
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version		最新版本号
	 * @param absolutePath	目标绝对路径
	 * @param resId			数据库资源文件
	 */
	public CustomSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version,String absolutePath,int resId) {
		super(context, name, factory, version);
		this.context = context;
		this.absolutePath = absolutePath;
		this.resId = resId;
		this.newVersion = version;
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}
	
	@Override
	public SQLiteDatabase getReadableDatabase() {
		//获取数据库
		SQLiteDatabase db =  openDatabase(context, absolutePath, resId);
		int version = db.getVersion();
		if(version == 0){
			onCreate(db);
			db.setVersion(newVersion);
		}else if(version != newVersion){
			onUpgrade(db, version, newVersion);
			db.setVersion(newVersion);
		}
		return db;
	}
	
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		System.out.println("--------");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int version, int newVersion) {
		System.out.println("--------");
	}
	
	/**
	 * 复制资源文件中得数据库到指定目录中
	 * @param context
	 * @param absolutePath
	 * @param rawResId
	 */
	protected void copyFromRaw(Context context,String absolutePath,int rawResId){
		InputStream is = null;
		try {
			is = context.getResources().openRawResource(
					rawResId);
			String str[] = FileUtil.splitPath(absolutePath);
			FileUtil.write2SDFromInput(str[0],str[1], is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * openDatabase
	 * @param context
	 * @param absolutePath	destPath of the DB file
	 * @param rawResId		resource id of the DB file
	 * @return
	 */
	public synchronized SQLiteDatabase openDatabase(Context context,String absolutePath,int rawResId){
		SQLiteDatabase database = null;
		if (!FileUtil.isFileExist(absolutePath)) {
			Log.i("CustomSQLiteOpenHelper", "delete database");
			copyFromRaw(context,absolutePath,rawResId);
		}
		if(FileUtil.isFileExist(absolutePath)){
			database = SQLiteDatabase.openOrCreateDatabase(absolutePath, null);
		}
		return database;
	}
	
	public void deleteDatabase(Context context,String absolutePath){
		FileUtil.deleteFile(absolutePath);
	}
	

}
