package com.sinosoft.ms.model;

/**
 * 系统名：移动查勘定损 影像资料 对象 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午9:47:55
 */

public class ImageCenterBean {
	private String id;
	private String createDate;// 拍摄日期
	private String imgName;// 图片名称
	private String imgSize;// 图片大小
	private String location;// 图片拍摄地点
	private String path;// 图片的路径
	private String reportNo;// 报案号
	private String type;// 类型（行驶证、驾驶证等）
	private String isUpload;//是否更新成功
	private String taskId;//任务id
	private String registId;//报案id

	/**
	 * @return the registId
	 */
	public String getRegistId() {
		return registId;
	}

	/**
	 * @param registId the registId to set
	 */
	public void setRegistId(String registId) {
		this.registId = registId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateDate() {
		return createDate;
	}

	
	public String getImgName() {
		return imgName;
	}

	public String getImgSize() {
		return imgSize;
	}

	public String getLocation() {
		return location;
	}

	public String getPath() {
		return path;
	}

	public String getReportNo() {
		return reportNo;
	}

	public String getType() {
		return type;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param imgName
	 *            the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	/**
	 * @param imgSize
	 *            the imgSize to set
	 */
	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param reportNo
	 *            the reportNo to set
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the isUpload
	 */
	public String getIsUpload() {
		return isUpload;
	}
	/**
	 * @param isUpload the isUpload to set
	 */
	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}

}
