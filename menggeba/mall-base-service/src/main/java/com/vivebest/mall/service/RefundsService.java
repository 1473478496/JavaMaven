/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Refunds.Status;

/**
 * Service - 退款单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RefundsService extends BaseService<Refunds, Long> {
	
	public Refunds findBySn(String sn); 
    
	Page<Refunds> findPage(Status status,Pageable pageable);
	
	/**
	 * 根据订单查找退货单
	 * @param orderId
	 * @return
	 */
	public List<Refunds> findByOrderId(Long orderId);
	
	void refund(int pageNumber,int pageSize);
	
	Long unRefundsCount(Refunds refunds);
}