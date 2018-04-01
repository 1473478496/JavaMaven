/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Order.Direction;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.SmsDao;
import com.vivebest.mall.entity.Sms;

/**
 * Dao - 短信
 * 
 * @author liu.jch
 * @date 2015/07/22
 */
@Repository("smsDaoImpl")
public class SmsDaoImpl extends BaseDaoImpl<Sms, Long> implements SmsDao {

	@Override
	public List<Sms> findByNumAndStatus(String number, int status) {
		if (number == null) {
			return Collections.<Sms> emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sms> criteriaQuery = criteriaBuilder.createQuery(Sms.class);
		Root<Sms> root = criteriaQuery.from(Sms.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (number != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("number"), number));
		}
		if (status == 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order("sendTime", Direction.desc));
		return super.findList(criteriaQuery, null, 1, null, orders);
	}

	@Override
	public List<Sms> findLikeContent(String mobile, String code) {
		if(mobile==null || code==null){
			return Collections.<Sms> emptyList();
		}
		String jpql = "select sms from Sms sms where lower(sms.number)=lower(:mobile) and sms.content like :code";
		try {
			return entityManager.createQuery(jpql, Sms.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).setParameter("code", "%"+code+"%").getResultList();
		} catch (NoResultException e) {
			return Collections.<Sms> emptyList();
		}
	}

}