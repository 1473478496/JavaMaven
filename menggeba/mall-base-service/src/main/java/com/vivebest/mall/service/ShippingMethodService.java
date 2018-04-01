/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.ShippingMethod;

/**
 * Service - 配送方式
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ShippingMethodService extends BaseService<ShippingMethod, Long> {

	public ShippingMethod findByName(String name);
}