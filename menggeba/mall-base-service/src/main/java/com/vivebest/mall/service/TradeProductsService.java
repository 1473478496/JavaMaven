package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.TradeProducts;

/**
 * 
 * 商户商品分类
 * @author Terry
 *
 */
public interface TradeProductsService extends BaseService<TradeProducts, Long> {

	/**
	 * 根据商户id查询列表
	 * 
	 */
	public List<Long> findList(Long tradeId);
}
