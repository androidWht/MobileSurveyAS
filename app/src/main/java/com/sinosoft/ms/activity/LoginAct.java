package com.sinosoft.ms.activity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.setting.SystemSettingAct;
import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.impl.DictionaryService;
import com.sinosoft.ms.service.impl.HeartbeatService;
import com.sinosoft.ms.service.impl.LoginService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.SystemUtil;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.Utility;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DatabaseHelper;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.DictDatabase;
import com.sinosoft.ms.utils.net.Network;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Mon Nov 19 14:49:28 CST 2012
 */

public class LoginAct extends Activity
{
	private ProgressDialogUtil progressDialog = null;
	private SharedPreferences sharedPerences = null;
	private List<DicInfoBean> dicInfoBeans = null;
	private boolean rememberMe;
	private boolean primarySurvey;
	private boolean dispatch;
	private boolean loss;
	private boolean agentDriving;
	private String name;

	private DictionaryService service = null;
	private DictDatabase database = null;

	private Intent intent;
	private EditText nameTxt;// 用户名
	private EditText passwordTxt;// 密码
	private Button loginBtn;// 登录按扭
	private Button cancelBtn;// 取消按扭

	private CheckBox rememberMeCb;// 记住我
	private CheckBox primarySurveyCb;// 我是主查勘员
	private CheckBox dispatchCb;// 接受查勘任务
	private CheckBox lossCb;// 接受定损任务
	private CheckBox agentDrivingCb;// 接受代驾任务
	private CheckBox wifiCb;
	private InputMethodManager imm;
	private User user;
	private Dialog dialog;
	private WifiManager wifiManager;

	private Spinner accessModeSp; // 访问模式
	private Map<String, Map<String, String>> localMap = null;

	private String networkTips;
	private TextView tipsTxt;
	
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		
		mContext = this;
		
		localMap = AppConstants.getLocalItemConf();
		ActivityManage.getInstance().distory();
		ActivityManage.getInstance().push(this);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
		// 设置视图控件
		setViewControl();

		// 装载数据
		loadData();
		loginBtn.setOnClickListener(new LoginOnClick());
		cancelBtn.setOnClickListener(new LoginOnClick());
		// wifiCb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if (isChecked) {
		// AppConstants.URL = AppConstants.WIFI_URL;
		// AppConstants.IMGURL = AppConstants.WIFI_IMGURL;
		// if (!wifiManager.isWifiEnabled()) {
		// /*
		// * Intent intent = new Intent(""); ComponentName cm =
		// * new ComponentName("com.android.settings",
		// * "com.android.settings.WirelessSettings");
		// * intent.setComponent(cm);
		// * intent.setAction("android.intent.action.VIEW");
		// * startActivityForResult( intent , 0);
		// */
		// wifiManager.setWifiEnabled(true);
		// }
		// } else {
		// AppConstants.URL = AppConstants.G_URL;
		// AppConstants.IMGURL = AppConstants.G_IMAGEURL;
		// if (wifiManager.isWifiEnabled()) {
		// wifiManager.setWifiEnabled(false);
		// }
		// }
		// }
		// });

		networkTips = super.getIntent().getStringExtra("networkTips");
		if (networkTips == null)
		{
			networkTips = "";
		}

