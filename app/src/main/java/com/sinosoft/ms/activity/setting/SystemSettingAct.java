package com.sinosoft.ms.activity.setting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.service.impl.LoginService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.Utility;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.download.DownloadManager;

/**
 * 系统名：移动查勘定损 子系统名：系统设置界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Administrator
 * @createTime 下午1:53:27
 */

public class SystemSettingAct extends Activity
{
	private ProgressDialog progressDialog;// 加载框
	private int errorState;// 错误标识
	private Button settingBrackBtn;
	private Button clearSystemCashBtn;
	private Button checkVersionBtn;
	private AppInfoBean app;
	private CheckBox receptionPushInfoCb;
	private RadioButton picSaveToExcCardCb, picSaveToSdcardCardCb;
	private EditText imgStorageEdit; // 图片保存天数

	private SharedPreferences sharedPerences = null;
	private InputMethodManager imm;
	private CheckBox picLoactionSaveCb; // 图片保存

	private ProgressDialogUtil progressDialogUtil;

	private Dialog dialog;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		progressDialog = null;// 加载框
		settingBrackBtn = null;
		clearSystemCashBtn = null;
		checkVersionBtn = null;
		app = null;
		receptionPushInfoCb = null;
		picSaveToExcCardCb = picSaveToSdcardCardCb = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_system_setting);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		sharedPerences = SystemSettingAct.this.getSharedPreferences("base_info", MODE_WORLD_WRITEABLE);
		// Utility.getSdcardAvail(this)
		ActivityManage.getInstance().push(this);
		// 设置视图
		setViewController();

		// 设置数据
		setDate();

		clearSystemCashBtn.setOnClickListener(new SystemSettingListener());

		checkVersionBtn.setOnClickListener(new SystemSettingListener());

		settingBrackBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				Intent intent = new Intent();
				intent.putExtra("isAcceptTask", receptionPushInfoCb.isChecked());
				setResult(1988, intent);

				String imgStorageDay = imgStorageEdit.getText().toString(); // 图片保存天数

				// 照片保存
				SharedPreferences.Editor editor = sharedPerences.edit();
				editor.putBoolean("systemMemory", picSaveToSdcardCardCb.isChecked()); // 系统内存
				editor.putBoolean("extendMemory", picSaveToExcCardCb.isChecked()); // 扩展内存
				editor.commit();

				// 是否保存图片本地
				if (picLoactionSaveCb.isChecked())
				{
					if (!"".equals(imgStorageDay) && !imgStorageDay.equals(String.valueOf(sharedPerences.getInt(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY))))
					{
						SharedPreferences.Editor edit = sharedPerences.edit();
						edit.putBoolean("isSaveLocal", true); // 保存本地图片
						edit.putString(AppConstants.IMG_STORAGE_DATE, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 保存当天日期
						edit.putInt(AppConstants.IMG_STORAGE_DAY, Integer.parseInt(imgStorageDay)); // 保存天数
						edit.commit(); // 保存数据
					}
				}
				else
				{
					SharedPreferences.Editor edit = sharedPerences.edit();
					edit.putBoolean("isSaveLocal", false); // 保存本地图片
					edit.putString(AppConstants.IMG_STORAGE_DATE, null); // 保存当天日期
					edit.putInt(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY); // 保存天数
					edit.commit(); // 保存数据
				}
				try
				{
					SystemSettingAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		SharedPreferences readPerences = super.getSharedPreferences("base_info", MODE_WORLD_READABLE);
		// 照片保存
		this.picSaveToSdcardCardCb.setChecked(readPerences.getBoolean("systemMemory", false));
		this.picSaveToExcCardCb.setChecked(readPerences.getBoolean("extendMemory", true));
		int imgStorageDay = readPerences.getInt(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY);
		this.imgStorageEdit.setText(String.valueOf(imgStorageDay)); // 设置图片保存天数
	}

	/** 是否触发返回键 **/
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME)
		{
			String imgStorageDay = imgStorageEdit.getText().toString(); // 图片保存天数

			// 照片保存
			SharedPreferences.Editor editor = sharedPerences.edit();
			editor.putBoolean("systemMemory", picSaveToSdcardCardCb.isChecked()); // 系统内存
			editor.putBoolean("extendMemory", picSaveToExcCardCb.isChecked()); // 扩展内存
			editor.commit(); // 保存数据

			// 是否保存图片本地
			if (picLoactionSaveCb.isChecked())
			{
				if (!"".equals(imgStorageDay) && !imgStorageDay.equals(sharedPerences.getInt(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY)))
				{
					SharedPreferences.Editor edit = sharedPerences.edit();
					edit.putBoolean("isSaveLocal", true); // 保存本地图片
					edit.putString(AppConstants.IMG_STORAGE_DATE, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 保存当天日期
					edit.putInt(AppConstants.IMG_STORAGE_DAY, Integer.parseInt(imgStorageDay)); // 保存天数
					edit.commit(); // 保存数据
				}
			}
			else
			{
				SharedPreferences.Editor edit = sharedPerences.edit();
				edit.putBoolean("isSaveLocal", false); // 保存本地图片
				edit.putString(AppConstants.IMG_STORAGE_DATE, null); // 保存当天日期
				edit.putInt(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY); // 保存天数
				edit.commit(); // 保存数据
			}
			if (!"".equals(imgStorageDay) && !imgStorageDay.equals(sharedPerences.getInt(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY)))
			{
				SharedPreferences.Editor edit = sharedPerences.edit();
				edit.putString(AppConstants.IMG_STORAGE_DATE, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 保存当天日期
				edit.putInt(AppConstants.IMG_STORAGE_DAY, Integer.parseInt(imgStorageDay)); // 保存天数
				edit.commit(); // 保存数据
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 初始化视图
	 */
	public void setViewController()
	{
		this.picLoactionSaveCb = (CheckBox) super.findViewById(R.id.picLoactionSaveCb);
		this.imgStorageEdit = (EditText) super.findViewById(R.id.imgStorageEdit); // 图片保存天数
		settingBrackBtn = (Button) findViewById(R.id.settingBrackBtn);
		clearSystemCashBtn = (Button) findViewById(R.id.clearSystemCashBtn);
		checkVersionBtn = (Button) findViewById(R.id.checkVersionBtn);
		receptionPushInfoCb = (CheckBox) findViewById(R.id.receptionPushInfoCb);
		picSaveToExcCardCb = (RadioButton) findViewById(R.id.picSaveToExcCardCb);
		picSaveToSdcardCardCb = (RadioButton) findViewById(R.id.picSaveToSdcardCardCb);

	}

	public void setDate()
	{
		picSaveToExcCardCb.setText(picSaveToExcCardCb.getText() + Utility.getSdcardAvail(this));
		picSaveToSdcardCardCb.setText(picSaveToSdcardCardCb.getText() + Utility.getPhoneAvail(this));
		Intent intent = getIntent();
		boolean isAcceptTask = intent.getBooleanExtra("isAcceptTask", true);
		receptionPushInfoCb.setChecked(isAcceptTask);
		this.picLoactionSaveCb.setChecked(this.sharedPerences.getBoolean("isSaveLocal", true)); // 是否保存图片本地
	}

	class SystemSettingListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// 显示进度框
			progressDialog = new ProgressDialog(SystemSettingAct.this);
			progressDialog.setMessage("数据加载中...");
			progressDialog.setIndeterminate(true);
			progressDialog.setCancelable(true);
			progressDialog.show();

			switch (v.getId())
			{
			case R.id.clearSystemCashBtn:
				new Thread()
				{

					@Override
					public void run()
					{
						super.run();
						new MyUtils().clearDataDictionary(getApplicationContext());
						messageHandler.sendEmptyMessage(4);
					}

				}.start();
				// try {
				// new MyUtils().clear(SystemSettingAct.this);
				// } catch (Exception e) {
				// e.printStackTrace();
				// } finally {
				// messageHandler.sendEmptyMessage(3);
				// }
				errorState = 1;
				break;
			case R.id.checkVersionBtn:
				checkVersion();
				errorState = 2;
				break;
			}
			/*
			 * // 发送数据同步协议 Thread thread = new Thread(new LoadHandler());
			 * thread.start();
			 */
		}
	}

	private Dialog mDialog;
	private Handler versionHandler = new Handler()
	{

		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				mDialog = CustomDialog.show(SystemSettingAct.this, "信息提示", msg.obj + "", "重试", "关闭", new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						checkVersion();
						mDialog.dismiss();
					}

				}, new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						mDialog.dismiss();
					}

				});
				break;
			// 版本更新
			case 2:
				List<AppInfoBean> apps = (List<AppInfoBean>) msg.obj;
				//String url = AppConstants.info.getUrl();
				if (apps != null && !apps.isEmpty())
				{
					AppInfoBean app = apps.get(0);
					String url = app.getAddress();
					if (!getVersion(SystemSettingAct.this).equals(app.getVersion()))
					{
						/** 检测版本是否需要更新 **/
						// String url = app.getAddress();
						// url = "http://" + AppConstants.PLATFORM_URL
						// + ":9201/msplatform/soft/"
						// + AppConstants.APP_UPDATE_NAME;
						DownloadManager downloadManager = new DownloadManager(SystemSettingAct.this, url, app.getVersion(), app.getRemark());
						if ("1".equals(app.getForce()))
						{ // 强制更新
							downloadManager.checkUpdateInfo(app.getForce(), null);
						}
						else
						{ // 非强制更新
							downloadManager.checkUpdateInfo("0", null);
						}
					}
					else
					{
						CustomDialog.show(SystemSettingAct.this, "温馨提示", "已经是最新版本!");
					}
				}
				break;
			}
		};
	};

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
					progressDialog.dismiss();
					versionHandler.sendMessage(msg);
				}
			};
		}.start();
	}

	/**
	 * 调用通知线程
	 * 
	 * @param errorState
	 *            0.操作失败 1.操作成功 2.网络连接失败 3.数据库操作异常 4.程序异常
	 */
	private void doHandle(int errorState)
	{
		Message message = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("errorState", errorState);
		message.setData(bundle);
		messageHandler.sendMessage(message);
	}

	/**
	 * 加载数据线程处理
	 * 
	 * @author linianchun
	 * 
	 */
	class LoadHandler implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				Thread.sleep(5000);
				// 关闭加载框
				progressDialog.dismiss();
				doHandle(errorState);
			}
			catch (Exception e)
			{
			}
		}
	}

	/** 通知更新UI线程 */
	Handler messageHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{

			if (progressDialog != null)
			{
				progressDialog.dismiss();
			}
			switch (msg.what)
			{
			case 0:
				ToastDialog.show(SystemSettingAct.this, "系统版本取得错误" + msg.obj.toString(), ToastDialog.ERROR);
				break;
			case 1:
				List<AppInfoBean> apps = (List<AppInfoBean>) msg.obj;
				if (apps != null && !apps.isEmpty())
				{

					AppInfoBean app = apps.get(0);
					if (getVersion(SystemSettingAct.this).equals(app.getVersion()))
					{
						// 跳转至登陆界面
						CustomDialog.show(SystemSettingAct.this, "温馨提示", "已经是最新版本!");
					}
					else
					{
						/** 检测版本是否需要更新 **/
						String url = AppConstants.info.getUrl();
						if ("1".equals(app.getForce()))
						{ // 强制更新
						// String url = app.getAddress(); // 应用URL
						// url = "http://" + AppConstants.PLATFORM_URL
						// + ":9201/msplatform/soft/"
						// + AppConstants.APP_UPDATE_NAME;// 测试URL
							DownloadManager downloadManager = new DownloadManager(SystemSettingAct.this, url, app.getVersion(), app.getRemark());
							downloadManager.checkUpdateInfo(app.getForce(), null);
						}
						else
						{ // 非强制更新
						// String url = app.getAddress(); // 应用URL
						// url = "http://" + AppConstants.PLATFORM_URL
						// + ":9201/msplatform/soft/"
						// + AppConstants.APP_UPDATE_NAME;// 测试URL
							DownloadManager downloadManager = new DownloadManager(SystemSettingAct.this, url, app.getVersion(), app.getRemark());
							downloadManager.checkUpdateInfo("0", null);
						}
					}
				}
				break;
			case 3:
				ToastDialog.show(SystemSettingAct.this, "缓存清楚成功", ToastDialog.ERROR);
				break;
			case 4:
				dialog = CustomDialog.show(SystemSettingAct.this, "提示", "缓存清除成功，请重新登录", "重新登录", null, new OnClickListener()
				{

					@Override
					public void onClick(View arg0)
					{
						dialog.dismiss();
						Intent intent = new Intent(SystemSettingAct.this, LoginAct.class);
						startActivity(intent);
						finish();
					}
				}, null);
				break;
			}
		}
	};

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

	/*
	 * public static String getApplicationName(Context context) { PackageManager
	 * packageManager = null; ApplicationInfo applicationInfo = null; try {
	 * packageManager = context.getPackageManager(); applicationInfo =
	 * packageManager.getApplicationInfo(context.getPackageName(), 0); } catch
	 * (PackageManager.NameNotFoundException e) { applicationInfo = null; }
	 * String applicationName = (String)
	 * packageManager.getApplicationLabel(applicationInfo); return
	 * applicationName; }
	 */

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		try
		{
			this.finish();
			ActivityManage.getInstance().pop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (getCurrentFocus() != null)
		{
			if (getCurrentFocus().getWindowToken() != null)
			{
				imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.dispatchTouchEvent(ev);
	}
}