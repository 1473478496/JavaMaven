/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Cart;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.entity.Payment;
import com.vivebest.mall.entity.PaymentMethod;
import com.vivebest.mall.entity.Receiver;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Shipping;
import com.vivebest.mall.entity.ShippingMethod;

/**
 * Service - 订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface OrderService extends BaseService<Order, Long> {

	/**
	 * 根据订单编号查找订单
	 * 
	 * @param sn
	 *            订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	Order findBySn(String sn);
	
	public int updateBySn(String sn,String name);

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
	 * 发货15天后自动确认收货
	 */
	public void orderComplete();

	/**
	 * 换货后生成的订单
	 * 
	 * @param changes
	 * @return
	 */
	Order createChangesOrder(Changes changes);

	/**
	 * 查找订单分页
	 * 
	 * @param orderStatus
	 *            订单状态
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(OrderStatus orderStatus, String sn, Member member, Pageable pageable);

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
	 * 
	 * @param member
	 *            会员
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param pageable
	 *            分页信息
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
	Page<Order> findPage(Date beginDate, Date endDate, OrderStatus orderStatus, PaymentStatus paymentStatus,
			ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable);

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
			Boolean hasExpired, Pageable pageable, Date bDate, Date eDate);

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
	Long count(Member member, OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
			Boolean hasExpired);

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

	/**
	 * 生成订单
	 * 
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param useBalance
	 *            是否使用余额
	 * @param memo
	 *            附言
	 * @return 订单
	 */
	Order build(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo,BigDecimal freight);

	/**
	 * 创建订单
	 * 
	 * @param cart
	 *            购物车
	 * @param receiver
	 *            收货地址
	 * @param paymentMethod
	 *            支付方式
	 * @param shippingMethod
	 *            配送方式
	 * @param couponCode
	 *            优惠码
	 * @param isInvoice
	 *            是否开据发票
	 * @param invoiceTitle
	 *            发票抬头
	 * @param useBalance
	 *            是否使用余额
	 * @param memo
	 *            附言
	 * @param operator
	 *            操作员
	 * @return 订单
	 */
	Order create(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo,
			Long pricePoint, Admin operator,BigDecimal freight,String buyRremark);

	/**
	 * 更新订单
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void update(Order order, Admin operator);

	/**
	 * 订单确认
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void confirm(Order order, Admin operator);

	/**
	 * 订单完成
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void complete(Order order, Admin operator);

	/**
	 * 订单取消
	 * 
	 * @param order
	 *            订单
	 * @param operator
	 *            操作员
	 */
	void cancel(Order order, Admin operator);

	/**
	 * 删除订单
	 * 
	 * @param order
	 * @param operator
	 */
	// void delete(Order order, String operator);

	/**
	 * 订单支付
	 * 
	 * @param order
	 *            订单
	 * @param payment
	 *            收款单
	 * @param operator
	 *            操作员
	 */
	void payment(Order order, Payment payment, Admin operator);

	/**
	 * 订单退款
	 * 
	 * @param order
	 *            订单
	 * @param refunds
	 *            退款单
	 * @param operator
	 *            操作员
	 */
	void refunds(Order order, Refunds refunds, Admin operator);

	/**
	 * 订单发货
	 * 
	 * @param order
	 *            订单
	 * @param shipping
	 *            发货单
	 * @param operator
	 *            操作员
	 */
	void shipping(Order order, Shipping shipping, Admin operator);

	/**
	 * 订单退货
	 * 
	 * @param order
	 *            订单
	 * @param returns
	 *            退货单
	 * @param operator
	 *            操作员
	 */
	void returns(Order order, Returns returns, Admin operator);

	/**
	 * 批量更新待支付状态订单(订单已支付成功)
	 * 
	 * @param pageNumber
	 * @param pageSize
	 */
	void queryOrderStatus(int pageNumber, int pageSize);

	/**
	 * 批量取消待支付订单
	 * 
	 * @param pageNumber
	 * @param pageSize
	 */
	void cancelOrder(int pageNumber, int pageSize);

	/**
	 * 删除订单
	 * 
	 * @param order
	 * @param operator
	 */
	void delete(Order order, String operator);

	/**
	 * 查找有效优惠券
	 * 
	 * @param order
	 * @return
	 */
	List<Object[]> findValidCouponCodes(Long[] productIds, Long[] productcatIds, Long[] parentcatergorytIds,
			Member member);
	
	Page<Order> findPage(Order order, String ssn ,String psn,String pname,String mname,String bDate,String eDate,Integer orderStatue,Integer shippingStatue,Integer payStatue,Pageable pageable,Boolean hasExpired);

	void queryAcceptBiz(int pageNumber, int pageSize);

}