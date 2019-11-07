//package com.sinosoft.ms.utils;
//
//import java.text.DecimalFormat;
//
//import android.content.Context;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//
///**
// * 网络定位
// */
//
//@SuppressWarnings("all")
//public class LocationUtil {
//
//	private LocationManager locationManager;
//	private Context context;
//	private static DecimalFormat df;
//	public static Double latitude = 0d;
//	public static Double longitude = 0d;
//
//	/**
//	 * 构造方法
//	 * 
//	 * @param context
//	 */
//	public LocationUtil(Context context) {
//		locationManager = (LocationManager) context
//				.getSystemService(Context.LOCATION_SERVICE);
//		this.context = context;
//		df = new DecimalFormat("0");
//	}
//
//	/**
//	 * 获取位置对象
//	 * 
//	 * @return
//	 */
//	public Location getLocation() {
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_FINE);
//		criteria.setAltitudeRequired(false);
//		criteria.setBearingRequired(false);
//		criteria.setCostAllowed(true);
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//		String provider = locationManager.getBestProvider(criteria, true);
//		if (provider == null) {
//			provider = LocationManager.NETWORK_PROVIDER;
//		}
//		Location location = locationManager.getLastKnownLocation(provider);
//		if (location == null) {
//			provider = LocationManager.NETWORK_PROVIDER;
//		}
//
//		locationManager.requestLocationUpdates(provider, 10, 2,
//				locationListener);
//
//		return location;
//	}
//
//	/**
//	 * 定位监听
//	 */
//	private final LocationListener locationListener = new LocationListener() {
//
//		@Override
//		public void onLocationChanged(Location location) {
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			if (latitude != 0d && longitude != 0d) {
//				removeUpdates();
//			}
//		}
//
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//		}
//
//		@Override
//		public void onProviderDisabled(String provider) {
//		}
//	};
//
//	public static double getLat() {
//		return latitude;
//	}
//
//	public static double getLng() {
//		return longitude;
//	}
//
//	public boolean isGPSopen() {
//		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//	}
//
//	public boolean isNetWorkopen() {
//		return locationManager
//				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//	}
//
//	public static String getLatS() {
//		return df.format(getLat() * 1000000);
//	}
//
//	public static String getLngS() {
//		return df.format(getLng() * 1000000);
//	}
//
//	public void removeUpdates() {
//		locationManager.removeUpdates(locationListener);
//	}
//
//}
