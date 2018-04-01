/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.ProductCateIndex;


/**
 * Service - 首页商品显示
 * 
 * @author junly
 * @version 3.0
 */
public interface ProductCateIndexService extends BaseService<ProductCateIndex, Long> {
	/**
	 * 查找在首页显示商品
	 * 
	 * @param count
	 *            数量
	 * @return 首页显示商品
	 */
	List<ProductCateIndex> findAll(Integer count);

	/**
	 * 查找在首页显示商品(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 首页显示商品(缓存)
	 */
	List<ProductCateIndex> findAll(Integer count, String cacheRegion);

	/**
	 * 根据名称查找
	 * @param name 名称
	 * @return
	 */
	ProductCateIndex findByName(String name);

	/**
	 * 检查名称是否已存在
	 * @param name 名称
	 * @return
	 */
	boolean checkUsername(String name);

}