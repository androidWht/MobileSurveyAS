package com.sinosoft.ms.utils.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sinosoft.ms.R;
import com.sinosoft.ms.activity.task.loss.vehicle.VehicleTypeQueryAct;
import com.sinosoft.ms.model.BasicVehicle;
import com.sinosoft.ms.model.VehicleType;
import com.sinosoft.ms.service.IBasicVehicleService;
import com.sinosoft.ms.service.impl.BasicVehicleService;
import com.sinosoft.ms.utils.ActivityManage;

/**
 * 系统名：移动查勘定损 子系统名：案件中心-涉案车辆-驾驶员信息列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT
 * INFORMATION SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 07 13:11:00 CST 2013
 */
public class VehicleTypeAdapter extends BaseExpandableListAdapter {
	private IBasicVehicleService basicVehicleService;
	public static List<VehicleType> list;
	private Context context;
	private String registNo;
	private int toTag;//进入车辆定型，1为车辆基本信息 2为换件 3为简易 车辆基本信息 4为简易换件 
	private String taskId;
	
	private String vehFactoryName ;
	private String vehBrandName ;
	private String vehGroupName ;
	private boolean flag[] ;
	
	public VehicleTypeAdapter(List<VehicleType> list, String registNo,String taskId,Context context,int toTag,String vehFactoryName,String vehBrandName,String vehGroupName) {
		VehicleTypeAdapter.list = list;
		flag = new boolean[list.size()] ;
		this.context = context;
		this.registNo = registNo;
		this.taskId= taskId;
		this.toTag = toTag;
		this.vehFactoryName=vehFactoryName;
		this.vehBrandName=vehBrandName;
		this.vehGroupName=vehGroupName;
		
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		this.flag[groupPosition] = false ;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		this.flag[groupPosition] = true ;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View childView = View.inflate(context, R.layout.item_vehicle_type_data_child, null);
		TextView sex = (TextView) childView.findViewById(R.id.seatTxt);
		TextView age = (TextView) childView.findViewById(R.id.modelYearTxt);
		TextView firstLicenseDate = (TextView) childView
				.findViewById(R.id.descriptionTxt);
		TextView certificatesNumber = (TextView) childView
				.findViewById(R.id.remarkTxt);

		TextView modelNameTxt = (TextView) childView.findViewById(R.id.modelNameTxt) ; // 厂商名称
		TextView carNameTxt = (TextView) childView.findViewById(R.id.carNameTxt) ; // 车系名称
		TextView categoryNameTxt = (TextView) childView.findViewById(R.id.categoryNameTxt) ; // 种类名称
		TextView priceTxt = (TextView) childView.findViewById(R.id.priceTxt) ; // 价格
		
		final VehicleType bean = list.get(groupPosition);
		
		modelNameTxt.setText(bean.getModelName()) ;
		carNameTxt.setText(bean.getCarName()) ;
		categoryNameTxt.setText(bean.getCategoryName()) ;
		priceTxt.setText(bean.getPrice()) ;
		
		sex.setText(bean.getSeat());
		age.setText(bean.getModelYear());
		firstLicenseDate.setText(bean.getDescription());
		certificatesNumber.setText(bean.getRemark());
		return childView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return 1;
	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {

		return childId;
	}

	@Override
	public long getCombinedGroupId(long groupId) {

		return groupId;
	}

	@Override
	public Object getGroup(int groupPosition) {

		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View parentView = View
				.inflate(context, R.layout.item_vehicle_type_data_group, null);
		TextView theDriverName = (TextView) parentView
				.findViewById(R.id.tradeName);
		TextView tel = (TextView) parentView.findViewById(R.id.modelName);
		TextView quasiDrivingType = (TextView) parentView
				.findViewById(R.id.carName);
		TextView drivingLicenseNumber = (TextView) parentView
				.findViewById(R.id.categoryName);
		TextView certificatesType = (TextView) parentView
				.findViewById(R.id.price);
		TextView volume = (TextView) parentView
				.findViewById(R.id.volume);
		Button button = (Button) parentView.findViewById(R.id.selectBtn);
		
		final VehicleType bean = list.get(groupPosition);
		theDriverName.setText(bean.getTradeName());
		tel.setText(bean.getModelName());
		quasiDrivingType.setText(bean.getCarName());
		drivingLicenseNumber.setText(bean.getCategoryName());

		certificatesType.setText(bean.getPrice());
		volume.setText(bean.getVolume());
		
		button.setOnClickListener(new MyOnClickListener(bean));
		if (isExpanded) {
			parentView.setBackgroundResource(R.drawable.new_item_bg_select);
		} else {
			parentView.setBackgroundResource(R.drawable.new_item_bg_nomal);
		}
		return parentView;
	}
	
	class MyOnClickListener implements OnClickListener{
		private VehicleType bean;
		
		public MyOnClickListener(VehicleType bean) {
			super();
			this.bean = bean;
		}

		@Override
		public void onClick(View v) {
			try {
				if(null==basicVehicleService){
					basicVehicleService = new BasicVehicleService();
				}
				BasicVehicle basicVehicle = basicVehicleService.getByRegistNo(taskId);
				basicVehicle.setVehCertainCode(bean.getVehCertainCode());
				basicVehicle.setVehCertainName(bean.getVehCertainName());
				
				/** 保存车辆定型信息 **/
				basicVehicle.setVehFactoryName(vehFactoryName) ; 
				basicVehicle.setVehBrandName(vehBrandName) ;
				basicVehicle.setVehGroupName(vehGroupName) ;
				
				if(basicVehicleService.update(basicVehicle)){
					ActivityManage.getInstance().pop().finish();
					Activity activity=ActivityManage.getInstance().peek();
					if(activity!=null&&activity instanceof VehicleTypeQueryAct){
							activity.finish();
							ActivityManage.getInstance().pop();
						
					}
				}else{
					Toast.makeText(context, "程序更新车辆定型信息失败", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
