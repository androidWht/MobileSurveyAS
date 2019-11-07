package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.model.User;
import com.sinosoft.ms.model.sub.TaskTag;

/**
 * 系统名：移动查勘定损 子系统名：用户登录接口 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author linianchun
 * @createTime 16 Jan 2013 10:36:53
 */
public interface ILoginService {

	/**
	 * 用户登录 说明：当用户登录时调用本方法，本方法向平台服务器发送登录请求，验证
	 * 用户是否为注册用户，当用户登录失败时将返回"false".反馈错误提示与内 容将以异常形式抛出，需要对异常进行捕获处理。
	 * 
	 * @param user
	 *            用户信息
	 * @param taskTag
	 *            任务标识
	 * @return true为成功 false为失败
	 * @throws Exception
	 */
	public boolean login(User user, TaskTag taskTag) throws Exception;
	
	/**
	 * 版本更新
	 * @return
	 * @throws Exception
	 */
	public List<AppInfoBean> checkVersion() throws Exception;
	
	
	
	/**
	 * 退出登录，用于记录移动端退出，地图调度时不在查到其查勘员
	 * @return true为成功 false为失败
	 * @throws Exception
	 */
	public boolean exitLogin(User user) throws Exception;
	
}
