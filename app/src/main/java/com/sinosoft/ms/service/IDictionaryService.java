package com.sinosoft.ms.service;

import java.util.List;

import com.sinosoft.ms.model.DicInfoBean;
import com.sinosoft.ms.model.User;

/**
 * 系统名：移动查勘定损 
 * 子系统名：取得字典信息
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author zhengtongsheng
 * @createTime 下午5:41:04
 */

public interface IDictionaryService {
	/**
	 * 取得字典信息
	 * @param user 登陆用户
	 */
	public List<DicInfoBean> getDicInfo(User user,String synTime,String deptNo) throws Exception;

}

