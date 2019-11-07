package com.sinosoft.ms.service;

import com.sinosoft.ms.model.FeedbackMessage;
import com.sinosoft.ms.model.PushMessage;

/**
 * 系统名：移动查勘定损 子系统名：推送消息服务接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 15 Jan 2013 9:40:14
 */
public interface IPushMessageService {

	/**
	 * 发送推送消息接口 说明：本接口用来定时向服务器发送推送消息，获取最新任务信息。
	 * 
	 * @param pushMessage
	 *            推送消息
	 * @return 反馈消息
	 * @throws Exception
	 */
	public FeedbackMessage send(PushMessage pushMessage) throws Exception;
}
