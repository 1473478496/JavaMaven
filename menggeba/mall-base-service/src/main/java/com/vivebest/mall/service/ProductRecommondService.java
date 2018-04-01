package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.ProductRecommond;

/**
 * Service - 首页楼层
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ProductRecommondService extends BaseService<ProductRecommond, Long> {

	/**
	 * 检查商品是否已存在楼层
	 * @param id
	 * @return
	 */
	boolean checkProduct(Long id);

	/**
	 * 通过商品id查找
	 * @param productId
	 * @return
	 */
	ProductRecommond findByProductId(Long productId);

}
