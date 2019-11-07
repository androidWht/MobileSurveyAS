package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.DictCashe;

/**
 * 系统名：移动查勘定损 子系统名：案件中心-涉案车辆列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 07 13:11:00 CST 2013
 */
public class SurveyVehicleAdapter implements ExpandableListAdapter {

	private List<CarData> list;
	private Context context;
	private OnClickListener toListener;
	
	private Map<String,Map<String,String>> dicMap;
	private Map<String,Map<String,String>> dicConfigMap;
	
	public SurveyVehicleAdapter(List<CarData> list, Context context,
			OnClickListener toListener) {
		this.list = list;
		this.context = context;
		this.toListener = toListener;
		dicMap=DictCashe.getInstant().getDict();
		dicConfigMap=AppConstants.getLocalItemConf();
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
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View childView = View.inflate(context, R.layout.item_survey_vehicle_involved_child,
				null);
		TextView frameNumber = (TextView) childView
				.findViewById(R.id.frameNumber);
		TextView engineNumber = (TextView) childView
				.findViewById(R.id.engineNumber);

		final CarData bean = list.get(groupPosition);
		bean.init();
		frameNumber.setText(bean.getVinNo());
		engineNumber.setText(bean.getEngineNo());
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
		View parentView = View.inflate(context,
				R.layout.item_survey_vehicle_involved_group, null);
		TextView lossType = (TextView) parentView.findViewById(R.id.lossType);
		TextView licenseNo = (TextView) parentView.findViewById(R.id.licenseNo);
		TextView theOwner = (TextView) parentView.findViewById(R.id.theOwner);
		TextView numberPlateType = (TextView) parentView
				.findViewById(R.id.numberPlateType);
		final CarData bean = list.get(groupPosition);
		bean.init();
		lossType.setText(SpinnerUtils.getValue(bean.getLossItemType(),dicConfigMap.get("LossItemType")));
		licenseNo.setText(bean.getLicenseNo());
		theOwner.setText(bean.getCarOwner());
		numberPlateType.setText(SpinnerUtils.getValue(bean.getLicenseType(),dicMap.get("LicenseKindCode")));
		return parentView;
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

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#registerDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#unregisterDataSetObserver(android.database.DataSetObserver)
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#onGroupExpanded(int)
	 */
	@Override
	public void onGroupExpanded(int groupPosition) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#onGroupCollapsed(int)
	 */
	@Override
	public void onGroupCollapsed(int groupPosition) {
		// TODO Auto-generated method stub
		
	}

	
}
