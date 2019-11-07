package com.sinosoft.ms.activity;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.provider.SyncStateContract.Constants;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.service.impl.HeartbeatService;
import com.sinosoft.ms.service.impl.LoginService;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.download.DownloadManager;
import com.sinosoft.ms.utils.download.DownloadManager.DownLoadCallback;
import com.sinosoft.ms.utils.net.Network;

/**
 * 系统名：移动查勘定损 子系统名：主程序入口 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Mon Nov 19 14:49:28 CST 2012
 */
public class MainActivity extends Activity
{

	private boolean isTouch = false;
	Dialog dialog = null;

	private String networkTips;
	private boolean isNet = false;// 是否有可用连接网络
	private WifiManager wifiManager;
	private HeartbeatService heart = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.WEEK_OF_MONTH, 1);
		SystemClock.setCurrentTimeMillis(calendar.getTimeInMillis());

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		String string = "手机屏幕分辨率为：" + displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;

		// 设置屏幕亮度为最亮
		// setScreenBrightness();

		// 版本更新
		checkVersion();
	}

	public static String getVersion(Context context)
	{
		PackageManager packageManager;
		PackageInfo packInfo = null;
		try
		{
			// 获取packagemanager的实例
			packageManager = context.getPackageManager();
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		}
		catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return packInfo.versionName;
	}

	/** 选择网络类型 **/
	private void connectionSelected()
	{
		//System.out.println("wifiManager.isWifiEnabled() = " + wifiManager.isWifiEnabled());
		if (wifiManager.isWifiEnabled())
		{// 是否
			
			loadNet();
		}
		else if (!isNet && Network.is3GEnabled(MainActivity.this))
		{
			
			loadNet();
		}
		if (!isNet)
		{
			throw new IllegalArgumentException("请确认网络连接是否打开或后台支撑系统是否正常！");
		}
	}

	/**
	 * 加载网络
	 * 
	 * @throws Exception
	 * 
	 */
	private void loadNet()
	{
		try
		{
			if (null == heart)
			{
				heart = new HeartbeatService();
			}
			// ----------检查WIFI是否可以联通-----------------------------//
			AppConstants.info = AppConstants.getNetInfo(AppConstants.NETWORK_TYPE.WIFI);
			if (heart.checkNetwork2())
			{
				networkTips = "访问模式：内网";
				isNet = true;
				return;
			}
			// ------------检查APN卡是否可以联通----------------------------//
			AppConstants.info = AppConstants.getNetInfo(AppConstants.NETWORK_TYPE.APN);// add
																						// by
																						// lidaogang
			if (heart.checkNetwork2())
			{
				networkTips = "访问模式：APN";
				isNet = true;
				return;
			}
			// ------------检查3G卡是否可以联通-----------------------------//
			AppConstants.info = AppConstants.getNetInfo(AppConstants.NETWORK_TYPE.THREE_G);// add
																							// by
																							// lidaogang
			if (heart.checkNetwork2())
			{
				networkTips = "访问模式：互联网";
				isNet = true;
				return;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private Handler versionHandler = new Handler()
	{

		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:

				dialog = CustomDialog.show(MainActivity.this, "信息提示", msg.obj + "", "重试", "退出", new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						checkVersion();
						dialog.dismiss();
					}

				}, new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						System.exit(0);
						dialog.dismiss();
					}

				});

				break;
			// 版本更新
			case 2:
				List<AppInfoBean> apps = (List<AppInfoBean>) msg.obj;
				if (apps != null && !apps.isEmpty())
				{
					
					AppInfoBean app = apps.get(0);
					
					if (getVersion(MainActivity.this).equals(app.getVersion()))
					{
						// 跳转至登陆界面
						Intent intent = new Intent();
						intent.setClass(MainActivity.this, LoginAct.class);
						intent.putExtra("networkTips", networkTips); // 网络地址信息
						startActivity(intent);
						finish();
					}
					else
					{
						/** 检测版本是否需要更新 **/
						
						String url = app.getAddress();
						
						if ("1".equals(app.getForce()))
						{ // 强制更新
							DownloadManager downloadManager = new DownloadManager(MainActivity.this, url, app.getVersion(), app.getRemark());
							downloadManager.checkUpdateInfo(app.getForce(), new MyDownLoadListener());
						}
						else
						{ // 非强制更新
							
							DownloadManager downloadManager = new DownloadManager(MainActivity.this, url, app.getVersion(), app.getRemark());
							downloadManager.checkUpdateInfo("0", new MyDownLoadListener());
						}
					}
				}
				break;
			}
		};
	};

	private class MyDownLoadListener implements DownLoadCallback
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sinosoft.ms.utils.download.DownloadManager.DownLoadCallback#
		 * uploadLater()
		 */
		@Override
		public void uploadLater()
		{
			// 跳转至登陆界面
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, LoginAct.class);
			intent.putExtra("networkTips", networkTips); // 网络地址信息
			startActivity(intent);
			finish();
		}

	}

	// 版本更新
	public void checkVersion()
	{

		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				LoginService versionService = new LoginService();
				List<AppInfoBean> apps = null;
				try
				{

					connectionSelected(); // 选择网络类型

					apps = versionService.checkVersion();

					msg.what = 2;
					msg.obj = apps;
				}
				catch (Exception e)
				{
					msg.what = 0;
					msg.obj = e.getMessage();
					e.printStackTrace();
				}
				finally
				{
					versionHandler.sendMessage(msg);
				}
			};
		}.start();
	}

	/**
	 * 设置屏幕亮度为最亮
	 */
	private void setScreenBrightness()
	{
		// 是否启用自动调节亮度
		if (isAutoBrightness(this.getContentResolver()))
		{
			// 停止自动亮度调节
			Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		}
		// 设置屏幕亮度
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.screenBrightness = Float.valueOf(255) * (1f / 255f);
		getWindow().setAttributes(lp);
		// 保存亮度使用其生效
		saveBrightness(this.getContentResolver(), 255);
	}

	/**
	 * 判断是否开启了自动亮度调节
	 * 
	 * @param aContext
	 * @return
	 */
	public static boolean isAutoBrightness(ContentResolver aContentResolver)
	{
		boolean automicBrightness = false;
		try
		{
			automicBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
		}
		catch (SettingNotFoundException e)
		{
			e.printStackTrace();
		}
		return automicBrightness;
	}

	/**
	 * 保存亮度设置状态
	 * 
	 * @param resolver
	 * @param brightness
	 */
	public static void saveBrightness(ContentResolver resolver, int brightness)
	{
		Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
		android.provider.Settings.System.putInt(resolver, "screen_brightness", brightness);
		// resolver.registerContentObserver(uri, true, myContentObserver);
		resolver.notifyChange(uri, null);
	}

	/**
	 * 停止自动亮度调节
	 * 
	 * @param activity
	 */
	public static void stopAutoBrightness(Activity activity)
	{
		Settings.System.putInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// isTouch=true;
	// handler.removeCallbacks(runable);
	// Intent intent = new Intent();
	// intent.setClass(MainActivity.this, LoginAct.class);
	// startActivity(intent);
	// finish();
	// return super.onTouchEvent(event);
	// }

}
