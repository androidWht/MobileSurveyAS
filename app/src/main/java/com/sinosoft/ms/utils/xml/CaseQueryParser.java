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

import com.sinosoft.ms.model.po.NodeData;
import com.sinosoft.ms.utils.StringUtils;

public class CaseQueryParser {
	public static String responseType;
	public static String errorMessage;
	public static String responseCode;

	private List<NodeData> nodeDataList;
	private NodeData nodeData;
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
			nodeDataList = new LinkedList<NodeData>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("node")) {
				nodeData = new NodeData();
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
				if (tagName.equals("taskId")) {
					nodeData.setTaskId(StringUtils.add(nodeData.getTaskId(), data));
				} else if (tagName.equals("preTaskId")) {
					nodeData.setPreTaskId(StringUtils.add(nodeData.getPreTaskId(), data));
				} else if (tagName.equals("nodeId")) {
					nodeData.setNodeId(StringUtils.add(nodeData.getNodeId(), data));
				} else if (tagName.equals("nodeCName")) {
					nodeData.setNodeCName(StringUtils.add(nodeData.getNodeCName(), data));
				} else if (tagName.equals("state")) {
//					String state="";
//					if(data.equals("0")){
//						state="待处理";
//					}else if(data.equals("1")){
//						state="已处理";
//					}else if(data.equals("2")){
//						state="正在处理";
//					}
					nodeData.setState(data);
					
					
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
			if (tagName.equals("node")) {
				nodeDataList.add(nodeData);
			}
			tagName = null;
		}
	}

	/**
	 * @return
	 */
	public List<NodeData> getNodeList() {
		return nodeDataList;
	}
}
