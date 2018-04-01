/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * Service - 订单项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface OrderItemService extends BaseService<OrderItem, Long> {

	/**
	 * check是否购买
	 * @param member
	 * @param promPro
	 * @return
	 */
	List<OrderItem> checkProNum(Member member, PromotionProducts promPro);

}