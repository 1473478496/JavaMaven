/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

 
import java.util.List;

import com.vivebest.mall.entity.ProductImage;

/**
 * Service - 商品图片
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface ProductImageService {

	/**
	 * 生成商品图片
	 * 
	 * @param productImage
	 *            商品图片
	 */
	void build(ProductImage productImage);
	
	
	List<ProductImage>  buildSource(String  productImgSour);
}