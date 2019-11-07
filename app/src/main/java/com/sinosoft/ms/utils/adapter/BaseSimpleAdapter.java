package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;

/**
 * 系统名：移动查勘
 * 子系统名：
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午5:25:44
 */

public class BaseSimpleAdapter extends SimpleAdapter{
	int[] buttonIds;
	OnClickListener listener;
	Context context;
	int resource;

	public BaseSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to, int buttonIds[], OnClickListener listener) {
		super(context, data, resource, from, to);
		this.buttonIds = buttonIds;
		this.listener = listener;
		this.context = context;
		this.resource = resource;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//

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

