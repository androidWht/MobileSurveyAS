package com.sinosoft.ms.activity.task.survey.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.survey.SurveyAddVehicleAct;
import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.CarDriverData;
import com.sinosoft.ms.model.CarLossData;
import com.sinosoft.ms.model.CarPolicyData;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.SmVehicleInvolvedAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.CarDatasDatabase;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名： 4.车辆损失页面 著作权：COPYRIGHT (C) 2014 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:24:52
 */

public class SurveyVehicleFrag extends Fragment implements OnClickListener
{
	// 添加三者车标志
	private static final int ADD_THREE_VEHICLE_CODE = 4444;

	private View view;
	private ExpandableListView vehicleInvolvedList;
	private Button vehicleAddBtn;

	private CarData carData;
	private SmVehicleInvolvedAdapter vehicleAdapter;
	private VehicleAdapterListener vehicleAdapterListener;
	private String liabilityType = "1";

	// 责任比例 交强险、商业险责任比例列表
	private List<LiabilityRatio> liabilityRatios;
	// 交强险，责任险 代码
	private String indemnityDuty, cidutyFlag;

	// 数据字典
	private Map<String, Map<String, String>> dataMap;
	private Map<String, Map<String, String>> localItemConf;
	// 保单信息
	private RegistData RegistData;
	private List<PolicyData> policyDatas;
	// 报案号
	private String registNo;
	private String accidentCause;
	// 事故类型
	private String accidentType;
	// 查勘基本信息
	private SurveyTreatmentDatabase2 database;
	private BasicSurvey basicSurvey;
	private List<CarPolicyData> carPolicyData;
	// 车辆列表
	private List<CarData> carDatas;
	// 对话框
	private Dialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取查勘基本信息
		database = SurveyTreatmentDatabase2.getInstance();
		basicSurvey = database.getBasicSurvey();
		policyDatas = database.getPolicyDatas();
		liabilityRatios = database.getLiabilityRatios();
		// 设置监听
		vehicleAdapterListener = new VehicleAdapterListener();
		// 获取数据字典
		dataMap = DictCashe.getInstant().getDict();
		localItemConf = AppConstants.getLocalItemConf();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_survey_vehicle_item, container, false);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		setCarDataList();
	}

	@Override
	public void onDestroyView()
	{
		// TODO Auto-generated method stub
		super.onDestroyView();
		view = null;
	}

	private void initView(View vehicleView)
	{
		vehicleInvolvedList = (ExpandableListView) vehicleView.findViewById(R.id.vehicleInvolvedList);
		vehicleInvolvedList.setEnabled(false);
		vehicleAddBtn = (Button) vehicleView.findViewById(R.id.vehicle_add_btn);
		vehicleAddBtn.setOnClickListener(this);
	}

	/**
	 * 设置涉案车辆数据
	 */
	public void setCarDataList()
	{
		// SurveyTreatmentDatabase2 database = SurveyTreatmentDatabase2
		// .getInstance();
		// 获取涉案车辆
		carDatas = database.getCarDatas();
		if (carDatas == null)
		{
			carDatas = new ArrayList<CarData>();

		}
		if (carDatas.size() > 0)
		{
			if (!"050".equals(carDatas.get(0).getLossItemType()))
			{
				Collections.reverse(carDatas); // 翻转数据
			}
			// 获取标的车
			if ("050".equals(carDatas.get(0).getLossItemType()))
			{
				CarData car = carDatas.get(0);
				CarLossData loss = car.getCarLossData();
				// 如果是单方事故，并且标的车责任比例为空，则应该赋初值
				if (StringUtils.isEmpty(loss.getIndemnityDuty()) && "单方事故".equals(accidentType))
				{
					/** 交强险：全责 商业险：有责 **/
					// 设置交强险
					String indemnityDuty = SpinnerUtils.getKey("全责", dataMap.get("IndemnityDuty"));
					
					if ("050".equals(carDatas.get(0).getLossItemType()))
					{
						loss.setIndemnityDuty(indemnityDuty);
						loss.setIndemnityDutyRate(SpinnerUtils.getValue("全责", localItemConf.get("Rate")));
					}
					else
					{
						loss.setIndemnityDuty(carData.getCarLossData().getIndemnityDuty());
						loss.setIndemnityDutyRate(carData.getCarLossData().getIndemnityDutyRate());
					}
					
					// 设置商业险
					String cidutyFlag = SpinnerUtils.getKey("有责", localItemConf.get("CIDutyFlag"));
					car.setLiabilityType(cidutyFlag);

				}
				// 初始化商业险、交强险责任比例
				modifyInsuranceRatio(carDatas.get(0));
			}
		}

		vehicleAdapter = new SmVehicleInvolvedAdapter(carDatas, getActivity(), vehicleAdapterListener);
		vehicleInvolvedList.setAdapter(vehicleAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
	}

	class VehicleAdapterListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			Button btn = (Button) arg0;
			int index = (Integer) btn.getTag();
			carData = carDatas.get(index);
			String btnText = btn.getText().toString();
			// 如果点击了处理按钮
			if (btnText.equals("处理"))
			{
				if (carDatas != null && carDatas.size() > index)
				{
					Intent it = new Intent(getActivity(), SurveyAddVehicleAct.class);
					Bundle bundle = new Bundle();
					PolicyData policyData = policyDatas.get(0);
					bundle.putSerializable("bean", carData);
					bundle.putSerializable("policyData", policyData);
					boolean isExist = false;
					for (CarData carData : carDatas)
					{
						String lossItemType = carData.getLossItemType();
						if (StringUtils.isNotEmpty(lossItemType) && lossItemType.equals("050"))
						{
							isExist = true;
							break;
						}
					}
					bundle.putString("reason", accidentCause); // 出险原因
					bundle.putBoolean("isExist", isExist);
					// 损失详情
					CarLossData loss = carData.getCarLossData();
					loss.init();
					if (basicSurvey != null)
					{
						loss.setCheckSite(basicSurvey.getCheckSite() + "");
					}
					bundle.putString("LossItemType", carData.getLossItemType());
					bundle.putSerializable("loss", loss);
					// 驾驶员信息
					CarDriverData driver = carData.getCarDriverData();
					carData.init();
					driver.init();
					bundle.putSerializable("driver", driver);
					it.putExtra("item", bundle);
					startActivityForResult(it, ADD_THREE_VEHICLE_CODE);

				}
			}
			else if (btnText.equals(getActivity().getResources().getString(R.string.detele)))
			{
				if (dialog != null)
				{
					dialog.dismiss();
				}
				// 如果是删除按钮
				dialog = CustomDialog.show(getActivity(), "信息提示", "是否确定删除?", new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
						String lossItemType = carData.getLossItemType();
						if (StringUtils.isNotEmpty(lossItemType) && lossItemType.equals("050"))
						{
							CustomDialog.show(getActivity(), "信息提示", "涉案车辆中必须有一个标的车");
						}
						else
						{
							String id = carData.getCarId();
							// 删除数据库中的车辆信息
							if (StringUtils.isNotEmpty(id))
							{
								CarDatasDatabase database = new CarDatasDatabase(getActivity());
								database.deleteCarDataById(registNo, carData.getCarId());
							}
							// 删除车辆列表中的车辆信息，并更新列表
							carDatas.remove(carData);
							vehicleAdapter = new SmVehicleInvolvedAdapter(carDatas, getActivity(), vehicleAdapterListener);
							vehicleInvolvedList.setAdapter(vehicleAdapter);
							ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
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
			else
			{
				ToastDialog.show(getActivity(), "无选择项", ToastDialog.ERROR);
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ADD_THREE_VEHICLE_CODE && null != data)
		{
			// 插入三者车
			addVehicle(data);
		}
	}

	private void addVehicle(Intent data)
	{
		Bundle bundle = data.getBundleExtra("item");
		CarData carDataBean = (CarData) bundle.getSerializable("carDataBean"); // 涉案车辆-处理
		CarLossData detailBean = (CarLossData) bundle.getSerializable("detailBean"); // 损失详情
		CarDriverData driverBean = (CarDriverData) bundle.getSerializable("driverBean"); // 驾驶员信息

		carDataBean.setCarDriverData(driverBean);
		carDataBean.setCarLossData(detailBean);

		carDatas.remove(carData);
		carData = carDataBean;
		carDatas.add(carData);

		if ("050".equals(carDataBean.getLossItemType()))
		{
			Collections.reverse(carDatas); //  翻转
		}
		vehicleAdapter = new SmVehicleInvolvedAdapter(carDatas, getActivity(), vehicleAdapterListener);
		vehicleInvolvedList.setAdapter(vehicleAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);

		/** 如果修改了标的车的商业险/交强险比例，则会更改对应的代码值 **/
		// if("050".equals(carDataBean.getLossItemType())){
		// 设置商业险/交强险比例
		modifyInsuranceRatio(carDataBean);

		// }
	}

	/**
	 * 在标的车处理中，如果修改了商业险赔偿责任，则会修改对应的责任比例代码
	 * 
	 * @param carLossData
	 */
	private void modifyInsuranceRatio(CarData carData)
	{
		// 商业险比例
		indemnityDuty = carData.getCarLossData().getIndemnityDuty();
		// 交强险比例
		cidutyFlag = null;
		// 无责
		if ("4".equals(indemnityDuty))
		{
			cidutyFlag = SpinnerUtils.getKey("无责", localItemConf.get("CIDutyFlag"));
			;
		}
		else
		{
			cidutyFlag = SpinnerUtils.getKey("有责", localItemConf.get("CIDutyFlag"));
		}
	}

	/**
	 * 取得责任比例的数据
	 */
	public List<LiabilityRatio> getAbilityRatioData()
	{
		// liabilityRatios.clear();
		if (null == liabilityRatios)
		{
			return null;
		}
		if (null == carDatas)
		{
			carDatas = database.getCarDatas();
		}
		if (null == indemnityDuty && null == cidutyFlag)
		{
			for (CarData carDataBean : carDatas)
			{
				// if("050".equals(carDataBean.getLossItemType())){
				// 设置商业险/交强险比例
				modifyInsuranceRatio(carDataBean);
				// }
			}
		}

		for (LiabilityRatio ratio : liabilityRatios)
		{
			ratio.init();
			// 商业险
			if (ratio.getPolicyFlag().equals("1"))
			{
				// 商业险赔偿责任
				// String indemnityDuty = SpinnerUtils
				// .getKey(lindemnityDutySp.getSelectedItem().toString(),
				// dataMap.get("IndemnityDuty"));
				// 获取责任比例对应的权重
				String value = dataMap.get("IndemnityDuty").get(indemnityDuty);
				
				if(carData ==null)
				{
					carData=carDatas.get(0);
				}
				if (carData.getLossItemType().equals("050"))
				{
				   ratio.setIndemnityDuty(indemnityDuty);
				   ratio.setIndemnityDutyRate(SpinnerUtils.getValue(value, localItemConf.get("Rate")));
				}
				else
				{      ratio.setIndemnityDuty(carData.getCarLossData().getIndemnityDuty());
					   ratio.setIndemnityDutyRate(carData.getCarLossData().getIndemnityDutyRate());
				}
				/**   **/
				ratio.setcIIndemDuty(SpinnerUtils.getKey("不属于", localItemConf.get("CIIndemDuty")));
				ratio.setClaimFlag(SpinnerUtils.getKey("属于", localItemConf.get("ClaimFlag")));
				if (StringUtils.isNotEmpty(indemnityDuty) && !indemnityDuty.equals("无责"))
				{
					String cIDutyFlag = SpinnerUtils.getKey("有责", localItemConf.get("CIDutyFlag"));
					ratio.setcIDutyFlag(cIDutyFlag);
				}
				else
				{
					String cIDutyFlag = SpinnerUtils.getKey("无责", localItemConf.get("CIDutyFlag"));
					ratio.setcIDutyFlag(cIDutyFlag);
				}
			}
			else
			{
				// String cIDutyFlag =
				// lcIDutyFlagSp.getSelectedItem().toString();
				if(carData ==null)
				{
					carData=carDatas.get(0);
				}
				
				
				
				String value = localItemConf.get("CIDutyFlag").get(cidutyFlag);
				ratio.setcIIndemDuty(SpinnerUtils.getKey("属于", localItemConf.get("CIIndemDuty")));
				ratio.setcIDutyFlag(cidutyFlag);
				ratio.setClaimFlag(SpinnerUtils.getKey("不属于", localItemConf.get("ClaimFlag")));
				if (StringUtils.isNotEmpty(value) && value.equals("有责"))
				{
					String indemnityDuty = "主责";
					

					if (carData.getLossItemType().equals("050"))
					{
						ratio.setIndemnityDuty(SpinnerUtils.getKey(indemnityDuty, dataMap.get("IndemnityDuty")));
						ratio.setIndemnityDutyRate(SpinnerUtils.getValue("主责", localItemConf.get("Rate")));
					}
					else
					{
						ratio.setIndemnityDuty(carData.getCarLossData().getIndemnityDuty());
						ratio.setIndemnityDutyRate(carData.getCarLossData().getIndemnityDutyRate());
					}
				}
				else
				{
					if (carData.getLossItemType().equals("050"))
					{
						ratio.setIndemnityDuty(SpinnerUtils.getKey("无责", dataMap.get("IndemnityDuty")));
						ratio.setIndemnityDutyRate(SpinnerUtils.getValue("无责", localItemConf.get("Rate")));
					}
					else
					{
						ratio.setIndemnityDuty(carData.getCarLossData().getIndemnityDuty());
						ratio.setIndemnityDutyRate(carData.getCarLossData().getIndemnityDutyRate());
					}
				}
			}
		}
		return liabilityRatios;
	}

	/**
	 * 车辆信息验证
	 * 
	 * @return
	 */
	public String vehicleVerify()
	{
		if (null == basicSurvey)
		{
			database = SurveyTreatmentDatabase2.getInstance();
			basicSurvey = database.getBasicSurvey();
		}
		if (null == dataMap)
		{
			// 获取数据字典
			dataMap = DictCashe.getInstant().getDict();
		}
		if (null == localItemConf)
		{
			localItemConf = AppConstants.getLocalItemConf();
		}
		if (null == carDatas)
		{
			carDatas = database.getCarDatas();
		}

		basicSurvey = database.getBasicSurvey();
		liabilityRatios = getAbilityRatioData();
		String errorMsg = "";
		/** 1.计算标的车和三者车的责任比例之和，并找出标的车 **/
		CarData car = null;
		// 责任比例之和
		int total = 0;
		for (CarData carData : carDatas)
		{
			if (null != carData)
			{
				// 找到责任比例
				CarLossData carLoss = carData.getCarLossData();
				String indemnityDutyRate = carLoss.getIndemnityDutyRate();
				total += StringUtils.toInt(indemnityDutyRate);
				// 找到标的车
				if (("050").equals(carData.getLossItemType()))
				{
					car = carData;
				}
				else
				{
					// 如果是三者车，则验证内容
					carData.init();
					String carOwner = carData.getCarOwner(); // 车主
					if (StringUtils.isEmpty(carOwner))
					{
						errorMsg = "请完整填写三者车信息";
					}
				}
			}
		}

		/** 2.标的车+全部三者车的责任比例【IndemnityDutyRate】之和不允许大于100% **/
		if (total > 100)
		{
		    errorMsg = "标的车+全部三者车的责任比例之和不允许大于100";
		}

		/** 3.对于标的车的驾驶员信息，证件类型、证件号码、驾驶证号码必须录入。 **/
		if (car != null)
		{
			CarDriverData carDriverData = car.getCarDriverData();
			carDriverData.init();
			String identifyType = carDriverData.getIdentifyType();// 证件类型 ;
			String identifyNumber = carDriverData.getIdentifyNumber();// 证件号码 ;
			String drivingLicenseNo = carDriverData.getDrivingLicenseNo();// 驾驶证号码
																			// ;
			String str = "涉案车辆中,对于标的车的驾驶员信息,";
			if (identifyType.trim().equals(""))
			{
				errorMsg = str + "证件类型不能为空";
			}
			else if (identifyNumber.trim().equals(""))
			{
				errorMsg = str + "证件号码不能为空";
			}
			else if (drivingLicenseNo.trim().equals(""))
			{
				errorMsg = str + "驾驶证号码不能为空";
			}
		}

		String indemnityDuty = null;
		String cIDutyFlag = null;
		if (StringUtils.isEmpty(errorMsg))
		{
			for (LiabilityRatio ratio : liabilityRatios)
			{
				// 商业险
				if ("1".equals(ratio.getPolicyFlag()))
				{
					// 判断商业险责任是否为空
					indemnityDuty = SpinnerUtils.getValue(ratio.getIndemnityDuty(), dataMap.get("IndemnityDuty"));
					if (StringUtils.isEmpty(indemnityDuty))
					{
						errorMsg = "商业险赔偿责任不能为空";
					}
				}
				else
				{
					// 交强险
					// 判断交强险责任是否为空
					cIDutyFlag = SpinnerUtils.getValue(ratio.getcIDutyFlag(), localItemConf.get("CIDutyFlag"));
					if (StringUtils.isEmpty(cIDutyFlag))
					{
						errorMsg = "交强险赔偿责任不能为空";
					}

				}
			}
		}

		if (StringUtils.isEmpty(errorMsg))
		{
			if (car != null)
			{
				if (StringUtils.isNotEmpty(indemnityDuty))
				{
					CarLossData carLossData = car.getCarLossData();
					if (carLossData != null)
					{
						String carDuty = SpinnerUtils.getValue(carLossData.getIndemnityDuty(), dataMap.get("IndemnityDuty"));
						if (StringUtils.isEmpty(carDuty) || !indemnityDuty.equals(carDuty))
						{
							// errorMsg = "责任比例中的商业险赔偿，应该与涉案车辆的标的车责任比例一致";
						}
					}
				}
			}
			if (StringUtils.isNotEmpty(indemnityDuty) && StringUtils.isNotEmpty(cIDutyFlag))
			{
				if (indemnityDuty.equals("无责") && !cIDutyFlag.equals("无责"))
				{
					errorMsg = "商业险赔偿责任必须与交强险赔偿责任同时为无责或有责";
				}
				else if (!indemnityDuty.equals("无责") && cIDutyFlag.equals("无责"))
				{
					errorMsg = "商业险赔偿责任必须与交强险赔偿责任同时为无责或有责";
				}
			}
		}

		return errorMsg;
	}

	// 添加三者车
	private void startForAddVehicle()
	{
		CarData car = new CarData();
		car.setRegistNo(registNo);
		car.setLossItemType("010");
		car.setLiabilityType(liabilityType);
		CarDriverData carDriverData = new CarDriverData();//
		CarLossData carLossData = new CarLossData();

		car.setCarDriverData(carDriverData);
		car.setCarLossData(carLossData);
		carDatas.add(car);
		vehicleAdapter = new SmVehicleInvolvedAdapter(carDatas, getActivity(), vehicleAdapterListener);
		vehicleInvolvedList.setAdapter(vehicleAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.vehicle_add_btn:
			// 点击增加三者车按钮
			startForAddVehicle();
			break;
		}
	}

	// 设置报案号
	public void refreshData(String registNo, String accidentType, RegistData registData)
	{
		this.registNo = registNo;
		this.accidentType = accidentType;
		this.RegistData = registData;
	}

	// 设置出现原因
	public void setAccidentCause(String accidentCause)
	{
		this.accidentCause = accidentCause;
	}

	public List<CarData> getCarDatas()
	{
		if (null != view)
		{
			return carDatas;
		}
		else
		{
			return null;
		}
	}

}
