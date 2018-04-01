/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ProductCateIndexDao;
import com.vivebest.mall.entity.ProductCateIndex;
import com.vivebest.mall.service.ProductCateIndexService;

/**
 * Service - 首页显示
 * 
 * @author ding.hy 
 * 
 * @version 3.0
 */
@Service("productCateIndexServiceImpl")
public class ProductCateIndexServiceImpl extends BaseServiceImpl<ProductCateIndex, Long> implements ProductCateIndexService {

	@Resource(name = "productCateIndexDaoImpl")
	private ProductCateIndexDao productCateIndexDao;

	@Transactional(readOnly = true)
	public List<ProductCateIndex> findAll(Integer count) {
		return productCateIndexDao.findAll(count);
	}

	@Transactional(readOnly = true)
	public List<ProductCateIndex> findAll(Integer count, String cacheRegion) {
		return productCateIndexDao.findAll(count);
	}

	@Transactional(readOnly = true)
	public ProductCateIndex find(Long id) {
		return productCateIndexDao.find(id);
	}
	
	@Override
	@Transactional
	public void save(ProductCateIndex productCateIndex) {
		productCateIndexDao.persist(productCateIndex);
	}
	
	@Override
	@Transactional
	public void delete(ProductCateIndex productCateIndex) {
		productCateIndexDao.remove(productCateIndex);
	}
	
	@Override
	@Transactional
	public ProductCateIndex update(ProductCateIndex productCateIndex) {
		return productCateIndexDao.merge(productCateIndex);
	}

	@Override
	public ProductCateIndex findByName(String name) {
		return productCateIndexDao.findByName(name);
	}

	@Override
	public boolean checkUsername(String name) {
		return productCateIndexDao.checkUsername(name);
	}
	
}