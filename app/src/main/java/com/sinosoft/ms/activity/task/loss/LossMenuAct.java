package com.sinosoft.ms.activity.task.loss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.LoginAct;
import com.sinosoft.ms.activity.TestData;
import com.sinosoft.ms.activity.query.BluetoothActivity;
import com.sinosoft.ms.activity.task.image.PictureGalleryActivity;
import com.sinosoft.ms.activity.task.image.SurveyPictureExamineActivity;
import com.sinosoft.ms.activity.task.image.UploadImageActivity;
import com.sinosoft.ms.activity.task.image.camera.CameraActivity;
import com.sinosoft.ms.activity.task.loss.assist.AssistDefineAct;
import com.sinosoft.ms.activity.task.loss.assist.AssistModfiyAct;
import com.sinosoft.ms.activity.task.loss.change.ChangeProjectModfiyAct;
import com.sinosoft.ms.activity.task.loss.change.ChangeProjectdefineAct;
import com.sinosoft.ms.activity.task.loss.change.ChangeQueryAct;
import com.sinosoft.ms.activity.task.loss.repair.RepairAddAct;
import com.sinosoft.ms.activity.task.loss.vehicle.VehicleTypeListAct;
import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.DeflossOptionData;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.InsuredData;
import com.sinosoft.ms.model.Item;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.LossAssistInfoItem;
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.model.LossRepairInfoItem;
import com.sinosoft.ms.model.LossVehicle;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PolicyDetailData;
import com.sinosoft.ms.model.PolicyKindData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.po.Brand;
import com.sinosoft.ms.model.po.CacheImage;
import com.sinosoft.ms.model.po.CacheLossImage;
import com.sinosoft.ms.model.po.Car;
import com.sinosoft.ms.model.po.Emus;
import com.sinosoft.ms.model.po.GeneralParty;
import com.sinosoft.ms.model.po.ImageBean;
import com.sinosoft.ms.model.po.Manufacturer;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.service.IDeflossDataService;
import com.sinosoft.ms.service.IKindCodeDataService;
import com.sinosoft.ms.service.ILossAssistInfoService;
import com.sinosoft.ms.service.ILossFitInfoService;
import com.sinosoft.ms.service.ILossRepairInfoService;
import com.sinosoft.ms.service.ILossSreachService;
import com.sinosoft.ms.service.ILossVehicleService;
import com.sinosoft.ms.service.IPrintInfoService;
import com.sinosoft.ms.service.IRepairFactoryService;
import com.sinosoft.ms.service.IThirdPartyService;
import com.sinosoft.ms.service.impl.BankInfoService;
import com.sinosoft.ms.service.impl.BasicVehicleService;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.HeartbeatService;
import com.sinosoft.ms.service.impl.ImageUploadService;
import com.sinosoft.ms.service.impl.InsuredDataService;
import com.sinosoft.ms.service.impl.KindCodeDataService;
import com.sinosoft.ms.service.impl.LossAssistInfoService;
import com.sinosoft.ms.service.impl.LossFitInfoService;
import com.sinosoft.ms.service.impl.LossRepairInfoService;
import com.sinosoft.ms.service.impl.LossService;
import com.sinosoft.ms.service.impl.LossSreachService;
import com.sinosoft.ms.service.impl.LossVehicleService;
import com.sinosoft.ms.service.impl.PricePlanService;
import com.sinosoft.ms.service.impl.PrintInfoService;
import com.sinosoft.ms.service.impl.RepairFactoryService;
import com.sinosoft.ms.service.impl.TaskCenterService;
import com.sinosoft.ms.service.impl.ThirdPartyService;
import com.sinosoft.ms.utils.ATobigA;
import com.sinosoft.ms.utils.ActivityManage;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.MyUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.Utility;
import com.sinosoft.ms.utils.adapter.AutoCompleteArrayAdapter;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.LossChangeItemAdapter;
import com.sinosoft.ms.utils.adapter.MediaSimpleAdapter;
import com.sinosoft.ms.utils.adapter.SimleAssistAdapter;
import com.sinosoft.ms.utils.adapter.SmRepairItemAdapter;
import com.sinosoft.ms.utils.adapter.SurveyPagerAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.FactoryDialog;
import com.sinosoft.ms.utils.component.ImageGallery;
import com.sinosoft.ms.utils.component.ProgressDialogUtil;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.component.datadialog.DateDialogUtils;
import com.sinosoft.ms.utils.db.CommonFactoryDatabase;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.ImageCenterDatabase;
import com.sinosoft.ms.utils.db.ImageCenterDatabase.DatabaseCallback;

/**
 * 系统名：移动查勘定损系统 子系统名： 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 2013-5-28 下午3:56:35
 */
@SuppressWarnings("all")
public class LossMenuAct extends Activity implements OnClickListener, OnPageChangeListener, OnCheckedChangeListener
{
	private final String title[] = new String[]
	{ "定损基本信息", "定损车辆信息", "车辆基本信息", "定损换件信息", "定损维修信息", "辅料信息" };
	// private final int icon[] = new int[] { R.drawable.sm_loss_basic_logo,
	// R.drawable.sm_loss_car_logo, R.drawable.sm_loss_car_basic_logo,
	// R.drawable.sm_loss_change_logo, R.drawable.sm_loss_repair_logo,
	// R.drawable.sm_loss_assist_logo };
	private Map<String, Map<String, String>> dataMap = null;
	private IDeflossDataService deflossDataService = null;
	private IPrintInfoService printInfoService = null;
	private ILossSreachService lossSreachService = null;
	private IBasicVehicleService basicVehicleService = null;
	private BasicVehicle basicVehicle = null;
	private ProgressDialogUtil progressDialog = null;
	private DeflossData deflossData = null;
	private String Systemtime = "";
	private double sum_loss_fee = 0; // 定损总金额
	private static int currentPage;
	private RadioGroup radioGroup = null;
	private ViewPager viewPager = null;
	private List<View> views;
	private SurveyPagerAdapter surveyPagerAdapter;
	private View smTakePhotoView = null; // 2、定损拍照
	private View vehicleTypeQueryView = null; // 3、定损车辆信息（未定型）
	private View vehicleBasicView = null;// 3、定损车辆信息（已定型）
	private View smLossBasicView = null; // 4、定损信息
	private View smBankInfoView = null; // 8、银行信息
	private View smChangeItemView = null; // 5、换件录入
	private View smRepairItemView = null; // 6、维修信息录入
	private View smAssistItemView = null; // 7、辅料信息录入
	private View smTaskSubmitView = null; // 9、任务提交
	private View policyView;// 1、保单信息
	// private RadioButton yesRTBtn, noRTBtn;
	private Button backBtn = null;

	private String printContext = null;
	private String registNo = null;
	private String taskId = null;
	private String registId = null;
	private String vehicleType = null;
	private RegistData registData = null;
	private String reportorMobile = null;
	private boolean isStandardCar = true; // 是否标的车

	private final int SAVE_SUCCESS = 1100;
	private boolean isFinish = false;
	private Dialog dialog = null;
	private Dialog saveDialog;

	private Button mSurveyPictureBtn;

	// private boolean isTakedPhoto = false ;

