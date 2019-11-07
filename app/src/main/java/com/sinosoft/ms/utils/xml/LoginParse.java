package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.News;
import com.sinosoft.ms.model.NewsFactory;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：影像中心服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class LoginParse
{
	private String responseType;
	public String errorMessage;
	public String responseCode;
	private Business business;

	private DateTimeFactory dateTimeFactory;
	private AppInfoBean app;
	private String sys = "";
	private News news;
	private String tagName;

	public void parse(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new LoginFeedbackHandler());
		xmlReader.parse(new InputSource(inputStream));
	}

	private class LoginFeedbackHandler extends DefaultHandler
	{
		@Override
		public void startDocument() throws SAXException
		{
			NewsFactory.getInstance().setNewsList(new ArrayList<News>());
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length() != 0 ? localName : qName;
			tagName = qName;
			if (tagName.equals("business"))
			{
				business = new Business();
			}
			if (tagName.equals("news"))
			{
				news = new News();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			super.characters(ch, start, length);
			String date = new String(ch, start, length);
			if (null != tagName && null != date)
			{
				if (tagName.equals("responseType"))
				{
					responseType = date;
					// LogFatory.d("login", "" + tagName + ":" + date);
				}
				else if (tagName.equals("responseCode"))
				{
					responseCode = date;
					LogFatory.d("responseCode", "" + tagName + ":" + date);
				}
				if (date.trim().equals(AppConstants.PLATFORM))
				{
					sys = date;
				}
				else if (date.trim().equals("IPAD"))
				{
					sys = "";
				}

				// 保存版本信息
				if (sys.trim().equals(AppConstants.PLATFORM))
				{
					LogFatory.d(AppConstants.PLATFORM, "" + tagName + ":" + date);
					if (tagName.equals("sys"))
					{
						app = new AppInfoBean();
						app.setSys(StringUtils.add(app.getSys(), date));
					}
					if (tagName.trim().equals("version"))
					{
						app.setVersion(StringUtils.add(app.getVersion(), date));
					}
					else if (tagName.equals("address"))
					{
						app.setAddress(StringUtils.add(app.getAddress(), date));
					}
					else if (tagName.equals("force"))
					{
						app.setForce(StringUtils.add(app.getForce(), date));
					}
				}
				// 保存任务数
				if (business != null)
				{

					if (tagName.equals("sessionNo"))
					{
						business.setSessionNo(StringUtils.add(business.getSessionNo(), date));

					}
					else if (tagName.equals("deptNo"))
					{
						business.setDeptNo(StringUtils.add(business.getDeptNo(), date));
						// }else if (tagName.equals("surveyTaskCount")) {
						// business.setSurveyTaskCount(StringUtils.add(business.getSurveyTaskCount(),
						// date));
						//
						// } else if (tagName.equals("receiveTaskCount")) {
						// business.setReceiveTaskCount(StringUtils.add(business.getReceiveTaskCount(),
						// date));
						//
						// } else if (tagName.equals("notReceiveTaskCount")) {
						// business.setNotReceiveTaskCount(StringUtils.add(business.getNotReceiveTaskCount(),
						// date));
						//
						// }else if (tagName.equals("lossTaskCount")){
						// business.setLossTaskCount(StringUtils.add(business.getLossTaskCount(),
						// date));
					}
					else if (tagName.equals("synTime"))
					{
						business.setSynTime(StringUtils.add(business.getSynTime(), date));
					}
					else if (tagName.equals("userName"))
					{
						business.setUserName(StringUtils.add(business.getUserName(), date));
					}
					else if (tagName.equals("news"))
					{
						news.setInfo(StringUtils.add(news.getInfo(), date));
					}
				}

				// 2014/2/28 14:21 新增获取系统时间
				if (tagName.equals("sysDateTime"))
				{
					DateTimeFactory factory = dateTimeFactory.getInstance();
					factory.setDateTime(date);
					Log.i("dateTime", factory.getDateTime().toString());
				}

				if ("0".equals(responseCode) && tagName.equals("errorMessage"))
				{
					errorMessage = date;
					// LogFatory.d("login", "" + tagName + ":" + date);
				}

			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			super.endElement(uri, localName, qName);
			tagName = localName.length() != 0 ? localName : qName;
			if (tagName.equals("news"))
			{
				NewsFactory.getInstance().addNews(news);
			}
			tagName = null;
		}
	}

	public Business getResult()
	{
		return business;
	}

	public String getResponseType()
	{
		return responseType;
	}

	public AppInfoBean getApp()
	{
		return app;
	}

	public String getResponseCode()
	{
		return responseCode;
	}

}
