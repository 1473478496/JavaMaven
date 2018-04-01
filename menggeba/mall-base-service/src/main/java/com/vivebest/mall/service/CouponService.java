/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Coupon;



/**
 * Service - 优惠券
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface CouponService extends BaseService<Coupon, Long> {

	/**
	 * 查找优惠券分页
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param isExchange
	 *            是否允许积分兑换
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 优惠券分页
	 */
	Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange, Boolean hasExpired, Pageable pageable);
	
    Coupon findByName(String Name);

}