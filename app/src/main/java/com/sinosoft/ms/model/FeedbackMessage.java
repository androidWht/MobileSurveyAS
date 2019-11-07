package com.sinosoft.ms.model;

import java.io.Serializable;

/**
 * 系统名：移动查勘定损 子系统名：推送消息数据结构类 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 上午9:41:34
 */

public class FeedbackMessage implements Serializable {
	private static final long serialVersionUID = -6897127259315277192L;
	private String radioMessage;
	private boolean isNotify=false;

	public String getRadioMessage() {
		return radioMessage;
	}

	public void setRadioMessage(String radioMessage) {
		this.radioMessage = radioMessage;
	}

	/**
	 * @return the isNotify
	 */
	public boolean isNotify() {
		return isNotify;
	}

	/**
	 * @param isNotify the isNotify to set
	 */
	public void setNotify(boolean isNotify) {
		this.isNotify = isNotify;
	}
    
}
