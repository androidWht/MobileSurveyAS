package com.sinosoft.ms.service.impl;

import java.net.SocketTimeoutException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.sinosoft.ms.model.PolicyData;
import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.model.TaskBean;
import com.sinosoft.ms.service.ITaskCenterService;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.db.TaskDatabase;
import com.sinosoft.ms.utils.xml.RequestXml;
import com.sinosoft.ms.utils.xml.WorkStatusParse;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 上午11:44:58
 */

public class TaskCenterService extends BasicHttp implements ITaskCenterService
{
	private Context context;
	private TaskDatabase database;

	public TaskCenterService()
	{
	}

	public TaskCenterService(Context context)
	{
		this.context = context;
	}

	@Override
	public List<RegistData> getTaskCenterListByType(int type) throws Exception
	{
		List list = null;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.requestTaskListXML(type); // 发送任务中心请求，获取服务器数据
			sendRequest(xmlText); // 解析任务中心列表
			// Log.e("the xml is",new Utility().getString(inputStream));
			 //new FileUtils().write2SDFromInput("", "task.xml", inputStream);
			list = responseXML.parserTaskList(inputStream);
			// 任务中心列表数据保存
			// putSqlite(list); //获取数据库任务中心列表、
			// list=getDBTaskCenterList();

		}
		catch (SocketTimeoutException e)
		{
			throw new Exception("连接超时");
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
			database = null;
		}
		return list;
	}

	/*
	 * 根据报案号后几位数字搜索
	 */
	@Override
	public List<RegistData> getTaskCenterListByType(int type, String registNo) throws Exception
	{
		List list = null;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.requestTaskListXML(type, registNo); // 发送任务中心请求，获取服务器数据
			sendRequest(xmlText); // 解析任务中心列表
			// Log.e("the xml is",new Utility().getString(inputStream));
			// new FileUtils().write2SDFromInput("", "task.xml", inputStream);
			list = responseXML.parserTaskList(inputStream);
			// 任务中心列表数据保存
			// putSqlite(list); //获取数据库任务中心列表、
			// list=getDBTaskCenterList();
		}
		catch (SocketTimeoutException e)
		{
			throw new Exception("连接超时");
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
			database = null;
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.ITaskCenterService#getTaskCenterListByTypeRegistNo(int, java.lang.String)
	 */
	@Override
	public List<PolicyData> getTaskCenterListByTypeRegistNo(int type, String registNo) throws Exception
	{
		// TODO Auto-generated method stub
		List list = null;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.requestTaskListXML(type, registNo); // 发送任务中心请求，获取服务器数据
			sendRequest(xmlText); // 解析任务中心列表
			// Log.e("the xml is",new Utility().getString(inputStream));
			// new FileUtils().write2SDFromInput("", "task.xml", inputStream);
			list = responseXML.parserPolicyList(inputStream);
			// 任务中心列表数据保存
			// putSqlite(list); //获取数据库任务中心列表、
			// list=getDBTaskCenterList();
		}
		catch (SocketTimeoutException e)
		{
			throw new Exception("连接超时");
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
			///database = null;
		}
		return list;

		///return null;
	}


	/**
	 * 解析任务中心列表
	 * 
	 * @param inputStream
	 *            服务器响应资源
	 * @return 任务中心列表数据
	 */
	/*
	 * private List parseTaskCenterList() { List<TaskBean> list=null;
	 * if(inputStream!=null){ TaskCenterListXMLParser parserXML=new
	 * TaskCenterListXMLParser(); list=parserXML.getTaskList(inputStream);
	 * 
	 * }
	 * 
	 * // TODO Auto-generated method stub return list; }
	 */

	/**
	 * 任务中心列表数据保存
	 * 
	 * @param list
	 *            任务中心列表信息
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
	 * 获取数据库任务中心列表
	 * 
	 * @return 数据库任务中心列表
	 */
	private List<RegistData> getDBTaskCenterList() throws Exception
	{
		if (database == null)
		{
			database = new TaskDatabase(context);
		}
		return database.selectTaskList("task", null, "reportTime");
	}

	/**
	 * 
	 * @param registData
	 * @param refusalReason
	 * @return
	 * @throws Exception
	 */
	@Override
	public int rejectTtask(RegistData registData, String refusalReason) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = new RequestXml().workStatusXML(registData, refusalReason);
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}
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

	/*
	 * @Override public int rejectTtask(BasicTask basicTask, String
	 * refusalReason) throws Exception { try {
	 * 
	 * // 取得拒绝任务的xml String xml = requestXmlFactory.requestRejectTaskXML(
	 * basicTask.getReportNo(), refusalReason); // 请求数据 sendRequest(xml); //
	 * responseFactoryXMLParser.parserRejectTask(inputStream);
	 * 
	 * 
	 * } catch (Exception e) { throw new Exception(e.getMessage());
	 * 
	 * } finally { destroy(); }
	 * 
	 * return 0; }
	 */
	@Override
	public int receivingTask(RegistData registData) throws Exception
	{

		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, "");
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();

			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw e;
		}
		finally
		{
			destroy();
		}
		return result;

	}

	@Override
	public int archiveTask(RegistData registData) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, "");
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw e;
		}
		finally
		{
			destroy();
		}
		return result;
	}

	@Override
	public TaskBean lookTask(String reportNo) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int reassignedTask(RegistData registData, String changeReason) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, changeReason);
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}

	@Override
	public int arrivalProcessTask(RegistData registData, String currAddr) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, currAddr);
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sinosoft.ms.service.ITaskCenterService#revokeTask(com.sinosoft.ms
	 * .model.RegistData)
	 */
	@Override
	public int revokeTask(RegistData registData, String revokeReason) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, revokeReason);
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}

	public int changePCSurvey(RegistData registData, String reason) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, reason);
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isEmpty(workStatusParse.errorMessage))
				{
					throw new Exception("操作失败");
				}
				else
				{
					throw new Exception(workStatusParse.errorMessage);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}

	public int phoneContactTask(RegistData registData, String reason) throws Exception
	{
		int result = 0;
		try
		{
			// 加载任务中心的xml的文件
			String xmlText = requestXml.workStatusXML(registData, reason);
			// 发送任务中心请求，获取服务器数据
			sendRequest(xmlText);
			// 解析任务中心列表
			WorkStatusParse workStatusParse = new WorkStatusParse();
			workStatusParse.parse(inputStream);
			result = workStatusParse.getResult();
			if (0 == result)
			{
				if (StringUtils.isNotEmpty(workStatusParse.errorMessage))
				{
					throw new Exception(workStatusParse.errorMessage);
				}
				else
				{
					throw new Exception("服务器返回错误信息为空");
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.e("error", "" + e.getMessage());
			throw new Exception(e.getMessage());
		}
		finally
		{
			destroy();
		}
		return result;
	}


}
