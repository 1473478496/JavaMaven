/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.OrderItemDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * Dao - 订单项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

	@Override
	public List<OrderItem> checkProNum(Member member, PromotionProducts promPro) {
		if(member == null || promPro == null){
			return Collections.emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrderItem> criteriaQuery = criteriaBuilder.createQuery(OrderItem.class);
		
		Root<OrderItem> root = criteriaQuery.from(OrderItem.class);
		criteriaQuery.select(root);
		
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if(member != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("order").get("member"),member));
		}
		
		if(promPro != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotionProducts"),promPro));
		}
		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.and(criteriaBuilder.notEqual(root.get("order").get("orderStatus"),OrderStatus.cancelled),
				criteriaBuilder.notEqual(root.get("order").get("orderStatus"),OrderStatus.completed),
				criteriaBuilder.notEqual(root.get("order").get("orderStatus"),OrderStatus.deleted),
				criteriaBuilder.notEqual(root.get("order").get("orderStatus"),OrderStatus.closed) ));
		
		criteriaQuery.where(restrictions);
		TypedQuery<OrderItem> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

}