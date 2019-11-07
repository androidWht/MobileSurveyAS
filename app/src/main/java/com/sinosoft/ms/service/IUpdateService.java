package com.sinosoft.ms.service;

import com.sinosoft.ms.model.AppInfoBean;
import com.sinosoft.ms.model.User;

/**
 * 系统名：移动查勘定损 
 * 子系统名：版本更新
 * 著作权：COPYRIGHT (C) 2013 SINOSOFT INFORMATION SYSTEMS
 * 		CORPORATION ALL RIGHTS RESERVED.
 * 
 * @author mako
 * @createTime 下午4:10:11
 */

public interface IUpdateService {
      public AppInfoBean checkAppVersion(User user) throws Exception;
}

