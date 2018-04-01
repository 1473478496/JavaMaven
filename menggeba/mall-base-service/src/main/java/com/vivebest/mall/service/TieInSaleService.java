package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.TieInSale;

/**
 * 商品搭配销售标题 service
 * 
 * @author junly
 *
 */
public interface TieInSaleService extends BaseService<TieInSale, Long> {
	
	/**
	 * 通过主商品的Id和搭配的子标题和搭配的分类查找搭配商品的集合
	 * 
	 * @param tieInSaleCatetory
	 *            商品搭配分类
	 * @param tieInSaleTitles
	 *            商品搭配销售标题集合
	 * @param productId
	 *            主商品Id
	 * @return 商品搭配分类集合
	 */
	public List<TieInSale> findProductByCyAndTlAndPId(Long catetoryId,
			Long tieTitleId, Long productId);
	
	
	/**
	 * 通过搭配商品的Id和查找搭配商品的集合

	 * @param productSuitId
	 *            搭配商品Id
	 * @return 商品搭配分类集合
	 */
	public List<TieInSale> findProductByPSuitId( Long productSuitId);
	
	/**
	 * 通过主商品Id查找搭配商品集合
	 * @param ProductId
	 * 			主商品Id
	 * @return
	 */
	public List<TieInSale> findByProductId (Long ProductId);

	
	/**
	 * 通过搭配商品的Id删除

	 * @param productSuitId
	 *            搭配商品Id
	 */
	public void deleteProductByPSuitId(Long productSuitId);

}
