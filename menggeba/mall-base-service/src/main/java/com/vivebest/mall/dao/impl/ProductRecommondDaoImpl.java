package com.vivebest.mall.dao.impl;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ProductRecommondDao;
import com.vivebest.mall.entity.ProductRecommond;

/**
 * Dao - 首页楼层
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("productRecommondDaoImpl")
public class ProductRecommondDaoImpl extends BaseDaoImpl<ProductRecommond, Long> implements ProductRecommondDao {

	@Override
	public boolean checkProduct(Long productId) {
		if (productId == null) {
			return false;
		}
		String jpql = "select count(*) from ProductRecommond productRecommond where lower(productRecommond.product.id) = lower(:productId)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("productId", productId).getSingleResult();
		return count > 0;
	}

	@Override
	public ProductRecommond findByProductId(Long productId) {
		ProductRecommond productRecommond = null;
		if (productId == null) {
			String jpql = "select * from ProductRecommond productRecommond where lower(productRecommond.product.id) = lower(:productId)";
			productRecommond = entityManager.createQuery(jpql, ProductRecommond.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("productId", productId).getSingleResult();
		}
		return productRecommond;
	}

}
