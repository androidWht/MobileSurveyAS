package com.sinosoft.ms.utils.adapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PersonDetailData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.DictCashe;

/**
 * 系统名：移动查勘定损 子系统名：案件中心-涉案车辆列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 07 13:11:00 CST 2013
 */
public class PersonVehicleInvolvedAdapter implements ExpandableListAdapter
{

	private List<PersonData> list;
	private Context context;
	private OnClickListener toListener;

	private Map<String, Map<String, String>> dicMap;
	private Map<String, Map<String, String>> dicConfigMap;
	private boolean flag[];

	public PersonVehicleInvolvedAdapter(List<PersonData> list, Context context, OnClickListener toListener)
	{
		this.list = list;
		flag = new boolean[list.size()];
		this.context = context;
		this.toListener = toListener;
		dicMap = DictCashe.getInstant().getDict();
		dicConfigMap = AppConstants.getLocalItemConf();
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return list.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
	    return null;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{

		return 1;
	}

	@Override
	public long getCombinedChildId(long groupId, long childId)
	{

		return childId;
	}

	@Override
	public long getCombinedGroupId(long groupId)
	{

		return groupId;
	}

	@Override
	public Object getGroup(int groupPosition)
	{

		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{

		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		View parentView = View.inflate(context, R.layout.item_person_involved_group, null);
		TextView personType = (TextView) parentView.findViewById(R.id.personType);
		TextView personName = (TextView) parentView.findViewById(R.id.personName);
		TextView personInfo = (TextView) parentView.findViewById(R.id.personInfo);
		
		///LinearLayout optionLayout = (LinearLayout) parentView.findViewById(R.id.optionPropLayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.weight=1;
        
		
		PersonData bean = list.get(groupPosition);
		bean.init();
		if(bean != null){
		  /*List<PersonDetailData> personDetailDataList= bean.getPersonDetailData();
		  if(personDetailDataList != null && personDetailDataList.size()>0){
			  PersonDetailData personDetailData = personDetailDataList.get(0);
  			  Map<String, String> map =dicConfigMap.get("personDataBDType");
  			  String type=personDetailData.getPersonPayType();
  			  String typeName=map.get(type);
  			  personType.setText(typeName);
  			  personName.setText(personDetailData.getPersonName());
  			  personInfo.setText(personDetailData.getHospitalName());
		  }*/
			  Map<String, String> map =dicConfigMap.get("personDataBDType");
			  String type=bean.getCreateOwner();
			  String typeName=map.get(type);
			  personType.setText(typeName);
			  personName.setText(bean.getLossParty());
			  personInfo.setText(bean.getModfiyOwner());
			
		}
		Button a= (Button) parentView.findViewById(R.id.dealPerson);
		Button d= (Button) parentView.findViewById(R.id.delPerson);
		d.setTag(groupPosition);
		a.setTag(groupPosition);
		

		if (isExpanded)
		{
			parentView.setBackgroundResource(R.drawable.new_item_bg_select);
		}
		else
		{
			parentView.setBackgroundResource(R.drawable.new_item_bg_nomal);
		}

		a.setOnClickListener(toListener);
		d.setOnClickListener(toListener);
		return parentView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.ExpandableListAdapter#registerDataSetObserver(android.
	 * database.DataSetObserver)
	 */
	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.ExpandableListAdapter#unregisterDataSetObserver(android
	 * .database.DataSetObserver)
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#onGroupExpanded(int)
	 */
	@Override
	public void onGroupExpanded(int groupPosition)
	{
		// TODO Auto-generated method stub
		flag[groupPosition] = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ExpandableListAdapter#onGroupCollapsed(int)
	 */
	@Override
	public void onGroupCollapsed(int groupPosition)
	{
		// TODO Auto-generated method stub
		flag[groupPosition] = false;
	}

}
