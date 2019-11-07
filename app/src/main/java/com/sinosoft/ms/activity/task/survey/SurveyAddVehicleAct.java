package com.sinosoft.ms.activity.task.survey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.CarDriverData;
import com.sinosoft.ms.model.CarLossData;
import com.sinosoft.ms.model.CarPolicyData;
import com.sinosoft.ms.model.KindCodeData2;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.ATobigA;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.SearchWather;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损系统 子系统名：新增涉案车辆界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-5-28 下午12:03:59
 */

public class SurveyAddVehicleAct extends Activity implements OnClickListener
{

	private InputMethodManager imm;
	private Map<String, Map<String, String>> dicMap;// 字典信息
	private Map<String, Map<String, String>> dicConfigMap;

	private CarData carData;
	private List<CarPolicyData> carPolicyDatas;
	private CarLossData bean;
	private String lossItemType;
	private String CarKindCode = "";
	private Map<String, String> carKindMap;
	private CarDriverData driver;
	private boolean isExist = false;

	// 涉案车辆处理
	private Spinner loss_typeSp; // 损失类别
	private EditText dangerPlaceExt; // 车牌号

	private EditText engineNumberExt;// 发动机号
	private EditText frameNumberExt;// 车架号／VIN：
	
	private EditText indemintyDutydateExt;//商业损失险别
	
	
	private LinearLayout  engineNumberLine;

	private EditText the_ownerEdt; // 车主
	private Spinner number_plate_typeSp; // 车牌种类
	private Spinner strong_typeSp; // 交强险责任类型
	// 损失详情
	private Spinner loss_situationSp; // 损失情况
	private EditText estimated_loss_amountExt; // 估损金额
	private Spinner car_KindCodeSp; // 车辆损失险别
	private Spinner lindemnityDutySp; // 商业险赔偿责任
	// 保单信息
	private PolicyData policyData;

	// 驾驶员信息

	private EditText nameExt; // 驾驶员姓名
	private Spinner documentTypeSp; // 证件类型
	private EditText certificates_numberExt; // 证件号码
	private EditText driverNoExt; // 驾驶证号
	private Button saveCarBtn; // 保存
	private Button backCarBtn; // 返回
	private Button autoNum; // 自动输入
	private Button idcardBtn;// 识别()
	private Button IDBtn;
	private String[] type =
	{ "一代身份证", "二代身份证正面", "二代身份证证背面", "临时身份证", "驾照", "行驶证", "军官证", "士兵证", "港澳通行证", "大陆通行证", "台湾通行证", "签证", "护照", "内地通行证正面", "内地通行证背面", "户口本", "车辆识别代号" };

	private int srcwidth = 0;
	private int srcheight = 0;

	private final int DRIVER_CARD = 5;// 驾照
	private final int DRIVER_NO = 100;
	private Intent recogIntent;

