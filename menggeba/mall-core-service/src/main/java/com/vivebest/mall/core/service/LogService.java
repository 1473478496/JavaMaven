/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.service;

import com.vivebest.mall.core.entity.Log;
import com.vivebest.mall.core.service.BaseService;

/**
 * Service - 日志
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();

}