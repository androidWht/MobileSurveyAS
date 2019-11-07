package com.sinosoft.ms.activity.task.survey.fragment;

import java.util.ArrayList;
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
import com.sinosoft.ms.activity.task.survey.SurveyAddPersonAct;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.adapter.ExpandableUtil;
import com.sinosoft.ms.utils.adapter.PersonVehicleInvolvedAdapter;
import com.sinosoft.ms.utils.component.CustomDialog;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.component.ToastDialog;
import com.sinosoft.ms.utils.db.DictCashe;
import com.sinosoft.ms.utils.db.PersonDataDatabase;
import com.sinosoft.ms.utils.db.SurveyTreatmentDatabase2;

/**
 * 系统名：移动查勘定损 子系统名：物损
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author hijack
 * @createTime 下午4:24:52
 */

public class SurveyPersonFrag extends Fragment  implements OnClickListener{


	// 添加标志
	private static final int ADD_PROP_VEHICLE_CODE = 4445;
	
	private View view;
	private ExpandableListView vehicleInvolvedList;
	private Button vehicleAddBtn;	
	// 车辆列表
	private List<CarData> carDatas;	
	//private List<PropData> propDatas;
	//private PropData propData;	

	
	private List<PersonData> personDatas;
	private PersonData personData;
	
	
	private PersonAdapterListener personAdapterListener;	
	private PersonVehicleInvolvedAdapter personAdapter;
	
	// 对话框
	private Dialog dialog;	

	// 报案号
	private String registNo;

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
		dataMap = DictCashe.getInstant().getDict();
		localItemConf = AppConstants.getLocalItemConf();
		// 设置监听
		personAdapterListener = new PersonAdapterListener();		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_survey_person_item, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		initData();
	}

	public void initView(View vehicleView) {
		vehicleInvolvedList = (ExpandableListView) vehicleView.findViewById(R.id.vehicleInvolvedListPerson);
		vehicleInvolvedList.setEnabled(false);
		vehicleAddBtn = (Button) vehicleView.findViewById(R.id.vehicle_add_Person_btn);
		vehicleAddBtn.setOnClickListener(this);
	}

	/**
	 * 设置财产损失数据
	 */
	public void initData() {
		//propDatas = database.getProDatas();
		personDatas = database.getPersonDatas();
		carDatas = database.getCarDatas();
		if (personDatas == null)
		{
			personDatas = new ArrayList<PersonData>();

		}
		personAdapter = new PersonVehicleInvolvedAdapter(personDatas, getActivity(), personAdapterListener);
		vehicleInvolvedList.setAdapter(personAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
		
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.vehicle_add_Person_btn:
			// 点击增人伤按钮
			startForAddVehicle();
			break;
		}
	}
	// 添加人伤
	private void startForAddVehicle()
	{
		PersonData person = new PersonData();
		
		person.setRegistNo(registNo);
		
		String no = "50";
		int tmpNo =50;
		
		if (personDatas != null && personDatas.size()>0){
			for(int i=0;i<personDatas.size();i++){
				String relateNo = personDatas.get(i).getSevereNum();
				if(StringUtils.isNotEmpty(relateNo)){
					int tmp = Integer.valueOf(relateNo);
					if(tmp>=tmpNo)
						tmpNo=tmp+1;
				}
			}
		}
		person.setSevereNum(String.valueOf(tmpNo));
		person.setCreateOwner("3");//标的类型
		person.setDeathNum("0"); //默认非死亡
		///List<PersonDetailData> personDetailDataList =  new ArrayList<PersonDetailData>();
		
		///PersonDetailData personDetailData = new PersonDetailData();
		///personDetailData.setPersonPayType("3");   //"3", "本车司机"
		///personDetailData.setTreatType("0");
		///personDetailDataList.add(personDetailData);
		///person.setPersonDetailData(personDetailDataList);

		personDatas.add(person);
		personAdapter = new PersonVehicleInvolvedAdapter(personDatas, getActivity(), personAdapterListener);
		vehicleInvolvedList.setAdapter(personAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
		
	}
	class PersonAdapterListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			Button btn = (Button) arg0;
			int index = (Integer) btn.getTag();
			personData = personDatas.get(index);
			String btnText = btn.getText().toString();
			// 如果点击了处理按钮
			if (btnText.equals("处理"))
			{
				if (personDatas != null && personDatas.size() > index)
				{
					Intent it = new Intent(getActivity(), SurveyAddPersonAct.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("bean", personData);
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
						String personDataId = personData.getPersonDataId();
						if (StringUtils.isNotEmpty(personDataId))
						{
							PersonDataDatabase database = new PersonDataDatabase(getActivity());
							database.deletePersonDataById( personData.getRegistNo(),personDataId);
						}
						personDatas.remove(personData);
						personAdapter = new PersonVehicleInvolvedAdapter(personDatas, getActivity(), personAdapterListener);
						vehicleInvolvedList.setAdapter(personAdapter);

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
			// 插人伤
			addVehicle(data);
		}
	}

	private void addVehicle(Intent data)
	{
		Bundle bundle = data.getBundleExtra("item");
		PersonData propDataSave = (PersonData) bundle.getSerializable("personData");
		
		personDatas.remove(personData);
		personData = propDataSave;
		personDatas.add(personData);

		personAdapter = new PersonVehicleInvolvedAdapter(personDatas, getActivity(), personAdapterListener);
		vehicleInvolvedList.setAdapter(personAdapter);
		ExpandableUtil.setListViewHeightBasedOnChildren(vehicleInvolvedList);
	}	
	
	public void refreshData(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 设置财产损失数据
	 */
	public List<PersonData> getPersonData() {
		if (null != view)
		{
			return personDatas;
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
	public String personVerify()
	{
		for (PersonData person : personDatas)
		{
			if (null != person)
			{
				person.init();
				String str = "人伤:";
				
				
				String val = person.getCreateOwner(); //人员类型
				if(StringUtils.isEmpty(val))
				{	
					str+="人员类型不能空";
					return str;
				}			
				val=person.getLossParty();  //受伤人员姓名
				if(StringUtils.isEmpty(val))
				{	
					str+="受伤人员姓名不能空";
					return str;
				}
				
				val = person.getRescueFee();   //证件类型
				if(StringUtils.isEmpty(val))
				{	
					str+="证件类型不能空";
					return str;
				}				
				
				val=person.getCheckDate(); //证件号码
				if(StringUtils.isEmpty(val))
				{	
					str+="证件号码不能空";
					return str;
				}
				
				val=person.getSumLossFee(); //
				if(StringUtils.isEmpty(val))
				{	
					str+="受伤人员性别不能为空";
					return str;
				}				
				val = person.getDeathNum(); //是否死亡	
				if(StringUtils.isEmpty(val))  
				{	
					str+="是否死亡不能为空";
					return str;
				}
				
				val = personData.getModfiyOwner(); //人伤属性
				if(StringUtils.isEmpty(val))  
				{	
					str+="人伤属性不能为空";
					return str;
				}
				
			}
		}
		return "";
	}
	
}
