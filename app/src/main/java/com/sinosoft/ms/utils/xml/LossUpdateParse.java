package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sinosoft.ms.utils.LogFatory;

/**
 * 系统名：移动查勘定损 
 * 子系统名：定损更新解释 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class LossUpdateParse {
	public  String responseType;
	public  String errorMessage="";
	public  String responseCode;

	private int result;
	private String tagName;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); 
		SAXParser saxParser = saxParserFactory.newSAXParser(); 
		XMLReader xmlReader = saxParser.getXMLReader(); 
		xmlReader.setContentHandler(new LossUpdateHandler()); 
		xmlReader.parse(new InputSource(inputStream));
	}

	private class LossUpdateHandler extends DefaultHandler {
		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			tagName = qName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			String date = new String(ch, start, length);

			if (null != tagName && null != date) {
				if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
					responseCode = date;
					result = Integer.parseInt(responseCode);
				}
				if ("0".equals(responseCode) && tagName.equals("errorMessage")) {
					errorMessage += date;
					LogFatory.d("lossUpdateParse", String.valueOf(errorMessage));
					
				}
				
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			tagName = null;
		}
	}

	public int getResult() {
		return result;
	}

}
