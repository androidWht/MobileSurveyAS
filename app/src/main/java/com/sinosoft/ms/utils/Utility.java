package com.sinosoft.ms.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 系统名：移动查勘定损 子系统名：ScrollView 与 列表共存 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午5:43:31
 */

public class Utility {
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 20;
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	public static String getDeviceId(Context context) {
		TelephonyManager mTelephonyMgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = mTelephonyMgr.getDeviceId();
		return imei==null?"无效的imei码":imei;
	}

	public static String getSdcardAvail(Context context) {
		File path = Environment.getExternalStorageDirectory();
		return getPathSize(path, context);
	}

	public static String getPhoneAvail(Context context) {
		File path = Environment.getDataDirectory();
		return getPathSize(path, context);
	}

	public static String getPathSize(File path, Context context) {

		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		long availableBlocks = stat.getAvailableBlocks();
		// SD卡总容量
		String sdSize = Formatter.formatFileSize(context, totalBlocks
				* blockSize);

		// SD卡剩余容量
		String sdAvail = Formatter.formatFileSize(context, availableBlocks
				* blockSize)
				+ "";

		return sdAvail;
	}

	public String getString(InputStream in) throws Exception {
		byte[] bty = new byte[1024];
		StringBuffer result = new StringBuffer();
		int length = 0;
		try {
			while ((length = in.read(bty)) != -1) {
				result.append(new String(bty, 0, length));
				
			}
		} catch (IOException ioe) {
			throw new IOException(ioe.getMessage());

		}

		return result.toString();
	}
    
	public Location getLocation(Context context) throws Exception{
		 LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
	        List<String> lp = lm.getAllProviders();
	        Location location=null;
	        for (String item:lp)
	        {
	            Log.i("8023", "可用位置服务："+item); 
	        }
	        Criteria criteria = new Criteria();  
	        criteria.setCostAllowed(false); 
	         //设置位置服务免费 
	        criteria.setAccuracy(Criteria.ACCURACY_COARSE); //设置水平位置精度
	         //getBestProvider 只有允许访问调用活动的位置供应商将被返回
	        String  providerName =         lm.getBestProvider(criteria, true);
	        Log.i("8023", "------位置服务："+providerName);
	        if (providerName != null)
	        {        
	            location = lm.getLastKnownLocation(providerName);
	        }
	        else
	        {
	        	throw new Exception("1.请检查网络连接 \n2.请打开我的位置");
	        }
		   return location;
		
	}
	
	
	
	
}