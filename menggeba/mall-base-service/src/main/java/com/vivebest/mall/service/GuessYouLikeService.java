package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.GuessYouLike;

/**
 * Service - 首页猜你喜欢
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface GuessYouLikeService extends BaseService<GuessYouLike, Long> {

	/**
	 * 验证
	 * @param productId 商品id
	 * @return
	 */
	boolean checkProduct(Long productId);
	
	/**
	 * 查找实体对象集合
	 * 
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 实体对象集合
	 */
	List<GuessYouLike> findList(Integer count, List<Filter> filters, List<Order> orders);

}