		DatabaseHelper.getHelper(this).clear();
	}

	public static boolean PingHost(String ip)
	{
		boolean status = false;
		int timeOut = 3000; // 定义超时，表明该时间内连不上即认定为不可达，超时值不能太小。
		try
		{// ping功能
			status = InetAddress.getByName(ip).isReachable(timeOut);
		}
		catch (UnknownHostException e)
		{
			status = false;
		}
		catch (IOException e)
		{
			status = false;
		}
		return status;
	}

	/** 选择网络类型 **/
	private void connectionSelected()
	{
		HeartbeatService heart = new HeartbeatService();
		if (wifiManager.isWifiEnabled())
		{
			// AppConstants.URL = AppConstants.INNER_URL; // 测试环境WIFI内网
			// AppConstants.IMGURL = AppConstants.INNER_IMG_URL; // 测试环境图片上传
			try
			{
				if (heart.checkNetwork(this.user))
				{
					// AppConstants.PLATFORM_URL = AppConstants.INNET_URL;
					networkTips = "访问模式：内网";
					return;
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new IllegalArgumentException("WIFI内网访问失败!");
			}
		}
		if (Network.is3GEnabled(LoginAct.this))
		{
			try
			{
				// AppConstants.URL = AppConstants.APN_URL; // 测试环境APN内网
				// AppConstants.IMGURL = AppConstants.APN_IMG_URL; //
				// 测试环境图片上传APN地址
				// AppConstants.PLATFORM_URL = AppConstants.APNNET_URL;
				boolean flag = heart.checkNetwork(this.user);
				if (!flag)
				{
					// AppConstants.URL = AppConstants.THREE_G_URL; // 测试环境3G地址
					// AppConstants.IMGURL = AppConstants.THREE_G_IMG_URL; //
					// 测试环境图片上传3G地址
					// AppConstants.PLATFORM_URL = AppConstants.INTERNET_URL;
					networkTips = "访问模式：互联网";
				}
				else
				{
					networkTips = "访问模式：APN";
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			throw new IllegalArgumentException("请确认网络连接是否打开或后台支撑系统是否正常！");
		}
	}

	// /** 网络链接 **/
	// @SuppressWarnings("all")
	// private void connected(String accessMode) {
	// if ("内网".equals(accessMode)) {
	// if (!Network.isWifiConnected(this)) {
	// CustomDialog.show(this, "信息提示", "请确认WIFI是否打开！");
	// return;
	// }
	// AppConstants.URL = AppConstants.INNER_URL; // 测试环境WIFI内网
	// AppConstants.IMGURL = AppConstants.INNER_IMG_URL; // 测试环境图片上传地址
	// } else if ("互联网".equals(accessMode)) {
	// if (Network.isWifiConnected(this)) {
	// AppConstants.URL = AppConstants.THREE_G_URL; // 测试环境3G内网
	// AppConstants.IMGURL = AppConstants.THREE_G_IMG_URL; // 测试环境3G图片上传内网
	// } else if (Network.is3GConnected(this)) {
	// AppConstants.URL = AppConstants.THREE_G_URL; // 测试环境3G内网
	// AppConstants.IMGURL = AppConstants.TEST_3G_IMGURL; // 测试环境3G图片上传内网
	// } else if (TelephonyManager.SIM_STATE_ABSENT == 1) {
	// CustomDialog.show(this, "信息提示", "请确认sim卡是否插入或者sim卡暂时不可用！");
	// return;
	// } else {
	// CustomDialog.show(this, "信息提示", "请确认WIFI/数据连接是否打开！");
	// return;
	// }
	// } else if ("APN".equals(accessMode)) {
	// if (TelephonyManager.SIM_STATE_ABSENT == 1) { // 是否存在SIM卡
	// CustomDialog.show(this, "信息提示", "请确认sim卡是否插入或者sim卡暂时不可用！");
	// return;
	// } else if (!Network.is3GConnected(this)) {
	// CustomDialog.show(this, "信息提示", "请确认数据连接是否打开！");
	// return;
	// }
	// AppConstants.URL = AppConstants.TEST_APN_URL; // 测试环境APN内网
	// AppConstants.IMGURL = AppConstants.TEST_APN_IMGURL; // 测试环境APN图片上传内网
	// }
	// }

	/**
	 * 设置视图控件
	 */
	private void setViewControl()
	{
		this.tipsTxt = (TextView) super.findViewById(R.id.tipsTxt);
		this.tipsTxt.setText(AppConstants.VERSION_NAME + "：" + SystemSettingAct.getVersion(this));
		this.accessModeSp = (Spinner) super.findViewById(R.id.accessModeSp);
		SpinnerUtils.setSpinnerData(this, this.accessModeSp, this.localMap.get("accessMode"), "inner",""); // 设置访问模式数据
		// 选择访问模式
		this.accessModeSp.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position, long id)
			{
				// connected(accessModeSp.getItemAtPosition(position)
				// .toString()); // 网络连接
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});
		nameTxt = (EditText) findViewById(R.id.username);
		passwordTxt = (EditText) findViewById(R.id.password);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		rememberMeCb = (CheckBox) findViewById(R.id.remember_me_cb);
		primarySurveyCb = (CheckBox) findViewById(R.id.primary_survey_cb);
		dispatchCb = (CheckBox) findViewById(R.id.dispatch_cb);
		lossCb = (CheckBox) findViewById(R.id.loss_cb);
		agentDrivingCb = (CheckBox) findViewById(R.id.agent_driving_cb);
		wifiCb = (CheckBox) findViewById(R.id.wifiCb);
	}

	/**
	 * 装载数据 说明:记住我选中时要装用户名默认显示到控件上 并且记住我复选框设置为选中状态
	 */
	private void loadData()
	{
		sharedPerences = this.getSharedPreferences("base_info", MODE_WORLD_READABLE);
		rememberMe = sharedPerences.getBoolean("rememberMe", false);
		rememberMeCb.setChecked(rememberMe);
		// 记住我复选上次如果被选中
		if (rememberMe)
		{
			nameTxt.setText(sharedPerences.getString("name", ""));
		}
		/** 显示版本号 - 严植培 **/
		TextView version_tv = (TextView) findViewById(R.id.version_tv);
		version_tv.setText("版本号：" + SystemSettingAct.getVersion(this));
		/** 显示版本号 - 严植培 **/
	}

	/**
	 * 登录按扭事件
	 * 
	 * @author linianchun
	 * 
	 */
	class LoginOnClick implements OnClickListener
	{

		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.loginBtn:// 登录处理

				user = new User();
				user.setName(nameTxt.getText().toString().trim());
				user.setPassword(passwordTxt.getText().toString().trim());
				user.setUid(Utility.getDeviceId(LoginAct.this));
				try
				{
					checkUser(user);
					checkSDCardSpace();
					progressDialog = new ProgressDialogUtil(LoginAct.this);
					progressDialog.show("正在登陆...");

					new Thread()
					{
						public void run()
						{
							Message msg = new Message();
							try
							{
								LoginService loginService = new LoginService();

								if (loginService.login(user, null))
								{
									// 保存当前用户

									Business business = BusinessFactory.getInstance();
									business.init();
									user.setDeptNo(business.getDeptNo());
									UserCashe userCashe = UserCashe.getInstant();
									userCashe.setUser(user);

									// 获取字典同步时间，是否取字典信息
									String dicTime = business.getSynTime();
									String synTime = sharedPerences.getString(user.getName(), "");
									if (StringUtils.isEmpty(synTime) || MyUtils.complateString(dicTime, synTime))
									{
										// if (true) {
										service = new DictionaryService();
										dicInfoBeans = service.getDicInfo(user, synTime, user.getDeptNo());
										database = new DictDatabase();
										database.clearDic(LoginAct.this);
										database.insertDic(LoginAct.this, dicInfoBeans);
										Editor editor = sharedPerences.edit();
										// 9901006_13674758493840
										editor.putString(user.getName(), dicTime);
										editor.commit();
									}
								}
								msg.what = 1;

							}
							catch (Exception e)
							{
								String error = e.getMessage();
								if (StringUtils.isEmpty(error))
								{
									error = "登录时发生异常，请检查网络状态或存储空间";
								}
								msg.obj = error;
								msg.what = 0;
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
					ToastDialog.show(LoginAct.this, "" + e.getMessage(), ToastDialog.ERROR);
				}
				break;
			case R.id.cancelBtn:// 取消处理
				showAlertDialog();
				break;
			}
		}
	}

	// 用于更新界面 跳转
	private Handler handler = new Handler()
	{
		// 0表示失败，1表示成功
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				if (progressDialog != null)
				{
					progressDialog.dismiss();
				}
				ToastDialog.show(LoginAct.this, msg.obj + "", ToastDialog.ERROR);
				break;
			case 1:
				saveLoginProperty();
				// 初始化字典信息
				DictDatabase database = new DictDatabase();
				Map<String, Map<String, String>> dicMap = database.getDict(getApplicationContext());
				if (dicMap != null && !dicMap.isEmpty())
				{
					DictCashe.getInstant().setDict(dicMap);

					// 跳转到下一界面
					intent = new Intent();
					intent.putExtra("networkTips", networkTips); // 网络地址信息
					intent.setClass(LoginAct.this, MainMeunAct.class);
					startActivity(intent);
					try
					{
						finish();
						ActivityManage.getInstance().pop();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					if (progressDialog != null)
					{
						progressDialog.dismiss();
					}
					Editor editor = sharedPerences.edit();
					editor.putString(user.getName(), "");
					editor.commit();
					ToastDialog.show(LoginAct.this, "系统服务忙请重试!", ToastDialog.ERROR);
				}
				break;
			}
		};

	};

	/**
	 * 取得界面数据
	 */
	private void getDate()
	{
		name = nameTxt.getText().toString();
		rememberMe = rememberMeCb.isChecked();
		primarySurvey = primarySurveyCb.isChecked();
		dispatch = dispatchCb.isChecked();
		loss = lossCb.isChecked();
		agentDriving = agentDrivingCb.isChecked();
	}

	/**
	 * 保存登陆的有关操作
	 */
	public void saveLoginProperty()
	{
		// 如果记住我被选中保存本次输入信息
		getDate();
		Editor editor = sharedPerences.edit();
		int pushMessageTime = sharedPerences.getInt("pushMessageTime", AppConstants.DEFAULT_PUSH_MESSAGE_TIME);
		if (rememberMe)
		{
			editor.putString("name", name);
		}
		else
		{
			editor.putString("name", "");
		}
		// 记录本次登录复选框选择信息
		editor.putBoolean("rememberMe", rememberMe);
		editor.putBoolean("primarySurvey", primarySurvey);
		editor.putBoolean("dispatch", dispatch);
		editor.putBoolean("loss", loss);
		editor.putBoolean("agentDriving", agentDriving);
		// 设置轮询时间（如果未设置默认10分钟轮询一次）
		editor.putInt("pushMessageTime", pushMessageTime);
		editor.commit();
		UserCashe.saveUserName(name);

	}

	/**
	 * 校验用户信息
	 * 
	 * @return 校验成功true 失败false
	 */
	public void checkUser(User user) throws Exception
	{
		if (StringUtils.isEmpty(user.getName()))
		{
			throw new IllegalArgumentException("用户名不能为空");
		}
		else if (StringUtils.isEmpty(user.getPassword()))
		{
			throw new IllegalArgumentException("密码不能为空");
		}
		else if (StringUtils.isEmpty(user.getUid()))
		{
			ToastDialog.show(this, "手机识别码为空", ToastDialog.ERROR);
		}
	}

	/**
	 * 校验SD卡空间
	 * 
	 * @throws Exception
	 */
	public void checkSDCardSpace() throws Exception
	{
		long store = SystemUtil.getAvailableStore(Environment.getExternalStorageDirectory().getAbsolutePath());
		if (store <= 1024 * 1024 * 10)
		{
			throw new IllegalAccessException("内部存储空间不足,请空出更多存储空间.");
		}
	}

	@Override
	protected void onPause()
	{
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
		super.onPause();
	}

	@Override
	public void onBackPressed()
	{
		showAlertDialog();
	}

	/**
	 * 退出对话框
	 */
	public void showAlertDialog()
	{
		dialog = CustomDialog.show(this, "信息提示", "您确定退出程序?", new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				LoginService loginService = new LoginService();

				try
				{
/*					if (loginService.exitLogin())
					{
						
					}
					else
					{
						ToastDialog.show(mContext, "查勘退出失败，请重试!!", ToastDialog.ERROR);
					}
*/				}
				catch (Exception e)
				{
					
					e.printStackTrace();
				}
				finally
				{
				   dialog.dismiss();
				   ActivityManage.getInstance().distory();
			    }
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

	/*
	 * 
	 * 下面用于点击文本外隐藏键盘
	 */
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (getCurrentFocus() != null)
			{
				if (getCurrentFocus().getWindowToken() != null)
				{
					imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		progressDialog = null;
		sharedPerences = null;
		dicInfoBeans = null;
		name = null;
		service = null;
		database = null;
		intent = null;
		nameTxt = null;// 用户名
		passwordTxt = null;// 密码
		loginBtn = null;// 登录按扭
		cancelBtn = null;// 取消按扭
		rememberMeCb = null;// 记住我
		primarySurveyCb = null;// 我是主查勘员
		dispatchCb = null;// 接受查勘任务
		lossCb = null;// 接受定损任务
		agentDrivingCb = null;// 接受代驾任务
		imm = null;
		user = null;
		dialog = null;
	}

}
