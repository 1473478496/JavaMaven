/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.constants.CreditEventEnum;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.OrderDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.service.PromotionProductsService;
import com.vivebest.mall.service.impl.CreditServiceImpl;

/**
 * Dao - 订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long>implements OrderDao {

	@Value("${pay.acceptBizNo}")
	private String acceptBizNo;

	@Resource(name = "creditServiceImpl")
	private CreditServiceImpl creditService;

	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductsService;

	private static Logger logger = Logger.getLogger(OrderDaoImpl.class);

	public Order findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select orders from Order orders where lower(orders.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Order.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	@Override
	public int updateBySn(String sn,String paymentMethodName) {
		if (sn == null) {
			return -1;
		}
//		String jpql = "update Order orders set orders.paymentMethodName='11111111' where orders.sn=:sn";
//		System.out.println(jpql);
//		return entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).executeUpdate();
		
		
		String jpql = "update Order as  orders set orders.paymentMethodName='"+paymentMethodName+"' where orders.sn ='"+sn+"'";
//		String jpql = "update Order as  orders set orders.paymentMethodName='微信扫码支付' where orders.sn ='m2016072284042'";
		System.out.println(jpql);
		Query query = entityManager.createQuery(jpql);
	    System.out.println(sn +"---------"+ paymentMethodName);
//	    query.setParameter("paymentMethodName", paymentMethodName);
//		query.setParameter("sn", sn);
		return query.executeUpdate();
		
	}
	
	
	
	
	
	

	public List<Order> findList(Member member, Integer count, List<Filter> filters,
			List<com.vivebest.mall.core.common.Order> orders) {
		if (member == null) {
			return Collections.<Order> emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<Order> findPage(OrderStatus orderStatus, String sn, Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (sn != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sn"), sn));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Order> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Order>(Collections.<Order> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Order> findPage(Member member, Date beginDate, Date endDate, Pageable pageable) {
		if (member == null) {
			return new Page<Order>(Collections.<Order> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Order> findPage(Date beginDate, Date endDate, OrderStatus orderStatus, PaymentStatus paymentStatus,
			ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("createDate").isNull(),
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate)));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("createDate").isNull(),
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate)));
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (paymentStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
		}
		if (shippingStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("shippingStatus"), shippingStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(),
						criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(),
						criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public List<Order> findList(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
			Boolean hasExpired, Pageable pageable,Date bDate, Date eDate) {
		if (pageable == null) {
			pageable = new Pageable();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (paymentStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
		}
		if (shippingStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("shippingStatus"), shippingStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(),
						criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(),
						criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		if (bDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("createDate").isNull(),
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), bDate)));
		}
		if (eDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("createDate").isNull(),
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), eDate)));
		}
		if (StringUtils.isNotEmpty(pageable.getSearchProperty()) && StringUtils.isNotEmpty(pageable.getSearchValue())) {
			String searchProperty = pageable.getSearchProperty();
			if (searchProperty.contains("_")) {
				String[] searchPropertys = searchProperty.split("_");
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.like(root.get(searchPropertys[0]).<String> get(searchPropertys[1]),
								"%" + pageable.getSearchValue() + "%"));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
						.like(root.<String> get(pageable.getSearchProperty()), "%" + pageable.getSearchValue() + "%"));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	public Long count(Member member, OrderStatus orderStatus, PaymentStatus paymentStatus,
			ShippingStatus shippingStatus, Boolean hasExpired) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
		}
		if (paymentStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
		}
		if (shippingStatus != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("shippingStatus"), shippingStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(),
						criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(),
						criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public Long waitingPaymentCount(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		// restrictions = criteriaBuilder.and(restrictions,
		// criteriaBuilder.notEqual(root.get("orderStatus"),
		// OrderStatus.completed),
		// criteriaBuilder.notEqual(root.get("orderStatus"),
		// OrderStatus.cancelled));
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.or(criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.unpayment),
						criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.payment)));
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.or(criteriaBuilder.equal(root.get("paymentStatus"), PaymentStatus.unpaid),
						criteriaBuilder.equal(root.get("paymentStatus"), PaymentStatus.partialPayment)));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(),
				criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public Long waitingShippingCount(Member member) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.notEqual(root.get("orderStatus"), OrderStatus.completed),
				criteriaBuilder.notEqual(root.get("orderStatus"), OrderStatus.cancelled),
				criteriaBuilder.equal(root.get("paymentStatus"), PaymentStatus.paid),
				criteriaBuilder.equal(root.get("shippingStatus"), ShippingStatus.unshipped));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(),
				criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public BigDecimal getSalesAmount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal> get("amountPaid")));
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.completed));
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	public Integer getSalesVolume(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(criteriaBuilder.sum(root.join("orderItems").<Integer> get("shippedQuantity")));
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("orderStatus"), OrderStatus.completed));
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getSingleResult();
	}

	public void releaseStock() {
		String jpql = "select orders from Order orders where orders.isAllocatedStock = :isAllocatedStock  and  orders.orderStatus='5' and orders.expire is not null and orders.expire <= :now";
		List<Order> orders = entityManager.createQuery(jpql, Order.class).setParameter("isAllocatedStock", true)
				.setParameter("now", new Date()).getResultList();
		if (orders != null) {
			for (Order order : orders) {
				if (order != null && order.getOrderItems() != null) {
					// 返回赠送的萌值
					Long PaidPoint = 0l;
					for (OrderItem orderItem : order.getOrderItems()) {

						// 1 释放普通商品的库存
						if (orderItem != null) {
							PaidPoint += orderItem.getPayPricePoint();
							Product product = orderItem.getProduct();
							if (product != null) {
								entityManager.lock(product, LockModeType.PESSIMISTIC_WRITE);
								product.setAllocatedStock(product.getAllocatedStock()
										- (orderItem.getQuantity() - orderItem.getShippedQuantity()));
							}

							// 2 释放促销商品库存
							if (orderItem.getPromotionProducts() != null) {
								PromotionProducts promotionProducts = orderItem.getPromotionProducts();
								if (orderItem.getPrice() != null && orderItem.getPrice()
										.equals(orderItem.getPromotionProducts().getPromotionPrice())) {
									if (orderItem.getPricePoint() != null && orderItem.getPricePoint()
											.equals(orderItem.getPromotionProducts().getPromotionPricePoint())) {
										try {
											if(promotionProducts.getSaleQuantity() != null){
												promotionProducts.setSaleQuantity(promotionProducts.getSaleQuantity() - orderItem.getQuantity());
											}
											promotionProductsService.update(promotionProducts);
										} catch (Exception e) {
											logger.error("取消促销订单失败", e);

										}
									}

								}
							}

						}

					}
					order.setIsAllocatedStock(false);
					order.setMemo("未支付，已取消");
					// 1 过期后，释放订单为取消
					order.setOrderStatus(OrderStatus.cancelled);
					// 2 退还已经消费的萌值
					creditService.engineMengTopUp(order.getMember().getSn(), PaidPoint, "跑批取消订单返回消费的积分", CreditEventEnum.CancelOrder);
				}
			}
		}
	}

	@Override
	public List findPage(Order order, String ssn ,String psn,String pname,String mname,String bDate,String eDate,Integer orderStatue,Integer shippingStatue,Integer payStatue,Boolean hasExpired) {
		String sql = 
			" SELECT o.id FROM gbm_order o WHERE o.id IN (SELECT i.orders FROM gbm_order_item i "+
			" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE p.name like ?1 "+
			" AND p.sn = ?2)) "+
			" AND o.id IN (SELECT s.orders FROM gbm_shipping s WHERE s.sn = ?3) "+
			" AND o.member IN (SELECT m.id FROM gbm_member m WHERE m.NAME = ?4) "+
			" AND o.create_date >= ?5 "+
			" AND o.create_date <= ?6 "+
			" AND o.order_status = ?7 "+
			" AND o.payment_status = ?8 "+
			" AND o.shipping_status = ?9 "+
			" AND o.consignee = ?10 "+
			" AND o.phone = ?12 ";
		
		StringBuffer sb = new StringBuffer();
		StringBuffer countSql = new StringBuffer();
		StringBuffer selectSql = new StringBuffer(); 
		Map<Integer,Object> map = new HashMap<Integer, Object>();
		countSql.append(" SELECT count(id) FROM gbm_order o WHERE 1=1 ");
		selectSql.append(" SELECT o.id FROM gbm_order o WHERE 1=1 "); 
		if(pname!=null && !"".equals(pname) && psn!=null && !"".equals(psn)){
			sb.append(" AND o.id IN (SELECT i.orders FROM gbm_order_item i ");
			sb.append(" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE 1=1 AND p.name like ?1  AND p.sn = ?2)) ");
			map.put(1, pname);
			map.put(2, psn);
		}else{
			if(psn != null && !"".equals(psn)){
				sb.append(" AND o.id IN (SELECT i.orders FROM gbm_order_item i ");
				sb.append(" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE 1=1 AND  p.sn = ?2)) ");
				map.put(2, psn);
			}
			if(pname != null && !"".equals(pname)){
				sb.append(" AND o.id IN (SELECT i.orders FROM gbm_order_item i ");
				sb.append(" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE 1=1 AND p.name like ?1)) ");
				map.put(1, pname);
			}
		}
		
		if(ssn!=null && !"".equals(ssn)){
			sb.append(" AND o.id IN (SELECT s.orders FROM gbm_shipping s WHERE s.tracking_no = ?3) ");
			map.put(3, ssn);
		}
		
		if(mname!=null && !"".equals(mname)){
			sb.append(" AND o.member IN (SELECT m.id FROM gbm_member m WHERE m.username = ?4) ");
			map.put(4, mname);
		}
		
		if(bDate!=null && !"".equals(bDate)){
			sb.append(" AND o.create_date >= ?5 ");
			map.put(5, bDate);
		}
		
		if(eDate!=null && !"".equals(eDate)){
			sb.append(" AND o.create_date <= ?6 ");
			map.put(6, eDate);
		}
		if(orderStatue != null){
			sb.append(" AND o.order_status = ?7 ");
			map.put(7, orderStatue);
		}
		if(payStatue != null){
			sb.append(" AND o.payment_status = ?8 ");
			map.put(8, payStatue);
		}
		if(shippingStatue != null){
			sb.append(" AND o.shipping_status = ?9 ");
			map.put(9, shippingStatue);
		}
		
		if(order != null){
			if(order.getConsignee() != null && !"".equals(order.getConsignee())){
				sb.append(" AND o.consignee = ?10 ");
				map.put(10, order.getConsignee());
			}
			if(order.getPhone() != null && !"".equals(order.getPhone())){
				sb.append(" AND o.phone = ?11 ");
				map.put(11, order.getPhone());
			}
			
			if(hasExpired!=null){
				if(hasExpired){
					sb.append(" AND o.expire <= now()");
				}else{
					sb.append(" AND o.expire >= now()");
				}
			}
		}
		
		
		//System.out.println("----->"+sb);
		/*
		Query cQuery = entityManager.createNativeQuery(countSql.append(sb).toString());
		for (Integer key : map.keySet()) {
			cQuery.setParameter(key, map.get(key));
		}
		
		long total = Long.parseLong(cQuery.getResultList().get(0).toString());
		*/
		Query query = entityManager.createNativeQuery(selectSql.append(sb).toString());
		for (Integer key : map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		
		
		
		//query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		//query.setMaxResults(pageable.getPageSize());
		
		/*query.setParameter(1, pname);
		query.setParameter(2, psn);
		query.setParameter(3, ssn);
		query.setParameter(4, mname);
		query.setParameter(5, bDate);
		query.setParameter(6, eDate);
		query.setParameter(7, 0);
		query.setParameter(8, 2);
		query.setParameter(9, 2);
		query.setParameter(10, order.getConsignee());
		query.setParameter(11, order.getPhone());
		if(pageable == null){
			pageable = new Pageable();
		}
		*/
		List result = query.getResultList();	
		return result;
		//return new Page<Order>(result, total, pageable);
	}

	@Override
	public Page<Order> findById(List list,Pageable pageable) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		if(list.size()>0){
			Expression<String> exp = root.get("id");
			Predicate predicate = exp.in(list);
			criteriaQuery.where(predicate);
			return super.findPage(criteriaQuery, pageable);
		}else{

			return new Page<Order>();
		}
	}

	@Override
	public Page<Order> findPageMeOrderId(String sn, String member,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
			 
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.isNull(root.get("payMerOrderNo")));
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (sn != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sn"), sn));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
}