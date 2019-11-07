package com.sinosoft.ms.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.os.Environment;
import android.util.Log;

import com.sinosoft.ms.activity.task.survey.fragment.SurveyBasicInfoFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyMediaCenterFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyPersonFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyPolicyInfoFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyPropFrag;
import com.sinosoft.ms.activity.task.survey.fragment.SurveyVehicleFrag;
import com.sinosoft.ms.model.DeflossData;
import com.sinosoft.ms.model.KindCodeData;
import com.sinosoft.ms.model.NetInfo;
import com.sinosoft.ms.service.impl.DeflossDataService;
import com.sinosoft.ms.service.impl.KindCodeDataService;

/**
 * 系统名：MobileSurvey 子系统名：应用程序配置常量 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime Sat Jan 06 12:15:00 CST 2013
 */
public class AppConstants
{

	// 版本尺寸
	public static final int SMALLNO = 5;
	// 平台
	public static final int ADAPTER_ASSEMBLY = 2;
	// 修改版本号，请在AndroidManifest.xml中的versionName修改
	// public static final String version = "V2.0.0";
	public static final String PLATFORM = "ANDROID";
	// 5寸应用升级名称
	public static String APP_UPDATE_NAME = "ms5.apk";

	public static final String IMG_STORAGE_DATE = "imgStorageDate"; // 图片保存当天日期
	public static final String IMG_STORAGE_DAY = "imgStorageDay"; // 图片保存天数
	public static final int IMG_DEFAULT_DAY = 0; // 默认保存天数
	public static final String IMG_PATH = "finger";
	// public static final String IMG_PATH = "mobileSurvey" + File.separator
	// + "images"; // 图片文件路径
	public static final String IMAGE_FORMAT = ".jpg";
	public static String send_time = "send_time";// 心跳系统发送次数

	public static final String APP_PATH = "mobileSurvey"; // 应用目录
	public static final String DB_NAME = "mobile_survey";
	public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_PATH + "/";/* 应用数据保存路径 */
	public static final String PATH_CACHE = PATH + "cache/";
	public static final String PACKAGE_NAME_SURVEY_CAMERA = "com.sinosoft.sc";
	// public static final String PATH_DB = PATH + DB_NAME + ".db";/*数据库地址*/
    //-----------------------------------------------------------------------------------------------------------------------------
	public static final  int policyFrag_No =0; // 1.保单信息页面
	public static final  int mediaFrag_No =1;    // 2.影像中心页面
	public static final  int basicFrag_No =2;    // 3.基本信息页面
	public static final  int vehicleFrag_No =3;  // 4.车辆信息页面
	public static final  int propFrag_No =4;     // 5.财产损失页面
	public static final  int peopleFrag_No =5;   // 6.人伤损失页面	
	
	
	// ----------------------------------------------------------------------------------------------------------------------------
	/**
	 * 【测试环境】 内网 10.17.192.30 APN 192.168.192.30 互联网 58.254.177.5
	 * 
	 * 【正式环境】 内网 10.17.192.69 APN 192.168.192.69 互联网 58.254.177.2
	 */
	public static int CONNECT_TIME = 20;// TODO 连接时间20秒

	// 网络类型枚举
	public enum NETWORK_TYPE
	{
		WIFI, APN, THREE_G
	}

	// 系统网络环境
	public enum SYSTEM_TYPE
	{
		OFFICIAL, // 正式环境
		TEST // 测试环境
	}

	// 系统服务器Ip
	public enum IP_TYPE
	{
		OFFICIAL, // 生产服务器
		TEST, // 测试服务器
		LOCAL // 本地
	}

	// ----------------------为方便大家的思路，增加以下变量
	public static String VERSION_NAME = "测试版";

	// TODO 更改环境
	public static SYSTEM_TYPE systemType = SYSTEM_TYPE.OFFICIAL; // 当前环境为正式环境
	///public static SYSTEM_TYPE systemType = SYSTEM_TYPE.TEST; // 当前环境为测试环境
	
