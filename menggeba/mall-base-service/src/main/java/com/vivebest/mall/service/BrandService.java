/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Brand;


/**
 * Service - 品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface BrandService extends BaseService<Brand, Long> {

	/**
	 * 查找品牌(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 品牌(缓存)
	 */
	List<Brand> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);
	
	/***
	 * 根据名称查找品牌
	 * @param Name
	 * @return
	 */
	Brand findByName(String Name);
	
	/**
	 * 品牌列表
	 * @param brand
	 * @param pageable
	 * @return
	 */
	Page<Brand> findList(Brand brand, Pageable pageable);
	
	/**
	 * 查询一级分类
	 * @return
	 */
	List<Brand> findParent();
	
	/**
	 * 设置品牌为推荐
	 * @param brand
	 * @return
	 */
	Brand setIsPopularity(Brand brand);

}