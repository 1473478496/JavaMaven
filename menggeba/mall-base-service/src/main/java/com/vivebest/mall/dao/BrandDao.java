/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Brand;
 

/**
 * Dao - 品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface BrandDao extends BaseDao<Brand, Long> {
    
	Brand findByName(String name);
	
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
	 */
	Brand setIsPopularity(Brand brand);
}