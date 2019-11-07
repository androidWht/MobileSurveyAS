package com.sinosoft.ms.utils;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.utils.db.DictDatabase;

public class MyUtils {

	/**
	 * 格式化时间 说明:如格式不匹配则返回空
	 * 
	 * @param format
	 *            时间格式
	 * @param source
	 *            时间字符串
	 * @return 格式化后的时间
	 */
	public static Date format(String format, String source) {
		Date date = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			date = df.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化时间 说明:如格式不匹配则返回空
	 * 
	 * @param format
	 *            时间格式
	 * @param date
	 *            时间
	 * @return 格式化后的时间
	 */
	public static String format(String format, Date date) {
		String time = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			time = df.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 为空字符串判断，如果为空返回"",否则返回原内容
	 * 
	 * @param text
	 *            判断字符串
	 * @return
	 */
	public static String toString(String text) {
		String result = "";
		if (null != text && !"".equals(text)) {
			result = text;
		}
		return result;
	}

	public static int getTaskType(String taskType) {
		if ("查勘任务".equals(taskType)) {
			return 0;

		} else if ("定损任务".equals(taskType)) {
			return 1;

		}
		return 2;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */

	public static double add(double sumLossFee, String v2) {
		double result = 0;
		try {
			if (StringUtils.isEmpty(v2)) {
				v2 = "0";
			}
			BigDecimal b1 = new BigDecimal(sumLossFee);
			BigDecimal b2 = new BigDecimal(v2);
			result = b1.add(b2).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param multNumber
	 *            乘数
	 * @param multNumbered
	 *            被乘数
	 * @return 乘法运算结果
	 */
	public static double mult(double sumLossFee, String v2) {
		double result = 0;
		try {
			if (StringUtils.isEmpty(v2)) {
				v2 = "0";
			}
			BigDecimal b1 = new BigDecimal(sumLossFee);
			BigDecimal b2 = new BigDecimal(v2);
			result = b1.multiply(b2).doubleValue();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return result;
	}

	public static String getTaskTypeString(int type) {
		String typeName = "";
		switch (type) {
		case 0:
			typeName = AppConstants.SURVEY;
			break;
		case 1:
			typeName = AppConstants.COMPLETE_SETLOSS;
			break;
		case 2:
			typeName = AppConstants.COMPLETE_SETLOSS;
			// typeName = AppConstants.SURVEY;
			break;
		case 3:
			typeName = AppConstants.RETREAT_SETLOSS;
			break;
		case 4:
			typeName = AppConstants.PRIVATE_SURVEY;
			break;
		case 5:
			typeName = AppConstants.COUNT_RETREAT_SETLOSS;
		}
		return typeName;
	}

	public static int getStatus(String str) {
		if ("已完成".equals(str)) {
			return 5;

		} else if ("归档".equals(str)) {
			return 6;

		}
		return 0;

	}

	public static String getStatusString(int status) {
		String statusName = "未知";
		// 0.未接收 1.接收 2.改派 3.拒绝 4.到达 5.完成 6.归档
		switch (status) {
		case 0:
			statusName = "未接收";
			break;
		case 1:
			statusName = "已接收";
			break;

		case 2:
			statusName = "改派";
			break;
		case 3:
			statusName = "拒绝";
			break;
		case 4:
			statusName = "到达现场";
			break;
		case 5:
			statusName = "完成";
			break;
		case 6:
			statusName = "归档";
			break;
		case 90:
			statusName = "联系客户";
			break;
		}

		return statusName;
	}

	private long getEnvironmentSize() {
		File localFile = Environment.getDataDirectory();
		long l1;
		if (localFile == null)
			l1 = 0L;
		while (true) {
			String str = localFile.getPath();
			StatFs localStatFs = new StatFs(str);
			long l2 = localStatFs.getBlockSize();
			l1 = localStatFs.getBlockCount() * l2;
			return l1;
		}

	}

	public void clear(Context context) throws Exception {
		File f1 = context.getCacheDir();
		File file = f1.getParentFile();
		File files[] = file.listFiles();
		for (File f : files) {
			f.delete();
		}
		// 是否存在SD卡
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File mobileFile = new File(
					Environment.getExternalStorageDirectory() + File.separator
							+ "mobileSurvey");
			
			if (mobileFile.exists()) {
				for(File temp : mobileFile.listFiles()){
					if("crash".equals(temp.getName())){
						if(temp.isDirectory()){
							for(File childFile : temp.listFiles()){
								childFile.delete() ; //  删除crash文件夹
							}
						}
					}else if("data_search.xml".equals(temp.getName())){
						temp.delete() ; // 删除data_search.xml
					}else if("dd.xml".equals(temp.getName())){
						temp.delete() ; // 删除dd.xml
					}else if("error".equals(temp.getName())){
						if(temp.isDirectory()){
							for(File childFile : temp.listFiles()){
								childFile.delete() ; //  删除error
							}
						}
					}else if("image.xml".equals(temp.getName())){
						temp.delete() ; // 删除image.xml
					}else if("update_loss.xml".equals(temp.getName())){
						temp.delete() ; // 删除update_loss.xml
					}
				}
			}
		}

		/*
		 * PackageManager pm = context.getPackageManager(); Class[] arrayOfClass
		 * = new Class[2]; Class localClass2 = Long.TYPE; arrayOfClass[0] =
		 * localClass2; arrayOfClass[1] = IPackageDataObserver.class; Method
		 * localMethod = pm.getClass().getMethod("freeStorageAndNotify",
		 * arrayOfClass); Long localLong = Long.valueOf(getEnvironmentSize() -
		 * 1L); Object[] arrayOfObject = new Object[2]; arrayOfObject[0] =
		 * localLong; localMethod.invoke(pm,localLong,new
		 * IPackageDataObserver.Stub(){ public void onRemoveCompleted(String
		 * packageName,boolean succeeded) throws RemoteException { 
		 * Auto-generated method stub Log.i("complete","yes"); }});
		 */

	}
	
	//清理数据字典
	public void clearDataDictionary(Context context){
		try {
			//删除数据字典表
			DictDatabase database = new DictDatabase();
			database.clearDic(context);
			//将同步时间置为空
			SharedPreferences sharedPerences = context.getSharedPreferences("base_info",
					context.MODE_WORLD_READABLE);
			sharedPerences.edit().putString(UserCashe.getInstant().getUserName(), "").commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int verssioToInt(String version) {
		int v = 0;
		if (StringUtils.isNotEmpty(version)) {
			version = version.replace(".", "").replace("V", "")
					.replace("v", "");
			try {
				v = Integer.parseInt(version.trim());
			} catch (Exception e) {
				v = 0;
			}
		}
		return v;
	}

	/**
	 * 比较版本
	 * 
	 * @param sysVersion
	 * @param currentVersion
	 * @return
	 */
	public boolean compareVersion(String sysVersion, String currentVersion) {

		if (verssioToInt(sysVersion) > verssioToInt(currentVersion)) {
			return false;

		} else {
			return true;
		}

	}

	/**
	 * 获取列表当中代码
	 * 
	 * @param kindCodeList
	 * @param name
	 * @return
	 */
	public static String getListCode(List<KindCodeData> kindCodeList,
			String name) {
		String insureTermCode = null;
		if (null != kindCodeList && !kindCodeList.isEmpty()) {
			Iterator iter = kindCodeList.iterator();
			while (iter.hasNext()) {
				KindCodeData kindCodeData = (KindCodeData) iter.next();
				if (name.equals(kindCodeData.getInsureTerm())) {
					insureTermCode = kindCodeData.getInsureTermCode();
					break;
				}
			}
		}
		return insureTermCode;
	}

	public static String convertNull(String text) {
		String result = null;
		if (null == text || "".equals(text) || "".equals(text.trim())) {
			result = "";
		} else {
			result = text;
		}
		return result;
	}

	public List<Address> getLocationAdress(Context context, double mLat,
			double mLon) throws Exception {
		List<Address> address = null;
		// private double mLat = 39.982402;
		// private double mLon = 116.305304;
		Geocoder coder = null;
		address = coder.getFromLocation(mLat, mLon, 3);
		if (address != null && address.size() > 0) {
			Address addres = address.get(0);
			String addressName = addres.getAdminArea()
					+ addres.getSubLocality() + addres.getFeatureName() + "附近";
		}
		return address;
	}

	public void getLocation(Context context) {
		final LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
		List<String> lp = manager.getAllProviders();
		for (String item : lp) {
			Log.i("8023", "可用位置服务：" + item);
		}

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度

		// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		final String providerName = manager.getBestProvider(criteria, true);
		Log.i("8023", "------位置服务：" + providerName);

		Location location = manager.getLastKnownLocation(providerName);

		Log.i("8023", "-------" + location);
		// 获取维度信息
		if (location != null) {
			double latitude = location.getLatitude();
			// 获取经度信息
			double longitude = location.getLongitude();
			LogFatory.e("location", "定位方式： " + providerName + "  维度："
					+ latitude + "  经度：" + longitude);
		}

	}

	/**
	 * 比较当前时间不能晚于当天时间
	 * 
	 * @param date
	 *            当前时间(格式:yyyy-MM-dd)
	 * @return true为不晚于当天时间 false为晚于当天时间
	 */
	public static boolean complateTime(String date,Date today) {
		boolean result = false;
		if (StringUtils.isNotEmpty(date)) {
			Date enrollTime = MyUtils.format("yyyy-MM-dd", date);
			long enrollLong = enrollTime.getTime();
			Calendar calendar = Calendar.getInstance();
			int year = today.getYear();
			int month = today.getMonth();
			int day = today.getDate();
//			int year = calendar.get(Calendar.YEAR);
//			int month = calendar.get(Calendar.MONTH) + 1;
//			int day = calendar.get(Calendar.DAY_OF_MONTH);
//			Log.i("MyUtils", year+" "+ month + " " + day);
			calendar.set(year, month + 1, day);
			Date currDate = MyUtils.format("yyyy-MM-dd", year + "-"
					+ ((month < 10) ? "0" + month : month) + "-"
					+ ((day < 10) ? "0" + day : day));
			long currTime = currDate.getTime();
			if (enrollLong < currTime) {
				result = true;
			}
		}
		return true;
	}

	/**
	 * 比较当前时间不能晚于当天时间
	 * 
	 * @param date
	 *            当前时间(格式:yyyy-MM-dd)
	 * @return true为不早于当天时间 false为晚于当天时间
	 */
	public static boolean complateTime(String date, String date2) {
		boolean result = false;
		if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(date2)) {
			Date enrollTime = MyUtils.format("yyyy-MM-dd", date);
			long enrollLong = enrollTime.getTime();

			Date currDate = MyUtils.format("yyyy-MM-dd", date2);
			long currTime = currDate.getTime();
			// test
			Log.i("enrollLong", String.valueOf(enrollLong));
			Log.i("currTime", String.valueOf(currTime));
			if (enrollLong >= currTime) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 比较long（微秒）
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean complateString(String data1, String data2) {
		boolean result = false;
		try {
			Long time1 = Long.parseLong(data1);
			Long time2 = Long.parseLong(data2);
			if (time1 > time2) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public static int getErrorCode(String message) {
		int result = 0;
		if (StringUtils.isNotEmpty(message)
				&& (AppConstants.LOGIN_ERROR.equals(message) || AppConstants.LOGIN_FAIL
						.equals(message))) {
			result = AppConstants.NO_LOGIN_CODE;
		}
		return result;
	}

	private static long lastClickTime; // 最后点击时间

	public static boolean isFastDoubleClick() {

		long time = System.currentTimeMillis();

		long timeD = time - lastClickTime;

		if (0 < timeD && timeD < 2000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
