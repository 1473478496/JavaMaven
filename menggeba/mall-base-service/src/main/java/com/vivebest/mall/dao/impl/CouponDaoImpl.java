/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.CouponDao;
import com.vivebest.mall.entity.Coupon;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.ProductCategory;

/**
 * Dao - 优惠券
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("couponDaoImpl")
public class CouponDaoImpl extends BaseDaoImpl<Coupon, Long> implements
		CouponDao {
	private static Logger logger = Logger.getLogger(CouponDaoImpl.class);
	public Page<Coupon> findPage(Boolean isEnabled, Boolean isExchange,
			Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder
				.createQuery(Coupon.class);
		Root<Coupon> root = criteriaQuery.from(Coupon.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (isEnabled != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isEnabled"), isEnabled));
		}
		if (isExchange != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isExchange"), isExchange));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions,
						root.get("endDate").isNotNull(), criteriaBuilder
								.lessThan(root.<Date> get("endDate"),
										new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder
								.or(root.get("endDate").isNull(),
										criteriaBuilder.greaterThanOrEqualTo(
												root.<Date> get("endDate"),
												new Date())));
			}
		}
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("isExchange"), true));  //过滤不需要萌值兑换的优惠券,此类优惠券做其它功能用
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("isMarketbelong"), false)); // 过滤营销专属的优惠券

		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@SuppressWarnings("unchecked")
	public List<Coupon> findCoupunPrdList(Long[] productId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder
				.createQuery(Coupon.class);
		Root<Coupon> Coupon = criteriaQuery.from(Coupon.class);
		// Join<Coupon, ProductCategory> cateItems =
		// Coupon.join("productCategories");
		Join<Coupon, Product> productitems = Coupon.join("products");
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productId != null) {

			Path<Object> path = ((Path<Object>) productitems.<Object> get("id"));
			In<Object> in = criteriaBuilder.in(path);
			for (Long conditionColumnValue : productId) {
				in.value(conditionColumnValue);
			}
			restrictions = criteriaBuilder.and(restrictions, in);
		}

		criteriaQuery.where(restrictions);

		return entityManager.createQuery(criteriaQuery)
				.setFlushMode(FlushModeType.COMMIT).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Coupon> findCoupunCateList(Long[] productCategoryId,
			Long[] parentcatergorytIds) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder
				.createQuery(Coupon.class);
		Root<Coupon> Coupon = criteriaQuery.from(Coupon.class);
		Join<Coupon, ProductCategory> cateItems = Coupon
				.join("productCategories");
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategoryId != null) {

			Path<Object> path = ((Path<Object>) cateItems.<Object> get("id"));
			In<Object> in = criteriaBuilder.in(path);
			for (Long conditionColumnValue : productCategoryId) {
				in.value(conditionColumnValue);
			}
			restrictions = criteriaBuilder.and(restrictions, in);
			// 防止最顶级分类为空的情况
			if (parentcatergorytIds != null) {
				Path<Object> pathParent = ((Path<Object>) cateItems
						.<Object> get("id"));
				In<Object> parentin = criteriaBuilder.in(pathParent);
				for (Long conditionColumnValue : parentcatergorytIds) {
					parentin.value(conditionColumnValue);
				}

				restrictions = criteriaBuilder.or(restrictions, parentin);
			}
		}

		criteriaQuery.where(restrictions);

		return entityManager.createQuery(criteriaQuery)
				.setFlushMode(FlushModeType.COMMIT).getResultList();

	}

	@Override
	public Coupon findbyName(String couponName) {
		// TODO Auto-generated method stub
		try {
			logger.info("receive findbyName: "+couponName);
			CriteriaBuilder criteriaBuilder = entityManager
					.getCriteriaBuilder();
			CriteriaQuery<Coupon> criteriaQuery = criteriaBuilder
					.createQuery(Coupon.class);
			Predicate restrictions = criteriaBuilder.conjunction();
			Root<Coupon> root = criteriaQuery.from(Coupon.class);
			if (couponName != null) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get("name"), couponName));
			}

			criteriaQuery.where(restrictions);
			Coupon value=entityManager.createQuery(criteriaQuery)
					.setFlushMode(FlushModeType.COMMIT).getSingleResult();
			logger.info("receive findbyName end ");
			return value;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}

	}

	/**
	 * 查询会员下可用的
	 * 
	 * @param productCategoryId
	 * @param productId
	 * @return
	 */
	/*
	 * private List<Coupon> findAvalidCoupunList(Long productCategoryId, Long
	 * productId) { CriteriaBuilder criteriaBuilder =
	 * entityManager.getCriteriaBuilder(); CriteriaQuery<Coupon> criteriaQuery =
	 * criteriaBuilder.createQuery(Coupon.class); Root<Coupon> Coupon =
	 * criteriaQuery.from(Coupon.class); Join<Coupon, ProductCategory> cateItems
	 * = Coupon.join("productCategories"); Join<Coupon, Product> productitems =
	 * Coupon.join("products"); Predicate restrictions =
	 * criteriaBuilder.conjunction(); if (productCategoryId != null) {
	 * 
	 * 
	 * restrictions = criteriaBuilder.and(restrictions,
	 * criteriaBuilder.equal(cateItems.<Long> get("id"), productCategoryId)); }
	 * if (productId != null) { restrictions = criteriaBuilder.or(restrictions,
	 * criteriaBuilder.equal(productitems.<Long> get("id"), productId)); }
	 * 
	 * criteriaQuery.where(restrictions);
	 * 
	 * 
	 * return
	 * entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType
	 * .COMMIT).getResultList();
	 * 
	 * 
	 * }
	 */

}