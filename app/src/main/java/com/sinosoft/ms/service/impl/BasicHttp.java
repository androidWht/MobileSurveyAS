package com.sinosoft.ms.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.net.HttpClientUtil;
import com.sinosoft.ms.utils.xml.IRequestXml;
import com.sinosoft.ms.utils.xml.IResponseXml;
import com.sinosoft.ms.utils.xml.RequestXml;
import com.sinosoft.ms.utils.xml.ResponseXml;

/**
 * 系统名：移动查勘定损 子系统名：基础HTTP类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午4:23:34
 */

public abstract class BasicHttp
{
	public IResponseXml responseXML = null;
	public IRequestXml requestXml = null;
	public InputStream inputStream = null;
	public HttpClientUtil http = null;

	public BasicHttp()
	{
		if (null == responseXML)
		{
			responseXML = new ResponseXml();
		}
		if (null == requestXml)
		{
			requestXml = new RequestXml();
		}
		if (null == http)
		{
			http = new HttpClientUtil();
		}
	}

	protected boolean sendRequestTest(String xmlText) throws Exception
	{
		// TODO 打印请求报文
		AppConstants.debug("requestURL:" + AppConstants.info.getUrl());
		AppConstants.debug("request:" + xmlText);
		// inputStream = HttpClientUtil.postMethodRequestTest(AppConstants.URL,
		// new StringBuffer(xmlText),"UTF-8");
		inputStream = HttpClientUtil.postMethodRequestTest(AppConstants.info.getUrl(), new StringBuffer(xmlText), "UTF-8");
		if (inputStream == null)
		{
			return false;
		}
		else
		{
			// TODO 打印服务器返回报文
			String responseStr = AppConstants.convertStreamToString(inputStream);
			AppConstants.debug("response:" + responseStr);
			inputStream = new ByteArrayInputStream(responseStr.getBytes("UTF-8"));
		}
		return true;
	}

	/**
	 * 发送请求
	 * 
	 * @param xmlText
	 *            XML报文
	 * @throws Exception
	 */
	protected void sendRequest(String xmlText) throws Exception
	{
		// TODO 打印请求报文
		AppConstants.debug("requestURL:" + AppConstants.info.getUrl());
		AppConstants.debug("request:" + xmlText);
		// inputStream = HttpClientUtil.postMethodRequest(AppConstants.URL, new
		// StringBuffer(xmlText),"UTF-8");
		inputStream = HttpClientUtil.postMethodRequest(AppConstants.info.getUrl(), new StringBuffer(xmlText), "UTF-8");
		if (inputStream == null)
		{
			throw new IllegalArgumentException("服务器无响应信息！");
		}
		else
		{
			// TODO 打印服务器返回报文
			String responseStr = AppConstants.convertStreamToString(inputStream);
			AppConstants.debug("response:" + responseStr);
			inputStream = new ByteArrayInputStream(responseStr.getBytes("UTF-8"));
		}
	}

	protected void sendRequestForGBK(String xmlText) throws Exception
	{
		// TODO 打印请求报文
		AppConstants.debug("requestURL:" + AppConstants.info.getUrl());
		AppConstants.debug("request:" + xmlText);
		// String url = AppConstants.URL ;
		String url = AppConstants.info.getUrl();
		// url = url.substring(0, url.indexOf("/ms")) ;
		// url += "/cliamservice/servlet/ClaimInterface" ;
		inputStream = HttpClientUtil.postMethodRequest(url, new StringBuffer(xmlText), "GBK");
		if (inputStream == null)
		{
			throw new IllegalArgumentException("服务器无响应信息！");
		}
		else
		{
			// TODO 打印服务器返回报文
			String responseStr = AppConstants.convertStreamToString(inputStream);
			AppConstants.debug("response:" + responseStr);
			inputStream = new ByteArrayInputStream(responseStr.getBytes("GBK"));
		}
	}

	protected void destroy()
	{
		responseXML = null;
		requestXml = null;
		try
		{
			if (null != inputStream)
			{
				inputStream.close();
				inputStream = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		http = null;
	}

	/**
	 * @param type
	 * @param imageId
	 * @return
	 */
	public String getImageXml(int type, String imageId)
	{
		return null;
	}

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

}
