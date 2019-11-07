package com.sinosoft.ms.service;

import java.util.List;

import android.graphics.Bitmap;

import com.sinosoft.ms.model.LookImage;

/**
 * 系统名：移动查勘定损 
 * 子系统名：查看影像服务接口
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午2:13:28
 */

public interface ILookImageService {
	
	/**
	 * 根据报案号获取影像信息
	 * 
	 * @param registNo
	 *            报案号
	 * @return 影像信息
	 * @throws Exception
	 */
	public List<LookImage> getImageByRegistNo(String registNo) throws Exception;
	
	/**
	 * 获取影像文件
	 * 
	 * @param type 影像类型(1-缩略图,2-原图)
	 * @param imageId 影像ID
	 * @return 图片资源
	 * @throws Exception
	 */
	public Bitmap getImageFile(int type,String imageId) throws Exception;
	
	public String getImageXmll(int type,String imageId);
}

