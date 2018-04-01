/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.PromotionDao;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Promotion.Category;

/**
 * Dao - 促销
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("promotionDaoImpl")
public class PromotionDaoImpl extends BaseDaoImpl<Promotion, Long>implements PromotionDao {
	private static Logger logger = Logger.getLogger(PromotionDaoImpl.class);

	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters,
			List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (hasBegun != null) {
			if (hasBegun) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(),
						criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), new Date())));
			} else {
				restrictions = criteriaBuilder.and(restrictions, root.get("beginDate").isNotNull(),
						criteriaBuilder.greaterThan(root.<Date> get("beginDate"), new Date()));
			}
		}
		if (hasEnded != null) {
			if (hasEnded) {
				restrictions = criteriaBuilder.and(restrictions, root.get("endDate").isNotNull(),
						criteriaBuilder.lessThan(root.<Date> get("endDate"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(),
						criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Promotion> findCurrentSeckill() {
		Locale locale = Locale.CANADA;
		SimpleDateFormat frm1 = new SimpleDateFormat("yyyy-MM-dd", locale);
		Calendar cal = Calendar.getInstance();
		//DateFormat d1 = DateFormat.getDateInstance();
		cal.add(Calendar.DATE, 0);
		Date before = cal.getTime();
		String str1 = frm1.format(before);
		cal.add(Calendar.DATE, 1);
		Date after = cal.getTime();
		String str2 = frm1.format(after);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",locale);
		Date beginDate = null;
		try {
			beginDate = format.parse(str1);
			//Calendar calendar = DateUtils.toCalendar(beginDate);
			//calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			//calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			//calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			//beginDate = calendar.getTime();
		} catch (ParseException e) {
			logger.error("日期格式化失败", e);
		}

		Date endDate = null;
		try {
			endDate = format.parse(str2);
			//Calendar calendar = DateUtils.toCalendar(endDate);
			//calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			//calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		//	calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		//	endDate = calendar.getTime();
		} catch (ParseException e) {
			logger.error("日期格式化失败", e);

		}

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(),
				criteriaBuilder.between(root.<Date> get("beginDate"), beginDate, endDate)));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("category"), Category.secKill));

		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("beginDate")));
		return super.findList(criteriaQuery, null, null, null, null);
	}

	public List<Promotion> findByCurrent(Promotion.Category category) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(),
				criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), new Date())));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("endDate").isNull(),
				criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("endDate"), new Date())));
		if (category != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("category"), category));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("beginDate")));
		try {
			return super.findList(criteriaQuery, null, null, null, null);
		} catch (Exception e) {
			logger.info(e);
			return null;
		}
	}

	// String spql0 = "select promotion from Promotion promotion where
	// lower(promotion.beginDate)<=lower(:date) and
	// (promotion.category)=low(:category) order by promotion.beginDate desc";
	// Promotion promotion = entityManager.createQuery(spql0,
	// Promotion.class).setFlushMode(FlushModeType.COMMIT)
	// .setParameter("date", date).setParameter("category",
	// category).getResultList().get(0);
	// List<Promotion> promotions = new ArrayList<Promotion>();
	// if (promotion != null) {
	// promotions.add(promotion);
	// }
	//
	// String spql = "select promotion from Promotion promotion where
	// lower(promotion.beginDate)>lower(:date) and
	// (promotion.category)=low(:category) order by promotion.beginDate";
	// List<Promotion> promotionss = entityManager.createQuery(spql,
	// Promotion.class)
	// .setFlushMode(FlushModeType.COMMIT).setParameter("date",
	// date).setParameter("category", category).getResultList();
	// if (promotionss != null) {
	// for (int i = 0; i < 3; i++) {
	// if (promotionss.size() > (i)) {
	// promotions.add(promotionss.get(i));
	// }
	// }
	// }
	// return promotions;
	// }

	public List<Promotion> findUnBeginByCurrent(Promotion.Category category) {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String nowDate = sf.format(date);
		// 通过日历获取下一天日期
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sf.parse(nowDate));
		} catch (ParseException e) {
			logger.info(e);
		}
		cal.add(Calendar.DATE, 1);
		Date afterDay = cal.getTime();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(),
				criteriaBuilder.between(root.<Date> get("beginDate"), date, afterDay)));
		if (category != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("category"), category));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("beginDate")));
		return super.findList(criteriaQuery, null, null, null, null);
	}

	// public List<Promotion> findByCategory(Promotion.Category category) {
	// CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	// CriteriaQuery<Promotion> criteriaQuery =
	// criteriaBuilder.createQuery(Promotion.class);
	// Root<Promotion> root = criteriaQuery.from(Promotion.class);
	// criteriaQuery.select(root);
	// if (category!=null) {
	// criteriaQuery.where(criteriaBuilder.equal(root.get("category"),
	// category.ordinal()));
	// }
	//
	// return super.findList(criteriaQuery, null, null, null, null);
	// }

	public List<Promotion> findByTimePoint(Promotion.Category category, Date beginDate, String beginTime, Date date) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (category != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("category"), category.ordinal()));
		}
		if (date != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(),
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("beginDate"), date)));
		}
		if (beginTime != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("timePoint"), beginTime));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("beginDate").isNull(),
					criteriaBuilder.equal(root.<Date> get("beginDate"), beginDate)));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("beginDate")));
		try {
			return super.findList(criteriaQuery, null, null, null, null);
		} catch (Exception e) {
			logger.info(e);
			return null;
		}
	}

	public Promotion findByTimePoint(Promotion.Category category, Date beginDate) {

		if (beginDate == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");

		String jpql = "select promotion from Promotion promotion where promotion.category = :category and promotion.beginDate= :beginDate";
		try {
			return entityManager.createQuery(jpql, Promotion.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("category", category).setParameter("beginDate", format.format(beginDate))
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Page<Promotion> findPage(Date beginDate, Date endDate, Boolean status,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Promotion> criteriaQuery = criteriaBuilder.createQuery(Promotion.class);
		Root<Promotion> root = criteriaQuery.from(Promotion.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("beginDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("beginDate"), endDate));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
}