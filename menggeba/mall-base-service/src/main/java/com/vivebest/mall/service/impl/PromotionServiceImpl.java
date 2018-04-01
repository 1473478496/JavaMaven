/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.PromotionDao;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.service.PromotionService;

/**
 * Service - 促销
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("promotionServiceImpl")
public class PromotionServiceImpl extends BaseServiceImpl<Promotion, Long>implements PromotionService {

	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;

	@Resource(name = "promotionDaoImpl")
	public void setBaseDao(PromotionDao promotionDao) {
		super.setBaseDao(promotionDao);
	}

	@Transactional(readOnly = true)
	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters,
			List<Order> orders) {
		return promotionDao.findList(hasBegun, hasEnded, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable("promotion")
	public List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters,
			List<Order> orders, String cacheRegion) {
		return promotionDao.findList(hasBegun, hasEnded, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void save(Promotion promotion) {
		super.save(promotion);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public Promotion update(Promotion promotion) {
		return super.update(promotion);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public Promotion update(Promotion promotion, String... ignoreProperties) {
		return super.update(promotion, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Transactional
	@CacheEvict(value = { "promotion", "product" }, allEntries = true)
	public void delete(Promotion promotion) {
		super.delete(promotion);
	}

	public List<Promotion> findCurrentSeckill() {
		return promotionDao.findCurrentSeckill();
	}

	public Promotion findByCurrent(Promotion.Category category) {
		List<Promotion> promotions = promotionDao.findByCurrent(category);
		if (promotions != null && promotions.size()>0) {
			return promotions.get(0);
		} else {
			return null;
		}
	}

	public List<Promotion> findUnBeginByCurrent(Promotion.Category category) {
		List<Promotion> promotions = promotionDao.findUnBeginByCurrent(category);
		if (promotions != null && promotions.size() > 0) {
			Promotion promotion = promotions.get(0);
			if (promotion.getBeginDate().equals(new Date())) {
				promotions.remove(promotion);
			}
		}
		return promotions;
	}

	// public List<Promotion> findByCateGory(Promotion.Category category) {
	// return promotionDao.findByCategory(category);
	// }

	public List<Promotion> findByTimePoint(Promotion.Category category, Date beginDate, String beginTime, Date date) {
		return promotionDao.findByTimePoint(category, beginDate, beginTime, date);
	}
	
	public Promotion findByTimePoint(Promotion.Category category, Date beginDate) {
		return promotionDao.findByTimePoint(category, beginDate);
	}
	
	public Page<Promotion> findPage(Date beginDate, Date endDate, Boolean status,
			Pageable pageable) {
		return promotionDao.findPage(beginDate, endDate, status, pageable);
	}
	
}