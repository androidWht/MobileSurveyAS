package com.sinosoft.ms.service.impl;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.sinosoft.ms.model.BasicSurvey;
import com.sinosoft.ms.model.CarData;
import com.sinosoft.ms.model.ImageCenterBean;
import com.sinosoft.ms.model.LiabilityRatio;
import com.sinosoft.ms.model.PersonData;
import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.PropData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.RegistThirdCarData;
import com.sinosoft.ms.model.SurveyInfo;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.sub.BasicTask;
import com.sinosoft.ms.model.sub.ReportBean;
import com.sinosoft.ms.model.sub.SurveyBean;
import com.sinosoft.ms.service.ICaseCenterService;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.db.TaskDatabase;
import com.sinosoft.ms.utils.xml.CurrencyParse;
import com.sinosoft.ms.utils.xml.SurveyInfoParser;
import com.sinosoft.ms.utils.xml.TaskCenterListParse;

/**
 * 系统名：移动查勘定损 子系统名：案件中心（） 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午9:33:04
 */

public class CaseCenterService extends BasicHttp implements ICaseCenterService
{
	private Context context;
	private TaskDatabase database;

	public CaseCenterService(Context context)
	{
		this.context = context;
	}

	@Override
	public List<RegistData> getCaseCenterListByType(int type) throws Exception
	{
		List list = null;
		try
		{
			// 加载案件中心的xml的文件
			String xmlText = requestXml.requestCaseCenterListXML(type);
			// 发送案件中心请求，获取服务器数据
			sendRequest(xmlText); // 解析案件中心列表
			TaskCenterListParse parser = new TaskCenterListParse();
			parser.parse(inputStream);
			LogFatory.d("responseCode", parser.responseCode + "");
			if (!parser.responseCode.trim().equals("1"))
			{
				throw new Exception("案件中心任务，" + parser.errorMessage);
			}
			list = parser.getResult();// 案件中心列表数据保存
			// putSqlite(list);
			// 获取数据库案件中心列表、

			// list = getDBTaskCenterList();

		}
		catch (Exception e)
		{
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
			database = null;
		}
		return list;
	}

	// /**
	// * 解析案件中心列表
	// *
	// * @param inputStream
	// * 服务器响应资源
	// * @return 案件中心列表数据
	// */
	// private List parseTaskCenterList() {
	// List<TaskBean> list = null;
	// if (inputStream != null) {
	// CaseCenterListXMLParser parserXML = new CaseCenterListXMLParser();
	// list = parserXML.getCaseList(inputStream);
	//
	// }
	// return list;
	// }

	/**
	 * 案件中心列表数据保存
	 * 
	 * @param list
	 *            案件中心列表信息
	 */
	private void putSqlite(List<RegistData> list)
	{
		if (list != null && !list.isEmpty())
		{
			database = new TaskDatabase(context);
			database.insertIntoTaskList("task", list);
		}
	}

	/**
	 * 获取数据库案件中心列表
	 * 
	 * @return 数据库案件中心列表
	 */
	private List<RegistData> getDBTaskCenterList() throws Exception
	{
		if (database == null)
		{
			database = new TaskDatabase(context);
		}
		return database.selectTaskList("caseTask", null, "reportTime");
	}

	@Override
	public int report(ReportBean reportBean) throws Exception
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int maintenanceTasks(BasicTask basicTask, String remark) throws Exception
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int cancelCase(BasicTask basicTask, String someReasons) throws Exception
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int surveyProcess(SurveyBean surveyBean) throws Exception
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 查勘信息 查询
	 * 
	 * @param reportNo
	 *            报案号
	 */
	public SurveyInfo querySurveyInfo(String reportNo, String registId) throws Exception
	{
		SurveyInfo surveyInfo = null;
		try
		{
			// 加载案件中心的xml的文件
			String xmlText = requestXml.requestCaseCenterSurveyInfoXML(reportNo, registId);
			// 发送案件中心请求，获取服务器数据
			sendRequest(xmlText); // 解析案件中心列表
			SurveyInfoParser parser = new SurveyInfoParser();
			// new FileUtils().write2SDFromInput("", "query.xml",
			// inputStream);
			try
			{
				parser.parse(inputStream, reportNo);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			surveyInfo = parser.getSurveyInfo();
			if (!parser.responseCode.trim().equals("1"))
			{
				throw new Exception("" + parser.errorMessage);
			}

		}
		catch (Exception e)
		{
			Log.e("error", "" + e.getMessage());
			LogRecoder.wirteException("HTTP", e);
			throw e;
		}
		finally
		{
			destroy();
		}
		return surveyInfo;
	}

	@Override
	public int updateSurveyInfo(String registId, BasicSurvey basicSurvet, List<RegistThirdCarData> registThirdCarDatas, List<LiabilityRatio> liabilityRatios, List<CarData> carDatas, List<PropData> propDatas, List<PersonData> personDatas, List<PolicyData> policyDatas) throws Exception
	{
		int result = 0;
		try
		{
			String xmlText = requestXml.updateCaseCenterSurveyInfoXML(registId, basicSurvet, registThirdCarDatas, liabilityRatios, carDatas, propDatas, personDatas, policyDatas);
			// if(!xmlText.equals(""))
			// return 0;
			sendRequest(xmlText);
			new FileUtils().write2SDFromString("", "update_survey", xmlText);

			CurrencyParse currencyParse = new CurrencyParse();
			// new FileUtils().write2SDFromInput("", "survey_response.xml",
			// inputStream);
			if (inputStream == null && inputStream.available() <= 0)
			{
				throw new IllegalArgumentException("服务器无返回");
			}
			currencyParse.parse(inputStream);
			result = currencyParse.getResult();
			if (result == 0)
			{
				Log.e("reponse", currencyParse.getErrorMessage() + "");
				throw new Exception("" + currencyParse.getErrorMessage());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;

	}

	public int updateImage(List<ImageCenterBean> imageDatas) throws Exception
	{
		int result = 0;
		try
		{

			User user = UserCashe.getInstant().getUser();
			String xmlText = requestXml.updateImageXml(user, imageDatas);
			sendRequest(xmlText);
			// new FileUtils().write2SDFromString("", "image.xml", xmlText);
			CurrencyParse currencyParse = new CurrencyParse();
			currencyParse.parse(inputStream);
			result = currencyParse.getResult();
			if (result == 0)
			{
				throw new IllegalArgumentException(currencyParse.getErrorMessage() + "");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}

	public int updateDocuments(String registNo) throws Exception
	{
		int result = 1;
		try
		{

			User user = UserCashe.getInstant().getUser();
			String xmlText = requestXml.updateDocumentsXml(user, registNo);
			sendRequestForGBK(xmlText);
			// CurrencyParse currencyParse = new CurrencyParse();
			// currencyParse.parse(inputStream);
			// result = currencyParse.getResult();
			// if(result==0){
			// throw new
			// IllegalArgumentException(currencyParse.getErrorMessage()+"");
			// }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}
}
