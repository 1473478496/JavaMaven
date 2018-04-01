/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Navigation;
import com.vivebest.mall.entity.Navigation.Position;


/**
 * Service - 导航
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface NavigationService extends BaseService<Navigation, Long> {

	/**
	 * 查找导航
	 * 
	 * @param position
	 *            位置
	 * @return 导航
	 */
	List<Navigation> findList(Position position);

	/**
	 * 查找导航(缓存)
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @param cacheRegion
	 *            缓存区域
	 * @return 导航(缓存)
	 */
	List<Navigation> findList(Integer count, List<Filter> filters, List<Order> orders, String cacheRegion);

}