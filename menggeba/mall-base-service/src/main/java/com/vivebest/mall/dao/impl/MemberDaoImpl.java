/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.MemberDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Product;

/**
 * Dao - 会员
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("memberDaoImpl")
public class MemberDaoImpl extends BaseDaoImpl<Member, Long>implements MemberDao {

	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("username", username).getSingleResult();
		return count > 0;
	}

	public boolean emailExists(String email) {
		if (email == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.email) = lower(:email)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("email", email).getSingleResult();
		return count > 0;
	}

	public boolean mobileExists(String mobile) {
		if (mobile == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.mobile) = lower(:mobile)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("mobile", mobile).getSingleResult();
		return count > 0;
	}

	public Member findByUsername(String username) {
		if (username == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.username) = lower(:username)";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Member> findListByEmail(String email) {
		if (email == null) {
			return Collections.<Member> emptyList();
		}
		String jpql = "select members from Member members where lower(members.email) = lower(:email)";
		return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("email", email).getResultList();
	}

	public List<Member> findListByMobile(String mobile) {
		if (mobile == null) {
			return Collections.<Member> emptyList();
		}
		String jpql = "select members from Member members where lower(members.mobile) = lower(:mobile)";
		return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("mobile", mobile).getResultList();
	}

	public List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Member> member = criteriaQuery.from(Member.class);
		Join<Product, Order> orders = member.join("orders");
		criteriaQuery.multiselect(member.get("id"), member.get("username"), member.get("email"), member.get("point"),
				member.get("amount"), member.get("balance"),
				criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.greaterThanOrEqualTo(orders.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.lessThanOrEqualTo(orders.<Date> get("createDate"), endDate));
		}
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(orders.get("orderStatus"), OrderStatus.completed),
				criteriaBuilder.equal(orders.get("paymentStatus"), PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(member.get("id"), member.get("username"), member.get("email"), member.get("point"),
				member.get("amount"), member.get("balance"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(orders.<BigDecimal> get("amountPaid"))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public boolean wxOpenIdExists(String wxOpenId) {
		if (wxOpenId == null) {
			return false;
		}
		String jpql = "select count(*) from Member members where lower(members.wxOpenId) = lower(:wxOpenId)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("wxOpenId", wxOpenId).getSingleResult();
		return count > 0;
	}

	public Member findByWxOpenId(String wxOpenId) {
		if (wxOpenId == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.wxOpenId) = lower(:wxOpenId) and is_enabled = 1";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("wxOpenId", wxOpenId).setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Member findUserBySn(String userSn) {
		// TODO Auto-generated method stub
		if (userSn == null) {
			return null;
		}
		try {
			String jpql = "select members from Member members where lower(members.sn) = lower(:userSn)";
			return entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("userSn", userSn).setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Member> findBySearchProperty(Pageable pageable) {
		if (pageable == null) {
			pageable = new Pageable();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
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
	
	@Override
	public Member findByMobile(String mobile) {
		if (mobile == null) {
			return null;
		}
		try{
			String jpql = "select members from Member members where lower(members.mobile) = lower(:mobile)";
			Member member = entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("mobile", mobile).getSingleResult();
			return member;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Member findByToken(String token) {
		if (token == null) {
			return null;
		}
		try{
			String jpql = "select members from Member members where lower(members.token) = lower(:token)";
			Member member = entityManager.createQuery(jpql, Member.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("token", token).getSingleResult();
			return member;
		} catch (NoResultException e) {
			return null;
		}
	}

}