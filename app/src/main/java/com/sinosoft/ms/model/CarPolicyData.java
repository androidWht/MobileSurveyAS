package com.sinosoft.ms.model;

import java.io.Serializable;

import com.sinosoft.ms.utils.StringUtils;

/**
 * 系统名：移动查勘 子系统名： 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 上午10:26:05
 */

public class CarPolicyData implements Serializable {
	private String id;
	private String carId;
	private String checkCarId;
	private String policyFlag;// 保单交强/商业标志
	private String oppRegistNoBI;// 商业险报案号
	private String oppPolicyNoBI;// 商业险保单号
	private String oppInsurerCodeBI;// 商业险承保机构
	private String oppInsurerAreaBI;// 商业险承保地区
	private String oppRegistNoCI;// 交强险报案号
	private String oppPolicyNoCI;// 交强险保单号
	private String oppInsurerCodeCI;// 交强险承保机构
	private String oppInsurerAreaCI;// 交强险承保地区
	/** add by zhengongsheng 2013-5-11 10:31 **/
	private String oppIdBi;
	private String oppIdCi;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	
	/**
	 * @return the carId
	 */
	public String getCarId() {
		return carId;
	}

	/**
	 * @param carId the carId to set
	 */
	public void setCarId(String carId) {
		this.carId = carId;
	}

	/**
	 * @return the checkCarId
	 */
	public String getCheckCarId() {
		return checkCarId;
	}

	/**
	 * @param checkCarId
	 *            the checkCarId to set
	 */
	public void setCheckCarId(String checkCarId) {
		this.checkCarId = checkCarId;
	}

	/**
	 * @return the policyFlag
	 */
	public String getPolicyFlag() {
		return policyFlag;
	}

	/**
	 * @param policyFlag
	 *            the policyFlag to set
	 */
	public void setPolicyFlag(String policyFlag) {
		this.policyFlag = policyFlag;
	}

	/**
	 * @return the oppRegistNoBI
	 */
	public String getOppRegistNoBI() {
		return oppRegistNoBI;
	}

	/**
	 * @param oppRegistNoBI
	 *            the oppRegistNoBI to set
	 */
	public void setOppRegistNoBI(String oppRegistNoBI) {
		this.oppRegistNoBI = oppRegistNoBI;
	}

	/**
	 * @return the oppPolicyNoBI
	 */
	public String getOppPolicyNoBI() {
		return oppPolicyNoBI;
	}

	/**
	 * @param oppPolicyNoBI
	 *            the oppPolicyNoBI to set
	 */
	public void setOppPolicyNoBI(String oppPolicyNoBI) {
		this.oppPolicyNoBI = oppPolicyNoBI;
	}

	/**
	 * @return the oppInsurerCodeBI
	 */
	public String getOppInsurerCodeBI() {
		return oppInsurerCodeBI;
	}

	/**
	 * @param oppInsurerCodeBI
	 *            the oppInsurerCodeBI to set
	 */
	public void setOppInsurerCodeBI(String oppInsurerCodeBI) {
		this.oppInsurerCodeBI = oppInsurerCodeBI;
	}

	/**
	 * @return the oppInsurerAreaBI
	 */
	public String getOppInsurerAreaBI() {
		return oppInsurerAreaBI;
	}

	/**
	 * @param oppInsurerAreaBI
	 *            the oppInsurerAreaBI to set
	 */
	public void setOppInsurerAreaBI(String oppInsurerAreaBI) {
		this.oppInsurerAreaBI = oppInsurerAreaBI;
	}

	/**
	 * @return the oppRegistNoCI
	 */
	public String getOppRegistNoCI() {
		return oppRegistNoCI;
	}

	/**
	 * @param oppRegistNoCI
	 *            the oppRegistNoCI to set
	 */
	public void setOppRegistNoCI(String oppRegistNoCI) {
		this.oppRegistNoCI = oppRegistNoCI;
	}

	/**
	 * @return the oppPolicyNoCI
	 */
	public String getOppPolicyNoCI() {
		return oppPolicyNoCI;
	}

	/**
	 * @param oppPolicyNoCI
	 *            the oppPolicyNoCI to set
	 */
	public void setOppPolicyNoCI(String oppPolicyNoCI) {
		this.oppPolicyNoCI = oppPolicyNoCI;
	}

	/**
	 * @return the oppInsurerCodeCI
	 */
	public String getOppInsurerCodeCI() {
		return oppInsurerCodeCI;
	}

	/**
	 * @param oppInsurerCodeCI
	 *            the oppInsurerCodeCI to set
	 */
	public void setOppInsurerCodeCI(String oppInsurerCodeCI) {
		this.oppInsurerCodeCI = oppInsurerCodeCI;
	}

	/**
	 * @return the oppInsurerAreaCI
	 */
	public String getOppInsurerAreaCI() {
		return oppInsurerAreaCI;
	}

	/**
	 * @param oppInsurerAreaCI
	 *            the oppInsurerAreaCI to set
	 */
	public void setOppInsurerAreaCI(String oppInsurerAreaCI) {
		this.oppInsurerAreaCI = oppInsurerAreaCI;
	}

	
	/**
	 * @return the oppIdBi
	 */
	public String getOppIdBi() {
		return oppIdBi;
	}

	/**
	 * @param oppIdBi the oppIdBi to set
	 */
	public void setOppIdBi(String oppIdBi) {
		this.oppIdBi = oppIdBi;
	}

	/**
	 * @return the oppIdCi
	 */
	public String getOppIdCi() {
		return oppIdCi;
	}

	/**
	 * @param oppIdCi the oppIdCi to set
	 */
	public void setOppIdCi(String oppIdCi) {
		this.oppIdCi = oppIdCi;
	}

	public void init() {
		policyFlag = policyFlag == null ? "" : policyFlag;
		oppRegistNoBI = oppRegistNoBI == null ? "" : oppRegistNoBI;
		oppPolicyNoBI = oppPolicyNoBI == null ? "" : oppPolicyNoBI;
		oppInsurerCodeBI = oppInsurerCodeBI == null ? "" : oppInsurerCodeBI;
		oppInsurerAreaBI = oppInsurerAreaBI == null ? "" : oppInsurerAreaBI;
		oppRegistNoCI = oppRegistNoCI == null ? "" : oppRegistNoCI;
		oppPolicyNoCI = oppPolicyNoCI == null ? "" : oppPolicyNoCI;
		oppInsurerCodeCI = oppInsurerCodeCI == null ? "" : oppInsurerCodeCI;
		oppInsurerAreaCI = oppInsurerAreaCI == null ? "" : oppInsurerAreaCI;
		oppIdBi=StringUtils.isEmpty(oppIdBi)?"":oppIdBi;
		oppIdCi=StringUtils.isEmpty(oppIdCi)?"":oppIdCi;

	}

}
