/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ShippingMethodDao;
import com.vivebest.mall.entity.ShippingMethod;
import com.vivebest.mall.service.ShippingMethodService;

/**
 * Service - 配送方式
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("shippingMethodServiceImpl")
public class ShippingMethodServiceImpl extends BaseServiceImpl<ShippingMethod, Long> implements ShippingMethodService {

	@Resource(name = "shippingMethodDaoImpl")
	public void setBaseDao(ShippingMethodDao shippingMethodDao) {
		super.setBaseDao(shippingMethodDao);
	}
	@Resource(name = "shippingMethodDaoImpl")
	private ShippingMethodDao shippingMethodDao;
	
	public ShippingMethod findByName(String name) {
		
		return shippingMethodDao.findByName(name);
	}

}