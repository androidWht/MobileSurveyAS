package com.sinosoft.ms.activity.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.po.GeneralParty;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.service.ILossSreachService;
import com.sinosoft.ms.service.impl.BasicVehicleService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.LossAssistInfoService;
import com.sinosoft.ms.service.impl.LossFitInfoService;
import com.sinosoft.ms.service.impl.LossRepairInfoService;
import com.sinosoft.ms.service.impl.LossSreachService;
import com.sinosoft.ms.service.impl.LossVehicleService;
import com.sinosoft.ms.service.impl.PricePlanService;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.adapter.ChangeDetailItemAdapter;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.RepairDetailItemAdapter;
import com.sinosoft.ms.utils.adapter.SimleAssistAdapter;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.datadialog.DateDialogUtils;
import com.sinosoft.ms.utils.db.DictCashe;

/**
 * 系统名：移动查勘定损 子系统名：定损处理详情界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author yanzhipei
 * @createTime 上午11:36:02
 */

public class LossDetailsAct extends Activity {

	private Map<String, Map<String, String>> dataMap;// 数据字典集合
	private Map<String, Map<String, String>> localItemConf;
	private IDeflossDataService deflossDataService;
	private String registNo; // 定损编号
	private String taskId; // 任务ID
	private String registId; // 报案id号

	/** 定损基本信息控件 **/
	private TextView registNoTxt; // 报案号
	private TextView handlerCode; // 定损员
	private TextView repairFactoryType; // 修理厂类型
	private TextView defLossDate; // 定损日期
	private TextView cetainLossType; // 定损方式
	private TextView repairFactoryCode; // 修理厂名称
	private TextView defSite; // 定损地点
	private TextView sendDate; // 送修时间
	private TextView lossLevel; // 损失程度
	private TextView estimatedDate; // 修理完毕日期
	private TextView damagePurchasePrice; // 出险新车购置价
	private TextView caseFlag; // 互碰自赔标志
	private TextView intermediaryCode; // 公估公司代码
	private TextView intermediaryName; // 公估公司名称
	private TextView intermediaryFlag; // 定损类别
	private TextView lIntermediaryCode; // 公估公司名称
	private TextView lossPart; // 损失部位
	private TextView damageDate; // 出险时间
	private TextView lossOption; // 定损意见

	private TextView licenseNo; // 车牌号
	private TextView defLossCarType; // 损失方
	private TextView lossType; // 损失类型
	private TextView carOwner; // 车主
	private TextView enrollDate; // 初登日期
	private TextView carKindCode; // 车辆种类
	private TextView frameNo; // 车架号
	private TextView licenseColorCode; // 号牌底色
	private TextView modelCode; // 车型代码
	private TextView vinNo; // VIN码
	private TextView licenseType; // 车牌种类
	private TextView brandName; // 车型名称
	private TextView engineNo; // 发动机号
	private TextView gearboxType; // 变速箱
	private TextView gasType; // 燃料类型
	private TextView carKindFrom; // 车型产地
	private TextView insureComCode; // 承保公司
	private TextView loss_indemDutyCi; // 交强险责任类型
	private TextView loss_indemnityDuty; // 商业险赔偿责任

	/** 车辆基本信息 **/
	private TextView caseNo; // 事故号码
	private TextView plateNo; // 车牌号码
	private TextView vehKindCode; // 定损车辆种类名称
	private TextView vehYearType; // 初登年月
	private TextView vehCertainName; // 定损车型名称
	private TextView vehCertainCode; // 定损车型编码
	private TextView vehSeriName; // 定损车系名称
	private TextView vehSeriCode; // 定损车系编码
	private TextView vehFactoryName; // 厂家名称
	private TextView vehBrandName; // 品牌名称
	private TextView selfConfigFlag; // 自定义车型标志
	private TextView version; // 版本号
	private TextView vehGroupName; // 定损车组名称
	private TextView sumChgCompFee; // 合计换件金额
	private TextView sumRepairFee; // 合计修理金额
	private TextView sumMaterialFee; // 合计辅料费
	private TextView sumLossFee; // 定损合计金额
	private TextView remark; // 备注

