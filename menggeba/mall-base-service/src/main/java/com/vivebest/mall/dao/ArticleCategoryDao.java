/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.ArticleCategory;
import com.vivebest.mall.entity.Navigation;


/**
 * Dao - 文章分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ArticleCategoryDao extends BaseDao<ArticleCategory, Long> {

	/**
	 * 查找顶级文章分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级文章分类
	 */
	List<ArticleCategory> findRoots(Integer count);

	/**
	 * 查找上级文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param count
	 *            数量
	 * @return 上级文章分类
	 */
	List<ArticleCategory> findParents(ArticleCategory articleCategory, Integer count);

	/**
	 * 查找下级文章分类
	 * 
	 * @param articleCategory
	 *            文章分类
	 * @param count
	 *            数量
	 * @return 下级文章分类
	 */
	List<ArticleCategory> findChildren(ArticleCategory articleCategory, Integer count);
	
	/**
	 * @Title: findRoots 
	 * @Description: 查找顶级文章分类
	 * @param @param navigation
	 * @param @param count
	 * @param @return    设定文件 
	 * @return List<ArticleCategory>    返回类型 
	 * @throws
	 */
	List<ArticleCategory> findRoots(Navigation navigation, Integer count);

}