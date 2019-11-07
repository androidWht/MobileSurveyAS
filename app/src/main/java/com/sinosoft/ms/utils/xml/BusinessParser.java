package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.sinosoft.ms.model.Business;
import com.sinosoft.ms.model.BusinessFactory;
import com.sinosoft.ms.model.DateTimeFactory;
import com.sinosoft.ms.model.News;
import com.sinosoft.ms.model.NewsFactory;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名: 心跳请求响应XML解释 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION
 * SYSTEMS CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class BusinessParser
{
	private String responseType;
	private String errorMessage;
	private String responseCode;

	private Business business;
	private News news;
	private String tagName;

	public void parse(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new FeedbackHandler());
		xmlReader.parse(new InputSource(inputStream));
	}

	private class FeedbackHandler extends DefaultHandler
	{
		@Override
		public void startDocument() throws SAXException
		{
			NewsFactory.getInstance().setNewsList(new ArrayList<News>());
			business = new Business();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length() != 0 ? localName : qName;
			if (tagName.equals("news"))
			{
				news = new News();
			}
			tagName = qName;
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
				}
				else if (tagName.equals("responseCode"))
				{
					responseCode = date;
					LogFatory.d("responseCode", "" + tagName + ":" + date);
				}

				// 保存任务数
				// if (tagName.equals("surveyTaskCount")) {
				// business = BusinessFactory.getInstance();
				// business.setSurveyTaskCount(date);
				// } else if (tagName.equals("lossTaskCount")) {
				// business.setLossTaskCount(date);
				//
				// } else if (tagName.equals("receiveTaskCount")) {
				// business.setReceiveTaskCount(date);
				//
				// } else if (tagName.equals("notReceiveTaskCount")) {
				// business.setNotReceiveTaskCount(date);
				//
				// }
				if (tagName.equals("news"))
				{
					news.setInfo(StringUtils.add(news.getInfo(), date));
				}
				if (tagName.equals("sysDateTime"))
				{ // 2014/2/28 14:21 新增获取系统时间
					DateTimeFactory factory = DateTimeFactory.getInstance();
					factory.setDateTime(date);
					Log.i("dateTime", factory.getDateTime().toString());
				}

				if ("0".equals(responseCode) && tagName.equals("errorMessage"))
				{
					errorMessage = date;
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

	public Business getResult() throws Exception
	{
		if (StringUtils.isEmpty(responseCode) || !responseCode.equals("1"))
		{
			throw new Exception("登陆失败:" + errorMessage);
		}
		return business;
	}

	public String getResponseType()
	{
		return responseType;
	}

}
