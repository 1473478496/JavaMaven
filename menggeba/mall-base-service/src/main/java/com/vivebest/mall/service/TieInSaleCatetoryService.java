package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.TieInSaleCatetory;

/**
 * 商品搭配分类 service
 * 
 * @author junly
 *
 */
public interface TieInSaleCatetoryService extends BaseService<TieInSaleCatetory, Long> {
	/**
	 * 通过搭配分类查找商品搭配分类
	 * 
	 * @param tieInSaleCatetory
	 *            商品搭配分类
	 * @return 商品搭配分类
	 */
	public TieInSaleCatetory findTieCatetoryByCatetory(String name);

	/**
	 * 通过商品Id查找商品搭配分类
	 * 
	 * @param ProductId
	 *            商品Id
	 * @return 商品搭配分类集合
	 */
	public List<TieInSaleCatetory> findTieCatetoryByProId(Long ProductId);
}
