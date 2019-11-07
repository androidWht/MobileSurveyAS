package com.sinosoft.ms.utils.adapter;

import java.util.List;
import java.util.Map;

import com.sinosoft.ms.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

public class MediaSimpleAdapter extends SimpleAdapter{
	int[] buttonIds;
	OnClickListener listener;
	Context context;
	int resource;
	public static int selectItem=-1;

	public MediaSimpleAdapter(Context context,
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
		if(selectItem==position){
			v.setBackgroundResource(R.drawable.sm_loss_selected_bg) ;
		}else{
			v.setBackgroundColor(0x636363);
		}
		
		
		for (int i = 0; i < buttonIds.length; i++) {
			Button btn = (Button) v.findViewById(buttonIds[i]);
			btn.setTag(position);
			btn.setOnClickListener(listener);

		}

		return v;
	}

}

