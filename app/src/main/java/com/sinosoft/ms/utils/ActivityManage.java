package com.sinosoft.ms.utils;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

/**
 * 系统名：真龙移动应用
 * 子系统名：扩展Activity管理 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午1:23:45
 */

public class ActivityManage {
	private List<Activity> actList = new LinkedList<Activity>();
	private static ActivityManage instance;
	private int top;

	private ActivityManage() {
	}

	public static ActivityManage getInstance() {
		if (null == instance) {
			instance = new ActivityManage();
		}
		return instance;
	}

	/**
	 * 加入Activity对象
	 * 
	 * @param activity
	 *            Activity对象
	 */
	public void push(Activity activity) {
		Log.e("add  activity", activity.toString());
		actList.add(activity);
		top ++;
	}

	/**
	 * 取出Activity对象
	 * 
	 * @return Activity对象
	 */
	public Activity pop() throws Exception {
		if (null == actList || actList.isEmpty()) {
			throw new Exception("无可用Activity对象");
		}
		Activity activity= actList.remove(--top);
		Log.e("activity", activity.toString());
		Log.e("all activity", actList.toString());
		return activity;
	}

	/**
	 * 返回栈顶Activity对象
	 * 
	 * @return Activity对象
	 */
	public Activity peek() {
		return actList.get(top-1);
	}

	/**
	 * 返回Activity元素个数
	 * 
	 * @return Activity元素个数
	 */
	public int getElementCount() {
		return top;
	}

	/**
	 * 销毁资源
	 */
	public void distory() {
		for (Activity act : actList) {
			act.finish();
			
		}
		actList.clear();
		top = 0;
	}

	/**
	 * @return the actList
	 */
	public List<Activity> getActList() {
		return actList;
	}

	/**
	 * @param actList the actList to set
	 */
	public void setActList(List<Activity> actList) {
		this.actList = actList;
	}
	
}
