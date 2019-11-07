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

import com.sinosoft.ms.model.VehicleType;
import com.sinosoft.ms.utils.StringUtils;

public class VehicleTypeParse {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;
	
	private List<VehicleType> list = null;
	// 构建Brand对象
	private VehicleType bean;
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
			list = new ArrayList<VehicleType>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("vehicleType")) {
				bean = new VehicleType();
			}
			tagName = qName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			if (tagName != null) {
				String date = new String(ch, start, length);
				if (tagName.equals("modelName")) {
					bean.setModelName(StringUtils.add(bean.getModelName(), date));
				} else if (tagName.equals("tradeName")) {
					bean.setTradeName(StringUtils.add(bean.getTradeName(), date));
				} else if (tagName.equals("carName")) {
					bean.setCarName(StringUtils.add(bean.getCarName(), date));
				} else if (tagName.equals("categoryName")) {
					bean.setCategoryName(StringUtils.add(bean.getCategoryName(), date));
				} else if (tagName.equals("seat")) {
					bean.setSeat(StringUtils.add(bean.getSeat(), date));
				} else if (tagName.equals("price")) {
					bean.setPrice(StringUtils.add(bean.getPrice(), date));
				} else if (tagName.equals("volume")) {
					bean.setVolume(StringUtils.add(bean.getVolume(), date));
				} else if (tagName.equals("description")) {
					bean.setDescription(StringUtils.add(bean.getDescription(), date));
				} else if (tagName.equals("modelYear")) {
					bean.setModelYear(StringUtils.add(bean.getModelYear(), date));
				} else if (tagName.equals("remark")) {
					bean.setRemark(StringUtils.add(bean.getRemark(), date));
				} else if (tagName.equals("vehCertainCode")) {
					bean.setVehCertainCode(StringUtils.add(bean.getVehCertainCode(), date));
				} else if (tagName.equals("vehCertainName")) {
					bean.setVehCertainName(StringUtils.add(bean.getVehCertainName(), date));
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
			if (tagName.equals("vehicleType")) {
				list.add(bean);
			}
			tagName = null;
		}

	}

	public List<VehicleType> getResult() {
		return list;
	}
	
}
