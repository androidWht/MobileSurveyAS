package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
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
import com.sinosoft.ms.utils.component.SpinnerUtils;
import com.sinosoft.ms.utils.db.DictCashe;

/**
 * 系统名：移动查勘定损 子系统名：案件中心列表适配器 著作权：COPYRIGHT (C) 2012 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 05 13:11:00 CST 2013
 */
public class LossChangeItemAdapter implements ExpandableListAdapter {
	
	 Map<String, Map<String, String>> map = null;
	 Map<String, Map<String, String>> dataMap = null;
	private ExpandableListView lossChangeModfiyList;
	public static List<LossFitInfoItem> list;
	private OnClickListener listener;
	private Context context;
	private String taskId;
	
	public LossChangeItemAdapter(List<LossFitInfoItem> lists, String registNo,
			Context context, ExpandableListView lossChangeModfiyList,
			OnClickListener listener) {
		this.lossChangeModfiyList = lossChangeModfiyList;
		this.taskId = registNo;
		this.context = context;
		list = lists;
		this.listener = listener;
		dataMap = DictCashe.getInstant().getDict();
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
		View childView = View
				.inflate(context, R.layout.item_change_project_child, null);
		TextView localhostPriceTxt = (TextView) childView.findViewById(R.id.localhostPriceTxt);
		TextView countTxt = (TextView) childView
				.findViewById(R.id.countTxt);
		TextView insurTxt = (TextView) childView
				.findViewById(R.id.insurTxt);
		TextView salvageTxt = (TextView) childView
				.findViewById(R.id.salvageTxt);
		TextView pricePlanTxt = (TextView) childView
				.findViewById(R.id.pricePlanTxt);
		TextView lossOfMoreThanTxt = (TextView) childView
				.findViewById(R.id.lossOfMoreThanTxt);
		TextView remarkTxt = (TextView) childView
				.findViewById(R.id.remarkTxt);
		final LossFitInfoItem bean = list.get(groupPosition);
		
		localhostPriceTxt.setText(String.valueOf(bean.getLocPrice1())+" 元");
		countTxt.setText(String.valueOf(bean.getLossCount())+" 个/件");
		
		//Map<String,String> insuranceMap=AppConstants.getInsuranceMap(taskId);
		//Log.e("mapmap", bean.getInsureTerm()+":"+insuranceMap.toString());
		insurTxt.setText(bean.getInsureTerm());
		salvageTxt.setText(String.valueOf(bean.getRemnant()));
		
		pricePlanTxt.setText(SpinnerUtils.getValue(bean.getChgCompSetCode(),map.get("ChgCompSetCode")));
//		pricePlanTxt.setText(dataMap.get("RepairFactoryType").get(bean.getChgCompSetCode()));
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
		View parentView = View.inflate(context, R.layout.item_change_project_group,
				null);
		TextView replacementProjectTxt = (TextView) parentView.findViewById(R.id.replacementProjectTxt);
		TextView factoryPartsNumberTxt = (TextView) parentView.findViewById(R.id.factoryPartsNumberTxt);
		TextView localPriceTxt = (TextView) parentView
				.findViewById(R.id.localPriceTxt);
		TextView surveyQuotedPriceTxt = (TextView) parentView
				.findViewById(R.id.surveyQuotedPriceTxt);
		TextView modifyFactoryPriceTxt = (TextView) parentView.findViewById(R.id.modifyFactoryPriceTxt);
		LinearLayout optionLayout = (LinearLayout) parentView
				.findViewById(R.id.optionLayout);
		
		final LossFitInfoItem bean = list.get(groupPosition);
		replacementProjectTxt.setText(bean.getPartStandard());
		factoryPartsNumberTxt.setText(bean.getOriginalId());
		//本地价
		localPriceTxt.setText(String.valueOf(bean.getRefPrice1())+"元");
		if(StringUtils.isNotEmpty(bean.getSurveyQuotedPrice())){
			surveyQuotedPriceTxt.setText(String.valueOf(bean.getSurveyQuotedPrice())+"元");
		}
		if(StringUtils.isNotEmpty(bean.getModifyFactoryPrice())){
			modifyFactoryPriceTxt.setText(String.valueOf(bean.getModifyFactoryPrice())+"元");
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
		a.setTextColor(Color.WHITE);
		a.setTag(groupPosition);
		
		Button b = new Button(context);
		b.setBackgroundResource(R.drawable.sm_loss_delete_btn);
		b.setLayoutParams(params);
		b.setTextColor(Color.WHITE);
		b.setTag(groupPosition);
		
		a.setText(R.string.updateBtn);
		b.setText(R.string.deleteBtn);
			
		optionLayout.addView(a);
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
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		ExpandableUtil.setListViewHeightBasedOnChildren(lossChangeModfiyList);
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
