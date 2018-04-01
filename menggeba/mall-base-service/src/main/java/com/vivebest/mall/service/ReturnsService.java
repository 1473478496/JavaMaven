/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.math.BigDecimal;
import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.Returns.Status;

/**
 * Service - 退货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ReturnsService extends BaseService<Returns, Long> {

	
	/**
	 * 查找退款退货分页
	 * @param status
	 * 			退货退款状态
	 * @param returnType
	 * 			退货退款类型
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<Returns> findPage(Status status, ReturnType returnType, com.vivebest.mall.entity.Refunds.Status refundStatus, Pageable pageable);
	
	/**
	 * 查找退货单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Returns> findPage(Member member, Pageable pageable);
	
	Page<Returns> findPage(Status status,Pageable pageable);

	Returns build(OrderItem orderItem,BigDecimal price, ReturnType returnType, Member member, String returnCause, String returnDesc, String returnVoucher);

	void recevied(Returns returns, Admin admin);

	void approve(Returns returns,BigDecimal returnPrice, Admin admin, String auditDesc);

	List<Returns> findByOrderItem(Long orderItemId);

	void applyAgain(Returns returns,BigDecimal price,ReturnType returnType, OrderItem orderItem, Member member,
			String returnCause, String returnDesc, String returnVoucher);
	
	void orderClose(Returns returns);
	
	Long unReturnsCount();
	
}