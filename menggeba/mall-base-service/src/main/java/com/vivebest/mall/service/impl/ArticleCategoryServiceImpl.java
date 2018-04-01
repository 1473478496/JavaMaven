/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ArticleCategoryDao;
import com.vivebest.mall.entity.ArticleCategory;
import com.vivebest.mall.entity.Navigation;
import com.vivebest.mall.service.ArticleCategoryService;

/**
 * Service - 文章分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("articleCategoryServiceImpl")
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategory, Long> implements ArticleCategoryService {

	@Resource(name = "articleCategoryDaoImpl")
	private ArticleCategoryDao articleCategoryDao;

	@Resource(name = "articleCategoryDaoImpl")
	public void setBaseDao(ArticleCategoryDao articleCategoryDao) {
		super.setBaseDao(articleCategoryDao);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findRoots() {
		return articleCategoryDao.findRoots(null);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findRoots(Integer count) {
		return articleCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	@Cacheable("articleCategory")
	public List<ArticleCategory> findRoots(Integer count, String cacheRegion) {
		return articleCategoryDao.findRoots(count);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findParents(ArticleCategory articleCategory) {
		return articleCategoryDao.findParents(articleCategory, null);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findParents(ArticleCategory articleCategory, Integer count) {
		return articleCategoryDao.findParents(articleCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("articleCategory")
	public List<ArticleCategory> findParents(ArticleCategory articleCategory, Integer count, String cacheRegion) {
		return articleCategoryDao.findParents(articleCategory, count);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findTree() {
		return articleCategoryDao.findChildren(null, null);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findChildren(ArticleCategory articleCategory) {
		return articleCategoryDao.findChildren(articleCategory, null);
	}

	@Transactional(readOnly = true)
	public List<ArticleCategory> findChildren(ArticleCategory articleCategory, Integer count) {
		return articleCategoryDao.findChildren(articleCategory, count);
	}

	@Transactional(readOnly = true)
	@Cacheable("articleCategory")
	public List<ArticleCategory> findChildren(ArticleCategory articleCategory, Integer count, String cacheRegion) {
		return articleCategoryDao.findChildren(articleCategory, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void save(ArticleCategory articleCategory) {
		super.save(articleCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public ArticleCategory update(ArticleCategory articleCategory) {
		return super.update(articleCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public ArticleCategory update(ArticleCategory articleCategory, String... ignoreProperties) {
		return super.update(articleCategory, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public void delete(ArticleCategory articleCategory) {
		super.delete(articleCategory);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public List<ArticleCategory> findRoots(Navigation navigation, Integer count, String cacheRegion) {
		return articleCategoryDao.findRoots(navigation, count);
	}

	@Override
	@Transactional
	@CacheEvict(value = { "article", "articleCategory" }, allEntries = true)
	public List<ArticleCategory> findRoots(Navigation navigation, Integer count) {
		return articleCategoryDao.findRoots(navigation, count);
	}
	

}