	///public static IP_TYPE iptype = IP_TYPE.LOCAL; // 本地
   /// public static IP_TYPE iptype = IP_TYPE.TEST; // 测试服务器
	public static IP_TYPE iptype = IP_TYPE.OFFICIAL; // 生产服务器
	public static final boolean DEBUG = true; // 是否调试程序

	public static NetInfo info = null;// 网络地址的实体
	// 测试环境打印报文
	public static final String LOG_TAG = "MobileSurvey";

	public static void debug(Object info)
	{
		if (info != null && DEBUG)
		{
			// /Log.d(LOG_TAG, info.toString());
			Log.i(LOG_TAG, info.toString());
		}
	}

	/**
	 * 根据参数据取网络URL路径
	 * 
	 * @param systemType
	 *            --系统类型，0--测试环境，1--正式环境
	 * @param networkType
	 *            --网络类型，1--WIFI;2--APN;3--3G 三种网络
	 * @return
	 */
	public static NetInfo getNetInfo(NETWORK_TYPE networkType)
	{
		info = new NetInfo();
		if (SYSTEM_TYPE.TEST == systemType)
		{// 如果是测试环境
			
			info.setDownImagUrl("http://58.17.45.9:7006/hbFileManager/fileManageController/doUpload");
			
			AppConstants.VERSION_NAME = "测试版";
			AppConstants.CONNECT_TIME = 20;
			if (NETWORK_TYPE.WIFI == networkType || NETWORK_TYPE.APN == networkType || NETWORK_TYPE.THREE_G == networkType)
			{// 如果是WIFI网络
				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "测试版。本地";
					info.setUrl("http://10.1.218.109:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.1.218.109:9601/ms/upload.htm");
					info.setDownUrl("http://10.1.218.109:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.1.218.109:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.1.218.109:9601/msplatform/soft/sc5.apk");
				}
				else if (IP_TYPE.OFFICIAL == iptype)
				{
					AppConstants.VERSION_NAME = "测试版。83";
				
					info.setUrl("http://192.168.8.3:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://192.168.8.3:9601/ms/upload.htm");
					info.setDownUrl("http://192.168.8.3:9601/ms/imagedown.htm");
					info.setAppUrl("http://192.168.8.3:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://192.168.8.3:9601/msplatform/soft/sc5.apk");					
					
				}
				else
				{
					AppConstants.VERSION_NAME = "测试版。85";
				
					info.setUrl("http://192.168.8.5:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://192.168.8.5:9601/ms/upload.htm");
					info.setDownUrl("http://192.168.8.5:9601/ms/imagedown.htm");
					info.setAppUrl("http://192.168.8.5:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://192.168.8.5:9601/msplatform/soft/sc5.apk");					
					
				}
				return info;
			}
/*			else if (NETWORK_TYPE.APN == networkType)
			{// 如果是APN网络（通过后台服务设置代理访问）

				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "测试版。本地";
					info.setUrl("http://:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.32.12.33:9601/ms/upload.htm");
					info.setDownUrl("http://10.32.12.33:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.32.12.33:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.32.12.33:9601/msplatform/soft/sc5.apk");
				}

				else
				{
					AppConstants.VERSION_NAME = "测试版。85";
					info.setUrl("http://58.17.45.9:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://58.17.45.9:9601/ms/upload.htm");
					info.setDownUrl("http://58.17.45.9:9601/ms/imagedown.htm");
					info.setAppUrl("http://58.17.45.9:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://58.17.45.9:9601/msplatform/soft/sc5.apk");
				}
				return info;
			}
			else if (NETWORK_TYPE.THREE_G == networkType)
			{// 如果是3G网络

				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "测试版。本地";
					info.setUrl("http://10.32.12.33:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.32.12.33:9601/ms/upload.htm");
					info.setDownUrl("http://10.32.12.33:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.32.12.33:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.32.12.33:9601/msplatform/soft/sc5.apk");
				}
				else
				{
					AppConstants.VERSION_NAME = "测试版。85";
					info.setUrl("http://58.17.45.9:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://58.17.45.9:9601/ms/upload.htm");
					info.setDownUrl("http://58.17.45.9:9601/ms/imagedown.htm");
					info.setAppUrl("http://58.17.45.9:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://58.17.45.9:9601/msplatform/soft/sc5.apk");
				}

				return info;
			}
*/			else
			{
				return null;
			}
		}
		else
		{// 如果是正式环境
			AppConstants.VERSION_NAME = "正式版";
			AppConstants.CONNECT_TIME = 5;
			if (NETWORK_TYPE.WIFI == networkType)
			{// 如果是WIFI网络
				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 。本地";
					info.setUrl("http://10.32.12.33:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.32.12.33:9601/ms/upload.htm");
					info.setDownUrl("http://10.32.12.33:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.32.12.33:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.32.12.33:9601/msplatform/soft/sc5.apk");
				}
				else if (IP_TYPE.TEST == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 (8.3)";
					info.setUrl("http://192.168.8.3:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://192.168.8.3:9601/ms/upload.htm");
					info.setDownUrl("http://192.168.8.3:9601/ms/imagedown.htm");
					info.setAppUrl("http://192.168.8.3:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://192.168.8.3:9601/msplatform/soft/sc5.apk");				
				}
				else
				{
				      info.setUrl("http://epolicy.hbins.com.cn/ms/mobileInteractive.htm");
				      info.setImgUrl("http://epolicy.hbins.com.cn/ms/upload.htm");
				      info.setDownUrl("http://epolicy.hbins.com.cn/ms/imagedown.htm");
				      info.setAppUrl("http://epolicy.hbins.com.cn/msplatform/soft/ms5.apk");
				      info.setScUrl("http://epolicy.hbins.com.cn/msplatform/soft/sc5.apk");

//				      info.setUrl("http://58.17.45.4:9601/ms/mobileInteractive.htm");
//				      info.setImgUrl("http://58.17.45.4:9601/ms/upload.htm");
//				      info.setDownUrl("http://58.17.45.4:9601/ms/imagedown.htm");
//				      info.setAppUrl("http://58.17.45.4:9601/msplatform/soft/ms5.apk");
//				      info.setScUrl("http://58.17.45.4:9601/msplatform/soft/sc5.apk");
				      
				}
				return info;
			}
			else if (NETWORK_TYPE.APN == networkType)
			{// 如果是APN网络
				// APN生产环境地址
				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 本地";
					info.setUrl("http://10.32.12.33:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.32.12.33:9601/ms/upload.htm");
					info.setDownUrl("http://10.32.12.33:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.32.12.33:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.32.12.33:9601/msplatform/soft/sc5.apk");
				}
				else if (IP_TYPE.TEST == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 (8.3)";
					info.setUrl("http://192.168.8.3:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://192.168.8.3:9601/ms/upload.htm");
					info.setDownUrl("http://192.168.8.3:9601/ms/imagedown.htm");
					info.setAppUrl("http://192.168.8.3:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://192.168.8.3:9601/msplatform/soft/sc5.apk");				
				}
				else
				{
				      info.setUrl("http://epolicy.hbins.com.cn/ms/mobileInteractive.htm");
				      info.setImgUrl("http://epolicy.hbins.com.cn/ms/upload.htm");
				      info.setDownUrl("http://epolicy.hbins.com.cn/ms/imagedown.htm");
				      info.setAppUrl("http://epolicy.hbins.com.cn/msplatform/soft/ms5.apk");
				      info.setScUrl("http://epolicy.hbins.com.cn/msplatform/soft/sc5.apk");
				}
				return info;
			}
			else if (NETWORK_TYPE.THREE_G == networkType)
			{// 如果是3G网络
				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 本地";
					info.setUrl("http://10.32.12.33:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.32.12.33:9601/ms/upload.htm");
					info.setDownUrl("http://10.32.12.33:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.32.12.33:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.32.12.33:9601/msplatform/soft/sc5.apk");
				}
				else if (IP_TYPE.TEST == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 (8.3)";
					info.setUrl("http://192.168.8.3:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://192.168.8.3:9601/ms/upload.htm");
					info.setDownUrl("http://192.168.8.3:9601/ms/imagedown.htm");
					info.setAppUrl("http://192.168.8.3:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://192.168.8.3:9601/msplatform/soft/sc5.apk");				
				}
				else
				{
					info.setUrl("http://epolicy.hbins.com.cn/ms/mobileInteractive.htm");
					info.setImgUrl("http://epolicy.hbins.com.cn/ms/upload.htm");
					info.setDownUrl("http://epolicy.hbins.com.cn/ms/imagedown.htm");
					info.setAppUrl("http://epolicy.hbins.com.cn/msplatform/soft/ms5.apk");
					info.setScUrl("http://epolicy.hbins.com.cn/msplatform/soft/sc5.apk");

				}
				return info;
			}
			else
			{
				if (IP_TYPE.LOCAL == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 本地";
					info.setUrl("http://10.32.12.33:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://10.32.12.33:9601/ms/upload.htm");
					info.setDownUrl("http://10.32.12.33:9601/ms/imagedown.htm");
					info.setAppUrl("http://10.32.12.33:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://10.32.12.33:9601/msplatform/soft/sc5.apk");
				}
				else if (IP_TYPE.TEST == iptype)
				{
					AppConstants.VERSION_NAME = "正式版 (8.3)";
					info.setUrl("http://192.168.8.3:9601/ms/mobileInteractive.htm");
					info.setImgUrl("http://192.168.8.3:9601/ms/upload.htm");
					info.setDownUrl("http://192.168.8.3:9601/ms/imagedown.htm");
					info.setAppUrl("http://192.168.8.3:9601/msplatform/soft/ms5.apk");
					info.setScUrl("http://192.168.8.3:9601/msplatform/soft/sc5.apk");				
				}
				else
				{
				      info.setUrl("http://epolicy.hbins.com.cn/ms/mobileInteractive.htm");
				      info.setImgUrl("http://epolicy.hbins.com.cn/ms/upload.htm");
				      info.setDownUrl("http://epolicy.hbins.com.cn/ms/imagedown.htm");
				      info.setAppUrl("http://epolicy.hbins.com.cn/msplatform/soft/ms5.apk");
				      info.setScUrl("http://epolicy.hbins.com.cn/msplatform/soft/sc5.apk");
				}
				return info;
			}
		}
	}

	/* 系统编码 */
	public static final String SYSTEM_ENCODE = "UTF-8";

	/* 登录 */
	public static final String LOGIN = "CA-0001";

	/* 查勘意见文件名称 */
	public static final String SURVEY_FILE = "surveyFeedback.txt";

	/* 定损意见文件名称 */
	public static final String LOSS_FILE = "lossFeedback.txt";

	/* 应用存储目录 */
	public static final String SDCARD_MAIN_DIR = "mobileSurvey";
	/* 推送消息默认设置时间(单位:分钟) */
	public static final int DEFAULT_PUSH_MESSAGE_TIME = 1;
	/**
	 * 异常分类
	 */
	public static final String DB_ERROR = "未获取到SD卡路径";
	public static final String SC_NOT_FOUND = "没有找到匹配的地址";
	public static final String NET_TOME_OUT = "网络连接超时";
	public static final String SC_PARTIAL_CONTENT = "内容不完整";
	public static final String SC_BAD_REQUEST = "错误请求";
	public static final String SC_FORBIDDEN = "禁止访问";
	public static final String SC_INTERNAL_SERVER_ERROR = "服务器内部错误";
	public static final String SYSTEM_EXCEPTION = "系统程序异常";
	public static final boolean IS_SHOW = true;
	public static final String SDCARD_NOT_FIND = "未获取到SD卡路径";
	public static final String DB_NOT_FIND = "数据库未找到";

	// 查勘处理 （用于表示其内部的activity是否加载）
	public static boolean basicInfo = false;// 基本信息
	public static boolean vehicleInvoke = false;// 涉案车辆
	public static boolean propertyDamage = false;// 财产损失
	public static boolean personInjured = false;// 人伤信息
	public static boolean extendedInfo = false;// 查勘扩展
	public static boolean insuranceInfo = false;// 保单信息
	public static boolean liablity = false;//

	public static final int BASIC_INFO = 10000;
	public static final int VECHICLE_INVOKE = 10001;
	public static final int PROPERTY_DAMAGE = 10002;
	public static final int PERSON_INDEJURED = 10003;
	public static final int EXTENDED_INFO = 10004;
	public static final int INSURANCE_INFO = 10005;

	// 任务中心 任务类型
	public static final String SURVEY = "查勘";
	public static final String COMPLETE_SETLOSS = "定损";
	public static final String RETREAT_SETLOSS = "核损退回";
	public static final String PRIVATE_SURVEY = "司内查勘";
	public static final String COUNT_RETREAT_SETLOSS = "理算退回";
	public static final int TYPE_SURVEY = 0; // 查勘
	public static final int TYPE_COMPLETE_SETLOSS = 1; // 定损
	public static final int TYPE_RETREAT_SETLOSS = 3; // 核损退回
	public static final int TYPE_PRIVATE_SURVEY = 4; // 司内查勘
	public static final int TYPE_COUNT_RETREAT_SETLOSS = 5; // 理算退回
	// public static final int TYPE_PRIVATE_SURVEY1=6; //核损
	// public static final String SIMPLE_SETLOSS = "简易定损";

	/** 声明按钮lock **/
	public static boolean LOCK = false;
	/* 临时险别 */
	public static String TEMP_INSUR_CODE = null;
	public static int num = 1;
	/** 登陆错误信息 **/
	public static final String LOGIN_ERROR = "心跳己失效,请重新登录!";
	public static final String LOGIN_FAIL = "心跳设置失败!";
	public static final int NO_LOGIN_CODE = 99;

	public static String cetainLossType;// 定损方式

	/**
	 * 获取本地字典信息配置项
	 * 
	 * @return 字典信息配置项
	 */
	private static Map<String, Map<String, String>> localMap;

	public static Map<String, Map<String, String>> getLocalItemConf()
	{
		if (localMap == null || localMap.isEmpty())
		{
			localMap = new HashMap<String, Map<String, String>>();
			// 是否状态代码
			Map<String, String> map = new HashMap<String, String>();
			map.put("0", "否");
			map.put("1", "是");
			localMap.put("Status", map);
			// 访问模式
			map = new LinkedHashMap<String, String>();
			map.put("inner", "内网");
			map.put("internet", "互联网");
			map.put("apn", "APN");
			localMap.put("accessMode", map);
			// 数量
			map = new LinkedHashMap<String, String>();
			map.put("1", "1");
			map.put("2", "2");
			map.put("3", "3");
			map.put("4", "4");
			map.put("5", "5");
			map.put("6", "6");
			map.put("7", "7");
			map.put("8", "8");
			map.put("9", "9");
			map.put("10", "10");
			localMap.put("CountNum", map);
			// 是否属于商业险责任标识代码
			map = new HashMap<String, String>();
			map.put("1", "属于");
			map.put("2", "不属于");
			localMap.put("ClaimFlag", map);
			// 交强险责任比例标识代码
			map = new HashMap<String, String>();
			map.put("100", "属于");
			map.put("0", "不属于");
			localMap.put("CIIndemDuty", map);

			// 交强险责任标识代码
			map = new HashMap<String, String>();
			map.put("1", "有责");
			map.put("2", "无责");
			localMap.put("CIDutyFlag", map);

			// 交强险责任类型代码
			map = new HashMap<String, String>();
			map.put("100", "有责");
			map.put("0", "无责");
			localMap.put("IndemDutyCI", map);

			// 查勘类别代码
			map = new HashMap<String, String>();
			map.put("0", "司内查勘");
			map.put("1", "公估查勘");
			localMap.put("IntermediaryFlags", map);
			// 是否第一现场查勘标识代码
			map = new HashMap<String, String>();
			map.put("0", "非现场查勘");
			map.put("1", "现场查勘");
			map.put("2", "回勘");
			localMap.put("FirstSiteFlag", map);

			// 查勘基本信息 损失类型
			// 损失类型代码
			map = new HashMap<String, String>();
			map.put("01", "三者车");
			map.put("02", "标的车");
			localMap.put("LossType", map);
			// 损失类型代码
			map = new HashMap<String, String>();
			map.put("010", "三者车");
			map.put("050", "标的车");
			localMap.put("LossItemType", map);
			
			
			// 损失类型代码
			map = new HashMap<String, String>();
			map.put("6", "车上物");
			map.put("7", "三者物");
			localMap.put("ProDateType", map);
			
			
			
			// 人伤险别
			map = new LinkedHashMap<String, String>();
			map.put("02", "商业第三者责任险");
			map.put("BZ", "机动车交通事故责任强制险 ");
			map.put("041", "车上人员责任险（司机）");
			map.put("044", "车上人员责任险（乘客）");
			localMap.put("personDataInsType", map);
			
			// 费用名称
			map = new LinkedHashMap<String, String>();
			map.put("D001", "丧葬费");
			map.put("D002", "残疾赔偿金");
			map.put("D003", "残疾辅助器具费");
			map.put("D004", "交通费");
			map.put("D005", "被扶养人生活费");
			map.put("D006", "住宿费");
			map.put("D007", "精神损害抚慰金");
			map.put("D008", "伤者误工费");
			map.put("D009", "护理费");
			map.put("D010", "死亡补助费");
			map.put("D011", "亲属误工费");
			map.put("D012", "康复费");
			map.put("M001", "医药费");
			map.put("M002", "诊疗费");
			map.put("M003", "住院费");
			map.put("M004", "住院伙食补助费");
			map.put("M005", "后续医疗费");
			map.put("M006", "整容费");
			map.put("M007", "营养费");
			map.put("M009", "鉴定费");
			localMap.put("personDateMoenyType", map);
			
			//证件类型
			map = new LinkedHashMap<String, String>();
			map.put("01", "居民身份证");
			map.put("02", "居民户口薄");
			map.put("03", "驾驶证");
			map.put("04", "军官证");
			map.put("05", "士兵证");
			map.put("06", "军官离退休证");
			map.put("07", "中国护照");
			map.put("08", "异常身份证");
			map.put("41", "港澳通行证");
			map.put("42", "台湾通行证");
			map.put("43", "回乡证");
			map.put("51", "外国护照");
			map.put("52", "旅行证");
			map.put("53", "居留证件");
			map.put("71", "组织机构代码证");
			map.put("72", "税务登记证");
			map.put("73", "营业执照");
			map.put("99", "其他证件");
			localMap.put("certificatesType", map);			
			
			//性别
			map = new LinkedHashMap<String, String>();
			map.put("0", "未知");
			map.put("1", "男");
			map.put("2", "女");
			map.put("9", "未说明");
			localMap.put("sexType", map);	
			
			//是否死亡
			map = new LinkedHashMap<String, String>();
			map.put("0", "否");
			map.put("1", "是");
			localMap.put("dieType", map);			
			
			//人伤
			map = new HashMap<String, String>();
			map.put("3", "本车司机");
			map.put("4", "本车人员 ");
			map.put("5", "三者人员");
			localMap.put("personDataBDType", map);
			
			
			// 损失类型代码
			map = new HashMap<String, String>();
			map.put("02", "商业第三者责任险");
			map.put("BZ", "机动车交通事故责任强制险 ");
			map.put("08", "车上货物责任险");
			localMap.put("ProDateInsType", map);
			
			// 损失费用名称
			map = new HashMap<String, String>();
			map.put("03", "物损");
			localMap.put("ProDateWuType", map);		
			
			
			// 损失情况代码
			map = new HashMap<String, String>();
			map.put("0", "无损失");
			map.put("1", "有损失");
			localMap.put("DamageFlag", map);
			// 保单交强/商业标志代码
			map = new HashMap<String, String>();
			map.put("1", "商业险");
			map.put("2", "交强险");
			localMap.put("PolicyFlag", map);
			// 定损类别代码
			map = new HashMap<String, String>();
			map.put("0", "司内定损");
			map.put("1", "公估定损");
			localMap.put("IntermediaryFlag", map);
			/*
			 * //换件价格方案代码(价格方案编码) map = new HashMap<String,String>();
			 * map.put("001", "4S店价格 （原厂价、厂方指导价、特约维修站价格）"); map.put("002",
			 * "一类修理厂价格（市场价、普通修理厂价格）"); map.put("003", "二类修理厂价格（适用价、普通修理厂价格）");
			 * localMap.put("ChgCompSetCode", map);
			 */
			// 换件价格方案代码(价格方案编码)
			map = new HashMap<String, String>();
			map.put("001", "特约维修厂");
			map.put("002", "一类维修厂");
			map.put("003", "二类维修厂");
			localMap.put("ChgCompSetCode", map);
			// 修理项目代码(工种代码)
			map = new HashMap<String, String>();
			map.put("01", "钣金");
			map.put("02", "喷漆");
			map.put("03", "机修");
			map.put("04", "电工");
			map.put("05", "拆装");
			map.put("06", "其它");
			localMap.put("RepairCode", map);
			// 修理项目受损程度代码
			map = new HashMap<String, String>();
			map.put("1", "轻度受损");
			map.put("2", "中度受损");
			map.put("3", "重度受损");
			localMap.put("LevelCode", map);

			// 商业险责任比例
			map = new HashMap<String, String>();
			map.put("全责", "100");
			map.put("主责", "70");
			map.put("同责", "50");
			map.put("次责", "30");
			map.put("无责", "0");
			localMap.put("Rate", map);

			map = new HashMap<String, String>();
			map.put("1", "存在");
			map.put("2", "不存在");
			localMap.put("Property", map);
		}
		return localMap;
	}

	public static Map<String, String> getInsuranceMap(String taskId)
	{
		Map<String, String> insureTermCodeMap = new HashMap<String, String>();
		try
		{
			DeflossDataService deflossDataService = new DeflossDataService();
			DeflossData deflossData = deflossDataService.getByTaskId(taskId);
			KindCodeDataService kindCodeDataService = new KindCodeDataService();

			List kindCodeList = kindCodeDataService.getById(deflossData.getId());

			if (null != kindCodeList && !kindCodeList.isEmpty())
			{
				Iterator iter = kindCodeList.iterator();
				while (iter.hasNext())
				{
					KindCodeData kindCodeData = (KindCodeData) iter.next();
					insureTermCodeMap.put(kindCodeData.getInsureTermCode(), kindCodeData.getInsureTerm());
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (insureTermCodeMap.isEmpty())
		{
			insureTermCodeMap.put("02", "商业第三者责任险");
			insureTermCodeMap.put("BZ", "机动车交通事故责任强制险");
		}
		return insureTermCodeMap;
	}
	
	/*****
	 *  SELECT t.codecode,t.codecname FROM ggcode t where t.codetype = 'ClaimDocumentType';
	 *  SELECT c.codecode codecode,c.codecname codecname,t.Outercode 
    	FROM ggcodetransfer t
  	 	inner join (SELECT * FROM ggcode where codetype='DocCode') c
    	on t.innercode = c.codecode
   		where t.configcode = 'DocCode'
    	and t.outercodetype = 'ClaimDocumentType'
	 *  
	 * ******/
	public static String getOutercodeByCodecode(String codecode){
		
		if(StringUtils.isEmpty(codecode)){
			return null;
		}
		
		boolean b = codecode.startsWith("A000");
		if(b){
			return "A000";
		}
		b = codecode.startsWith("A01");
		if(b){
			return "A01";
		}
		b = codecode.startsWith("A02");
		if(b){
			return "A02";
		}
		b = codecode.startsWith("A03");
		if(b){
			return "A03";
		}	
		b = codecode.startsWith("A04");
		if(b){
			return "A04";
		}	
		b = codecode.startsWith("A05");
		if(b){
			return "A05";
		}	
		
		b = codecode.startsWith("A06");
		if(b){
			return "A06";
		}	
		b = codecode.startsWith("A09");
		if(b){
			return "A09";
		}	
		b = codecode.startsWith("CAR0");
		if(b){
			return "C001";
		}
		b = codecode.startsWith("CAR1");
		if(b){
			return "C002";
		}
		b = codecode.startsWith("CAR2");
		if(b){
			return "C003";
		}
		b = codecode.startsWith("CAR3");
		if(b){
			return "C004";
		}		
		b = codecode.startsWith("CAR4");
		if(b){
			return "C005";
		}
		b = codecode.startsWith("CAR5");
		if(b){
			return "C006";
		}
		b = codecode.startsWith("P00");
		if(b){
			return "PA06";
		}		
		
		return null;
	}

	/**
	 * 根据当前数据库版本号与最新数据库版本号，更新数据库表结构
	 * 
	 * @param currentVersion
	 * @param newVersion
	 * @return
	 */
	public static List<String> getUpdateSql(int currentVersion, int newVersion)
	{
		List<String> list = new ArrayList<String>();
		String sql = "ALTER TABLE `defloss_data` ADD `dispatch_type` VARCHAR( 10 );";
		list.add(sql);
		sql = "ALTER TABLE `loss_vehicle` ADD `licenseColorCodeTo2` VARCHAR( 10 );";
		list.add(sql);
		sql = "ALTER TABLE `loss_vehicle` ADD `model_name` VARCHAR( 10 );";
		list.add(sql);
		sql = "ALTER TABLE `loss_assist_info_item` ADD `jyId` VARCHAR(36);";
		list.add(sql);
		sql = "ALTER TABLE `loss_assist_info_item` ADD `registId` VARCHAR(36);";
		list.add(sql);
		sql = "ALTER TABLE `loss_fit_info_item` ADD `jyId` VARCHAR(36);";
		list.add(sql);
		sql = "ALTER TABLE `loss_fit_info_item` ADD `registId` VARCHAR(36);";
		list.add(sql);
		sql = "ALTER TABLE `loss_repair_info_item` ADD `jyId` VARCHAR(36);";
		list.add(sql);
		sql = "ALTER TABLE `loss_repair_info_item` ADD `registId` VARCHAR(36);";
		list.add(sql);
		sql = "ALTER TABLE `defloss_data` ADD `registId` VARCHAR(36);";
		list.add(sql);
		
		sql = "ALTER TABLE `prop_data` ADD `propId` VARCHAR(36);";
		list.add(sql);
		
		sql = "ALTER TABLE `prop_data` ADD `checkPropId` VARCHAR(36);";
		list.add(sql);
		
		sql = "ALTER TABLE `person_data` ADD `createOwner` VARCHAR(36);";
		list.add(sql);
		
		sql = "ALTER TABLE `person_data` ADD `modfiyOwner` VARCHAR(36);";
		list.add(sql);
		return list;
	}

	/**
	 * 将InputStream转换成String
	 * 
	 * @param is
	 *            InputStream
	 * @return
	 */
	public static String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			AppConstants.debug("转换异常1" + e);
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				AppConstants.debug("转换异常2" + e);
			}
		}
		return sb.toString();
	}
}
