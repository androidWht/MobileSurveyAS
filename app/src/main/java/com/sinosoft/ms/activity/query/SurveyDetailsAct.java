package com.sinosoft.ms.activity.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.PropDetailData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.service.impl.CaseCenterService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.Utility;
import com.sinosoft.ms.utils.adapter.BaseSimpleAdapter;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.ListBaseAdapter;
import com.sinosoft.ms.utils.adapter.SurveyVehicleAdapter;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.BasicSurveyDatabase;
import com.sinosoft.ms.utils.db.CarDatasDatabase;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.LiabilityRadioDatabase;
import com.sinosoft.ms.utils.db.PolicyDatasDatabase;
import com.sinosoft.ms.utils.db.PropDataDatabase;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：查勘处理详情 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author yanzhipei
 * @createTime 下午1:14:30
 */

public class SurveyDetailsAct extends Activity
{

	/** 基本信息控件 **/
	private TextView accident_handle_type;// 事故处理类型
	private TextView accident_cause; // 出险原因
	private TextView accident_type; // 事故类型
	private TextView loss_type; // 损失类别
	private TextView survey_addr; // 查勘地点
	private TextView survey_date_damage; // 查勘日期
	private TextView report_person; // 查勘员
	private TextView mapping_class; // 查勘类别
	private TextView first_site_survey;// 第一现场查勘
	private TextView danger_place;// 出险地点
	private TextView accident_processing_department;// 事故处理部门
	private TextView touch_for;// 互碰自赔
	private TextView survey_idea;// 勘查意见

	/** 涉案车辆控件 **/
	private TextView lossItemType; // 损失类型
	private TextView licenseNo; // 车牌号
	private TextView carOwner; // 车主
	private TextView engineNo; // 发动机号
	private TextView vinNo; // vin码
	private TextView licenseType; // 车牌种类
	private TextView liabilityType; // 交强险责任类型

	/** 人伤信息控件 **/
	// private TextView lossParty; // 损失归属方
	// private TextView severeNum; // 重伤人员
	// private TextView minor; // 轻伤人员
	// private TextView deathNum; // 死亡人数
	// private TextView sumLossFee; // 估损金额
	// private TextView rescueFee; // 施救费用
	// private TextView checkDate; // 查勘日期
	// private TextView checkSite; // 查勘地点
	// private TextView rescueContext; // 施救过程

	private Button treatmentBrackBtn; // 返回按钮
	private ProgressDialogUtil progressDialog;
	private ExpandableListView vehicleInvolvedList;
	private ListView liabilityRadioList;
	private BaseSimpleAdapter liabilityAdapter;

	private Map<String, Map<String, String>> localItemConf;
	private String registNo; // 查勘编号
	private String registId; // 报案id号
	private RegistData registData; // 查勘对象

