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

import com.sinosoft.ms.model.AgeingStatBean;
import com.sinosoft.ms.utils.StringUtils;


/**
 * 系统名：移动查勘定损 
 * 子系统名：登录界面控制
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 下午2:54:35
 */

public class PrescriptionListParser {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private List<AgeingStatBean> taskList;
	// 构建Brand对象
	private AgeingStatBean bean;
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new MyParserHandler());
		xmlReader.parse(new InputSource(inputStream));
	}

	private class MyParserHandler extends DefaultHandler {
		// 只调用一次 初始化list集合
		@Override
		public void startDocument() throws SAXException {
			taskList = new ArrayList<AgeingStatBean>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("ageingStat")) {
				bean = new AgeingStatBean();
			}
			tagName = qName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			if (tagName != null) {
				String date = new String(ch, start, length);
				if (tagName.equals("processingStage")) {
					bean.setProcessingStage(StringUtils.add(bean.getProcessingStage(), date));
				} else if (tagName.equals("receiptTime")) {
					bean.setReceiptTime(StringUtils.add(bean.getReceiptTime(), date));
				} else if (tagName.equals("startTime")) {
					bean.setStartTime(StringUtils.add(bean.getStartTime(), date));
				} else if (tagName.equals("endTime")) {
					bean.setEndTime(StringUtils.add(bean.getEndTime(), date));
				} else if (tagName.equals("processingResults")) {
					bean.setProcessingResults(StringUtils.add(bean.getProcessingResults(), date));
				} else if (tagName.equals("timeConsumption")) {
					bean.setTimeConsumption(StringUtils.add(bean.getTimeConsumption(), date));
				} else if (tagName.equals("status")) {
					bean.setStatus(StringUtils.add(bean.getStatus(), date));
				} else if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
					responseCode = date;
				}
				if ("0".equals(responseCode) && tagName.equals("errorMessage")) {
					errorMessage = date;
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("ageingStat")) {
				taskList.add(bean);
			}
			tagName = null;
		}

	}

	public List<AgeingStatBean> getResult() {
		return taskList;
	}
}
