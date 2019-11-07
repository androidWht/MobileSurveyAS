package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：推送消息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午9:41:34
 */

public class PushMessage implements Serializable {
	private static final long serialVersionUID = 4264876384497974811L;
	private boolean primarySurvey;//
	private boolean dispatch;//
	private boolean loss;//
	private boolean agentDriving;//

	public PushMessage() {
		super();
	}

	public PushMessage(boolean primarySurvey, boolean dispatch, boolean loss,
			boolean agentDriving) {
		super();
		this.primarySurvey = primarySurvey;
		this.dispatch = dispatch;
		this.loss = loss;
		this.agentDriving = agentDriving;
	}

	public boolean isPrimarySurvey() {
		return primarySurvey;
	}

	public void setPrimarySurvey(boolean primarySurvey) {
		this.primarySurvey = primarySurvey;
	}

	public boolean isDispatch() {
		return dispatch;
	}

	public void setDispatch(boolean dispatch) {
		this.dispatch = dispatch;
	}

	public boolean isLoss() {
		return loss;
	}

	public void setLoss(boolean loss) {
		this.loss = loss;
	}

	public boolean isAgentDriving() {
		return agentDriving;
	}

	public void setAgentDriving(boolean agentDriving) {
		this.agentDriving = agentDriving;
	}

}
