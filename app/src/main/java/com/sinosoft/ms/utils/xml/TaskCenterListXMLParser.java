package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * 系统名：移动查勘定损 子系统名：登录界面控制 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author sinosoft
 * @createTime 下午2:35:04
 */

public class TaskCenterListXMLParser
{
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;
	List list = new ArrayList();

	public List getTaskList(InputStream in)
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try
		{

			SAXParser parser = factory.newSAXParser();
			parser.parse(in, handler);
			// initList();//假设解析的数据

		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	DefaultHandler handler = new DefaultHandler()
	{

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			Log.d("start element", qName);

		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			Log.d("parser :", new String(ch, start, length));

		};

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{
			Log.d("end element", "<\\" + qName + ">");

		}

	};

}
