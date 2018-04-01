/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RightsBrandTypeDao;
import com.vivebest.mall.entity.RightsBrandType;

/**
 * Dao - 权益商家类型
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("rightsBrandTypeDaoImpl")
public class RightsBrandTypeDaoImpl extends BaseDaoImpl<RightsBrandType, Long> implements RightsBrandTypeDao  {

	@Override
	public List<RightsBrandType> findAll() {
		return super.findList(0, 50, null, null);
	}

}