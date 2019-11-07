package com.sinosoft.ms.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.query.DataSreachListAct;
import com.sinosoft.ms.activity.setting.SystemSettingAct;
import com.sinosoft.ms.activity.task.TaskCenterAct;
import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.NewsFactory;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.impl.BackstageServices;
import com.sinosoft.ms.service.impl.DictionaryService;
import com.sinosoft.ms.service.impl.LoginService;
import com.sinosoft.ms.service.impl.PushMessageService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConfig;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LocationService;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.SystemUtil;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictDatabase;

/**
 * 系统名：移动查勘定损 子系统名：主菜单界面控制 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Mon Nov 19 14:49:28 CST 2012
 */
public class MainMeunAct extends Activity
{
	private SharedPreferences sharedPreferences = null;
	private ProgressDialogUtil progress = null;

	private String tag = "MainMeunAct";
	private Button convertUserBtn = null;
	private Button exitSystemBtn = null;
	private Button surveyCameraBtn = null; // 查勘相机

	private TextView newsInfoTxt = null; // 任务列表提醒
	// private TextView currTaskTxt = null;
	// private TextView surveyTaskTxt = null;
	// private TextView lossTaskTxt = null;
	// private TextView receiveTxt = null;
	// private TextView solveTxt = null;

	private TextView currTimeTxt = null;
	private Intent pollingIntent = null;
	private GridView meunGrid = null;
	private final int requestCodes = 1988;

	// private final Integer itemBgs[] = new Integer[] {
	// R.drawable.new_menu_taskcenter_icon, R.drawable.new_menu_casecenter_icon,
	// R.drawable.new_menu_datasearch_icon, R.drawable.new_menu_setting_icon };
	// private final String itemName[] = new String[] { "任务中心", "案件中心", "数据查询",
	// "系统设置" };
	private final Integer itemBgs[] = new Integer[]
	{ R.drawable.new_menu_taskcenter_icon, R.drawable.new_menu_datasearch_icon, R.drawable.new_menu_setting_icon };
	private final String itemName[] = new String[]
	{ "任务中心", "数据查询", "系统设置" };
	private boolean isAcceptTask = false;
	private boolean isFinish = false;
	private Dialog dialog = null;

	private int imgStorageDay = 0;

	private SharedPreferences imgPreferences;
	
	private ProgressDialogUtil progressDialog = null;

