package com.vivebest.mall.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.TieInSaleCatetory;

/**
 * 商品搭配分类 dao
 * 
 * @author junly
 *
 */
public interface TieInSaleCatetoryDao extends BaseDao<TieInSaleCatetory, Long> {
	
	/**
	 * 通过搭配分类查找商品搭配分类名称
	 * 
	 * @param tieInSaleCatetory
	 *            商品搭配分类
	 * @return 商品搭配分类
	 */
	public TieInSaleCatetory findTieCatetoryByCatetory(String name);
}
