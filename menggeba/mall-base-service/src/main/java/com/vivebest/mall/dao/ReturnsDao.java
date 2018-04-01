/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.Returns.Status;

/**
 * Dao - 退货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ReturnsDao extends BaseDao<Returns, Long> {

	
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
	 * 查找订单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 退货单分页
	 */
	Page<Returns> findPage(Member member, Pageable pageable);
	
	Page<Returns> findPage(Status status, Pageable pageable);

	List<Returns> findByOrderItem(Long orderItemId);
	
	Long unReturnsCount();

	/**
	 * 通过orderId查找
	 * @param id
	 * @return
	 */
	List<Returns> findByOrder(Long id);
}