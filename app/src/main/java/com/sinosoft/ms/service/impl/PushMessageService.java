package com.sinosoft.ms.service.impl;

import java.util.List;

import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.FeedbackMessage;
import com.sinosoft.ms.model.News;
import com.sinosoft.ms.model.NewsFactory;
import com.sinosoft.ms.model.PushMessage;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.IPushMessageService;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.xml.BusinessParser;

/**
 * 系统名：移动查勘定损 子系统名：推送消息服务接口实现 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午9:52:12
 */
public class PushMessageService extends BasicHttp implements IPushMessageService
{

	@Override
	public FeedbackMessage send(PushMessage pushMessage) throws Exception
	{
		FeedbackMessage feedbackMessage = null;
		try
		{
			User user = UserCashe.getInstant().getUser();
			String xml = requestXml.requestTaskNumXML(user);
			sendRequest(xml);
			
			BusinessParser parser = new BusinessParser();
			parser.parse(inputStream);
			// 任务数
			Business business = parser.getResult();
			List<News> newsList = NewsFactory.getInstance().getNewsList();
			if (newsList.size() > 0)
			{
				// BusinessFactory.setBusiness(business);

				// String notReceiveTaskCount =
				// business.getNotReceiveTaskCount();
				// String receiveTaskCount = business.getReceiveTaskCount();
				// String SurveyTaskCount = business.getSurveyTaskCount();
				// String lossTaskCount = business.getLossTaskCount();

				String taskStr = new NewsFactory().getInstance().getNewsString();

				// taskStr.append("您现在有" + notReceiveTaskCount + "个待接收任务，");
				// taskStr.append(receiveTaskCount + "个待处理任务,其中");
				// taskStr.append(SurveyTaskCount + "个查勘任务,");
				// taskStr.append(lossTaskCount + "个定损任务");

				feedbackMessage = new FeedbackMessage();
				feedbackMessage.setRadioMessage(taskStr);

				// if (StringUtils.toInt(notReceiveTaskCount) > 0
				// || StringUtils.toInt(receiveTaskCount) > 0
				// || StringUtils.toInt(SurveyTaskCount) > 0
				// || StringUtils.toInt(lossTaskCount) > 0) {
				if (newsList.size() > 0)
				{
					feedbackMessage.setNotify(true);

				}
				else
				{
					feedbackMessage.setNotify(false);
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
		}
		return feedbackMessage;
	}

}
