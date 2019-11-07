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

import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.Item;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 
 * 子系统名：影像中心服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 			CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class DicInfoParser {
	public String responseType;
	public String errorMessage;
	public String responseCode;
	private String tagName;

	private List<DicInfoBean> list;
	private DicInfoBean dicInfo;
	private List<Item> itemList;
	private Item item;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); // 調用靜態方法newInstance得到SAXParseFactory實例
		SAXParser saxParser = saxParserFactory.newSAXParser(); // 調用newSAXParser創建SAXParser對象
		XMLReader xmlReader = saxParser.getXMLReader(); // 獲得XMLReader對象
		xmlReader.setContentHandler(new FeedbackHandler()); // 設置處理XML的Handler
		xmlReader.parse(new InputSource(inputStream)); // 將InputStream裝飾為InputSource，進行解析
	}

	private class FeedbackHandler extends DefaultHandler {
		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("dicts")) {
				list = new ArrayList<DicInfoBean>();
			} else if (tagName.equals("dict")) {
				dicInfo = new DicInfoBean();
			} else if (tagName.equals("items")) {
				itemList = new ArrayList<Item>();
			} else if (tagName.equals("item")) {
				item = new Item();
			}
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
				} else if (tagName.equals("type")) {
					dicInfo.setType(StringUtils.add(dicInfo.getType(), date));
				} else if (tagName.equals("name")) {
					dicInfo.setName(StringUtils.add(dicInfo.getName(), date));
				} else if (tagName.equals("code")) {
					item.setCode(StringUtils.add(item.getCode(), date));
				} else if (tagName.equals("value")) {
					item.setValue(StringUtils.add(item.getValue(), date));
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
			String tag=qName;
			if (tag != null) {
				if (tag.equals("dict")) {
					list.add(dicInfo);
				} else if (tagName.equals("items")) {
					dicInfo.setList(itemList);
				} else if (tagName.equals("item")) {
					itemList.add(item);
				}
			}
			tagName = null;
		}
	}

	public List<DicInfoBean> getResult() {
		if ("0".equals(responseCode)){
			throw new IllegalArgumentException(errorMessage);
		}
		return list;
	}

}
