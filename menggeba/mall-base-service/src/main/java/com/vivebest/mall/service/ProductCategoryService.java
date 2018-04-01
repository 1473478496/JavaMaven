/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.ProductCategory;


/**
 * Service - 商品分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ProductCategoryService extends BaseService<ProductCategory, Long> {

	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots();
	
	/**
	 * 查找顶级商品分类
	 * 
	 * @return 顶级商品分类
	 */
	List<ProductCategory> categoryFindRoots();
	/**
	 * 根据商品分类名称查找商品
	 * 
	 * @param sn
	 *            商品编号(忽略大小写)
	 * @return 商品，若不存在则返回null
	 */
	ProductCategory findByName(String name);
	

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots(Integer count);

	/**
	 * 查找顶级商品分类(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 顶级商品分类(缓存)
	 */
	List<ProductCategory> findRoots(Integer count, String cacheRegion);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory, Integer count);

	/**
	 * 查找上级商品分类(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 上级商品分类(缓存)
	 */
	List<ProductCategory> findParents(ProductCategory productCategory, Integer count, String cacheRegion);

	/**
	 * 查找商品分类树
	 * 
	 * @return 商品分类树
	 */
	List<ProductCategory> findTree();

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory, Integer count);

	/**
	 * 查找下级商品分类(缓存)
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param count
	 *            数量
	 * @param cacheRegion
	 *            缓存区域
	 * @return 下级商品分类(缓存)
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory, Integer count, String cacheRegion);

}