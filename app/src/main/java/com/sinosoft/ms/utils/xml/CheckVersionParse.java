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

import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘定损 
 * 子系统名：影像中心服务接口 
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public class CheckVersionParse {
	
	private String responseType;
	private String errorMessage;
	private String responseCode;
	
	private List<AppInfoBean> apps;
	private AppInfoBean app;
	private String tagName;

	public void parse(InputStream inputStream)
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance(); 
		SAXParser saxParser = saxParserFactory.newSAXParser(); 
		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setContentHandler(new LoginFeedbackHandler()); 
		xmlReader.parse(new InputSource(inputStream));
	}

	private class LoginFeedbackHandler extends DefaultHandler {
		@Override
		public void startDocument() throws SAXException {
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			
			tagName = localName.length()!=0 ? localName : qName;
			if (tagName.equals("softs")) {
				apps = new ArrayList<AppInfoBean>() ;
			}else if (tagName.equals("soft")) {
				app = new AppInfoBean() ;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
			
			String date = new String(ch, start, length);
			
			if (null != tagName && null != date) {
				
				if(tagName.equals("sys")){
					app.setSys(StringUtils.add(app.getSys(), date)) ;
				}else if(tagName.equals("version")){
					app.setVersion(StringUtils.add(app.getVersion(), date)) ;
				}else if(tagName.equals("name")){
					app.setName(StringUtils.add(app.getName(), date)) ;
				}else if(tagName.equals("address")){
					app.setAddress(StringUtils.add(app.getAddress(), date)) ;
				}else if(tagName.equals("force")){
					app.setForce(StringUtils.add(app.getForce(), date)) ;
				}else if(tagName.equals("remark")){
					app.setRemark(StringUtils.add(app.getRemark(), date));
				}else if (tagName.equals("responseType")) {
					responseType = date;
				} else if (tagName.equals("responseCode")) {
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
			if(tagName != null){ 
				if (tagName.equals("soft")) {
					apps.add(app) ;
				}
			}
			
			tagName = null;
		}
	}
	
	public List<AppInfoBean> getResult() throws Exception {
		if(responseCode == "0"){
			throw new Exception(errorMessage) ;
		}
		if(app == null){
			app = new AppInfoBean() ;
			app.init() ;
		}
		apps.add(app) ; // 临时写法
		return this.apps ;
	}
}
