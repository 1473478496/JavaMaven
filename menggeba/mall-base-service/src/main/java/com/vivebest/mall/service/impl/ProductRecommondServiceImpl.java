package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ProductRecommondDao;
import com.vivebest.mall.entity.ProductRecommond;
import com.vivebest.mall.service.ProductRecommondService;

/**
 * Service - 首页楼层
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("productRecommondServiceImpl")
public class ProductRecommondServiceImpl extends BaseServiceImpl<ProductRecommond, Long> implements ProductRecommondService {

	@Resource(name = "productRecommondDaoImpl")
	private ProductRecommondDao productRecommondDao;
	
	@Resource(name = "productRecommondDaoImpl")
	public void setBaseDao(ProductRecommondDao productRecommondDao){
		super.setBaseDao(productRecommondDao);
	}

	@Override
	public boolean checkProduct(Long id) {
		return productRecommondDao.checkProduct(id);
	}

	@Override
	public ProductRecommond findByProductId(Long productId) {
		return productRecommondDao.findByProductId(productId);
	}
	
}
