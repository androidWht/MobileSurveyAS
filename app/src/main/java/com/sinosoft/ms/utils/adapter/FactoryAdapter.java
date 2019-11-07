package com.sinosoft.ms.utils.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sinosoft.ms.R;

public class FactoryAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private LayoutInflater mInflater;
	private OnClickListener listener;
	//
	public List<List<String>> sonStr = null;
	public List<String> fatherStr = null;

	public FactoryAdapter(Context context,OnClickListener listener) {
		this.mContext = context;
		mInflater = mInflater.from(context);
		this.listener = listener;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		if (fatherStr != null) {
			return fatherStr.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return fatherStr.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupHolder groupHolder = null;
		if (convertView == null) {
			groupHolder = new GroupHolder();
			convertView = mInflater.inflate(R.layout.item_factory_group, null);
			groupHolder.father_title_tv = (TextView) convertView
					.findViewById(R.id.groupTxt);
			convertView.setTag(groupHolder);
			//groupHolder.father_title_tv.setOnClickListener(listener);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		groupHolder.father_title_tv.setText(fatherStr.get(groupPosition));
		return convertView;
	}

	class GroupHolder {
		TextView father_title_tv;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		if (sonStr != null && sonStr.get(groupPosition) != null) {
			return sonStr.get(groupPosition).size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return sonStr.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if (convertView == null) {
			childHolder = new ChildHolder();
			convertView = mInflater.inflate(R.layout.item_factory_chail, null);
			childHolder.son_title_tv = (TextView) convertView
					.findViewById(R.id.subTxt);
			convertView.setTag(childHolder);
			childHolder.son_title_tv.setPadding(30, 5, 0, 5);
			childHolder.son_title_tv.setOnClickListener(listener);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		childHolder.son_title_tv.setTag(fatherStr.get(groupPosition));
		childHolder.son_title_tv.setText(sonStr.get(groupPosition).get(childPosition));
		
		return convertView;
	}

	class ChildHolder {
		TextView son_title_tv;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	// 销毁资源
	public void exit() {
		mContext = null;
		mInflater = null;
		sonStr = null;
		fatherStr = null;
	}
}