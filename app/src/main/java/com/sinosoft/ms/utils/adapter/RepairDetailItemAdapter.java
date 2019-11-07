package com.sinosoft.ms.utils.adapter;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.LossRepairInfoItem;

/**
 * 系统名：移动查勘定损 
 * 子系统名：定损详细修理列表适配器 
 * 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public class RepairDetailItemAdapter implements ExpandableListAdapter {
	private ExpandableListView lossChangeModfiyList;
	public static List<LossRepairInfoItem> list;
	private Context context;
	private boolean flag[] ;
	public RepairDetailItemAdapter(List<LossRepairInfoItem> lists,
			String registNo, Context context,
			ExpandableListView lossChangeModfiyList,
			OnClickListener listener) {
		list = lists;
		flag = new boolean[list.size()];
		this.context = context;
		this.lossChangeModfiyList = lossChangeModfiyList;
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
		View childView = View.inflate(context, R.layout.item_repair_project_child,
				null);
		TextView workeHoursFeeTxt = (TextView) childView
				.findViewById(R.id.workeHoursFeeTxt);
		TextView insurTxt = (TextView) childView.findViewById(R.id.insurTxt);
		TextView remarkTxt = (TextView) childView.findViewById(R.id.remarkTxt);
		final LossRepairInfoItem bean = list.get(groupPosition);
		workeHoursFeeTxt.setText(String.valueOf(bean.getHourFee()));
		insurTxt.setText(bean.getInsureTerm());
		remarkTxt.setText(bean.getRemark());
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
		View parentView = View.inflate(context, R.layout.item_repair_detail_group,
				null);
		TextView professionNameTxt = (TextView) parentView
				.findViewById(R.id.professionNameTxt);
		TextView repairNameTxt = (TextView) parentView
				.findViewById(R.id.repairNameTxt);
		TextView repairNumberTxt = (TextView) parentView
				.findViewById(R.id.repairNumberTxt);
		TextView repairWorkeHoursTxt = (TextView) parentView
				.findViewById(R.id.repairWorkeHoursTxt);
		TextView repairMoneyTxt = (TextView) parentView
				.findViewById(R.id.repairMoneyTxt);
		
		final LossRepairInfoItem bean = list.get(groupPosition);
		professionNameTxt.setText(bean.getRepairName());
		repairNameTxt.setText(bean.getRepairItemName());
		repairNumberTxt.setText(bean.getRepairItemCode());
		repairWorkeHoursTxt.setText(String.valueOf(bean.getManHour()));
		repairMoneyTxt.setText(String.valueOf(bean.getRepairFee()));
		if(flag[groupPosition]){
			parentView.setBackgroundResource(R.drawable.new_item_bg_select);
		}else{
			parentView.setBackgroundResource(R.drawable.new_item_bg_nomal);
		}
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

	@Override
	public void onGroupCollapsed(int groupPosition) {
		ExpandableUtil.setListViewHeightBasedOnChildren(lossChangeModfiyList);
		flag[groupPosition]=false;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		ExpandableUtil.setListViewHeightBasedOnChildren(lossChangeModfiyList);
		flag[groupPosition]=true;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub

	}
}