	private List<LossRepairInfoItem> lossRepairInfoItemList;
	private LossRepairInfoService lossRepairInfoService;
	private ExpandableListView lossChangeModfiyList;
	private ExpandableListView lossRepairModfiyList;
	private DeflossData deflossData;
	private LossVehicleService lossVehicleService;
	private LossVehicle lossVehicle;
	private String initTime;
	private DateDialogUtils dateDialogUtils = null;
	private ProgressDialogUtil progress = null;
	private PricePlanService pricePlanService;
	private List<GeneralParty> generalPartyList;
	private ListView listView;
	private ILossAssistInfoService lossAssistInfoService;
	private List<LossAssistInfoItem> assistInfoList;
	private SimleAssistAdapter adapter;
	private List<Map<String, String>> data;
	private BasicVehicleService basicVehicleService;
	private BasicVehicle basicVehicle;
	private LossFitInfoService lossFitInfoService;
	private List<LossFitInfoItem> fitList;
	private Button treatmentBrackBtn;
	private ProgressDialogUtil progressDialog;

	// 日期控件实例化
	public LossDetailsAct() {

		dateDialogUtils = new DateDialogUtils();
		initTime = MyUtils.format("yyyy-MM-dd", new Date());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.setContentView(R.layout.activity_loss_basic_info_detail);
		ActivityManage.getInstance().push(this);
		Intent intent = getIntent();
		deflossDataService = new DeflossDataService();
		registNo = (String) intent.getCharSequenceExtra("registNo");
		if (registNo == null || registNo.trim().equals("")) {
			registNo = "";
		}
		taskId = (String) intent.getStringExtra("taskId");
		if (taskId == null || taskId.trim().equals("")) {
			taskId = "";
		}
		registId = (String) intent.getStringExtra("registId");
		if (registId == null || registId.trim().equals("")) {
			registId = "";
		}
		// 设置数据
		localItemConf = AppConstants.getLocalItemConf();
		dataMap = DictCashe.getInstant().getDict();
		// 设置视图控件
		setViewControl();
		try {
			startRequestAndSave();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startRequestAndSave() {
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("数据加载中....");
		new Thread() {
			public void run() {
				Message msg = new Message();
				try {
					requestData(); // 加载数据信息
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					msg.what = 1;
					handler.sendMessage(msg);
				}
			};

		}.start();

	}

	/**
	 * 更新视图
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
			try {
				if (msg.what == 1) {
					loadBasicData(); // 加载基本信息
					loadVehicleData(); // 加载定损车辆信息
					loadAssistData(); // 加载辅料信息
					loadRepairData(); // 加载维修信息
					loadVehicleBasicData(); // 加载车辆基本信息
					loadChangeProjectData(); // 加载定损换件信息
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};

	};

	public void requestData() throws Exception {
		deflossData = deflossDataService.getByTaskId(taskId);
		if (null == deflossData) {
			ILossSreachService iLossSreachService = new LossSreachService();
			deflossData = iLossSreachService.sreach(UserCashe.getInstant()
					.getUser().getName(), this.taskId,registNo,registId);
		}
		deflossData.init();
		if (null == lossVehicleService) {
			lossVehicleService = new LossVehicleService();
		}
		if (null == lossVehicle) {
			lossVehicle = lossVehicleService.getByRegistNo(taskId);
		}

		if (null == lossVehicleService) {
			lossVehicleService = new LossVehicleService();
		}
		if (null == deflossDataService) {
			deflossDataService = new DeflossDataService();
		}
		if (null == deflossData) {
			deflossData = deflossDataService.getByRegistNo(registNo);
		}
		lossVehicle = lossVehicleService.getByRegistNo(taskId);
		if (lossVehicle == null) {
			ILossSreachService iLossSreachService = new LossSreachService();
			lossVehicle = iLossSreachService.sreach(
					UserCashe.getInstant().getUser().getName(), this.taskId,registNo,registId)
					.getLossVehicle();
		}

		if (null == lossAssistInfoService) {
			lossAssistInfoService = new LossAssistInfoService();
		}
		assistInfoList = lossAssistInfoService.getByRegistNo(taskId);

		if (null == lossRepairInfoService) {
			lossRepairInfoService = new LossRepairInfoService();
		}
		lossRepairInfoItemList = lossRepairInfoService.getByRegistNo(taskId);

		if (null == basicVehicleService) {
			basicVehicleService = new BasicVehicleService();
		}
		basicVehicle = basicVehicleService.getByRegistNo(taskId);
		if (null == basicVehicle) {
			ILossSreachService iLossSreachService = new LossSreachService();
			basicVehicle = iLossSreachService.sreach(
					UserCashe.getInstant().getUser().getName(), this.taskId,registNo,registId)
					.getBasicVehicle();
		}

		if (null == lossFitInfoService) {
			lossFitInfoService = new LossFitInfoService();
		}
		try {
			fitList = lossFitInfoService.getByRegistNo(taskId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadBasicData() throws Exception {
		// 1.设置视图数据
		repairFactoryType.setText(SpinnerUtils.getValue(deflossData.getRepairFactoryType(), dataMap.get("RepairFactoryType"))) ; // 维修工广场类型
		lossOption.setText(deflossData.getHistoryString());
		if (StringUtils.isNotEmpty(deflossData.getRegistNo())) {
			registNoTxt.setText(deflossData.getRegistNo());
		}
		if (StringUtils.isNotEmpty(deflossData.getHandlerName())) {
			handlerCode.setText(deflossData.getHandlerName());
		}
		if (StringUtils.isNotEmpty(deflossData.getRepairFactoryCode())) {
			repairFactoryCode.setText(deflossData.getRepairFactoryName());
		}
		if (StringUtils.isNotEmpty(deflossData.getDefLossDate())) {
			defLossDate.setText(deflossData.getDefLossDate());
		} else {
			defLossDate.setText(initTime);
		}
		if (StringUtils.isNotEmpty(deflossData.getDefSite())) {
			defSite.setText(deflossData.getDefSite());
		}
		if (StringUtils.isNotEmpty(deflossData.getSendDate())) {
			sendDate.setText(deflossData.getSendDate());
		} else {
			sendDate.setText(initTime);
		}

		if (StringUtils.isNotEmpty(deflossData.getEstimatedDate())) {
			estimatedDate.setText(deflossData.getEstimatedDate());
		} else {
			estimatedDate.setText(initTime);
		}
		if (StringUtils.isNotEmpty(deflossData.getLossPart())) {
			lossPart.setText(deflossData.getLossPart());
		}
		if (StringUtils.isNotEmpty(deflossData.getDamagePurchasePrice())) {
			damagePurchasePrice.setText(deflossData.getDamagePurchasePrice());
		}
		if (StringUtils.isNotEmpty(deflossData.getIntermediaryCode())) {
			intermediaryCode.setText(deflossData.getIntermediaryCode());
		}
		if (StringUtils.isNotEmpty(deflossData.getIntermediaryName())) {
			intermediaryName.setText(deflossData.getIntermediaryName());
		}
		if (StringUtils.isNotEmpty(deflossData.getDamageDate())) {
			damageDate.setText(deflossData.getDamageDate());
		} else {
			damageDate.setText(initTime);
		}
		this.cetainLossType.setText(SpinnerUtils.getValue(
				deflossData.getCetainLossType() + "",
				dataMap.get("CertainLossType")));
		this.intermediaryFlag.setText(SpinnerUtils.getValue(
				deflossData.getIntermediaryFlag(),
				localItemConf.get("IntermediaryFlag")));
//		this.repairFactoryCode.setText(SpinnerUtils.getValue(
//				deflossData.getRepairFactoryCode(),
//				dataMap.get("RepairFactory")));
		String lossLevel = null;
		if(StringUtils.isEmpty(deflossData.getLossLevel())){
			lossLevel = "轻度受损";
		}else{
			if(deflossData.getLossLevel().length()==1){
				deflossData.setLossLevel("0"+deflossData.getLossLevel());
			}
			lossLevel = SpinnerUtils.getValue(deflossData.getLossLevel(),dataMap.get("LossLevel"));
		}
		this.lossLevel.setText(lossLevel);
		this.lIntermediaryCode.setText(SpinnerUtils.getValue(
				deflossData.getIntermediaryCode(),
				localItemConf.get("IntermediaryInfoCode")));
		this.caseFlag.setText(SpinnerUtils.getValue(deflossData.getCaseFlag(),
				localItemConf.get("Status")));
	}

	/** 加载定损车辆信息 **/
	private void loadVehicleData() {
		try {
			lossVehicle.init();
			registNoTxt.setText(registNo);
			licenseNo.setText(lossVehicle.getLicenseNo());
			carOwner.setText(lossVehicle.getCarOwner());
			enrollDate.setText(lossVehicle.getEnrollDate());
			frameNo.setText(lossVehicle.getFrameNo());

			licenseColorCode.setText(lossVehicle.getLicenseColorCode());
			modelCode.setText(lossVehicle.getModelCode());
			vinNo.setText(lossVehicle.getVinNo());
			brandName.setText(lossVehicle.getBrandName());
			gearboxType.setText(this.dataMap.get("GearBoxType").get(lossVehicle.getGearboxType()));
			gasType.setText(this.dataMap.get("GasType").get(lossVehicle.getGasType()));
			carKindFrom.setText(this.dataMap.get("ProduceArea").get(lossVehicle.getCarKindFrom()));
			engineNo.setText(lossVehicle.getEngineNo());
			insureComCode.setText(this.dataMap.get("ComCode").get(lossVehicle.getInsureComCode()));
			loss_indemDutyCi.setText(lossVehicle.getIndemDutyCi());
			loss_indemnityDuty.setText(lossVehicle.getIndemnityDuty());
			// 设置下拉数据
			defLossCarType.setText(SpinnerUtils.getValue(
					lossVehicle.getDefLossCarType(),
					localItemConf.get("LossItemType")));
			// 定损规则:当损失方为三者车时【DefLossCarType=01】，车辆损失类别【LossType】在数据字典中的类型是【CodeType=OtherLossType】
			if ("2".equals(lossVehicle.getDefLossCarType())) {
				lossType.setText(SpinnerUtils.getValue(
						lossVehicle.getLossType(), dataMap.get("OtherLossType")));
			} else {
				lossType.setText(SpinnerUtils.getValue(
						lossVehicle.getLossType(), dataMap.get("LossType")));
			}
			carKindCode.setText(SpinnerUtils.getValue(
					lossVehicle.getCarKindCode(), dataMap.get("CarKind")));
			this.licenseType.setText(SpinnerUtils.getValue(
					lossVehicle.getLicenseType(),
					dataMap.get("LicenseKindCode")));
			this.licenseColorCode.setText(SpinnerUtils.getValue(
					lossVehicle.getLicenseColorCode(),
					dataMap.get("LicenseColorCode")));
			loss_indemDutyCi.setText(SpinnerUtils.getValue(
					lossVehicle.getIndemDutyCi(), localItemConf.get("IndemDutyCI")));
			loss_indemnityDuty.setText(SpinnerUtils.getValue(
					lossVehicle.getIndemnityDuty(),
					dataMap.get("IndemnityDuty")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadAssistData() {
		try {
			if (null != assistInfoList && !assistInfoList.isEmpty()) {
				data = new ArrayList<Map<String, String>>();
				Iterator iter = assistInfoList.iterator();
				while (iter.hasNext()) {
					LossAssistInfoItem assistInfoItem = (LossAssistInfoItem) iter
							.next();
					assistInfoItem.init();
					Map<String, String> m = new HashMap<String, String>();
					m.put("id", String.valueOf(assistInfoItem.getId()));
					m.put("MaterialName", MyUtils.convertNull(assistInfoItem
							.getMaterialName()));
					m.put("MaterialCode", MyUtils.convertNull(assistInfoItem
							.getMaterialCode()));
					m.put("UnitPrice",
							String.valueOf(assistInfoItem.getUnitPrice()));
					m.put("Count", String.valueOf(assistInfoItem.getCount()));
					m.put("MaterialFee",
							String.valueOf(assistInfoItem.getMaterialFee()));
					m.put("InsureTerm",
							MyUtils.convertNull(assistInfoItem.getInsureTerm()));
					m.put("defineType", assistInfoItem.getDefineType());
					data.add(m);
				}
				adapter = new SimleAssistAdapter(this, data,
						R.layout.item_loss_assist_query, new String[] {
								"MaterialName", "MaterialCode", "UnitPrice",
								"Count", "MaterialFee", "InsureTerm" },
						new int[] { R.id.nameTxt, R.id.codeTxt, R.id.priceTxt,
								R.id.countTxt, R.id.moneyTxt, R.id.insurTxt },
						new int[] {}, null, listView);
				listView.setAdapter(adapter);
				ExpandableUtil.setListViewHeightBasedOnChildren(listView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 加载维修信息 **/
	public void loadRepairData() {
		if (null != lossRepairInfoItemList && !lossRepairInfoItemList.isEmpty()) {
			RepairDetailItemAdapter adapter = new RepairDetailItemAdapter(
					lossRepairInfoItemList, registNo, this,
					lossRepairModfiyList, null);
			lossRepairModfiyList.setAdapter(adapter);
			ExpandableUtil
					.setListViewHeightBasedOnChildren(lossRepairModfiyList);
			lossRepairModfiyList
					.setOnGroupExpandListener(new OnGroupExpandListener() {

						@Override
						public void onGroupExpand(int groupPosition) {
							ExpandableUtil
									.setListViewHeightBasedOnChildren(lossRepairModfiyList);
						}
					});
			lossRepairModfiyList
					.setOnGroupCollapseListener(new OnGroupCollapseListener() {

						@Override
						public void onGroupCollapse(int groupPosition) {
							ExpandableUtil
									.setListViewHeightBasedOnChildren(lossRepairModfiyList);
						}
					});
		}
	}

	/** 车辆基本信息 **/
	private void loadVehicleBasicData() {
		try {
			if (StringUtils.isNotEmpty(taskId)) {
				basicVehicle.init();
				caseNo.setText("2323");// basicVehicle.getCaseNo() + "234234");
				vehCertainCode.setText(basicVehicle.getVehCertainCode());
				vehCertainName.setText(basicVehicle.getVehCertainName());
				vehSeriCode.setText(basicVehicle.getVehSeriCode());
				vehSeriName.setText(basicVehicle.getVehSeriName());
				vehYearType.setText(basicVehicle.getVehYearType());
				vehFactoryName.setText(basicVehicle.getVehFactoryName());
				vehBrandName.setText(basicVehicle.getVehBrandName());

				plateNo.setText(basicVehicle.getPlateNo());
				if (basicVehicle.getPlateNo().equals("")) {
					plateNo.setText("粤A3333");
				}
				selfConfigFlag.setText(this.localItemConf.get("Status").get(basicVehicle.getSelfConfigFlag()+""));

				version.setText(basicVehicle.getVersion());

				remark.setText(basicVehicle.getRemark());

				vehGroupName.setText(basicVehicle.getVehGroupName());

				vehYearType.setText(basicVehicle.getVehYearType());

				sumChgCompFee.setText(String.valueOf(basicVehicle
						.getSumChgCompFee()));
				sumRepairFee.setText(String.valueOf(basicVehicle
						.getSumRepairFee()));
				sumMaterialFee.setText(String.valueOf(basicVehicle
						.getSumMaterialFee()));
				sumLossFee
						.setText(String.valueOf(basicVehicle.getSumLossFee()));
			}
			vehKindCode.setText(SpinnerUtils.getValue(
					basicVehicle.getVehKindCode(), dataMap.get("CarKind")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 加载定损换件信息 **/
	public void loadChangeProjectData() {
		if (null != fitList) {
			ChangeDetailItemAdapter adapter = new ChangeDetailItemAdapter(
					fitList, registNo, this, lossChangeModfiyList, null);
			lossChangeModfiyList.setAdapter(adapter);
			ExpandableUtil
					.setListViewHeightBasedOnChildren(lossChangeModfiyList);
			lossChangeModfiyList
					.setOnGroupExpandListener(new OnGroupExpandListener() {

						@Override
						public void onGroupExpand(int groupPosition) {
							ExpandableUtil
									.setListViewHeightBasedOnChildren(lossChangeModfiyList);
						}
					});
			lossChangeModfiyList
					.setOnGroupCollapseListener(new OnGroupCollapseListener() {

						@Override
						public void onGroupCollapse(int groupPosition) {
							ExpandableUtil
									.setListViewHeightBasedOnChildren(lossChangeModfiyList);
						}
					});
		}
	}

	/** 设置控件 **/
	public void setViewControl() {
		/** 基本信息 **/
		this.repairFactoryType = (TextView) super
				.findViewById(R.id.repairFactoryType);
		this.defLossDate = (TextView) super.findViewById(R.id.defLossDate);
		this.cetainLossType = (TextView) super
				.findViewById(R.id.cetainLossType);
		this.repairFactoryCode = (TextView) super
				.findViewById(R.id.repairFactoryCode);
		this.defSite = (TextView) super.findViewById(R.id.defSite);
		this.sendDate = (TextView) super.findViewById(R.id.sendDate);
		this.lossLevel = (TextView) super.findViewById(R.id.lossLevel);
		this.estimatedDate = (TextView) super.findViewById(R.id.estimatedDate);
		this.damagePurchasePrice = (TextView) super
				.findViewById(R.id.damagePurchasePrice);
		this.caseFlag = (TextView) super.findViewById(R.id.caseFlag);
		this.intermediaryCode = (TextView) super
				.findViewById(R.id.intermediaryCode);
		this.intermediaryName = (TextView) super
				.findViewById(R.id.intermediaryName);
		this.intermediaryFlag = (TextView) super
				.findViewById(R.id.intermediaryFlag);
		this.lIntermediaryCode = (TextView) super
				.findViewById(R.id.lIntermediaryCode);
		this.lossPart = (TextView) super.findViewById(R.id.lossPart);
		this.damageDate = (TextView) super.findViewById(R.id.damageDate);
		this.lossOption = (TextView) super.findViewById(R.id.lossOption);
		this.registNoTxt = (TextView) super.findViewById(R.id.registNoTxt);
		this.handlerCode = (TextView) super.findViewById(R.id.handlerCode);

		/** 车辆定损信息控件 **/
		this.licenseNo = (TextView) super.findViewById(R.id.licenseNo);
		this.defLossCarType = (TextView) super
				.findViewById(R.id.defLossCarType);
		this.lossType = (TextView) super.findViewById(R.id.lossType);
		this.carOwner = (TextView) super.findViewById(R.id.carOwner);
		this.enrollDate = (TextView) super.findViewById(R.id.enrollDate);
		this.carKindCode = (TextView) super.findViewById(R.id.carKindCode);
		this.frameNo = (TextView) super.findViewById(R.id.frameNo);
		this.licenseColorCode = (TextView) super
				.findViewById(R.id.licenseColorCode);
		this.modelCode = (TextView) super.findViewById(R.id.modelCode);
		this.vinNo = (TextView) super.findViewById(R.id.vinNo);
		this.licenseType = (TextView) super.findViewById(R.id.licenseType);
		this.brandName = (TextView) super.findViewById(R.id.brandName);
		this.engineNo = (TextView) super.findViewById(R.id.engineNo);
		this.gearboxType = (TextView) super.findViewById(R.id.gearboxType);
		this.gasType = (TextView) super.findViewById(R.id.gasType);
		this.carKindFrom = (TextView) super.findViewById(R.id.carKindFrom);
		this.insureComCode = (TextView) super.findViewById(R.id.insureComCode);
		this.loss_indemDutyCi = (TextView) super
				.findViewById(R.id.loss_indemDutyCi);
		this.loss_indemnityDuty = (TextView) super
				.findViewById(R.id.loss_indemnityDuty);

		/** 维修信息 **/
		lossRepairModfiyList = (ExpandableListView) findViewById(R.id.lossRepairModfiyList);
		/** 辅料信息 **/
		listView = (ListView) super.findViewById(R.id.lvperson);

		/** 车辆基本信息 **/
		this.caseNo = (TextView) super.findViewById(R.id.caseNo);
		this.plateNo = (TextView) super.findViewById(R.id.plateNo);
		this.vehKindCode = (TextView) super.findViewById(R.id.vehKindCode);
		this.vehYearType = (TextView) super.findViewById(R.id.vehYearType);
		this.vehCertainName = (TextView) super
				.findViewById(R.id.vehCertainName);
		this.vehCertainCode = (TextView) super
				.findViewById(R.id.vehCertainCode);
		this.vehSeriName = (TextView) super.findViewById(R.id.vehSeriName);
		this.vehSeriCode = (TextView) super.findViewById(R.id.vehSeriCode);
		this.vehFactoryName = (TextView) super
				.findViewById(R.id.vehFactoryName);
		this.vehBrandName = (TextView) super.findViewById(R.id.vehBrandName);
		this.selfConfigFlag = (TextView) super
				.findViewById(R.id.selfConfigFlag);
		this.version = (TextView) super.findViewById(R.id.version);
		this.vehGroupName = (TextView) super.findViewById(R.id.vehGroupName);
		this.sumChgCompFee = (TextView) super.findViewById(R.id.sumChgCompFee);
		this.sumRepairFee = (TextView) super.findViewById(R.id.sumRepairFee);
		this.sumMaterialFee = (TextView) super
				.findViewById(R.id.sumMaterialFee);
		this.sumLossFee = (TextView) super.findViewById(R.id.sumLossFee);
		this.remark = (TextView) super.findViewById(R.id.remark);

		/** 定损换件信息控件 **/
		lossChangeModfiyList = (ExpandableListView) findViewById(R.id.lossChangeModfiyList);

		this.treatmentBrackBtn = (Button) super
				.findViewById(R.id.treatmentBrackBtn); // 返回按钮
		this.treatmentBrackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					LossDetailsAct.this.finish();
					ActivityManage.getInstance().pop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		localItemConf = null;
		deflossDataService = null;
		registNo = null; // 定损编号
		taskId = null; // 任务ID
		registNoTxt = null; // 报案号
		handlerCode = null; // 定损员
		repairFactoryType = null; // 修理厂类型
		defLossDate = null; // 定损日期
		cetainLossType = null; // 定损方式
		repairFactoryCode = null; // 修理厂名称
		defSite = null; // 定损地点
		sendDate = null; // 送修时间
		lossLevel = null; // 损失程度
		estimatedDate = null; // 修理完毕日期
		damagePurchasePrice = null; // 出险新车购置价
		caseFlag = null; // 互碰自赔标志
		intermediaryCode = null; // 公估公司代码
		intermediaryName = null; // 公估公司名称
		intermediaryFlag = null; // 定损类别
		lIntermediaryCode = null; // 公估公司名称
		lossPart = null; // 损失部位
		damageDate = null; // 出险时间
		lossOption = null; // 定损意见
		licenseNo = null; // 车牌号
		defLossCarType = null; // 损失方
		lossType = null; // 损失类型
		carOwner = null; // 车主
		enrollDate = null; // 初登日期
		carKindCode = null; // 车辆种类
		frameNo = null; // 车架号
		licenseColorCode = null; // 号牌底色
		modelCode = null; // 车型代码
		vinNo = null; // VIN码
		licenseType = null; // 车牌种类
		brandName = null; // 车型名称
		engineNo = null; // 发动机号
		gearboxType = null; // 变速箱
		gasType = null; // 燃料类型
		carKindFrom = null; // 车型产地
		insureComCode = null; // 承保公司
		loss_indemDutyCi = null; // 交强险责任类型
		loss_indemnityDuty = null; // 商业险赔偿责任
		caseNo = null; // 事故号码
		plateNo = null; // 车牌号码
		vehKindCode = null; // 定损车辆种类名称
		vehYearType = null; // 初登年月
		vehCertainName = null; // 定损车型名称
		vehCertainCode = null; // 定损车型编码
		vehSeriName = null; // 定损车系名称
		vehSeriCode = null; // 定损车系编码
		vehFactoryName = null; // 厂家名称
		vehBrandName = null; // 品牌名称
		version = null; // 版本号
		sumChgCompFee = null; // 合计换件金额
		sumRepairFee = null; // 合计修理金额
		sumMaterialFee = null; // 合计辅料费
		sumLossFee = null; // 定损合计金额
		remark = null; // 备注
		lossRepairInfoItemList = null;
		lossRepairInfoService = null;
		lossChangeModfiyList = null;
		lossRepairModfiyList = null;
		deflossData = null;
		lossVehicleService = null;
		lossVehicle = null;
		initTime = null;
		dateDialogUtils = null;
		progress = null;
		pricePlanService = null;
		generalPartyList = null;
		listView = null;
		lossAssistInfoService = null;
		assistInfoList = null;
		adapter = null;
		data = null;
		basicVehicleService = null;
		basicVehicle = null;
		lossFitInfoService = null;
		fitList = null;
		treatmentBrackBtn = null;
		progressDialog = null;

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		try {
			this.finish();
			ActivityManage.getInstance().pop() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
