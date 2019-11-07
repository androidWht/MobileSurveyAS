package com.sinosoft.ms.activity.task.survey;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.activity.task.image.MediaCenterAct;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.KindCodeData2;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.model.po.CacheSmGeneral;
import com.sinosoft.ms.model.po.SmGeneral;
import com.sinosoft.ms.service.impl.CaseCenterService;
import com.sinosoft.ms.service.impl.KindCodeDataService2;
import com.sinosoft.ms.service.impl.TaskCenterService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.BasicSurveyDatabase;
import com.sinosoft.ms.utils.db.CarDatasDatabase;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.LiabilityRadioDatabase;
import com.sinosoft.ms.utils.db.PersonDataDatabase;
import com.sinosoft.ms.utils.db.PolicyDatasDatabase;
import com.sinosoft.ms.utils.db.PropDataDatabase;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损系统 子系统名：查勘概要信息界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author yanzhipei
 * @createTime 2013-5-28 上午11:59:52
 */

public class SurveyGeneralAct extends Activity implements OnClickListener
{
	private Button okBtn, backBtn;

	private RadioButton bSubrogationFlagNO; // 是否代位求偿-否
	private RadioButton bSubrogationFlagYES; // 是否代位求偿-是
	private RadioButton reserveFlagNO; // 是否重大赔案-否
	private RadioButton reserveFlagYES; // 是否重大赔案-是
	private Spinner accidentTypeSp; // 事故类型
	private String Systemtime = "";
	private TextView registNoTV; // 报案号
	private TextView liscenseNoTV; // 车牌号
	private TextView informantTV; // 报案人
	private TextView recognizeeTV; // 被保人

	private String registNo; // 报案号
	private String registId; // 报案id号
	private RegistData registData;
	private SurveyTreatmentDatabase2 database;
	private ProgressDialogUtil progressDialog;
	private Dialog dialog, selectDialog;
	private final int SAVE_SUCCESS = 1100;
	private BasicSurvey basicSurvey;
	private Map<String, Map<String, String>> dataMap;// 数据字典集合
	private Map<String, Map<String, String>> localItemConf = null;
	private static final String COMPLICATEDSURVEY = "COMPLICATEDSURVEY"; // 复杂查勘
	private static final String SIMPLESURVEY = "SIMPLESURVEY"; // 简易查勘
	List<CarData> carDatas; // 车辆信息

	// 互碰自赔
	private RadioButton isMutableTouch_yes;
	private RadioButton isMutableTouch_no;

