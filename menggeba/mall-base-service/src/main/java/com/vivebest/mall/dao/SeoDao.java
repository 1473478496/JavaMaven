/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Seo;
import com.vivebest.mall.entity.Seo.Type;

/**
 * Dao - SEO设置
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface SeoDao extends BaseDao<Seo, Long> {

	/**
	 * 查找SEO设置
	 * 
	 * @param type
	 *            类型
	 * @return SEO设置
	 */
	Seo find(Type type);

}