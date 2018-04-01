/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.OrderItemDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.service.OrderItemService;

/**
 * Service - 订单项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {
	
	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;

	@Resource(name = "orderItemDaoImpl")
	public void setBaseDao(OrderItemDao orderItemDao) {
		super.setBaseDao(orderItemDao);
	}

	@Override
	public List<OrderItem> checkProNum(Member member, PromotionProducts promPro) {
		return orderItemDao.checkProNum(member, promPro);
	}

}