	private Button mImageUploadBtn, mPCProcessingBtn, mAgainBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_general);
		ActivityManage.getInstance().push(this);
		// 获取字典信息
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("item");
		registData = (RegistData) bundle.getSerializable("bean");
		registNo = intent.getStringExtra("registNo");
		registId = intent.getStringExtra("registId");
		dataMap = DictCashe.getInstant().getDict();
		localItemConf = AppConstants.getLocalItemConf();
		getSystemTime();
		setViewController(); // / 设置界面控件
		// setData(); // 设置界面数据
		startRequestAndSave(); // 获取车辆信息

	}

	/**
	 * 开启线程请求服务或者读取数据库
	 */
	public void startRequestAndSave()
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中....");
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					requestSurveyInfo();
					msg.what = 1;
				}
				catch (Exception e)
				{

					String message = e.getMessage();
					int result = 0;
					if (StringUtils.isNotEmpty(message) && (AppConstants.LOGIN_ERROR.equals(message) || AppConstants.LOGIN_FAIL.equals(message)))
					{
						result = AppConstants.NO_LOGIN_CODE;
					}
					else
					{
						result = 1000;
						LogRecoder.wirteException("survey_search", e);
					}
					msg.what = result;
					msg.obj = e.getMessage();
				}
				finally
				{
					handler.sendMessage(msg);
				}
			};

		}.start();

	}

	/**
	 * 更新视图
	 */
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			// Log.i("key", String.valueOf(msg.what));
			if (progressDialog != null)
			{
				progressDialog.dismiss();
			}
			switch (msg.what)
			{
			case 0:

				break;
			case 1:
				setData();
				break;
			case AppConstants.NO_LOGIN_CODE:
				Intent intent = new Intent();
				intent.setClass(SurveyGeneralAct.this, LoginAct.class);
				startActivity(intent);
				try
				{
					SurveyGeneralAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}

		};

	};

	/**
	 * 获取当前系统时间
	 */
	public void getSystemTime()
	{
		Date curDate = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Systemtime = formatter.format(curDate);
		// Log.i("System", Systemtime);
	}

	/**
	 * 检查系统时间
	 */
	public Boolean checkSystemTime()
	{
		if (Systemtime != null && !Systemtime.equals(""))
		{
			return true;
		}
		return false;
	}

	private boolean isSingleAccident()
	{
		this.carDatas = this.database.getCarDatas();
		if (this.carDatas != null && !this.carDatas.isEmpty())
		{
			String temp = "";
			for (CarData car : this.carDatas)
			{
				temp = SpinnerUtils.getValue(car.getLossItemType(), localItemConf.get("LossItemType"));
				if ("三者车".equals(temp))
				{
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * public void requestSurveyInfo() throws Exception { database =
	 * SurveyTreatmentDatabase2.getInstance();
	 * 
	 * if (!database.isExist(this, registNo)) {
	 * 
	 * CaseCenterService service = new CaseCenterService(null); SurveyInfo
	 * surveyInfo = service.querySurveyInfo(registNo); carDatas =
	 * surveyInfo.getCarDatas();// 车辆信息.i("请求查勘数据","取得数据"); } CarDatasDatabase
	 * cardata = new CarDatasDatabase(this); carDatas =
	 * cardata.selectCarData("car_data", registNo, null); //
	 * 车辆信息.i("请求查勘数据","取得数据");
	 * 
	 * }
	 */
	/**
	 * 请求服务或者读取数据库
	 * 
	 * @throws Exception
	 */
	public void requestSurveyInfo() throws Exception
	{
		database = SurveyTreatmentDatabase2.getInstance();

		if (!database.isExist(this, registNo))
		{

			CaseCenterService service = new CaseCenterService(null);
			SurveyInfo surveyInfo = service.querySurveyInfo(registNo, registId);
			BasicSurvey basicSurvey = surveyInfo.getBasicSurvey();
			// basicSurvey.setCheckDate(Systemtime);
			List<CarData> carDatas = surveyInfo.getCarDatas();// 车辆信息.i("请求查勘数据","取得数据");
			///PersonData personData = surveyInfo.getPersonData();// 人伤信息tch(Exception
			List<PersonData> personDatas = surveyInfo.getPersonDatas();
																// e){
			List<PolicyData> policyDatas = surveyInfo.getPolicyDatas();// 保单信息e.printStackTrace();
			List<PropData> proDatas = surveyInfo.getPropDatas();// 财产信息
			List<LiabilityRatio> LibbilityRadios = surveyInfo.getLiabilityRatios();
			List<RegistThirdCarData> registThirdCarDatas = surveyInfo.getRegistThirdCarDatas();
			List<KindCodeData2> kindCodeDatas = surveyInfo.getKindCodeDatas();

			database.setBasicSurvey(basicSurvey);
			database.setCarDatas(carDatas);
			///database.setPersonData(personData);
			database.setPersonDatas(personDatas);
			database.setPolicyDatas(policyDatas);
			database.setProDatas(proDatas);
			database.setLiabilityRatios(LibbilityRadios);
			database.setRegistThirdCarDatas(registThirdCarDatas);
			database.setKindCodeDatas(kindCodeDatas);
			database.save(this);

		}

		BasicSurveyDatabase basic = new BasicSurveyDatabase(this);
		CarDatasDatabase cardata = new CarDatasDatabase(this);
		PersonDataDatabase personDatabase = new PersonDataDatabase(this);
		PropDataDatabase propDatabase = new PropDataDatabase(this);
		PolicyDatasDatabase policyDatabase = new PolicyDatasDatabase(this);
		LiabilityRadioDatabase liabilityRadioDatabase = new LiabilityRadioDatabase(this);

		BasicSurvey basicSurvey = basic.selectBasicSurvey("basic_survey", registNo, null);
		basicSurvey.setCheckDate(DateTimeFactory.getInstance().getDateTime());
		basic.updateBasicSurvey("basic_survey", registNo, basicSurvey);
		// inserttime(basicSurvey);

		List<CarData> carDatas = cardata.selectCarData("car_data", registNo, null); // 车辆信息.i("请求查勘数据","取得数据");
		List<PolicyData> policyDatas = new ArrayList<PolicyData>();
		List<PropData> proDatas = propDatabase.selectPropDatas("prop_data", registNo);// 财产信息
		List<PersonData> personDatas= personDatabase.selectPersonData("person_data", registNo);
		
		
		List<LiabilityRatio> LibbilityRadios = new ArrayList<LiabilityRatio>();
		LibbilityRadios = liabilityRadioDatabase.selectLiablity("liablity_data", registNo);
		try
		{
			policyDatas = policyDatabase.selectPolicyData("policy_data", registNo);// 保单信息
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		KindCodeDataService2 kindCodeDataService = new KindCodeDataService2();
		List<KindCodeData2> kindCodeDatas = kindCodeDataService.getById(registNo);

		database.setBasicSurvey(basicSurvey);
		database.setCarDatas(carDatas);
		// database.setPersonData(personData);
		database.setPolicyDatas(policyDatas);
		database.setProDatas(proDatas);
		database.setPersonDatas(personDatas);
		database.setLiabilityRatios(LibbilityRadios);
		database.setKindCodeDatas(kindCodeDatas);

	}

	/** 设置界面数据 **/
	private void setData()
	{
		registNoTV.setText(registNo); // 报案号
		informantTV.setText(registData.getReportorName()); // 报案人
		database = SurveyTreatmentDatabase2.getInstance();
		List<CarData> carDatas = database.getCarDatas();
		CarData bCarData = null;// 标的车
		for (CarData carData : carDatas)
		{// 查找标的车
			carData.init();
			String car = carData.getLossItemType();
			if (StringUtils.isNotEmpty(car) && car.equals("050"))
			{
				bCarData = carData;// 标的车存在，跳出循环
				break;
			}
		}
		String linceNo = null;// 车牌号
		if (bCarData != null)
		{
			linceNo = bCarData.getLicenseNo();// 取得车牌号
		}
		if (StringUtils.isNotEmpty(linceNo))
		{
			liscenseNoTV.setText(linceNo); // 车牌号
		}

		List<PolicyData> policyDatas = database.getPolicyDatas();
		if (policyDatas != null && !policyDatas.isEmpty())
		{
			PolicyData policyData = policyDatas.get(0);
			if (policyData != null)
			{
				String policyName = policyData.getInsuredName();
				if (StringUtils.isNotEmpty(policyName))
				{
					recognizeeTV.setText(policyName); // 被保人
				}

			}
		}

		// 获取概要信息
		SmGeneral smGeneral = CacheSmGeneral.getInstant().getSmGeneral();
		smGeneral.init();
		if (this.registNo.equals(smGeneral.getRegistNo()))
		{
			// 事故类型 
			SpinnerUtils.setSpinnerData(this, accidentTypeSp, dataMap.get("AccidentDutyType"), SpinnerUtils.getKey(smGeneral.getAccidentType(), dataMap.get("AccidentDutyType")),""); // 事故类型
			// 代位求偿
			if (this.bSubrogationFlagYES.getText().toString().equals(smGeneral.getbSubrogationFlag()))
			{
				this.bSubrogationFlagYES.setChecked(true);
			}
			else
			{
				this.bSubrogationFlagNO.setChecked(true);
			}
			// 重大赔案
			if (this.reserveFlagYES.getText().toString().equals(smGeneral.getReserveFlag()))
			{
				this.reserveFlagYES.setChecked(true);
			}
			else
			{
				this.reserveFlagNO.setChecked(true);
			}
			// 互碰自赔
			if (this.isMutableTouch_yes.getText().toString().equals(smGeneral.getIsMutableTouch()))
			{
				this.isMutableTouch_yes.setChecked(true);
			}
			else
			{
				this.isMutableTouch_no.setChecked(true);
			}
		}
		else
		{
			// 事故类型 
			SpinnerUtils.setSpinnerData(this, accidentTypeSp, dataMap.get("AccidentDutyType"), "",""); // 事故类型
		}
	}

	public void inserttime(final BasicSurvey bean)
	{

		new Thread()
		{
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				super.run();
				BasicSurveyDatabase baiscDatabase = new BasicSurveyDatabase(getApplicationContext());
				try
				{
					// bean.setCheckDate(Systemtime);
					baiscDatabase.updateBasicSurvey("basic_survey", registNo, bean);

				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

			}

		};

	}

	/**
	 * 初始化视图
	 */
	public void setViewController()
	{

		// 互碰自赔
		this.isMutableTouch_yes = (RadioButton) super.findViewById(R.id.isMutableTouch_yes);
		this.isMutableTouch_no = (RadioButton) super.findViewById(R.id.isMutableTouch_no);

		this.registNoTV = (TextView) super.findViewById(R.id.registNo); // 报案号
		this.liscenseNoTV = (TextView) super.findViewById(R.id.liscenseNo); // 车牌号
		this.informantTV = (TextView) super.findViewById(R.id.informant); // 报案人
		this.recognizeeTV = (TextView) super.findViewById(R.id.recognizee); // 被保人

		this.bSubrogationFlagNO = (RadioButton) super.findViewById(R.id.bSubrogationFlag_no); // 是否代位求偿-否
		this.bSubrogationFlagYES = (RadioButton) super.findViewById(R.id.bSubrogationFlag_yes); // 是否代位求偿-是
		this.reserveFlagNO = (RadioButton) super.findViewById(R.id.reserveFlag_no); // 是否重大赔案-否
		this.reserveFlagYES = (RadioButton) super.findViewById(R.id.reserveFlag_yes); // 是否重大赔案-否
		this.accidentTypeSp = (Spinner) super.findViewById(R.id.accidentTypeSp); // 事故类型
		okBtn = (Button) findViewById(R.id.okBtn);
		backBtn = (Button) findViewById(R.id.backBtn);
		okBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	/** 数据校验 **/
	private String checkInfo()
	{
		if ("请选择".equals(this.accidentTypeSp.getSelectedItem().toString()))
		{
			ToastDialog.show(this, "事故类型不能为空", ToastDialog.ERROR);
			return "";
		}
		else if ("多方事故".equals(this.accidentTypeSp.getSelectedItem().toString()) || "其他".equals(this.accidentTypeSp.getSelectedItem().toString()))
		{
			return COMPLICATEDSURVEY;
		}
		if (this.bSubrogationFlagYES.isChecked())
		{// 是否代位求偿-是
			return COMPLICATEDSURVEY;
		}
		if (this.reserveFlagYES.isChecked())
		{ // 是否重大赔案-是
			return COMPLICATEDSURVEY;
		}
		// 是否互碰自赔
		if (this.isMutableTouch_yes.isChecked())
		{
			return COMPLICATEDSURVEY;
		}
		return SIMPLESURVEY;
	}

	public void showAlertDialog()
	{
		if (dialog != null)
		{
			dialog.dismiss();
			dialog = null;
		}
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.dialog_task_select, (ViewGroup) findViewById(R.id.selectdialog));

		mImageUploadBtn = (Button) layout.findViewById(R.id.button1);
		mImageUploadBtn.setText("仅拍照上传");
		mPCProcessingBtn = (Button) layout.findViewById(R.id.button2);
		mPCProcessingBtn.setText("转电脑处理");
		mAgainBtn = (Button) layout.findViewById(R.id.button3);
		mAgainBtn.setText("重新填写信息");
		mAgainBtn.setOnClickListener(this);
		mImageUploadBtn.setOnClickListener(this);
		mPCProcessingBtn.setOnClickListener(this);
		TextView message = (TextView) layout.findViewById(R.id.message);
		message.setText("此类案件暂不支持在移动设备上处理，请选择后续处理操作");

		selectDialog = CustomDialog.showTip(SurveyGeneralAct.this, "信息提示", layout);
	}

	/**
	 * PC处理
	 */
	public void changePC()
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("代位求偿任务申请电脑处理...");
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					TaskCenterService service = new TaskCenterService();
					registData.setStatus(8);
					registData.setRemark("电脑处理");
					service.changePCSurvey(registData, "电脑处理");
					msg.what = 1;
				}
				catch (Exception e)
				{
					msg.what = MyUtils.getErrorCode(e.getMessage());
					msg.obj = e.getMessage();
				}
				finally
				{
					PCHandler.sendMessage(msg);
				}
			}
		}.start();

	}

	Handler PCHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			final Intent intent = new Intent();
			if (progressDialog != null)
			{
				try
				{
					progressDialog.dismiss();
				}
				catch (Exception e)
				{
				}
			}
			switch (msg.what)
			{
			case 0:// 申请电脑任务 错误

				ToastDialog.show(SurveyGeneralAct.this, "" + msg.obj.toString(), ToastDialog.ERROR);
				break;
			case 1:// 申请电脑任务 正确

				if (dialog != null)
				{
					dialog.dismiss();
				}
				dialog = CustomDialog.show(SurveyGeneralAct.this, "信息提示", "申请处理成功", "确定", "", new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						try
						{
							if (dialog != null)
							{
								dialog.dismiss();
							}
							intent.putExtra("success", "success");
							setResult(RESULT_OK, intent);
							SurveyGeneralAct.this.finish();
							ActivityManage.getInstance().pop();
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, null);

				break;
			case AppConstants.NO_LOGIN_CODE:
				intent.setClass(SurveyGeneralAct.this, LoginAct.class);
				startActivity(intent);
				try
				{
					SurveyGeneralAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			}
		};
	};

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.okBtn:// 确定按钮事件
			String flag = this.checkInfo(); // 数据校验
			// boolean isSingleAcc = this.isSingleAccident();
			// if (COMPLICATEDSURVEY.equals(flag)
			// && "单方事故".equals(this.accidentTypeSp.getSelectedItem()
			// .toString()) && isSingleAcc) {
			// ToastDialog.show(this, "涉案车辆存在三者车，事故类型不能选择单方事故！",
			// ToastDialog.ERROR);
			// return;
			// }
			if (SIMPLESURVEY.equals(flag))
			{
				// 获取概要信息
				SmGeneral smGeneral = CacheSmGeneral.getInstant().getSmGeneral();
				// getSystemTime();
				// 报案号
				smGeneral.setRegistNo(this.registNo);
				// 事故类型
				smGeneral.setAccidentType(this.accidentTypeSp.getSelectedItem().toString());
				// 代位求偿
				if (this.bSubrogationFlagNO.isChecked())
				{
					smGeneral.setbSubrogationFlag(this.bSubrogationFlagNO.getText().toString());
				}
				else
				{
					smGeneral.setbSubrogationFlag(this.bSubrogationFlagYES.getText().toString());
				}
				// 互碰自赔
				if (this.isMutableTouch_no.isChecked())
				{
					smGeneral.setIsMutableTouch(this.isMutableTouch_no.getText().toString());
				}
				else
				{
					smGeneral.setIsMutableTouch(this.isMutableTouch_yes.getText().toString());
				}
				// 重大赔案
				if (this.reserveFlagNO.isChecked())
				{
					smGeneral.setReserveFlag(this.reserveFlagNO.getText().toString());
				}
				else
				{
					smGeneral.setReserveFlag(this.reserveFlagYES.getText().toString());
				}
				// 保存概要信息
				CacheSmGeneral.getInstant().setSmGeneral(smGeneral);

				// 简易查勘界面
				final Intent intent = getIntent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("bean", this.registData);
				intent.putExtra("registNo", registNo); // 报案号
				intent.putExtra("accidentType", this.accidentTypeSp.getSelectedItem().toString()); // 事故类型
				intent.putExtra("item", bundle);
				intent.putExtra("registId", registId);

				intent.putExtra("systemtime", Systemtime);

				/** test **/
				// new
				// AlertDialog.Builder(SmSurveyGeneralAct.this).setPositiveButton("aaa",
				// new DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface arg0, int arg1) {
				// intent.setClass(getApplicationContext(), SmSurveyAct.class);
				// startActivity(intent);
				// }
				// }).setNegativeButton("bbb", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface arg0, int arg1) {
				// intent.setClass(getApplicationContext(),
				// SmSurveyInfoAct.class);
				// startActivity(intent);
				// }
				// }).show();
				/** test end **/

				intent.setClass(this, SurveyAct.class);
				startActivity(intent);
				try
				{
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				finish();
			}
			else if (COMPLICATEDSURVEY.equals(flag))
			{
				showAlertDialog();
			}
			break;
		case R.id.backBtn:// 返回按钮事件
			try
			{
				this.finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		case R.id.button3:
		{
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
		}
			break;
		case R.id.button2:
		{
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			new AlertDialog.Builder(SurveyGeneralAct.this).setTitle("提示").setMessage("转电脑后，案件将不能在移动设备上继续处理!").setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					changePC();
				}
			}).show();
		}
			break;
		case R.id.button1:
		{
			if (selectDialog.isShowing())
			{
				selectDialog.dismiss();
			}
			Intent intent = new Intent();
			intent.setClass(SurveyGeneralAct.this, MediaCenterAct.class);
			intent.putExtra("fromtasklistitem", true);
			intent.putExtra("registNo", registNo);
			Bundle bundle = new Bundle();
			bundle.putSerializable("bean", registData);
			intent.putExtra("item", bundle);
			startActivity(intent);
		}
			break;
		}
	}

}
