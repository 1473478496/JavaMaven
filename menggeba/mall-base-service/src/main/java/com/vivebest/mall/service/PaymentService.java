/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Payment;

/**
 * Service - 收款单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface PaymentService extends BaseService<Payment, Long> {

	/**
	 * 根据编号查找收款单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 收款单，若不存在则返回null
	 */
	Payment findBySn(String sn);

	/**
	 * 支付处理
	 * 
	 * @param payment
	 *            收款单
	 */
	void handle(Payment payment);

}