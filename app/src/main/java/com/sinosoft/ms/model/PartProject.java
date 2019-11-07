package com.sinosoft.ms.model;

/**
 * 换件项目
 * 
 * @author Administrator
 * 
 */
public class PartProject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String replacementParts;// 更换部位1
	private String partsGrouping;// 零件分组名称2
	private String partType;// 零件类型3
	private String partName;// 零件名称
	private String keywords;// 关键字
	private String surveyQuotedPrice;// 查勘报价
	private String modifyFactoryPrice;// 修改厂价格
	private String count;// 数量
	private String insur;// 险别
	private String lossOfMoreThan;// 是否损余
	private String salvage;// 残值
	private String pricePlan;// 价格方案
	private String factoryPartsNumber;// 原厂零件编号
	private String factoryPartsName;// 原厂零件名称
	private String localhostPrice;// 本地价
	private String systemPrice;// 系统价
	private String remark;

	public String getReplacementParts() {
		return replacementParts;
	}

	public void setReplacementParts(String replacementParts) {
		this.replacementParts = replacementParts;
	}

	public String getPartsGrouping() {
		return partsGrouping;
	}

	public void setPartsGrouping(String partsGrouping) {
		this.partsGrouping = partsGrouping;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getSurveyQuotedPrice() {
		return surveyQuotedPrice;
	}

	public void setSurveyQuotedPrice(String surveyQuotedPrice) {
		this.surveyQuotedPrice = surveyQuotedPrice;
	}

	public String getModifyFactoryPrice() {
		return modifyFactoryPrice;
	}

	public void setModifyFactoryPrice(String modifyFactoryPrice) {
		this.modifyFactoryPrice = modifyFactoryPrice;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getInsur() {
		return insur;
	}

	public void setInsur(String insur) {
		this.insur = insur;
	}

	public String getLossOfMoreThan() {
		return lossOfMoreThan;
	}

	public void setLossOfMoreThan(String lossOfMoreThan) {
		this.lossOfMoreThan = lossOfMoreThan;
	}

	public String getSalvage() {
		return salvage;
	}

	public void setSalvage(String salvage) {
		this.salvage = salvage;
	}

	public String getPricePlan() {
		return pricePlan;
	}

	public void setPricePlan(String pricePlan) {
		this.pricePlan = pricePlan;
	}

	public String getFactoryPartsNumber() {
		return factoryPartsNumber;
	}

	public void setFactoryPartsNumber(String factoryPartsNumber) {
		this.factoryPartsNumber = factoryPartsNumber;
	}

	public String getFactoryPartsName() {
		return factoryPartsName;
	}

	public void setFactoryPartsName(String factoryPartsName) {
		this.factoryPartsName = factoryPartsName;
	}

	public String getLocalhostPrice() {
		return localhostPrice;
	}

	public void setLocalhostPrice(String localhostPrice) {
		this.localhostPrice = localhostPrice;
	}

	public String getSystemPrice() {
		return systemPrice;
	}

	public void setSystemPrice(String systemPrice) {
		this.systemPrice = systemPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
