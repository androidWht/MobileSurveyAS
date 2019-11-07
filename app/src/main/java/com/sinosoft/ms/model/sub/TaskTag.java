package com.sinosoft.ms.model.sub;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：任务标识数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 10:26:52
 */

public class TaskTag implements Serializable {
	private static final long serialVersionUID = 3781457823525673364L;
	private boolean rememberMe;// 记住我
	private boolean primarySurvey;// 我是主查勘员
	private boolean dispatch;// 接受查勘任务
	private boolean loss;// 接受定损任务
	private boolean agentDriving;// 接受代驾任务

	public TaskTag(boolean rememberMe, boolean primarySurvey, boolean dispatch,
			boolean loss, boolean agentDriving) {
		super();
		this.rememberMe = rememberMe;
		this.primarySurvey = primarySurvey;
		this.dispatch = dispatch;
		this.loss = loss;
		this.agentDriving = agentDriving;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
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