	private boolean isUploadDocuments = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loss_menu);
		ActivityManage.getInstance().push(this);
		dataMap = DictCashe.getInstant().getDict();
		Intent intent = getIntent();
		registNo = intent.getStringExtra("registNo");
		taskId = intent.getStringExtra("taskId");
		registId = intent.getStringExtra("registId");
		reportorMobile = intent.getStringExtra("reportorMobile");
		Bundle bundle = intent.getBundleExtra("item");
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		if (bundle != null)
		{
			registData = (RegistData) bundle.getSerializable("bean");
		}
		if (registData == null)
		{
			registData = new RegistData();
			registData.init();
		}
		getSystemTime();
		loadData();
		currentPage = 0;
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(this);
		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(this);

		mSurveyPictureBtn = (Button) findViewById(R.id.survey_picture_btn);
		mSurveyPictureBtn.setOnClickListener(this);

		// try {
		// Thread.sleep(1000) ;
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		setViewPager();
	}

	private void getSystemTime()
	{
		Systemtime = MyUtils.format("yyyy-MM-dd HH:mm:ss", new Date(System.currentTimeMillis()));
		// Log.i("SystemTime", Systemtime);
	}

	/**
	 * 
	 */
	private void loadData()
	{
		try
		{
			if (null == deflossDataService)
			{
				deflossDataService = new DeflossDataService();
			}
			// deflossData = deflossDataService.getByTaskId(taskId);
			if (null == deflossData)
			{
				progressDialog = new ProgressDialogUtil(this);
				progressDialog.show("数据加载中...");
				getDefLossData();
			}

			if (deflossData != null)
			{
				deflossData.init();
				AppConstants.cetainLossType = deflossData.getCetainLossType();
			}

		}
		catch (Exception e)
		{
			// e.printStackTrace();
			ToastDialog.show(this, e.getMessage(), ToastDialog.ERROR);
		}
	}

	private void initVehicle()
	{
		try
		{
			if (null == basicVehicleService)
			{
				basicVehicleService = new BasicVehicleService();
			}
			basicVehicle = basicVehicleService.getByRegistNo(taskId);
			basicVehicle.init();
			sum_loss_fee = basicVehicle.getSumLossFee();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * 从服务器获取定损信息
	 */
	private void getDefLossData()
	{
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					sleep(1000);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
				try
				{
					// 根据报案号获取定损信息
					if (null == lossSreachService)
					{
						lossSreachService = new LossSreachService();
					}
					User user = UserCashe.getInstant().getUser();
					// Log.i("system",
					// DateTimeFactory.getInstance().getDateTime());
					deflossData = lossSreachService.sreach(user.getName(), taskId, registNo, registId, Systemtime);

					if (null != deflossData)
					{

						msg.what = 1;
						msg.obj = deflossData;
					}
					else
					{
						msg.what = 2;
						msg.obj = "报案号\"" + registNo + "\"数据己删除或不存在!";
					}
				}
				catch (Exception e)
				{
					msg.what = MyUtils.getErrorCode(e.getMessage());
					msg.obj = e.getMessage() + "";
				}
				finally
				{
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	// *** 保单信息 start

	// 商业险
	private TextView policy_policyNo;
	private TextView policy_insuredName;
	private TextView policy_licenseNo;
	private TextView policy_brandName;
	private TextView policy_startDate;
	private TextView policy_endDate;
	private ListView leftInsuranceList;
	private ListView rightInsuranceList;

	// 标题栏
	private TextView dividerTV;
	private TextView rightTitle1;
	private TextView rightTitle2;
	private TextView rightTitle3;
	private TextView rightTitle4;
	private TextView dividerTv1;
	private TextView dividerTv2;
	private LinearLayout policyLayout;
	private LinearLayout buninessLayout;

	// 交强险
	private TextView policy_policyNo2;
	private TextView policy_insuredName2;
	private TextView policy_licenseNo2;
	private TextView policy_brandName2;
	private TextView policy_startDate2;
	private TextView policy_endDate2;
	private ListView leftInsuranceList2;
	private ListView rightInsuranceList2;

	private List<PolicyData> policyDatas;
	private PolicyData policyData = null;
	private PolicyDetailData policyDetailData;
	private String carKind;

	/**
	 * 保单视图
	 */
	public void setPolicyView()
	{
		policy_policyNo = (TextView) policyView.findViewById(R.id.policy_policyNo);
		policy_insuredName = (TextView) policyView.findViewById(R.id.policy_insuredName);
		policy_licenseNo = (TextView) policyView.findViewById(R.id.policy_licenseNo);
		policy_brandName = (TextView) policyView.findViewById(R.id.policy_brandName);
		policy_startDate = (TextView) policyView.findViewById(R.id.policy_startDate);
		policy_endDate = (TextView) policyView.findViewById(R.id.policy_endDate);
		leftInsuranceList = (ListView) policyView.findViewById(R.id.leftInsuranceList);
		rightInsuranceList = (ListView) policyView.findViewById(R.id.rightInsuranceList);

		rightTitle1 = (TextView) policyView.findViewById(R.id.rightTitle1);
		rightTitle2 = (TextView) policyView.findViewById(R.id.rightTitle2);
		rightTitle3 = (TextView) policyView.findViewById(R.id.rightTitle3);
		rightTitle4 = (TextView) policyView.findViewById(R.id.rightTitle4);
		dividerTv1 = (TextView) policyView.findViewById(R.id.dividerTv1);
		dividerTv2 = (TextView) policyView.findViewById(R.id.dividerTv2);
		dividerTV = (TextView) policyView.findViewById(R.id.dividerTV);
		policyLayout = (LinearLayout) policyView.findViewById(R.id.policyLayout);
		buninessLayout = (LinearLayout) policyView.findViewById(R.id.businessLayout);

		// 交强险
		policy_policyNo2 = (TextView) policyView.findViewById(R.id.policy_policyNo2);
		policy_insuredName2 = (TextView) policyView.findViewById(R.id.policy_insuredName2);
		policy_licenseNo2 = (TextView) policyView.findViewById(R.id.policy_licenseNo2);
		policy_brandName2 = (TextView) policyView.findViewById(R.id.policy_brandName2);
		policy_startDate2 = (TextView) policyView.findViewById(R.id.policy_startDate2);
		policy_endDate2 = (TextView) policyView.findViewById(R.id.policy_endDate2);
		leftInsuranceList2 = (ListView) policyView.findViewById(R.id.leftInsuranceList2);
		rightInsuranceList2 = (ListView) policyView.findViewById(R.id.rightInsuranceList2);
	}

	/**
	 * 设置保单信息数据
	 */
	public void getPolicyData()
	{
		// if(this.deflossData == null){
		// return ;
		// }
		// 获取保单信息
		policyDatas = this.deflossData.getPolicyDatas();
		if (this.policyDatas != null && !policyDatas.isEmpty())
		{
			for (PolicyData p : this.policyDatas)
			{
				// 商业险
				if ("0801".equals(p.getRiskCode()))
				{
					// 显示布局
					policyLayout.setVisibility(View.VISIBLE);
					dividerTV.setVisibility(View.VISIBLE);

					policyDetailData = p.getPolicyDetailData();
					policy_policyNo2.setText(p.getPolicyNo());
					String code = policyDetailData.getRiskCode();
					if (StringUtils.isEmpty(code))
					{
						code = "";
					}
					policy_insuredName2.setText(policyDetailData.getInsuredName());
					policy_licenseNo2.setText(policyDetailData.getLicenseNo());
					policy_brandName2.setText(policyDetailData.getBrandName());
					policy_startDate2.setText(policyDetailData.getStartDate());
					policy_endDate2.setText(policyDetailData.getEndDate());

					List<Map<String, String>> data = new ArrayList<Map<String, String>>();
					List<PolicyKindData> policyKindDataList = p.getPolicyKindDataList();
					int psize = policyKindDataList.size();

					// 左列表
					List<Map<String, String>> leftList = new ArrayList<Map<String, String>>();
					// 右列表
					List<Map<String, String>> rightList = new ArrayList<Map<String, String>>();

					for (int i = 0; i < psize; i++)
					{
						Map<String, String> map = new HashMap<String, String>();
						PolicyKindData policyKindData = policyKindDataList.get(i);

						String remark = policyKindData.getRemark();
						map.put("kindName", policyKindData.getKindName());
						map.put("amount", policyKindData.getAmount());
						map.put("remark", remark);
						if (i <= psize / 2)
						{
							leftList.add(map);
						}
						else
						{
							rightList.add(map);
						}
						// data.add(map);
					}

					// 左列表
					SimpleAdapter leftAdapter = new SimpleAdapter(this, leftList, R.layout.item_insurance_info_detail_item, new String[]
					{ "kindName", "remark" }, new int[]
					{ R.id.policy_kindName, R.id.policy_remark });
					leftInsuranceList2.setAdapter(leftAdapter);
					new Utility().setListViewHeightBasedOnChildren(leftInsuranceList2);

					// 右列表
					if (rightList.size() > 0)
					{
						SimpleAdapter rightAdapter = new SimpleAdapter(this, rightList, R.layout.item_insurance_info_detail_item, new String[]
						{ "kindName", "remark" }, new int[]
						{ R.id.policy_kindName, R.id.policy_remark });
						rightInsuranceList2.setAdapter(rightAdapter);
						new Utility().setListViewHeightBasedOnChildren(rightInsuranceList2);
					}
					else
					{
						rightTitle1.setVisibility(View.GONE);
						rightTitle2.setVisibility(View.GONE);
						rightInsuranceList2.setVisibility(View.GONE);
					}
				}
				// 交强险
				else
				{
					buninessLayout.setVisibility(View.VISIBLE);
					policyDetailData = p.getPolicyDetailData();
					policy_policyNo.setText(p.getPolicyNo());
					String code = policyDetailData.getRiskCode();
					if (StringUtils.isEmpty(code))
					{
						code = "";
					}
					policy_insuredName.setText(policyDetailData.getInsuredName());
					policy_licenseNo.setText(policyDetailData.getLicenseNo());
					policy_brandName.setText(policyDetailData.getBrandName());
					policy_startDate.setText(policyDetailData.getStartDate());
					policy_endDate.setText(policyDetailData.getEndDate());

					List<Map<String, String>> data = new ArrayList<Map<String, String>>();
					List<PolicyKindData> policyKindDataList = p.getPolicyKindDataList();
					int psize = policyKindDataList.size();

					// 左列表
					List<Map<String, String>> leftList = new ArrayList<Map<String, String>>();
					// 右列表
					List<Map<String, String>> rightList = new ArrayList<Map<String, String>>();

					for (int i = 0; i < psize; i++)
					{
						Map<String, String> map = new HashMap<String, String>();
						PolicyKindData policyKindData = policyKindDataList.get(i);

						String remark = policyKindData.getRemark();
						map.put("kindName", policyKindData.getKindName());
						map.put("amount", policyKindData.getAmount());
						map.put("remark", remark + "/" + policyKindData.getAmount());

						if (i <= psize / 2)
						{
							leftList.add(map);
						}
						else
						{
							rightList.add(map);
						}
						// data.add(map);

					}

					// 左列表
					SimpleAdapter leftAdapter = new SimpleAdapter(this, leftList, R.layout.item_insurance_info_detail_item, new String[]
					{ "kindName", "remark" }, new int[]
					{ R.id.policy_kindName, R.id.policy_remark });
					leftInsuranceList.setAdapter(leftAdapter);
					new Utility().setListViewHeightBasedOnChildren(leftInsuranceList);

					// 右列表
					if (rightList.size() > 0)
					{
						SimpleAdapter rightAdapter = new SimpleAdapter(this, rightList, R.layout.item_insurance_info_detail_item, new String[]
						{ "kindName", "remark" }, new int[]
						{ R.id.policy_kindName, R.id.policy_remark });
						rightInsuranceList.setAdapter(rightAdapter);
						new Utility().setListViewHeightBasedOnChildren(rightInsuranceList);
					}
					else
					{
						rightTitle3.setVisibility(View.GONE);
						rightTitle4.setVisibility(View.GONE);
						rightInsuranceList.setVisibility(View.GONE);
					}
				}
			}
		}
	}

	// 获取保单信息
	// public void setPolicyData(){
	// // 获取控件
	// this.setPolicyViewController() ;
	// // 设置保单信息
	// this.getPolicyData() ;
	// }

	// 用于更新界面 跳转
	private Handler handler = new Handler()
	{
		// 0表示失败，1表示成功
		public void handleMessage(Message msg)
		{
			if (progressDialog != null)
			{
				progressDialog.dismiss();
			}

			switch (msg.what)
			{
			case 0:
				String errorMsg = msg.obj.toString();
				if (StringUtils.isNotEmpty(errorMsg))
				{
					if (errorMsg.contains("被处理"))
					{
						if (dialog != null)
						{
							dialog.dismiss();
						}
						dialog = CustomDialog.show(LossMenuAct.this, "信息提示", errorMsg, "确定", "", new OnClickListener()
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
									Intent it = new Intent();
									setResult(SAVE_SUCCESS, it);
									LossMenuAct.this.finish();
									ActivityManage.getInstance().pop();
								}
								catch (Exception e)
								{
									// e.printStackTrace();
								}
							}
						}, null);
					}
					else
					{
						CustomDialog.show(LossMenuAct.this, "信息提示", errorMsg);
					}
				}
				else
				{
					CustomDialog.show(LossMenuAct.this, "信息提示", "上传错误");
				}
				break;
			case 1: // 成功，更新界面

				List<ImageBean> imgBeans = CacheLossImage.getInstant().getImageBeans();
				DeflossData DeflossData = (com.sinosoft.ms.model.DeflossData) msg.obj;
				for (ImageBean b : imgBeans)
				{
					if (registNo.equals(b.getRegistNo()))
					{
						b.setUpload(false);
						CacheLossImage.getInstant().setImageBeans(imgBeans);
						break;
					}
				}

				try
				{

					// 设置保单信息
					getPolicyData();
					// setPolicyData() ; // 获取保单信息
					loadVehicleBasicData();// 2、定损车辆信息
					loadVehicleView(); // 更新定型页面
					// 4.更新定损基本信息
					setSmLossBasicInfo();
					// 3.更新定损车辆信息
					setSmLossVehicleInfo();
					// 8.更新银行信息
					initBankDatas();
					// 5.更新换件信息
					loadChangeItemData();
					// 6.更新维修信息
					loadRepairList();
					// 7.更新辅料信息
					loadAssistItemData();
					defLossDateBtn.setText(DeflossData.getDefLossDate());

				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}

				break;
			case 2:
				ToastDialog.show(LossMenuAct.this, msg.obj.toString(), ToastDialog.ERROR);
				break;
			case 3:
				// TODO
				break;
			case 10:
				ToastDialog.show(LossMenuAct.this, "数据上传" + msg.obj.toString(), ToastDialog.ERROR);
				break;
			case 11:
				if (dialog != null)
				{
					dialog.dismiss();
				}
				dialog = CustomDialog.show(LossMenuAct.this, "信息提示", "定损信息更新成功", "确定", "", new OnClickListener()
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
							// 数据归档
							// Intent it = new Intent();
							// it.setClass(SmLossMeunAct.this,
							// TaskCenterAct.class);
							// setResult(SAVE_SUCCESS, it);
							bluetoothPrint();

							LossMenuAct.this.finish();
							ActivityManage.getInstance().pop();
						}
						catch (Exception e)
						{
							// e.printStackTrace();
						}
					}
				}, null);
				break;
			case AppConstants.NO_LOGIN_CODE:
				Intent intent = new Intent();
				intent.setClass(LossMenuAct.this, LoginAct.class);
				startActivity(intent);
				try
				{
					LossMenuAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
				break;
			}
		};
	};

	public void onCheckedChanged(RadioGroup group, int checkedId)
	{
		currentPage = viewPager.getCurrentItem();
		int index = 0;
		switch (checkedId)
		{
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
		viewPager.setCurrentItem(index);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.backBtn:// 返回
			if (isFinish)
			{
				Intent it = new Intent();
				setResult(SAVE_SUCCESS, it);
			}

			try
			{
				finish();
				ActivityManage.getInstance().pop();
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
			break;
		case R.id.survey_picture_btn:
			Intent intent1 = new Intent();
			intent1.setClass(LossMenuAct.this, SurveyPictureExamineActivity.class);
			intent1.putExtra("registNo", registNo);
			startActivity(intent1);
			break;
		}
	}

	/**
	 * 提交更新定损接口
	 */
	public void update()
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("定损信息提交中...");
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{

					// if("1".equals(quickClaimFlag) && !isUploadDocuments){
					// // 上传单证快赔
					// CaseCenterService service = new CaseCenterService(null);
					// if(service.updateDocuments(registNo) == 1){
					// isUploadDocuments = true ;
					// }
					// }

					// 检查是否输入险别信息
					deflossData = deflossDataService.getByTaskId(taskId);

					LossService lossService = new LossService();
					int result = lossService.updataLossStatus(taskId, registId, LossMenuAct.this);
					if (1 == result)
					{

					}
					msg.what = 11;

				}
				catch (Exception e)
				{
					msg.what = MyUtils.getErrorCode(e.getMessage());
					if (StringUtils.isNotEmpty(e.getMessage()))
					{
						msg.obj = e.getMessage();
					}
					else
					{
						msg.obj = "服务器无返回错误信息";
						LogRecoder.wirteException(e);
					}
				}
				finally
				{
					handler.sendMessage(msg);
				}
			}
		}.start();
	}

	/**
	 * 执行定损信息更新
	 */
	private void executeLossUpdate()
	{
		try
		{
			if (!saveSmVehicleBasic())
			{
				ToastDialog.show(LossMenuAct.this, "" + errorMsg, ToastDialog.ERROR);
				onPageSelected(2);
				return;
			}
			if (!saveSmLossBasic() || !saveSmLossVehicle())
			{
				ToastDialog.show(LossMenuAct.this, "" + errorMsg, ToastDialog.ERROR);
				onPageSelected(3);
				return;
			}
			if (quickClaimFlag.equals("1"))
			{
				if (!saveSmBankInfo())
				{
					ToastDialog.show(LossMenuAct.this, "" + errorMsg, ToastDialog.ERROR);
					onPageSelected(7);
					return;
				}
			}
			else
			{
				BankInfoService service = new BankInfoService();
				service.delete(taskId);
			}

			/*
			 * if (Double.valueOf(sumLossFeeExt.getText().toString()) <= 5000) {
			 * // 总金额是否小于等于5000
			 * 
			 * } else { CustomDialog.show(LossMenuAct.this, "信息提示",
			 * "案件总金额必须小于等于5000元"); return; }
			 */
			String cetainLossType = SpinnerUtils.getValue(AppConstants.cetainLossType, dataMap.get("CertainLossType"));
			// 检查是否输入险别信息
			deflossData = deflossDataService.getByTaskId(taskId);
			// 如果不是一次性协议定损，险别需要判断
			if (StringUtils.isNotEmpty(cetainLossType) && !cetainLossType.equals("一次性协议定损"))
			{
				// 险别不为空时，直接更新
				if (StringUtils.isNotEmpty(deflossData.getInsureTermCode()))
				{
					update();
				}
				else
				{// 险别为空，跳转到修改界面
					if (dialog != null)
					{
						dialog.dismiss();
					}
					dialog = CustomDialog.show(this, "信息提示", "请选择车辆基本信息中险别信息", "确定", "", new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							if (dialog != null)
							{
								dialog.dismiss();
							}
							viewPager.setCurrentItem(2);
						}
					}, null);
				}
			}
			else
			{// 如果是一次性协议定损，险别可以不用判断
				update();
			}
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

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
				CustomDialog.show(LossMenuAct.this, "消息提示", msg.obj.toString());
				break;
			case 1:// 申请电脑任务 正确
				if (dialog != null)
				{
					dialog.dismiss();
				}
				dialog = CustomDialog.show(LossMenuAct.this, "信息提示", "申请处理成功", "确定", "", new OnClickListener()
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
							LossMenuAct.this.finish();
							ActivityManage.getInstance().pop();
						}
						catch (Exception e)
						{
							// e.printStackTrace();
						}
					}
				}, null);
				break;
			case AppConstants.NO_LOGIN_CODE:
				intent.setClass(LossMenuAct.this, LoginAct.class);
				startActivity(intent);
				try
				{
					LossMenuAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
			}
		};
	};

	public void changePC()
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("任务申请电脑处理");
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

	@Override
	protected void onPause()
	{
		super.onPause();
		if (progressDialog != null)
		{
			progressDialog.dismiss();
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		try
		{
			if (isFinish)
			{
				setResult(SAVE_SUCCESS, getIntent());
			}
			LossMenuAct.this.finish();
			ActivityManage.getInstance().pop();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	// true标识已定型，false表示未定型
	private boolean checkVehicleType()
	{
		boolean bool = false;
		try
		{
			if (null == basicVehicleService)
			{
				basicVehicleService = new BasicVehicleService();
			}
			if (null == basicVehicle || StringUtils.isEmpty(basicVehicle.getVehCertainCode()))
			{
				basicVehicle = basicVehicleService.getByRegistNo(taskId);
			}
			if (null != basicVehicle && StringUtils.isNotEmpty(basicVehicle.getVehCertainCode()))
			{
				vehicleType = basicVehicle.getVehCertainCode();
			}
			if (StringUtils.isNotEmpty(vehicleType) || // 调度时填写了车型，也需要重新定。--临时
					(StringUtils.isNotEmpty(cetainLossType) && cetainLossType.equals("一次性协议定损")))
			{
				bool = true;

			}
			else
			{
				bool = false;
			}
		}
		catch (Exception e)
		{

		}
		finally
		{
			return bool;
		}
	}

	/**
	 * 0000**********************************************************
	 * 
	 */
	private void setViewPager()
	{
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		views = new ArrayList<View>();
		getLayoutInflater();
		LayoutInflater inflater = LayoutInflater.from(this);
		smTakePhotoView = inflater.inflate(R.layout.layout_media_center, null); // 2、拍照
		vehicleTypeQueryView = inflater.inflate(R.layout.layout_loss_vehicle_type_sreach, null); // 3、定损车辆信息（未定型）
		vehicleBasicView = inflater.inflate(R.layout.layout_loss_vehicle_basic, null); // 3、定损车辆信息（已定型）
		vehicleBasicView.findViewById(R.id.layout_title).setVisibility(View.GONE);
		smLossBasicView = inflater.inflate(R.layout.layout_loss_basic, null); // 4、定损信息
		smChangeItemView = inflater.inflate(R.layout.layout_loss_change_project_list, null); // 5、定损换件信息
		smRepairItemView = inflater.inflate(R.layout.layout_loss_repair_item, null); // 6、定损维修信息
		smAssistItemView = inflater.inflate(R.layout.layout_loss_assist_query_list, null); // 7、辅料信息
		smBankInfoView = inflater.inflate(R.layout.layout_loss_bank_info, null); // 8、银行信息
		smTaskSubmitView = inflater.inflate(R.layout.layout_loss_submit, null); // 9、任务提交
		// 保单
		policyView = inflater.inflate(R.layout.layout_insurance_info_detail, null); // 1.保单详情
		// 保单信息
		views.add(policyView); // 1

		views.add(smTakePhotoView); // 2
		if (checkVehicleType())
		{ // 已定型 //3
			views.add(vehicleBasicView);
		}
		else
		{ // 未定型
			views.add(vehicleTypeQueryView);
		}
		views.add(smLossBasicView); // 4
		views.add(smChangeItemView); // 5
		views.add(smRepairItemView); // 6
		views.add(smAssistItemView); // 7
		views.add(smBankInfoView); // 8
		views.add(smTaskSubmitView); // 9
		surveyPagerAdapter = new SurveyPagerAdapter(this, views);
		viewPager.setAdapter(surveyPagerAdapter);
		viewPager.setOnPageChangeListener(this);
		// viewPager.setCurrentItem(currentPage);
		radioGroup = (RadioGroup) findViewById(R.id.main_radio);
		radioGroup.setOnCheckedChangeListener(this);

		setChildViews();
	}

	private void setViewPagerInfo()
	{
		if (checkVehicleType())
		{ // 已定型//3
			views.set(2, vehicleBasicView);
		}
		else
		{
			// 未定型
			views.set(2, vehicleTypeQueryView);
		}
		surveyPagerAdapter.refreshViews(views);
		viewPager.setOnPageChangeListener(null);
		viewPager.setAdapter(surveyPagerAdapter);
		viewPager.setCurrentItem(currentPage);
		viewPager.setOnPageChangeListener(this);

	}

	private void setChildViews()
	{
		// 设置保单信息
		// gggggg
		setPolicyView();
		setSmTakePhoto(); // 1
		setVehicleBasic(); // 2
		setVehicleTypeQuery();
		setSmLossBasic(); // 3
		setSmLossVehicle();
		setSmChangeItem(); // 4
		setSmRepairItem(); // 5
		setSmAssistItem(); // 6
		setBankInfo(); // 7
		setTaskSubmit(); // 8
	}

	/**
	 * 22***********************************************************************
	 * ************************** 定损拍照
	 */
	private boolean canEditFlag = true;
	private GridView mediaList;
	// private Map<String, Map<String, String>> dataMap = null;

	private List<String> typeList;
	private MediaSimpleAdapter mediaSimpleAdapter;
	private Button mediaTakePictureBtn;

	// private String registNo = "";
	// private RegistData taskBean;
	private String imageType;
	private List<String> listBitmap;
	private ImageGallery imageGallery;
	private RelativeLayout imageBottom;
	private Dialog imageDialog;

	private Button mDirectUploadBtn, mUploadImgBtn, mediaDeleteBtn;
	private ImageButton leftArrowBtn, rightArrowBtn;

	private List<ImageCenterBean> imageDatas;

	private int pic_index;// 选择了第几张图片

	private List<Map<String, String>> imageData;
	private ImageGalleryAdapter imageAdapter;

	private Dialog clearDialog;
	private ImageCenterDatabase database = null;

	private void setSmTakePhoto()
	{
		mediaList = (GridView) smTakePhotoView.findViewById(R.id.mediaList);
		mediaTakePictureBtn = (Button) smTakePhotoView.findViewById(R.id.mediaTakePictureBtn);
		imageGallery = (ImageGallery) smTakePhotoView.findViewById(R.id.mediaImageGallery);
		imageBottom = (RelativeLayout) smTakePhotoView.findViewById(R.id.media_gallery_bottom);

		mUploadImgBtn = (Button) smTakePhotoView.findViewById(R.id.mUploadImgBtn);
		mediaDeleteBtn = (Button) smTakePhotoView.findViewById(R.id.mediaDeleteBtn);
		mDirectUploadBtn = (Button) smTakePhotoView.findViewById(R.id.directUploadBtn);
		leftArrowBtn = (ImageButton) smTakePhotoView.findViewById(R.id.leftArrowBtn);
		rightArrowBtn = (ImageButton) smTakePhotoView.findViewById(R.id.rightArrowBtn);

		ImageCenterClickListener listener = new ImageCenterClickListener();
		mUploadImgBtn.setOnClickListener(listener);
		mediaDeleteBtn.setOnClickListener(listener);
		mDirectUploadBtn.setOnClickListener(listener);
		leftArrowBtn.setOnClickListener(listener);
		rightArrowBtn.setOnClickListener(listener);

		setImageData();
	}

	private void setImageData()
	{
		database = new ImageCenterDatabase(LossMenuAct.this);
		// 案件信息
		ImageCenterClickListener listener = new ImageCenterClickListener();
		mDirectUploadBtn.setOnClickListener(listener);

		loadImageType(null != deflossData ? deflossData.getQuickClaimFlag() : "0");

		MediaSimpleAdapter.selectItem = -1;
		mediaList.setOnItemClickListener(new ImageCenterItemClickListener());
		mediaTakePictureBtn.setOnClickListener(listener);
		imageData = new ArrayList<Map<String, String>>();
		imageAdapter = new ImageGalleryAdapter(this, imageData, R.layout.item_media_img, new String[]
		{ "image" }, new int[]
		{ R.id.mediaImage });

		// 图片预览
		imageGallery.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id)
			{
				String image = listBitmap.get(position);
				PictureGalleryActivity.startForPictureGallery(getApplicationContext(), listBitmap, position);
				// Bitmap bitmap = BitmapFactory.decodeFile(image);
				// ImageView imageView = new ImageView(SmLossMeunAct.this);
				// imageView.setImageBitmap(bitmap);// 显示图片
				// if(null == imageDialog || !imageDialog.isShowing())
				// imageDialog = new AlertDialog.Builder(SmLossMeunAct.this)
				// .setTitle("图片预览").setView(imageView)
				// .setPositiveButton("关闭", null).show();

			}
		});

		imageGallery.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				scrollToImage(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
			}
		});
		imageGallery.setAdapter(imageAdapter);
		listImage();
	}

	/**
	 * 加载图片类型选项
	 * 
	 * @param flag
	 *            0.否 1.是快赔
	 */
	public void loadImageType(String flag)
	{
		Map<String, String> listData = dataMap.get("DocData");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		typeList = new ArrayList<String>();

		if (listData != null)
		{
			Iterator<String> iterator = listData.keySet().iterator();
			while (iterator.hasNext())
			{
				String code = iterator.next();
				String values = listData.get(code);
				typeList.add(code);
				Map<String, String> map = new HashMap<String, String>();
				map.put("type", values);
				list.add(map);
			}
		}

		// 0001 索赔申请书
		// 0002 驾驶证、行驶证、体检回执
		// 0004 责任认定书、协议书
		// 0018 标的车修理发票
		// 3008 活期存折/银行卡
		if ("1".equals(flag))
		{
			Map<String, String> map = new HashMap<String, String>();
			typeList.add("0001");
			typeList.add("0002");
			typeList.add("0004");
			typeList.add("0018");
			typeList.add("3008");
			map = new HashMap<String, String>();
			map.put("type", "索赔申请书");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("type", "驾驶证、行驶证、体检回执");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("type", "责任认定书、协议书");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("type", "标的车修理发票");
			list.add(map);
			map = new HashMap<String, String>();
			map.put("type", "活期存折/银行卡");
			list.add(map);
		}
		mediaSimpleAdapter = new MediaSimpleAdapter(this, list, R.layout.item_media_type, new String[]
		{ "type" }, new int[]
		{ R.id.media_item_type }, new int[]
		{}, null);
		mediaList.setAdapter(mediaSimpleAdapter);
	}

	private void scrollToImage(int position)
	{
		LinearLayout linear = (LinearLayout) imageBottom.getChildAt(0);
		pic_index = position;
		for (int i = 0; i < linear.getChildCount(); i++)
		{
			ImageView v = (ImageView) linear.getChildAt(i);
			if (position == i)
			{
				v.setImageResource(R.drawable.greenicon);
			}
			else
			{
				v.setImageResource(R.drawable.grayicon);
			}
		}
	}

	public void listImage()
	{
		listBitmap = new ArrayList<String>();
		try
		{
			imageDatas = database.select("imageCenter", "reportNo=? and type=?", new String[]
			{ taskId, imageType }, "createDate");
			if (imageDatas == null || imageDatas.isEmpty())
			{
				// throw new IllegalArgumentException("没有可上传的图片");
			}
			ImageCenterBean bean = null;
			for (int i = 0; i < imageDatas.size(); i++)
			{
				bean = imageDatas.get(i);
				listBitmap.add(bean.getPath());
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		loadImage();
	}

	public void loadImage()
	{
		// ImageAdapter adapter = new ImageAdapter(this);
		pic_index = 0;
		imageData.clear();
		int size = listBitmap.size();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++)
		{
			map.put("image", listBitmap.get(i));
			imageData.add(map);
		}

		LinearLayout linear = new LinearLayout(this);

		for (int i = 0; i < imageAdapter.getCount(); i++)
		{
			ImageView img = new ImageView(this);
			LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			if (i == 0)
			{
				img.setImageResource(R.drawable.greenicon);
			}
			else
			{
				img.setImageResource(R.drawable.grayicon);
			}
			linear.addView(img, params);
		}
		imageBottom.removeAllViews();
		imageBottom.addView(linear);
		imageAdapter.notifyDataSetChanged();
		imageAdapter.notifyDataSetChanged();
		imageGallery.setAdapter(imageAdapter);

	}

	public void getImages()
	{
		listBitmap = new ArrayList<String>();
		try
		{
			imageDatas = database.select("imageCenter", "reportNo=? and type=?", new String[]
			{ taskId, imageType }, "createDate");
			if (imageDatas == null || imageDatas.isEmpty())
			{
				// throw new IllegalArgumentException("没有可上传的图片");
			}
			ImageCenterBean bean = null;
			for (int i = 0; i < imageDatas.size(); i++)
			{
				bean = imageDatas.get(i);
				listBitmap.add(bean.getPath());
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	class ImageCenterItemClickListener implements OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
		{
			progressDialog = new ProgressDialogUtil(LossMenuAct.this);
			progressDialog.show("加载中...");
			new Thread()
			{

				@Override
				public void run()
				{
					super.run();
					getImages();
					MediaSimpleAdapter.selectItem = position;
					mediaHandler.sendEmptyMessage(0);
					if (position < mediaSimpleAdapter.getCount())
					{
						imageType = typeList.get(position);
					}
					else
					{
						imageType = "9999";
					}
					mediaHandler.sendEmptyMessage(1);
				}

			}.start();

		}
	}

	Handler mediaHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 0:
				mediaSimpleAdapter.notifyDataSetChanged();
				break;
			case 1:
				listImage();
				progressDialog.dismiss();
				break;
			case 2:
				progressDialog.dismiss();
				loadImage();
				imageAdapter.notifyDataSetChanged();
				break;
			}
		}

	};

	// 影像中心按钮监听
	class ImageCenterClickListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			switch (arg0.getId())
			{
			case R.id.leftArrowBtn:
				try
				{
					pic_index--;
					if (pic_index >= 0)
						imageGallery.setSelection(pic_index);
					else
					{
						pic_index++;
					}
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				break;
			case R.id.rightArrowBtn:
				try
				{
					pic_index++;
					if (pic_index < imageDatas.size())
						imageGallery.setSelection(pic_index);
					else
					{
						pic_index--;
					}
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				break;
			// 影响拍照
			case R.id.mediaTakePictureBtn:
				if (canEditFlag)
				{
					if (MediaSimpleAdapter.selectItem >= 0)
					{
						if (MediaSimpleAdapter.selectItem < mediaSimpleAdapter.getCount())
						{
							Intent intent2 = new Intent();
							intent2.setClass(LossMenuAct.this, CameraActivity.class);
							intent2.putExtra("imageType", imageType);
							intent2.putExtra("num", 0);
							intent2.putExtra("total", 12);
							intent2.putExtra("reportNo", taskId);
							intent2.putExtra("taskId", taskId);
							intent2.putExtra("registId", registId);
							startActivityForResult(intent2, 1231);
						}
					}
					else
					{
						ToastDialog.show(LossMenuAct.this, "请先选择拍照类型", ToastDialog.ERROR);
					}
				}
				else
				{
					CustomDialog.show(LossMenuAct.this, "信息提示", "该案件已经归档，不能实现拍照操作");
				}

				break;
			// 本地上传
			case R.id.mUploadImgBtn:
				if (null == imageType || imageType.equals(""))
				{
					CustomDialog.show(LossMenuAct.this, "信息提示", "请先选中照片类型");
				}
				else
				{
					if (canEditFlag)
					{
						Intent intent = new Intent(LossMenuAct.this, UploadImageActivity.class);
						intent.putExtra("registNo", taskId);
						intent.putExtra("taskId", taskId);
						intent.putExtra("registId", registId);
						intent.putExtra("imageType", imageType);
						startActivityForResult(intent, 1232);
						// startActivity(intent);
					}
					else
					{
						CustomDialog.show(LossMenuAct.this, "信息提示", "该案件已经归档，不能实现上传操作");
					}
				}
				break;
			case R.id.mediaDeleteBtn:
				if (canEditFlag)
				{
					deleteImage();
				}
				else
				{
					CustomDialog.show(LossMenuAct.this, "信息提示", "该案件已经归档，不能实现删除操作");
				}
				break;
			// 影响资料上传
			case R.id.directUploadBtn:
				progressDialog = new ProgressDialogUtil(LossMenuAct.this);
				progressDialog.show("加载中...");
				new AsyncTask<Void, Void, List<ImageCenterBean>>()
				{

					@Override
					protected List<ImageCenterBean> doInBackground(Void... arg0)
					{
						ImageCenterDatabase database = new ImageCenterDatabase(LossMenuAct.this);
						return database.selectNotUpload(taskId);
					}

					@Override
					protected void onPostExecute(List<ImageCenterBean> list)
					{
						super.onPostExecute(list);
						progressDialog.dismiss();
						if (null == list || list.isEmpty())
						{
							ToastDialog.show(LossMenuAct.this, "当前没有图片或没有需要上传的图片");
							return;
						}
						if (canEditFlag)
						{
							try
							{
								clearDialog = CustomDialog.show(LossMenuAct.this, "信息提示", "是否上传所有图片?", new OnClickListener()
								{
									@Override
									public void onClick(View v)
									{
										clearDialog.dismiss();

										List<ImageBean> imglist = CacheLossImage.getInstant().getImageBeans();
										if (imglist.isEmpty())
										{
											ImageBean entry = new ImageBean();
											entry.setRegistNo(registNo);
											entry.setUpload(true);
											imglist.add(entry);
											CacheLossImage.getInstant().setImageBeans(imglist);
										}
										else
										{
											boolean isExist = false;
											for (ImageBean img : imglist)
											{
												if (registNo.equals(img.getRegistNo()))
												{
													img.setRegistNo(registNo);
													img.setUpload(true);
													CacheLossImage.getInstant().setImageBeans(imglist);
													break;

												}
											}
											if (!isExist)
											{
												ImageBean entry = new ImageBean();
												entry.setRegistNo(registNo);
												entry.setUpload(true);
												imglist.add(entry);
												CacheImage.getInstant().setImageBeans(imglist);
											}
										}
										// isTakedPhoto = true ;
										Intent intent = new Intent(LossMenuAct.this, ImageUploadService.class);
										intent.putExtra("registNo", taskId);
										startService(intent);
									}
								}, new OnClickListener()
								{
									@Override
									public void onClick(View v)
									{
										clearDialog.dismiss();
									}
								});
							}
							catch (Exception e)
							{
								// e.printStackTrace();
								ToastDialog.show(LossMenuAct.this, "图片上传错：" + e.getMessage(), ToastDialog.ERROR);
							}
						}
						else
						{
							CustomDialog.show(LossMenuAct.this, "信息提示", "该案件已经归档，不能实现上传操作");
						}
					}

				}.execute();

				// }
				break;
			}

		}

	}

	/**
	 * 图片删除事件
	 */
	public void deleteImage()
	{
		if (imageDatas != null && !imageDatas.isEmpty())
		{
			progressDialog = new ProgressDialogUtil(this);
			progressDialog.show("操作中...");
			new Thread()
			{

				@Override
				public void run()
				{
					super.run();
					try
					{
						ImageCenterBean image = imageDatas.get(pic_index);
						File file = new File(image.getPath());

						if (image.getPath().contains("finger"))
						{
							file.delete();
						}
						// if (file.delete()) {
						ImageCenterDatabase database = new ImageCenterDatabase(LossMenuAct.this);
						database.delete("imageCenter", "reportNo=? and type=? and id=?", new String[]
						{ taskId, image.getType(), image.getId() });
						listBitmap.remove(pic_index);
						imageDatas.remove(pic_index);
						mediaHandler.sendEmptyMessage(2);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

			}.start();
		}
		else
		{
			CustomDialog.show(LossMenuAct.this, "信息提示", "没有可以删除的图片");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1231 || requestCode == 1232)
		{
			// isTakedPhoto = false ;
			listImage();
		}
	}

	class ImageGalleryAdapter extends SimpleAdapter
	{
		private Context context;
		private int resource;

		public ImageGalleryAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
		{
			super(context, data, resource, from, to);
			this.context = context;
			this.resource = resource;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View view;
			if (convertView == null)
			{
				view = View.inflate(context, resource, null);
				ImageView imageView = (ImageView) view.findViewById(R.id.mediaImage);
				imageView.setBackgroundColor(0x00bbaa);
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeFile(listBitmap.get(position), options);
				imageView.setImageBitmap(bitmap);
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
				convertView = imageView;
			}
			return convertView;
		}
	}

	/**
	 * 44***********************************************************************
	 * ************************ 定损基本信息
	 */
	private String[] lossIdeas = new String[]
	{ "特约店定损", "普通修理厂定损", "已核对所有项目", "证件齐全", "上报审核" };
	private boolean[] lossIdeasSelected = new boolean[]
	{ false, false, false };
	// private IDeflossDataService deflossDataService = null;
	private InputMethodManager imm = null;

	// private DeflossData deflossData = null;
	private TextView registNoTxt = null;// 报案号
	private EditText defLossDateBtn = null;// 定损日期
	private TextView cetainLossTypeTv = null;// 定损方式
	private CheckBox addCommonFactoryCheckBox = null; // 加入常用修理厂
	private Button commonFactoryButton = null; // 常用修理厂
	private Spinner repairFactoryTypeSp = null;// 修理厂类型
	// private Spinner repairFactoryCodeSp = null;// 修理厂名称
	private EditText repairFactoryCodeSp = null;// 修理厂名称
	private EditText defSiteExt = null;// 定损地点
	private EditText lossOptionExt = null;// 定损意见
	private Button historyOpinionBtn = null;// 历史意见按钮
	private Button selectOptionBtn = null;

	// private String registNo = null;
	// private String taskId = null;
	private String defLossDate = null;
	private String cetainLossType = null;
	private String repairFactoryType = null;
	private String repairFactoryCode = null;
	private String repairFactoryName = null;
	private String defSite = null;
	private String option = null;

	/** 封装日期控件 */
	private DateDialogUtils dateDialogUtils = null;
	/** 声明按钮lock **/
	public static boolean lock = false;
	private int y, m, d = 0;
	private String errorMsg = null;
	private String initTime = null;
	// private Map<String, Map<String, String>> dataMap0;

	private String lossPlace; // 出险地点

	private String feedbacks[]; // 定损意见数据

	private String totalFeedbacks[] = null; // 所有定损意见
	private boolean totalFeedbacksChecked[] = null; // 所有是否选中定损意见

	// private Dialog saveDialog ; // 查勘意见提示框
	private Button localFeedbackBtn;// 自定义定损意见
	/**
	 * 2013.8.16 新增快速理赔标志
	 */
	private RadioGroup quickClaimRadioGroup;
	private String quickClaimFlag = "0";

	private EditText mAddFeedbackEdit;
	private Button mConfirmBtn, mCancelBtn;
	private Dialog addFeedbackDialog;
	private ListView mSelfDefineListView;
	private FeedbackAdapter feedAdapter;
	// 修理厂快速检索
	// private Button repairSearchBtn;
	// 修理厂名称适配器
	private AutoCompleteArrayAdapter repairNamesAdapter = null;
	// 是否从服务器获取的数据库列表
	private boolean isNetworkRepairFactory;
	// 修理厂名称代码
	private String[] repairNamesforKey = null;
	// 修理厂名称名称
	private String[] repairNamesforValue = null;
	// 修理厂快速检索弹出框
	// private Dialog repairNamesDialog;
	// 修理厂服务类
	private IRepairFactoryService repairFactoryService;

	// 获取修理厂名称数组
	private void getReparNames(Map<String, String> map)
	{
		if (map == null || map.isEmpty())
		{
			this.repairNamesforKey = new String[0];
			this.repairNamesforValue = new String[0];
			return;
		}
		this.repairNamesforKey = new String[map.size()];
		this.repairNamesforValue = new String[map.size()];
		// 修理厂名称-代码
		Set<String> repairNamesKey = map.keySet();
		int keyIndex = 0;
		for (Object obj : repairNamesKey.toArray())
		{
			this.repairNamesforKey[keyIndex++] = obj.toString();
		}
		// 修理厂名称-名称
		Collection<String> repairNamesValue = map.values();
		int valueIndex = 0;
		for (Object obj : repairNamesValue.toArray())
		{
			this.repairNamesforValue[valueIndex++] = obj.toString();
		}

	}

	/*
	 * // 快速检索修理厂名称 private Dialog showRepartNamesDialog(Context context, String
	 * keyword) { Dialog dialog = new Dialog(context,
	 * R.style.Theme_ShareDialog);
	 * dialog.setContentView(R.layout.dialog_keyword_search); final EditText
	 * editText = (EditText) dialog .findViewById(R.id.sreachWord); // 搜索按钮
	 * Button button = (Button) dialog.findViewById(R.id.sreachBtn); final
	 * ListView listView = (ListView) dialog .findViewById(R.id.searchList);
	 * 
	 * // 获取修理厂名称代码，名称 this.getReparNames(dataMap.get("RepairFactory")); //
	 * 设置适配器 repairNamesAdapter = new AutoCompleteArrayAdapter(LossMenuAct.this,
	 * android.R.layout.simple_list_item_1, this.repairNamesforValue);
	 * listView.setAdapter(repairNamesAdapter);
	 * editText.addTextChangedListener(new TextWatcher() {
	 * 
	 * @Override public void afterTextChanged(Editable arg0) {
	 * 
	 * }
	 * 
	 * @Override public void beforeTextChanged(CharSequence arg0, int arg1, int
	 * arg2, int arg3) {
	 * 
	 * }
	 * 
	 * @Override public void onTextChanged(CharSequence arg0, int arg1, int
	 * arg2, int arg3) { String keyword = editText.getText().toString(); if
	 * (StringUtils.isEmpty(keyword)) { isNetworkRepairFactory = false;
	 * repairNamesAdapter = new AutoCompleteArrayAdapter( LossMenuAct.this,
	 * android.R.layout.simple_list_item_1, repairNamesforValue);
	 * listView.setAdapter(repairNamesAdapter); }
	 * repairNamesAdapter.getFilter().filter(keyword); }
	 * 
	 * }); button.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View arg0) {
	 * searchRepairFactory(editText.getText().toString(), new
	 * RepairFactoryCallback() {
	 * 
	 * @Override public void onSuccess(List<Item> result) {
	 * repairNamesAdapter.setData(result); }
	 * 
	 * @Override public void onFailure() {
	 * Toast.makeText(getApplicationContext(), "无搜索结果",
	 * Toast.LENGTH_SHORT).show(); } }); } });
	 * 
	 * listView.setOnItemClickListener(new OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
	 * position, long arg3) { try { // 如果是从网络获取 if (isNetworkRepairFactory) {
	 * Item item = (Item) repairNamesAdapter.getItem(position);
	 * 
	 * String key = SpinnerUtils.getKey(item.getValue(),
	 * dataMap.get("RepairFactory")); // 根据代码获取本地的修理厂名称 String localValue =
	 * dataMap.get("RepairFactory").get( item.getCode()); if
	 * ((!StringUtils.isEmpty(key) && key.equals(item .getCode())) ||
	 * !StringUtils.isEmpty(localValue)) { //
	 * 先检查在本地中是否存在相同的数据,或者代码一样，只是修理厂名称被修改了 // 如果存在相同的数据，则更新本地数据库
	 * DictCashe.getInstant().updateRepairFactory( item.getCode(),
	 * item.getValue()); DictDatabase db = new DictDatabase();
	 * db.updateCommonFactory(getApplicationContext(), item.getCode(),
	 * item.getValue()); SpinnerUtils.setSpinnerData(LossMenuAct.this,
	 * repairFactoryCodeSp, dataMap.get("RepairFactory"), item.getCode()); }
	 * else { // 如果本地不存在该数据，则只设置当前修理厂 Map<String, String> map = new
	 * HashMap<String, String>(); map.put(item.getCode(), item.getValue());
	 * SpinnerUtils.setSpinnerData( getApplicationContext(),
	 * repairFactoryCodeSp, map, item.getCode()); } repairFactoryCode =
	 * item.getCode(); repairFactoryName = item.getValue(); } else { //
	 * 如果是从本地数据中获取 String key = SpinnerUtils.getKey( (String)
	 * repairNamesAdapter.getItem(position), dataMap.get("RepairFactory"));
	 * SpinnerUtils.setSpinnerData(LossMenuAct.this, repairFactoryCodeSp,
	 * dataMap.get("RepairFactory"), key); repairFactoryCode = key;
	 * repairFactoryName = dataMap.get("RepairFactory").get( key); }
	 * repairNamesDialog.dismiss(); } catch (Exception e) { } }
	 * 
	 * }); if (StringUtils.isNotEmpty(keyword)) { editText.setText(keyword); }
	 * return dialog; }
	 */
	/**
	 * 从服务器获取修理厂列表
	 * 
	 * @param keyword
	 */
	private void searchRepairFactory(final String keyword, final RepairFactoryCallback callback)
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("正在加载...");
		new AsyncTask<Void, Void, List<Item>>()
		{

			@Override
			protected List<Item> doInBackground(Void... arg0)
			{
				isNetworkRepairFactory = true;
				RepairFactoryService service = new RepairFactoryService();
				try
				{
					return service.getRepairFactory(keyword, getApplicationContext());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(List<Item> result)
			{
				super.onPostExecute(result);
				progressDialog.dismiss();
				if (null != result && !result.isEmpty())
				{
					callback.onSuccess(result);
					;
				}
				else
				{
					callback.onFailure();
				}
			}

		}.execute();
	}

	interface RepairFactoryCallback
	{
		public void onSuccess(List<Item> result);

		public void onFailure();
	}

	// 设置试图
	private void setSmLossBasic()
	{
		/*
		 * // 修理厂名称快速检索 repairSearchBtn = (Button) smLossBasicView
		 * .findViewById(R.id.repairSearchBtn);
		 * repairSearchBtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { isNetworkRepairFactory =
		 * false; repairNamesDialog = showRepartNamesDialog(LossMenuAct.this,
		 * ""); repairNamesDialog.show(); // repairNamesDialog = new //
		 * AlertDialog
		 * .Builder(SmLossMeunAct.this).setView(showRepartNamesDialog()).show()
		 * // ; } });
		 */
		localFeedbackBtn = (Button) smLossBasicView.findViewById(R.id.localFeedbackBtn);
		final LossBasicListener listener = new LossBasicListener();
		localFeedbackBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (saveDialog != null)
				{
					saveDialog.dismiss();
				}
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.layout_add_feedback_dialog, (ViewGroup) findViewById(R.id.addfeedbackdialog));
				mAddFeedbackEdit = (EditText) layout.findViewById(R.id.inputTexgt);
				mConfirmBtn = (Button) layout.findViewById(R.id.sure);
				mCancelBtn = (Button) layout.findViewById(R.id.cancel);
				mConfirmBtn.setOnClickListener(listener);
				mCancelBtn.setOnClickListener(listener);
				mSelfDefineListView = (ListView) layout.findViewById(R.id.mydefinelist);
				if (null != feedbacks && feedbacks.length > 0)
				{
					feedAdapter = new FeedbackAdapter();
					mSelfDefineListView.setAdapter(feedAdapter);
				}
				if (null != feedAdapter)
				{
					feedAdapter.notifyDataSetChanged();
				}
				addFeedbackDialog = CustomDialog.showTip(LossMenuAct.this, "自定义意见", layout);
			}
		});
		registNoTxt = (TextView) smLossBasicView.findViewById(R.id.registNoTxt);
		repairFactoryTypeSp = (Spinner) smLossBasicView.findViewById(R.id.repairFactoryTypeSp);
		// repairFactoryCodeSp = (Spinner) smLossBasicView
		// .findViewById(R.id.repairFactoryCodeSp);
		defLossDateBtn = (EditText) smLossBasicView.findViewById(R.id.defLossDateBtn1);
		defSiteExt = (EditText) smLossBasicView.findViewById(R.id.defSiteExt);
		repairFactoryCodeSp = (EditText) smLossBasicView.findViewById(R.id.repairFactoryCodeSp);
		cetainLossTypeTv = (TextView) smLossBasicView.findViewById(R.id.cetainLossTypeTv);
		lossOptionExt = (EditText) smLossBasicView.findViewById(R.id.lossOptionExt);// 定损意见
		selectOptionBtn = (Button) smLossBasicView.findViewById(R.id.selectOptionBtn);
		// 历史意见
		historyOpinionBtn = (Button) smLossBasicView.findViewById(R.id.historyOpinionBtn);
		initTime = MyUtils.format("yyyy-MM-dd", new Date());

		/**
		 * 2013.8.16 新增快速理赔
		 */
		quickClaimRadioGroup = (RadioGroup) smBankInfoView.findViewById(R.id.quickClaimRadioGroup);
		quickClaimRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1)
			{
				switch (arg1)
				{
				case R.id.radio0:
					quickClaimFlag = "0";
					bankInfoLayout.setVisibility(View.GONE);
					break;
				case R.id.radio1:
					dialog = CustomDialog.show(LossMenuAct.this, "系统提示", "暂不支持该功能", new OnClickListener()
					{

						@Override
						public void onClick(View arg0)
						{
							// TODO Auto-generated method stub
							dialog.dismiss();

							// Message msg=new Message();
							// msg.what=1;
							isquick.sendEmptyMessage(1);
							// noRTBtn.setChecked(true);
							// isQuick.setChecked(true);
							// quickClaimRadioGroup.set
						}
					});
					quickClaimFlag = "1";
					bankInfoLayout.setVisibility(View.VISIBLE);
					break;
				}
				// 重新加载图片类型
				loadImageType(quickClaimFlag);
			}
		});

		try
		{
			setSmLossBasicInfo();

		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}

		defLossDateBtn.setOnClickListener(listener);
		selectOptionBtn.setOnClickListener(listener);
		historyOpinionBtn.setOnClickListener(listener);
		/**
		 * 2013.8.30 新增常用修理厂
		 */
		/*
		 * addCommonFactoryCheckBox = (CheckBox) smLossBasicView
		 * .findViewById(R.id.addCommonFactoryCheckBox);
		 * addCommonFactoryCheckBox .setOnCheckedChangeListener(new
		 * CommonFactoryCheckedChangedListener()); commonFactoryButton =
		 * (Button) smLossBasicView .findViewById(R.id.commonFactoryButton);
		 * commonFactoryButton.setOnClickListener(listener);
		 */
		setLossFeedbacks();
	}

	public final class ViewHolder
	{
		public TextView feedback;
		public Button deleteBtn;
	}

	class FeedbackAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			return feedbacks.length;
		}

		@Override
		public Object getItem(int arg0)
		{
			return feedbacks[arg0];
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = LayoutInflater.from(LossMenuAct.this).inflate(R.layout.item_feedback, null);
				holder.feedback = (TextView) convertView.findViewById(R.id.feedback_text);
				holder.deleteBtn = (Button) convertView.findViewById(R.id.delete_btn);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.feedback.setText(feedbacks[position]);
			holder.deleteBtn.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					try
					{
						deleteFeedback(position);
						getLossFile();
						updateLossFeedbacks();
						if (addFeedbackDialog.isShowing())
						{
							addFeedbackDialog.dismiss();
						}
					}
					catch (Exception e)
					{
						// e.printStackTrace();
					}
					Toast.makeText(LossMenuAct.this, "删除成功", Toast.LENGTH_LONG).show();
				}
			});
			return convertView;
		}

	}

	/**
	 * 加入到常用修理厂
	 */
	/*
	 * class CommonFactoryCheckedChangedListener implements
	 * android.widget.CompoundButton.OnCheckedChangeListener {
	 * 
	 * @Override public void onCheckedChanged(CompoundButton arg0, boolean arg1)
	 * { switch (arg0.getId()) { case R.id.addCommonFactoryCheckBox: if
	 * (!StringUtils.isEmpty(repairFactoryCode) &&
	 * !StringUtils.isEmpty(repairFactoryName)) { if (arg1 == true) { //
	 * 加入到常用修理厂 try { insertToCommonFactory(repairFactoryCode,
	 * repairFactoryName); } catch (Exception e) { // e.printStackTrace(); } }
	 * else { // 删除常用修理厂 CommonFactoryDatabase database = new
	 * CommonFactoryDatabase( LossMenuAct.this); CommonFactory bean = new
	 * CommonFactory(); try { database.deleteBasicInfo(repairFactoryCode); }
	 * catch (Exception e) { // e.printStackTrace(); } } } break;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * }
	 */
	/**
	 * 添加到常用修理厂
	 * 
	 * @param repairFactoryCode
	 * @param repairFactoryName
	 */
	/*
	 * private void insertToCommonFactory(String repairFactoryCode, String
	 * repairFactoryName) { try { CommonFactoryDatabase database = new
	 * CommonFactoryDatabase( LossMenuAct.this); CommonFactory bean = new
	 * CommonFactory(); bean.setRepairFactoryCode(repairFactoryCode);
	 * bean.setRepairFactoryName(repairFactoryName); // 如果本地有缓存，则插入，如果没有，则更新 if
	 * (null == database.getCommonFactory(repairFactoryCode)) {
	 * database.insertCommonFactory(bean); } else {
	 * database.updateCommonFactory(bean); } } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */
	private void setSmLossBasicInfo() throws Exception
	{
		if (null == deflossDataService)
		{
			deflossDataService = new DeflossDataService();
		}
		if (null == deflossData)
		{
			deflossData = deflossDataService.getByTaskId(taskId);
		}
		deflossData.init();
		lossOptionExt.setText(deflossData.getLocalOption());
		registNoTxt.setText(deflossData.getRegistNo());
		/** 设置出险地点默认值 **/
		if (deflossData.getDefSite() != null && !"".equals(deflossData.getDefSite()))
		{
			defSiteExt.setText(deflossData.getDefSite());
		}
		else
		{
			defSiteExt.setText(this.lossPlace);
		}
		if (StringUtils.isNotEmpty(deflossData.getDefLossDate()))
		{
			defLossDateBtn.setText(deflossData.getDefLossDate());
		}
		else
		{
			defLossDateBtn.setText(initTime);
		}
		// dataMap = DictCashe.getInstant().getDict();
		this.cetainLossTypeTv.setText("修复定损");

		SpinnerUtils.setSpinnerData(this, repairFactoryTypeSp, dataMap.get("RepairFactoryType"), deflossData.getRepairFactoryType(), "");
		/*
		 * try{ // 初始化修理厂信息
		 * setRepairNameSpinner(deflossData.getRepairFactoryCode(),
		 * deflossData.getRepairFactoryName()); }catch(Exception e){
		 * e.printStackTrace(); }
		 */
		/**
		 * 2013.8.30 新增
		 */
		RepairFactorySelectedListener listener = new RepairFactorySelectedListener();
		// repairFactoryCodeSp.setOnItemSelectedListener(listener);
		repairFactoryTypeSp.setOnItemSelectedListener(listener);

		RadioButton radio;
		if (bankInfoLayout == null)
			bankInfoLayout = (LinearLayout) smBankInfoView.findViewById(R.id.bankInfoLayout);
		try
		{
			// 如果是快速理赔
			if (deflossData.getQuickClaimFlag().equals("1"))
			{
				radio = (RadioButton) quickClaimRadioGroup.getChildAt(1);
				quickClaimFlag = "1";
				bankInfoLayout.setVisibility(View.VISIBLE);
			}
			else
			{
				radio = (RadioButton) quickClaimRadioGroup.getChildAt(0);
				quickClaimFlag = "0";
				bankInfoLayout.setVisibility(View.GONE);
			}
		}
		catch (Exception e)
		{
			radio = (RadioButton) quickClaimRadioGroup.getChildAt(0);
			quickClaimFlag = "0";
			bankInfoLayout.setVisibility(View.GONE);
		}
		radio.setChecked(true);
	}

	/**
	 * 设置修理厂信息
	 * 
	 * @param code
	 * @param value
	 */
	/*
	 * private void setRepairNameSpinner(String code, String value) { String v =
	 * dataMap.get("RepairFactory").get(code); if (!StringUtils.isEmpty(v) ||
	 * StringUtils.isEmpty(code) || StringUtils.isEmpty(value)) {
	 * SpinnerUtils.setSpinnerData(this, repairFactoryCodeSp,
	 * dataMap.get("RepairFactory"), code); } else { Map<String, String> map =
	 * new HashMap<String, String>(); map.put(code, value);
	 * SpinnerUtils.setSpinnerData(this, repairFactoryCodeSp, map, code); }
	 * 
	 * repairFactoryCode = code; repairFactoryName = value; }
	 */
	class RepairFactorySelectedListener implements OnItemSelectedListener
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			switch (arg0.getId())
			{
			/*
			 * case R.id.repairFactoryCodeSp: // 如果是从修理厂名称控件点击选择 // String value
			 * = repairFactoryCodeSp.getSelectedItem().toString(); String value
			 * = repairFactoryCodeSp.getSelectedItem().toString(); String code =
			 * SpinnerUtils.getKey(value, dataMap.get("RepairFactory")); if
			 * (!StringUtils.isEmpty(code)) { // 当从spinner选择修理厂时 if
			 * (!value.equals(repairFactoryName) && !isNetworkRepairFactory) {
			 * repairFactoryCode = code; repairFactoryName =
			 * dataMap.get("RepairFactory").get( repairFactoryCode); } } else {
			 * if (value.equals("请选择")) { repairFactoryCode = "";
			 * repairFactoryName = ""; } }
			 * checkCommonFactory(repairFactoryCode); //
			 * 如果是从服务器获取到的修理厂，则默认添加进常用修理厂 if (isNetworkRepairFactory) { if
			 * (!StringUtils.isEmpty(repairFactoryCode) &&
			 * !StringUtils.isEmpty(repairFactoryName)) {
			 * insertToCommonFactory(repairFactoryCode, repairFactoryName);
			 * addCommonFactoryCheckBox.setChecked(true); } }
			 * isNetworkRepairFactory = false; break;
			 */
			case R.id.repairFactoryTypeSp:
				if (!repairFactoryTypeSp.getSelectedItem().toString().equals("请选择"))
				{
					SharedPreferences sp = getSharedPreferences("repairFactoryType", 0);
					sp.edit().putString("key", SpinnerUtils.getKey(repairFactoryTypeSp.getSelectedItem().toString(), dataMap.get("RepairFactoryType"))).putString("value", repairFactoryTypeSp.getSelectedItem().toString()).commit();
				}
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}

	}

	private boolean checkCommonFactory(String repairFactoryCode)
	{
		boolean result = false;
		if (!StringUtils.isEmpty(repairFactoryCode))
		{
			CommonFactoryDatabase database = new CommonFactoryDatabase(this);
			try
			{
				if (null != database.getCommonFactory(repairFactoryCode))
				{
					addCommonFactoryCheckBox.setChecked(true);
				}
				else
				{
					addCommonFactoryCheckBox.setChecked(false);
				}
			}
			catch (Exception e)
			{
				// e.printStackTrace();
			}
		}
		else
		{
			addCommonFactoryCheckBox.setChecked(false);
		}

		return result;
	}

	class LossBasicListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.defLossDateBtn:
				String time = null;
				if (StringUtils.isNotEmpty(defLossDateBtn.getText().toString()))
				{
					time = defLossDateBtn.getText().toString();
				}
				else
				{
					time = initTime;
				}
				y = Integer.valueOf(time.replaceAll("-", "").trim().substring(0, 4));
				m = Integer.valueOf(time.replaceAll("-", "").trim().substring(4, 6)) - 1;
				d = Integer.valueOf(time.replaceAll("-", "").trim().substring(6, 8));
				// dateDialogUtils.dateDialog(LossMenuAct.this, defLossDateBtn,
				// y, m, d);
				lock = true;
				break;
			case R.id.selectOptionBtn:// 选择定损意见
				Dialog dialog = new AlertDialog.Builder(LossMenuAct.this).setTitle("请选择定损意见").setMultiChoiceItems(totalFeedbacks, totalFeedbacksChecked, new DialogInterface.OnMultiChoiceClickListener()
				{

					@Override
					public void onClick(DialogInterface arg0, int which, boolean isChecked)
					{
						totalFeedbacksChecked[which] = isChecked;
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface arg0, int arg1)
					{
						StringBuffer msgs = new StringBuffer("");
						for (int i = 0; i < totalFeedbacksChecked.length; i++)
						{
							if (totalFeedbacksChecked[i])
							{
								msgs.append(totalFeedbacks[i]).append(";");
							}
						}
						lossOptionExt.setText(msgs.toString());
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface arg0, int arg1)
					{

					}
				}).create();
				dialog.show();
				break;
			/*
			 * case R.id.commonFactoryButton: try { CommonFactoryDatabase
			 * database = new CommonFactoryDatabase( LossMenuAct.this); final
			 * List<CommonFactory> list = database .selectCommonFactoryList();
			 * ListAdapter adapter = setCommonFactoryAdapter(list,
			 * LossMenuAct.this); new AlertDialog.Builder(LossMenuAct.this)
			 * .setTitle("选择常用修理厂") .setAdapter(adapter, new
			 * DialogInterface.OnClickListener() {
			 * 
			 * @Override public void onClick( DialogInterface arg0, int arg1) {
			 * try { CommonFactoryDatabase database = new CommonFactoryDatabase(
			 * LossMenuAct.this); List<CommonFactory> list = database
			 * .selectCommonFactoryList(); CommonFactory comm = list .get(arg1);
			 * showCommandFactoryByKeyword(comm .getRepairFactoryName());
			 * arg0.dismiss(); } catch (Exception e) { // e.printStackTrace(); }
			 * } }).show(); } catch (Exception e) { // e.printStackTrace(); }
			 * break;
			 */
			case R.id.sure:
			{
				if (mAddFeedbackEdit.getText().toString().equals(""))
				{
					Toast.makeText(LossMenuAct.this, "请填写意见内容", Toast.LENGTH_SHORT).show();
				}
				else
				{
					if (addFeedbackDialog.isShowing())
					{
						addFeedbackDialog.dismiss();
					}
					try
					{
						saveFeedbackFile();// 保存定损意见至本地
					}
					catch (Exception e)
					{
						// e.printStackTrace();
					}
					updateLossFeedbacks(); // 获取定损意见
					Toast.makeText(LossMenuAct.this, "增加成功", Toast.LENGTH_SHORT).show();
				}
			}
				break;
			// 历史意见查询
			case R.id.historyOpinionBtn:
				showHistoryOpinion(deflossData);
				break;
			case R.id.cancel:
				if (addFeedbackDialog.isShowing())
				{
					addFeedbackDialog.dismiss();
				}
				break;
			}
		}
	}

	/**
	 * 常用修理厂搜索，当用户点击常用修理厂选项时，从根据关键字从服务器读取数据
	 * 
	 * @param keyword
	 */
	/*
	 * private void showCommandFactoryByKeyword(String keyword) {
	 * repairNamesDialog = showRepartNamesDialog(LossMenuAct.this, keyword);
	 * repairNamesDialog.show();
	 * 
	 * }
	 */
	/**
	 * 弹出历史意见对话框
	 * 
	 * @param deflossData
	 */
	private void showHistoryOpinion(final DeflossData deflossData)
	{
		if (null != progressDialog)
		{
			progressDialog.dismiss();
		}
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("加载中...");
		// 获得历史意见信息
		final List<DeflossOptionData> list = deflossData.getHistoryOption();

		// 如果无记录
		if (null == list || list.isEmpty())
		{
			progressDialog.dismiss();
			ToastDialog.show(this, "无历史意见记录");
		}
		else
		{
			new AsyncTask<Void, Void, ExpandableListAdapter>()
			{

				@Override
				protected ExpandableListAdapter doInBackground(Void... arg0)
				{
					Map<String, Object> map = null;
					// 主列表
					final List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
					// 子列表
					final List<List<Map<String, Object>>> childGroups = new ArrayList<List<Map<String, Object>>>();
					for (int i = 0; i < list.size(); i++)
					{
						DeflossOptionData data = list.get(i);
						map = new HashMap<String, Object>();
						map.put("operator", data.getOperator());
						map.put("opinion", data.getOpinion());
						map.put("inputDate", data.getInputDate());
						contents.add(map);
						// 子列表对象
						List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
						child.add(map);
						childGroups.add(child);
					}
					// 创建列表适配器
					ExpandableListAdapter adapter = new SimpleExpandableListAdapter(LossMenuAct.this, contents, R.layout.item_loss_defloss_option, new String[]
					{ "operator", "opinion", "inputDate" }, new int[]
					{ R.id.operatorText, R.id.opinionText, R.id.inputDataText }, childGroups, R.layout.item_loss_defloss_option_child, new String[]
					{ "operator", "opinion", "inputDate" }, new int[]
					{ R.id.operatorText, R.id.opinionText, R.id.inputDataText });
					return adapter;
				}

				@Override
				protected void onPostExecute(ExpandableListAdapter result)
				{
					super.onPostExecute(result);
					if (null != progressDialog)
					{
						progressDialog.dismiss();
					}
					ExpandableListView listView = new ExpandableListView(LossMenuAct.this);
					listView.setAdapter(result);
					for (int i = 0; i < result.getGroupCount(); i++)
					{
						listView.expandGroup(i);
					}
					// 显示列表
					new AlertDialog.Builder(LossMenuAct.this).setTitle("历史意见查询").setView(listView).setNegativeButton("确定", null).setCancelable(false).show();
				}

			}.execute();
		}

	}

	/*
	 * private ArrayAdapter setCommonFactoryAdapter(List<CommonFactory> list,
	 * Context context) { List<String> contents = new ArrayList<String>(); for
	 * (CommonFactory item : list) { contents.add(item.getRepairFactoryName());
	 * } ArrayAdapter adapter = new ArrayAdapter(this,
	 * android.R.layout.simple_list_item_1, contents);
	 * 
	 * adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item
	 * ); return adapter; }
	 */
	private boolean saveSmLossBasic()
	{
		boolean result = false;
		registNo = registNoTxt.getText().toString();

		defLossDate = defLossDateBtn.getText().toString();
		defSite = defSiteExt.getText().toString();
		option = lossOptionExt.getText().toString();
		repairFactoryType = SpinnerUtils.getKey(repairFactoryTypeSp.getSelectedItem().toString(), dataMap.get("RepairFactoryType"));
		// repairFactoryName = SpinnerUtils.getValue(repairFactoryCode,
		// dataMap.get("RepairFactory"));
		repairFactoryName = repairFactoryCodeSp.getText().toString();
		repairFactoryCode = "";
		cetainLossType = SpinnerUtils.getKey("修复定损", dataMap.get("CertainLossType"));

		// 保存暂存信息
		try
		{
			AppConstants.cetainLossType = cetainLossType;// 设置定损方式
			deflossData.setRegistNo(registNo);
			deflossData.setTaskId(taskId);
			deflossData.setSubrogationFlag(String.valueOf(0));
			deflossData.setClaimSignFlag(String.valueOf(0));
			deflossData.setCetainLossType(cetainLossType);
			deflossData.setRepairFactoryCode(repairFactoryCode);
			deflossData.setRepairFactoryName(repairFactoryName);
			deflossData.setRepairFactoryType(repairFactoryType);
			deflossData.setDefLossDate(defLossDate);
			deflossData.setDefSite(defSite);
			deflossData.setLossLevel("02");// 轻度受损
			deflossData.setCaseFlag(String.valueOf(0));
			// deflossData.setLossPart(lossPart);
			deflossData.setLocalOption(option);
			deflossData.setIntermediaryFlag("0");// 司内定损
			deflossData.setQuickClaimFlag(quickClaimFlag);
			deflossDataService.update(deflossData);
		}
		catch (Exception e)
		{
			// //e.printStackTrace();
			errorMsg = "暂存失败！";
		}
		if (checkInputData())
		{
			return true;
		}
		else
		{
			return false;
		}
		// } else {
		// // ToastDialog.show(SmLossMeunAct.this, errorMsg,
		// // ToastDialog.ERROR);
		// }
	}

	/**
	 * @return
	 */
	private boolean checkInputData()
	{
		errorMsg = null;
		boolean result = true;
		if (StringUtils.isEmpty(cetainLossType))
		{
			errorMsg = "定损方式不能为空";
		}
		else if (StringUtils.isEmpty(defLossDate))
		{
			errorMsg = "定损日期不能为空";
		}
		else if (StringUtils.isEmpty(defSite))
		{
			errorMsg = "定损地点不能为空";
		}
		if (!StringUtils.isEmpty(errorMsg))
		{
			return false;
		}
		try
		{
			// 定损规则:当定损方式选择修复定损时【CetainLossType=01】，修理厂代码【RepairFactoryCode】不能为空
			if ("01".equals(cetainLossType) && StringUtils.isEmpty(repairFactoryCode))
			{
				throw new IllegalArgumentException("当定损方式选择修复定损时，修理厂名称不能为空");
			}
			// 定损规则:定损日期【DefLossDate】不应晚于当天
			if (!MyUtils.complateTime(defLossDate, DateTimeFactory.getInstance().getDat()))
			{
				throw new IllegalArgumentException("定损日期不能晚于当天");
			}
		}
		catch (Exception e)
		{
			errorMsg = e.getMessage();
		}
		if (!StringUtils.isEmpty(errorMsg))
		{
			return false;
		}
		return result;
	}

	private void setLossFeedbacks()
	{
		try
		{
			getLossFile(); // 获取本地定损信息
			if (null != feedbacks && feedbacks.length > 0)
			{
				int feedbackSize = feedbacks.length + lossIdeas.length; // 总长度
				totalFeedbacks = new String[feedbackSize]; // 实例化定损意见数据
				totalFeedbacksChecked = new boolean[feedbackSize];
				int index = 0;
				int checkedIndex = 0;
				// 获取所有定损意见
				for (int i = 0; i < lossIdeas.length; i++)
				{
					totalFeedbacks[index++] = lossIdeas[i];
					totalFeedbacksChecked[checkedIndex++] = false;
				}
				// 获取所有本地定损意见
				for (int i = 0; i < feedbacks.length; i++)
				{
					totalFeedbacks[index++] = feedbacks[i];
					totalFeedbacksChecked[checkedIndex++] = false;
				}
			}
			else
			{
				totalFeedbacks = new String[lossIdeas.length];
				totalFeedbacksChecked = new boolean[lossIdeas.length];
				// 获取所有定损意见
				for (int i = 0; i < lossIdeas.length; i++)
				{
					totalFeedbacks[i] = lossIdeas[i];
					totalFeedbacksChecked[i] = false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} // 获取本地查勘意见
	}

	private void updateLossFeedbacks()
	{
		try
		{
			getLossFile(); // 获取本地定损信息
			if (null != feedbacks && feedbacks.length > 0)
			{
				int feedbackSize = feedbacks.length + lossIdeas.length; // 总长度
				String[] tempTotalFeedbacks = new String[feedbackSize]; // 实例化定损意见数据
				boolean[] tempTotalFeedbacksChecked = new boolean[feedbackSize];
				int index = 0;
				int checkedIndex = 0;
				// 获取所有定损意见
				for (int i = 0; i < lossIdeas.length; i++)
				{
					tempTotalFeedbacks[index++] = lossIdeas[i];
					if (index < totalFeedbacksChecked.length)
					{
						tempTotalFeedbacksChecked[checkedIndex++] = totalFeedbacksChecked[index - 1];
					}
					else
					{
						tempTotalFeedbacksChecked[checkedIndex++] = false;
					}
				}
				// 获取所有本地定损意见
				for (int i = 0; i < feedbacks.length; i++)
				{
					tempTotalFeedbacks[index++] = feedbacks[i];
					if (index < totalFeedbacksChecked.length)
					{
						tempTotalFeedbacksChecked[checkedIndex++] = totalFeedbacksChecked[index - 1];
					}
					else
					{
						tempTotalFeedbacksChecked[checkedIndex++] = false;
					}
				}
				totalFeedbacks = tempTotalFeedbacks;
				totalFeedbacksChecked = tempTotalFeedbacksChecked;
			}
			else
			{
				totalFeedbacks = new String[lossIdeas.length];
				totalFeedbacksChecked = new boolean[lossIdeas.length];
				// 获取所有定损意见
				for (int i = 0; i < lossIdeas.length; i++)
				{
					totalFeedbacks[i] = lossIdeas[i];
					totalFeedbacksChecked[i] = false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} // 获取本地查勘意见
	}

	/** 保存定损意见 **/
	private void saveFeedbackFile() throws Exception
	{
		// 是否新添加定损意见
		for (String feedback : this.totalFeedbacks)
		{
			if (mAddFeedbackEdit.getText().toString().equals(feedback) || "".equals(mAddFeedbackEdit.getText().toString()))
			{
				return;
			}
		}
		// 是否存在SDCcard
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			String path = Environment.getExternalStorageDirectory() + File.separator + AppConstants.SDCARD_MAIN_DIR + File.separator + AppConstants.LOSS_FILE; // 文件保存路径
			File file = new File(path);
			if (!file.exists())
			{
				file.createNewFile(); // 创建文件
			}
			PrintStream ps = new PrintStream(new FileOutputStream(file, true));
			ps.println(mAddFeedbackEdit.getText().toString() + "_lossFeedback"); // 写入定损意见
			ps.close(); // 关闭输出流
		}
		else
		{
			Toast.makeText(this, "SDCard不存在，定损意见保存失败！", Toast.LENGTH_LONG).show();
		}
	}

	/** 读取定损意见文件 **/
	public void getLossFile() throws Exception
	{
		// 是否存在SDCcard
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			String path = Environment.getExternalStorageDirectory() + File.separator + AppConstants.SDCARD_MAIN_DIR + File.separator + AppConstants.LOSS_FILE; // 文件路径
			File file = new File(path);
			if (!file.exists())
			{
				file.createNewFile();
				// return;
			}
			Scanner scanner = new Scanner(file);
			StringBuffer msg = new StringBuffer();
			while (scanner.hasNextLine())
			{
				msg.append(scanner.nextLine()); // 读取文件信息
			}
			scanner.close(); // 关闭输出流
			if (null != msg && !msg.toString().equals(""))
			{
				feedbacks = msg.toString().split("_lossFeedback"); // 拆分内容信息
			}
			else
			{
				feedbacks = null;
			}
			if (feedbacks != null)
			{
				for (String feedback : feedbacks)
				{
					feedback.trim();
				}
			}
		}
		else
		{
			Toast.makeText(this, "SDCard不存在，读取失败!", Toast.LENGTH_LONG).show();
		}
	}

	public void deleteFeedback(int index) throws Exception
	{
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			String path = Environment.getExternalStorageDirectory() + File.separator + AppConstants.SDCARD_MAIN_DIR + File.separator + AppConstants.LOSS_FILE; // 文件路径
			File file = new File(path);
			if (!file.exists())
			{
				// Toast.makeText(this, "暂无任何本地定损意见!", Toast.LENGTH_LONG).show()
				// ;
				return;
			}
			Scanner scanner = new Scanner(file);
			StringBuffer msg = new StringBuffer();
			while (scanner.hasNextLine())
			{
				msg.append(scanner.nextLine()); // 读取文件信息
			}
			scanner.close(); // 关闭输出流
			int index1 = msg.indexOf(feedbacks[index]);
			if (index1 + feedbacks[index].length() + 12 >= msg.length())
			{
				msg.delete(msg.indexOf(feedbacks[index]), index1 + feedbacks[index].length());
			}
			else
			{
				msg.delete(msg.indexOf(feedbacks[index]), index1 + feedbacks[index].length() + 13);
			}

			if (file.exists())
			{
				file.delete();
				file.createNewFile();
			}
			PrintStream ps = new PrintStream(new FileOutputStream(file, true));
			ps.println(msg.toString()); // 写入定损意见
			ps.close(); // 关闭输出流

		}
		else
		{
			Toast.makeText(this, "SDCard不存在，读取失败!", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 33***********************************************************************
	 * ********** 定损车辆信息
	 */
	private Map<String, Map<String, String>> localMap1 = null;
	// private Map<String, Map<String, String>> dataMap = null;
	private ILossVehicleService lossVehicleService = null;
	private LossVehicle lossVehicle = null;
	private EditText licenseNoExt = null;// 车 牌 号
	
	private EditText engineNumberExt;
	private int firtFlag =0; //0.标的车 1->三者车
	
	private EditText frameNumberExt;
	
	
	private EditText carOwnerExt = null;// 车 主
	private EditText defLossCarTypeExt = null;// 损失方
	private Spinner licenseTypeSp = null;// 车牌 种类
	private Spinner indemnityDutySp = null;// 商业险赔偿责任
	private EditText indemDutyCiExt = null;// 交强险责任类型
	// private InputMethodManager imm = null;

	// private String taskId = null;
	private String licenseNo = null;
	private String defLossCarType = null;
	private String carOwner = null;
	private String licenseType = null;
	private String indemDutyCi = null;
	private String indemnityDuty = null;

	// private String errorMsg = null;
	private void setSmLossVehicle()
	{
		localMap1 = AppConstants.getLocalItemConf();
		// dataMap = DictCashe.getInstant().getDict();

		licenseNoExt = (EditText) vehicleBasicView.findViewById(R.id.licenseNoExt);
		
		engineNumberExt = (EditText) vehicleBasicView.findViewById(R.id.engineNumber);
		frameNumberExt = (EditText) vehicleBasicView.findViewById(R.id.frameNumber);
		frameNumberExt.setTransformationMethod(new ATobigA());
		
		
		
		carOwnerExt = (EditText) smLossBasicView.findViewById(R.id.carOwnerExt);
		
		
		
		defLossCarTypeExt = (EditText) smLossBasicView.findViewById(R.id.defLossCarTypeExt);
		licenseTypeSp = (Spinner) smLossBasicView.findViewById(R.id.licenseTypeSp);
		indemnityDutySp = (Spinner) smLossBasicView.findViewById(R.id.indemnityDutySp);
		indemDutyCiExt = (EditText) smLossBasicView.findViewById(R.id.indemDutyCiExt);
		try
		{
			setSmLossVehicleInfo();
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		indemnityDutySp.setOnItemSelectedListener(new LossVehicleOnItemListener());
	}

	private void setSmLossVehicleInfo() throws Exception
	{
		if (null == lossVehicleService)
		{
			lossVehicleService = new LossVehicleService();
		}
		lossVehicle = lossVehicleService.getByRegistNo(taskId);
		if (lossVehicle == null)
		{
			lossVehicle = new LossVehicle();
		}
		lossVehicle.init();
		licenseNoExt.setText(lossVehicle.getLicenseNo());
		
		engineNumberExt.setText(lossVehicle.getEngineNo());
		frameNumberExt.setText(lossVehicle.getFrameNo());
		
		String carType =lossVehicle.getDefLossCarType();
		if(StringUtils.isNotEmpty(carType))
		{
			//map.put("010", "三者车");
			//map.put("050", "标的车");
			if(carType.equals("010"))
			{
				//三者车
				firtFlag =1;
			}
			else
			{
				firtFlag =0;
				//标的车
				engineNumberExt.setBackgroundColor(Color.rgb(0xef, 0xef, 0xef));
				engineNumberExt.setFocusable(false);
				//frameNumberExt.setEnabled(false);
     			frameNumberExt.setBackgroundColor(Color.rgb(0xef, 0xef, 0xef));
				frameNumberExt.setFocusable(false);
			}
		}
		
		
		
		carOwnerExt.setText(lossVehicle.getCarOwner());
		// 设置下拉
		SpinnerUtils.setSpinnerData(this, licenseTypeSp, dataMap.get("LicenseKindCode"), lossVehicle.getLicenseType(), "");
		SpinnerUtils.setSpinnerData(this, indemnityDutySp, dataMap.get("IndemnityDuty"), lossVehicle.getIndemnityDuty(), "");

		if (!"4".equals(lossVehicle.getIndemnityDuty()))
		{// 有责
			indemDutyCiExt.setText("有责");
		}
		else
		{
			indemDutyCiExt.setText("无责");
		}
		defLossCarTypeExt.setText(SpinnerUtils.getValue(lossVehicle.getDefLossCarType(), localMap1.get("LossItemType")));
	}

	private boolean saveSmLossVehicle()
	{
		boolean result = false;
		try
		{
			carOwner = carOwnerExt.getText().toString();
			licenseNo = licenseNoExt.getText().toString();
			indemnityDuty = SpinnerUtils.getKey(indemnityDutySp.getSelectedItem().toString(), dataMap.get("IndemnityDuty"));
			indemDutyCi = indemDutyCiExt.getText().toString();
			defLossCarType = defLossCarTypeExt.getText().toString();
			licenseType = SpinnerUtils.getKey(licenseTypeSp.getSelectedItem().toString(), dataMap.get("LicenseKindCode"));

			lossVehicle.setCarOwner(carOwner);
			lossVehicle.setLicenseNo(licenseNo);
			

			
			if ("无责".equals(indemDutyCi))
			{
				lossVehicle.setIndemDutyCi(String.valueOf(0));
			}
			else
			{
				lossVehicle.setIndemDutyCi(String.valueOf(100));// 100
			}
			lossVehicle.setIndemnityDuty(indemnityDuty);// 0

			String indemnityDutyRate = SpinnerUtils.getValue(indemnityDuty, dataMap.get("IndemnityDutyRate"));
			lossVehicle.setIndemnityDutyRate(indemnityDutyRate);

			lossVehicle.setDefLossCarType(SpinnerUtils.getKey(defLossCarType, localMap1.get("LossItemType")));
			lossVehicle.setLossType("01");// 损失类型(非全损)
			// lossVehicle.setCarKindCode(carKindCode);
			lossVehicle.setLicenseType(licenseType);
			// lossVehicle.setEnrollDate(enrollDate);
			LossVehicleService vehicleService = new LossVehicleService();
			if (vehicleService.update(lossVehicle))
			{
				result = true;
				// CustomDialog.show(SmLossMeunAct.this, "信息提示",
				// "保存成功!", null, null);
			}
			else
			{
				errorMsg = "暂存失败！";
				// ToastDialog.show(SmLossMeunAct.this, "暂存失败",
				// ToastDialog.ERROR);
			}

			// } else {
			// // ToastDialog.show(SmLossMeunAct.this, errorMsg,
			// // ToastDialog.ERROR);
			// }
		}
		catch (Exception e)
		{
			// e.printStackTrace();
			errorMsg = "暂存失败！";
		}
		if (checkVehicleData())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	class LossVehicleOnItemListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
			if (0 != position)
			{
				switch (parent.getId())
				{
				case R.id.indemnityDutySp:// 商业险赔偿责任
					indemnityDuty = SpinnerUtils.getKey(indemnityDutySp.getSelectedItem().toString(), dataMap.get("IndemnityDuty"));
					if ("4".equals(indemnityDuty))
					{// 无责
						indemDutyCiExt.setText("无责");
					}
					else
					{
						indemDutyCiExt.setText("有责");
					}
					break;
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}

	}

	/**
	 * @return
	 */
	private boolean checkVehicleData()
	{
		boolean result = true;
		errorMsg = null;
		if (StringUtils.isEmpty(defLossCarType))
		{
			errorMsg = "损失方不能为空";
		}
		else if (StringUtils.isEmpty(carOwner))
		{
			errorMsg = "车主不能为空";
		}
		else if (StringUtils.isEmpty(licenseType))
		{
			errorMsg = "车牌种类不能为空";
		}
		else if (StringUtils.isEmpty(indemnityDuty))
		{
			errorMsg = "商业险赔偿责任不能为空";
		}
		if (StringUtils.isNotEmpty(errorMsg))
		{
			result = false;
		}
		return result;
	}

	/**
	 * 33***********************************************************************
	 * ********** 车辆基本信息 未定型
	 */
	private String[] zmIndex =
	{ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private IThirdPartyService thirdPartyService;
	private Map<String, Map<String, String>> map;
	private List<Manufacturer> list = null;
	private List<Brand> brandList = null;
	private List<Car> carList = null;
	private List<Emus> emusList = null;
	private Button dataSreachBtn;// 查询车型
	private Button cancelBtn; // 取消定型

	private Button vehFactoryNameBtn;// 厂家名称
	private Spinner vehBrandNameSp;// 品牌名称
	private Spinner vehSeriNameSp;// 定损车系名称
	private Spinner vehGroupNameSp;// 定损车组名称
	private TableRow tableRow2;
	private TableRow tableRow3;
	private TableRow tableRow4;
	// private Dialog dialog = null;
	private String emusId = null;
	// private String registNo;
	// private String taskId;
	private String subStr;
	private String indexValue;

	private void setVehicleTypeQuery()
	{
		dataSreachBtn = (Button) vehicleTypeQueryView.findViewById(R.id.dataSreachBtn);
		cancelBtn = (Button) vehicleTypeQueryView.findViewById(R.id.cancelBtn);

		vehFactoryNameBtn = (Button) vehicleTypeQueryView.findViewById(R.id.vehFactoryNameBtn);// 厂家名称
		vehBrandNameSp = (Spinner) vehicleTypeQueryView.findViewById(R.id.vehBrandNameSp);// 品牌名称
		vehSeriNameSp = (Spinner) vehicleTypeQueryView.findViewById(R.id.vehSeriNameSp);// 定损车系名称
		vehGroupNameSp = (Spinner) vehicleTypeQueryView.findViewById(R.id.vehGroupNameSp);// 定损车组名称
		tableRow2 = (TableRow) vehicleTypeQueryView.findViewById(R.id.tableRow2);//
		tableRow3 = (TableRow) vehicleTypeQueryView.findViewById(R.id.tableRow3);//
		tableRow4 = (TableRow) vehicleTypeQueryView.findViewById(R.id.tableRow4);//

		if (null == thirdPartyService)
		{
			thirdPartyService = new ThirdPartyService();
		}

		VehicleTypeListener listener = new VehicleTypeListener();
		dataSreachBtn.setOnClickListener(listener);
		cancelBtn.setOnClickListener(listener);
		vehFactoryNameBtn.setOnClickListener(listener);
	}

	class VehicleTypeListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.vehFactoryNameBtn:// 选择生产厂家事件处理
				selectVehicleFactory(this);
				break;
			case R.id.subTxt:// Dialog选择框(选择生产厂家后回到主界面操作)
				TextView subText = (TextView) v;
				subStr = subText.getText().toString();
				vehFactoryNameBtn.setText(subStr);

				if (null != dialog)
				{
					dialog.dismiss();

					String key = subText.getTag().toString();
					Map<String, String> chailMap = map.get(key);
					String value = chailMap.get(subStr);
					if (StringUtils.isEmpty(value))
					{
						value = "";
					}
					Manufacturer manufacturer = list.get(0);
					brandList = thirdPartyService.getBandByManufacturerId(value);
					if (null != brandList && !brandList.isEmpty())
					{
						ArrayAdapter<String> brandAdapter = new ArrayAdapter<String>(LossMenuAct.this, R.layout.item_simple_spinner);
						brandAdapter.add("请选择品牌");
						Iterator iter = brandList.iterator();
						while (iter.hasNext())
						{
							Brand brand = (Brand) iter.next();
							brandAdapter.add(brand.getName());
						}
						brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						vehBrandNameSp.setAdapter(brandAdapter);
						vehBrandNameSp.setOnItemSelectedListener(new VehicleTypeOnItemListener());
						// vehBrandNameSp.setSelection(0, true);
						tableRow2.setVisibility(0);
						tableRow3.setVisibility(8);
						tableRow4.setVisibility(8);
					}
					else
					{
						tableRow2.setVisibility(8);
					}
				}
				break;
			case R.id.dataSreachBtn:
				if (StringUtils.isNotEmpty(emusId))
				{
					Intent intent = getIntent();
					intent.putExtra("registNo", registNo);
					intent.putExtra("taskId", taskId);
					intent.putExtra("emusId", emusId);
					intent.putExtra("vehSeriName", vehSeriNameSp.getSelectedItem().toString());
					intent.putExtra("vehGroupName", vehGroupNameSp.getSelectedItem().toString());
					/* 新增时间2013.5.11 - 严植培 */
					intent.putExtra("vehFactoryName", vehFactoryNameBtn.getText().toString());
					intent.putExtra("vehBrandName", vehBrandNameSp.getSelectedItem().toString());
					intent.putExtra("vehGroupName", vehGroupNameSp.getSelectedItem().toString());
					intent.setClass(LossMenuAct.this, VehicleTypeListAct.class);
					startActivity(intent);
				}
				else
				{
					ToastDialog.show(LossMenuAct.this, "请选择查询条件", ToastDialog.ERROR);
				}
				break;
			case R.id.cancelBtn:
				// 取消定型
				if (!"".equals(vehCertainNameExt.getText().toString()))
				{
					views.set(2, vehicleBasicView);
					viewPager.setOnPageChangeListener(null);
					surveyPagerAdapter.refreshViews(views);
					viewPager.setAdapter(surveyPagerAdapter);
					viewPager.setCurrentItem(currentPage);
					viewPager.setOnPageChangeListener(LossMenuAct.this);
				}
				else
				{
					ToastDialog.show(LossMenuAct.this, "请选择查询条件", ToastDialog.ERROR);
				}
				break;
			}
		}

	}

	class VehicleTypeOnItemListener implements OnItemSelectedListener
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View v, int pos, long arg3)
		{
			if (0 != pos)
			{

				switch (arg0.getId())
				{
				case R.id.vehBrandNameSp:
					Brand brand = brandList.get(pos - 1);
					carList = thirdPartyService.getCarByBrandId(brand.getId());
					if (null != carList && !carList.isEmpty())
					{
						ArrayAdapter<String> carAdapter = new ArrayAdapter<String>(LossMenuAct.this, R.layout.item_simple_spinner);
						carAdapter.add("请选择车系名称");
						Iterator iter = carList.iterator();
						while (iter.hasNext())
						{
							Car car = (Car) iter.next();
							carAdapter.add(car.getName());
						}
						carAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						vehSeriNameSp.setAdapter(carAdapter);
						vehSeriNameSp.setOnItemSelectedListener(this);
						vehSeriNameSp.setSelection(0, true);
						tableRow3.setVisibility(0);
						tableRow4.setVisibility(8);
					}
					else
					{
						tableRow3.setVisibility(8);
					}
					emusId = null;
					break;
				case R.id.vehSeriNameSp:
					Car car = carList.get(pos - 1);
					emusList = thirdPartyService.getEmusByCarId(car.getId());
					if (null != emusList && !emusList.isEmpty())
					{
						ArrayAdapter<String> emusAdapter = new ArrayAdapter<String>(LossMenuAct.this, R.layout.item_simple_spinner);
						emusAdapter.add("请选择车系名称");
						Iterator iter = emusList.iterator();
						while (iter.hasNext())
						{
							Emus emus = (Emus) iter.next();
							emusAdapter.add(emus.getName());
						}
						emusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						vehGroupNameSp.setAdapter(emusAdapter);
						vehGroupNameSp.setOnItemSelectedListener(this);
						vehGroupNameSp.setSelection(0, true);
						tableRow4.setVisibility(0);
					}
					else
					{
						tableRow4.setVisibility(8);
					}
					break;
				case R.id.vehGroupNameSp:
					Emus emus = emusList.get(pos - 1);
					emusId = emus.getId();
					break;
				}
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}

	}

	private void selectVehicleFactory(OnClickListener listener)
	{
		loadFactory(null);
		FactoryDialog customDialog = new FactoryDialog();
		if (dialog != null)
		{
			try
			{
				dialog.dismiss();
			}
			catch (Exception e)
			{

			}
		}
		dialog = customDialog.getDialog(LossMenuAct.this, "选择生产厂家", map, listener);
		dialog.show();
	}

	private void loadFactory(String text)
	{
		map = new HashMap<String, Map<String, String>>();
		list = thirdPartyService.getFactory(text);
		Map a = new HashMap<String, String>();
		Map b = new HashMap<String, String>();
		Map c = new HashMap<String, String>();
		Map d = new HashMap<String, String>();
		Map e = new HashMap<String, String>();
		Map f = new HashMap<String, String>();
		Map g = new HashMap<String, String>();
		Map h = new HashMap<String, String>();
		Map i = new HashMap<String, String>();
		Map j = new HashMap<String, String>();
		Map k = new HashMap<String, String>();
		Map l = new HashMap<String, String>();
		Map m = new HashMap<String, String>();
		Map n = new HashMap<String, String>();
		Map o = new HashMap<String, String>();
		Map p = new HashMap<String, String>();
		Map q = new HashMap<String, String>();
		Map r = new HashMap<String, String>();
		Map s = new HashMap<String, String>();
		Map t = new HashMap<String, String>();
		Map u = new HashMap<String, String>();
		Map v = new HashMap<String, String>();
		Map w = new HashMap<String, String>();
		Map x = new HashMap<String, String>();
		Map y = new HashMap<String, String>();
		Map z = new HashMap<String, String>();

		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Manufacturer manufacturer = (Manufacturer) iter.next();
			if ("A".equals(manufacturer.getZmIndex()))
			{
				a.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("B".equals(manufacturer.getZmIndex()))
			{
				b.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("C".equals(manufacturer.getZmIndex()))
			{
				c.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("D".equals(manufacturer.getZmIndex()))
			{
				d.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("E".equals(manufacturer.getZmIndex()))
			{
				e.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("F".equals(manufacturer.getZmIndex()))
			{
				f.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("G".equals(manufacturer.getZmIndex()))
			{
				g.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("H".equals(manufacturer.getZmIndex()))
			{
				h.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("I".equals(manufacturer.getZmIndex()))
			{
				i.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("J".equals(manufacturer.getZmIndex()))
			{
				j.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("K".equals(manufacturer.getZmIndex()))
			{
				k.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("L".equals(manufacturer.getZmIndex()))
			{
				l.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("M".equals(manufacturer.getZmIndex()))
			{
				m.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("N".equals(manufacturer.getZmIndex()))
			{
				n.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("O".equals(manufacturer.getZmIndex()))
			{
				o.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("P".equals(manufacturer.getZmIndex()))
			{
				p.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("Q".equals(manufacturer.getZmIndex()))
			{
				q.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("R".equals(manufacturer.getZmIndex()))
			{
				r.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("S".equals(manufacturer.getZmIndex()))
			{
				s.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("T".equals(manufacturer.getZmIndex()))
			{
				t.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("U".equals(manufacturer.getZmIndex()))
			{
				u.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("V".equals(manufacturer.getZmIndex()))
			{
				v.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("W".equals(manufacturer.getZmIndex()))
			{
				w.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("X".equals(manufacturer.getZmIndex()))
			{
				x.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("Y".equals(manufacturer.getZmIndex()))
			{
				y.put(manufacturer.getName(), manufacturer.getId());
			}
			else if ("Z".equals(manufacturer.getZmIndex()))
			{
				z.put(manufacturer.getName(), manufacturer.getId());
			}
		}
		map.put("A", a);
		map.put("B", b);
		map.put("C", c);
		map.put("D", d);
		map.put("E", e);
		map.put("F", f);
		map.put("G", g);
		map.put("H", h);
		map.put("I", i);
		map.put("J", j);
		map.put("K", k);
		map.put("R", r);
		map.put("M", m);
		map.put("N", n);
		map.put("O", o);
		map.put("P", p);
		map.put("Q", q);
		map.put("L", l);
		map.put("S", s);
		map.put("T", t);
		map.put("U", u);
		map.put("V", v);
		map.put("W", w);
		map.put("X", x);
		map.put("Y", y);
		map.put("Z", z);
	}

	/**
	 * 33***********************************************************************
	 * **************************************** 车辆基本信息-已定型
	 */
	private Map<String, Map<String, String>> localMap = null;
	private Map<String, Map<String, String>> dicMap = null;
	private Map<String, String> insureMap = null;
	/** 封装日期控件 */
	// private DateDialogUtils dateDialogUtils = null;
	// private Dialog dialog = null;

	// private IDeflossDataService deflossDataService = null;
	private IKindCodeDataService kindCodeDataService = null;
	private List<KindCodeData> kindCodeList = null;
	// private IBasicVehicleService basicVehicleService = null;
	// private DeflossData deflossData = null;
	// private BasicVehicle basicVehicle = null;
	// private InputMethodManager imm = null;

	private Button modifyVehicleBtn = null; // 修改定损车型
	private Spinner vehKindCodeSp = null;
	private EditText vehCertainNameExt = null;// 定损车型名称
	private Button vehYearTypeBtn = null;
	private TextView plateNoTxt = null;
	// private Spinner selfConfigFlagSp = null;
	private EditText remarkExt = null;
	private EditText sumChgCompFeeExt = null;
	private EditText sumRepairFeeExt = null;
	private EditText sumMaterialFeeExt = null;
	private EditText sumLossFeeExt = null;
	private EditText sumRemnantExt = null; // 折残总金额
	private EditText sumRescueFeeExt = null; // 施救费
	private Spinner insureTermSp = null;

	// private String registNo = null;
	// private String taskId = null;
	private String vehKindCode = null;
	private String vehKindName = null;
	private String vehCertainCode = null;
	private String vehCertainName = null;
	private String vehYearType = null;
	private String plateNo = null;
	private String selfConfigFlag = null;
	private String remark = null;
	private double sumChgCompFee = 0;
	private double sumRepairFee = 0;
	private double sumMaterialFee = 0;
	private double sumLossFee = 0;
	private double sumRemnant = 0;
	private double sumRescueFee = 0;
	private double remnant = 0;// 残值
	private String cetainLossType6 = null;
	private String insureTerm = null;
	private String insureTermCode = null;
	private long deflossId = 0;

	// private String errorMsg = null;

	private void setVehicleBasic()
	{
		dicMap = DictCashe.getInstant().getDict();
		localMap = AppConstants.getLocalItemConf();

		dateDialogUtils = new DateDialogUtils();
		// 设置视图控件
		// setViewControl();
		modifyVehicleBtn = (Button) vehicleBasicView.findViewById(R.id.modifyVehicleBtn);
		vehKindCodeSp = (Spinner) vehicleBasicView.findViewById(R.id.vehKindCodeSp);
		vehCertainNameExt = (EditText) vehicleBasicView.findViewById(R.id.vehCertainNameExt);
		vehYearTypeBtn = (Button) vehicleBasicView.findViewById(R.id.vehYearTypeBtn);
		plateNoTxt = (TextView) vehicleBasicView.findViewById(R.id.plateNoTxt);
		// selfConfigFlagSp = (Spinner) vehicleBasicView
		// .findViewById(R.id.selfConfigFlagSp);
		remarkExt = (EditText) vehicleBasicView.findViewById(R.id.remarkExt);
		/****************************************************
		 * 银行信息
		 */
		sumChgCompFeeExt = (EditText) smTaskSubmitView.findViewById(R.id.sumChgCompFeeExt);
		sumRepairFeeExt = (EditText) smTaskSubmitView.findViewById(R.id.sumRepairFeeExt);
		sumMaterialFeeExt = (EditText) smTaskSubmitView.findViewById(R.id.sumMaterialFeeExt);
		sumLossFeeExt = (EditText) smTaskSubmitView.findViewById(R.id.sumLossFeeExt);
		sumRemnantExt = (EditText) smTaskSubmitView.findViewById(R.id.sumRemnantExt);// 折残总金额
		sumRescueFeeExt = (EditText) smTaskSubmitView.findViewById(R.id.sumRescueFeeExt);
		/**
		 * 银行信息 ***************************************************
		 */

		// 险别
		insureTermSp = (Spinner) vehicleBasicView.findViewById(R.id.insureTermSp);

		// 装载数据
		// loadVehicleBasicData();

		vehYearTypeBtn.setOnClickListener(new DateBtnClick(vehYearTypeBtn));
		modifyVehicleBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				views.set(2, vehicleTypeQueryView);
				viewPager.setOnPageChangeListener(null);
				surveyPagerAdapter.refreshViews(views);
				viewPager.setAdapter(surveyPagerAdapter);
				viewPager.setCurrentItem(currentPage);
				viewPager.setOnPageChangeListener(LossMenuAct.this);
			}

		});
	}

	private void loadVehicleView()
	{
		if (checkVehicleType())
		{ // 已定型//3
			views.set(2, vehicleBasicView);
		}
		else
		{
			// 未定型
			views.set(2, vehicleTypeQueryView);
		}
		surveyPagerAdapter.refreshViews(views);
		viewPager.setAdapter(surveyPagerAdapter);
	}

	private void loadVehicleBasicData()
	{

		try
		{
			if (StringUtils.isNotEmpty(taskId))
			{
				if (null == deflossDataService)
				{
					deflossDataService = new DeflossDataService();
				}
				deflossData = deflossDataService.getByTaskId(taskId);
				deflossData.init();
				if (null == basicVehicleService)
				{
					basicVehicleService = new BasicVehicleService();
				}
				basicVehicle = basicVehicleService.getByRegistNo(taskId);
				basicVehicle.init();
				if (null == basicVehicle)
				{
					return;
				}
				vehCertainNameExt.setText(basicVehicle.getVehCertainName());
				vehCertainNameExt.setEnabled(false);
				// 车辆定损信息
				LossVehicleService lossVehicleService = new LossVehicleService();
				LossVehicle lossVehicle = lossVehicleService.getByRegistNo(this.taskId);
				if (null != lossVehicle && StringUtils.isNotEmpty(lossVehicle.getEnrollDate()))
				{
					vehYearTypeBtn.setText(lossVehicle.getEnrollDate()); // 初登日期
				}
				else
				{
					vehYearTypeBtn.setText(MyUtils.format("yyyy-MM-dd", DateTimeFactory.getInstance().getDat())); // 初登日期
				}
				// 以定损车辆信息中的车牌号码为准
				if (null != lossVehicle && StringUtils.isNotEmpty(lossVehicle.getLicenseNo()))
				{
					plateNoTxt.setText(lossVehicle.getLicenseNo());
				}
				else
				{
					plateNoTxt.setText(basicVehicle.getPlateNo());
				}
				remarkExt.setText(basicVehicle.getRemark());

				cetainLossType6 = SpinnerUtils.getValue(AppConstants.cetainLossType, dicMap.get("CertainLossType"));
				sumLossFeeExt.setEnabled(false);
				if (0 < basicVehicle.getSumRemnant())
				{
					remnant = basicVehicle.getSumRemnant();
					sumRemnantExt.setText(String.valueOf(basicVehicle.getSumRemnant()));// 折残总金额
				}

				if (0 < basicVehicle.getSumRescueFee())
				{
					sumRescueFeeExt.setText(String.valueOf(basicVehicle.getSumRescueFee()));
				}

				if (StringUtils.isNotEmpty(cetainLossType6) && cetainLossType6.equals("一次性协议定损"))
				{
					/** 可修改 - **/
					sumChgCompFeeExt.setEnabled(true);
					sumRepairFeeExt.setEnabled(true);
					sumMaterialFeeExt.setEnabled(true);
					sumRemnantExt.setEnabled(true);
					sumRescueFeeExt.setEnabled(true);
					String temp = "";
					if (0 < basicVehicle.getSumChgCompFee())
					{
						temp = new BigDecimal(String.valueOf(basicVehicle.getSumChgCompFee())).toPlainString();
						sumChgCompFeeExt.setText(temp);
					}
					if (0 < basicVehicle.getSumRepairFee())
					{
						temp = new BigDecimal(String.valueOf(basicVehicle.getSumRepairFee())).toPlainString();
						sumRepairFeeExt.setText(temp);
					}
					if (0 < basicVehicle.getSumMaterialFee())
					{
						temp = new BigDecimal(String.valueOf(basicVehicle.getSumMaterialFee())).toPlainString();
						sumMaterialFeeExt.setText(temp);
					}
					if (0 < basicVehicle.getSumLossFee())
					{
						temp = new BigDecimal(String.valueOf(basicVehicle.getSumLossFee())).toPlainString();
						sumLossFeeExt.setText(temp);
					}
					sumChgCompFeeExt.setOnFocusChangeListener(focusChangeListener);
					sumRepairFeeExt.setOnFocusChangeListener(focusChangeListener);
					sumMaterialFeeExt.setOnFocusChangeListener(focusChangeListener);

					/** 不可修改 - 严植培 **/

					/**
					 * 2013.5.16 严植培 定损处理-车辆基本信息，所有金额统计
					 */
					/** 统计修理总金额 **/

					/** 可修改 - **/

				}
				else
				{
					/** 不可修改 - 严植培 **/
					sumChgCompFeeExt.setEnabled(false);
					sumRepairFeeExt.setEnabled(false);
					sumMaterialFeeExt.setEnabled(false);
					sumRemnantExt.setEnabled(false);
					sumRescueFeeExt.setEnabled(true);
					remnant = 0;
					/** 不可修改 - 严植培 **/

					/** 统计换件总金额 **/
					double totalFitInfoPrice = 0; // 换件总金额
					LossFitInfoService fitInfoService = new LossFitInfoService(); // 实例化LossFitInfoService实现类
					List<LossFitInfoItem> lossFitList = fitInfoService.getByRegistNo(taskId); // 根据任务ID获取换件集合信息
					for (LossFitInfoItem item : lossFitList)
					{
						totalFitInfoPrice += MyUtils.mult(item.getLossCount(), item.getSurveyQuotedPrice());// 统计总金额
						remnant += item.getRemnant();
					}
					String tempStr = "";
					if (0 < totalFitInfoPrice)
					{
						tempStr = new BigDecimal(String.valueOf(totalFitInfoPrice)).toPlainString();
						sumChgCompFeeExt.setText(tempStr); // 设置换件总金额
					}
					else
					{
						sumChgCompFeeExt.setText("");
					}
					double totalRepairInfoPrice = 0; // 修理总金额
					LossRepairInfoService repairInfoService = new LossRepairInfoService(); // 实例化LossRepairInfoService实现类
					List<LossRepairInfoItem> repairList = repairInfoService.getByRegistNo(taskId); // 根据任务ID获取修理集合信息
					for (LossRepairInfoItem item : repairList)
					{
						totalRepairInfoPrice += item.getRepairFee(); // 统计总金额
					}
					if (0 < totalRepairInfoPrice)
					{
						tempStr = new BigDecimal(String.valueOf(totalRepairInfoPrice)).toPlainString();
						sumRepairFeeExt.setText(tempStr); // 设置修理总金额
					}
					else
					{
						sumRepairFeeExt.setText("");
					}
					/** 统计辅料总金额 **/
					double totalAssistInfoPrice = 0; // 辅料总金额
					LossAssistInfoService assistInfoService = new LossAssistInfoService(); // 实例化LossAssistInfoService实现类
					List<LossAssistInfoItem> assistList = assistInfoService.getByRegistNo(taskId); // 根据任务ID获取辅料集合信息
					for (LossAssistInfoItem item : assistList)
					{
						totalAssistInfoPrice += item.getMaterialFee(); // 统计总金额
					}
					if (0 < totalAssistInfoPrice)
					{
						tempStr = new BigDecimal(String.valueOf(totalAssistInfoPrice)).toPlainString();
						sumMaterialFeeExt.setText(tempStr); // 设置辅料总金额
					}
					else
					{
						sumMaterialFeeExt.setText("");
					}
					// 改变监听事件
					sumRemnantExt.setOnFocusChangeListener(focusChangeListener);
					sumRescueFeeExt.setOnFocusChangeListener(focusChangeListener);

					sumRemnant = StringUtils.toDouble(sumRemnantExt.getText().toString()); //
					sumRescueFee = StringUtils.toDouble(sumRescueFeeExt.getText().toString());
					/** 总金额 **/
					double sumLossFee = totalFitInfoPrice + totalRepairInfoPrice + totalAssistInfoPrice;// -remnant;
					// + sumRemnant + sumRescueFee - remnant;
					tempStr = new BigDecimal(String.valueOf(sumLossFee)).toPlainString();
					sumLossFeeExt.setText(tempStr); // 设置总金额

				}
				SpinnerUtils.setSpinnerData(this, vehKindCodeSp, dicMap.get("CarKind"), basicVehicle.getVehKindCode(), "");

				// SpinnerUtils.setSpinnerData(this, selfConfigFlagSp,
				// localMap.get("Status"),
				// basicVehicle.getSelfConfigFlag() + "");
				sumRemnantExt.setText(remnant + "");

				// if (null == deflossDataService) {
				// deflossDataService = new DeflossDataService();
				// }
				// DeflossData deflossData = deflossDataService
				// .getByTaskId(taskId);
				if (null == kindCodeDataService)
				{
					kindCodeDataService = new KindCodeDataService();
				}
				String vehkindCode = deflossData.getInsureTermCode() + "";
				kindCodeList = kindCodeDataService.getById(deflossData.getId());
				insureMap = new HashMap<String, String>();
				if (null != kindCodeList && !kindCodeList.isEmpty())
				{
					Iterator iter = kindCodeList.iterator();
					while (iter.hasNext())
					{
						KindCodeData kindCodeData = (KindCodeData) iter.next();

						insureMap.put(kindCodeData.getInsureTermCode(), kindCodeData.getInsureTerm());
					}
					if (StringUtils.isNotEmpty(cetainLossType6) && cetainLossType6.equals("一次性协议定损"))
					{
						// 设置下拉数据
						vehkindCode = SpinnerUtils.getKey("车辆损失险", insureMap);// 一次性协议定损，无需填写险别，默认是车辆损失险
					}
				}
				// biao'di'che
				if (insureMap.isEmpty())
				{

					insureMap.put("02", "商业第三者责任险");
					insureMap.put("BZ", "机动车交通事故责任强制险");
					if (StringUtils.isNotEmpty(cetainLossType6) && cetainLossType6.equals("一次性协议定损"))
					{
						// 设置下拉数据
						vehkindCode = SpinnerUtils.getKey("商业第三者责任险", insureMap);// 一次性协议定损，无需填写险别，默认是车辆损失险
					}
				}

				SpinnerUtils.setSpinnerData(this, insureTermSp, insureMap, vehkindCode, "");

				insureTermSp.setOnItemSelectedListener(new OnItemSelectedListener()
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
					{
						if (!insureTermSp.getSelectedItem().toString().equals("请选择"))
						{
							SharedPreferences sp = getSharedPreferences("insureTerm", 0);
							sp.edit().putString("key", SpinnerUtils.getKey(insureTermSp.getSelectedItem().toString(), insureMap)).putString("value", insureTermSp.getSelectedItem().toString()).commit();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0)
					{

					}

				});

				// 如果为标的车，商业险第三责任不能选择
				if ("050".equals(lossVehicle.getDefLossCarType()))
				{
					isStandardCar = true;
					insureMap.remove("B");
				}
				else
				{
					isStandardCar = false;
				}

				// 如果为标的车，则不可以修改定损车型
				// if (isStandardCar) {
				// modifyVehicleBtn.setVisibility(View.GONE);

				// } else {
				// // 如果为三者车，则可以修改定损车型
				// modifyVehicleBtn.setVisibility(View.VISIBLE);
				// }
				modifyVehicleBtn.setVisibility(View.VISIBLE);

			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	private OnFocusChangeListener focusChangeListener = new OnFocusChangeListener()
	{
		@Override
		public void onFocusChange(View v, boolean hasFocus)
		{
			sumLossFeeExt.setText(String.valueOf(sumFree()));
		}

	};

	private double sumFree()
	{
		sumChgCompFee = StringUtils.toDouble(sumChgCompFeeExt.getText().toString());
		sumRepairFee = StringUtils.toDouble(sumRepairFeeExt.getText().toString());
		sumMaterialFee = StringUtils.toDouble(sumMaterialFeeExt.getText().toString());
		sumRemnant = StringUtils.toDouble(sumRemnantExt.getText().toString()); //
		sumRescueFee = StringUtils.toDouble(sumRescueFeeExt.getText().toString());
		return sumChgCompFee + sumRepairFee + sumMaterialFee;
	}

	private boolean saveSmVehicleBasic()
	{
		boolean result = false;
		try
		{
			// 定损车辆种类代码、名称
			vehKindCode = SpinnerUtils.getKey(vehKindCodeSp.getSelectedItem().toString(), dicMap.get("CarKind"));
			vehKindName = vehKindCodeSp.getSelectedItem().toString();

			vehCertainName = vehCertainNameExt.getText().toString();
			vehYearType = vehYearTypeBtn.getText().toString();
			plateNo = plateNoTxt.getText().toString();

			selfConfigFlag = "0";
			// selfConfigFlag = SpinnerUtils.getKey(selfConfigFlagSp
			// .getSelectedItem().toString(), localMap
			// .get("Status"));
			remark = remarkExt.getText().toString();

			sumChgCompFee = StringUtils.toDouble(sumChgCompFeeExt.getText().toString());
			sumRepairFee = StringUtils.toDouble(sumRepairFeeExt.getText().toString());
			sumMaterialFee = StringUtils.toDouble(sumMaterialFeeExt.getText().toString());
			sumLossFeeExt.setText(String.valueOf(sumFree()));
			sumLossFee = StringUtils.toDouble(sumLossFeeExt.getText().toString());
			sumRemnant = StringUtils.toDouble(sumRemnantExt.getText().toString());
			sumRescueFee = StringUtils.toDouble(sumRescueFeeExt.getText().toString());

			
			
			String engineNumber = engineNumberExt.getText().toString().trim();
			String frameNumber = frameNumberExt.getText().toString().trim().toUpperCase();// 车架号／VIN：
			
			if(StringUtils.isNotEmpty(engineNumber))
			{
				lossVehicle.setEngineNo(engineNumber);
			}
			if(StringUtils.isNotEmpty(frameNumber))
			{
				lossVehicle.setVinNo(frameNumber);
			}
			
			
			basicVehicle.setCaseNo(registNo);
			basicVehicle.setVehKindCode(vehKindCode);
			basicVehicle.setVehKindName(vehKindName);
			basicVehicle.setVehYearType(vehYearType);
			basicVehicle.setPlateNo(plateNo);
			basicVehicle.setSelfConfigFlag(selfConfigFlag);
			// basicVehicle.setVersion(version);
			basicVehicle.setRemark(remark);
			basicVehicle.setSumChgCompFee(sumChgCompFee);
			basicVehicle.setSumRepairFee(sumRepairFee);
			basicVehicle.setSumMaterialFee(sumMaterialFee);
			basicVehicle.setSumLossFee(sumLossFee);

			sum_loss_fee = sumLossFee; // 保存定损总金额

			basicVehicle.setSumRemnant(sumRemnant);
			basicVehicle.setSumRescueFee(sumRescueFee);
			// Log.e("sumRemnant", "" + sumRemnant);

			basicVehicle.setVehYearType(vehYearTypeBtn.getText().toString());
			if (basicVehicleService.update(basicVehicle))
			{
				// 更新险别
				insureTermCode = SpinnerUtils.getKey(insureTermSp.getSelectedItem().toString(), insureMap);
				insureTerm = insureTermSp.getSelectedItem().toString();
				deflossData.setInsureTermCode(insureTermCode);
				deflossData.setInsureTerm(insureTerm);
				deflossDataService.update(deflossData);

				if (dialog != null)
				{
					dialog.dismiss();
				}
				// result = true;
				// dialog = CustomDialog.show(SmLossMeunAct.this,
				// "信息提示", "暂存成功", "确定", "", null, null);
			}
			else
			{
				errorMsg = "暂存失败！";
			}
			// } else {
			// // ToastDialog.show(SmLossMeunAct.this, "" + errorMsg,
			// // ToastDialog.ERROR);
			//
			// }
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		if (checkVehicleBasic())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean checkVehicleBasic()
	{
		boolean result = false;
		errorMsg = null;
		insureTerm = insureTermSp.getSelectedItem().toString();
		if (StringUtils.isEmpty(vehKindCode))
		{
			errorMsg = "定损车辆种类名称不能为空";
		}
		else if (StringUtils.isNotEmpty(vehYearType) && !MyUtils.complateTime(vehYearType, DateTimeFactory.getInstance().getDat()))
		{
			errorMsg = "初次登记日期不能晚于当天";
		}
		else if (StringUtils.isEmpty(vehCertainCode))
		{

			if (StringUtils.isEmpty(cetainLossType6) || !cetainLossType6.equals("一次性协议定损"))
			{
				// errorMsg = "定损车型编码不能为空";
			}

		}
		else if (StringUtils.isEmpty(vehCertainName))
		{
			errorMsg = "定损车型名称不能为空";
		}
		else if (StringUtils.isEmpty(plateNo))
		{
			errorMsg = "车牌号码不能为空";
		}
		else if (StringUtils.isEmpty(insureTerm))
		{
			errorMsg = "险别不能为空";
		}

		if (StringUtils.isEmpty(errorMsg))
		{
			result = true;
		}
		return result;
	}

	class DateBtnClick implements OnClickListener
	{
		Button dateBtn = null;

		public DateBtnClick(Button dateButton)
		{
			this.dateBtn = dateButton;
		}

		@Override
		public void onClick(View v)
		{
			if (StringUtils.isEmpty(dateBtn.getText().toString()))
			{
				dateBtn.setText(MyUtils.format("yyyy-MM-dd", DateTimeFactory.getInstance().getDat()));
			}
			// if (!lock) {
			int curYear = 2000, curMonth = 1, curDay = 1;
			curYear = StringUtils.toInt(dateBtn.getText().toString());
			String date = dateBtn.getText().toString();
			if (StringUtils.isEmpty(date))
			{
				Calendar cal;
				// 获得日期实例
				cal = Calendar.getInstance();
				// 获取相应属性
				curYear = cal.get(Calendar.YEAR);
				curMonth = cal.get(Calendar.MONTH);
				curDay = cal.get(Calendar.DAY_OF_MONTH);
			}
			else
			{
				try
				{
					curYear = Integer.valueOf(String.valueOf(date).replaceAll("-", "").trim().substring(0, 4));
					curMonth = Integer.valueOf(String.valueOf(date).replaceAll("-", "").trim().substring(4, 6)) - 1;
					curDay = Integer.valueOf(String.valueOf(date).replaceAll("-", "").trim().substring(6, 8));

				}
				catch (Exception e)
				{
					// e.printStackTrace();
					curYear = 2000;
					curMonth = 1;
					curDay = 1;
				}

			}

			dateDialogUtils.dateDialog(LossMenuAct.this, dateBtn, curYear, curMonth, curDay);
		}
	}

	/**
	 * 55***********************************************************************
	 * **************************** 定损换件信息
	 */
	private ILossFitInfoService lossFitInfoService = null;
	private ExpandableListView lossChangeModfiyList = null;
	// private ProgressDialogUtil progressDialog = null;
	private PricePlanService pricePlanService = null;
	private List<GeneralParty> generalPartyList = null;
	private LossChangeItemAdapter lossChangeItemAdapter = null;
	private List<LossFitInfoItem> fitList = null;
	private Button addChangeProjectBtn = null; // 新增换件信息
	private Button definedChangeProjectBtn = null; // 自定义换件信息

	private Button change_SaveBtn = null;// 暂存
	private Button change_BackBtn = null;// 返回

	// private String registNo = null;
	// private String taskId = null;
	// private String vehicleType = null;

	private void setSmChangeItem()
	{
		// 设置视图控件
		setChangeItemView();
		// 加载列表数据
		// loadChangeItemData();

		// if (null != fitList) {
		// lossChangeItemAdapter = new LossChangeItemAdapter(fitList, taskId,
		// this, lossChangeModfiyList, new ChangeItemListener());
		// lossChangeModfiyList.setAdapter(lossChangeItemAdapter);
		// ExpandableUtil
		// .setListViewHeightBasedOnChildren(lossChangeModfiyList);
		// }
		ChangeItemClickListener listener = new ChangeItemClickListener();
		addChangeProjectBtn.setOnClickListener(listener);
		definedChangeProjectBtn.setOnClickListener(listener);
		change_SaveBtn.setOnClickListener(listener);
		change_BackBtn.setOnClickListener(listener);
	}

	/**
	 * 设置视图控件
	 */
	public void setChangeItemView()
	{
		lossChangeModfiyList = (ExpandableListView) smChangeItemView.findViewById(R.id.lossChangeModfiyList);
		addChangeProjectBtn = (Button) smChangeItemView.findViewById(R.id.addChangeProjectBtn);
		definedChangeProjectBtn = (Button) smChangeItemView.findViewById(R.id.definedChangeProjectBtn);
		change_SaveBtn = (Button) smChangeItemView.findViewById(R.id.simple_setloss_vehicle_save);
		change_BackBtn = (Button) smChangeItemView.findViewById(R.id.simple_setloss_vehicle_back);
	}

	/**
	 * 加载列表数据
	 */
	public void loadChangeItemData()
	{
		if (null == lossFitInfoService)
		{
			lossFitInfoService = new LossFitInfoService();
		}
		try
		{
			fitList = lossFitInfoService.getByRegistNo(taskId);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		if (null == fitList)
		{
			fitList = new ArrayList<LossFitInfoItem>();
		}
		lossChangeItemAdapter = new LossChangeItemAdapter(fitList, taskId, this, lossChangeModfiyList, new ChangeItemListener());
		lossChangeModfiyList.setAdapter(lossChangeItemAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(lossChangeModfiyList);
	}

	class ChangeItemListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (v instanceof TextView)
			{
				int tag = (Integer) v.getTag();
				TextView btn = (TextView) v;
				// 修改换件信息
				if (btn.getText().toString().equals(LossMenuAct.this.getResources().getString(R.string.updateBtn)))
				{

					LossFitInfoItem item = fitList.get(tag);
					item.init();
					String defineType = item.getDefineType();
					Intent it = null;
					// 自定义换件信息
					if (defineType.equals("1"))
					{
						it = new Intent(LossMenuAct.this, ChangeProjectdefineAct.class);
					}
					else
					{
						// 换件信息修改
						it = new Intent(LossMenuAct.this, ChangeProjectModfiyAct.class);
					}
					it.putExtra("taskId", taskId);
					it.putExtra("registNo", registNo);
					it.putExtra("lossFitInfoItemId", item.getId());
					LossMenuAct.this.startActivity(it);

				}
				else if (btn.getText().toString().equals(LossMenuAct.this.getResources().getString(R.string.deleteBtn)))
				{
					// 删除换件信息
					if (null == lossFitInfoService)
					{
						lossFitInfoService = new LossFitInfoService();
					}
					try
					{
						if (lossFitInfoService.delete(fitList.get(tag).getId()))
						{
							// onRestart();
							onResume();
							ToastDialog.show(LossMenuAct.this, "删除成功", ToastDialog.ERROR);

						}
						else
						{
							ToastDialog.show(LossMenuAct.this, "删除失败", ToastDialog.ERROR);
						}
					}
					catch (Exception e)
					{
						// e.printStackTrace();
					}
				}
			}
		}
	}

	class ChangeItemClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.addChangeProjectBtn: // 新建换件信息
				progressDialog = new ProgressDialogUtil(LossMenuAct.this);
				progressDialog.show("数据加载中...");
				new Thread()
				{
					public void run()
					{
						Message msg = new Message();
						try
						{
							// loadData();
							// 此处请求接口的返回报文并没有用到
							pricePlanService = new PricePlanService();
							User user = UserCashe.getInstant().getUser();
							generalPartyList = pricePlanService.execute(user.getName(), "44000000"); // user.getDeptNo());支持广州
							if (generalPartyList != null && !generalPartyList.isEmpty())
							{
								msg.obj = generalPartyList;
							}

							msg.what = 1;
						}
						catch (Exception e)
						{
							// e.printStackTrace();
							msg.what = MyUtils.getErrorCode(e.getMessage());
							msg.obj = "" + e.getMessage();
						}
						finally
						{
							changeItemHandler.sendMessage(msg);
						}

					};
				}.start();

				break;
			case R.id.definedChangeProjectBtn:

				Intent it = new Intent(LossMenuAct.this, ChangeProjectdefineAct.class);
				it.putExtra("registNo", registNo);
				it.putExtra("taskId", taskId);
				it.putExtra("lossFitInfoItemId", -1);
				LossMenuAct.this.startActivity(it);

				/*
				 * LossFitInfoItem lossFitInfoItem =
				 * lossFitInfoItemList.get(generalPartyListItemID.get(i));
				 * lossFitInfoItem.setRegistNo(taskId);
				 * 
				 * lossFitInfoItem.setChgCompSetCode(SpinnerUtils.getKey(pricePlanId
				 * ,dicConfMap.get("ChgCompSetCode")));
				 * if(0==lossFitInfoService.save(lossFitInfoItem)){
				 * 
				 * }
				 */
				break;

			case R.id.simple_setloss_vehicle_save:// 暂存
				// 所有的数据己在操作中处理所以这里不需要做暂存动作
				if (null != fitList && !fitList.isEmpty())
				{
					ToastDialog.show(LossMenuAct.this, "暂存成功", ToastDialog.ERROR);
				}
				else
				{
					ToastDialog.show(LossMenuAct.this, "请选择暂存数据", ToastDialog.ERROR);
				}
				break;
			case R.id.simple_setloss_vehicle_back:// 返回

				try
				{
					LossMenuAct.this.finish();
					ActivityManage.getInstance().pop();

				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				break;
			}
		}

	}

	// 用于更新界面 跳转
	private Handler changeItemHandler = new Handler()
	{
		// 0表示失败，1表示成功
		public void handleMessage(Message msg)
		{
			if (progressDialog != null)
			{
				progressDialog.dismiss();
			}
			switch (msg.what)
			{
			case 0:
				ToastDialog.show(LossMenuAct.this, msg.obj + "", ToastDialog.ERROR);
				break;
			case 1:
				List<GeneralParty> generalPartyList = (List<GeneralParty>) msg.obj;
				String str = null;
				if (generalPartyList != null && !generalPartyList.isEmpty())
				{
					for (GeneralParty generalParty : generalPartyList)
					{
						if (null == str)
						{
							str = generalParty.getName();
						}
						else
						{
							str += "|" + generalParty.getName();
						}
					}
				}
				if (StringUtils.isEmpty(str))
				{
					str = "";
				}
				Intent intent = new Intent();
				intent.putExtra("registNo", registNo);
				intent.putExtra("taskId", taskId);
				intent.putExtra("vehicleType", vehicleType);
				intent.putExtra("pricePlan", str);
				intent.setClass(LossMenuAct.this, ChangeQueryAct.class);
				startActivity(intent);
				break;
			case AppConstants.NO_LOGIN_CODE:
				Intent toLogin = getIntent();
				toLogin.setClass(LossMenuAct.this, LoginAct.class);
				startActivity(toLogin);
				try
				{
					LossMenuAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
				}
				break;
			}

		};

	};

	/**
	 * 66***********************************************************************
	 * ******************** 定损维修信息
	 */
	private ILossRepairInfoService lossRepairInfoService = null;
	private List<LossRepairInfoItem> repairList = null;
	private ExpandableListView repairExpList = null;
	private SmRepairItemAdapter adapter4 = null;
	// private String registNo = null;
	// private String taskId = null;
	private Button backBtn4 = null;
	private Button addBtn4 = null;

	private void setSmRepairItem()
	{
		repairExpList = (ExpandableListView) smRepairItemView.findViewById(R.id.lossRepairModfiyList);
		addBtn4 = (Button) smRepairItemView.findViewById(R.id.addBtn);
		backBtn4 = (Button) smRepairItemView.findViewById(R.id.backBtn);
		SmRepairItemListener listener = new SmRepairItemListener();
		addBtn4.setOnClickListener(listener);
		backBtn4.setOnClickListener(listener);

		// loadListData4();
	}

	class SmRepairItemListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.addBtn:
				Intent intent = getIntent();
				intent.setClass(LossMenuAct.this, RepairAddAct.class);
				intent.putExtra("registNo", registNo);
				intent.putExtra("taskId", taskId);
				intent.putExtra("lossRepairItemId", -1);
				startActivity(intent);
				break;
			case R.id.backBtn:
				try
				{
					LossMenuAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
				break;
			}
		}
	}

	private void loadRepairList()
	{
		try
		{
			if (null == lossRepairInfoService)
			{
				lossRepairInfoService = new LossRepairInfoService();
			}
			repairList = lossRepairInfoService.getByRegistNo(taskId);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		if (null != repairList)
		{
			adapter4 = new SmRepairItemAdapter(repairList, taskId, this, repairExpList, new SmRepairListener());
			repairExpList.setAdapter(adapter4);
			ExpandableUtil.setListViewHeightBasedOnChildren(repairExpList);
		}
	}

	class SmRepairListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (v instanceof TextView)
			{
				int tag = (Integer) v.getTag();
				TextView btn = (TextView) v;
				// 修改
				if (btn.getText().toString().equals(LossMenuAct.this.getResources().getString(R.string.updateBtn)))
				{
					LossRepairInfoItem item = repairList.get(tag);
					item.init();
					Intent it = new Intent(LossMenuAct.this, RepairAddAct.class);
					it.putExtra("registNo", registNo);
					it.putExtra("taskId", taskId);
					it.putExtra("lossRepairItemId", item.getId());
					LossMenuAct.this.startActivity(it);

					// 删除
				}
				else if (btn.getText().toString().equals(LossMenuAct.this.getResources().getString(R.string.deleteBtn)))
				{
					if (null == lossRepairInfoService)
					{
						lossRepairInfoService = new LossRepairInfoService();
					}
					try
					{
						if (lossRepairInfoService.delete(repairList.get(tag).getId()))
						{
							ToastDialog.show(LossMenuAct.this, "删除成功", ToastDialog.ERROR);
							// onRestart();
							loadRepairList();
						}
						else
						{
							ToastDialog.show(LossMenuAct.this, "删除失败", ToastDialog.ERROR);
						}
					}
					catch (Exception e)
					{
						// e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 77***********************************************************************
	 * ********** 辅料信息
	 */
	private ILossAssistInfoService lossAssistInfoService;
	// private IThirdPartyService thirdPartyService;
	private List<LossAssistInfoItem> assistInfoList;
	private SimleAssistAdapter simleAssistAdapter;
	private List<Map<String, String>> assistData;
	private Button addAssistBtn;
	private Button addDefineAssistBtn;
	private Button assist_BackBtn;
	private ListView assistListView;

	// private String registNo;
	// private String taskId;

	private void setSmAssistItem()
	{
		setAssistItemView();

		// loadAssistItemData();

		AssistClickListener listener = new AssistClickListener();
		addAssistBtn.setOnClickListener(listener);
		assist_BackBtn.setOnClickListener(listener);
		addDefineAssistBtn.setOnClickListener(listener);
	}

	private void setAssistItemView()
	{
		assist_BackBtn = (Button) smAssistItemView.findViewById(R.id.backBtn);
		assistListView = (ListView) smAssistItemView.findViewById(R.id.lvperson);
		addAssistBtn = (Button) smAssistItemView.findViewById(R.id.addAssistBtn);
		addDefineAssistBtn = (Button) smAssistItemView.findViewById(R.id.addDefineAssistBtn);
	}

	private void loadAssistItemData()
	{
		try
		{
			if (null == lossAssistInfoService)
			{
				lossAssistInfoService = new LossAssistInfoService();
			}
			assistInfoList = lossAssistInfoService.getByRegistNo(taskId);
			if (null != assistInfoList && !assistInfoList.isEmpty())
			{
				assistData = new ArrayList<Map<String, String>>();
				Iterator iter = assistInfoList.iterator();
				while (iter.hasNext())
				{
					LossAssistInfoItem assistInfoItem = (LossAssistInfoItem) iter.next();
					assistInfoItem.init();
					Map<String, String> m = new HashMap<String, String>();
					m.put("id", String.valueOf(assistInfoItem.getId()));
					m.put("MaterialName", MyUtils.convertNull(assistInfoItem.getMaterialName()));
					m.put("MaterialCode", MyUtils.convertNull(assistInfoItem.getMaterialCode()));
					m.put("UnitPrice", String.valueOf(assistInfoItem.getUnitPrice()));
					m.put("Count", String.valueOf(assistInfoItem.getCount()));
					m.put("MaterialFee", String.valueOf(assistInfoItem.getMaterialFee()));
					m.put("InsureTerm", MyUtils.convertNull(assistInfoItem.getInsureTerm()));
					m.put("defineType", assistInfoItem.getDefineType());

					assistData.add(m);
				}
				simleAssistAdapter = new SimleAssistAdapter(this, assistData, R.layout.activity_assist_query_item, new String[]
				{ "MaterialName", "MaterialCode", "UnitPrice", "Count", "MaterialFee", "InsureTerm" }, new int[]
				{ R.id.nameTxt, R.id.codeTxt, R.id.priceTxt, R.id.countTxt, R.id.moneyTxt, R.id.insurTxt }, new int[]
				{ R.id.modfiyBtn, R.id.deleteBtn }, new AssistOnClickListener(), assistListView);
				assistListView.setAdapter(simleAssistAdapter);
				ExpandableUtil.setListViewHeightBasedOnChildren(assistListView);
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * 列表事件处理
	 * 
	 * @author Administrator
	 * 
	 */
	private class AssistOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// 列表按扭事件处理
			if (v instanceof Button)
			{
				if (null != v.getTag())
				{
					int tag = (Integer) v.getTag();
					Map<String, String> mapContent = assistData.get(tag);
					switch (v.getId())
					{
					case R.id.modfiyBtn:// 修改事件

						String defineType = mapContent.get("defineType");
						Intent intent = getIntent();

						if (defineType != null && defineType.equals("1"))
						{
							intent.setClass(LossMenuAct.this, AssistDefineAct.class);
						}
						else
						{
							intent.setClass(LossMenuAct.this, AssistModfiyAct.class);
						}

						intent.putExtra("registNo", registNo);
						intent.putExtra("taskId", taskId);
						intent.putExtra("assistInfoId", StringUtils.toInt(mapContent.get("id")));
						startActivity(intent);

						break;

					case R.id.deleteBtn:// 删除事件
						boolean isDel = false;
						if (null == lossAssistInfoService)
						{
							lossAssistInfoService = new LossAssistInfoService();
						}
						isDel = lossAssistInfoService.delete(Integer.parseInt(mapContent.get("id")));
						if (tag < assistData.size() && isDel)
						{
							assistData.remove(tag);
							simleAssistAdapter.notifyDataSetChanged();
							ExpandableUtil.setListViewHeightBasedOnChildren(assistListView);
							ToastDialog.show(LossMenuAct.this, "已删除", ToastDialog.ERROR);
						}
						else
						{
							ToastDialog.show(LossMenuAct.this, "删除错误", ToastDialog.ERROR);
						}
						break;
					}
				}
			}
		}
	}

	class AssistClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.addAssistBtn:
				if (null == thirdPartyService)
				{
					thirdPartyService = new ThirdPartyService();
				}
				final List<GeneralParty> generList = thirdPartyService.getAssistItem();
				if (null != generList && !generList.isEmpty())
				{
					int size = generList.size();
					String[] arrayFruit = new String[size];
					for (int i = 0; i < size; i++)
					{
						arrayFruit[i] = generList.get(i).getName();
					}
					final String[] showArray = arrayFruit;
					final String no = taskId;
					Dialog dialog = new AlertDialog.Builder(LossMenuAct.this).setTitle("请选择辅料信息").setIcon(R.drawable.ic_launcher).setItems(showArray, new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							try
							{
								GeneralParty generalParty = generList.get(which);
								// 与本地辅料列表进行比较，检查是否有辅料名称相同的信息
								Map<String, String> repeatItem = null;
								if (null != assistData)
								{
									for (Map<String, String> item : assistData)
									{
										repeatItem = null;
										String m1 = item.get("MaterialName");
										String m2 = generalParty.getName();
										if (!StringUtils.isEmpty(m1) && !StringUtils.isEmpty(m2) && m1.equals(m2))
										{
											repeatItem = item;
											break;
										}
									}
								}
								// 如果有相同的信息，则不能添加
								if (null != repeatItem)
								{
									CustomDialog.show(LossMenuAct.this, "", "辅料重复\n" + repeatItem.get("MaterialName"));
								}
								else
								{
									ILossAssistInfoService lossAssistInfoService = new LossAssistInfoService();
									LossAssistInfoItem lossAssistInfoItem = new LossAssistInfoItem();
									lossAssistInfoItem.setRegistNo(no);
									lossAssistInfoItem.setMaterialName(generalParty.getName());
									lossAssistInfoItem.setMaterialCode(generalParty.getId());
									// 添加标志
									lossAssistInfoItem.setDefineType("0");
									if (0 != lossAssistInfoService.save(lossAssistInfoItem))
									{

										LossMenuAct.this.onResume();
									}
									else
									{
										ToastDialog.show(LossMenuAct.this, "添加失败", ToastDialog.ERROR);
									}
								}
							}
							catch (Exception e)
							{
								// e.printStackTrace();
							}
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{

						}
					}).create();
					dialog.show();

				}
				break;
			case R.id.backBtn:
				try
				{
					LossMenuAct.this.finish();
					ActivityManage.getInstance().pop();
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}

				break;
			case R.id.addDefineAssistBtn:
				Intent intent = new Intent();
				intent.setClass(LossMenuAct.this, AssistDefineAct.class);
				intent.putExtra("registNo", registNo);
				intent.putExtra("taskId", taskId);
				intent.putExtra("assistInfoId", 0);
				startActivity(intent);

				break;
			}

		}

	}

	/**
	 * 88***********************************************************************
	 * ****************************** 银行信息
	 */
	private LinearLayout bankInfoLayout;
	private Spinner provinceSpinner;// 开户行省份
	private Spinner citySpinner;// 开户行城市
	private Spinner bankNameSpinner; // 银行开户行
	private EditText bankTypeEdit; // 银行类型

	private AutoCompleteArrayAdapter bankPointAdapter;
	private Button selectBankPointBtn; // 筛选
	private Button checkForBankPoint; // 搜索银行网点
	private Dialog selectBankPointDialog;
	// private AutoCompleteTextView bankPointAutoComplete; //网点查询
	private Spinner bankOutletsSpinner; // 银行网点名称
	private EditText bankNumberEdit; // 银行号

	private EditText accountsNameEdit; // 银行账户名称
	private EditText accountsEdit; // 银行账号
	private EditText mobileEdit; // 电话号码
	private EditText purposeEdit; // 用途
	private RadioGroup cityFlagRadioGroup;// 是否同城
	private RadioGroup priorityFlagRadioGroup;// 汇款模式

	// private String registNo;
	// private String taskId;
	private BankInfo bankInfo;
	private List<BankInfo> bankPointList;
	private InsuredData insuredData;
	// private String errorMsg = "";
	private String priorityFlag = "0";
	private String cityFlag = "0";

	private String provinceId; // 当前省ID

	private void setBankInfo()
	{
		initBankViews();
		initBankDatas();
	}

	private void initBankViews()
	{
		bankInfoLayout = (LinearLayout) smBankInfoView.findViewById(R.id.bankInfoLayout);
		provinceSpinner = (Spinner) smBankInfoView.findViewById(R.id.provinceSpinner);
		citySpinner = (Spinner) smBankInfoView.findViewById(R.id.citySpinner);
		bankNameSpinner = (Spinner) smBankInfoView.findViewById(R.id.bankNameSpinner);
		bankTypeEdit = (EditText) smBankInfoView.findViewById(R.id.bankTypeEdit);
		checkForBankPoint = (Button) smBankInfoView.findViewById(R.id.checkForBankPoint);
		selectBankPointBtn = (Button) smBankInfoView.findViewById(R.id.selectBankPointBtn);
		accountsNameEdit = (EditText) smBankInfoView.findViewById(R.id.accountsNameEdit);
		accountsEdit = (EditText) smBankInfoView.findViewById(R.id.accountsEdit);
		bankOutletsSpinner = (Spinner) smBankInfoView.findViewById(R.id.bankOutletsSpinner);
		bankNumberEdit = (EditText) smBankInfoView.findViewById(R.id.bankNumberEdit);
		mobileEdit = (EditText) smBankInfoView.findViewById(R.id.mobileEdit);
		purposeEdit = (EditText) smBankInfoView.findViewById(R.id.purposeEdit);
		cityFlagRadioGroup = (RadioGroup) smBankInfoView.findViewById(R.id.cityFlagRadioGroup);
		priorityFlagRadioGroup = (RadioGroup) smBankInfoView.findViewById(R.id.priorityFlagRadioGroup);
		// noRTBtn = (RadioButton) smBankInfoView.findViewById(R.id.radio0);
		// yesRTBtn = (RadioButton) smBankInfoView.findViewById(R.id.radio01);
		BankInfoCheckedChangedListener checkedChangeListener = new BankInfoCheckedChangedListener();
		cityFlagRadioGroup.setOnCheckedChangeListener(checkedChangeListener);
		priorityFlagRadioGroup.setOnCheckedChangeListener(checkedChangeListener);
		BankInfoListener listener = new BankInfoListener();
		provinceSpinner.setOnItemSelectedListener(listener);
		citySpinner.setOnItemSelectedListener(listener);
		bankNameSpinner.setOnItemSelectedListener(listener);
		bankOutletsSpinner.setOnItemSelectedListener(listener);

		// 筛选银行网点
		selectBankPointBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				if (bankPointAdapter == null)
				{
					Toast.makeText(LossMenuAct.this, "查询银行网点结果为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				selectBankPointDialog = setBankPointAutoComplete(LossMenuAct.this);
				selectBankPointDialog.show();
			}
		});
		// 查询银行网点
		checkForBankPoint.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (checkBankInfo(false))
				{
					requestForBankInfo();
				}
				else
				{
					Toast.makeText(LossMenuAct.this, "请输入完整省份、城市、开户行！", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	private Dialog setBankPointAutoComplete(Context context)
	{
		Dialog dialog = new Dialog(context, R.style.Theme_ShareDialog);
		dialog.setContentView(R.layout.dialog_keyword_search);
		final EditText editText = (EditText) dialog.findViewById(R.id.sreachWord);
		Button button = (Button) dialog.findViewById(R.id.sreachBtn);
		ListView listView = (ListView) dialog.findViewById(R.id.searchList);
		if (bankPointAdapter != null)
		{
			listView.setAdapter(bankPointAdapter);
		}
		editText.setSingleLine();
		editText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f));
		editText.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable arg0)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
			{

			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
			{
				bankPointAdapter.getFilter().filter(editText.getText().toString());
			}

		});
		button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				hideKeyboard();
				bankPointAdapter.getFilter().filter(editText.getText().toString());
			}

		});
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				try
				{
					String key = SpinnerUtils.getKey((String) bankPointAdapter.getItem(arg2), getBankPoint(bankPointList));
					SpinnerUtils.setSpinnerData(LossMenuAct.this, bankOutletsSpinner, getBankPoint(bankPointList), key, "");
					selectBankPointDialog.dismiss();
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
			}

		});
		return dialog;
	}

	private void initBankDatas()
	{
		InsuredDataService insuredDataService = new InsuredDataService();
		bankPointList = new ArrayList<BankInfo>();
		BankInfoService bankInfoService = new BankInfoService();
		try
		{
			bankInfo = bankInfoService.getByTaskId(taskId);
			if (null == bankInfo)
			{
				bankInfo = new BankInfo();
			}
			setInitData(bankInfo);
			insuredData = insuredDataService.getByTaskId(taskId);
			if (insuredData == null)
				insuredData = new InsuredData();
			insuredData.init();
			// 将银行账号与电话号码改成被保人信息
			if (StringUtils.isEmpty(bankInfo.getAccountsName()))
			{
				accountsNameEdit.setText(insuredData.getInsuredName());
			}
			else
			{
				accountsNameEdit.setText(bankInfo.getAccountsName());
			}
			if (StringUtils.isEmpty(bankInfo.getMobile()))
			{
				mobileEdit.setText(insuredData.getPhoneNumber());
			}
			else
			{
				mobileEdit.setText(bankInfo.getMobile());
			}

		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	private Map<String, String> provinceMap;

	private Map<String, String> getProvince()
	{
		if (provinceMap == null)
		{
			provinceMap = dataMap.get("BankProvinceCode");
		}
		return provinceMap;
	}

	private Map<String, Map<String, String>> cityMap;
	private Map<String, String> currentCityMap;

	private Map<String, String> getCity(String province)
	{
		if (cityMap == null)
		{
			cityMap = new HashMap<String, Map<String, String>>();
			Map<String, String> cities = dataMap.get("BankCityCode"); // <4000|4401234,深圳市>
			Iterator<String> iter = cities.keySet().iterator();
			Map<String, String> city;
			while (iter.hasNext())
			{
				String key = (String) iter.next();
				String value = cities.get(key);
				// 省份ID
				String provinceId = key.substring(0, 2);
				city = new HashMap<String, String>();
				String cityId = key;
				city.put(cityId, value);
				cityMap.put(key, city);
			}
		}
		Iterator<String> iter = cityMap.keySet().iterator();
		currentCityMap = new HashMap<String, String>();
		while (iter.hasNext())
		{
			String key = (String) iter.next();
			Map<String, String> currentCity = cityMap.get(key);
			String provinceId = key.substring(0, 2);
			String cityId = key;
			Map<String, String> city;
			String provinceTmp = province;
			if (province != null && province.length() > 2)
			{
				provinceTmp = province.substring(0, 2);
			}
			if (provinceId.equals(provinceTmp))
			{
				currentCityMap.put(cityId, currentCity.get(cityId));
			}
		}
		return currentCityMap;
	}

	// private Map<String, String> bankToCodeMap;
	// private Map<String,String> getBankToCode() {
	// if (bankToCodeMap == null) {
	// bankToCodeMap = dataMap.get("CodeToBank");
	// }
	// return bankToCodeMap;
	// }

	private String[] bankPointSelect;

	private String[] getBankPointSelect(List<BankInfo> list)
	{
		if (isNewBankList || bankPointSelect == null)
		{
			bankPointSelect = new String[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				bankPointSelect[i] = list.get(i).getBankOutlets();
			}
		}
		return bankPointSelect;
	}

	private Map<String, String> bankPointMap;
	private boolean isNewBankList = false;

	private Map<String, String> getBankPoint(List<BankInfo> bankPointList)
	{
		if (isNewBankList)
		{
			bankPointMap = new HashMap<String, String>();
			for (BankInfo info : bankPointList)
			{
				bankPointMap.put(info.getBankNumber(), info.getBankOutlets());
			}
			isNewBankList = false;
		}
		return bankPointMap;
	}

	private class BankInfoListener implements OnItemSelectedListener
	{
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			switch (arg0.getId())
			{
			case R.id.provinceSpinner:
				// 触发省份控件,初始化城市控件
				provinceId = SpinnerUtils.getKey(provinceSpinner.getSelectedItem().toString(), getProvince());
				String city = "";
				if (provinceId.equals(bankInfo.getProvince()))
				{
					city = bankInfo.getCity();
				}
				SpinnerUtils.setSpinnerData(LossMenuAct.this, citySpinner, getCity(provinceId), city, "");
				break;
			case R.id.citySpinner:
				// 清空银行网点
				SpinnerUtils.setSpinnerData(LossMenuAct.this, bankOutletsSpinner, new HashMap<String, String>(), "", "");
				bankNumberEdit.setText("");
				if (null != bankPointAdapter)
					bankPointAdapter = null;
				break;
			case R.id.bankNameSpinner:
				bankTypeEdit.setText(SpinnerUtils.getKey(bankNameSpinner.getSelectedItem().toString(), DictCashe.getBankList(LossMenuAct.this)));
				SpinnerUtils.setSpinnerData(LossMenuAct.this, bankOutletsSpinner, new HashMap<String, String>(), "", "");
				if (checkBankInfo(false))
				{
					requestForBankInfo();
				}
				break;
			case R.id.bankOutletsSpinner:
				if (null != bankPointList && bankPointList.size() != 0)
				{
					bankNumberEdit.setText(SpinnerUtils.getKey(bankOutletsSpinner.getSelectedItem().toString(), getBankPoint(bankPointList)));
				}
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{

		}

	}

	private void requestForBankInfo()
	{
		progressDialog = new ProgressDialogUtil(this);
		progressDialog.show("银行网点查询中...");
		new Thread()
		{

			@Override
			public void run()
			{
				super.run();
				// 设置需要搜索的银行信息
				BankInfo info = new BankInfo();
				info.setProvince(SpinnerUtils.getKey(provinceSpinner.getSelectedItem().toString(), getProvince()));
				info.setCity(SpinnerUtils.getKey(citySpinner.getSelectedItem().toString(), getCity(provinceId)));
				info.setBankName(bankNameSpinner.getSelectedItem().toString());
				info.setBankType(bankTypeEdit.getText().toString());
				info.setBankNumber("");
				info.setBankOutlets("");
				BankInfoService bankInfoService = new BankInfoService();
				try
				{
					// 请求银行列表
					bankPointList = bankInfoService.requestForBankPoint(info);
					if (null != bankPointList && bankPointList.size() != 0)
					{
						isNewBankList = true;
						bankPointAdapter = new AutoCompleteArrayAdapter(LossMenuAct.this, android.R.layout.simple_list_item_1, getBankPointSelect(bankPointList));
						Message msg = new Message();
						msg.what = 1;
						bank_Handler.sendMessage(msg);
					}
					else
					{
						isNewBankList = false;
						bankPointAdapter = null;
						bank_Handler.sendEmptyMessage(0);
					}

				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
			}

		}.start();
	}

	Handler bank_Handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (progressDialog != null)
			{
				progressDialog.dismiss();
			}
			switch (msg.what)
			{
			case 0:
				if (null == bankPointAdapter || bankPointAdapter.isEmpty())
				{
					Toast.makeText(LossMenuAct.this, "查询银行网点结果为空！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(LossMenuAct.this, "无法获取网点信息", Toast.LENGTH_SHORT).show();
				}
				break;
			case 1:
				SpinnerUtils.setSpinnerData(LossMenuAct.this, bankOutletsSpinner, getBankPoint(bankPointList), bankInfo.getBankNumber(), "");
				bankNumberEdit.setText(bankInfo.getBankNumber());
				// bankPointAdapter = new
				// AutoCompleteArrayAdapter(SmLossMeunAct.this,android.R.layout.simple_dropdown_item_1line,getBankPoints((List<BankInfo>)msg.obj));
				// bankPointAutoComplete.setAdapter(bankPointAdapter);
				break;
			}
		}

	};

	// 初始化数据
	private void setInitData(BankInfo info)
	{
		info.init();
		// 触发省份控件和银行名称控件的onItemSelectedListener事件
		SpinnerUtils.setSpinnerData(this, provinceSpinner, getProvince(), info.getProvince(), "");
		SpinnerUtils.setSpinnerData(LossMenuAct.this, citySpinner, getCity(info.getProvince()), info.getCity(), "");
		SpinnerUtils.setSpinnerData(this, bankNameSpinner, DictCashe.getBankList(LossMenuAct.this), info.getBankName());
		accountsNameEdit.setText(info.getAccountsName());
		accountsEdit.setText(info.getAccounts());
		mobileEdit.setText(info.getMobile());
		purposeEdit.setText(info.getPurpose());
		if (info.getCityFlag().equals("1"))
		{
			RadioButton btn = (RadioButton) cityFlagRadioGroup.getChildAt(1);
			btn.setChecked(true);
		}
		else
		{
			RadioButton btn = (RadioButton) cityFlagRadioGroup.getChildAt(0);
			btn.setChecked(true);
		}
		if (info.getPriorityFlag().equals("1"))
		{
			RadioButton btn = (RadioButton) priorityFlagRadioGroup.getChildAt(1);
			btn.setChecked(true);
		}
		else
		{
			RadioButton btn = (RadioButton) priorityFlagRadioGroup.getChildAt(0);
			btn.setChecked(true);
		}
	}

	class BankInfoCheckedChangedListener implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1)
		{
			switch (arg0.getId())
			{
			case R.id.cityFlagRadioGroup:
				switch (arg1)
				{
				case R.id.radio0:
					cityFlag = "0";
					break;
				case R.id.radio1:
					cityFlag = "1";
					break;
				}
				break;
			case R.id.priorityFlagRadioGroup:
				switch (arg1)
				{
				case R.id.radio0:
					priorityFlag = "0";
					break;
				case R.id.radio1:
					priorityFlag = "1";
					break;
				}
				break;
			}
		}
	}

	/**
	 * 保存银行信息
	 * 
	 * @return
	 */
	private boolean saveSmBankInfo()
	{
		boolean result = false;
		try
		{
			// 如果是快赔，则保存银行信息
			if (quickClaimFlag.equals("1"))
			{
				bankInfo = new BankInfo();
				bankInfo.setRegistNo(registNo);
				bankInfo.setTask_id(taskId);
				bankInfo.setProvince(SpinnerUtils.getKey(provinceSpinner.getSelectedItem().toString(), getProvince()));
				bankInfo.setCity(SpinnerUtils.getKey(citySpinner.getSelectedItem().toString(), getCity(provinceId)));
				bankInfo.setBankType(bankTypeEdit.getText().toString());
				bankInfo.setBankName(bankNameSpinner.getSelectedItem().toString());
				bankInfo.setBankNumber(bankNumberEdit.getText().toString());
				bankInfo.setBankOutlets(bankOutletsSpinner.getSelectedItem().toString());

				bankInfo.setAccountsName(accountsNameEdit.getText().toString());
				bankInfo.setAccounts(accountsEdit.getText().toString());
				bankInfo.setMobile(mobileEdit.getText().toString());
				bankInfo.setPriorityFlag(priorityFlag);
				bankInfo.setCityFlag(cityFlag);
				bankInfo.setPurpose(purposeEdit.getText().toString());
				BankInfoService bankInfoService = new BankInfoService();
				if (!bankInfoService.save(bankInfo) && !bankInfoService.update(bankInfo))
				{
					errorMsg = "暂存失败！";
				}
			}
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		if (checkBankInfo(true))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean checkBankInfo(boolean checkAll)
	{
		boolean result = false;
		if (checkAll)
		{
			if (StringUtils.isEmpty(accountsNameEdit.getText().toString()))
			{
				errorMsg = "银行账户名称不能为空！";
			}
			else if (StringUtils.isEmpty(accountsEdit.getText().toString()))
			{
				errorMsg = "银行账号不能为空！";
			}
			else if (bankOutletsSpinner.getSelectedItem().toString().equals("请选择"))
			{
				errorMsg = "银行网点名称不能为空！";
			}
			else if (StringUtils.isEmpty(bankNumberEdit.getText().toString()))
			{
				errorMsg = "银行网点不能为空！";
			}
			else if (StringUtils.isEmpty(mobileEdit.getText().toString()))
			{
				errorMsg = "电话号不能为空！";
			}
			else
			{
				result = true;
			}
		}
		if (provinceSpinner.getSelectedItem().toString().equals("请选择"))
		{
			errorMsg = "开户行省份不能为空！";
		}
		else if (citySpinner.getSelectedItem().toString().equals("请选择"))
		{
			errorMsg = "开户行城市不能为空！";
		}
		else if (bankNameSpinner.getSelectedItem().toString().equals("请选择"))
		{
			errorMsg = "银行名称不能为空！";
		}
		else
		{
			result = true;
		}
		return result;
	}

	/**
	 * 99*********************************************************************
	 * 任务提交
	 */
	private Button uploadBtn = null;
	private Button bloothPritlnBtn = null;
	private Button chagePCBtn = null;

	private void setTaskSubmit()
	{
		uploadBtn = (Button) smTaskSubmitView.findViewById(R.id.upload_setloss_btn);
		bloothPritlnBtn = (Button) smTaskSubmitView.findViewById(R.id.loss_bluetoothPrintBtn);
		chagePCBtn = (Button) smTaskSubmitView.findViewById(R.id.chagePCBtn);
		TaskSubmitOnClickListener listener = new TaskSubmitOnClickListener();
		uploadBtn.setOnClickListener(listener);
		bloothPritlnBtn.setOnClickListener(listener);
		chagePCBtn.setOnClickListener(listener);
	}

	private Dialog mediaDialog = null;

	public void isExistImage()
	{
		try
		{
			imageDatas = database.select("imageCenter", "reportNo=?", new String[]
			{ taskId }, "createDate");
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	// 数据上传
	public void uploadData()
	{
		saveDialog = CustomDialog.show(LossMenuAct.this, "信息提示", "定损信息是否录入齐全，确定提交?", new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					executeLossUpdate(); // 数据提交
					saveDialog.dismiss();
				}
				catch (Exception e)
				{
					// e.printStackTrace();
				}
			}
		}, new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				saveDialog.dismiss();
			}
		});
	}

	class TaskSubmitOnClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.upload_setloss_btn:// 定损信息更新
				progressDialog = new ProgressDialogUtil(LossMenuAct.this);
				progressDialog.show("加载中，请稍后...");
				database.asyncSelect(taskId, new DatabaseCallback()
				{

					@Override
					public void post(List<ImageCenterBean> list)
					{
						if (progressDialog != null)
						{
							progressDialog.dismiss();
						}
						imageDatas = list;
						if (imageDatas == null || imageDatas.size() == 0)
						{
							mediaDialog = CustomDialog.show(LossMenuAct.this, "信息提示", "您还未现场拍照，是否需要拍照?", "需要", "不需要", new OnClickListener()
							{

								@Override
								public void onClick(View arg0)
								{
									onPageSelected(1); // 跳转到现场拍照
									mediaDialog.dismiss();
								}
							}, new OnClickListener()
							{

								@Override
								public void onClick(View arg0)
								{
									uploadData();
									mediaDialog.dismiss();
								}
							});
						}
						else
						{
							// 是否上传图片
							for (ImageCenterBean bean : imageDatas)
							{
								if (bean.getIsUpload().equals("0"))
								{
									CustomDialog.show(LossMenuAct.this, "信息提示", "图片未上传，请先上传图片!");
									return;
								}
							}
							uploadData(); // 保存数据
						}
					}
				});

				break;
			case R.id.loss_bluetoothPrintBtn:// 打印
				/*
				 * if (Double.valueOf(sumLossFeeExt.getText().toString()) >
				 * 5000) { // 总金额是否小于等于5000 CustomDialog.show(LossMenuAct.this,
				 * "信息提示", "案件总金额必须小于等于5000元"); return; }
				 */
				bluetoothPrint();

				break;
			case R.id.chagePCBtn:// 转为PC处理
				if (dialog != null)
				{
					dialog.dismiss();
				}
				dialog = CustomDialog.show(LossMenuAct.this, "信息提示", "是否申请电脑处理？", "是", "否", new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
						changePC();// 确定就申请电脑改派
					}
				}, new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						dialog.dismiss();
					}
				});
				break;
			}
		}

	}

	private void bluetoothPrint()
	{
		printInfoService = new PrintInfoService();
		printContext = printInfoService.lossVoucher(this, null, taskId, reportorMobile);
		TestData.setTitle("\n");
		TestData.setContent(printContext);
		Intent intent2 = new Intent(LossMenuAct.this, BluetoothActivity.class);
		intent2.putExtra("taskId", taskId);
		intent2.putExtra("registNo", registNo);
		intent2.putExtra("registId", registId);
		// startActivity(intent2);
		final int LOSS = 10008;
		startActivityForResult(intent2, LOSS);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		setViewPagerInfo();
		initVehicle();
		loadVehicleBasicData();// 2、定损车辆信息
		loadChangeItemData(); // 5、定损换件
		loadRepairList(); // 6、维修信息
		loadAssistItemData(); // 7、辅料信息
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		MediaSimpleAdapter.selectItem = -1;
		Systemtime = "";
	}

	@Override
	public void onPageScrollStateChanged(int arg0)
	{

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2)
	{

	}

	@Override
	public void onPageSelected(int toIndex)
	{
		Log.i("SmLossMeunAct", "toIndex : " + toIndex + " currentPage : " + currentPage);
		try
		{
			switch (currentPage)
			{
			case 2:
				saveSmVehicleBasic();
				break;
			case 3:
				saveSmLossBasic();
				saveSmLossVehicle();
				break;
			case 7:
				saveSmLossBasic();
				saveSmBankInfo();
				break;
			}
			if (toIndex > currentPage)
			{
				String errorMsg = null;
				switch (toIndex)
				{
				case 0:
					// errorMsg = basicFinish();
					break;
				case 1:
					// errorMsg = vehicleFinish();
					break;
				default:
					// 未定型
					if (views.get(2) == vehicleTypeQueryView && toIndex >= 3)
					{
						// onPageSelected(2);
						viewPager.setCurrentItem(2);
						CustomDialog.show(this, "提示！", "请先对车辆定型！");
						return;
					}
					if ((!checkVehicleType() && currentPage >= 2))
					{
						// onPageSelected(2);
						viewPager.setCurrentItem(2);
						CustomDialog.show(this, "提示！", "请先对车辆定型！");
						return;
					}
					else
					{ // 已定型，则检查险别信息
						if (insureTermSp.getSelectedItem().toString().equals("请选择") && currentPage >= 2)
						{
							// onPageSelected(2);
							viewPager.setCurrentItem(2);
							CustomDialog.show(this, "提示！", "请先选择险别！");
							return;
						}
						
						else if (repairFactoryTypeSp.getSelectedItem().toString().equals("请选择") && currentPage >= 3)
						{
							// onPageSelected(3);
							viewPager.setCurrentItem(3);
							CustomDialog.show(this, "提示！", "请先选择修理厂类型！");
							return;
						}
						else if (vehKindCodeSp.getSelectedItem().toString().equals("请选择") && toIndex > 2)
						{
							viewPager.setCurrentItem(2);
							CustomDialog.show(this, "提示！", "请先选择车辆种类名称");
							return;
						}
						else if(firtFlag ==1  && currentPage >= 2)
						{
							String frameNumber = frameNumberExt.getText().toString().trim().toUpperCase();// 车架号／VIN：
							if (frameNumber.equals(""))
							{
								viewPager.setCurrentItem(2);
								CustomDialog.show(this,"提示！", "车架号／VIN不能为空");
								return;
							}
							else 
							{
								int len = frameNumber.length();
								if(len != 17)
								{
									viewPager.setCurrentItem(2);
									CustomDialog.show(this, "提示！", "涉案车辆车架号码/VIN码能由17位（含）以数字或字母组成！");
									return;
								}
								else if(frameNumber.contains("I") || frameNumber.contains("O") ||frameNumber.contains("Q"))
								{
									viewPager.setCurrentItem(2);
									CustomDialog.show(this, "提示！", "涉案车辆车架号码/VIN码只能由大写英文字母（I,O,Q除外）和数字组成！");
									return;
								}
/*								else
								{
									checkVin();
								}
*/							}

						}
					}
					break;

				}
				if (StringUtils.isNotEmpty(errorMsg))
				{
					CustomDialog.show(this, "", errorMsg);
					toIndex = currentPage;
				}
			}

			// viewPager.setCurrentItem(toIndex, true);
			// viewPager.setOnPageChangeListener(this);
			setRadioButton(toIndex);
			// RadioButton radio = (RadioButton) radioGroup.getChildAt(toIndex);
			// radioGroup.setOnCheckedChangeListener(null);
			// radio.setChecked(true);
			// radioGroup.setOnCheckedChangeListener(this);
			currentPage = toIndex;
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * 提交更新定损接口
	 */
	public void checkVin()
	{
		new Thread()
		{
			public void run()
			{
				Message msg = new Message();
				try
				{
					HeartbeatService heart = new HeartbeatService();
					String frameNumber = frameNumberExt.getText().toString().trim().toUpperCase();// 车架号／VIN：
					Business business =heart.requestTaskVin(taskId, registNo, licenseNo, frameNumber);
					business.getResponseCode();
					msg.what =1;
	
				}
				catch (Exception e)
				{
					msg.what = 0;//MyUtils.getErrorCode(e.getMessage());
					if (StringUtils.isNotEmpty(e.getMessage()))
					{
						msg.obj = e.getMessage();
					}
					else
					{
						msg.obj = "校验VIN码失败！";
						//LogRecoder.wirteException(e);
					}
				}
				finally
				{
					
					handler.sendMessage(msg);
					
				}
			}
		}.start();
	}
	
	
	private void setRadioButton(int toIndex)
	{
		radioGroup.getChildAt(0).setBackgroundResource(R.drawable.sm_step_highlight_01);
		radioGroup.getChildAt(1).setBackgroundResource(R.drawable.sm_step_highlight_02);
		radioGroup.getChildAt(2).setBackgroundResource(R.drawable.sm_step_highlight_03);
		radioGroup.getChildAt(3).setBackgroundResource(R.drawable.sm_step_highlight_04);
		radioGroup.getChildAt(4).setBackgroundResource(R.drawable.sm_step_highlight_05);
		radioGroup.getChildAt(5).setBackgroundResource(R.drawable.sm_step_highlight_06);
		radioGroup.getChildAt(6).setBackgroundResource(R.drawable.sm_step_highlight_07);
		radioGroup.getChildAt(7).setBackgroundResource(R.drawable.sm_step_highlight_08);
		// 保单详情
		radioGroup.getChildAt(8).setBackgroundResource(R.drawable.sm_step_highlight_09);
		RadioButton radio = (RadioButton) radioGroup.getChildAt(toIndex);
		radio.setChecked(true);
		for (int i = 0; i < radioGroup.getChildCount(); i++)
		{
			switch (toIndex)
			{
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
			case 8:
				radio.setBackgroundResource(R.drawable.sm_step_09);
				break;
			}
		}
	}

	@Override
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

	/**
	 * 隐藏键盘
	 */
	private void hideKeyboard()
	{
		// 得到InputMethodManager的实例
		if (imm.isActive())
		{
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
	}

	private Handler isquick = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:

				break;

			case 1:

				RadioButton radio = (RadioButton) quickClaimRadioGroup.getChildAt(0);
				radio.setChecked(true);
				quickClaimFlag = "0";
				bankInfoLayout.setVisibility(View.GONE);
				break;
			}
		};
	};

}