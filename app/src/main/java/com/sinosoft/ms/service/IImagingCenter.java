package com.sinosoft.ms.service;

import com.sinosoft.ms.model.sub.ImageBean;
import com.sinosoft.ms.model.sub.ImageData;

/**
 * 系统名：移动查勘定损 子系统名：影像中心服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 14:10:24
 */
public interface IImagingCenter {

	/**
	 * 获取影像资料信息 说明：进入影像资料信息界面时调用本方法获取到影像资料信息。反馈 错误提示与内容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param reportNo
	 *            报案号
	 * @return 影像资料信息
	 * @throws Exception
	 */
	public ImageData getImageData(String reportNo) throws Exception;

	/**
	 * 上传影像资料 说明：本方法用来上传影像资料，影像资料信息采用集合方式封装，主键为照片
	 * 类型;值为数组形式，封装了图片信息列表。反馈错误提示与内容将以异常形式 抛出，需要对异常进行捕获处理。 调用时机：
	 * 1.在影像中心拍照完成选择上传时
	 * 
	 * @param fileMap
	 *            影像资料信息
	 * @param reportNo
	 *            报案号
	 * @return 1为成功 0为失败
	 * @throws Exception
	 */
	public int uploadImage(ImageData imageData, String reportNo)
			throws Exception;

	/**
	 * 删除影像资料 说明：本方法提供单张影像资料删除操作功能，反馈错误提示与内容将以异常形式 抛出，需要对异常进行捕获处理。
	 * 
	 * @param reportNo
	 *            报案号
	 * @param filePath
	 *            文件路径
	 * @param type
	 *            类型
	 * @return 1为删除成功 0为失败
	 * @throws Exception
	 */
	public int delete(String reportNo, String filePath, int type)
			throws Exception;

	/**
	 * 预览图片
	 * 
	 * @param imageId
	 *            图片ID
	 * @return 图片信息
	 * @throws Exception
	 */
	public ImageBean getImageDetail(int imageId) throws Exception;

}
