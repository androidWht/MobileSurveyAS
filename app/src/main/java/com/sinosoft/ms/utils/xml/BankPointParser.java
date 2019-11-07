package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sinosoft.ms.model.BankInfo;
import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.utils.LogFatory;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 子系统名：定损银行网点 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author Hijack
 * @createTime 下午2:49:25
 */

public class BankPointParser {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private BankInfo bankInfo;
	private List<BankInfo> bankInfoList;
	private String tagName;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new BankPointHandler());
		xmlReader.parse(new InputSource(inputStream));
	}

	private class BankPointHandler extends DefaultHandler {
		@Override
		public void startDocument() throws SAXException {
			bankInfoList = new LinkedList<BankInfo>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("point")) {
				bankInfo = new BankInfo();
			}
			tagName = qName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			if (tagName != null) {
				String data = new String(ch, start, length);
				if(null==data || "".equals(data)){
					return ;
				}
				if (tagName.equals("bankNumber")) {
					bankInfo.setBankNumber(StringUtils.add(bankInfo.getBankNumber(), data));
//					bankInfo.setBankNumber(data);
				} else if (tagName.equals("bankOutlets")) {
					bankInfo.setBankOutlets(StringUtils.add(bankInfo.getBankOutlets(), data));
//					bankInfo.setBankOutlets(data);
				}
				if ("0".equals(responseCode) && tagName.equals("errorMessage")) {
					errorMessage = data;
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("point")) {
				bankInfoList.add(bankInfo);
			}
			tagName = null;
		}

	}

	public List<BankInfo> getBankInfoList() {
		return bankInfoList;
	}
}
