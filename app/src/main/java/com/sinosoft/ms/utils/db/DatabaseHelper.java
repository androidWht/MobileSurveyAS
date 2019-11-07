package com.sinosoft.ms.utils.db;

import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sinosoft.ms.R;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.DBConstants;
import com.sinosoft.ms.utils.UserCashe;

/**
 * 系统名：MobileSurvey 子系统名：数据库帮助类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:13:00 CST 2013
 */
public class DatabaseHelper extends CustomSQLiteOpenHelper{
	/** resource of the database file **/
	private static final int RAW_RES_ID = R.raw.mobile_survey;
	
	/** database name **/
	private static final String DB_NAME = "mobile_survey.db";
	
	/** database version **/
	private static final int DATABASE_VERSION = 3;
	
	private static DatabaseHelper instance;
	private Context context;

	private DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION,"",RAW_RES_ID);
		this.context = context;
		//设置数据库目标地址
		//1.获取用户名
		UserCashe userCase=UserCashe.getInstant();
		String dbName=userCase.getUser().getName();
		//2.设置目标地址
		String databaseFilename = AppConstants.PATH+dbName+ DBConstants.DB_NAME + ".db";
		setAbsolutePath(databaseFilename);
	}

	public static DatabaseHelper getHelper(Context context) {
		if (instance == null)
			instance = new DatabaseHelper(context);

		return instance;
	}
	
	public synchronized SQLiteDatabase openDatabase(){
		return getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		super.onCreate(arg0);
		updateDatabase(arg0, 0, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int version, int newVersion) {
		super.onUpgrade(arg0, version, newVersion);
		//更新操作
		updateDatabase(arg0, version, newVersion);
	}
	
	private void updateDatabase(SQLiteDatabase arg0,int version,int newVersion){
		//更新操作
		List<String> list = AppConstants.getUpdateSql(version,newVersion);
		for(String sql : list){
			try {
				arg0.execSQL(sql);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
	}
	
	public void clear(){
		instance = null;
	}
	
	
//	
//
//	/** 共享数据库 */
//	private static SQLiteDatabase DB;
//
//	/**
//	 * 打开数据库
//	 * 
//	 * @param context
//	 *            上下文配置
//	 * @return SQLite数据库
//	 * @throws Exception
//	 */
//	public static synchronized SQLiteDatabase openDatabase(Context context)
//			throws Exception {
//		InputStream is = null;
//		FileOutputStream fos = null;
//		FileUtils fileUtils = new FileUtils();
//
//		if (null == fileUtils.getSDPATH()) {// 未获取到SD卡路径
//			throw new Exception(AppConstants.DB_ERROR);
//		}
//		try {
//			UserCashe userCase=UserCashe.getInstant();
//			String dbName=userCase.getUser().getName();
//			
//			if (null == DBConstants.DB_NAME || "".equals(DBConstants.DB_NAME)) {
//				DBConstants.DB_NAME = SharedPreUtils.getSharePre(context,
//						"tobaccoShareData", "initload");
//			}
//			
//			String databaseFilename = fileUtils.getSDPATH()+dbName+
//					 DBConstants.DB_NAME + ".db";
//			if (!(new File(databaseFilename)).exists()) {
//				// 数据库未找到
//				File file_original = new File(databaseFilename);
//				if (file_original.exists()) {
//					// 将旧的数据库命名为新数据库
//					File file = new File(databaseFilename);
//					if (!file_original.renameTo(file)) {
//						throw new Exception(AppConstants.DB_ERROR);
//					}
//				} else {
//					// 将项目根目录下的数据库拷贝到SD卡中
//					is = context.getResources().openRawResource(
//							R.raw.mobile_survey);
//					
//					fileUtils.write2SDFromInput("",
//							dbName+DBConstants.DB_NAME + ".db", is);
//					is.close();
//				}
//			}
//			
//			if (DB != null)
//				if (DB.isOpen()) {
//					int i = 0;
//					while (DB.isDbLockedByCurrentThread()
//							|| DB.isDbLockedByOtherThreads()) {
//						if (i > 0)
//							throw new Exception(AppConstants.DB_ERROR);
//						i++;
//						Thread.sleep(1000);
//					}
//				}
//			DB = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
//			return DB;
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (null != is) {
//					is.close();
//				}
//				if (null != fos) {
//					fos.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		throw new Exception(AppConstants.DB_ERROR);
//	}
//	
//	/**
//	 * 打开数据库
//	 * @throws Exception 
//	 */
//	public static synchronized SQLiteDatabase openDB(Context context) throws Exception {
//		InputStream is = null;
//		FileOutputStream fos = null;
//		FileUtils utils = new FileUtils();
//		UserCashe userCase = UserCashe.getInstant();
//		String dbName=userCase.getUser().getName();
//		String databaseFilename = utils.getSDPATH() +
//				dbName + DBConstants.DB_NAME + ".db";
//		try {
//			//检查存储设备
//			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//				throw new IllegalArgumentException(AppConstants.SDCARD_NOT_FIND);
//			}
//			utils.creatSDDir("");
//			File file = new File(databaseFilename);
//			if (!file.exists()) {// 如果数据库文件不存在则将项目根目录下的数据库拷贝到SD卡中
//				is = context.getResources().openRawResource(R.raw.mobile_survey);
//				utils.write2SDFromInput("",
//						dbName + AppConstants.DB_NAME + ".db", is);
//			}
//			if (DB != null && DB.isOpen()){
//				int i = 0;
//				while (DB.isDbLockedByCurrentThread()
//						|| DB.isDbLockedByOtherThreads()) {
//					if (i > 0)
//						throw new Exception(AppConstants.DB_NOT_FIND);
//					i++;
//					Thread.sleep(1000);
//				}
//			}
//			DB = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			utils.close(is,fos);
//		}
//		return DB;
//	}
//	
//	public static void deleteDB(Context context) {
//		FileUtils utils = new FileUtils();
//		UserCashe userCase=UserCashe.getInstant();
//		String dbName=userCase.getUser().getName();
//		String databaseFilename = utils.getSDPATH();
//		File dir = new File(databaseFilename);
//		if(dir.exists()){
//			File files[] = dir.listFiles();
//			for(File f : files){
//				if(f.getName().contains(DBConstants.DB_NAME + ".db")){
//					f.delete();
//				}
//			}
//		}
//	}
}