	private TextView tipsTxt;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_menu);
		imgPreferences = getSharedPreferences("base_info", MODE_WORLD_READABLE);
		AppConfig.loginFlag = true;
		
		mContext = this;
		
		// 初始化后台服务
		if (pollingIntent == null)
		{
			pollingIntent = new Intent();
			pollingIntent.putExtra("primarySurvey", false);
			pollingIntent.putExtra("dispatch", false);
			pollingIntent.putExtra("loss", false);
			pollingIntent.putExtra("agentDriving", false);
			pollingIntent.putExtra(AppConstants.IMG_STORAGE_DAY, imgPreferences.getInt(AppConstants.IMG_STORAGE_DAY, 7)); // 图片保存天数
			pollingIntent.putExtra(AppConstants.IMG_STORAGE_DATE, imgPreferences.getString(AppConstants.IMG_STORAGE_DATE, "")); // 图片保存当天日期
			pollingIntent.setClass(this, BackstageServices.class);
		}
		sharedPreferences = getSharedPreferences("base_info", MODE_PRIVATE);
		// 记录本次登录复选框选择信息
		boolean dispatch = sharedPreferences.getBoolean("dispatch", false);
		boolean loss = sharedPreferences.getBoolean("loss", false);

		startLocationService(); // 先起定位服务再进行轮询服务
		if (dispatch || loss)
		{
			startService(pollingIntent);
			isAcceptTask = true;
		}
		else
		{
			stopService(pollingIntent);
			isAcceptTask = false;
		}
		// 设置视图控件
		setViewControl();

		// getDicInfo();//取得字典信息

		// 设置数据
		setDate();

		// 开始刷新任务数
		startRefresh();

		meunGrid.setOnItemClickListener(new GrideViewItemClick());
		// 设置控件事件
		MainMeunClick listener = new MainMeunClick();
		convertUserBtn.setOnClickListener(listener);
		exitSystemBtn.setOnClickListener(listener);
		surveyCameraBtn.setOnClickListener(listener);
		//
		ActivityManage.getInstance().push(this);
	}

	/**
	 * @return
	 */
	private ArrayList<HashMap<String, Object>> getData()
	{
		ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < itemBgs.length; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", itemBgs[i]);
			map.put("ItemText", itemName[i]);
			meumList.add(map);
		}
		return meumList;
	}

	/**
	 * 设置视图控件
	 */
	private void setViewControl()
	{
		this.tipsTxt = (TextView) super.findViewById(R.id.tipsTxt);
		meunGrid = (GridView) findViewById(R.id.meunGrids);
		convertUserBtn = (Button) findViewById(R.id.convertUserBtn);
		exitSystemBtn = (Button) findViewById(R.id.exitSystemBtn);
		surveyCameraBtn = (Button) findViewById(R.id.surveyCameraBtn);
		newsInfoTxt = (TextView) findViewById(R.id.newsInfoText);
		// currTaskTxt = (TextView) findViewById(R.id.currTaskTxt);
		currTimeTxt = (TextView) findViewById(R.id.currTimeTxt);
		// surveyTaskTxt = (TextView) findViewById(R.id.surveyTaskTxt);
		// lossTaskTxt = (TextView) findViewById(R.id.lossTaskTxt);
		// receiveTxt = (TextView) findViewById(R.id.receiveTxt);
		// solveTxt = (TextView) findViewById(R.id.solveTxt);
	}

	/**
	 * 设置页面数据
	 */
	public void setDate()
	{
		tipsTxt.setText(AppConstants.VERSION_NAME + "：" + SystemSettingAct.getVersion(this) + " \n" + super.getIntent().getStringExtra("networkTips"));
		ArrayList<HashMap<String, Object>> meumList = getData();
		SimpleAdapter saItem = new SimpleAdapter(this, meumList, // 数据源
				R.layout.item_main_menu, // xml实现
				new String[]
				{ "ItemImage", "ItemText" }, // 对应map的Key
				new int[]
				{ R.id.ItemImage, R.id.ItemText }); // 对应R的Id
		meunGrid.setAdapter(saItem);
		refreshTaskNum();
	}

	/**
	 * 开启线程刷新任务数
	 */
	public void startRefresh()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				while (!isFinish)
				{
					try
					{
						Thread.sleep(1000);
						DateTimeFactory.getInstance().addTime(1);
						handler.sendEmptyMessage(100);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	/**
	 * 开启定位服务
	 */
	private void startLocationService()
	{
		Intent intent = new Intent(this, LocationService.class);
		startService(intent);
	}

	/**
	 * 关闭定位服务
	 */
	private void stopLocationService()
	{
		LocationService.latitude =0d;
		LocationService.longitude =0d;
		Intent intent = new Intent(this, LocationService.class);
		stopService(intent);
	}

	/**
	 * 用于更新任务数视图
	 */
	Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case 0:
				if (progress != null)
				{
					progress.dismiss();
				}
				ToastDialog.show(MainMeunAct.this, "获取字典失败：" + msg.obj.toString(), ToastDialog.ERROR);
				break;
			case 1:
				if (progress != null)
				{
					progress.dismiss();
				}
				break;
			case 100:
				if (!isFinish)
				{
					refreshTaskNum();
				}
				break;
			case AppConstants.NO_LOGIN_CODE:
				Intent intent = getIntent();
				intent.setClass(MainMeunAct.this, LoginAct.class);
				startActivity(intent);
				try
				{
					MainMeunAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
				}
				break;
			case 201:
			case 301:
				ToastDialog.show(MainMeunAct.this, msg.obj.toString(), ToastDialog.ERROR);
				ActivityManage.getInstance().distory();	
				 break;
			case 200: //切换用户
				try
				{
					if (progressDialog != null)
					{
						progressDialog.dismiss();
					}
					finish();
					Intent intentV = getIntent();
					ActivityManage.getInstance().pop();
					intentV.setClass(MainMeunAct.this, LoginAct.class);
					startActivity(intentV);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 break;
			case 300:  //退出用户 
				if (progressDialog != null)
				{
					progressDialog.dismiss();
				}
				///ToastDialog.show(mContext, "查勘退出失败，请重试!!", ToastDialog.ERROR);
				///dialog.dismiss();
				ActivityManage.getInstance().distory();				     
				 break;
			}

		};

	};

	/**
	 * 更新任务数
	 */
	public void refreshTaskNum()
	{
		Business business = BusinessFactory.getInstance();
		NewsFactory factory = NewsFactory.getInstance();
		if (factory.getNewsList().size() > 0)
		{
			// business.init();
			// String notReceiveTaskCount = business.getNotReceiveTaskCount();
			// String receiveTaskCount = business.getReceiveTaskCount();
			// String SurveyTaskCount = business.getSurveyTaskCount();
			// String lossTaskCount = business.getLossTaskCount();

			String taskStr = factory.getNewsString();
			newsInfoTxt.setText(taskStr);
			// // taskStr.append("您当前有以下任务：\n");
			// taskStr.append("\t"+SurveyTaskCount + "个查勘任务,  ");
			// taskStr.append(lossTaskCount + "个定损任务\n");
			// taskStr.append("共计："+notReceiveTaskCount + "个待接收任务,\n");
			// taskStr.append(receiveTaskCount + "个待处理任务");
			// surveyTaskTxt.setText(SurveyTaskCount);
			// lossTaskTxt.setText(lossTaskCount);
			// receiveTxt.setText(notReceiveTaskCount);
			// solveTxt.setText(receiveTaskCount);

			// String task = taskStr.toString();
			// SpannableString sp = new SpannableString(task);
			// //BackgroundColorSpan bg = new BackgroundColorSpan(Color.RED);
			// sp.setSpan(new BackgroundColorSpan(Color.RED), 4
			// ,task.indexOf("个待接收任务"),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// sp.setSpan(new BackgroundColorSpan(Color.RED),
			// task.indexOf("个待接收任务")+7
			// ,task.indexOf("个待处理"),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// sp.setSpan(new BackgroundColorSpan(Color.RED),
			// task.indexOf("其中")+2
			// ,task.indexOf("个查勘任务"),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// sp.setSpan(new BackgroundColorSpan(Color.RED),
			// task.indexOf("个查勘任务")+6
			// ,task.indexOf("个定损任务"),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// currTaskTxt.setText(sp);
			String time = "更新时间：" + DateTimeFactory.getInstance().getDat().toLocaleString();
			currTimeTxt.setText(time);
		}
	}

	/**
	 * 轮询广播操作
	 * 
	 * @param isClose
	 *            广播开关
	 */
	private void doPolling(Boolean isClose)
	{
		if (isClose)
		{
			isAcceptTask = false;
			stopService(pollingIntent);
		}
		else
		{
			isAcceptTask = true;
			this.startService(pollingIntent);
		}
	}

	/**
	 * 主菜单界面控制事件处理
	 * 
	 * @author linianchun
	 * 
	 */
	class GrideViewItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			Intent intent = getIntent();
			switch (arg2)
			{
			case 0:
				intent.setClass(MainMeunAct.this, TaskCenterAct.class);
				startActivity(intent);
				break;
			// case 1:
			//
			// intent.setClass(MainMeunAct.this, CaseCenterAct.class);
			// startActivity(intent);
			//
			// break;
			case 1:
				// intent.setClass(MainMeunAct.this, DataSreachAct.class);
				intent.setClass(MainMeunAct.this, DataSreachListAct.class);
				startActivity(intent);
				break;
			case 2:
				intent.setClass(MainMeunAct.this, SystemSettingAct.class);
				intent.putExtra("isAcceptTask", isAcceptTask);
				startActivityForResult(intent, requestCodes);
				break;
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		if (data != null)
		{
			if (requestCodes == requestCode)
			{
				// intent.get("isAcceptTask", isAcceptTask);
				isAcceptTask = data.getBooleanExtra("isAcceptTask", false);
				if (isAcceptTask)
				{
					ToastDialog.show(MainMeunAct.this, "接收任务己恢复", ToastDialog.INFO);
					startService(pollingIntent);

				}
				else
				{
					stopService(pollingIntent);
					ToastDialog.show(MainMeunAct.this, "暂停接收任务", ToastDialog.INFO);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	
	/**
	 * 开启线程刷新任务数
	 */
	public void exitLogin(final int exitflag)
	{
		progressDialog = new ProgressDialogUtil(mContext);
		progressDialog.show("正在关闭...");
		
		new Thread()
		{
			@Override
			public void run()
			{

				try
				{


					new Thread()
					{
						public void run()
						{
							Message msg = new Message();
							try
							{
								////stopLocationService();
								LoginService loginService = new LoginService();
								User user = UserCashe.getInstant().getUser();
								loginService.exitLogin(user);
								if(exitflag == 0) //切换
								  msg.what = 200;
								else 
								  msg.what = 300;
							}
							catch (Exception e)
							{
								String error = e.getMessage();
								if (StringUtils.isEmpty(error))
								{
									error = "关闭用户失败";
								}
								msg.obj = error;
								if(exitflag == 0) //切换
									  msg.what = 201;
									else 
									  msg.what = 301;
							}
							finally
							{
								handler.sendMessage(msg);

							}

						};
					}.start();
				}
				catch (Exception e)
				{
					///ToastDialog.show(LoginAct.this, "" + e.getMessage(), ToastDialog.ERROR);
					e.printStackTrace();
				}
				
				
			}//run
		}.start();

	}
	
	/**
	 * 底栏菜单界面控制事件处理
	 * 
	 * @author linianchun
	 * 
	 */
	class MainMeunClick implements OnClickListener
	{
		public void onClick(View v)
		{
			Intent intent = getIntent();
			switch (v.getId())
			{
			case R.id.convertUserBtn:
				exitLogin(0);

/*				try
				{
					progressDialog = new ProgressDialogUtil(mContext);
					progressDialog.show("正在切换用户...");
					stopLocationService();
					LoginService loginService = new LoginService();
					User user = UserCashe.getInstant().getUser();
					loginService.exitLogin(user);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally
				{


					try
					{
						if (progressDialog != null)
						{
							progressDialog.dismiss();
						}
						finish();
						ActivityManage.getInstance().pop();
						intent.setClass(MainMeunAct.this, LoginAct.class);
						startActivity(intent);
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		

			    }
*/
				break;
			case R.id.exitSystemBtn:
				showAlertDialog();
				break;
			case R.id.surveyCameraBtn:
				startSurveyCamera();
				break;
			}

		}
	}

	private void startSurveyCamera()
	{
		try
		{
			SystemUtil.startAppByPackageName(MainMeunAct.this, AppConstants.PACKAGE_NAME_SURVEY_CAMERA);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			dialog = CustomDialog.show(this, "提示", "找不到查勘相机，是否下载", "立即下载", "取消", new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					// TODO Auto-generated method stub
					dialog.dismiss();
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.info.getScUrl()));
					startActivity(intent);
				}

			}, new OnClickListener()
			{

				@Override
				public void onClick(View arg0)
				{
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.show();
			e.printStackTrace();
		}
	}

	/*
	 * 重写返回按钮的事件onBackPressed()
	 */
	@Override
	public void onBackPressed()
	{
		showAlertDialog();
	}

	/**
	 * 退出系统对话框
	 */
	public void showAlertDialog()
	{
		if (dialog != null && dialog.isShowing())
		{
			dialog.dismiss(); // 关闭对话框
		}
		dialog = CustomDialog.show(this, "信息提示", "您确定退出程序?", new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dialog.dismiss();

				
				exitLogin(1);
				
/*				LoginService loginService = new LoginService();

				try
				{
					progressDialog = new ProgressDialogUtil(mContext);
					progressDialog.show("正在切换用户...");
					
					stopLocationService();
					User user = UserCashe.getInstant().getUser();
					loginService.exitLogin(user);

				}
				catch (Exception e)
				{
					
					e.printStackTrace();
				}
				finally
				{
					if (progressDialog != null)
					{
						progressDialog.dismiss();
					}
					ToastDialog.show(mContext, "查勘退出失败，请重试!!", ToastDialog.ERROR);
					dialog.dismiss();
					ActivityManage.getInstance().distory();
			    }*/

			}
		}, new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				dialog.dismiss();
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		stopLocationService();
		isFinish = true;
		sharedPreferences = null;
		progress = null;
		tag = null;
		convertUserBtn = null;
		exitSystemBtn = null;
		// currTaskTxt = null;
		pollingIntent = null;
		meunGrid = null;
		dialog = null;
	}
}
