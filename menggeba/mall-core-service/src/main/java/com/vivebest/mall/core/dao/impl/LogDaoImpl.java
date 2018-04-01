/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.dao.impl;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.LogDao;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.core.entity.Log;

/**
 * Dao - 日志
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("logDaoImpl")
public class LogDaoImpl extends BaseDaoImpl<Log, Long> implements LogDao {

	public void removeAll() {
		String jpql = "delete from Log log";
		entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
	}

}