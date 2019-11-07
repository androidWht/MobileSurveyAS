package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 系统名：移动查勘定损 
 * 子系统名：辅料信息适配器
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 上午11:48:33
 */

public class SimleAssistAdapter extends SimpleAdapter {
	private int[] buttonIds;
	private OnClickListener listener;
	private Context context;
	private int resource;
	private ListView listView;
	
	public SimleAssistAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to, int buttonIds[], OnClickListener listener,ListView listView) {
		super(context, data, resource, from, to);
		this.buttonIds = buttonIds;
		this.listener = listener;
		this.context = context;
		this.resource = resource;
		this.listView = listView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = super.getView(position, View.inflate(context, resource, null),
				parent);
		if (buttonIds != null) {
			for (int i = 0; i < buttonIds.length; i++) {
				Button btn = (Button) v.findViewById(buttonIds[i]);
				btn.setTag(position);
				btn.setOnClickListener(listener);
			}
		}
		return v;
	}

}
