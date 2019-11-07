package com.sinosoft.ms.utils;

import java.text.DecimalFormat;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
/**
 * 定位服务
 * @className LocationService
 * @classFunction 
 * @author hijack
 * @createTime 4:02:15 PM Jul 23, 2014
 */
public class LocationService extends Service implements AMapLocationListener,Runnable{
	private static final String TAG = "LocationService";
	private MyBinder myBinder = new MyBinder();
	
	private LocationManagerProxy aMapLocManager = null;
	private Handler handler = new Handler();
	
	private static DecimalFormat df;
	public static Double longitude = 0d;	//经度
	public static Double latitude = 0d;		//纬度

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return myBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		df = new DecimalFormat("0.0000");
		requestLocation();//开始定位
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopLocation();// 停止定位
	}
	
	/**
	 * 开始定位
	 */
	private void requestLocation(){
		aMapLocManager = LocationManagerProxy.getInstance(this);
		aMapLocManager.setGpsEnable(true);
		//aMapLocManager.setSensorEnable(true);
		
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法
		// 其中如果间隔时间为-1，则定位只定一次,
		// 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
		//aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
		 aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, 10*1000, 15, this);
	}
	
	/**
	 * 销毁定位
	 */
	private void stopLocation() {
		if (aMapLocManager != null) 
		{
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
	}

	public class MyBinder extends Binder {
		public LocationService getService() {
			return LocationService.this;
		}
	}

	/**
	 * 此方法舍弃不用
	 */
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		// TODO Auto-generated method stub
		
		
		
		
		if (location != null && location.getAMapException().getErrorCode() == 0) {
			
			AppConstants.debug("定位信息成功----->");
			//获取纬度
			latitude = location.getLatitude();
			//获取经度
			longitude = location.getLongitude();
			Log.i(TAG, "latitute:"+latitude+",longitute:"+longitude);
			//一旦获取到经纬度就停止定位，然后往后每隔3分钟定位一次
			stopLocation();
			handler.postDelayed(this, 3 * 60 * 1000);
		}
		else
		{
			AppConstants.debug("定位信息ErrorMessage----->" + location.getAMapException().getErrorMessage());
			AppConstants.debug("定位信息ErrorCode----->" + location.getAMapException().getErrorCode());
			AppConstants.debug("定位信息LocalizedMessage----->" + location.getAMapException().getLocalizedMessage());
			AppConstants.debug("定位信息Message----->" + location.getAMapException().getMessage());
		}
	}
	
	public static double getLat() {
		return latitude;
	}

	public static double getLng() {
		return longitude;
	}

	public static String getLatS() {
		return df.format(getLat());
	}

	public static String getLngS() {
		return df.format(getLng());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		requestLocation();
	}

}
