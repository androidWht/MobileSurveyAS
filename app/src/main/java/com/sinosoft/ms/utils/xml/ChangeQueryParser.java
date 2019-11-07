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

import com.sinosoft.ms.model.LossFitInfoItem;
import com.sinosoft.ms.utils.StringUtils;

public class ChangeQueryParser {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private List<LossFitInfoItem> lossFitInfoItemList;
	private LossFitInfoItem lossFitInfoItem;
	
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
			lossFitInfoItemList = new LinkedList<LossFitInfoItem>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (qName.equals("lossFitInfoItem")) {
				lossFitInfoItem = new LossFitInfoItem();
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
//定损基本信息
				if (tagName.equals("SerialNo")) {
					lossFitInfoItem.setSerialNo(Long.parseLong(data));
				} else if (tagName.equals("OriginalId")) {
					lossFitInfoItem.setOriginalId(StringUtils.add(lossFitInfoItem.getOriginalId(), data));
				} else if (tagName.equals("OriginalName")) {
					lossFitInfoItem.setOriginalName(StringUtils.add(lossFitInfoItem.getOriginalName(), data));
				} else if (tagName.equals("PartId")) {
					lossFitInfoItem.setPartId(StringUtils.add(lossFitInfoItem.getPartId(), data));
				} else if (tagName.equals("PartStandardCode")) {
					lossFitInfoItem.setPartStandardCode(StringUtils.add(lossFitInfoItem.getPartStandardCode(), data));
				} else if (tagName.equals("PartStandard")) {
					lossFitInfoItem.setPartStandard(StringUtils.add(lossFitInfoItem.getPartStandard(), data));
				} else if (tagName.equals("PartGroupCode")) {
					lossFitInfoItem.setPartGroupCode(StringUtils.add(lossFitInfoItem.getPartGroupCode(), data));
				} else if (tagName.equals("PartGroupName")) {
					lossFitInfoItem.setPartGroupName(StringUtils.add(lossFitInfoItem.getPartGroupName(), data));
				} else if (tagName.equals("LossCount")) {
					lossFitInfoItem.setLossCount(Long.parseLong(data));
				} else if (tagName.equals("LossPrice")) {
					lossFitInfoItem.setLossPrice(Double.parseDouble(data));
				} else if (tagName.equals("ChgRefPrice")) {
					lossFitInfoItem.setChgRefPrice(Double.parseDouble(data));
				} else if (tagName.equals("Remnant")) {
					lossFitInfoItem.setRemnant(Double.parseDouble(data));
				} else if (tagName.equals("SelfConfigFlag")) {
					lossFitInfoItem.setSelfConfigFlag(StringUtils.add(lossFitInfoItem.getSelfConfigFlag(), data));
				} else if (tagName.equals("IfRemain")) {
					lossFitInfoItem.setIfRemain(StringUtils.add(lossFitInfoItem.getIfRemain(), data));
				} else if (tagName.equals("ChgCompSetCode")) {
					lossFitInfoItem.setChgCompSetCode(StringUtils.add(lossFitInfoItem.getChgCompSetCode(), data));
				} else if (tagName.equals("Status")) {
					lossFitInfoItem.setStatus(StringUtils.add(lossFitInfoItem.getStatus(), data));
				} else if (tagName.equals("Remark")) {
					lossFitInfoItem.setRemark(StringUtils.add(lossFitInfoItem.getRemark(), data));
				} else if (tagName.equals("InsureTermCode")) {
					lossFitInfoItem.setInsureTermCode(StringUtils.add(lossFitInfoItem.getInsureTermCode(), data));
				} else if (tagName.equals("InsureTerm")) {
					lossFitInfoItem.setInsureTerm(StringUtils.add(lossFitInfoItem.getInsureTerm(), data));
				} else if (tagName.equals("ChgLocPrice")) {
					lossFitInfoItem.setChgLocPrice(Double.parseDouble(data));
				} else if (tagName.equals("LocPrice1")) {
					lossFitInfoItem.setLocPrice1(Double.parseDouble(data));
				} else if (tagName.equals("LocPrice2")) {
					lossFitInfoItem.setLocPrice2(Double.parseDouble(data));
				} else if (tagName.equals("LocPrice3")) {
					lossFitInfoItem.setLocPrice3(Double.parseDouble(data));
				} else if (tagName.equals("RefPrice1")) {
					lossFitInfoItem.setRefPrice1(Double.parseDouble(data));
				} else if (tagName.equals("RefPrice2")) {
					lossFitInfoItem.setRefPrice2(Double.parseDouble(data));
				} else if (tagName.equals("RefPrice3")) {
					lossFitInfoItem.setRefPrice3(Double.parseDouble(data));
				} else if (tagName.equals("responseType")) {
					responseType = data;
				} else if (tagName.equals("responseCode")) {
					responseCode = data;
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
			if (qName.equals("lossFitInfoItems")) {
				lossFitInfoItemList.add(lossFitInfoItem);
			}
			tagName = null;
		}
	}

	public List<LossFitInfoItem> getLossFitInfoItemList() {
		return lossFitInfoItemList;
	}

}
