package com.sinosoft.ms.service.impl;

import com.sinosoft.ms.utils.component.ToastDialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author maya
 * @createTime 下午5:43:35
 */

public class  NetWorkStateReceiver extends BroadcastReceiver{
    
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    //ToastDialog.show(context, "网络已经连接",ToastDialog.INFO);
     ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
     NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
     NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
 
    if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
         ToastDialog.show(context, "网络已经断开",ToastDialog.ERROR);
      
      }else {
 
    	 // ToastDialog.show(context, "网络已经连接",ToastDialog.ERROR);
      }
    }
  };
