/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ReturnsDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.Returns.Status;

/**
 * Dao - 退货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("returnsDaoImpl")
public class ReturnsDaoImpl extends BaseDaoImpl<Returns, Long> implements ReturnsDao {

	
	public Page<Returns> findPage(Status status, ReturnType returnType, com.vivebest.mall.entity.Refunds.Status refundStatus, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Returns> criteriaQuery = criteriaBuilder.createQuery(Returns.class);
		Root<Returns> root = criteriaQuery.from(Returns.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (returnType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("returnType"), returnType));
		}
		if(refundStatus != null){
			Subquery<Object> subquery=criteriaQuery.subquery(Object.class);
			Root<Refunds> subRoot = subquery.from(Refunds.class);
			subquery.select(subRoot.get("returns"));
			subquery.where(criteriaBuilder.and(criteriaBuilder.equal(subRoot.get("status"), refundStatus)));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.in(root.get("id")).value(subquery));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	public Page<Returns> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Returns>(Collections.<Returns> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Returns> criteriaQuery = criteriaBuilder.createQuery(Returns.class);
		Root<Returns> root = criteriaQuery.from(Returns.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}
	

	public Page<Returns> findPage(Status status, Pageable pageable) {
		if (status == null) {
			return new Page<Returns>(Collections.<Returns> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Returns> criteriaQuery = criteriaBuilder.createQuery(Returns.class);
		Root<Returns> root = criteriaQuery.from(Returns.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("status"), status));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Returns> findByOrderItem(Long orderItemId) {
		String jpql = "select returns from Returns returns where lower(returns.orderItem.id) = lower(:id)";
		List<Returns> returns=entityManager.createQuery(jpql,Returns.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", orderItemId).getResultList();
		if(null != returns && returns.isEmpty()){
			return null;
		}
		else{
			return returns;
		}
	}
	public Long unReturnsCount(){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Returns> criteriaQuery = criteriaBuilder.createQuery(Returns.class);
		Root<Returns> root = criteriaQuery.from(Returns.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Subquery<Object> subquery=criteriaQuery.subquery(Object.class);
			Root<Refunds> subRoot = subquery.from(Refunds.class);
			subquery.select(subRoot.get("returns"));
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(subRoot.get("status"), 3)));
			restrictions = criteriaBuilder.in(root.get("id")).value(subquery);
			restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("status"), 0),criteriaBuilder.equal(root.get("status"), 3)));
			
			criteriaQuery.where(restrictions);
			return super.count(criteriaQuery, null);
	}

	@Override
	public List<Returns> findByOrder(Long orderId) {
		String jpql = "select returns from Returns returns where lower(returns.order.id) = lower(:id)";
		List<Returns> returns = null;
		try {
			returns=entityManager.createQuery(jpql,Returns.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", orderId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returns;
	}
	

}