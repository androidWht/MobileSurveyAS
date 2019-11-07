package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

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
import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：案件中心列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public class MsChangeItemAdapter implements ExpandableListAdapter {
	 Map<String, Map<String, String>> map = null;
	private ExpandableListView lossChangeModfiyList;
	public static List<LossFitInfoItem> list;
	private OnClickListener listener;
	private Context context;
	private String taskId;
	private boolean flag[] ;
	
	public MsChangeItemAdapter(List<LossFitInfoItem> lists, String registNo,
			Context context, ExpandableListView lossChangeModfiyList,
			OnClickListener listener) {
		this.lossChangeModfiyList = lossChangeModfiyList;
		this.taskId = registNo;
		this.context = context;
		list = lists;
		flag = new boolean[list.size()] ;
		this.listener = listener;
		map = AppConstants.getLocalItemConf();
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
		View childView = View.inflate(context, R.layout.item_change_child, null);
		TextView localhostPriceTxt = (TextView) childView.findViewById(R.id.localhostPriceTxt);
		TextView countTxt = (TextView) childView
				.findViewById(R.id.countTxt);
		TextView pricePlanTxt = (TextView) childView
				.findViewById(R.id.pricePlanTxt);
		TextView lossOfMoreThanTxt = (TextView) childView
				.findViewById(R.id.lossOfMoreThanTxt);
		TextView remarkTxt = (TextView) childView
				.findViewById(R.id.remarkTxt);
		final LossFitInfoItem bean = list.get(groupPosition);
		
		localhostPriceTxt.setText(String.valueOf(bean.getLocPrice1())+" 元");
		countTxt.setText(String.valueOf(bean.getLossCount())+" 个/件");	
		pricePlanTxt.setText(map.get("ChgCompSetCode").get(bean.getChgCompSetCode()));
		lossOfMoreThanTxt.setText(map.get("Status").get(bean.getIfRemain()));
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
		View parentView = View.inflate(context, R.layout.item_change_group,
				null);
		TextView originalNameTxt = (TextView) parentView.findViewById(R.id.originalNameTxt);
		TextView chgLocPriceTxt = (TextView) parentView.findViewById(R.id.chgLocPriceTxt);
		TextView lossCountTxt = (TextView) parentView.findViewById(R.id.lossCountTxt);
		TextView chgCompSetCodeTxt = (TextView) parentView.findViewById(R.id.chgCompSetCodeTxt);
		TextView remnantTxt = (TextView) parentView.findViewById(R.id.remnantTxt);
		LinearLayout optionLayout = (LinearLayout) parentView.findViewById(R.id.optionLayout);
		
		final LossFitInfoItem bean = list.get(groupPosition);
		originalNameTxt.setText(bean.getOriginalName());
		chgLocPriceTxt.setText(String.valueOf(bean.getChgLocPrice()));
		lossCountTxt.setText(String.valueOf(bean.getLossCount()));
		chgCompSetCodeTxt.setText(map.get("ChgCompSetCode").get(bean.getChgCompSetCode()));
		remnantTxt.setText(String.valueOf(bean.getRemnant()));
		if(flag[groupPosition]){
			parentView.setBackgroundResource(R.drawable.new_item_bg_select);
		}else{
			parentView.setBackgroundResource(R.drawable.new_item_bg_nomal);
		}
		setOption(optionLayout,bean,groupPosition);
		return parentView;
	}

	/**
	 * 设置操作
	 * 
	 * @param optionLayout
	 * @param bean
	 */
	private void setOption(LinearLayout optionLayout, LossFitInfoItem bean,int groupPosition) {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 5, 5, 5) ;
		Button a = new Button(context);
		a.setBackgroundResource(R.drawable.sm_loss_btn);
		a.setLayoutParams(params);
		a.setTag(groupPosition);
		a.setTextColor(Color.WHITE) ;
		a.setGravity(Gravity.CENTER) ;
		
		TextView txt = new TextView(context) ;
		txt.setLayoutParams(params);
		
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

	/**
	 * 当Group收缩时调用
	 */
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

	/**
	 * 当Adapter所表示的数据改变时会通知它
	 */
	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	/**
	 * 取消注册一个Observer
	 */
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}
	
}
