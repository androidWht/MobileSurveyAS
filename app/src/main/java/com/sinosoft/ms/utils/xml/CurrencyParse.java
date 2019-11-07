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

import com.sinosoft.ms.utils.StringUtils;
/**
 * 通用XML解释类
 */
public class CurrencyParse {
	private String responseType;
	private String errorMessage="";
	private String responseCode;

	private int result = 0;
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
			String date2 = new String(ch, start, length);
			if (tagName != null) {
				String date = new String(ch, start, length);
				if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
					responseCode = date;
				}
				if ("0".equals(responseCode) && tagName.equals("errorMessage")) {
					errorMessage += date;
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			super.endElement(uri, localName, qName);
			tagName = localName.length()!=0 ? localName : qName;
			tagName = null;
		}

	}

	public int getResult() {
		if (StringUtils.isNotEmpty(responseCode)) {
			result = Integer.parseInt(responseCode);
		}
		
		return result;
	}

	public String getResponseType() {
		return responseType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}
	
}
