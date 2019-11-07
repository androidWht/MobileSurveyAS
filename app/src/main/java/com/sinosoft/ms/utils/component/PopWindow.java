package com.sinosoft.ms.utils.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.sinosoft.ms.R;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午4:00:01
 */

public class PopWindow
{
	Context context;
	String[] title;

	PopupWindow pop;
	Integer backgroundImgId;
	OnItemClickListener onItemClickListener;

	public PopWindow(Context context, String[] title, Integer backgroundImgId, OnItemClickListener onItemClickListener)
	{
		super();
		this.context = context;
		this.title = title;
		this.backgroundImgId = backgroundImgId;
		this.onItemClickListener = onItemClickListener;
		ListView listView = new ListView(context);
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		for (int i = 0; i < title.length; i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", title[i]);
			data.add(map);

		}

		SimpleAdapter adapter = new SimpleAdapter(context, data, R.layout.item_task_type, new String[]
		{ "title" }, new int[]
		{ R.id.typeName });
		pop = new PopupWindow(listView, 200, 300);

		listView.setAdapter(adapter);
		listView.setCacheColorHint(0x00000000);
		listView.setOnItemClickListener(onItemClickListener);
		listView.setPadding(0, 0, 0, 0);
		pop.setBackgroundDrawable(context.getResources().getDrawable(backgroundImgId));

		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
	}

	public void show(View view)
	{

		pop.showAsDropDown(view);

	}

	public void dismiss()
	{
		if (pop != null && pop.isShowing())
		{
			pop.dismiss();

		}

	}

}
