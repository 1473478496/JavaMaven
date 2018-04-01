/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.common.Setting.StockAllocationTime;
import com.vivebest.mall.core.constants.CreditEventEnum;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.core.util.JsonUtils;
import com.vivebest.mall.core.util.MD5;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SignatureUtils;
import com.vivebest.mall.dao.CartDao;
import com.vivebest.mall.dao.ChangesDao;
import com.vivebest.mall.dao.CouponCodeDao;
import com.vivebest.mall.dao.CouponDao;
import com.vivebest.mall.dao.DepositDao;
import com.vivebest.mall.dao.MemberDao;
import com.vivebest.mall.dao.MemberRankDao;
import com.vivebest.mall.dao.OrderDao;
import com.vivebest.mall.dao.OrderItemDao;
import com.vivebest.mall.dao.OrderLogDao;
import com.vivebest.mall.dao.PaymentDao;
import com.vivebest.mall.dao.ProductDao;
import com.vivebest.mall.dao.RefundsDao;
import com.vivebest.mall.dao.ReturnsDao;
import com.vivebest.mall.dao.ShippingDao;
import com.vivebest.mall.dao.SnDao;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Cart;
import com.vivebest.mall.entity.CartItem;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.Coupon;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Deposit;
import com.vivebest.mall.entity.GiftItem;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.entity.MemberRankRigths;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.OrderType;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.OrderLog;
import com.vivebest.mall.entity.OrderLog.Type;
import com.vivebest.mall.entity.Payment;
import com.vivebest.mall.entity.PaymentMethod;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.entity.Receiver;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.ReturnsItem;
import com.vivebest.mall.entity.Shipping;
import com.vivebest.mall.entity.ShippingItem;
import com.vivebest.mall.entity.ShippingMethod;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.service.CouponCodeService;
import com.vivebest.mall.service.CouponService;
import com.vivebest.mall.service.CreditService;
import com.vivebest.mall.service.OrderService;
import com.vivebest.mall.service.PromotionProductsService;
import com.vivebest.mall.service.StaticService;

