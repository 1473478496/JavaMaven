/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.CouponCodeDao;
import com.vivebest.mall.entity.Coupon;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Member;

/**
 * Dao - 优惠码
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("couponCodeDaoImpl")
public class CouponCodeDaoImpl extends BaseDaoImpl<CouponCode, Long> implements CouponCodeDao {

	public boolean codeExists(String code) {
		if (code == null) {
			return false;
		}
		String jpql = "select count(*) from CouponCode couponCode where lower(couponCode.code) = lower(:code)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("code", code).getSingleResult();
		return count > 0;
	}

	public CouponCode findByCode(String code) {
		if (code == null) {
			return null;
		}
		try {
			String jpql = "select couponCode from CouponCode couponCode where lower(couponCode.code) = lower(:code)";
			return entityManager.createQuery(jpql, CouponCode.class).setFlushMode(FlushModeType.COMMIT).setParameter("code", code).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public CouponCode build(Coupon coupon, Member member) {
		Assert.notNull(coupon);
		CouponCode couponCode = new CouponCode();
		String uuid = UUID.randomUUID().toString().toUpperCase(); //券码12位
		couponCode.setCode(uuid.substring(0, 3) + uuid.substring(9, 12) + uuid.substring(14, 17) + uuid.substring(19, 22));
		couponCode.setIsUsed(false);
		couponCode.setCoupon(coupon);
		couponCode.setMember(member);
		couponCode.setReceiveDate(new Date());
		super.persist(couponCode);
		return couponCode;
	}
	
 

	public List<CouponCode> build(Coupon coupon, Member member, Integer count) {
		Assert.notNull(coupon);
		Assert.notNull(count);
		List<CouponCode> couponCodes = new ArrayList<CouponCode>();
		for (int i = 0; i < count; i++) {
			CouponCode couponCode = build(coupon, member);
			couponCodes.add(couponCode);
			if (i % 20 == 0) {
				super.flush();
				super.clear();
			}
		}
		return couponCodes;
	}

	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		criteriaQuery.select(root);
		if (member != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		}
		return super.findPage(criteriaQuery, pageable);
	}
	
	
	public Page<CouponCode> findPage(Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Path<Coupon> couponPath = root.get("coupon");
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
			
		}
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(couponPath.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(couponPath.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("endDate").isNotNull(), criteriaBuilder.lessThan(couponPath.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(couponPath.<Date> get("endDate"), new Date())));
			}
		}
		if (isUsed != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUsed"), isUsed));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	

	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CouponCode> criteriaQuery = criteriaBuilder.createQuery(CouponCode.class);
		Root<CouponCode> root = criteriaQuery.from(CouponCode.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Path<Coupon> couponPath = root.get("coupon");
		if (coupon != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponPath, coupon));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(couponPath.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(couponPath.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("endDate").isNotNull(), criteriaBuilder.lessThan(couponPath.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(couponPath.<Date> get("endDate"), new Date())));
			}
		}
		if (isUsed != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isUsed"), isUsed));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}
	
	
 
	
	public List<Object[]> findValidCouponCodeList(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<CouponCode> couponCode = criteriaQuery.from(CouponCode.class);
		Join<CouponCode, Coupon> couponPath = couponCode.join("coupon");
		
		criteriaQuery.multiselect(couponCode.<Integer>get("id"), couponCode.get("code"), couponPath.get("name"),couponPath.<BigDecimal>get("minimumPrice"));
		Predicate restrictions = criteriaBuilder.conjunction();
		
		
		
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponCode.get("member"), member));
		}
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("beginDate").isNull(), criteriaBuilder.lessThanOrEqualTo(couponPath.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("beginDate").isNotNull(), criteriaBuilder.greaterThan(couponPath.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, couponPath.get("endDate").isNotNull(), criteriaBuilder.lessThan(couponPath.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(couponPath.get("endDate").isNull(), criteriaBuilder.greaterThanOrEqualTo(couponPath.<Date> get("endDate"), new Date())));
			}
		}
		if (isUsed != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponCode.<Boolean> get("isUsed"), isUsed));
		}
		
		if (coupon != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponPath, coupon));
			
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponPath.<Boolean> get("isFullfield"), false));
		}else
		{
			//复合全场通用的条件
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(couponPath.<Boolean> get("isFullfield"), true));
		}	
		criteriaQuery.distinct(true);
		criteriaQuery.where(restrictions);
		
		
		criteriaQuery.groupBy(couponPath.<Integer> get("id"));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
	
		return query.getResultList();
	}

	@Override
	public Boolean isReceive(Coupon coupon, Member member) {
		if (member == null) {
			return false;
		}
		String jpql = "select count(*) from CouponCode couponCode where couponCode.receiveDate>= :newDate and couponCode.member = :member and couponCode.coupon = :coupon";
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		Long count;
		try {
			count = entityManager.createQuery(jpql, Long.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("newDate", matter.parse(matter.format(dt)))
					.setParameter("member", member)
					.setParameter("coupon", coupon).getSingleResult();
			return count > 0;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
 
	
	
	
	
	
	
	

}