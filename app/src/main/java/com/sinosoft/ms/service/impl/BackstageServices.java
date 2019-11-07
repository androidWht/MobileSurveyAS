package com.sinosoft.ms.service.impl;

import java.io.File;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.activity.MainActivity;
import com.sinosoft.ms.activity.task.TaskCenterAct;
import com.sinosoft.ms.model.FeedbackMessage;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IPushMessageService;
import com.sinosoft.ms.utils.AppConfig;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.UserCashe;

/**
 * 系统名：移动查勘定损 子系统名：后台服务实现 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午6:01:22
 */

public class BackstageServices extends Service
{
	// private am;
	private SharedPreferences sp;
	private Intent intent;
	private String tag;
	private boolean flag = false;
	private NotificationManager nm;
	private final int notifyId = 8888;
	private Editor spedit;

	@Override
	public void onCreate()
	{
		super.onCreate();
		nm = (NotificationManager) BackstageServices.this.getSystemService(Context.NOTIFICATION_SERVICE);
		// ActivityManager
		// am=(ActivityManager)BackstageServices.this.getSystemService(Context.ACTIVITY_SERVICE);
		sp = getSharedPreferences(AppConstants.send_time, Context.MODE_PRIVATE);
		spedit = sp.edit();
		spedit.putInt("time", 0);
		spedit.commit();
	}

	@Override
	public void onStart(Intent intent, int startId)
	{
		tag = "BackstageServices";
		this.intent = intent;
		if (null != intent)
		{
			intent.getBooleanExtra("primarySurvey", false);
			intent.getBooleanExtra("dispatch", false);
			intent.getBooleanExtra("loss", false);
			intent.getBooleanExtra("agentDriving", false);
			this.deleteImgFile(intent.getIntExtra(AppConstants.IMG_STORAGE_DAY, AppConstants.IMG_DEFAULT_DAY), intent.getStringExtra(AppConstants.IMG_STORAGE_DATE)); // 删除过期图片
		}
		if (!flag)
		{
			flag = true;
			new Thread(mRunnable).start();
		}

	}

