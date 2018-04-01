package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.TieInSaleTitle;

/**
 * 商品搭配销售标题 service
 * 
 * @author junly
 *
 */
public interface TieInSaleTitleService extends BaseService<TieInSaleTitle, Long> {
	/**
	 * 通过子标题查找商品搭配销售标题
	 * 
	 * @param title
	 *            子标题
	 * @return 商品搭配销售标题
	 */
	public List<TieInSaleTitle> findTieSaleTitleByTitle(String title);

	/**
	 * 通过分类查找商品搭配销售标题
	 * 
	 * @param catetoryId
	 *            分类id
	 * @return 商品搭配销售标题
	 */
	public List<TieInSaleTitle> findTieSaleTitleByCatetory(Long catetoryId,Long productId);
	
	
	/**
	 * 通过分类Id查找标题
	 * 
	 * @param catetoryId
	 *            分类Id
	 * @return 商品搭配销售标题集合
	 */
	public List<TieInSaleTitle> findTieInSaleTitleByCatetoryId(Long catetoryId);
	
	
	/**
	 * 通过分类id与标题名查找标题实体
	 * @param catetoryId
	 * @param title
	 * @return
	 */
	public List<TieInSaleTitle> findByCatetoryTitle(Long catetoryId, String title);
}
