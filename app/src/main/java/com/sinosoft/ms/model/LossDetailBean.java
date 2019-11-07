package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：损失信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:14:45
 */
public class LossDetailBean implements Serializable {
	private static final long serialVersionUID = -8542649481213051060L;
	// 基本信息
	private String lossSituation;// 损失情况
	private String surveySites;// 查勘地点
	private String liabilityRatio;// 责任比例
	private String estimatedLossAmount;// 估损金额
	private String surveyDate;// 查勘日期
	private String option;// 操作

	// 详细
	private String lossArea;// 损失部位(多选)
	private String reservationForLossAddr;// 预约定损地点
	private String salvageCharges;// 施救费用
	private String travelAFewKilometers;// 已行驶公里数
	private String ifTheRefit;// 是否改装或加装
	private String carryDangerousGoods;// 是否运载危险品
	private String ratio;// 比例

	public LossDetailBean() {
		super();
	}

	public LossDetailBean(String lossSituation, String surveySites,
			String liabilityRatio, String estimatedLossAmount,
			String surveyDate, String lossArea, String reservationForLossAddr,
			String salvageCharges, String travelAFewKilometers,
			String ifTheRefit, String carryDangerousGoods, String ratio) {
		super();
		this.lossSituation = lossSituation;
		this.surveySites = surveySites;
		this.liabilityRatio = liabilityRatio;
		this.estimatedLossAmount = estimatedLossAmount;
		this.surveyDate = surveyDate;
		this.option = option;
		this.lossArea = lossArea;
		this.reservationForLossAddr = reservationForLossAddr;
		this.salvageCharges = salvageCharges;
		this.travelAFewKilometers = travelAFewKilometers;
		this.ifTheRefit = ifTheRefit;
		this.carryDangerousGoods = carryDangerousGoods;
		this.ratio = ratio;
	}

	public String getLossSituation() {
		return lossSituation;
	}

	public void setLossSituation(String lossSituation) {
		this.lossSituation = lossSituation;
	}

	public String getSurveySites() {
		return surveySites;
	}

	public void setSurveySites(String surveySites) {
		this.surveySites = surveySites;
	}

	public String getLiabilityRatio() {
		return liabilityRatio;
	}

	public void setLiabilityRatio(String liabilityRatio) {
		this.liabilityRatio = liabilityRatio;
	}

	public String getEstimatedLossAmount() {
		return estimatedLossAmount;
	}

	public void setEstimatedLossAmount(String estimatedLossAmount) {
		this.estimatedLossAmount = estimatedLossAmount;
	}

	public String getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(String surveyDate) {
		this.surveyDate = surveyDate;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getLossArea() {
		return lossArea;
	}

	public void setLossArea(String lossArea) {
		this.lossArea = lossArea;
	}

	public String getReservationForLossAddr() {
		return reservationForLossAddr;
	}

	public void setReservationForLossAddr(String reservationForLossAddr) {
		this.reservationForLossAddr = reservationForLossAddr;
	}

	public String getSalvageCharges() {
		return salvageCharges;
	}

	public void setSalvageCharges(String salvageCharges) {
		this.salvageCharges = salvageCharges;
	}

	public String getTravelAFewKilometers() {
		return travelAFewKilometers;
	}

	public void setTravelAFewKilometers(String travelAFewKilometers) {
		this.travelAFewKilometers = travelAFewKilometers;
	}

	public String getIfTheRefit() {
		return ifTheRefit;
	}

	public void setIfTheRefit(String ifTheRefit) {
		this.ifTheRefit = ifTheRefit;
	}

	public String getCarryDangerousGoods() {
		return carryDangerousGoods;
	}

	public void setCarryDangerousGoods(String carryDangerousGoods) {
		this.carryDangerousGoods = carryDangerousGoods;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

}
