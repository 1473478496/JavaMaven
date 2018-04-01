package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.TieInSaleTitle;

/**
 * 商品搭配销售标题 Dao
 * 
 * @author junly
 *
 */
public interface TieInSaleTitleDao extends BaseDao<TieInSaleTitle, Long> {

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
	public List<TieInSaleTitle> findTieSaleTitleByCatetory(Long catetoryId);
	
	/**
	 * 通过分类id与标题名查找标题实体
	 * @param catetoryId
	 * @param title
	 * @return
	 */
	public List<TieInSaleTitle> findByCatetoryTitle(Long catetoryId, String title);
	
}
