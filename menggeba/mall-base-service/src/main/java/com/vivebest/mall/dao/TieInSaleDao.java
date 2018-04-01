package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.TieInSale;


/**
 * ——dao
 * 
 * @author junly
 *
 */
public interface TieInSaleDao extends BaseDao<TieInSale, Long> {

	/**
	 * 通过主商品的Id查找搭配商品的集合
	 * @param productId
	 *            主商品Id
	 * @return 商品搭配分类集合
	 */
	public List<TieInSale> findTieSaleByPId(Long productId);
	
	/**
	 * 通过搭配商品的Id查找搭配商品的集合
	 * @param productSuitId
	 *            搭配商品Id
	 * @return 商品搭配分类集合
	 */
	public List<TieInSale> findTieSaleByPSuitId(Long productSuitId);
	
	/**
	 * 通过主商品Id查找搭配商品集合
	 * @param ProductId
	 * 			主商品Id
	 * @return
	 */
	public List<TieInSale> findByProductId (Long ProductId);
	
}
