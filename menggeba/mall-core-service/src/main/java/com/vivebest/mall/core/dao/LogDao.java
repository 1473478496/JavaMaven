/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.core.entity.Log;

/**
 * Dao - 日志
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface LogDao extends BaseDao<Log, Long> {

	/**
	 * 删除所有日志
	 */
	void removeAll();

}