package com.sinosoft.ms.activity.task.confirmdamage;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.service.impl.BasicVehicleService;
import com.sinosoft.ms.service.impl.ConfirmDamageSreachService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.adapter.SurveyPagerAdapter;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 系统名：移动查勘定损 子系统名：核损界面控制 著作权：COPYRIGHT (C) 2016 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author gongshihao
 * @createTime 上午10:15:47
 */

public class ConfirmDamageAct extends Activity implements OnPageChangeListener,
		OnCheckedChangeListener,OnClickListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	private com.sinosoft.ms.service.IConfirmDamageSreachService IConfirmDamageSreachService=null;
	private List<View> views;
	private ViewPager viewpager = null;
	private View testview1 = null;
	private View testview2 = null;
	private View testview3 = null;
	private View testview4 = null;
	private View testview5 = null;
	private View testview6 = null;
	private View testview7 = null;
	private View testview8 = null;
	private View testview9 = null;

	private String registNo = null;
	private String taskId = null;
	private String registId = null;

	private RegistData registData = null;
	private String reportorMobile = null;
	private String vehicleType = null;

	private InputMethodManager imm = null;

	private IBasicVehicleService basicVehicleService = null;
	private BasicVehicle basicVehicle = null;

	private String defLossDate = null;
	private String cetainLossType = null;
	private String repairFactoryType = null;
	private String repairFactoryCode = null;
	private String repairFactoryName = null;
	private String defSite = null;
	private String option = null;

	private SurveyPagerAdapter surveyPagerAdapter;

	private RadioGroup radioGroup = null;
	private Button backBtn=null;
	private static int currentPage;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_confirmdamage);
		ActivityManage.getInstance().push(this);

		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		registId = intent.getStringExtra("registId");
		reportorMobile = intent.getStringExtra("reportorMobile");
		Bundle bundle = intent.getBundleExtra("item");
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (bundle != null) {
			registData = (RegistData) bundle.getSerializable("bean");
		}
		if (registData == null) {
			registData = new RegistData();
			registData.init();
		}
		currentPage = 0;
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		backBtn = (Button) findViewById(R.id.treatmentBrackBtn);
		radioGroup.setOnCheckedChangeListener(this);
		backBtn.setOnClickListener(this);
		setViewPager();
		loadData();
	}

	private void setViewPager() {
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		views = new ArrayList<View>();
		getLayoutInflater();
		LayoutInflater inflater = LayoutInflater.from(this);
		testview1 = inflater.inflate(R.layout.layout_insurance_info_detail,
				null); // 1.保单详情
		testview2 = inflater.inflate(R.layout.confirmdamagetest, null);
		testview3 = inflater.inflate(R.layout.layout_loss_vehicle_type_sreach,
				null);
		testview4 = inflater.inflate(R.layout.layout_loss_vehicle_basic, null);
		testview5 = inflater.inflate(R.layout.layout_confirmdamage_basic, null);
		testview6 = inflater.inflate(R.layout.layout_loss_change_project_list,
				null);
		testview7 = inflater.inflate(R.layout.layout_loss_repair_item, null);
		testview8 = inflater.inflate(R.layout.layout_loss_assist_query_list,
				null);
		testview9 = inflater.inflate(R.layout.layout_loss_submit, null);
		views.add(testview1);
		views.add(testview2);
		if (checkVehicleType()) {
			views.add(testview4);
		} else {
			views.add(testview3);
		}
		views.add(testview5);
		views.add(testview6);
		views.add(testview7);
		views.add(testview8);
		views.add(testview9);
		surveyPagerAdapter = new SurveyPagerAdapter(this, views);
		viewpager.setAdapter(surveyPagerAdapter);
		viewpager.setOnPageChangeListener(this);
		setchildview();
	}
	private void loadData(){
	
		try {
			if (null == IConfirmDamageSreachService) {
				IConfirmDamageSreachService = new ConfirmDamageSreachService();
			}
			// deflossData = deflossDataService.getByTaskId(taskId);
			getTestData();

		} catch (Exception e) {
			// e.printStackTrace();
			ToastDialog.show(this, e.getMessage(), ToastDialog.ERROR);
		}
	}
	private void getTestData(){
		new Thread(){
			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				User user=UserCashe.getInstant().getUser();
				IConfirmDamageSreachService.TestData(user.getName(), registNo, registId);
			}
		}.start();
	}
	private void setchildview(){
		settestview4();
		settestview5();
		settestview6();
		settestview7();
		settestview8();
		settestview9();
	}

	// true标识已定型，false表示未定型
	private boolean checkVehicleType() {
		boolean bool = false;
		try {
			if (null == basicVehicleService) {
				basicVehicleService = new BasicVehicleService();
			}
			if (null == basicVehicle
					|| StringUtils.isEmpty(basicVehicle.getVehCertainCode())) {
				basicVehicle = basicVehicleService.getByRegistNo(taskId);
			}
			if (null != basicVehicle
					&& StringUtils.isNotEmpty(basicVehicle.getVehCertainCode())) {
				vehicleType = basicVehicle.getVehCertainCode();
			}
			if (StringUtils.isNotEmpty(vehicleType) || // 调度时填写了车型，也需要重新定。--临时
					(StringUtils.isNotEmpty(cetainLossType) && cetainLossType
							.equals("一次性协议定损"))) {
				bool = true;

			} else {
				bool = false;
			}
		} catch (Exception e) {

		} finally {
			return bool;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setRadioButton(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
	 * .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		// TODO Auto-generated method stub
		currentPage = viewpager.getCurrentItem();
		int index = 0;
		switch (checkedId) {
		case R.id.radio_button0:
			index = 0;
			break;
		case R.id.radio_button1:
			index = 1;
			break;
		case R.id.radio_button2:
			index = 2;
			break;
		case R.id.radio_button3:
			index = 3;
			break;
		case R.id.radio_button4:
			index = 4;
			break;
		case R.id.radio_button5:
			index = 5;
			break;
		case R.id.radio_button6:
			index = 6;
			break;
		case R.id.radio_button7:
			index = 7;
			break;
		case R.id.radio_button8:
			index = 8;
			break;
		default:
			break;
		}
		viewpager.setCurrentItem(index);
	}
	
	private TextView reportnumberText,cetainLossTypeTv;
	
	private void settestview5(){
		reportnumberText=(TextView) testview5.findViewById(R.id.registNoTxt);
		cetainLossTypeTv=(TextView) testview5.findViewById(R.id.cetainLossTypeTv);
		cetainLossTypeTv.setText("修复定损");
		reportnumberText.setText(registNo);
	}
	
	
	private Button definedChangeProjectBtn,addChangeProjectBtn;
	private void settestview6() {
		definedChangeProjectBtn=(Button) testview6.findViewById(R.id.definedChangeProjectBtn);
		addChangeProjectBtn=(Button) testview6.findViewById(R.id.addChangeProjectBtn);
		definedChangeProjectBtn.setVisibility(View.GONE);
		addChangeProjectBtn.setVisibility(View.GONE);
	}
	
	
	private Button addBtn;
	private void settestview7() {
		addBtn=(Button) testview7.findViewById(R.id.addBtn);
		addBtn.setVisibility(View.GONE);
	}
	
	private Button addAssistBtn,addDefineAssistBtn;
	private void settestview8(){
		addAssistBtn=(Button) testview8.findViewById(R.id.addAssistBtn);
		addDefineAssistBtn=(Button) testview8.findViewById(R.id.addDefineAssistBtn);
		addDefineAssistBtn.setVisibility(View.GONE);
		addAssistBtn.setVisibility(View.GONE);
	}
	
	private EditText sumChgCompFeeExt,sumRepairFeeExt,sumMaterialFeeExt
	,sumLossFeeExt,sumRemnantExt,sumRescueFeeExt;
	private Button upload_setloss_btn,loss_bluetoothPrintBtn;
	private void settestview9(){
		sumChgCompFeeExt=(EditText) testview9.findViewById(R.id.sumChgCompFeeExt);
		sumRepairFeeExt=(EditText) testview9.findViewById(R.id.sumRepairFeeExt);
		sumMaterialFeeExt=(EditText) testview9.findViewById(R.id.sumMaterialFeeExt);
		sumLossFeeExt=(EditText) testview9.findViewById(R.id.sumLossFeeExt);
		sumRemnantExt=(EditText) testview9.findViewById(R.id.sumRemnantExt);
		sumRescueFeeExt=(EditText) testview9.findViewById(R.id.sumRescueFeeExt);
		loss_bluetoothPrintBtn=(Button) testview9.findViewById(R.id.loss_bluetoothPrintBtn);
		upload_setloss_btn=(Button) testview9.findViewById(R.id.upload_setloss_btn);
		sumChgCompFeeExt.setEnabled(false);
		sumRepairFeeExt.setEnabled(false);
		sumMaterialFeeExt.setEnabled(false);
		sumLossFeeExt.setEnabled(false);
		sumRemnantExt.setEnabled(false);
		sumRescueFeeExt.setEnabled(false);
		loss_bluetoothPrintBtn.setText("下发修改");
		upload_setloss_btn.setText("审核通过");
	}

	private TextView t1;

	private void settestview4() {
		t1 = (TextView) testview4.findViewById(R.id.caseCenterTitle);
		t1.setVisibility(View.GONE);
	}

	private void setRadioButton(int toIndex) {
		radioGroup.getChildAt(0).setBackgroundResource(
				R.drawable.sm_step_highlight_01);
		radioGroup.getChildAt(1).setBackgroundResource(
				R.drawable.sm_step_highlight_02);
		radioGroup.getChildAt(2).setBackgroundResource(
				R.drawable.sm_step_highlight_03);
		radioGroup.getChildAt(3).setBackgroundResource(
				R.drawable.sm_step_highlight_04);
		radioGroup.getChildAt(4).setBackgroundResource(
				R.drawable.sm_step_highlight_05);
		radioGroup.getChildAt(5).setBackgroundResource(
				R.drawable.sm_step_highlight_06);
		radioGroup.getChildAt(6).setBackgroundResource(
				R.drawable.sm_step_highlight_07);
		radioGroup.getChildAt(7).setBackgroundResource(
				R.drawable.sm_step_highlight_08);
		// 保单详情
		// radioGroup.getChildAt(8).setBackgroundResource(
		// R.drawable.sm_step_highlight_09);
		RadioButton radio = (RadioButton) radioGroup.getChildAt(toIndex);
		radio.setChecked(true);
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			switch (toIndex) {
			case 0:
				radio.setBackgroundResource(R.drawable.sm_step_01);
				break;
			case 1:
				radio.setBackgroundResource(R.drawable.sm_step_02);
				break;
			case 2:
				radio.setBackgroundResource(R.drawable.sm_step_03);
				break;
			case 3:
				radio.setBackgroundResource(R.drawable.sm_step_04);
				break;
			case 4:
				radio.setBackgroundResource(R.drawable.sm_step_05);
				break;
			case 5:
				radio.setBackgroundResource(R.drawable.sm_step_06);
				break;
			case 6:
				radio.setBackgroundResource(R.drawable.sm_step_07);
				break;
			case 7:
				radio.setBackgroundResource(R.drawable.sm_step_08);
				break;
			// case 8:
			// radio.setBackgroundResource(R.drawable.sm_step_09);
			// break;
			}
		}
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
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

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.treatmentBrackBtn:
			
			try {
				this.finish();
				ActivityManage.getInstance().pop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;

		
		}
	}
}
