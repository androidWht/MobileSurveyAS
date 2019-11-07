package com.sinosoft.ms.utils.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 系统名：移动查勘 子系统名： 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午9:55:08
 */

public class SurveyPagerAdapter extends PagerAdapter {
	private Context context;
	private List<View> views;

	public SurveyPagerAdapter(Context context, List<View> views) {
		this.context = context;
		this.views = views;
	}

	public void refreshViews(List<View> views) {
		this.views = views;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(views.get(position));// 删除页卡
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		container.addView(views.get(position), 0);// 添加页卡
		return views.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;// 官方提示这样写
	}

	public void updateViewPagerItem(View view, int index) {
		views.set(index, view);
		notifyDataSetChanged();
		// findViewById(getResources().getIdentifier("sysset_button"+(index+1),
		// "id", "com.jzbyapp.suzhou")).requestFocus();
	}

}
