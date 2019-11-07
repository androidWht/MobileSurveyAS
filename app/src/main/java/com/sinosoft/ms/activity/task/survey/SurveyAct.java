package com.sinosoft.ms.activity.task.survey;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyBasicInfoFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyMediaCenterFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyPersonFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyPolicyInfoFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyPropFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveySubmitFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyVehicleFrag;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.KindCodeData2;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.service.impl.CaseCenterService;
import com.sinosoft.ms.service.impl.KindCodeDataService2;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.FragmentAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.db.BasicSurveyDatabase;
import com.sinosoft.ms.utils.db.CarDatasDatabase;
import com.sinosoft.ms.utils.db.LiabilityRadioDatabase;
import com.sinosoft.ms.utils.db.PersonDataDatabase;
import com.sinosoft.ms.utils.db.PolicyDatasDatabase;
import com.sinosoft.ms.utils.db.PropDataDatabase;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：查勘任务主界面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:15:08
 */

public class SurveyAct extends FragmentActivity implements OnClickListener {

	private SurveyPolicyInfoFrag policyFrag; // 1.保单信息页面
	private SurveyMediaCenterFrag mediaFrag; // 2.影像中心页面
	private SurveyBasicInfoFrag basicFrag; // 3.基本信息页面
	private SurveyVehicleFrag vehicleFrag; // 4.车辆信息页面
	///private SurveyPropertyFrag propertyFrag; // 5.财产损失页面
	private SurveyPropFrag  propFrag;     //5.财产损失页面
	private SurveyPersonFrag personFrag; //    6.人伤损失页面
	
	private SurveySubmitFrag submitFrag; // 7.信息提交页面

	private InputMethodManager imm;

	private Button treatmentBrackBtn;

