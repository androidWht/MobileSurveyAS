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

import com.sinosoft.ms.model.RegistData;
import com.sinosoft.ms.utils.StringUtils;

public class DataSearchParser {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private List<RegistData> taskList;
	// 构建Brand对象
	private RegistData bean;
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new BrandListHandler());
		xmlReader.parse(new InputSource(inputStream));
	}

	private class BrandListHandler extends DefaultHandler {
		// 只调用一次 初始化list集合
		@Override
		public void startDocument() throws SAXException {
			taskList = new ArrayList<RegistData>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (qName.equals("data")) {
				bean = new RegistData();
			}
			tagName = qName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			if (tagName != null) {
				String date = new String(ch, start, length);
				if (tagName.equals("registNo")) {
					bean.setRegistNo(StringUtils.add(bean.getRegistNo(), date));
				} else if(tagName.equals("registId")){
					bean.setRegistId(StringUtils.add(bean.getRegistId(), date));
				} else if (tagName.equals("taskType")) {
					bean.setTaskType(Integer.parseInt(date));
				} else if (tagName.equals("taskId")) {
					bean.setTaskId(StringUtils.add(bean.getTaskId(), date));
				} else if (tagName.equals("reportorName")) {
					bean.setReportorName(StringUtils.add(bean.getReportorName(), date));
				} else if (tagName.equals("reportorNumber")) {
					bean.setReportorNumber(date);
				} else if (tagName.equals("damageAddress")) {
					bean.setDamageAddress(StringUtils.add(bean.getDamageAddress(), date));
				} else if (tagName.equals("damageName")) {
					bean.setDamageName(StringUtils.add(bean.getDamageName(), date));
				} else if (tagName.equals("damageDate")) {
					bean.setDamageDate(StringUtils.add(bean.getDamageDate(), date));
				} else if (tagName.equals("reportDate")) {
					bean.setReportDate(StringUtils.add(bean.getReportDate(), date));
				} else if(tagName.equals("remark")){
					bean.setRemark(date);
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
			if (qName.equals("data")) {
				taskList.add(bean);
			}
			tagName = null;
		}

	}

	public List<RegistData> getResult() {
		return taskList;
	}

}
