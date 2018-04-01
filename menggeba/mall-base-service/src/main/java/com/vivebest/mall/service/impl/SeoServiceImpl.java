/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.SeoDao;
import com.vivebest.mall.entity.Seo;
import com.vivebest.mall.entity.Seo.Type;
import com.vivebest.mall.service.SeoService;

/**
 * Service - SEO设置
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("seoServiceImpl")
public class SeoServiceImpl extends BaseServiceImpl<Seo, Long> implements SeoService {

	@Resource(name = "seoDaoImpl")
	private SeoDao seoDao;

	@Resource(name = "seoDaoImpl")
	public void setBaseDao(SeoDao seoDao) {
		super.setBaseDao(seoDao);
	}

	@Transactional(readOnly = true)
	public Seo find(Type type) {
		return seoDao.find(type);
	}

	@Transactional(readOnly = true)
	@Cacheable("seo")
	public Seo find(Type type, String cacheRegion) {
		return seoDao.find(type);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void save(Seo seo) {
		super.save(seo);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public Seo update(Seo seo) {
		return super.update(seo);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public Seo update(Seo seo, String... ignoreProperties) {
		return super.update(seo, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "seo", allEntries = true)
	public void delete(Seo seo) {
		super.delete(seo);
	}

}