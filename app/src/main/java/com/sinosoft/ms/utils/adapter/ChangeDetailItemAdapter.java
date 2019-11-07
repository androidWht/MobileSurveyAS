package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.DataSetObserver;
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
public class ChangeDetailItemAdapter implements ExpandableListAdapter {
	ExpandableListView lossChangeModfiyList;
	public static List<LossFitInfoItem> list;
	private Context context;
	Map<String, Map<String, String>> map = null;
	private boolean flag[] ;
	public ChangeDetailItemAdapter(List<LossFitInfoItem> lists, String registNo,
			Context context, ExpandableListView lossChangeModfiyList,
			OnClickListener listener) {
		this.lossChangeModfiyList = lossChangeModfiyList;
		this.context = context;
		list = lists;
		flag = new boolean[list.size()] ;
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
		localhostPriceTxt.setText(String.valueOf(bean.getLocPrice1()));
		countTxt.setText(String.valueOf(bean.getLossCount()));
		insurTxt.setText(bean.getInsureTerm());
		salvageTxt.setText(String.valueOf(bean.getRemnant()));
		
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
		View parentView = View.inflate(context, R.layout.item_change_detail_group,
				null);
		TextView replacementProjectTxt = (TextView) parentView.findViewById(R.id.replacementProjectTxt);
		TextView factoryPartsNumberTxt = (TextView) parentView.findViewById(R.id.factoryPartsNumberTxt);
		TextView systemPriceTxt = (TextView) parentView
				.findViewById(R.id.systemPriceTxt);
		TextView surveyQuotedPriceTxt = (TextView) parentView
				.findViewById(R.id.surveyQuotedPriceTxt);
		TextView modifyFactoryPriceTxt = (TextView) parentView.findViewById(R.id.modifyFactoryPriceTxt);
		
		final LossFitInfoItem bean = list.get(groupPosition);
		replacementProjectTxt.setText(bean.getPartStandard());
		factoryPartsNumberTxt.setText(bean.getOriginalName());
		systemPriceTxt.setText(String.valueOf(bean.getRefPrice1()));
		
		
		
		if(StringUtils.isNotEmpty(bean.getSurveyQuotedPrice())){
			surveyQuotedPriceTxt.setText(String.valueOf(bean.getSurveyQuotedPrice()));
		}
		if(StringUtils.isNotEmpty(bean.getModifyFactoryPrice())){
			modifyFactoryPriceTxt.setText(String.valueOf(bean.getModifyFactoryPrice()));
		}
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

	/**
	 * 当Group收缩时调用
	 */
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
