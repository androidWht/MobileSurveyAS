package com.sinosoft.ms.model;

/**
 * 系统名：移动查勘定损 子系统名：任务数 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 上午10:27:21
 */

public class Business extends Header {
	private static final long serialVersionUID = 1L;

	// 用户名称
	private String userName; // 用户名称
	private String sessionNo;
	private String deptNo;
//	private String surveyTaskCount;// 查勘任务
//	private String lossTaskCount;// 定损任务
//	private String receiveTaskCount;// 已接受任务
//	private String notReceiveTaskCount;// 未接受任务
	private String synTime;// 同步时间

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the sessionNo
	 */
	public String getSessionNo() {
		return sessionNo;
	}

	/**
	 * @param sessionNo
	 *            the sessionNo to set
	 */
	public void setSessionNo(String sessionNo) {
		this.sessionNo = sessionNo;
	}

	/**
	 * @return the deptNo
	 */
	public String getDeptNo() {
		return deptNo;
	}

	/**
	 * @param deptNo
	 *            the deptNo to set
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

//	/**
//	 * @return the surveyTaskCount
//	 */
//	public String getSurveyTaskCount() {
//		return surveyTaskCount;
//	}
//
//	/**
//	 * @param surveyTaskCount
//	 *            the surveyTaskCount to set
//	 */
//	public void setSurveyTaskCount(String surveyTaskCount) {
//		this.surveyTaskCount = surveyTaskCount;
//	}
//
//	/**
//	 * @return the lossTaskCount
//	 */
//	public String getLossTaskCount() {
//		return lossTaskCount;
//	}
//
//	/**
//	 * @param lossTaskCount
//	 *            the lossTaskCount to set
//	 */
//	public void setLossTaskCount(String lossTaskCount) {
//		this.lossTaskCount = lossTaskCount;
//	}
//
//	/**
//	 * @return the receiveTaskCount
//	 */
//	public String getReceiveTaskCount() {
//		return receiveTaskCount;
//	}
//
//	/**
//	 * @param receiveTaskCount
//	 *            the receiveTaskCount to set
//	 */
//	public void setReceiveTaskCount(String receiveTaskCount) {
//		this.receiveTaskCount = receiveTaskCount;
//	}
//
//	/**
//	 * @return the notReceiveTaskCount
//	 */
//	public String getNotReceiveTaskCount() {
//		return notReceiveTaskCount;
//	}
//
//	/**
//	 * @param notReceiveTaskCount
//	 *            the notReceiveTaskCount to set
//	 */
//	public void setNotReceiveTaskCount(String notReceiveTaskCount) {
//		this.notReceiveTaskCount = notReceiveTaskCount;
//	}

	/**
	 * @return the synTime
	 */
	public String getSynTime() {
		return synTime;
	}

	/**
	 * @param synTime
	 *            the synTime to set
	 */
	public void setSynTime(String synTime) {
		this.synTime = synTime;
	}
	


	public void init() {
//		surveyTaskCount = surveyTaskCount == null ? "" : surveyTaskCount;// 查勘任务
//		lossTaskCount = lossTaskCount == null ? "" : lossTaskCount;// 定损任务
//		receiveTaskCount = receiveTaskCount == null ? "" : receiveTaskCount;// 已接受任务
//		notReceiveTaskCount = notReceiveTaskCount == null ? ""
//				: notReceiveTaskCount;// 未接受任务
		synTime = synTime == null ? "" : synTime;
	}
}
