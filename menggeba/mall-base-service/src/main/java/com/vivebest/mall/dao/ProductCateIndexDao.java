/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.ProductCateIndex;


/**
 * Dao - 商品首页显示
 * 
 * @author junly
 * @version 3.0
 */
public interface ProductCateIndexDao extends BaseDao<ProductCateIndex, Long> {

	/**
	 * 获取首页显示商品
	 * 
	 * @param count
	 *            数量
	 * @return 首页显示商品系列
	 */
	List<ProductCateIndex> findAll(Integer count);

	/**
	 * 通过名称查找
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