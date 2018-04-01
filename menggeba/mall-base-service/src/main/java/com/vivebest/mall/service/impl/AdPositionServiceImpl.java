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
import com.vivebest.mall.dao.AdPositionDao;
import com.vivebest.mall.entity.AdPosition;
import com.vivebest.mall.service.AdPositionService;

/**
 * Service - 广告位
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("adPositionServiceImpl")
public class AdPositionServiceImpl extends BaseServiceImpl<AdPosition, Long> implements AdPositionService {

	@Resource(name = "adPositionDaoImpl")
	private AdPositionDao adPositionDao;

	@Resource(name = "adPositionDaoImpl")
	public void setBaseDao(AdPositionDao adPositionDao) {
		super.setBaseDao(adPositionDao);
	}

	@Transactional(readOnly = true)
	@Cacheable("adPosition")
	public AdPosition find(Long id, String cacheRegion) {
		return adPositionDao.find(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void save(AdPosition adPosition) {
		super.save(adPosition);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition update(AdPosition adPosition) {
		return super.update(adPosition);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public AdPosition update(AdPosition adPosition, String... ignoreProperties) {
		return super.update(adPosition, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(AdPosition adPosition) {
		super.delete(adPosition);
	}

}