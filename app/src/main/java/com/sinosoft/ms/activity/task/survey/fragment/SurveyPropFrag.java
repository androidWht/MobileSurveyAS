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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.survey.SurveyAddPropAct;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.CarDriverData;
import com.sinosoft.ms.model.CarLossData;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.PropDetailData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.PropVehicleInvolvedAdapter;
import com.sinosoft.ms.utils.adapter.SmVehicleInvolvedAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.PropDataDatabase;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：物损
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:24:52
 */

public class SurveyPropFrag extends Fragment  implements OnClickListener{


	// 添加标志
	private static final int ADD_PROP_VEHICLE_CODE = 4443;
	
	private View view;
	private ExpandableListView vehicleInvolvedList;
	private Button vehicleAddBtn;	
	// 车辆列表
	private List<CarData> carDatas;	
	private List<PropData> propDatas;
	private PropData propData;	
	
	private PropVehicleInvolvedAdapter propAdapter;
	private PropAdapterListener propAdapterListener;	
	// 对话框
	private Dialog dialog;	
	
	
	
	private LinearLayout lossAmount_ly;
	private EditText lossAmountExt;
	private Spinner isLossAmountSp;


	// 报案号
	private String registNo;
	private int index;
	private String lossTypeStr = "";
	private String lossParty;
	private List<String> isLossList = new ArrayList<String>();

	// 查勘基本信息
	private SurveyTreatmentDatabase2 database;

	// 数据字典
	private Map<String, Map<String, String>> dataMap;
	private Map<String, Map<String, String>> localItemConf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 获取查勘基本信息
		database = SurveyTreatmentDatabase2.getInstance();
		// 获取数据字典
		///dataMap = DictCashe.getInstant().getDict();
		///localItemConf = AppConstants.getLocalItemConf();
		// 设置监听
		propAdapterListener = new PropAdapterListener();		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_survey_prop_item, container, false);
		return view;

		
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}

	public void initView(View vehicleView) {
		vehicleInvolvedList = (ExpandableListView) vehicleView.findViewById(R.id.vehicleInvolvedListProp);
		vehicleInvolvedList.setEnabled(false);
		vehicleAddBtn = (Button) vehicleView.findViewById(R.id.vehicle_add_Prop_btn);
		vehicleAddBtn.setOnClickListener(this);
	}

	/**
	 * 设置财产损失数据
	 */
	public void initData() {
		propDatas = database.getProDatas();
		carDatas = database.getCarDatas();
		if (propDatas == null)
		{
			propDatas = new ArrayList<PropData>();

		}
		propAdapter = new PropVehicleInvolvedAdapter(propDatas, getActivity(), propAdapterListener);
		vehicleInvolvedList.setAdapter(propAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
		
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.vehicle_add_Prop_btn:
			// 点击增物损按钮
			startForAddVehicle();
			break;
		}
	}
	// 添加三者车
	private void startForAddVehicle()
	{
		PropData prop = new PropData();
		prop.setRegistNo(registNo);
		prop.setCheckDate("6");//车上物
		
		String no = "1000";
		int tmpNo =1000;
		
		if (propDatas != null && propDatas.size()>0){
			for(int i=0;i<propDatas.size();i++){
				String relatePhoneNum = propDatas.get(i).getRelatePhoneNum();
				if(StringUtils.isNotEmpty(relatePhoneNum)){
					int tmp = Integer.valueOf(relatePhoneNum);
					if(tmp>=tmpNo)
						tmpNo=tmp+1;
				}
			}
			
		}
		prop.setRelatePhoneNum(String.valueOf(tmpNo));

		propDatas.add(prop);
		propAdapter = new PropVehicleInvolvedAdapter(propDatas, getActivity(), propAdapterListener);
		vehicleInvolvedList.setAdapter(propAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
	}
	

	class PropAdapterListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			Button btn = (Button) arg0;
			int index = (Integer) btn.getTag();
			propData = propDatas.get(index);
			String btnText = btn.getText().toString();
			// 如果点击了处理按钮
			if (btnText.equals("处理"))
			{
				if (propDatas != null && propDatas.size() > index)
				{
					Intent it = new Intent(getActivity(), SurveyAddPropAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bean", propData);
					it.putExtra("item", bundle);
					startActivityForResult(it, ADD_PROP_VEHICLE_CODE);

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
						String id = propData.getId();
						if (StringUtils.isNotEmpty(id))
						{
							PropDataDatabase database = new PropDataDatabase(getActivity());
							database.deletePropDataById(id, propData.getRegistNo());
						}
						propDatas.remove(propData);
						propAdapter = new PropVehicleInvolvedAdapter(propDatas, getActivity(), propAdapterListener);
						vehicleInvolvedList.setAdapter(propAdapter);
						ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
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
		if (requestCode == ADD_PROP_VEHICLE_CODE && null != data)
		{
			// 插物损
			addVehicle(data);
		}
	}

	private void addVehicle(Intent data)
	{
		Bundle bundle = data.getBundleExtra("item");
		PropData propDataSave = (PropData) bundle.getSerializable("propData");
		
		propDatas.remove(propData);
		propData = propDataSave;
		propDatas.add(propData);


		propAdapter = new PropVehicleInvolvedAdapter(propDatas, getActivity(), propAdapterListener);
		vehicleInvolvedList.setAdapter(propAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
	}	
	
	public void refreshData(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 设置财产损失数据
	 */
	public List<PropData> getPropData() {
		if (null != view)
		{
			return propDatas;
		}
		else
		{
			return null;
		}	
	}
	/**
	 * 车辆信息验证
	 * 
	 * @return
	 */
	public String propVerify()
	{
		for (PropData propData : propDatas)
		{
			if (null != propData)
			{
				propData.init();
				String str = "物损:";
				
				
				String checkPropId = propData.getCheckDate();
				String lossParty =propData.getLossParty();
				String reserveFlag = propData.getReserveFlag();
				String checkSite = propData.getCheckSite();
				String lossFee =propData.getSumLossFee();
				String relatePersonName = propData.getRelatePersonName();
				String rescueInfo =propData.getRescueInfo();				
				if(StringUtils.isEmpty(checkPropId))
				{	
					str+="损失类别不能空";
					return str;
				}
				else if(StringUtils.isEmpty(lossParty))
				{	
					str+="损失名称不能空";
					return str;
				}				
				else if(StringUtils.isEmpty(reserveFlag))
				{	
					str+="险别不能空";
					return str;
				}
				else if(StringUtils.isEmpty(checkSite))
				{	
					str+="费用名称不能空";
					return str;
				}	
				else if(StringUtils.isEmpty(relatePersonName))
				{	
					str+="物损属性";
					return str;
				}					
			}
		}
		return "";
	}
	
}
