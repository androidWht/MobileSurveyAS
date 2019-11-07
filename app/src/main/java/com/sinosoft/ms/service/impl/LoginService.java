package com.sinosoft.ms.service.impl;

import java.util.List;

import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.sub.TaskTag;
import com.sinosoft.ms.service.ILoginService;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.FileUtils;
import com.sinosoft.ms.utils.StringUtils;
import com.sinosoft.ms.utils.xml.CheckVersionParse;
import com.sinosoft.ms.utils.xml.LoginParse;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 下午5:25:40
 */

public class LoginService extends BasicHttp implements ILoginService
{

	@Override
	public boolean login(User user, TaskTag taskTag) throws Exception
	{
		boolean result = false;
		try
		{
			String xml = requestXml.requestUserXML(user);
			sendRequest(xml);
			LoginParse parser = new LoginParse();
			parser.parse(inputStream);

			if (StringUtils.isEmpty(parser.responseCode) || parser.responseCode.equals("0"))
			{
				if (StringUtils.isNotEmpty(parser.errorMessage))
				{
					throw new IllegalArgumentException(parser.errorMessage);
				}
				else
				{
					throw new IllegalArgumentException("登录失败，服务器无返回错误信息");
				}
			}

			Business business = parser.getResult();
			// 任务数
			if (business != null)
			{
				BusinessFactory.setBusiness(business);

			}
			else
			{
				throw new IllegalArgumentException("查询任务失败");
			}
			result = true;
		}
		catch (IllegalArgumentException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IllegalArgumentException("登录失败");
		}
		finally
		{

		}
		return result;
	}

	@Override
	public List<AppInfoBean> checkVersion() throws Exception
	{

		List<AppInfoBean> apps = null;
		try
		{
			String xml = requestXml.requestVersionXML();
			sendRequest(xml);

			CheckVersionParse parser = new CheckVersionParse();
			parser.parse(inputStream);
			apps = parser.getResult();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			AppConstants.debug(e);
			throw new IllegalArgumentException("更新失败，请检测网络状态");
		}
		return apps;
	}

	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.ILoginService#exitLogin()
	 */
	@Override
	public boolean exitLogin(User user) throws Exception
	{
		boolean result = false;
		try
		{
			String xml = requestXml.requestExitLogin(user);
			sendRequest(xml);
			LoginParse parser = new LoginParse();
			parser.parse(inputStream);

			if (StringUtils.isEmpty(parser.responseCode) || parser.responseCode.equals("0"))
			{
				if (StringUtils.isNotEmpty(parser.errorMessage))
				{
					throw new IllegalArgumentException(parser.errorMessage);
				}
				else
				{
					throw new IllegalArgumentException("退出失败，服务器无返回错误信息");
				}
			}

			result = true;
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{

		}
		return result;

	}

}
