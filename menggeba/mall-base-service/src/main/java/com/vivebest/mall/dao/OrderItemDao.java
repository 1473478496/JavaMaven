/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * Dao - 订单项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface OrderItemDao extends BaseDao<OrderItem, Long> {

	List<OrderItem> checkProNum(Member member, PromotionProducts promPro);

}