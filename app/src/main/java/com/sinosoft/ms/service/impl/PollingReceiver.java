package com.sinosoft.ms.service.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 系统名：移动查勘定损 子系统名：轮询任务广播服务 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:16:43
 */
public class PollingReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// 运行轮询服务
		intent.setClass(context, BackstageServices.class);
		context.startService(intent);
	}

}
