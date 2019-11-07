package com.sinosoft.ms.utils.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.sinosoft.ms.model.Item;
import com.sinosoft.ms.utils.StringUtils;

public class RepairFactoryParse {
	public String responseType;
	public String errorMessage;
	public String responseCode;
	
	private List<Item> result ;
	private Item item;
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
		@Override
		public void startDocument() throws SAXException {
			result = new ArrayList<Item>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			//tagName = qName;
			if(tagName.equals("item")){
				item = new Item();
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			if (tagName != null) {
				String date = new String(ch, start, length);
				if(tagName.equals("code")){
					item.setCode(StringUtils.add(item.getCode(),date));
				}else if(tagName.equals("value")){
					item.setValue(StringUtils.add(item.getValue(), date));
				}else if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
					responseCode = date;
//					result = Integer.parseInt(date);
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
			if (tagName.equals("item")) {
				result.add(item);
			}
			tagName = null;
		}
	}

	public List<Item> getResult() {
		return result;
	}
	
}
