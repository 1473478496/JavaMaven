/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.RightsBrandType;

/**
 * Service - 权益商家类别
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightsBrandTypeService extends BaseService<RightsBrandType, Long> {
	/**
	 * 权益商家类别列表
	 */
	List<RightsBrandType> findAllType();
}