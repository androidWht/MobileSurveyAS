package com.sinosoft.ms.service.impl;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sinosoft.ms.model.LookImage;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.service.ILookImageService;
import com.sinosoft.ms.utils.AppConstants;
import com.sinosoft.ms.utils.UserCashe;
import com.sinosoft.ms.utils.net.HttpClientUtil;
import com.sinosoft.ms.utils.xml.LookImageDataParser;

/**
 * 系统名：移动查勘定损 
 * 子系统名：查看影像服务接口实现
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午2:17:20
 */

public class LookImageService extends BasicHttp implements ILookImageService {

	@Override
	public List<LookImage> getImageByRegistNo(String registNo) throws Exception {
		List<LookImage> result = null;
		try{
			User user = UserCashe.getInstant().getUser();
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<package>"
					+ "	<head>"
					+ "		<requestType>U0328</requestType>"
					+ "		<requestUser>"+user.getName()+"</requestUser>"
					+ "	</head>"
					+ "	<body>"
					+ "		<registNo>"+registNo+"</registNo>"
					+ "	</body>"
					+ "</package>";
			sendRequest(xml);
			LookImageDataParser parser = new LookImageDataParser();
			parser.parse(inputStream);
			result = parser.getResult();
		}catch(IllegalArgumentException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	

	@Override
	public Bitmap getImageFile(int type,String imageId) throws Exception {
		Bitmap bmp = null;
		try{
			User user = UserCashe.getInstant().getUser();
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<package>"
					+ "	<head>"
					+ "		<requestType>U0329</requestType>"
					+ "		<requestUser>"+user.getName()+"</requestUser>"
					+ "	</head>"
					+ "	<body>"
					+ "		<imageType>"+type+"</imageType>"
					+ "		<imageId>"+imageId+"</imageId>"
					+ "	</body>"
					+ "</package>";
			System.out.println("请求地址："+xml);
			inputStream = null;
			//inputStream = HttpClientUtil.postImage(AppConstants.DOWNURL, new StringBuffer(xml),"UTF-8");
			inputStream = HttpClientUtil.postImage(AppConstants.info.getDownUrl(), new StringBuffer(xml),"UTF-8");
			if (inputStream == null) {
					throw new IllegalArgumentException("服务器无响应信息！");
		    }
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			if(type == 1){
				newOpts.inSampleSize = 5;
			}
			bmp = BitmapFactory.decodeStream(inputStream,null,newOpts);
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{

		}
		return bmp;
	}


	/* (non-Javadoc)
	 * @see com.sinosoft.ms.service.ILookImageService#getImageXmll(int, java.lang.String)
	 */
	@Override
	public String getImageXmll(int type, String imageId) {
		// TODO Auto-generated method stub
		User user = UserCashe.getInstant().getUser();
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<package>"
				+ "	<head>"
				+ "		<requestType>U0329</requestType>"
				+ "		<requestUser>"+user.getName()+"</requestUser>"
				+ "	</head>"
				+ "	<body>"
				+ "		<imageType>"+type+"</imageType>"
				+ "		<imageId>"+imageId+"</imageId>"
				+ "	</body>"
				+ "</package>";
		return xml;
	}

}

