package com.sinosoft.ms.utils;

import java.util.List;
import java.util.UUID;

import com.sinosoft.ms.service.impl.BackstageServices;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
/**
 * 
 * 系统名 		查勘相机系统 
 * 类名			SystemUtil.java
 * 类作用		系统工具类
 * @author wuhaijie
 * @createTime 2014-1-24 下午4:50:42
 */
public class SystemUtil {
	private static final String SYSTEMUTILS = "system_utils";
	private static final String IMEI = "imei";
	
	/**
	 * 获取手机IMEI码
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context){
		String imei = null;
		if(context != null){
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		}
		if(imei == null){
			SharedPreferences sp = context.getSharedPreferences(SYSTEMUTILS, 0);
			imei = sp.getString(IMEI, "");
			if(imei.equals("")){
				imei = getShortUuid();
				sp.edit().putString(IMEI, imei).commit();
			}
		}
		return imei;     
	}
	
	/**
	 * 获取手机型号
	 * @return
	 */
	public static String getModel(){
		String model = android.os.Build.BRAND + android.os.Build.MODEL;
		if (model == null) {
			model = "android";
		}
		return model;
	}
	
	/**
	 * 获取系统SDK版本
	 * @return
	 */
	public static String getVersionRelease(){
		String release = android.os.Build.VERSION.RELEASE;
		if(release == null){
			release = "android";
		}
		return release; 
	}
	

	/**
	 * 普通UUID
	 * 
	 * @return
	 */
	public static String getUuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 去掉减号的UUID
	 * 
	 * @return
	 */
	public static String getShortUuid() {
		String uidStr = getUuid();
		uidStr = uidStr.replaceAll("-", "");
		return uidStr;
	}

	/**
	 * 获取系统版本
	 * @param context
	 * @return
	 */
	public static String getVersion(Context context) {
		PackageManager packageManager;
		PackageInfo packInfo = null;
		try {
			// 获取packagemanager的实例
			packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return packInfo.versionName;
	}
	
	/**
	 * 启动应用程序
	 * @param context
	 * @param packageName		如com.xxx.xxx
	 */
	public static void startAppByPackageName(Context context,String packageName){
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		 
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);
		 
		List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
		 
		ResolveInfo ri = apps.iterator().next();
		if (ri != null ) {
			String packageName1 = ri.activityInfo.packageName;
			String className = ri.activityInfo.name;
			 
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			 
			ComponentName cn = new ComponentName(packageName1, className);
			 
			intent.setComponent(cn);
			context.startActivity(intent);
		}
	}
	
	/**
	 * 获取可用空间
	 * @param filePath
	 * @return
	 */
	public static long getAvailableStore(String filePath) {
		// 取得sdcard文件路径
		StatFs statFs = new StatFs(filePath);
		// 获取block的SIZE
		long blocSize = statFs.getBlockSize();
		// 获取BLOCK数量
		// long totalBlocks = statFs.getBlockCount();
		// 可使用的Block的数量
		long availaBlock = statFs.getAvailableBlocks();
		// long total = totalBlocks * blocSize;
		long availableSpare = availaBlock * blocSize;
		return availableSpare;
	}
	
	public static int[] getWinSize(Context context){
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		int arr[] = new int[2];
		arr[0] = width;
		arr[1] = height;
		return arr;
	}
	
	/**
	 * 计算不同分辨率的比例值
	 * @param width
	 * @param height
	 * @return		16:9 18 | 5:3 17 | 4:3 14 | 1:1 11
	 */
	public static int resolutionRatio(int width,int height){
		return Math.round(((float)width/height)*10 + 0.5f);
	}
	
	/**
	 * 判断程序是否运行
	 * @param
	 * @param 
	 * @return		true||false
	 */
	public static boolean IsAlive(Context context){
		ActivityManager	am=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(100);
		boolean isAppRunning = false;
		String MY_PKG_NAME = "com.sinosoft.ms";
		for (RunningTaskInfo info : list) {
			Log.e(MY_PKG_NAME, info.topActivity.getPackageName()+info.baseActivity.getPackageName());
			if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
				//Log.e(MY_PKG_NAME, info.topActivity.getPackageName()+info.baseActivity.getPackageName());
				return true;
				
				
			}
		}
		
		
		
		return false;
	}

}