	private SurveyTreatmentDatabase2 database;
	private BasicSurvey basicSurvey; // 基本信息
	private PersonData personData; // 人伤信息
	private List<CarData> carDatas; // 涉案车辆信息
	private List<PropData> proDatas; // 财产信息
	private List<LiabilityRatio> LibbilityRadios; // 责任信息
	private List<PolicyData> policyDatas;
	private Map<String, Map<String, String>> dataMap;// 数据字典集合

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		accident_handle_type = null;// 事故处理类型
		accident_cause = null; // 出险原因
		accident_type = null; // 事故类型
		loss_type = null; // 损失类别
		survey_addr = null; // 查勘地点
		survey_date_damage = null; // 查勘日期
		report_person = null; // 查勘员
		mapping_class = null; // 查勘类别
		first_site_survey = null;// 第一现场查勘
		danger_place = null;// 出险地点
		accident_processing_department = null;// 事故处理部门
		touch_for = null;// 互碰自赔
		survey_idea = null;// 勘查意见
		lossItemType = null; // 损失类型
		licenseNo = null; // 车牌号
		engineNo = null; // 发动机号
		vinNo = null; // vin码
		licenseType = null; // 车牌种类
		liabilityType = null; // 交强险责任类型
		// lossParty = null; // 损失归属方
		// severeNum = null; // 重伤人员
		// minor = null; // 轻伤人员
		// checkDate = null; // 查勘日期
		// checkSite = null; // 查勘地点
		// rescueContext = null; // 施救过程
		treatmentBrackBtn = null; // 返回按钮
		progressDialog = null;
		vehicleInvolvedList = null;
		liabilityRadioList = null;
		liabilityAdapter = null;
		localItemConf = null;
		registNo = null; // 查勘编号
		registData = null; // 查勘对象
		database = null;
		basicSurvey = null; // 基本信息
		personData = null; // 人伤信息
		carDatas = null; // 涉案车辆信息
		proDatas = null; // 财产信息
		LibbilityRadios = null; // 责任信息
		policyDatas = null;
		super.onDestroy();
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
					msg.what = MyUtils.getErrorCode(e.getMessage());
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
			switch (msg.what)
			{
			case 1:
				if (progressDialog != null)
				{
					progressDialog.dismiss();
				}
				loadBasicData(); // 加载基本信息
				// loadPersonInjuredData(); // 加载人伤信息
				try
				{
					loadCarData(); // 加载涉案车辆信息
					loadPropDamageData(); // 加载财产损失信息
					loadLiabilityData(); // 加载责任比例信息
					// loadInsuranceData(); // 加载保单信息
				}
				catch (Exception e)
				{

				}
				break;
			default:
				break;
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_detail);
		ActivityManage.getInstance().push(this);
		localItemConf = AppConstants.getLocalItemConf();// 获取基本常量信息
		dataMap = DictCashe.getInstant().getDict(); // 获取字典信息
		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo"); // 获取查勘编号
		if (registNo == null || registNo.trim().equals(""))
		{
			registNo = "";
		}
		registId = intent.getStringExtra("registId"); // 获取查勘编号
		if (registId == null || registId.trim().equals(""))
		{
			registId = "";
		}
		Bundle bundle = intent.getBundleExtra("item"); // 获取Bundle对象
		if (bundle != null)
		{
			registData = (RegistData) bundle.getSerializable("bean"); // 获取查勘对象信息
		}
		if (registData == null)
		{
			registData = new RegistData();
		}
		this.registData.init(); // 格式化数据
		this.setViewController();// 初始化视图
		try
		{
			this.startRequestAndSave(); // 请求数据或读取数据库
		}
		catch (Exception e)
		{

		}
	}

	/** 加载基本信息数据 **/
	public void loadBasicData()
	{
		this.accident_handle_type.setText(SpinnerUtils.getValue(basicSurvey.getManageType(), dataMap.get("AccidentManageType"))); // 事故处理类型
		this.accident_cause.setText(SpinnerUtils.getValue(basicSurvey.getDamageCode(), dataMap.get("DamageCode"))); // 出险原因
		this.accident_type.setText(SpinnerUtils.getValue(basicSurvey.getDamageCaseCode(), dataMap.get("AccidentDutyType"))); // 事故类型
		this.loss_type.setText(SpinnerUtils.getValue(basicSurvey.getLossType(), dataMap.get("LossType"))); // 损失类别
		this.survey_addr.setText(basicSurvey.getCheckSite()); // 查勘地点
		this.survey_date_damage.setText(basicSurvey.getCheckDate()); // 查勘日期
		this.report_person.setText(basicSurvey.getCheckerName()); // 查勘员
		this.mapping_class.setText(SpinnerUtils.getValue(basicSurvey.getIntermediaryFlag(), localItemConf.get("IntermediaryFlags"))); // 查勘类别
		this.first_site_survey.setText(SpinnerUtils.getValue(basicSurvey.getFirstSiteFlag(), localItemConf.get("FirstSiteFlag"))); // 查勘第一现场
		this.danger_place.setText(basicSurvey.getDamageAddress()); // 出险地点
		this.accident_processing_department.setText(SpinnerUtils.getValue(basicSurvey.getSolutionUnit(), dataMap.get("SolutionUnit"))); // 事故处理部门
		this.touch_for.setText(SpinnerUtils.getValue(basicSurvey.getCaseFlag(), localItemConf.get("Status"))); // 不碰自赔
		this.survey_idea.setText(basicSurvey.getOpinion()); // 勘查意见
	}

	/** 加载涉案车辆信息 **/
	public void loadCarData() throws Exception
	{
		SurveyVehicleAdapter vehicleAdapter = new SurveyVehicleAdapter(carDatas, this, null);
		vehicleInvolvedList.setAdapter(vehicleAdapter); // 设置涉案车辆数据适配器
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
		vehicleInvolvedList.setOnGroupExpandListener(new OnGroupExpandListener()
		{

			@Override
			public void onGroupExpand(int groupPosition)
			{
				ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
			}
		});
		vehicleInvolvedList.setOnGroupCollapseListener(new OnGroupCollapseListener()
		{

			@Override
			public void onGroupCollapse(int groupPosition)
			{
				ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
			}
		});
	}

	/** 加载责任信息 **/
	public void loadLiabilityData() throws Exception
	{
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		for (LiabilityRatio liabilityRatio : LibbilityRadios)
		{
			liabilityRatio.init();
			listData.add(getMap(liabilityRatio));
		}
		liabilityAdapter = new BaseSimpleAdapter(this, listData, R.layout.item_survey_liablity_ratio_parent, new String[]
		{ "policyNo", "policyFlag", "claimFlag", "duty" }, new int[]
		{ R.id.liablity_policyNo, R.id.liablity_policyFlag, R.id.liablity_claimFlag, R.id.liablity_duty }, new int[]
		{}, null);
		liabilityRadioList.setAdapter(liabilityAdapter);
		new Utility().setListViewHeightBasedOnChildren(liabilityRadioList);
	}

	public Map<String, String> getMap(LiabilityRatio liabilityRatio)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("policyNo", liabilityRatio.getPolicyNo());
		String policyFlag = SpinnerUtils.getValue(liabilityRatio.getPolicyFlag(), localItemConf.get("PolicyFlag"));
		map.put("policyFlag", policyFlag);

		if (policyFlag.equals("商业险"))
		{
			String claimFlag = SpinnerUtils.getValue(liabilityRatio.getClaimFlag(), localItemConf.get("ClaimFlag"));
			if (!claimFlag.equals(""))
			{
				map.put("claimFlag", claimFlag + policyFlag + "责任");
			}
			else
			{
				map.put("claimFlag", "");
			}

			map.put("duty", SpinnerUtils.getValue(liabilityRatio.getIndemnityDuty(), dataMap.get("IndemnityDuty")));
		}
		else
		{
			String claimFlag = SpinnerUtils.getValue(liabilityRatio.getcIIndemDuty(), localItemConf.get("CIIndemDuty"));
			if (!claimFlag.equals(""))
			{
				map.put("claimFlag", claimFlag + policyFlag + "责任");
			}
			else
			{
				map.put("claimFlag", "");
			}

			map.put("duty", SpinnerUtils.getValue(liabilityRatio.getcIDutyFlag(), localItemConf.get("CIDutyFlag")));
		}
		return map;
	}

	/** 加载财产损失数据 **/
	public void loadPropDamageData() throws Exception
	{
		/*
		 * List<Map<String, String>> propList = new ArrayList<Map<String,
		 * String>>(); for (PropData propData : proDatas) {
		 * propList.add(getMap(propData)); } propDamageList.setDivider(null);
		 * propDamageList.setEnabled(false) ; propDamageAdapter = new
		 * DamageSimpleAdapter(this, this.proDatas);
		 * propDamageList.setAdapter(propDamageAdapter);
		 * ExpandableUtil.setListViewHeightBasedOnChildren(propDamageList);
		 * this.propDamageList.setFocusable(false) ;
		 */

		LinearLayout root = (LinearLayout) super.findViewById(R.id.damage_layout); // 根布局文件
		for (PropData entity : proDatas)
		{
			entity.init(); // 格式化数据
			/** 基本信息布局文件 **/
			LinearLayout basicLayout = this.getBasicLayout();
			// basicLayout.addView(this.getTitle("财产基本信息")); // 添加标题
			/** 财产基本信息 **/
			LinearLayout childLayout1 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout1.addView(this.setFieldTextView("损失方："));
			childLayout1.addView(this.setFileContentTextView(entity.getLossParty())); // 损失方
			basicLayout.addView(childLayout1); // 添加布局

			LinearLayout childLayout1_2 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout1_2.addView(this.setFieldTextView("联系人："));
			childLayout1_2.addView(this.setFileContentTextView(entity.getRelatePersonName())); // 联系人
			basicLayout.addView(childLayout1_2); // 添加布局

			LinearLayout childLayout2 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout2.addView(this.setFieldTextView("联系方式："));
			childLayout2.addView(this.setFileContentTextView(entity.getRelatePhoneNum())); // 联系方式
			basicLayout.addView(childLayout2); // 添加布局

			LinearLayout childLayout2_2 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout2_2.addView(this.setFieldTextView("估损金额："));
			childLayout2_2.addView(this.setFileContentTextView(entity.getSumLossFee())); // 估损金额
			basicLayout.addView(childLayout2_2); // 添加布局

			LinearLayout childLayout3 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout3.addView(this.setFieldTextView("查勘日期："));
			childLayout3.addView(this.setFileContentTextView(entity.getCheckDate())); // 查勘日期
			basicLayout.addView(childLayout3); // 添加布局

			LinearLayout childLayout3_2 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout3_2.addView(this.setFieldTextView("查勘地点："));
			childLayout3_2.addView(this.setFileContentTextView(entity.getCheckSite())); // 查勘地点
			basicLayout.addView(childLayout3_2); // 添加布局

			LinearLayout childLayout4 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout4.addView(this.setFieldTextView("施救过程："));
			childLayout4.addView(this.setFileContentTextView(entity.getRescueInfo())); // 施救过程
			basicLayout.addView(childLayout4); // 添加布局

			LinearLayout childLayout4_2 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout4_2.addView(this.setFieldTextView("是否重大赔偿案："));
			childLayout4_2.addView(this.setFileContentTextView(localItemConf.get("Status").get(entity.getReserveFlag()))); // 是否重大赔偿案
			basicLayout.addView(childLayout4_2); // 添加布局

			LinearLayout childLayout5 = this.getLinearLayout(); // 生成LinearLayout布局文件
			childLayout5.addView(this.setFieldTextView("备注："));
			childLayout5.addView(this.setFileContentTextView(entity.getRemark())); // 备注
			basicLayout.addView(childLayout5); // 添加布局

			root.addView(basicLayout); // 添加基本信息布局文件

			/** 财产信息列表 **/
			if (entity.getPropDetailDatas() != null && !entity.getPropDetailDatas().isEmpty())
			{
				/** 标题列 **/
				root.addView(this.getTitle("财产损失列表"));
				LinearLayout titleLayout = this.getTitleLinearLayout();
				titleLayout.addView(this.getDetailsTextView("财产名称"));
				titleLayout.addView(this.getDetailsTextView("损失金额"));
				titleLayout.addView(this.getDetailsTextView("损失程度"));
				titleLayout.addView(this.getDetailsTextView("损失种类"));
				root.addView(titleLayout);
				for (PropDetailData detailData : entity.getPropDetailDatas())
				{
					detailData.init(); // 格式化数据
					LinearLayout childLayout = this.getTitleLinearLayout();
					childLayout.addView(this.getDetailsTextView(detailData.getLossItemName()));
					childLayout.addView(this.getDetailsTextView(detailData.getLossFee()));
					childLayout.addView(this.getDetailsTextView(dataMap.get("LossLevel").get(detailData.getLossDegreeCode())));
					childLayout.addView(this.getDetailsTextView(dataMap.get("LossSpeciesCode").get(detailData.getLossSpeciesCode())));
					root.addView(childLayout);
				}
			}
		}

	}

	/** 基本信息布局 **/
	private LinearLayout getBasicLayout()
	{
		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(700, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setGravity(Gravity.CENTER);
		layout.setOrientation(LinearLayout.VERTICAL);
		return layout;
	}

	private TextView getTitle(String content)
	{
		TextView tv = new TextView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(10, 20, 0, 0);
		tv.setLayoutParams(params);
		tv.setTextSize(16);
		tv.setTextColor(this.getResources().getColor(R.color.flied_name));
		tv.setText(content);
		return tv;
	}

	/** 财产损失列表布局文件 **/
	private LinearLayout getTitleLinearLayout()
	{
		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);
		params.setMargins(5, 0, 5, 0);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		return layout;
	}

	/** 财产损失列表标题TextView **/
	private TextView getDetailsTextView(String content)
	{
		TextView textView = new TextView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(180, LinearLayout.LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(params);
		textView.setText(content);
		textView.setTextSize(16);
		textView.setPadding(0, 3, 0, 0);
		textView.setTextColor(this.getResources().getColor(R.color.flied_name));
		return textView;
	}

	/** 财产基本信息布局文件 **/
	private LinearLayout getLinearLayout()
	{
		LinearLayout layout = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		return layout;
	}

	/** 财产基本信息TextVeiw **/
	private TextView setFieldTextView(String content)
	{
		TextView tv = new TextView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(280, LayoutParams.WRAP_CONTENT);
		tv.setGravity(Gravity.RIGHT);
		tv.setLayoutParams(params);
		tv.setPadding(10, 2, 10, 2);
		tv.setTextSize(16);
		tv.setTextColor(this.getResources().getColor(R.color.flied_name));
		tv.setText(content);
		return tv;
	}

	private TextView setFileContentTextView(String content)
	{
		TextView tv = new TextView(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, LayoutParams.WRAP_CONTENT);
		tv.setLayoutParams(params);
		tv.setPadding(0, 2, 10, 2);
		tv.setTextSize(16);
		tv.setTextColor(this.getResources().getColor(R.color.flied_name));
		tv.setText(content);
		return tv;
	}

	/**
	 * 加载人伤信息数据 public void loadPersonInjuredData() {
	 * 
	 * if (personData != null) { this.lossParty.setText(SpinnerUtils.getValue(
	 * this.personData.getLossParty(), dataMap.get("LossParty")));
	 * this.severeNum.setText(this.personData.getSevereNum());
	 * this.minor.setText(this.personData.getMinor());
	 * this.deathNum.setText(this.personData.getDeathNum());
	 * this.sumLossFee.setText(this.personData.getSumLossFee());
	 * this.rescueFee.setText(this.personData.getRescueFee());
	 * this.checkDate.setText(this.personData.getCheckDate());
	 * this.checkSite.setText(this.personData.getCheckSite());
	 * this.rescueContext.setText(this.personData.getRescueContext()); }
	 * 
	 * }
	 **/
	public Map<String, String> getMap(PropData propData)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("lossParty", propData.getLossParty());
		map.put("relatePersonName", propData.getRelatePersonName());
		map.put("checkDate", propData.getCheckDate());
		map.put("checkSite", propData.getCheckSite());
		return map;
	}

	/**
	 * 初始化视图
	 */
	public void setViewController()
	{
		/** 基本信息控件 **/
		this.treatmentBrackBtn = (Button) super.findViewById(R.id.treatmentBrackBtn); // 返回按钮
		this.treatmentBrackBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					SurveyDetailsAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		TextView taskCode = (TextView) super.findViewById(R.id.taskCode);
		taskCode.setText(this.registNo); // 设置报案号
		this.accident_handle_type = (TextView) super.findViewById(R.id.accidentHandleTypeSp); // 事件处理类别
		this.accident_cause = (TextView) super.findViewById(R.id.accidentCause); // 出险原因
		this.accident_type = (TextView) super.findViewById(R.id.accidentType); // 事件类别
		this.loss_type = (TextView) super.findViewById(R.id.lossType); // 损失类别
		this.survey_addr = (TextView) super.findViewById(R.id.surveyAddr); // 查勘地点
		this.survey_date_damage = (TextView) super.findViewById(R.id.surveyTime); // 查勘日期
		this.report_person = (TextView) super.findViewById(R.id.report_people); // 查勘人员
		this.mapping_class = (TextView) super.findViewById(R.id.mappingClass); // 查勘类别
		this.first_site_survey = (TextView) super.findViewById(R.id.firstSiteSurvey); // 查勘第一现场
		this.danger_place = (TextView) super.findViewById(R.id.dangerPlace); // 出险地点
		this.accident_processing_department = (TextView) super.findViewById(R.id.accidentProcessingDepartment); // 事故处理部门
		this.touch_for = (TextView) super.findViewById(R.id.touchFor); // 互碰自陪
		this.survey_idea = (TextView) super.findViewById(R.id.survey_idea); // 查勘意见
		this.vehicleInvolvedList = (ExpandableListView) super.findViewById(R.id.vehicleInvolvedList);
		liabilityRadioList = (ListView) super.findViewById(R.id.liabilityRadioList);
		/**
		 * 人伤信息控件 this.lossParty = (TextView)
		 * super.findViewById(R.id.lossParty); this.severeNum = (TextView)
		 * super.findViewById(R.id.severeNum); this.minor = (TextView)
		 * super.findViewById(R.id.minor); this.deathNum = (TextView)
		 * super.findViewById(R.id.deathNum); this.sumLossFee = (TextView)
		 * super.findViewById(R.id.sumLossFee); this.rescueFee = (TextView)
		 * super.findViewById(R.id.rescueFee); this.checkDate = (TextView)
		 * super.findViewById(R.id.checkDate); this.checkSite = (TextView)
		 * super.findViewById(R.id.checkSite); this.rescueContext = (TextView)
		 * super.findViewById(R.id.rescueContext);
		 **/
	}

	/**
	 * 请求服务或者读取数据库
	 * 
	 * @throws Exception
	 */
	public void requestSurveyInfo() throws Exception
	{
		database = SurveyTreatmentDatabase2.getInstance();

		if (database.isExist(this, registNo))
		{

			BasicSurveyDatabase basic = new BasicSurveyDatabase(this);
			CarDatasDatabase cardata = new CarDatasDatabase(this);
			// PersonDataDatabase personDatabase = new PersonDataDatabase(this);
			PropDataDatabase propDatabase = new PropDataDatabase(this);
			PolicyDatasDatabase policyDatabase = new PolicyDatasDatabase(this);
			LiabilityRadioDatabase liabilityRadioDatabase = new LiabilityRadioDatabase(this);

			basicSurvey = basic.selectBasicSurvey("basic_survey", registNo, null);
			carDatas = cardata.selectCarData("car_data", registNo, null); // 车辆信息.i("请求查勘数据","取得数据");

			// personData = personDatabase.selectPersonData("person_data",
			// registNo); // 人伤信息tch(Exception e){
			policyDatas = new ArrayList<PolicyData>();
			proDatas = propDatabase.selectPropDatas("prop_data", registNo);// 财产信息

			LibbilityRadios = new ArrayList<LiabilityRatio>();
			LibbilityRadios = liabilityRadioDatabase.selectLiablity("liablity_data", registNo);
			try
			{
				policyDatas = policyDatabase.selectPolicyData("policy_data", registNo);// 保单信息

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			database.setBasicSurvey(basicSurvey);
			database.setCarDatas(carDatas);
			// database.setPersonData(personData);
			database.setPolicyDatas(policyDatas);
			database.setProDatas(proDatas);
			database.setLiabilityRatios(LibbilityRadios);

		}
		else
		{

			CaseCenterService service = new CaseCenterService(null);
			SurveyInfo surveyInfo = service.querySurveyInfo(registNo, registId);
			basicSurvey = surveyInfo.getBasicSurvey();

			carDatas = surveyInfo.getCarDatas();// 车辆信息.i("请求查勘数据","取得数据");
			// personData = surveyInfo.getPersonData();// 人伤信息tch(Exception
			// e){
			policyDatas = surveyInfo.getPolicyDatas();// 保单信息e.printStackTrace();
			proDatas = surveyInfo.getPropDatas();// 财产信息
			LibbilityRadios = surveyInfo.getLiabilityRatios();
			List<RegistThirdCarData> registThirdCarDatas = surveyInfo.getRegistThirdCarDatas();

			database.setBasicSurvey(basicSurvey);
			database.setCarDatas(carDatas);
			// database.setPersonData(personData);
			database.setPolicyDatas(policyDatas);
			database.setProDatas(proDatas);
			database.setLiabilityRatios(LibbilityRadios);
			database.setRegistThirdCarDatas(registThirdCarDatas);
			database.save(this);
		}
	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
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
}
