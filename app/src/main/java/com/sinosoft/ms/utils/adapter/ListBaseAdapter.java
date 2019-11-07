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
 * @createTime 下午5:17:54
 */

public class ListBaseAdapter extends SimpleAdapter{
     private int []btnIds;
     private OnClickListener listener;
	
	public ListBaseAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to,int []btnIds,OnClickListener listener) {
		super(context, data, resource, from, to);
		this.btnIds=btnIds;
		this.listener=listener;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=super.getView(position, convertView, parent);
		if (btnIds != null) {
			for (int i = 0; i < btnIds.length; i++) {
				Button btn = (Button) view.findViewById(btnIds[i]);
				btn.setTag(position);
				btn.setOnClickListener(listener);

			}

		}
		
		return view;
	}
	

}

