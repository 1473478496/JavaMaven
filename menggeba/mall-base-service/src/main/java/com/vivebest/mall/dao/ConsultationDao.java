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
import com.vivebest.mall.entity.Consultation;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;


/**
 * Dao - 咨询
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ConsultationDao extends BaseDao<Consultation, Long> {

	/**
	 * 查找咨询
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 咨询
	 */
	List<Consultation> findList(Member member, Product product, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找咨询分页
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 咨询分页
	 */
	Page<Consultation> findPage(Member member, Product product, Boolean isShow, Pageable pageable);
	
	/**
	 * 查找咨询分页(日期)
	 * 
	 * @param member
	 *            会员
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param product
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 咨询分页
	 */
	Page<Consultation> findPage(Member member, Date beginDate, Date endDate, Product product, Boolean isShow, Pageable pageable);

	/**
	 * 查找咨询数量
	 * 
	 * @param member
	 *            会员
	 * @param product
	 *            商品
	 * @param isShow
	 *            是否显示
	 * @return 咨询数量
	 */
	Long count(Member member, Product product, Boolean isShow);

}