/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.Map;

import com.vivebest.mall.entity.Article;
import com.vivebest.mall.entity.Product;


/**
 * Service - 静态化
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface StaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath);

	/**
	 * 生成静态
	 * 
	 * @param article
	 *            文章
	 * @return 生成数量
	 */
	int build(Article article);

	/**
	 * 生成静态
	 * 
	 * @param product
	 *            商品
	 * @return 生成数量
	 */
	int build(Product product);

	/**
	 * 生成首页静态
	 * 
	 * @return 生成数量
	 */
	int buildIndex();

	/**
	 * 生成Sitemap
	 * 
	 * @return 生成数量
	 */
	int buildSitemap();

	/**
	 * 生成admin其它静态
	 * 
	 * @return 生成数量
	 */
	public int buildAdminOther();
	
	/**
	 * 生成shop其它静态
	 * 
	 * @return 生成数量
	 */
	public int buildShopOther();
	
	/**
	 * 生成文章静态
	 * 
	 * @return 生成数量
	 */
	int buildArticle(); 
	
	/**
	 * 生成商品静态
	 * @return
	 */
	int buildProduct();
	/**
	 * 删除静态
	 * 
	 * @param staticPath
	 *            静态文件路径
	 * @return 删除数量
	 */
	int delete(String staticPath);

	/**
	 * 删除静态
	 * 
	 * @param article
	 *            文章
	 * @return 删除数量
	 */
	int delete(Article article);

	/**
	 * 删除静态
	 * 
	 * @param product
	 *            商品
	 * @return 删除数量
	 */
	int delete(Product product);

	/**
	 * 删除首页静态
	 * 
	 * @return 删除数量
	 */
	int deleteIndex();

	/**
	 * 删除其它静态
	 * 
	 * @return 删除数量
	 */
	int deleteOther();

}