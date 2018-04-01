package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.PromotionDao;
import com.vivebest.mall.dao.PromotionProductsDao;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.service.PromotionProductsService;

/**
 * serviceImpl 促销商品
 * 
 * @author junly
 *
 */
@Service("promotionProductsServiceImpl")
public class PromotionProductsServiceImpl extends BaseServiceImpl<PromotionProducts, Long>
		implements PromotionProductsService {
	@Resource(name = "promotionProductsDaoImpl")
	private PromotionProductsDao promotionProductsDao;

	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;

	@Resource(name = "promotionProductsDaoImpl")
	public void setBaseDao(PromotionProductsDao promotionProductsDao) {
		super.setBaseDao(promotionProductsDao);
	}

	@Override
	public Page<PromotionProducts> findPage(Product product, Pageable pageable) {
		return promotionProductsDao.findPage(product, pageable);
	}

	@Override
	public Page<PromotionProducts> findPage(Long promotionId, Pageable pageable) {
		if (promotionDao.find(promotionId) != null) {
			return promotionProductsDao.findPage(promotionDao.find(promotionId), pageable);
		} else {
			return null;
		}
	}

	public List<PromotionProducts> findPtionProdByPtionId(Long id) {
		return promotionProductsDao.findPtionProdByPtionId(id);
	}

	public List<PromotionProducts> findPtionProdIsStick(Promotion  promotion,Boolean isCheap,Boolean isStick) {
		return promotionProductsDao.findPtionProdIsStick(promotion,isCheap,isStick);
	}

	public PromotionProducts findByProductId(Long id, Promotion.Category category) {
		List<PromotionProducts> promotionProducts = promotionProductsDao.findByProductId(id);
		PromotionProducts promoProd = null;
		if (promotionProducts != null) {
			for (PromotionProducts promotionProduct : promotionProducts) {
				Promotion promo=promotionProduct.getPromotion();
				List<Promotion> promotions = promotionDao.findByCurrent(promo.getCategory());
				if (promotions!=null&&promotions.contains(promo)) {
					promoProd = promotionProduct;
					break;
				}
			}
		}
		return promoProd;
	}

	@Override
	public boolean checkProduct(Long productId) {
		return promotionProductsDao.checkProduct(productId);
	}

	@Override
	public List<PromotionProducts> findIsStick(List<Order> orders) {
		return promotionProductsDao.findIsStick(orders);
	}

	@Override
	public Object findSpecialPage(Pageable pageable) {
		return promotionProductsDao.findSpecialPage(pageable);
	}

	@Override
	public PromotionProducts findByProductId(Long productId) {
		List<PromotionProducts> promotionProducts = promotionProductsDao.findByProductId(productId);
		if (promotionProducts != null) {
			for (PromotionProducts promotionProduct : promotionProducts) {
				return promotionProduct;
			}
		}
		return null;
	}
}
