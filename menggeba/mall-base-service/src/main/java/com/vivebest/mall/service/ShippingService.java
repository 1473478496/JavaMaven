/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.Map;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Shipping;


/**
 * Service - 发货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ShippingService extends BaseService<Shipping, Long> {

	/**
	 * 根据编号查找发货单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Shipping findBySn(String sn);

	/**
	 * 查询物流动态
	 * 
	 * @param shipping
	 *            发货单
	 * @return 物流动态
	 */
	Map<String, Object> query(Shipping shipping);

}