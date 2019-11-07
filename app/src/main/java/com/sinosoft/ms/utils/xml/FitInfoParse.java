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

import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.utils.StringUtils;

public class FitInfoParse {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;
	
	private List<LossFitInfoItem> list;
	// 构建Brand对象
	private LossFitInfoItem bean;
	// 用来存放每次遍历后的元素名称(节点名称)
	private String tagName;

	public void parse(InputStream inputStream) throws ParserConfigurationException,
			SAXException, IOException {
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
			list = new ArrayList<LossFitInfoItem>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (qName.equals("fitInfo")) {
				bean = new LossFitInfoItem();
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
				} else if (tagName.equals("originalId")) {
					bean.setOriginalId(StringUtils.add(bean.getOriginalId(), date));
				} else if (tagName.equals("originalName")) {
					bean.setOriginalName(StringUtils.add(bean.getOriginalName(), date));
				} else if (tagName.equals("partId")) {
					bean.setPartId(StringUtils.add(bean.getPartId(), date));
				} else if (tagName.equals("partStandardCode")) {
					bean.setPartStandardCode(StringUtils.add(bean.getPartStandardCode(), date));
				} else if (tagName.equals("partStandard")) {
					bean.setPartStandard(StringUtils.add(bean.getPartStandard(), date));
				} else if (tagName.equals("partGroupCode")) {
					bean.setPartGroupCode(StringUtils.add(bean.getPartGroupCode(), date));
				} else if (tagName.equals("partGroupName")) {
					bean.setPartGroupName(StringUtils.add(bean.getPartGroupName(), date));
				} else if (tagName.equals("lossCount")) {
					bean.setLossCount(Integer.parseInt(date));
				} else if (tagName.equals("remark")) {
					bean.setRemark(StringUtils.add(bean.getRemark(), date));
				} else if (tagName.equals("refPrice1")) {
					bean.setRefPrice1(Double.parseDouble(date));
				} else if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
					responseCode = date;
				} 
				if("0".equals(responseCode) && tagName.equals("errorMessage")){
					errorMessage = date;
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			tagName = localName.length()!=0 ? localName : qName;
			if (qName.equals("fitInfo")) {
				list.add(bean);
			}
			tagName = null;
		}

	}

	public List<LossFitInfoItem> getResult() {
		return list;
	}
	
}
