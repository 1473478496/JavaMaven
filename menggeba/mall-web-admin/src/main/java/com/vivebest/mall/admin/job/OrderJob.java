/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.job;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vivebest.mall.service.OrderService;

/**
 * Job - 订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("orderJob")
@Lazy(false)
public class OrderJob {

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	/**
	 * 发货15天后自动确认收货
	 */
	@Scheduled(cron = "${job.order_complete_shipping.cron}")
	public void orderComplete() {
		orderService.orderComplete();
	}

}