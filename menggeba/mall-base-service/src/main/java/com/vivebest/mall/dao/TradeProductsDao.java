package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.TradeProducts;
/**
 * 
 * 商户商品分类
 * @author Terry
 *
 */
public interface TradeProductsDao extends BaseDao<TradeProducts,Long> {

	/**
	 * 根据商户id查询列表
	 * 
	 */
	public List<Long> findList(Long tradeId); 
	
}