	// public RecogService.MyBinder recogBinder;
	int iInitPlateIDSDK = 0;
	private String[] fieldname =
	{ "车牌号", "车牌颜色", "车牌颜色代码", "车牌类型代码", "整牌可信度", "亮度评价", "车牌运动方向", "车牌左上点横坐标", "车牌左上点纵坐标", "车牌右下点横坐标", "车牌右下点纵坐标", "时间", "车的亮度", "车的颜色", "用户数据" };
	private String pic;// 文件名
	private String cls;
	private int imageformat = 1;
	private int width = 640;
	private int height = 480;
	private boolean bGetVersion = false;
	private String sn;
	private String authfile;
	private int bVertFlip = 0;
	private int bDwordAligned = 1;
	private int nRet = -1;
	private String[] fieldvalue;
	private String userData;
	private byte[] picByte;
	private int carType = 1;
	private String reason = "";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey_add_vehicle);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		ActivityManage.getInstance().push(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("item");
		if (bundle != null)
		{
			carData = (CarData) bundle.getSerializable("bean");
		}
		else
		{
			carData = new CarData();
		}
		if (bundle != null)
		{
			isExist = bundle.getBoolean("isExist");
			reason = bundle.getString("reason"); // 出险原因
		}
		if (bundle != null)
		{
			policyData = (PolicyData) bundle.getSerializable("policyData");
		}
		else
		{
			policyData = new PolicyData();
		}
		this.setViewController(); // 设置控件
		// 获取缓存信息
		dicMap = DictCashe.getInstant().getDict();
		dicConfigMap = AppConstants.getLocalItemConf();
		this.setData(); // 设置处理界面信息
		this.setDetails(); // 设置详情界面信息
		this.setDrivers(); // 设置驾驶员界面信息
		dicMap = DictCashe.getInstant().getDict();
		this.saveCarBtn.setOnClickListener(this);
		this.backCarBtn.setOnClickListener(this);
	}

	/** 设置驾驶员界面信息 **/
	public void setDrivers()
	{
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("item");
		if (bundle != null)
		{
			driver = (CarDriverData) bundle.getSerializable("driver");
		}
		lossItemType = intent.getStringExtra("LossItemType");
		if (lossItemType == null)
		{
			lossItemType = "";
		}
		if (driver == null)
		{
			driver = new CarDriverData();
			driver.init();
		}
		nameExt.setText(driver.getDriverName()); // 驾驶员
		certificates_numberExt.setText(driver.getIdentifyNumber()); // 证件号码
		driverNoExt.setText(driver.getDrivingLicenseNo()); // 驾驶证号码
		SpinnerUtils.setSpinnerData(this, documentTypeSp, dicMap.get("IdentifyType"), driver.getIdentifyType() + "",""); // 驾驶证类型
	}

	/** 损失详情 **/
	public void setDetails()
	{
		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("item");
		if (bundle != null)
		{
			bean = (CarLossData) bundle.getSerializable("loss");
			if (bean == null)
			{
				bean = new CarLossData();

			}
			bean.init();
			lossItemType = bundle.getString("LossItemType");
			if (lossItemType == null)
			{
				lossItemType = "";
			}

			carKindMap = new HashMap<String, String>();
			estimated_loss_amountExt.setText(bean.getSumLossFee()); // 估损金额

			if (lossItemType.equals("050"))
			{
				SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2.getInstance();
				List<KindCodeData2> kindCodeDatas = database.getKindCodeDatas();

				if (kindCodeDatas != null && !kindCodeDatas.isEmpty())
				{
					for (KindCodeData2 kindCodeData : kindCodeDatas)
					{
						String code = kindCodeData.getInsureTermCode();
						if (StringUtils.isNotEmpty(code) && !code.equals("02") && !code.equals("BZ"))
						{
							carKindMap.put(kindCodeData.getInsureTermCode(), kindCodeData.getInsureTerm());
						}
					}
				}
				else
				{
					carKindMap.put("01", "车辆损失险");
					carKindMap.put("11", "玻璃单独破碎险");
					carKindMap.put("03", "全车盗抢险");
					carKindMap.put("43", "车灯、倒车镜单独损坏险");
					carKindMap.put("46", "换件特约");
					// carKindMap.put("46", "换件特约");

				}
			}
			else if (lossItemType.equals("010"))
			{
				SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2.getInstance();
				List<LiabilityRatio> liabilityRatios = database.getLiabilityRatios();
				if (liabilityRatios == null)
				{
					liabilityRatios = new ArrayList<LiabilityRatio>();
				}
				int size = liabilityRatios.size();
				for (LiabilityRatio liabilityRatio : liabilityRatios)
				{
					liabilityRatio.init();
					String policyFlag = liabilityRatio.getPolicyFlag();
					if (policyFlag.equals("1"))
					{
						carKindMap.put("02", "商业第三者责任险");
					}
					else
					{
						carKindMap.put("BZ", "机动车交通事故责任强制险");
					}
				}
				indemintyDutydateExt.setText(bean.getIndemnityDutyRate());
			}
			SpinnerUtils.setSpinnerData(this, car_KindCodeSp, carKindMap, this.getKindCode(),""); // 车辆损失险别
			SpinnerUtils.setSpinnerData(this, loss_situationSp, dicConfigMap.get("DamageFlag"), bean.getDamageFlag() + "",""); // 损失情况
			SpinnerUtils.setSpinnerData(this, lindemnityDutySp, dicMap.get("IndemnityDuty"), bean.getIndemnityDuty() + "","IndemnityDuty"); // 商业险赔偿责任
		}
	}

	/** 获取车辆损失类别 **/
	private String getKindCode()
	{
		String kindCode = ""; // 车辆损失类别
		if (StringUtils.isNotEmpty(bean.getKindCode()))
		{
			kindCode = bean.getKindCode();
		}
		else
		{
			if ("碰撞".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("车辆损失险", carKindMap);
			}
			else if ("玻璃破碎".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("玻璃单独破碎险", carKindMap);
			}
			else if ("车身划痕".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("车身划痕损失险", carKindMap);
			}
			else if ("全车盗抢".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("全车盗抢险", carKindMap);
			}
			else if ("自燃".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("自燃损失险", carKindMap);
			}
			else if ("自然灾害".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("车辆损失险", carKindMap);
			}
			else if ("其它".equals(this.reason))
			{
				kindCode = SpinnerUtils.getKey("车辆损失险", carKindMap);
			}
		}
		return kindCode;
	}

	
	/******/
	public void setEditState(int flag)
	{
		
		String lossItemType1 = loss_typeSp.getSelectedItem().toString();
		if (StringUtils.isNotEmpty(lossItemType1) && lossItemType1.equals("标的车"))
		{
			engineNumberLine.setVisibility(View.VISIBLE);
			engineNumberExt.setEnabled(false);
			///engineNumberExt.setKeyListener(null);
			engineNumberExt.setBackgroundColor(Color.rgb(0xef, 0xef, 0xef));
			engineNumberExt.setFocusable(false);

			frameNumberExt.setEnabled(false);
			///frameNumberExt.setKeyListener(null);
			frameNumberExt.setBackgroundColor(Color.rgb(0xef, 0xef, 0xef));
			frameNumberExt.setFocusable(false);
			
			indemintyDutydateExt.setVisibility(View.GONE);
			this.lindemnityDutySp.setVisibility(View.VISIBLE);
			
		}
		else if (StringUtils.isNotEmpty(lossItemType1) && !lossItemType1.equals("标的车"))
		{
			//engineNumberExt.setEnabled(true);
			engineNumberLine.setVisibility(View.GONE);
			//engineNumberExt.setKeyListener();
			///engineNumberExt.setBackgroundColor(Color.rgb(0xfd, 0xfd, 0xfd));
			///engineNumberExt.setFocusable(true);

			frameNumberExt.setEnabled(true);
			//frameNumberExt.setKeyListener(null);
			frameNumberExt.setBackgroundColor(Color.rgb(0xfd, 0xfd, 0xfd));
			frameNumberExt.setFocusable(true);	
			
			indemintyDutydateExt.setVisibility(View.VISIBLE);
			indemintyDutydateExt.setText(bean.getIndemnityDutyRate());
			///bean.setIndemnityDutyRate
			
			this.lindemnityDutySp.setVisibility(View.GONE);
			
		}
	}
	
	/**
	 * 设置界面数据
	 */
	public void setData()
	{

		carPolicyDatas = carData.getCarpolicyList();
		if (carPolicyDatas == null)
		{
			carPolicyDatas = new ArrayList<CarPolicyData>();
			carData.setCarpolicyList(carPolicyDatas);
		}

		String type = carData.getLossItemType();
		if (StringUtils.isEmpty(type))
		{
			type = "";
		}

		///setEditState(0);

		
		
		
		dangerPlaceExt.setText(carData.getLicenseNo()); // 车牌号

		engineNumberExt.setText(carData.getEngineNo()); // 发动机号

		frameNumberExt.setText(carData.getFrameNo().toUpperCase()); // 车架号／VIN：

		the_ownerEdt.setText(carData.getCarOwner()); // 车主
		if (carData.getLossItemType().equals("050"))
		{
			if (carData.getCarOwner().trim().equals("") || carData.getCarOwner().length() < 0)
			{
				the_ownerEdt.setText(policyData.getInsuredName());
			}
		}
		SpinnerUtils.setSpinnerData(this, loss_typeSp, dicConfigMap.get("LossItemType"), carData.getLossItemType() + "",""); // 损失类别
		SpinnerUtils.setSpinnerData(this, number_plate_typeSp, dicMap.get("LicenseKindCode"), carData.getLicenseType() + "",""); // 号牌类型
		SpinnerUtils.setSpinnerData(this, strong_typeSp, dicConfigMap.get("IndemDutyCI"), carData.getLiabilityType() + "",""); // 交强险责任类型
		loss_typeSp.setOnItemSelectedListener(onItemSelectClick);
	}

	private OnItemSelectedListener onItemSelectClick = new OnItemSelectedListener()
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
			String type = carData.getLossItemType();
			if (StringUtils.isEmpty(type))
			{
				type = "";
			}
			

			setEditState(1);
			
			if (isExist && !type.equals("050"))
			{
				
				
				String lossItemType1 = loss_typeSp.getSelectedItem().toString();
				if (StringUtils.isNotEmpty(lossItemType1) && lossItemType1.equals("标的车"))
				{
					loss_typeSp.setOnItemSelectedListener(null);
					loss_typeSp.setSelection(0);
					loss_typeSp.setOnItemSelectedListener(this);
					ToastDialog.show(SurveyAddVehicleAct.this, "标的车已经存在，不能添加标的车", ToastDialog.ERROR);
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
			System.out.println("--------");
		}
	};

	/**
	 * 生成N位随机数
	 * 
	 * @param length
	 *            位数
	 * @return
	 */
	public static String getRandomString(int length)
	{
		length = length < 0 ? -length : length;
		char[] chs = new char[length];
		Random ran = new Random();
		for (int i = 0; i < chs.length; i++)
		{
			chs[i] = (char) (ran.nextInt(10) + '0');
		}
		return new String(chs);
	}

	/**
	 * 初始化视图组件
	 */
	public void setViewController()
	{
		this.autoNum = (Button) super.findViewById(R.id.autoNum); // 自动输入
		this.autoNum.setVisibility(View.GONE);
		this.autoNum.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				SpinnerUtils.setSpinnerData(SurveyAddVehicleAct.this, documentTypeSp, dicMap.get("IdentifyType"), SpinnerUtils.getKey("其他证件", dicMap.get("IdentifyType")),"");// 驾驶证类型
				certificates_numberExt.setText(getRandomString(10)); // 设置随机生成的证件号码
				driverNoExt.setText(getRandomString(10)); // 设置随机生成的驾驶证号码
			}
		});
		this.loss_typeSp = (Spinner) super.findViewById(R.id.loss_typeSp); // 损失类别
		this.dangerPlaceExt = (EditText) super.findViewById(R.id.dangerPlaceExt); // 车牌号
		this.the_ownerEdt = (EditText) super.findViewById(R.id.the_ownerEdt); // 车主

		
		this.engineNumberLine = (LinearLayout) super.findViewById(R.id.engine_number_line);//
		this.engineNumberExt = (EditText) super.findViewById(R.id.engineNumber);// 发动机号
		
		this.indemintyDutydateExt = (EditText) super.findViewById(R.id.indemintyDutydate);
		
		/*
		 * engineNumberExt.setEnabled(false);
		 * engineNumberExt.setKeyListener(null);
		 * engineNumberExt.setBackgroundColor(Color.GRAY);
		 * engineNumberExt.setFocusable(false);
		 */
		this.frameNumberExt = (EditText) super.findViewById(R.id.frameNumber);// 车架号／VIN：
		
		frameNumberExt.setTransformationMethod(new ATobigA());
		
		/*
		 * frameNumberExt.setEnabled(false);
		 * frameNumberExt.setKeyListener(null);
		 * frameNumberExt.setBackgroundColor(Color.GRAY);
		 * frameNumberExt.setFocusable(false);
		 */

		this.number_plate_typeSp = (Spinner) super.findViewById(R.id.number_plate_typeSp); // 车牌种类
		this.strong_typeSp = (Spinner) super.findViewById(R.id.strong_typeSp); // 交强险责任类型
		this.strong_typeSp.setEnabled(false);
		this.loss_situationSp = (Spinner) super.findViewById(R.id.loss_situationSp); // 损失情况
		this.estimated_loss_amountExt = (EditText) super.findViewById(R.id.estimated_loss_amountExt); // 估损金额

		this.car_KindCodeSp = (Spinner) super.findViewById(R.id.car_KindCodeSp); // 车辆损失险别
		this.lindemnityDutySp = (Spinner) super.findViewById(R.id.lindemnityDutySp); // 商业险赔偿责任
		this.lindemnityDutySp.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String selectedItem = lindemnityDutySp.getSelectedItem().toString();
				if ("无责".equals(selectedItem))
				{
					SpinnerUtils.setSpinnerData(SurveyAddVehicleAct.this, strong_typeSp, dicConfigMap.get("IndemDutyCI"), SpinnerUtils.getKey("无责", dicConfigMap.get("IndemDutyCI")),"");
				}
				else if ("请选择".equals(selectedItem))
				{
					SpinnerUtils.setSpinnerData(SurveyAddVehicleAct.this, strong_typeSp, dicConfigMap.get("IndemDutyCI"), SpinnerUtils.getKey("请选择", dicConfigMap.get("IndemDutyCI")),"");
				}
				else
				{
					SpinnerUtils.setSpinnerData(SurveyAddVehicleAct.this, strong_typeSp, dicConfigMap.get("IndemDutyCI"), SpinnerUtils.getKey("有责", dicConfigMap.get("IndemDutyCI")),"");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});
		this.nameExt = (EditText) findViewById(R.id.nameExt); // 驾驶员姓名
		this.documentTypeSp = (Spinner) findViewById(R.id.documentTypeSp); // 证件类型
		this.certificates_numberExt = (EditText) findViewById(R.id.certificates_numberExt); // 证件号码
		this.driverNoExt = (EditText) findViewById(R.id.driverNoExt); // 驾驶证号
		this.saveCarBtn = (Button) findViewById(R.id.saveCarBtn); // 保存
		this.backCarBtn = (Button) findViewById(R.id.backCarBtn); // 返回

		loss_situationSp.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				boolean isEdit = false;
				if (loss_situationSp.getSelectedItem().toString().equals("无损失"))
				{
					isEdit = false;
				}
				else
				{
					isEdit = true;
				}

				if (!isEdit)
				{
					estimated_loss_amountExt.setText("0");
				}
				estimated_loss_amountExt.setEnabled(isEdit);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		// 证件类型监听
		this.documentTypeSp.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				String selectedItem = documentTypeSp.getSelectedItem().toString();
				if (!"其他证件".equals(selectedItem))
				{
					certificates_numberExt.setText(""); // 清空证件号码
					driverNoExt.setText(""); // 清空驾驶证号码
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{

			}
		});

		idcardBtn = (Button) findViewById(R.id.idcardBtn);
		IDBtn = (Button) findViewById(R.id.IDBtn);
		idcardBtn.setOnClickListener(this);
		IDBtn.setOnClickListener(this);
	}

	public Intent getData()
	{
		// 处理
		carData.setLicenseNo(dangerPlaceExt.getText().toString());
		
		
		///carData.setEngineNo(engineNumberExt.getText().toString().trim()); // 发动机号
		
		carData.setFrameNo(frameNumberExt.getText().toString().trim().toUpperCase());// 车架号／VIN：
		
		
		carData.setCarOwner(the_ownerEdt.getText().toString());
		carData.setLossItemType(SpinnerUtils.getKey(loss_typeSp.getSelectedItem().toString(), dicConfigMap.get("LossItemType")));
		carData.setLicenseType(SpinnerUtils.getKey(number_plate_typeSp.getSelectedItem().toString(), dicMap.get("LicenseKindCode")));
		if (carData.getLossItemType().equals("050"))
		{
			carData.setLiabilityType(SpinnerUtils.getKey(strong_typeSp.getSelectedItem().toString(), dicConfigMap.get("IndemDutyCI")));
		}
		else
		{
			carData.setLiabilityType("100");
		}
		// 详情
		/** 涉案车辆损失明细 精简字段及默认值 start **/
		bean.setDamageFlag("1");// 损失情况 默认值为：1-有损失
		bean.setReserveFlag("0");// 重大赔案标识 默认值为：0-否
		/** 涉案车辆损失明细 精简字段及默认值 end **/

		bean.setSumLossFee(estimated_loss_amountExt.getText().toString()); // 估损金额
		bean.setDamageFlag(SpinnerUtils.getKey(loss_situationSp.getSelectedItem().toString(), dicConfigMap.get("DamageFlag"))); // 损失情况
		String infrmnityDuty = "";
		if (carData.getLossItemType().equals("050"))
		{	
			try
			{
				
				String duty = lindemnityDutySp.getSelectedItem().toString();
				//infrmnityDuty = lindemnityDutySp.getSelectedItem().toString();
				if(StringUtils.isNotEmpty(duty))
				{
					String[] dutyTmp =duty.split(" ");
					infrmnityDuty = dutyTmp[0];
				}
				
			}
			catch (Exception e)
			{
			}
			bean.setIndemnityDuty(SpinnerUtils.getKey(infrmnityDuty, dicMap.get("IndemnityDuty"))); // 商业险赔偿责任
			bean.setIndemnityDutyRate(SpinnerUtils.getValue(infrmnityDuty, dicConfigMap.get("Rate")));
		}
		else
		{
			bean.setIndemnityDuty("0"); // 商业险赔偿责任
			bean.setIndemnityDutyRate(indemintyDutydateExt.getText().toString());
			
		}
/*		if (carData.getLossItemType().equals("050"))
		///if (lossItemType.equals("050"))
		{
			bean.setIndemnityDuty(SpinnerUtils.getKey(infrmnityDuty, dicMap.get("IndemnityDuty"))); // 商业险赔偿责任
			bean.setIndemnityDutyRate(SpinnerUtils.getValue(infrmnityDuty, dicConfigMap.get("Rate")));
		}
		else
		{
			///bean.setIndemnityDuty(SpinnerUtils.getKey(infrmnityDuty, dicMap.get("IndemnityDuty"))); // 商业险赔偿责任
			bean.setIndemnityDutyRate(indemintyDutydateExt.getText().toString());
			
		}
*/
		bean.setKindCode(SpinnerUtils.getKey(car_KindCodeSp.getSelectedItem().toString(), carKindMap)); // 车辆损失险别
		// 驾驶员信息
		driver.setDriverName(nameExt.getText().toString()); // 驾驶员
		driver.setIdentifyNumber("440307111111111111"); // 证件号码
		driver.setIdentifyType("01"); // 证件类型
		/*
		 * driver.setIdentifyNumber(certificates_numberExt.getText().toString());
		 * // 证件号码 driver.setIdentifyType(SpinnerUtils.getKey(documentTypeSp
		 * .getSelectedItem().toString(), dicMap.get("IdentifyType"))); // 证件类型
		 */
		driver.setDrivingLicenseNo(driverNoExt.getText().toString()); // 驾驶证号码
		Intent data = new Intent();
		Bundle bundle = new Bundle();
		carData.setCarpolicyList(carPolicyDatas);
		bundle.putSerializable("carDataBean", carData); // 处理信息
		bundle.putSerializable("detailBean", bean); // 详细信息
		bundle.putSerializable("driverBean", driver); // 驾驶员信息
		data.putExtra("item", bundle);
		return data;

	}

	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.saveCarBtn:
			String errorMsg = check();
			if (StringUtils.isEmpty(errorMsg))
			{
				Intent data = getData();
				this.setResult(4444, data);
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
				ToastDialog.show(this, errorMsg, ToastDialog.ERROR);
			}
			break;
		case R.id.backCarBtn:
			try
			{
				SurveyAddVehicleAct.this.finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			break;
		// case R.id.idcardBtn://车牌识别
		// if(!MyUtils.isFastDoubleClick()){
		// recogIntent = new Intent(this, RecogService.class);
		// //启动识别服务
		// startService(recogIntent);
		// Intent takePictureIntent = new
		// Intent(this,CarIdCameraActivity.class);
		// startActivityForResult(takePictureIntent,12);
		// }
		// break;
		// case R.id.IDBtn://证件识别
		// if(!MyUtils.isFastDoubleClick()){
		// recognitionCarId(DRIVER_CARD);
		// }
		// break;
		}
	}

	/**
	 * 初始化相机,设置照相机参数
	 */
	public void initCamera()
	{
		int width = 0;
		int height = 0;
		Camera camera = null;
		boolean isCatchPicture = false;
		boolean isCatchPreview = false;
		try
		{
			camera = Camera.open();
			if (camera != null)
			{
				// 读取支持的预览尺寸,优先选择640后320
				Camera.Parameters parameters = camera.getParameters();
				List<Camera.Size> previewSizes = splitSize(parameters.get("preview-size-values"), camera);// parameters.getSupportedPreviewSizes();

				for (int i = 0; i < previewSizes.size(); i++)
				{
					if (previewSizes.get(i).width == 640 && previewSizes.get(i).height == 480)
					{
						isCatchPreview = true;
						width = 640;
						height = 480;
						break;
					}
					if (previewSizes.get(i).width == 320 && previewSizes.get(i).height == 240)
					{// 640 //480
						isCatchPreview = true;
						width = 320;
						height = 240;
					}
				}
				// 读取支持的相机尺寸,优先选择1280后1600后2048
				List<Camera.Size> PictureSizes = splitSize(parameters.get("picture-size-values"), camera);// parameters.getSupportedPictureSizes();
				for (int i = 0; i < PictureSizes.size(); i++)
				{
					if (PictureSizes.get(i).width == 2048 && PictureSizes.get(i).height == 1536)
					{
						if (isCatchPicture == true)
						{
							break;
						}
						isCatchPicture = true;
						srcwidth = 2048;
						srcheight = 1536;
					}
					if (PictureSizes.get(i).width == 1600 && PictureSizes.get(i).height == 1200)
					{
						isCatchPicture = true;
						srcwidth = 1600;
						srcheight = 1200;
					}
					if (PictureSizes.get(i).width == 1280 && PictureSizes.get(i).height == 960)
					{
						isCatchPicture = true;
						srcwidth = 1280;
						srcheight = 960;
						break;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (camera != null)
			{
				try
				{
					camera.release();
					camera = null;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param str
	 * @param camera
	 * @return
	 */
	private ArrayList<Size> splitSize(String str, Camera camera)
	{
		if (str == null)
			return null;
		StringTokenizer tokenizer = new StringTokenizer(str, ",");
		ArrayList<Size> sizeList = new ArrayList<Size>();
		while (tokenizer.hasMoreElements())
		{
			Size size = strToSize(tokenizer.nextToken(), camera);
			if (size != null)
				sizeList.add(size);
		}
		if (sizeList.size() == 0)
			return null;
		return sizeList;
	}

	/**
	 * 
	 * @param str
	 * @param camera
	 * @return
	 */
	private Size strToSize(String str, Camera camera)
	{
		if (str == null)
			return null;
		int pos = str.indexOf('x');
		if (pos != -1)
		{
			String width = str.substring(0, pos);
			String height = str.substring(pos + 1);
			return camera.new Size(Integer.parseInt(width), Integer.parseInt(height));
		}
		return null;
	}

	/**
	 * 检测界面元素，是否符合要求
	 * 
	 * @return 返回错误信息，无则表示正确
	 */
	public String check()
	{
		String errorMsg = null;
		try
		{
			if (loss_typeSp.getSelectedItemPosition() <= 0)
			{
				throw new IllegalArgumentException("损失类别不能空");
			}
			else if (dangerPlaceExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("车牌号码不能空");
			}
			else if (the_ownerEdt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("车主不能为空");
			}
			else if (loss_situationSp.getSelectedItem().toString().equals("请选择"))
			{
				throw new IllegalArgumentException("损失情况不能为空");
			}
			else if (estimated_loss_amountExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("估损金额不能为空");
			}
/*			else if (lindemnityDutySp.getSelectedItem().toString().equals("请选择"))
			{
				throw new IllegalArgumentException("商业险赔偿责任不能为空");
			}*/
			else if (StringUtils.isEmpty(nameExt.getText().toString()))
			{
				throw new IllegalArgumentException("驾驶员姓名不能为空");
			}
			else if (driverNoExt.getText().toString().equals(""))
			{
				throw new IllegalArgumentException("驾驶证号不能为空");
			}
			else
			{
				String driverNo = driverNoExt.getText().toString();
				if (driverNo.length() != 10 && driverNo.length() != 15 && driverNo.length() != 18)
				{
					throw new IllegalAccessException("驾驶证号为10位，15位或18位！");
				}

			}
			
			
			String lossItemType1 = loss_typeSp.getSelectedItem().toString();
			if (StringUtils.isNotEmpty(lossItemType1) && lossItemType1.equals("三者车"))
			{
				
				String date = indemintyDutydateExt.getText().toString().trim();
				if (date.equals(""))
				{
					throw new IllegalArgumentException("商业险赔偿责任比不能为空");
				}
				
/*				String engineNumber = engineNumberExt.getText().toString().trim(); // 发动机号
				if (engineNumber.equals(""))
				{
					throw new IllegalArgumentException("发动机号不能为空");
				}*/				
				String frameNumber = frameNumberExt.getText().toString().trim().toUpperCase();// 车架号／VIN：
				if (frameNumber.equals(""))
				{
					throw new IllegalArgumentException("车架号／VIN不能为空");
				}
				else 
				{
					int len = frameNumber.length();
					if(len != 17)
					{
						throw new IllegalArgumentException("涉案车辆车架号码/VIN码能由17位（含）以数字或字母组成");
					}
					else if(frameNumber.contains("I") || frameNumber.contains("O") ||frameNumber.contains("Q"))
					{
						throw new IllegalArgumentException("涉案车辆车架号码/VIN码只能由大写英文字母（I,O,Q除外）和数字组成");
					}
				}
				
				//涉案车辆车架号码/VIN码("+i+")不能由17位（含）以上连续相同的数字或字母组成
				
			}
			else 
			{
				if (lindemnityDutySp.getSelectedItem().toString().equals("请选择"))
				{
					throw new IllegalArgumentException("商业险赔偿责任不能为空");
				}
				
			}
			
			// Object identifytype = documentTypeSp.getSelectedItem(); // 证件类型
			// if (lossItemType.equals("050")) {// 标的车
			// String str = "标的车的驾驶员信息,";
			// if (documentTypeSp == null
			// || documentTypeSp.toString().equals("请选择")) {
			// throw new IllegalArgumentException(str + "证件类型不能为空");
			// }
			// if (certificates_numberExt.getText().toString().equals("")) {
			// throw new IllegalArgumentException(str+ "证件号码不能为空");
			// }
			// }
			// if (identifytype != null &&
			// !identifytype.toString().equals("请选择")) {
			// String identifyNumber =
			// certificates_numberExt.getText().toString();
			// if (identifytype.toString().equals("居民身份证")) {
			// new SurveyVaildata().identifytypeVali(identifyNumber);
			// }
			// if (identifytype.toString().equals("军官证")) {
			// if (identifyNumber.length() != 10) {
			// throw new IllegalArgumentException("军官证必须为10位");
			// }
			// }
			// }

		}
		catch (Exception e)
		{
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}

	private boolean checkEstimated_loss_amountExt(String str)
	{
		Pattern p = Pattern.compile(SearchWather.NUMBER3);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		try
		{
			this.finish();
			// ActivityManage.getInstance().pop();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 识别服务绑定后的操作
	// public ServiceConnection recogConn = new ServiceConnection() {
	// @Override
	// public void onServiceDisconnected(ComponentName name) {
	// recogConn = null;
	// }
	// @Override
	// public void onServiceConnected(ComponentName name, IBinder service) {
	// recogBinder = (RecogService.MyBinder)service;
	// //Toast.makeText(getApplicationContext(), "识别服务 绑定成功",
	// Toast.LENGTH_SHORT).show();
	// //若授权验证成功，绑定识别服务。若授权验证不成功，识别初始化将失败。
	// //if (ReturnAuthority == 0) {
	// //判断识别初始化的情况
	// iInitPlateIDSDK = recogBinder.getInitPlateIDSDK();
	// if ( iInitPlateIDSDK != 0 ) {
	// Toast.makeText(getApplicationContext(), "识别初始化失败:" + iInitPlateIDSDK,
	// Toast.LENGTH_SHORT).show();
	// String[] str = {"" + iInitPlateIDSDK};
	// } else {
	// synchronized(this){
	// try{
	// // 设置识别用到的参数，可点击“程序设置”按钮进行设置。必须在doRecog()之前调用，否则无法识别。
	// recogBinder.setRecogArgu(pic, imageformat, bGetVersion,
	// bVertFlip,bDwordAligned);
	// // 得到设置是否成功的标志
	// nRet = recogBinder.getnRet();
	// //Log.i(TAG, "nRet:" + nRet);
	// // 调用识别并返回识别结果，pic代表要识别的图片。]
	// userData="1234";//演示用的，userdata是用户自定义数据
	// //Log.i(TAG, "onServiceConnected type="+type);
	// if(carType==1){
	// fieldvalue = recogBinder.doRecog(pic, width, height,userData);
	// //Log.i(TAG, "fieldvalue="+fieldvalue);
	// } else if(carType==2) {
	// //Log.i(TAG, "onServiceConnected picByte="+picByte.length);
	// fieldvalue = recogBinder.doRecog(picByte, width, height,userData);
	// //Log.i(TAG, "fieldvalue="+fieldvalue);
	// }
	// // 得到识别是否成功的标志
	// nRet = recogBinder.getnRet();
	// getCarNo(fieldvalue);
	//
	// }catch(Exception e){
	// e.printStackTrace();
	// }finally{
	// this.notify();
	// }
	// }
	// }
	// //解绑识别服务。
	// if (recogBinder != null) {
	// unbindService(recogConn);
	// }
	// }
	// };
	/**
	 * 取得识别数据 fieldvalue对于fileName
	 * 
	 * @param fieldvalue
	 */
	private void getCarNo(String[] fieldvalue)
	{
		String result = "";
		if (nRet != 0)
		{
			CustomDialog.show(this, "信息提示", "识别失败，请重新识别");
		}
		else
		{
			if (null != fieldname)
			{
				result = fieldvalue[0];
			}
		}
		nRet = -1;
		fieldvalue = null;
		dangerPlaceExt.setText(result);
	}

	/**
	 * 拦截activity的返回
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == DRIVER_NO && data != null)
		{// 证件识别返回（驾驶证）
			// String
			// name=StringUtils.initWithNull(data.getStringExtra("name"));
			// String NO=StringUtils.initWithNull(data.getStringExtra("No"));
			// documentTypeSp.setOnItemSelectedListener(null);
			// SpinnerUtils.setSpinnerData(this, documentTypeSp,
			// dicMap.get("IdentifyType"), SpinnerUtils.getKey("居民身份证",
			// dicMap.get("IdentifyType"))); // 驾驶证类型
			//
			// certificates_numberExt.setText(NO); //设置证件号
			// driverNoExt.setText(NO);//驾驶证
			// nameExt.setText(name); // 驾驶员
			// if (NO.equals("") || nameExt.equals("")) {
			// Toast.makeText(SmSurveyAddVehicleAct.this, "识别失败",
			// Toast.LENGTH_SHORT).show();
			// }
		}
		else if (resultCode == Activity.RESULT_OK)
		{// 车牌识别返回
			// pic = data.getStringExtra("path");//返回的要识别的图片地址
			// carType = data.getIntExtra("type", -1);//识别类型 1表示图片，2表示视频流
			// recogIntent = new Intent(this, RecogService.class);// 启动识别服务
			// bindService(recogIntent, recogConn, Service.BIND_AUTO_CREATE);
		}
	}

	/**
	 * 取消键盘的弹起
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

	/**
	 * 拦截返回按钮事件
	 */
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