	private ProgressDialogUtil progressDialog;
	private TextView caseCenterTitle;
	// 翻页控件
	private ViewPager viewPager;
	// 导航按钮
	private RadioGroup radioGroup;
	// 页面列表
	private List<Fragment> fragList;
	private FragmentAdapter fragmentAdapter;
	private MyOnPageChangeListener pageChangeListener;
	private MyOnCheckedChangeListener checkedChangeListener;
	// 当前页码
	private int currentPage;
	// 系统时间
	private String Systemtime = "";
	// 报案号
	private String registNo = "";
	// 报案ID
	private String registId = "";
	// 报案信息
	private RegistData registData;
	// 事故类型
	private String accidentType;
	// 是否可编辑
	public boolean canEdit = true;
	
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_survey);
		initView();
		initData();
	}

	private void initView() {
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		treatmentBrackBtn = (Button) findViewById(R.id.treatmentBrackBtn);
		treatmentBrackBtn.setOnClickListener(this);
		initViewPager();
	}

	/**
	 * 初始化页面
	 */
	private void initViewPager() {
		caseCenterTitle = (TextView) findViewById(R.id.caseCenterTitle);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		// 1.保单信息页面
		policyFrag = new SurveyPolicyInfoFrag();
		// 2.影像中心页面
		mediaFrag = new SurveyMediaCenterFrag();
		// 3.基本信息页面
		basicFrag = new SurveyBasicInfoFrag();
		// 4.车辆信息页面
		vehicleFrag = new SurveyVehicleFrag();

		// 5.财产损失页面
		///propertyFrag = new SurveyPropertyFrag();
		propFrag = new SurveyPropFrag();
		
		// 6.人伤
		personFrag =  new SurveyPersonFrag();
		// 7.信息提交页面
		submitFrag = new SurveySubmitFrag();
		fragList = new ArrayList<Fragment>();
		fragList.add(policyFrag); //1.保单信息页面
		fragList.add(mediaFrag);  //2.影像中心页面
		
	    //fragList.add(peopleFrag);  //6.人伤
		
		fragList.add(basicFrag);  //3.基本信息页面
		

		
		fragList.add(vehicleFrag); //4.车辆信息页面
		
		///fragList.add(propertyFrag); //5.财产损失页面
		fragList.add(propFrag);
		
		fragList.add(personFrag);  //6.人伤
		fragList.add(submitFrag);  //7.信息提交页面
		fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragList);
		viewPager.setAdapter(fragmentAdapter);
		viewPager.setCurrentItem(0);
		pageChangeListener = new MyOnPageChangeListener();
		checkedChangeListener = new MyOnCheckedChangeListener();
		viewPager.setOnPageChangeListener(pageChangeListener);
		radioGroup.setOnCheckedChangeListener(checkedChangeListener);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 接收从上一个页面传来的数据
		receiveData();
		loadData();
	}

	private void receiveData() {
		// dataMap = DictCashe.getInstant().getDict();
		// localItemConf = AppConstants.getLocalItemConf();
		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		registId = intent.getStringExtra("registId");
		Systemtime = intent.getStringExtra("systemtime");
		accidentType = intent.getStringExtra("accidentType"); // 事故类型
		canEdit = intent.getBooleanExtra("it", true);
		if (registNo == null || registNo.trim().equals("")) {
			registNo = "";
		}
		if (registId == null || registId.trim().equals("")) {
			registId = "";
		}
		Bundle bundle = intent.getBundleExtra("item");
		if (bundle != null) {
			registData = (RegistData) bundle.getSerializable("bean");
		}

		if (registData == null) {
			registData = new RegistData();

		}
		registData.init();
	}

	/**
	 * 开启线程请求服务或者读取数据库
	 */
	public void loadData() {
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中....");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					requestSurveyInfo();
					msg.what = 1;
				} catch (Exception e) {

					String message = e.getMessage();
					int result = 0;
					if (StringUtils.isNotEmpty(message)
							&& (AppConstants.LOGIN_ERROR.equals(message) || AppConstants.LOGIN_FAIL
									.equals(message))) {
						result = AppConstants.NO_LOGIN_CODE;
					} else {
						result = 1000;
						LogRecoder.wirteException("survey_search", e);
					}
					msg.what = result;
					msg.obj = e.getMessage();
				} finally {
					handler.sendMessage(msg);
				}
			};

		}.start();

	}

	/**
	 * 请求服务或者读取数据库
	 * 
	 * @throws Exception
	 */
	public void requestSurveyInfo() throws Exception {
		SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2
				.getInstance();

		if (!database.isExist(this, registNo)) 
		{

			CaseCenterService service = new CaseCenterService(null);
			SurveyInfo surveyInfo = service.querySurveyInfo(registNo, registId);
			BasicSurvey basicSurvey = surveyInfo.getBasicSurvey();
			basicSurvey.setCheckDate(Systemtime);
			List<CarData> carDatas = surveyInfo.getCarDatas();// 车辆信息.i("请求查勘数据","取得数据");
			//PersonData personData = surveyInfo.getPersonData();// 人伤信息tch(Exception
			List<PersonData> personDatas = surveyInfo.getPersonDatas();
																// e){
			List<PolicyData> policyDatas = surveyInfo.getPolicyDatas();// 保单信息//e.printStackTrace();
			List<PropData> proDatas = surveyInfo.getPropDatas();// 财产信息
			List<LiabilityRatio> LibbilityRadios = surveyInfo
					.getLiabilityRatios();
			List<RegistThirdCarData> registThirdCarDatas = surveyInfo
					.getRegistThirdCarDatas();
			List<KindCodeData2> kindCodeDatas = surveyInfo.getKindCodeDatas();

			database.setBasicSurvey(basicSurvey);
			database.setCarDatas(carDatas);
			//database.setPersonData(personData);
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
		LiabilityRadioDatabase liabilityRadioDatabase = new LiabilityRadioDatabase(
				this);

		BasicSurvey basicSurvey = basic.selectBasicSurvey("basic_survey",
				registNo, null);
		List<CarData> carDatas = cardata.selectCarData("car_data", registNo,
				null); // 车辆信息.i("请求查勘数据","取得数据");

		/*
		 * PersonData personData = personDatabase.selectPersonData(
		 * "person_data", registNo);
		 */// 人伤信息t
		List<PolicyData> policyDatas = new ArrayList<PolicyData>();
		List<PropData> proDatas = propDatabase.selectPropDatas("prop_data",
				registNo);// 财产信息

		List<LiabilityRatio> LibbilityRadios = new ArrayList<LiabilityRatio>();
		LibbilityRadios = liabilityRadioDatabase.selectLiablity(
				"liablity_data", registNo);
		try {
			policyDatas = policyDatabase.selectPolicyData("policy_data",
					registNo);// 保单信息
		} catch (Exception e) {
			// e.printStackTrace();
		}
		KindCodeDataService2 kindCodeDataService = new KindCodeDataService2();
		List<KindCodeData2> kindCodeDatas = kindCodeDataService
				.getById(registNo);
		basicSurvey.setCheckDate(Systemtime);
		database.setBasicSurvey(basicSurvey);
		database.setCarDatas(carDatas);
		// database.setPersonData(personData);
		database.setPolicyDatas(policyDatas);
		database.setProDatas(proDatas);
		database.setLiabilityRatios(LibbilityRadios);
		database.setKindCodeDatas(kindCodeDatas);
	}

	/**
	 * 将数据暂存到静态变量中
	 */
	public void saveToStatic() {
		SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2
				.getInstance();
		BasicSurvey basicSurvey = basicFrag.getBasicSurvey();
		List<PolicyData> policyDatas = policyFrag.getPolicyDatas();
		List<CarData> carDatas = vehicleFrag.getCarDatas();
		List<LiabilityRatio> liabilityRatios = vehicleFrag.getAbilityRatioData();
		///List<PropData> propDatas = propertyFrag.getPropData();
		List<PersonData> personDatas = personFrag.getPersonData();
		
		List<PropData> propDatas = propFrag.getPropData();
		
		//Log.i("systemTime", basicSurvey.getCheckDate());

		// getAbilityRatioData();
		/** 1.保存保单信息 **/
		if (null != policyDatas) {
			database.setPolicyDatas(policyDatas);
		}

		/** 3.保存基本数据 **/
		if (null != basicSurvey) {
			database.setBasicSurvey(basicSurvey);
		}

		/** 4.保存车辆信息 **/
		if (null != carDatas) {
			database.setCarDatas(carDatas);
			database.setLiabilityRatios(liabilityRatios);
		}

		/** 5.财产损失 **/
		if (null != propDatas) {
			database.setProDatas(propDatas);
		}
		
		/** 人伤损失 **/
		if (null != personDatas) {
			database.setPersonDatas(personDatas);
		}
		
		// database.setLiabilityRatios(liabilityRatios);
	}

	/**
	 * 将数据存放到数据库中
	 */
	public void saveToDatabase() {
		new Thread() {

			@Override
			public void run() {
				super.run();
				SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2
						.getInstance();
				try {
					database.update(SurveyAct.this, registNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.treatmentBrackBtn:
			finish();
			break;
		}
	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int toIndex) {
			// 数据保存
			new Thread() {
				public void run() {
					try {
						saveToStatic();
					} catch (Exception e) {
						// e.printStackTrace();
					}
				};
			}.start();
			// Log.i("SurveyAct", String.valueOf(currentPage));
			if (toIndex > currentPage) {
				String errorMsg = null;
				// 如果toIndex大于第二页,而currentPage小于或等于第二页，则验证查勘基本信息
				if (toIndex > 2 && currentPage <= 2) {
					errorMsg = basicFrag.basicVerify();
					if (StringUtils.isNotEmpty(errorMsg)) {
						currentPage = 2;
					}

				}
				// 如果toIndex大于第三页，而currentPage小于或等于第三页，则验证涉案车辆信息
				if (StringUtils.isEmpty(errorMsg)) {
					if (toIndex > 3 && currentPage <= 3) {
						errorMsg = vehicleFrag.vehicleVerify();
						if (StringUtils.isNotEmpty(errorMsg)) {
							currentPage = 3;
						}
					}
				}
				
				// 物损
				if (StringUtils.isEmpty(errorMsg)) {
					if (toIndex > 4 && currentPage <= 4) {
						errorMsg = propFrag.propVerify();
						if (StringUtils.isNotEmpty(errorMsg)) {
							currentPage = 4;
						}
					}
				}
				//人伤
				if (StringUtils.isEmpty(errorMsg)) {
					if (toIndex > 5 && currentPage <= 5) {
						errorMsg = personFrag.personVerify();
						if (StringUtils.isNotEmpty(errorMsg)) {
							currentPage = 5;
						}
					}
				}				
				if (StringUtils.isNotEmpty(errorMsg)) {
					CustomDialog.show(SurveyAct.this, "", errorMsg);
					toIndex = currentPage;
				}
			}

			viewPager.setOnPageChangeListener(null);
			viewPager.setCurrentItem(toIndex, false);
			viewPager.setOnPageChangeListener(pageChangeListener);
			setRadioButton(toIndex);
			radioGroup.setOnCheckedChangeListener(null);
			radioGroup.setOnCheckedChangeListener(checkedChangeListener);
			currentPage = toIndex;
		}

	}

	class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int checkedId) {
			currentPage = viewPager.getCurrentItem();
			int index = 0;
			switch (checkedId) {
			case R.id.radio_button0:
				caseCenterTitle.setText("保单详情");
				index = 0;
				break;
			case R.id.radio_button1:
				caseCenterTitle.setText("影像中心");
				index = 1;
				break;
			case R.id.radio_button2:
				caseCenterTitle.setText("查勘信息");
				index = 2;
				break;
			case R.id.radio_button4:
				caseCenterTitle.setText("涉案车辆信息");
				index = 3;
				break;
			case R.id.radio_button5:
				caseCenterTitle.setText("财产损失");
				index = 4;
				break;
			case R.id.radio_button6:
				caseCenterTitle.setText("人伤损失");
				index = 5;
				break;				
			case R.id.radio_button7:
				caseCenterTitle.setText("查勘信息提交");
				index = 6;
				break;
			default:
				break;
			}
			viewPager.setCurrentItem(index);
		}
	}

	/**
	 * 设置导航栏图标
	 * 
	 * @param toIndex
	 */
	private void setRadioButton(int toIndex) {
		radioGroup.getChildAt(0).setBackgroundResource(R.drawable.sm_survey_step_highlight_01);
		radioGroup.getChildAt(1).setBackgroundResource(	R.drawable.sm_survey_step_highlight_02);
		radioGroup.getChildAt(2).setBackgroundResource(R.drawable.sm_survey_step_highlight_03);
		radioGroup.getChildAt(3).setBackgroundResource(R.drawable.sm_survey_step_highlight_04);
		radioGroup.getChildAt(4).setBackgroundResource(R.drawable.sm_survey_step_highlight_05);
		radioGroup.getChildAt(5).setBackgroundResource(R.drawable.sm_survey_step_highlight_06);
		radioGroup.getChildAt(6).setBackgroundResource(R.drawable.sm_step_highlight_07);
		RadioButton radio = (RadioButton) radioGroup.getChildAt(toIndex);
		radio.setChecked(true);
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			switch (toIndex) {
			case 0:
				radio.setBackgroundResource(R.drawable.sm_survey_step_01);
				break;
			case 1:
				radio.setBackgroundResource(R.drawable.sm_survey_step_02);
				break;
			case 2:
				radio.setBackgroundResource(R.drawable.sm_survey_step_03);
				break;
			case 3:
				radio.setBackgroundResource(R.drawable.sm_survey_step_04);
				break;
			case 4:
				radio.setBackgroundResource(R.drawable.sm_survey_step_05);
				break;
			case 5:
				radio.setBackgroundResource(R.drawable.sm_survey_step_06);
				break;
			case 6:
				radio.setBackgroundResource(R.drawable.sm_step_07);
				break;
				
			}
		}
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
			policyFrag.refreshData();
			mediaFrag.refreshData(registData, registNo, registId);
			basicFrag.refreshData(accidentType, registData);
			vehicleFrag.refreshData(registNo, accidentType,registData);
			propFrag.refreshData(registNo);
			///propertyFrag.refreshData(registNo);
			
			submitFrag.refreshData(registData, registNo, registId);
			
		}

	};

	// 在基本查勘信息页面中调用此方法
	public void setAccidentCause(String accidentCause) {
		vehicleFrag.setAccidentCause(accidentCause);
	}

	// 移动到影像中心
	public void scrollToMedia() {
		if (null != viewPager) {
			viewPager.setCurrentItem(1);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (getCurrentFocus() != null) {
			if (getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(this.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		saveToDatabase();
		policyFrag = null;
		mediaFrag = null;
		basicFrag = null;
		vehicleFrag = null;
		//propertyFrag = null;
		propFrag = null;
		submitFrag = null;
	}

}
