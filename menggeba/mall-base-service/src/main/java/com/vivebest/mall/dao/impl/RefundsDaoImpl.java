/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RefundsDao;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Refunds.Status;

/**
 * Dao - 退款单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("refundsDaoImpl")
public class RefundsDaoImpl extends BaseDaoImpl<Refunds, Long> implements RefundsDao {

	public Refunds findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select refunds from Refunds refunds where lower(refunds.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Refunds.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public Page<Refunds> findPage(Status status, Pageable pageable){
		if (status == null) {
			return new Page<Refunds>(Collections.<Refunds> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Refunds> criteriaQuery = criteriaBuilder.createQuery(Refunds.class);
		Root<Refunds> root = criteriaQuery.from(Refunds.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.notEqual(root.get("status"), status));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Refunds> findByOrderId(Long id) {
		// TODO Auto-generated method stub
		String jpql = "select refunds from Refunds refunds where lower(refunds.order.id) = lower(:id)";
		List<Refunds> refunds=entityManager.createQuery(jpql,Refunds.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", id).getResultList();
		if(null != refunds && refunds.isEmpty()){
			return null;
		}
		else{
			return refunds;
		}
	}

	@Override
	public Long unRefundsCount(Refunds refunds) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Refunds> criteriaQuery = criteriaBuilder.createQuery(Refunds.class);
		Root<Refunds> root = criteriaQuery.from(Refunds.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (refunds != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("refunds"), refunds));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), 3));
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}
	
	



}