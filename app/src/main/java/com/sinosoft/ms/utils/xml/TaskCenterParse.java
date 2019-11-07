package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
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

import com.sinosoft.ms.model.RegistData;

/**
 * 系统名：移动查勘定损 子系统名：任务中心XML解析 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 18:10:24
 */
public class TaskCenterParse
{
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private List<RegistData> result;
	private String tagName;

	public void parse(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException
	{
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); // 調用靜態方法newInstance得到SAXParseFactory實例
		SAXParser saxParser = saxParserFactory.newSAXParser(); // 調用newSAXParser創建SAXParser對象
		XMLReader xmlReader = saxParser.getXMLReader(); // 獲得XMLReader對象
		xmlReader.setContentHandler(new FeedbackHandler()); // 設置處理XML的Handler
		xmlReader.parse(new InputSource(inputStream)); // 將InputStream裝飾為InputSource，進行解析
	}

	private class FeedbackHandler extends DefaultHandler
	{
		// 只调用一次 初始化list集合
		@Override
		public void startDocument() throws SAXException
		{
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length() != 0 ? localName : qName;
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
			tagName = null;
		}
	}

	public List<RegistData> getResult()
	{
		return result;
	}

}
