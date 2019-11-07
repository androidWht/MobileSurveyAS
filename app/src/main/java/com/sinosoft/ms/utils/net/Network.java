package com.sinosoft.ms.utils.net;

import java.net.URL;

import org.apache.commons.httpclient.util.HttpURLConnection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

/**
 * 系统名：移动查勘定损 子系统名：网络工具类 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 09:54:00 CST 2013
 */
public class Network {

	/**
	 * 判断当前网络状态是否连接
	 * 
	 * @param context
	 *            应用上下文
	 * @return true有可用连接 false无可用连接
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		if (manager == null) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * 测试ConnectivityManager ConnectivityManager主要管理和网络连接相关的操作
	 * 相关的TelephonyManager则管理和手机、运营商等的相关信息；WifiManager则管理和wifi相关的信息。
	 * 想访问网络状态，首先得添加权限<uses-permission
	 * android:name="android.permission.ACCESS_NETWORK_STATE"/>
	 * NetworkInfo类包含了对wifi和mobile两种网络模式连接的详细描述,通过其getState()方法获取的State对象则代表着
	 * 连接成功与否等状态。
	 * 
	 */
	public void testConnectivityManager(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		// 获取当前的网络连接是否可用
		boolean available = networkInfo.isAvailable();
		if (available) {
			Log.i("通知", "当前的网络连接可用");
		} else {
			Log.i("通知", "当前的网络连接可用");
		}

		State state = connManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		if (State.CONNECTED == state) {
			Log.i("通知", "GPRS网络已连接");
		}

		state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (State.CONNECTED == state) {
			Log.i("通知", "WIFI网络已连接");
		}

		// 跳转到无线网络设置界面
		context.startActivity(new Intent(
				android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		// 跳转到无限wifi网络设置界面
		context.startActivity(new Intent(
				android.provider.Settings.ACTION_WIFI_SETTINGS));

	}

	// WIFI是否可用
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	// 3G是否链接
	public static boolean is3GConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null
				&& activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	// URL是否连通
	public boolean exists(String URLName) {
		boolean flag = false;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(URLName)
					.openConnection();
			int code = conn.getResponseCode();
			if (code != 200) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断移动网络是否开启
	 * 
	 * @return
	 */
	public static boolean is3GEnabled(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null
				&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}
}
