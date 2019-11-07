package com.sinosoft.ms.model.sub;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：时效信息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 下午3:43:05
 */

public class Prescription implements Serializable {
	private static final long serialVersionUID = 4811614112360621108L;
	private String processingStage;// 处理阶段
	private String receptionTime;// 接收时间
	private String processingStartTime;// 处理开始时间
	private String processingEndTime;// 处理结束时间
	private String processingResult;// 处理结果
	private String minTime;// 耗时(分钟)
	private String effectStatus;// 状态

	public String getProcessingStage() {
		return processingStage;
	}

	public void setProcessingStage(String processingStage) {
		this.processingStage = processingStage;
	}

	public String getReceptionTime() {
		return receptionTime;
	}

	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}

	public String getProcessingStartTime() {
		return processingStartTime;
	}

	public void setProcessingStartTime(String processingStartTime) {
		this.processingStartTime = processingStartTime;
	}

	public String getProcessingEndTime() {
		return processingEndTime;
	}

	public void setProcessingEndTime(String processingEndTime) {
		this.processingEndTime = processingEndTime;
	}

	public String getProcessingResult() {
		return processingResult;
	}

	public void setProcessingResult(String processingResult) {
		this.processingResult = processingResult;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getEffectStatus() {
		return effectStatus;
	}

	public void setEffectStatus(String effectStatus) {
		this.effectStatus = effectStatus;
	}

}
