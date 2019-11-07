package com.sinosoft.ms.utils.adapter;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinosoft.ms.R;
import com.sinosoft.ms.model.LossRepairInfoItem;

/**
 * 系统名：移动查勘定损 子系统名：案件中心列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public class SmRepairItemAdapter implements ExpandableListAdapter {
	private ExpandableListView lossChangeModfiyList;
	public static List<LossRepairInfoItem> list;
	private OnClickListener listener;
	private Context context;
	private String taskId;
	private boolean flag[] ;
	public SmRepairItemAdapter(List<LossRepairInfoItem> lists,
			String registNo, Context context,
			ExpandableListView lossChangeModfiyList,
			OnClickListener listener) {
		list = lists;
		flag = new boolean[list.size()] ;
		this.context = context;
		this.lossChangeModfiyList = lossChangeModfiyList;
		this.taskId = registNo;
		this.listener = listener;
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
		View childView = View.inflate(context, R.layout.item_repair_child,
				null);
		TextView workeHoursFeeTxt = (TextView) childView
				.findViewById(R.id.workeHoursFeeTxt);
		TextView insurTxt = (TextView) childView.findViewById(R.id.insurTxt);
		TextView remarkTxt = (TextView) childView.findViewById(R.id.remarkTxt);
		final LossRepairInfoItem bean = list.get(groupPosition);
		workeHoursFeeTxt.setText(String.valueOf(bean.getHourFee()));
		
		//Map<String,String> map=AppConstants.getInsuranceMap(taskId);
		
		
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
		View parentView = View.inflate(context, R.layout.item_repair_group,
				null);
		TextView repairNameTxt = (TextView) parentView
				.findViewById(R.id.repairNameTxt);
		TextView repairNumberTxt = (TextView) parentView
				.findViewById(R.id.repairNumberTxt);
		TextView repairWorkeHoursTxt = (TextView) parentView
				.findViewById(R.id.repairWorkeHoursTxt);
		TextView repairMoneyTxt = (TextView) parentView
				.findViewById(R.id.repairMoneyTxt);
		LinearLayout optionLayout = (LinearLayout) parentView
				.findViewById(R.id.optionLayout);
		
		final LossRepairInfoItem bean = list.get(groupPosition);
		repairNameTxt.setText(bean.getRepairItemName());
		repairNumberTxt.setText(bean.getRepairItemCode());
		repairWorkeHoursTxt.setText(String.valueOf(bean.getManHour()));
		BigDecimal bd = new BigDecimal(String.valueOf(bean.getRepairFee()));
		repairMoneyTxt.setText(bd.toPlainString());
		if(flag[groupPosition]){
			parentView.setBackgroundResource(R.drawable.new_item_bg_select);
		}else{
			parentView.setBackgroundResource(R.drawable.new_item_bg_nomal);
		}
		setOption(optionLayout, groupPosition);
		return parentView;
	}

	/**
	 * 设置操作
	 * 
	 * @param optionLayout
	 * @param groupPosition
	 */
	private void setOption(LinearLayout optionLayout, int groupPosition) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5) ;
		Button a = new Button(context);
		a.setBackgroundResource(R.drawable.sm_loss_btn);
		a.setLayoutParams(params);
		a.setTextColor(Color.WHITE) ;
		a.setGravity(Gravity.CENTER) ;
		a.setTag(groupPosition);
		
		TextView txt = new TextView(context) ;
		txt.setLayoutParams(new LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)) ;
		
		Button b = new Button(context);
		b.setBackgroundResource(R.drawable.sm_loss_delete_btn);
		b.setLayoutParams(params);
		b.setTextColor(Color.WHITE) ;
		b.setGravity(Gravity.CENTER) ;
		b.setTag(groupPosition);
		
		a.setText(R.string.updateBtn);
		b.setText(R.string.deleteBtn);

		optionLayout.addView(a);
		optionLayout.addView(txt) ;
		optionLayout.addView(b);
		a.setOnClickListener(listener);
		b.setOnClickListener(listener);
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
		flag[groupPosition] = false ;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		ExpandableUtil.setListViewHeightBasedOnChildren(lossChangeModfiyList);
		flag[groupPosition] = true ;
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