	/** 删除过期图片 **/
	private void deleteImgFile(int day, String dateTime)
	{
		// SDCard是否存在
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			if (day != 0 && !"".equals(dateTime))
			{
				String imagePath = Environment.getExternalStorageDirectory() + File.separator + AppConstants.IMG_PATH; // 图片文件路径
				String fileName = ""; // 文件名称
				long validDate = Long.parseLong(dateTime); // 有效日期
				long fileDate = 0;
				File file = new File(imagePath);
				if (file.exists())
				{
					for (File f : file.listFiles())
					{
						if (f.isDirectory())
						{
							for (File childFile : f.listFiles())
							{
								fileName = childFile.getName(); // 图片名称
								fileName = fileName.substring(0, fileName.indexOf("_"));
								fileDate = Long.parseLong(fileName); // 图片日期
								if ((validDate - fileDate) / 1000000 >= day)
								{ //  是否过期图片
									childFile.delete(); // 删除图片
								}
							}
						}
						else
						{
							fileName = f.getName(); // 图片名称
							fileName = fileName.substring(0, fileName.indexOf("_"));
							fileDate = Long.parseLong(fileName);
							if ((validDate - fileDate) / 1000000 >= day)
							{ //  是否过期图片
								f.delete(); // 删除图片
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 执行后台服务方法 说明：本方法主要用来处理接收推送消息功能和本地保存照片维护工作 实现步骤：
	 * 1.按设置推送消息设置时间定时向服务器发送请求，轮询平台任务接口 2.将反馈回来的信息保存到相应的数据表中 3.并启动广播提示用户当前接收到新任务
	 */
	private Runnable mRunnable = new Runnable()
	{
		public void run()
		{
			while (flag)
			{
				try
				{
					// 后台服务一分钟请求一次
					Thread.sleep(AppConstants.DEFAULT_PUSH_MESSAGE_TIME * 60000);
					// 测试时间 10秒
					///Thread.sleep(AppConstants.DEFAULT_PUSH_MESSAGE_TIME *30000);
					// 1.发送请求到服务平台
					IPushMessageService pushMessageService = new PushMessageService();
					User user = UserCashe.getInstant().getUser();
					// IsAlive();
					int time = sp.getInt("time", 0);
					user.setSend_time(time + 1);
					FeedbackMessage feedbackMessage = pushMessageService.send(null);
					spedit.putInt("time", time + 1);
					spedit.commit();
					if (null != feedbackMessage)
					{
						// 2.保存反馈结果
						save(feedbackMessage);

						// 3.启动事件提示广播
						if (StringUtils.isNotEmpty(feedbackMessage.getRadioMessage()) && AppConstants.IS_SHOW)
						{
							if (feedbackMessage.isNotify())
							{
								startRadioBroadcast(feedbackMessage.getRadioMessage());
							}

						}
					}

				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
					flag = false;
					spedit.putInt("time", 0);
					spedit.commit();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					flag = false;
					spedit.putInt("time", 0);
					spedit.commit();
				}
			}
		}

		/**
		 * 保存反馈结果处理方法
		 * 
		 * @param feedbackMessage
		 *            反馈结果
		 */
		private void save(FeedbackMessage feedbackMessage)
		{
		}

		/**
		 * 启动事件提示广播处理方法
		 * 
		 * @param radioMessage
		 *            广播提示信息
		 */
		private void startRadioBroadcast(String radioMessage)
		{
			showNotifaction(1, radioMessage);
		}
	};

	Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			showNotifaction(1, msg.toString());
		}
	};

	/**
	 * 显示Notifaction提示历史数据下载结果
	 * 
	 * @param intent
	 * @param type
	 *            1:预购 ,2:拜访 ,3:简报
	 * @param insertCount
	 *            插入条数
	 * @param updateCount
	 *            更新条数
	 */
	private void showNotifaction(int type, String message)
	{
		// 在状态栏上显示轮询结果

		nm.cancel(notifyId);
		if (flag)
		{
			Notification n = new Notification();
			int icon = n.icon = R.drawable.ic_launcher;
			String tickerText = "更新条数";
			long when = System.currentTimeMillis();
			n.icon = icon;
			n.tickerText = tickerText;
			n.when = when;
			// 设置跳转
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (AppConfig.loginFlag)
			{
				intent.setClass(BackstageServices.this, TaskCenterAct.class);

			}
			else
			{
				intent.setClass(BackstageServices.this, LoginAct.class);
			}

			PendingIntent pi = PendingIntent.getActivity(BackstageServices.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// 通知时既震动又屏幕发亮还有默认的声音 这里用的是ALL
			// n.defaults = Notification.DEFAULT_ALL;
			// 点击通知后自动消失
			n.flags = Notification.FLAG_AUTO_CANCEL;
			n.setLatestEventInfo(BackstageServices.this, "移动查勘定损系统提示", message, pi);
			nm.notify(notifyId, n);
		}
	}

	@Override
	public void onDestroy()
	{
		flag = false;
		nm.cancel(notifyId);
		if (null != tag)
		{
			tag = null;

		}
		if (null != sp)
		{
			spedit.putInt("time", 0);
			spedit.commit();
			sp = null;

			spedit = null;
		}
		UserCashe.getInstant().getUser().setSend_time(0);
		// unregisterReceiver(mReceiver);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	// private boolean IsAlive(){
	// List<RunningTaskInfo> list = am.getRunningTasks(100);
	// boolean isAppRunning = false;
	// String MY_PKG_NAME = "com.sinosoft.ms";
	// for (RunningTaskInfo info : list) {
	// Log.e(MY_PKG_NAME,
	// info.topActivity.getPackageName()+info.baseActivity.getPackageName());
	// if (info.topActivity.getPackageName().equals(MY_PKG_NAME) ||
	// info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
	// Log.e(MY_PKG_NAME,
	// info.topActivity.getPackageName()+info.baseActivity.getPackageName());
	// return true;
	//
	//
	// }
	// }
	//
	//
	//
	// return false;
	// }
}
