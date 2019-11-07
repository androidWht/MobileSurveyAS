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

import com.sinosoft.ms.model.LookImage;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 
 * 子系统名：查看影像XML解析 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 18:10:24
 */
public class LookImageDataParser {
	public String responseType;
	public String errorMessage;
	public String responseCode;

	private List<LookImage> result;
	private LookImage lookImage;
	private String tagName;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); 
		SAXParser saxParser = saxParserFactory.newSAXParser(); 
		XMLReader xmlReader = saxParser.getXMLReader(); 
		xmlReader.setContentHandler(new FeedbackHandler()); 
		xmlReader.parse(new InputSource(inputStream));
	}

	private class FeedbackHandler extends DefaultHandler {
		@Override
		public void startDocument() throws SAXException {
			result = new ArrayList<LookImage>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if("image".equals(tagName)){
				lookImage = new LookImage();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			String date = new String(ch, start, length);
			if (null != tagName && null != date) {
				if (tagName.equals("codeType")) {
					lookImage.setCodeType(StringUtils.add(lookImage.getCodeType(), date));
				} else if (tagName.equals("imageId")) {
					lookImage.setImageId(StringUtils.add(lookImage.getImageId(), date));
				}else if (tagName.equals("responseType")) {
					responseType = date;
				}else if (tagName.equals("responseCode")) {
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
			if("image".equals(tagName)){
				result.add(lookImage);
			}
			tagName = null;
		}
	}

	public List<LookImage> getResult() {
		if("0".equals(responseCode)){
			throw new IllegalArgumentException(errorMessage);
		}
		return result;
	}

}
