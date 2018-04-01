/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.PosterDao;
import com.vivebest.mall.entity.Poster;
import com.vivebest.mall.service.PosterService;

/**
 * Service - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("posterServiceImpl")
public class PosterServiceImpl extends BaseServiceImpl<Poster, Long> implements PosterService {

	@Resource(name = "posterDaoImpl")
	public void setBaseDao(PosterDao posterDao) {
		super.setBaseDao(posterDao);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void save(Poster poster) {
		super.save(poster);
	}

	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public Poster update(Poster poster) {
		return super.update(poster);
	}


	@Override
	@Transactional
	@CacheEvict(value = "adPosition", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

}