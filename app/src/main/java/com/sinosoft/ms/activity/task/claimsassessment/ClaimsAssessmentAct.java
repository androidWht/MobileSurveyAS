package com.sinosoft.ms.activity.task.claimsassessment;

import java.util.ArrayList;
import java.util.List;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.service.impl.BasicVehicleService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.SurveyPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2016 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author gongshihao
 * @createTime 下午4:27:36
 */

public class ClaimsAssessmentAct extends Activity implements OnCheckedChangeListener,OnPageChangeListener{

	private ViewPager Viewpager=null;
	private SurveyPagerAdapter SurveyPagerAdapter=null;
	private RadioGroup radioGroup = null;
	private static int currentPage;
	private View testview1;
	private View testview2;
	private View testview3;
	private View testview4;
	private View testview5;
	private View testview6;
	private View testview7;
	private View testview8;


	private IBasicVehicleService basicVehicleService = null;
	private BasicVehicle basicVehicle = null;

	private String registNo = null;
	private String taskId = null;
	private String registId = null;

	private InputMethodManager imm = null;
	
	private RegistData registData = null;
	private String reportorMobile = null;
	private String vehicleType = null;

	private String defLossDate = null;
	private String cetainLossType = null;
	private String repairFactoryType = null;
	private String repairFactoryCode = null;
	private String repairFactoryName = null;
	private String defSite = null;
	private String option = null;

	private List<View>views;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_claims_assessment);
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
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(this);
		setViewPager();
	}
	
	
	private void setViewPager(){
		Viewpager=(ViewPager) findViewById(R.id.viewpager);
		views = new ArrayList<View>();
		getLayoutInflater();
		LayoutInflater inflater=LayoutInflater.from(this);
		testview1=inflater.inflate(R.layout.layout_insurance_info_detail,
				null);
		testview2=inflater.inflate(R.layout.confirmdamagetest,
				null);
		testview3=inflater.inflate(R.layout.layout_loss_vehicle_type_sreach,
				null);
		testview4=inflater.inflate(R.layout.layout_loss_vehicle_basic, null);
		testview5=inflater.inflate(R.layout.confirmdamagetest,
				null);
		testview6=inflater.inflate(R.layout.confirmdamagetest,
				null);
		testview7=inflater.inflate(R.layout.confirmdamagetest,
				null);
		testview8=inflater.inflate(R.layout.confirmdamagetest,
				null);
		views.add(testview1);
		views.add(testview2);
		views.add(testview3);
		
		if(checkVehicleType()){
			views.add(testview4);
		}else{
			views.add(testview3);
		}
		views.add(testview5);
		views.add(testview6);
		views.add(testview7);
		views.add(testview8);
		SurveyPagerAdapter =new SurveyPagerAdapter(this, views);
		Viewpager.setAdapter(SurveyPagerAdapter);
		Viewpager.setOnPageChangeListener(this);
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


	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setRadioButton(arg0);
	}


	/* (non-Javadoc)
	 * @see android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android.widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		currentPage = Viewpager.getCurrentItem();
		int index = 0;
		switch (arg1) {
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
		Viewpager.setCurrentItem(index);
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
}

