/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Promotion;

/**
 * Dao - 促销
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface PromotionDao extends BaseDao<Promotion, Long> {

	/**
	 * 查找促销
	 * 
	 * @param hasBegun
	 *            是否已开始
	 * @param hasEnded
	 *            是否已结束
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 促销
	 */
	List<Promotion> findList(Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters,
			List<Order> orders);

	/**
	 * 查找指定时间开始的促销活动
	 * 
	 * @param date
	 * @param status
	 * @return
	 */
	// Promotion findPromotionList(Date date);

	/**
	 * 查找今天所有已启动的整点秒杀
	 * @return
	 */
	List<Promotion> findCurrentSeckill();

	/**
	 * 通过当前时间查找当前活动
	 * @return
	 */
	List<Promotion> findByCurrent(Promotion.Category category);
	
	/**
	 * 通过当前时间查找没有开始的整点秒杀
	 * @return
	 */
	List<Promotion> findUnBeginByCurrent(Promotion.Category category);
	
	/**
	 * 通过促销类型查找
	 * @param category
	 * @return
	 */
	//List<Promotion> findByCategory(Promotion.Category category);
	
	/**
	 * 通过促销时间查找
	 * @param category
	 * @return
	 */
	List<Promotion> findByTimePoint(Promotion.Category category, Date beginDate,String beginTime,Date date);

	Promotion findByTimePoint(Promotion.Category category, Date beginDate);
	
	/**
	 * 查询分页
	 * @param beginDate
	 * 			开始日期
	 * @param endDate
	 * 			结束日期
	 * @param status
	 * 			活动状态
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<Promotion> findPage(Date beginDate, Date endDate, Boolean status,
			Pageable pageable);
	
}