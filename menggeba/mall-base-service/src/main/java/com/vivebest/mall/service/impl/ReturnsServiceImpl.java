/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.constants.CreditEventEnum;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.CouponCodeDao;
import com.vivebest.mall.dao.OrderDao;
import com.vivebest.mall.dao.ReturnsDao;
import com.vivebest.mall.dao.SnDao;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Refunds.Method;
import com.vivebest.mall.entity.Refunds.Type;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.Returns.Status;
import com.vivebest.mall.entity.ReturnsItem;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.service.ChangesService;
import com.vivebest.mall.service.OrderService;
import com.vivebest.mall.service.RefundsService;
import com.vivebest.mall.service.ReturnsService;

/**
 * Service - 退货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("returnsServiceImpl")
public class ReturnsServiceImpl extends BaseServiceImpl<Returns, Long>implements ReturnsService {
	private static Logger logger = Logger.getLogger(ReturnsServiceImpl.class);
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "returnsDaoImpl")
	private ReturnsDao returnsDao;
	@Resource(name = "refundsServiceImpl")
	private RefundsService refundsService;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "changesServiceImpl")
	private ChangesService changesService;

	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;

	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

	@Resource(name = "creditServiceImpl")
	private CreditServiceImpl creditService;

	protected static final String CHARSET = "UTF-8";

	@Resource(name = "returnsDaoImpl")
	public void setBaseDao(ReturnsDao returnsDao) {
		super.setBaseDao(returnsDao);
	}

	@Transactional(readOnly = true)
	public Page<Returns> findPage(Status status, ReturnType returnType,
			com.vivebest.mall.entity.Refunds.Status refundStatus, Pageable pageable) {
		return returnsDao.findPage(status, returnType, refundStatus, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Returns> findPage(Member member, Pageable pageable) {
		return returnsDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Returns> findPage(Status status, Pageable pageable) {
		return returnsDao.findPage(status, pageable);
	}

	@Transactional(readOnly = true)
	public Returns build(OrderItem orderItem,BigDecimal price, ReturnType returnType, Member member, String returnCause,
			String returnDesc, String returnVoucher) {
		Assert.notNull(orderItem);
		Assert.notNull(orderItem.getOrder());

		Order order = orderItem.getOrder();
		Returns returns = new Returns();

		if (returnType != null) {
			returns.setReturnType(returnType);
		} else {
			if (OrderStatus.paymented.equals(order.getOrderStatus())
					|| OrderStatus.unsend.equals(order.getOrderStatus())) {
				returns.setReturnType(ReturnType.directlyRefund);// 退货类型:直接退款
			} else if (OrderStatus.shipped.equals(order.getOrderStatus())
					|| OrderStatus.completed.equals(order.getOrderStatus())) {
				returns.setReturnType(ReturnType.returnToRefund);// 退货类型:先退货再退款
			} else {
				return null;
			}
		}

		returns.setMember(member);
		returns.setSn("m"+snDao.generate(Sn.Type.returns));// 编号
		returns.setAddress(order.getAddress());// 地址
		returns.setArea(order.getArea().getName());// 地区
		returns.setPhone(order.getPhone());// 电话

		returns.setOperator(member.getUsername());// 操作员(空！)
		returns.setShipper(member.getUsername());// 发货人

		returns.setZipCode(order.getZipCode());// 运单号
		returns.setOrderItem(orderItem);// 订单项
		returns.setOrder(order);// 订单

		returns.setReturnCause(returnCause);// 退货理由
		returns.setReturnDesc(returnDesc);// 退货描述
		returns.setReturnVoucher(returnVoucher);// 退货凭证
		returns.setStatus(Status.waitingApprove);// 退货状态
		returns.setReturnDate(new Date());// 退货日期

		List<ReturnsItem> returnsItems = new ArrayList<ReturnsItem>();
		ReturnsItem returnsItem = new ReturnsItem();
		returnsItem.setName(orderItem.getName());// 商品名称
		returnsItem.setQuantity(orderItem.getQuantity());// 商品数量
		
		BigDecimal freight = this.freight(order);
		// 订单有使用优惠券
		if (price!=null) {
			returnsItem.setPrice(price.add(freight));
		}else
		if (orderItem.getCouponDivide() != null)
			returnsItem.setPrice(orderItem.getPayPrice().subtract(orderItem.getCouponDivide()).add(freight));
		else
			returnsItem.setPrice(orderItem.getPayPrice().add(freight)); //应该是payPrice
            
		returnsItem.setSn(orderItem.getSn());// 商品编号
		returnsItem.setReturns(returns);// 退货单
		returnsItems.add(returnsItem);

		returns.setReturnsItems(returnsItems);

		returnsDao.merge(returns);
		return returns;
	}

	@Transactional(readOnly = true)
	public void recevied(Returns returns, Admin operator) {
		Assert.notNull(returns);
		Assert.notNull(returns.getOrder());
		Assert.notNull(returns.getMember());

		Refunds refunds = new Refunds();
		refunds.setSn("m"+snDao.generate(Sn.Type.refunds));
		refunds.setReturns(returns);
		refunds.setMember(returns.getMember());
		refunds.setOperator(operator.getUsername());
		refunds.setOrder(returns.getOrder());
		refunds.setMethod(Method.online);
		refunds.setStatus(com.vivebest.mall.entity.Refunds.Status.refunding);
		if (ReturnType.directlyRefund.equals(returns.getReturnType())) {
			refunds.setType(Type.onlyRefund);
		} else if (ReturnType.returnToRefund.equals(returns.getReturnType())) {
			refunds.setType(Type.refundAfterReturn);
		} else if (ReturnType.bookingRefund.equals(returns.getReturnType())) {
			refunds.setType(Type.bookingRefund);
		}

		Order order = refunds.getOrder();
		
		CouponCode couponCode = order.getCouponCode(); // 退优惠券
		if (couponCode != null) {
			couponCode.setIsUsed(false);
			couponCode.setUsedDate(null);
			couponCodeDao.merge(couponCode);

			order.setCouponCode(null);
			orderDao.flush();

		}
		
		refunds.setPaymentMethod(order.getPaymentMethodName());

		ReturnsItem returnsItem = returns.getReturnsItems().get(0);
		if (returnsItem == null) {
			refunds.setAmount(new BigDecimal(0));
		}
		// 订单有使用优惠券，退款金额减去相应的优惠券
		OrderItem orderItem = returns.getOrderItem();
		if(orderItem!=null)
		{
			if (orderItem.getCouponDivide() != null) {
				refunds.setAmount(orderItem.getPayPrice().subtract(orderItem.getCouponDivide()));
			} else {
				refunds.setAmount(orderItem.getPayPrice());
			}
			creditService.engineMengTopUp(order.getMember().getSn(), orderItem.getPayPricePoint(), "取消订单返回消费的萌值", CreditEventEnum.CancelOrder); //针对退款&退货的流程，退回萌值。
		}
		
		List<Refunds> list = new ArrayList<Refunds>();
		list.add(refunds);

		returns.setRefunds(list);
		returns.setStatus(Status.received);
		orderClose(returns);
		returnsDao.merge(returns);
		returnsDao.flush();
	}

	@Transactional(readOnly = true)
	public void approve(Returns returns, BigDecimal returnPrice, Admin operator, String auditDesc) {
		Assert.notNull(returns);
		Assert.notNull(returns.getOrder());
		Assert.notNull(returns.getMember());

		if (ReturnType.directlyRefund.equals(returns.getReturnType())
				|| ReturnType.bookingRefund.equals(returns.getReturnType())) {
			// 直接退款,生成退款单
			Refunds refunds = new Refunds();
			refunds.setSn("m"+snDao.generate(Sn.Type.refunds));
			refunds.setReturns(returns);
			refunds.setMember(returns.getMember());
			refunds.setOperator(operator.getUsername());
			refunds.setOrder(returns.getOrder());
			refunds.setMethod(Method.online);
			refunds.setStatus(com.vivebest.mall.entity.Refunds.Status.refunding);

			if (ReturnType.directlyRefund.equals(returns.getReturnType())) {
				refunds.setType(Type.onlyRefund);
			} else if (ReturnType.returnToRefund.equals(returns.getReturnType())) {
				refunds.setType(Type.refundAfterReturn);
			} else if (ReturnType.bookingRefund.equals(returns.getReturnType())) {
				refunds.setType(Type.bookingRefund);
			}
			Order order = refunds.getOrder();
			refunds.setPaymentMethod(order.getPaymentMethodName());
			

			ReturnsItem returnsItem = returns.getReturnsItems().get(0);
			if (returnsItem == null) {
				refunds.setAmount(new BigDecimal(0));
			}
			// refunds.setAmount(returnsItem.getSubtotal());
			if (returnPrice.compareTo(new BigDecimal(0)) == 1) {
				refunds.setAmount(returnPrice);
				returns.getReturnsItems().get(0).setPrice(returnPrice);
				
			} else {
				if (returns.getOrderItem().getCouponDivide() != null) {
					BigDecimal refendAmount = returns.getOrderItem().getPayPrice()
							.subtract(returns.getOrderItem().getCouponDivide());
					refunds.setAmount(refendAmount);
					if (refendAmount.compareTo(BigDecimal.ZERO) == 0) {
						refunds.setStatus(com.vivebest.mall.entity.Refunds.Status.refundSuccess);
					}
				} else
					refunds.setAmount(returns.getOrderItem().getPayPrice());
			}
			CouponCode couponCode = order.getCouponCode(); // 退优惠券
			if (couponCode != null) {
				couponCode.setIsUsed(false);
				couponCode.setUsedDate(null);
				couponCodeDao.merge(couponCode);

				order.setCouponCode(null);
				orderDao.flush();

			}

			//
			creditService.engineMengTopUp(order.getMember().getSn(), returns.getOrderItem().getPayPricePoint(), "退款返回消费的积分", CreditEventEnum.CancelOrder);
			
			creditService.engineMengTopUp(order.getMember().getSn(), 0-order.getAllProductPoint(), "退款扣除赠送积分", CreditEventEnum.ShopChange);

			if (order.getRptUuid() == null) // 标明是全萌值支付不需要走跑批退款，直接处理退款成功。
			{
				refunds.setStatus(com.vivebest.mall.entity.Refunds.Status.refundSuccess);
			}

			List<Refunds> list = new ArrayList<Refunds>();
			list.add(refunds);
			returns.setRefunds(list);
		}
		if (returnPrice.compareTo(new BigDecimal(0)) == 1) {
			returns.getReturnsItems().get(0).setPrice(returnPrice);
		}
		returns.setStatus(Status.approved);
		returns.setAuditApply(operator.getUsername());
		returns.setAuditDate(new Date());
		returns.setAuditDesc(auditDesc);
		if (ReturnType.directlyRefund.equals(returns.getReturnType())) {
			orderClose(returns);
		}
		returnsDao.merge(returns);

		returnsDao.flush();

	}

	@Transactional(readOnly = true)
	public void orderClose(Returns returns) {
		Order order = returns.getOrder();
		if (order != null && !order.getOrderItems().isEmpty()) {
			int count = 0;
			for (OrderItem orderItem : order.getOrderItems()) {
				Changes change = changesService.findByOrderItem(orderItem.getId());
				List<Returns> returns2 = returnsDao.findByOrderItem(orderItem.getId());
				List<Returns> returnss=new ArrayList<Returns>();
				Returns return2=null;
				if (returns2 != null) {
					returnss.addAll(returns2);
					for (Returns ret : returnss) {
						if (ReturnType.bookingRefund.equals(ret.getReturnType())) {
							returns2.remove(ret);
						} else {
							return2 = ret;
							break;
						}
					}
				}
				if (change != null && (com.vivebest.mall.entity.Changes.Status.completeRep.equals(change.getStatus())
						|| Status.delivered.equals(change.getStatus()))) {
					count++;
				} else if (return2 != null) {
					if (ReturnType.directlyRefund.equals(return2.getReturnType())) {
						if (Status.approved.equals(return2.getStatus())) {
							count++;
						}
					} else {
						if (Status.dealwith.equals(return2.getStatus())
								|| com.vivebest.mall.entity.Returns.Status.received.equals(return2.getStatus())) {
							count++;
						}
					}
				} else {
					break;
				}
			}
			if (count == order.getOrderItems().size()) {
				order.setOrderStatus(OrderStatus.closed);
				try {
					orderService.update(order);
				} catch (Exception e) {
					logger.info(e);
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public List<Returns> findByOrderItem(Long orderItemId) {
		return returnsDao.findByOrderItem(orderItemId);
	}

	@Transactional(readOnly = true)
	public void applyAgain(Returns returns,BigDecimal price,ReturnType returnType, OrderItem orderItem, Member member, String returnCause, String returnDesc,
			String returnVoucher) {
		returnsDao.remove(returns);
		build(orderItem,price, returnType, member, returnCause, returnDesc, returnVoucher);
		returnsDao.flush();
	}

	@Override
	public Long unReturnsCount() {
		return returnsDao.unReturnsCount();
	}

	/**
	 * 运费
	 * @param order
	 * @return
	 */
	public BigDecimal freight(Order order){
		int i=0,j=0;
		BigDecimal freight = new BigDecimal(0);
		if(order != null){
			if(order.getOrderItems().size() == 1){
				return order.getFreight();
			}
			List<Returns> list = returnsDao.findByOrder(order.getId());
			if(list != null && !list.isEmpty()){
				for(Returns returns2 : list){
					for(ReturnsItem returnsItem : returns2.getReturnsItems()){
						if(returnsItem.getPrice().compareTo(returns2.getOrderItem().getSubtotal()) == 0){
							if(Returns.Status.unapprove.equals(returns2.getStatus())){
								j++;
							}else{
								i++;
							}
						}else{
							if(Returns.Status.unapprove.equals(returns2.getStatus())){
								j++;
							}
						}
					}
				}
			}
			if(j > 1){
				return new BigDecimal(0);
			}
			if((order.getOrderItems().size() - i) < 2){
				freight = order.getFreight();
			}
		}
		return freight;
	}
}