/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;
import java.util.Set;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Parameter;
import com.vivebest.mall.entity.ParameterGroup;


/**
 * Dao - 参数
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ParameterDao extends BaseDao<Parameter, Long> {

	/**
	 * 查找参数
	 * 
	 * @param parameterGroup
	 *            参数组
	 * @param excludes
	 *            排除参数
	 * @return 参数
	 */
	List<Parameter> findList(ParameterGroup parameterGroup, Set<Parameter> excludes);

}