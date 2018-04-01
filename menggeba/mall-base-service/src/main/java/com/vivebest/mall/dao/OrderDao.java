/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;


/**
 * Dao - 订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface OrderDao extends BaseDao<Order, Long> {

	public int updateBySn(String sn,String name);
	
	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 订单，若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 查找订单
	 * 
	 * @param member
	 *            会员
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<Order> findList(Member member, Integer count, List<Filter> filters, List<com.vivebest.mall.core.common.Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param orderStatus
	 * 			  订单状态
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(OrderStatus orderStatus,String sn, Member member, Pageable pageable);
	
	/**
	 * 查找订单分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(Member member, Pageable pageable);
	
	/**
	 * 查找订单分页
	 * @param member
	 * 			会员
	 * @param beginDate
	 * 			起始日期
	 * @param endDate
	 * 			结束日期
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<Order> findPage(Member member, Date beginDate, Date endDate, Pageable pageable);
	
	

	/**
	 * 查找订单分页
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	Page<Order> findPage(Date beginDate, Date endDate,OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找订单
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 商品分页
	 */
	List<Order> findList(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, 
			Boolean hasExpired, Pageable pageable,Date bDate, Date eDate);

	/**
	 * 查询订单数量
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param paymentStatus
	 *            支付状态
	 * @param shippingStatus
	 *            配送状态
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(Member member,OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, Boolean hasExpired);

	/**
	 * 查询等待支付订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待支付订单数量
	 */
	Long waitingPaymentCount(Member member);

	/**
	 * 查询等待发货订单数量
	 * 
	 * @param member
	 *            会员
	 * @return 等待发货订单数量
	 */
	Long waitingShippingCount(Member member);

	/**
	 * 获取销售额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售额
	 */
	BigDecimal getSalesAmount(Date beginDate, Date endDate);

	/**
	 * 获取销售量
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 销售量
	 */
	Integer getSalesVolume(Date beginDate, Date endDate);

	/**
	 * 释放过期订单库存
	 */
	void releaseStock();
	
	List findPage(Order order, String ssn ,String psn,String pname,String mname,String bDate,String eDate,Integer orderStatue,Integer shippingStatue,Integer payStatue,Boolean hasExpired);
	
	/**
	 * 根据id查询订单列表
	 * @param list
	 * @return
	 */
	Page<Order> findById(List<Long> list,Pageable pageable);

	Page<Order> findPageMeOrderId(String sn, String member, Pageable pageable);
}