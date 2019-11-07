package com.sinosoft.ms.utils.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.LogRecoder;
import com.sinosoft.ms.utils.StringUtils;

/**
 * 
 * HttpClient 应用的实现类，提供了http协议PostMethod、GetMethod等的使用
 * 
 * @author huliping
 * @version 1.0
 * 
 */
public class HttpClientUtil
{
	// private static Log log = LogFactory.getLog(HttpClientUtil.class);
	private static HttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
	private static HttpClient httpClient;
	static
	{
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setMaxTotalConnections(200);
		params.setDefaultMaxConnectionsPerHost(30);
		httpConnectionManager.setParams(params);

		httpClient = new HttpClient(httpConnectionManager);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(500 * 1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(500 * 1000);// TODO
																					// 超时时间为50秒

	}

	public static InputStream postMethodRequestTest(String requestURL, StringBuffer requestContext, String charset) throws Exception
	{
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(AppConstants.CONNECT_TIME * 1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(AppConstants.CONNECT_TIME * 1000);
		InputStream resultStream = null;
		PostMethod postMethod = null;
		BufferedInputStream bufferedInputStream = null;
		try
		{
			postMethod = new PostMethod(requestURL);
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); // 设置请求参数
			bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(requestContext.toString().getBytes(charset)));
			InputStreamRequestEntity streamRequestEntity = new InputStreamRequestEntity(bufferedInputStream);
			postMethod.setRequestEntity(streamRequestEntity);
			httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK)
			{
				resultStream = postMethod.getResponseBodyAsStream();
			}
			else
			{
				throw new IllegalArgumentException(setStatusMsg(postMethod.getStatusCode()));
			}
		}
		catch (HttpException he)
		{
			throw new IllegalArgumentException("请检查服务是否已启动!");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			LogRecoder.wirteException(ioe);
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			if (postMethod != null)
			{
				postMethod = null;
			}
			try
			{
				if (bufferedInputStream != null)
				{
					bufferedInputStream.close();
					bufferedInputStream = null;
				}
			}
			catch (Exception ioExp)
			{
			}
		}
		return resultStream;
	}

	/**
	 * 发送服务请求，请求方式以参数的方式传
	 * 
	 * @param requestURL
	 *            请求的地址
	 * @param charset
	 *            字符集
	 * @param requestContext
	 *            请求内容
	 * @return InputStream
	 * @throws BusinessServiceException
	 */
	public static InputStream postMethodRequest(String requestURL, StringBuffer requestContext, String charset) throws Exception
	{
		///httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(200 * 1000);// TODO
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(600 * 1000);// TODO
																							// 20秒
		///httpClient.getHttpConnectionManager().getParams().setSoTimeout(200 * 1000);// TODO
																					// 20秒
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(600 * 1000);// TODO
		if (StringUtils.isEmpty(requestURL))
		{
			throw new IllegalArgumentException("请求地址为空");
		}
		if (null == requestContext)
		{
			throw new IllegalArgumentException("请求内容为空");
		}
		if (StringUtils.isEmpty(charset))
		{
			charset = "UTF-8";
		}
		InputStream resultStream = null;
		PostMethod postMethod = null;
		BufferedInputStream bufferedInputStream = null;
		try
		{
			postMethod = new PostMethod(requestURL);
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); // 设置请求参数
			bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(requestContext.toString().getBytes(charset)));
			InputStreamRequestEntity streamRequestEntity = new InputStreamRequestEntity(bufferedInputStream);
			postMethod.setRequestEntity(streamRequestEntity);

			httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK)
			{
				// postMethod.getResponseBody();
				resultStream = postMethod.getResponseBodyAsStream();
			}
			else
			{
				throw new IllegalArgumentException(setStatusMsg(postMethod.getStatusCode()));
			}
		}
		catch (HttpException he)
		{
			throw new IllegalArgumentException("请检查服务是否已启动!");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			LogRecoder.wirteException(ioe);
			// throw new IllegalArgumentException("网络连接异常!");
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			// 释放对象
			if (postMethod != null)
			{
				// postMethod.releaseConnection();
				postMethod = null;
			}
			try
			{
				if (bufferedInputStream != null)
				{
					bufferedInputStream.close();
					bufferedInputStream = null;
				}
			}
			catch (Exception ioExp)
			{
			}
		}
		return resultStream;
	}

	/**
	 * 发送服务请求，请求方式以参数的方式传
	 * 
	 * @param requestURL
	 *            请求的地址
	 * @param charset
	 *            字符集
	 * @param requestContext
	 *            请求内容
	 * @return InputStream
	 * @throws BusinessServiceException
	 */
	public static InputStream postImage(String requestURL, StringBuffer requestContext, String charset) throws Exception
	{
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);// TODO
																							// 60秒
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);
		if (StringUtils.isEmpty(requestURL))
		{
			throw new IllegalArgumentException("请求地址为空");
		}
		if (null == requestContext)
		{
			throw new IllegalArgumentException("请求内容为空");
		}
		if (StringUtils.isEmpty(charset))
		{
			charset = "UTF-8";
		}
		InputStream resultStream = null;
		PostMethod postMethod = null;
		BufferedInputStream bufferedInputStream = null;
		try
		{
			postMethod = new PostMethod(requestURL);
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); // 设置请求参数
			bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(requestContext.toString().getBytes(charset)));
			InputStreamRequestEntity streamRequestEntity = new InputStreamRequestEntity(bufferedInputStream);
			postMethod.setRequestEntity(streamRequestEntity);

			httpClient.executeMethod(postMethod);
			if (postMethod.getStatusCode() == HttpStatus.SC_OK)
			{
				// postMethod.getResponseBody();
				resultStream = postMethod.getResponseBodyAsStream();
			}
			else
			{
				throw new IllegalArgumentException(setStatusMsg(postMethod.getStatusCode()));
			}
		}
		catch (HttpException he)
		{
			throw new IllegalArgumentException("请检查服务是否已启动!");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			LogRecoder.wirteException(ioe);
			// throw new IllegalArgumentException("网络连接异常!");
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw exp;
		}
		finally
		{
			// 释放对象
			if (postMethod != null)
			{
				// postMethod.releaseConnection();
				postMethod = null;
			}
			try
			{
				if (bufferedInputStream != null)
				{
					bufferedInputStream.close();
					bufferedInputStream = null;
				}
			}
			catch (Exception ioExp)
			{
			}
		}
		return resultStream;
	}

	public static void test()
	{
		// PostMethod postMethod = new PostMethod(AppConstants.URL);
		PostMethod postMethod = new PostMethod(AppConstants.info.getUrl());
		try
		{
			httpClient.executeMethod(postMethod);
		}
		catch (Exception e)
		{

		}
		if (postMethod.getStatusCode() == HttpStatus.SC_OK)
		{
			System.out.println("success");
		}
		else
		{
			System.out.println("fail");
		}
	}

	/**
	 * 发送服务请求，请求方式以参数的方式传
	 * 
	 * @param requestURL
	 *            请求的地址
	 * @param charset
	 *            字符集
	 * @param requestContext
	 *            请求内容
	 * @return InputStream
	 * @throws BusinessServiceException
	 */
	public static InputStream postMethodRequest(String requestURL, InputStream requestContext) throws Exception
	{
		if (StringUtils.isEmpty(requestURL))
		{
			throw new IllegalArgumentException("请求地址为空");
		}
		InputStream resultStream = null;
		PostMethod postMethod = null;
		BufferedInputStream bufferedInputStream = null;
		try
		{
			postMethod = new PostMethod(requestURL);
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler()); // 设置请求参数

			bufferedInputStream = new BufferedInputStream(requestContext);
			InputStreamRequestEntity streamRequestEntity = new InputStreamRequestEntity(bufferedInputStream);
			postMethod.setRequestEntity(streamRequestEntity);

			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode == HttpStatus.SC_OK)
			{
				// postMethod.getResponseBody();
				resultStream = postMethod.getResponseBodyAsStream();
			}
			else
			{
				throw new IllegalArgumentException(setStatusMsg(postMethod.getStatusCode()));
			}
		}
		catch (HttpException he)
		{
			throw new IllegalArgumentException("请检查服务是否已启动!");
		}
		catch (IOException ioe)
		{
			throw new IllegalArgumentException("网络连接异常!");
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			LogRecoder.wirteException("HTTP", exp);
			throw exp;
		}
		finally
		{
			// 释放对象
			if (postMethod != null)
			{
				postMethod.releaseConnection();
				postMethod = null;
			}
			try
			{
				if (bufferedInputStream != null)
				{
					bufferedInputStream.close();
					bufferedInputStream = null;
				}
			}
			catch (IOException ioExp)
			{
			}
		}
		return resultStream;
	}

	/**
	 * 获取网络状态信息
	 * 
	 * @param statusCode
	 *            网络状态码
	 * @return 网络状态信息
	 */
	private static String setStatusMsg(int statusCode)
	{
		String msg = null;
		switch (statusCode)
		{
		case HttpStatus.SC_PARTIAL_CONTENT:// 206
			msg = "内容不完整";
			break;
		case HttpStatus.SC_BAD_REQUEST:// 400
			msg = "错误请求";
			break;
		case HttpStatus.SC_FORBIDDEN:// 403
			msg = "禁止访问";
			break;
		case HttpStatus.SC_NOT_FOUND:// 404
			msg = "没有找到匹配的地址";
			break;
		case HttpStatus.SC_INTERNAL_SERVER_ERROR:// 500
			msg = "服务器内部错误";
			break;
		default:
			msg = "网络异常";
		}
		return msg;
	}
}