/**
 * Service - 订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("orderServiceImpl")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long>implements OrderService {

	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Value("${pay.acceptBizNo}")
	private String acceptBizNo;
	@Value("${pay.manager.md5.key}")
	private String managerMd5Key;
	@Value("${pay.queryOrder.url}")
	private String queryOrderUrl;

	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;
	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;
	@Resource(name = "orderLogDaoImpl")
	private OrderLogDao orderLogDao;
	@Resource(name = "cartDaoImpl")
	private CartDao cartDao;
	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "depositDaoImpl")
	private DepositDao depositDao;
	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	@Resource(name = "shippingDaoImpl")
	private ShippingDao shippingDao;
	@Resource(name = "returnsDaoImpl")
	private ReturnsDao returnsDao;
	@Resource(name = "changesDaoImpl")
	private ChangesDao changesDao;
	@Resource(name = "staticServiceImpl")
	private StaticService staticService;

	@Resource(name = "couponServiceImpl")
	private CouponService couponService;

	@Resource(name = "couponDaoImpl")
	private CouponDao couponDao;
	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductsService;
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;

	@Resource(name = "creditServiceImpl")
	private CreditService creditService;

	@Resource(name = "orderDaoImpl")
	public void setBaseDao(OrderDao orderDao) {
		super.setBaseDao(orderDao);
	}

	@Transactional(readOnly = true)
	public Order findBySn(String sn) {
		return orderDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public List<Order> findList(Member member, Integer count, List<Filter> filters,
			List<com.vivebest.mall.core.common.Order> orders) {
		return orderDao.findList(member, count, filters, orders);
	}
	
	@Transactional(readOnly = true)
	private Page<Order> findPageMeOrderId(String sn, String member,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return orderDao.findPageMeOrderId(sn, member, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(OrderStatus orderStatus, String sn, Member member, Pageable pageable) {
		return orderDao.findPage(orderStatus, sn, member, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Member member, Pageable pageable) {
		return orderDao.findPage(member, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Member member, Date beginDate, Date endDate, Pageable pageable) {
		return orderDao.findPage(member, beginDate, endDate, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Date beginDate, Date endDate, OrderStatus orderStatus, PaymentStatus paymentStatus,
			ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable) {
		return orderDao.findPage(beginDate, endDate, orderStatus, paymentStatus, shippingStatus, hasExpired, pageable);
	}

	@Transactional(readOnly = true)
	public Page<Order> findPage(Order order, String ssn, String psn, String pname, String mname, String bDate,
			String eDate, Integer orderStatue, Integer shippingStatue, Integer payStatue, Pageable pageable,
			Boolean hasExpired) {
		List list = orderDao.findPage(order, ssn, psn, pname, mname, bDate, eDate, orderStatue, shippingStatue,
				payStatue, hasExpired);
		return orderDao.findById(list, pageable);
	}

	@Transactional(readOnly = true)
	public Long count(Member member, OrderStatus orderStatus, PaymentStatus paymentStatus,
			ShippingStatus shippingStatus, Boolean hasExpired) {
		return orderDao.count(member, orderStatus, paymentStatus, shippingStatus, hasExpired);
	}

	@Transactional(readOnly = true)
	public Long waitingPaymentCount(Member member) {
		return orderDao.waitingPaymentCount(member);
	}

	@Transactional(readOnly = true)
	public Long waitingShippingCount(Member member) {
		return orderDao.waitingShippingCount(member);
	}

	@Transactional(readOnly = true)
	public BigDecimal getSalesAmount(Date beginDate, Date endDate) {
		return orderDao.getSalesAmount(beginDate, endDate);
	}

	@Transactional(readOnly = true)
	public Integer getSalesVolume(Date beginDate, Date endDate) {
		return orderDao.getSalesVolume(beginDate, endDate);
	}

	public void releaseStock() {
		orderDao.releaseStock();
	}

	public void orderComplete() {
		List<Order> orders = orderDao.findList(OrderStatus.shipped, PaymentStatus.paid, ShippingStatus.shipped, null,
				null, null, null);
		List<Shipping> shippings = new ArrayList<Shipping>();

		Calendar cal = Calendar.getInstance();
		DateFormat d1 = DateFormat.getDateInstance();
		cal.add(Calendar.DATE, -14);
		Date before = cal.getTime();
		String str1 = d1.format(before);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date compareDate = null;
		try {
			compareDate = format.parse(str1);
		} catch (ParseException e) {
			logger.error("日期格式化失败", e);
		}
		if (orders != null && orders.size() > 0) {
			for (Order order : orders) {
				shippings.addAll(order.getShippings());
			}
			for (Shipping shipping : shippings) {
				Date createDate = shipping.getCreateDate();
				Order order = shipping.getOrder();
				if (createDate.before(compareDate)) {
					// 赠送积分
					Map<String, Object> map = creditService.engineMengTopUp(order.getMember().getSn(), order.getAllProductPoint(),
							"确认收货赠送积分", CreditEventEnum.ShopChange);
					if (map != null && "00".equals(map.get("txStatus"))) {
						//赠送积分成功，更新订单状态
						order.setOrderStatus(OrderStatus.completed);
						this.update(order);
					}
				}
			}
		}
	}

	@Transactional(readOnly = true)
	public Order buildChangeOrder(Changes changes) {
		Order or = changes.getOrders();
		OrderItem orderI = changes.getOrderItem();
		Order order = new Order();
		order.setShippingStatus(ShippingStatus.unshipped);
		order.setFee(new BigDecimal(0));
		order.setPromotionDiscount(new BigDecimal(0));
		order.setCouponDiscount(new BigDecimal(0));
		order.setOffsetAmount(new BigDecimal(0));
		order.setPoint(new Long(0));
		order.setMemo(changes.getMemo());
		order.setMember(or.getMember());
		order.setConsignee(or.getConsignee());
		order.setAreaName(or.getAreaName());
		order.setAddress(or.getAddress());
		order.setZipCode(or.getZipCode());
		order.setPhone(or.getPhone());
		order.setArea(or.getArea());
		order.setPromotion(or.getPromotion());
		order.setPaymentMethod(or.getPaymentMethod());
		order.setFreight(new BigDecimal(0));
		order.setShippingMethod(changes.getShippingMethod());
		order.setShippingMethodName(changes.getShippingMethod().getName());
		order.setIsInvoice(false);
		order.setTax(new BigDecimal(0));
		order.setExpire(or.getExpire());
		order.setSn("m" + snDao.generate(Sn.Type.order));

		List<OrderItem> orderItems = or.getOrderItems();
		if (orderI != null) {
			Product product = orderI.getProduct();
			OrderItem orderItem = new OrderItem();
			orderItem.setSn(product.getSn());
			orderItem.setName(product.getName());
			orderItem.setFullName(product.getFullName());
			orderItem.setPrice(orderI.getPrice());
			orderItem.setWeight(product.getWeight());
			orderItem.setThumbnail(product.getThumbnail());
			orderItem.setIsGift(false);
			orderItem.setQuantity(orderI.getQuantity());
			orderItem.setShippedQuantity(0);
			orderItem.setReturnQuantity(0);
			orderItem.setProduct(product);
			orderItem.setOrder(order);
			orderItem.setPayPrice(orderI.getPayPrice()); // 设置为减去使用优惠券的金额
			orderItem.setPayPricePoint(orderI.getPayPricePoint()); // 设置使用积分
			orderItem.setPricePoint(orderI.getPricePoint());
			if (orderI.getPromotionProducts() != null) {
				orderItem.setPromotionProducts(orderI.getPromotionProducts());
			}
			orderItems.add(orderItem);
		}
		order.setAmountPaid(orderI.getSubtotal());
		order.setOrderStatus(OrderStatus.paymented);
		order.setPoint(new Long(0));
		order.setOrderType(OrderType.changeOrder);
		order.setPaymentStatus(PaymentStatus.paid);
		order.setShippingStatus(ShippingStatus.unshipped);
		order.setOperator(changes.getAuditApply());
		order.setShippingMethod(changes.getShippingMethod());
		order.setShippingMethodName(changes.getDeliveryCorp());

		return order;
	}

	/**
	 * 根据换货单生成新的订单
	 */
	public Order createChangesOrder(Changes changes) {
		Assert.notNull(changes);
		Order order = buildChangeOrder(changes);
		Setting setting = SettingUtils.get();
		if (setting.getStockAllocationTime() == StockAllocationTime.order
				|| (setting.getStockAllocationTime() == StockAllocationTime.payment
						&& (order.getPaymentStatus() == PaymentStatus.partialPayment
								|| order.getPaymentStatus() == PaymentStatus.paid))) {
			order.setIsAllocatedStock(true);
		} else {
			order.setIsAllocatedStock(false);
		}
		orderDao.persist(order);
		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.create);
		orderLog.setOperator(changes.getOperator() != null ? changes.getOperator().getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem != null) {
				Product product = orderItem.getProduct();
				productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
				if (product != null && product.getStock() != null) {
					product.setAllocatedStock(
							product.getAllocatedStock() + (orderItem.getQuantity() - orderItem.getShippedQuantity()));
					productDao.merge(product);
					orderDao.flush();
					staticService.build(product);
				}
			}
		}
		return order;
	}

	/**
	 * 支付金额
	 * 
	 * @param price
	 * @param member
	 * @return
	 */
	public BigDecimal payPrice(BigDecimal price, Member member) {
		if (null != price && !price.equals(0)) {
			BigDecimal amount = price;
			if (amount.compareTo(new BigDecimal(0)) > 0) {
				Set<MemberRankRigths> memberRankRigthsSet = member.getMemberRank().getMemberRankRights();
				Iterator<MemberRankRigths> i = memberRankRigthsSet.iterator();
				while (i.hasNext()) {
					MemberRankRigths memberRankRigths = i.next();
					if (MemberRankRigths.RightsName.priceConcessions.equals(memberRankRigths.getRightsName())
							&& null != memberRankRigths.getRightsValue()) {
						amount = amount.multiply(new BigDecimal(memberRankRigths.getRightsValue()))
								.divide(new BigDecimal(100));
					}
				}
			}
			if (amount == null)
				return new BigDecimal(0);
			return amount;
		} else {
			return new BigDecimal(0);
		}
	}

	@Transactional(readOnly = true)
	public Order build(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo,
			BigDecimal fre) {
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());
		Order order = new Order();
		order.setShippingStatus(ShippingStatus.unshipped);
		order.setFee(new BigDecimal(0));
		order.setPromotionDiscount(cart.getDiscount());
		order.setCouponDiscount(new BigDecimal(0));
		order.setOffsetAmount(new BigDecimal(0));
		// order.setPoint(cart.getEffectivePoint());
		// order.setPricePoint(cart.getPricePoint());
		order.setMemo(memo);
		order.setMember(cart.getMember());

		if (receiver != null) {
			order.setConsignee(receiver.getConsignee());
			order.setAreaName(receiver.getAreaName());
			order.setAddress(receiver.getAddress());
			order.setZipCode(receiver.getZipCode());
			order.setPhone(receiver.getPhone());
			order.setArea(receiver.getArea());
		}

		if (!cart.getPromotions().isEmpty()) {
			StringBuffer promotionName = new StringBuffer();
			for (Promotion promotion : cart.getPromotions()) {
				if (promotion != null && promotion.getName() != null) {
					promotionName.append(" " + promotion.getName());
				}
			}
			if (promotionName.length() > 0) {
				promotionName.deleteCharAt(0);
			}
			order.setPromotion(promotionName.toString());
		}

		order.setPaymentMethod(paymentMethod);
		order.setOrderType(OrderType.commonOrder);
		if (shippingMethod != null && paymentMethod != null
				&& paymentMethod.getShippingMethods().contains(shippingMethod)) {
			BigDecimal freight = shippingMethod.calculateFreight(cart.getWeight());
			if (fre != null) {
				freight = fre;
			}
			for (Promotion promotion : cart.getPromotions()) {
				if (promotion.getIsFreeShipping()) {
					freight = new BigDecimal(0);
					break;
				}
			}
			order.setFreight(freight);
			order.setShippingMethod(shippingMethod);
		} else {
			order.setFreight(new BigDecimal(0));
		}
		order.setSn("m" + snDao.generate(Sn.Type.order));
		if (couponCode != null && cart.isCouponAllowed()) {
			couponCodeDao.lock(couponCode, LockModeType.PESSIMISTIC_WRITE);
			if (!couponCode.getIsUsed() && couponCode.getCoupon() != null && cart.isValid(couponCode.getCoupon())) {
				BigDecimal couponDiscount = cart.getEffectivePrice()
						.subtract(couponCode.getCoupon().calculatePrice(cart.getQuantity(), cart.getEffectivePrice()));
				couponDiscount = couponDiscount.compareTo(new BigDecimal(0)) > 0 ? couponDiscount : new BigDecimal(0);
				order.setCouponDiscount(couponDiscount);
				order.setCouponCode(couponCode);
			}
		}

		List<OrderItem> orderItems = order.getOrderItems();
		for (CartItem cartItem : cart.getCartItems()) {
			if (cartItem != null && cartItem.getProduct() != null) {

				Product product = cartItem.getProduct();
				OrderItem orderItem = new OrderItem();
				orderItem.setSn(product.getSn());
				orderItem.setName(product.getName());
				orderItem.setFullName(product.getFullName());
				orderItem.setPrice(cartItem.getPrice());
				orderItem.setWeight(product.getWeight());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setIsGift(false);
				orderItem.setQuantity(cartItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				orderItem.setProduct(product);
				orderItem.setOrder(order);
				orderItem.setPricePoint(cartItem.getPricePoint());
				orderItem.setPayPrice(this.payPrice(cartItem.getPrice(), order.getMember())
						.multiply(new BigDecimal(cartItem.getQuantity())));

				// 支付萌值
				Long payPricePointLong = (this.payPrice(new BigDecimal(cartItem.getPricePoint()), order.getMember()))
						.longValue();
				orderItem.setPayPricePoint(payPricePointLong * cartItem.getQuantity());
				if (cartItem.getPromotionProducts() != null) {
					orderItem.setPromotionProducts(cartItem.getPromotionProducts());
				}
				orderItems.add(orderItem);
			}
		}

		// 订单有使用优惠券分摊优惠券计算

		if (order.getCouponDiscount().compareTo(new BigDecimal(0)) > 0) {

			BigDecimal cscal = null;// 分摊比列标志
			BigDecimal cscalprice = BigDecimal.ZERO; // 先计算需要分摊的总额

			for (OrderItem orderitem : orderItems) {
				if (orderitem.getProduct().getSuit()) { // 有优惠套装的商品不参与优惠券

				} else if (orderitem.getPromotionCouponAllowed()) // 其它可以使用优惠券的
				{
					cscalprice = cscalprice.add(orderitem.getSubtotal());

				}

			}
			if (cscalprice.compareTo(BigDecimal.ZERO) > 0) // 开始分摊每项目的金额
			{
				for (OrderItem orderitem : orderItems) {
					if (orderitem.getProduct().getSuit()) { // 有优惠套装的商品不参与优惠券

					} else if (orderitem.getPromotionCouponAllowed()) // 其它可以使用优惠券的
					{
						cscal = orderitem.getPayPrice().divide(cscalprice, 2, BigDecimal.ROUND_HALF_UP); // 4舍5入取2位比率
						orderitem.setCouponDivide(order.getCouponDiscount().multiply(cscal));
					}

				}
			}

		}

		for (GiftItem giftItem : cart.getGiftItems()) {
			if (giftItem != null && giftItem.getGift() != null) {
				Product gift = giftItem.getGift();
				OrderItem orderItem = new OrderItem();
				orderItem.setSn(gift.getSn());
				orderItem.setName(gift.getName());
				orderItem.setFullName(gift.getFullName());
				orderItem.setPrice(new BigDecimal(0));
				orderItem.setWeight(gift.getWeight());
				orderItem.setThumbnail(gift.getThumbnail());
				orderItem.setIsGift(true);
				orderItem.setQuantity(giftItem.getQuantity());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				orderItem.setProduct(gift);
				orderItem.setOrder(order);
				orderItems.add(orderItem);
			}
		}

		Setting setting = SettingUtils.get();
		if (setting.getIsInvoiceEnabled() && isInvoice && StringUtils.isNotEmpty(invoiceTitle)) {
			order.setIsInvoice(true);
			order.setInvoiceTitle(invoiceTitle);
			order.setTax(order.calculateTax());
		} else {
			order.setIsInvoice(false);
			order.setTax(new BigDecimal(0));
		}

		if (useBalance) {
			Member member = cart.getMember();
			if (member.getBalance().compareTo(order.getAmount()) >= 0) {
				order.setAmountPaid(order.getAmount());
			} else {
				order.setAmountPaid(member.getBalance());
			}
		} else {
			order.setAmountPaid(new BigDecimal(0));
		}

		if (order.getAmountPayable().compareTo(new BigDecimal(0)) == 0) {
			order.setOrderStatus(OrderStatus.paymented);
			order.setPaymentStatus(PaymentStatus.paid);
		} else if (order.getAmountPayable().compareTo(new BigDecimal(0)) > 0
				&& order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setOrderStatus(OrderStatus.paymented);
			order.setPaymentStatus(PaymentStatus.partialPayment);
		} else {
			order.setOrderStatus(OrderStatus.unpayment);
			order.setPaymentStatus(PaymentStatus.unpaid);
		}

		if (paymentMethod != null && paymentMethod.getTimeout() != null
				&& order.getPaymentStatus() == PaymentStatus.unpaid) {
			order.setExpire(DateUtils.addMinutes(new Date(), paymentMethod.getTimeout()));
		}

		return order;
	}

	@Transactional(readOnly = true)
	public Order create(Cart cart, Receiver receiver, PaymentMethod paymentMethod, ShippingMethod shippingMethod,
			CouponCode couponCode, boolean isInvoice, String invoiceTitle, boolean useBalance, String memo,
			Long pricePoint, Admin operator, BigDecimal freight, String buyRremark) {
		Assert.notNull(cart);
		Assert.notNull(cart.getMember());
		Assert.notEmpty(cart.getCartItems());
		Assert.notNull(receiver);
		Assert.notNull(paymentMethod);
		Assert.notNull(shippingMethod);

		Order order = build(cart, receiver, paymentMethod, shippingMethod, couponCode, isInvoice, invoiceTitle,
				useBalance, memo, freight);
		order.setPoint(order.getPrice().longValue());
		if (paymentMethod.getMethod() == PaymentMethod.Method.online) {
			logger.info("[订单创建订单编号:]" + order.getSn());
			order.setLockExpire(DateUtils.addSeconds(new Date(), 5));
			logger.info("[订单创建new Date():]" + new Date());
			logger.info("[订单创建getLockExpire():]" + order.getLockExpire());
			order.setOperator(operator);
		}

		if (order.getCouponCode() != null) {
			couponCode.setIsUsed(true);
			couponCode.setUsedDate(new Date());
			couponCodeDao.merge(couponCode);
		}

		for (Promotion promotion : cart.getPromotions()) {
			for (Coupon coupon : promotion.getCoupons()) {
				order.getCoupons().add(coupon);
			}
		}

		Setting setting = SettingUtils.get();
		if (setting.getStockAllocationTime() == StockAllocationTime.order
				|| (setting.getStockAllocationTime() == StockAllocationTime.payment
						&& (order.getPaymentStatus() == PaymentStatus.partialPayment
								|| order.getPaymentStatus() == PaymentStatus.paid))) {
			order.setIsAllocatedStock(true);
		} else {
			order.setIsAllocatedStock(false);
		}

		order.setBuyRemark(buyRremark);

		try {
			orderDao.persist(order);
		} catch (Exception e) {
			e.printStackTrace();
		}

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.create);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);

		Member member = cart.getMember();
		if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setBalance(member.getBalance().subtract(order.getAmountPaid()));
			memberDao.merge(member);
			Deposit deposit = new Deposit();
			deposit.setType(operator != null ? Deposit.Type.adminPayment : Deposit.Type.memberPayment);
			deposit.setCredit(new BigDecimal(0));
			deposit.setDebit(order.getAmountPaid());
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMember(member);
			deposit.setOrder(order);
			depositDao.persist(deposit);
		}

		if (setting.getStockAllocationTime() == StockAllocationTime.order
				|| (setting.getStockAllocationTime() == StockAllocationTime.payment
						&& (order.getPaymentStatus() == PaymentStatus.partialPayment
								|| order.getPaymentStatus() == PaymentStatus.paid))) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null) {
						PromotionProducts ps = promotionProductsService.findByProductId(product.getId(), null);
						if (ps != null) {
							if (ps.getSaleQuantity() == null) {
								ps.setSaleQuantity(0 + orderItem.getQuantity());
							} else {
								ps.setSaleQuantity(ps.getSaleQuantity() + orderItem.getQuantity());
							}
							promotionProductsService.update(ps);
						} else if (ps == null && product.getStock() != null) {
							product.setAllocatedStock(product.getAllocatedStock()
									+ (orderItem.getQuantity() - orderItem.getShippedQuantity()));
							productDao.merge(product);
							orderDao.flush();
							staticService.build(product);
						}
					}
				}
			}
		}

		return order;
	}

	public void update(Order order, Admin operator) {
		Assert.notNull(order);

		Order pOrder = orderDao.find(order.getId());

		if (pOrder.getIsAllocatedStock()) {
			for (OrderItem orderItem : pOrder.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								- (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								+ (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						productDao.flush();
						staticService.build(product);
					}
				}
			}
		}

		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.modify);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void confirm(Order order, Admin operator) {
		Assert.notNull(order);

		order.setOrderStatus(OrderStatus.paymented);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.confirm);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void complete(Order order, Admin operator) {
		Assert.notNull(order);

		Member member = order.getMember();
		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);

		if (order.getShippingStatus() == ShippingStatus.partialShipment
				|| order.getShippingStatus() == ShippingStatus.shipped) {
			member.setPoint(member.getPoint() + order.getPoint());
			for (Coupon coupon : order.getCoupons()) {
				couponCodeDao.build(coupon, member);
			}
		}
		order.setOrderStatus(OrderStatus.completed);
		if (order.getShippingStatus() == ShippingStatus.unshipped
				|| order.getShippingStatus() == ShippingStatus.returned) {
			CouponCode couponCode = order.getCouponCode();
			if (couponCode != null) {
				couponCode.setIsUsed(false);
				couponCode.setUsedDate(null);
				couponCodeDao.merge(couponCode);

				order.setCouponCode(null);
				orderDao.merge(order);
			}
		}

		member.setAmount(member.getAmount().add(order.getAmountPaid()));
		if (!member.getMemberRank().getIsSpecial()) {
			MemberRank memberRank = memberRankDao.findByAmount(member.getAmount());
			if (memberRank != null && memberRank.getAmount().compareTo(member.getMemberRank().getAmount()) > 0) {
				member.setMemberRank(memberRank);
			}
		}
		memberDao.merge(member);

		if (order.getIsAllocatedStock()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								- (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(false);
		}

		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem != null) {
				Product product = orderItem.getProduct();
				productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
				if (product != null) {
					Integer quantity = orderItem.getQuantity();
					Calendar nowCalendar = Calendar.getInstance();
					Calendar weekSalesCalendar = DateUtils.toCalendar(product.getWeekSalesDate());
					Calendar monthSalesCalendar = DateUtils.toCalendar(product.getMonthSalesDate());
					if (nowCalendar.get(Calendar.YEAR) != weekSalesCalendar.get(Calendar.YEAR)
							|| nowCalendar.get(Calendar.WEEK_OF_YEAR) > weekSalesCalendar.get(Calendar.WEEK_OF_YEAR)) {
						product.setWeekSales((long) quantity);
					} else {
						product.setWeekSales(product.getWeekSales() + quantity);
					}
					if (nowCalendar.get(Calendar.YEAR) != monthSalesCalendar.get(Calendar.YEAR)
							|| nowCalendar.get(Calendar.MONTH) > monthSalesCalendar.get(Calendar.MONTH)) {
						product.setMonthSales((long) quantity);
					} else {
						product.setMonthSales(product.getMonthSales() + quantity);
					}
					product.setSales(product.getSales() + quantity);
					product.setWeekSalesDate(new Date());
					product.setMonthSalesDate(new Date());
					productDao.merge(product);
					orderDao.flush();
					staticService.build(product);
				}
			}
		}
		order.setExpire(null);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.complete);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLog.setContent(order.getSn() + "|" + order.getOrderStatus());
		orderLogDao.persist(orderLog);
	}

	// public void delete(Order order,String operator)
	// {
	// order.setOrderStatus(OrderStatus.deleted);
	// orderDao.merge(order);
	// OrderLog orderLog = new OrderLog();
	// orderLog.setType(Type.deleted);
	// orderLog.setOperator(operator);
	// orderLog.setContent(operator+"删除他的订单");
	// orderLog.setOrder(order);
	// orderLogDao.persist(orderLog);
	// }

	public void cancel(Order order, Admin operator) {
		Assert.notNull(order);

		CouponCode couponCode = order.getCouponCode();
		if (couponCode != null) {
			couponCode.setIsUsed(false);
			couponCode.setUsedDate(null);
			couponCodeDao.merge(couponCode);

			order.setCouponCode(null);
			orderDao.merge(order);
		}

		if (order.getIsAllocatedStock()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								- (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(false);
		}

		order.setOrderStatus(OrderStatus.cancelled);
		order.setExpire(null);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.cancel);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void payment(Order order, Payment payment, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(payment);

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		payment.setOrder(order);
		paymentDao.merge(payment);
		if (payment.getMethod() == Payment.Method.deposit) {
			Member member = order.getMember();
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setBalance(member.getBalance().subtract(payment.getAmount()));
			memberDao.merge(member);

			Deposit deposit = new Deposit();
			deposit.setType(operator != null ? Deposit.Type.adminPayment : Deposit.Type.memberPayment);
			deposit.setCredit(new BigDecimal(0));
			deposit.setDebit(payment.getAmount());
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMember(member);
			deposit.setOrder(order);
			depositDao.persist(deposit);
		}

		Setting setting = SettingUtils.get();
		if (!order.getIsAllocatedStock() && setting.getStockAllocationTime() == StockAllocationTime.payment) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								+ (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(true);
		}

		order.setAmountPaid(order.getAmountPaid().add(payment.getAmount()));
		order.setFee(payment.getFee());
		order.setExpire(null);
		if (order.getAmountPaid().compareTo(order.getAmount()) >= 0) {
			order.setOrderStatus(OrderStatus.paymented);
			order.setPaymentStatus(PaymentStatus.paid);
		} else if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setOrderStatus(OrderStatus.paymented);
			order.setPaymentStatus(PaymentStatus.partialPayment);
		}
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.payment);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void refunds(Order order, Refunds refunds, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(refunds);

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		refunds.setOrder(order);
		refundsDao.persist(refunds);
		if (refunds.getMethod() == Refunds.Method.deposit) {
			Member member = order.getMember();
			memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
			member.setBalance(member.getBalance().add(refunds.getAmount()));
			memberDao.merge(member);

			Deposit deposit = new Deposit();
			deposit.setType(Deposit.Type.adminRefunds);
			deposit.setCredit(refunds.getAmount());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getBalance());
			deposit.setOperator(operator != null ? operator.getUsername() : null);
			deposit.setMember(member);
			deposit.setOrder(order);
			depositDao.persist(deposit);
		}

		order.setAmountPaid(order.getAmountPaid().subtract(refunds.getAmount()));
		order.setExpire(null);
		if (order.getAmountPaid().compareTo(new BigDecimal(0)) == 0) {
			order.setPaymentStatus(PaymentStatus.refunded);
		} else if (order.getAmountPaid().compareTo(new BigDecimal(0)) > 0) {
			order.setPaymentStatus(PaymentStatus.partialRefunds);
		}
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.refunds);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	@Transactional(readOnly = true)
	public void shipping(Order order, Shipping shipping, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(shipping);
		Assert.notEmpty(shipping.getShippingItems());

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		Setting setting = SettingUtils.get();
		if (!order.getIsAllocatedStock() && setting.getStockAllocationTime() == StockAllocationTime.ship) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								+ (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
			order.setIsAllocatedStock(true);
		}

		shipping.setOrder(order);
		shippingDao.persist(shipping);
		for (ShippingItem shippingItem : shipping.getShippingItems()) {
			OrderItem orderItem = order.getOrderItem(shippingItem.getSn());
			if (orderItem != null) {
				Product product = orderItem.getProduct();
				productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
				if (product != null) {
					if (product.getStock() != null) {
						product.setStock(product.getStock() - shippingItem.getQuantity());
						if (order.getIsAllocatedStock()) {
							product.setAllocatedStock(product.getAllocatedStock() - shippingItem.getQuantity());
						}
					}
					productDao.merge(product);
					orderDao.flush();
					staticService.build(product);
				}
				orderItemDao.lock(orderItem, LockModeType.PESSIMISTIC_WRITE);
				orderItem.setShippedQuantity(orderItem.getShippedQuantity() + shippingItem.getQuantity());
			}
		}
		// if (order.getShippedQuantity() >= order.getQuantity()) {
		order.setShippingStatus(ShippingStatus.shipped);
		order.setOrderStatus(OrderStatus.shipped);
		order.setIsAllocatedStock(false);
		// order.setLockExpire(new Date());
		logger.info("[shipping-->>订单编号]:" + order.getSn());
		logger.info("[shipping-->>getLockExpire()]:" + order.getLockExpire());
		// } else if (order.getShippedQuantity() > 0) {
		// order.setShippingStatus(ShippingStatus.partialShipment);
		// }
		order.setExpire(null);
		orderDao.merge(order);
		orderDao.flush();

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.shipping);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	public void returns(Order order, Returns returns, Admin operator) {
		Assert.notNull(order);
		Assert.notNull(returns);
		Assert.notEmpty(returns.getReturnsItems());

		orderDao.lock(order, LockModeType.PESSIMISTIC_WRITE);

		returns.setOrder(order);
		returnsDao.persist(returns);
		for (ReturnsItem returnsItem : returns.getReturnsItems()) {
			OrderItem orderItem = order.getOrderItem(returnsItem.getSn());
			if (orderItem != null) {
				orderItemDao.lock(orderItem, LockModeType.PESSIMISTIC_WRITE);
				orderItem.setReturnQuantity(orderItem.getReturnQuantity() + returnsItem.getQuantity());
			}
		}
		if (order.getReturnQuantity() >= order.getShippedQuantity()) {
			order.setShippingStatus(ShippingStatus.returned);
		} else if (order.getReturnQuantity() > 0) {
			order.setShippingStatus(ShippingStatus.partialReturns);
		}
		order.setExpire(null);
		orderDao.merge(order);

		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.returns);
		orderLog.setOperator(operator != null ? operator.getUsername() : null);
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	@Override
	public void delete(Order order) {
		if (order.getIsAllocatedStock()) {
			for (OrderItem orderItem : order.getOrderItems()) {
				if (orderItem != null) {
					Product product = orderItem.getProduct();
					productDao.lock(product, LockModeType.PESSIMISTIC_WRITE);
					if (product != null && product.getStock() != null) {
						product.setAllocatedStock(product.getAllocatedStock()
								- (orderItem.getQuantity() - orderItem.getShippedQuantity()));
						productDao.merge(product);
						orderDao.flush();
						staticService.build(product);
					}
				}
			}
		}
		super.delete(order);
	}

	/**
	 * 批量取消待支付订单
	 * 
	 * @param pageNumber
	 * @param pageSize
	 */
	@Override
	public void cancelOrder(int pageNumber, int pageSize) {
		StringBuffer orderCancelStr = new StringBuffer();
		for (int i = pageNumber;; i++) {
			Page<Order> page = findPage(OrderStatus.unpayment, null, null, new Pageable(i, pageSize));
			List<Order> orderList = page.getContent();
			if (null == orderList) {
				return;
			}
			for (Order order : orderList) {
				if (null != order.getExpire() && !new Date().before(order.getExpire())) {
					order.setMemo("未支付，已取消");
					order.setOrderStatus(OrderStatus.cancelled);
					orderCancelStr.append(order.getSn()).append(",");
					// orderDao.merge(order);
				}
			}

			orderDao.flush();

			if (orderList.size() < pageSize) {
				logger.info("[取消订单编号]:" + orderCancelStr.toString());
				return;
			}
		}
	}

	
	/**
	 * 批量更新处理订单商户号
	 */
	@Override
	public void queryAcceptBiz(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		StringBuffer orderStatusStr = new StringBuffer();
		for (int i = pageNumber;; i++) {
			Page<Order> page = findPageMeOrderId( null, null, new Pageable(i, pageSize));
			List<Order> orderList = page.getContent();
			if (null == orderList) {
				return;
			}
			if (null != orderList && orderList.size() > 0) {
				for (Order order : orderList) {
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, Object> resultMap = null;
					params.put("acceptBizNo", acceptBizNo);
					params.put("rptUuid", order.getRptUuid());
					// 必输
					params.put("acceptBizJrnNo", System.currentTimeMillis());
					// 必输
					params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
					// 必输
					params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
					// 必输
					params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
					params.put("txCode", "gatewayQueryPayeeOrderProcess");
					params.put("version", "1.0.1");
					params = SignatureUtils.paraFilter(params);
					String signStr = SignatureUtils.createLinkString(params);
					String sign = MD5.sign(signStr, managerMd5Key, "UTF-8");
					params.put("sign", sign);
					String result;
					try {
						logger.info("[queryOrderUrl]:" + queryOrderUrl);
						result = HttpClientUtil.postJson(queryOrderUrl, params, "UTF-8");
						resultMap = JsonUtils.toObject(result, Map.class);
						if ("00".equals(resultMap.get("txStatus")) ) { // 查询成功，获取商户订单号,更新到数据库中
							//获取商户订单号
							order.setPayMerOrderNo(resultMap.get("merOrderId").toString());
							orderDao.merge(order);
						}
					} catch (JsonProcessingException e) {
					} catch (ServiceException e) {
					}
					
				}

			}
			// orderDao.flush(); //状态更新不在此位置下执行
			if (orderList.size() < pageSize) {
				logger.info("[批量更新处理订单商户号]:" + orderStatusStr.toString());
				return;
			}
		}
	}
	
	
	 


	/**
	 * 批量更新待支付状态订单(订单已支付成功)
	 */
	@Override
	public void queryOrderStatus(int pageNumber, int pageSize) {
		StringBuffer orderStatusStr = new StringBuffer();
		for (int i = pageNumber;; i++) {
			Page<Order> page = findPage(OrderStatus.unpayment, null, null, new Pageable(i, pageSize));
			List<Order> orderList = page.getContent();
			if (null == orderList) {
				return;
			}
			if (null != orderList && orderList.size() > 0) {
				for (Order order : orderList) {

					if (order.getRptUuid() != null && isPayOrder(order)) { // 过滤订单中无收款订单参考号的，全萌值支付的订单
						order.setOrderStatus(OrderStatus.paymented);
						orderStatusStr.append(order.getSn()).append(",");
						orderDao.merge(order);

					}
				}

			}
			// orderDao.flush(); //状态更新不在此位置下执行
			if (orderList.size() < pageSize) {
				logger.info("[更新待支付状态订单编号]:" + orderStatusStr.toString());
				return;
			}
		}

	}

	/**
	 * 查询订单支付状态
	 * 
	 * @param order
	 *            订单
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean isPayOrder(Order order) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		params.put("acceptBizNo", acceptBizNo);
		params.put("rptUuid", order.getRptUuid());
		// 必输
		params.put("acceptBizJrnNo", System.currentTimeMillis());
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		params.put("txCode", "gatewayQueryPayeeOrderProcess");
		params.put("version", "1.0.1");
		params = SignatureUtils.paraFilter(params);
		String signStr = SignatureUtils.createLinkString(params);
		String sign = MD5.sign(signStr, managerMd5Key, "UTF-8");
		params.put("sign", sign);
		String result;
		try {
			logger.info("[queryOrderUrl]:" + queryOrderUrl);
			result = HttpClientUtil.postJson(queryOrderUrl, params, "UTF-8");
			resultMap = JsonUtils.toObject(result, Map.class);
			if ("00".equals(resultMap.get("txStatus")) && "01".equals(resultMap.get("payStatus"))) { // 查询成功，并且支付管家的支付状态为成功的
				return true;
			}
		} catch (JsonProcessingException e) {
			return false;
		} catch (ServiceException e) {
			return false;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public List<Order> findList(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
			Boolean hasExpired, Pageable pageable, Date bDate, Date eDate) {
		return orderDao.findList(orderStatus, paymentStatus, shippingStatus, hasExpired, pageable, bDate, eDate);
	}

	@Override
	public List<Object[]> findValidCouponCodes(Long[] productIds, Long[] productcatIds, Long[] parentcatergorytIds,
			Member member) {
		// TODO Auto-generated method stub

		List<Coupon> CouponAll = new ArrayList<Coupon>();

		CouponAll.addAll(couponDao.findCoupunPrdList(productIds));

		CouponAll.addAll(couponDao.findCoupunCateList(productcatIds, parentcatergorytIds));

		List<Object[]> CouponCodeAll = new ArrayList<Object[]>();

		for (Coupon cp : CouponAll) {

			CouponCodeAll.addAll(couponCodeService.FindValidCouponCode(cp, member, true, false, false));
		}
		// 再关联全场通用的优惠券
		CouponCodeAll.addAll(couponCodeService.FindValidCouponCode(null, member, true, false, false));

		return CouponCodeAll;

	}

	@Override
	public void delete(Order order, String operator) {
		order.setOrderStatus(OrderStatus.deleted);
		orderDao.merge(order);
		OrderLog orderLog = new OrderLog();
		orderLog.setType(Type.deleted);
		orderLog.setOperator(operator);
		orderLog.setContent(operator + "删除订单");
		orderLog.setOrder(order);
		orderLogDao.persist(orderLog);
	}

	@Override
	@Transactional
	public int updateBySn(String sn, String name) {
		return orderDao.updateBySn(sn, name);
	}

	
}