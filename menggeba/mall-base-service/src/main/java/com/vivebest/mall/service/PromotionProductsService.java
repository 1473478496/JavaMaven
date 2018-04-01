package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * service 促销商品
 * 
 * @author junly
 *
 */
public interface PromotionProductsService extends BaseService<PromotionProducts, Long> {

	/**
	 * 根据促销Id查找促销商品分页
	 * 
	 * @param promotion
	 *            促销
	 * @param pageable
	 *            分页信息
	 * @return
	 */
	Page<PromotionProducts> findPage(Long promotionId, Pageable pageable);

	/**
	 * @param product
	 * @param pageable
	 * @return
	 */
	Page<PromotionProducts> findPage(Product product, Pageable pageable);

	/**
	 * 通过促销id查询促销商品
	 * 
	 * @param id
	 * @return
	 */
	public List<PromotionProducts> findPtionProdByPtionId(Long id);
	
	
	/**
	 * 查找置顶促销商品
	 * 
	 * @param id
	 * @return
	 */
	public List<PromotionProducts> findPtionProdIsStick(Promotion  promotion,Boolean isCheap,Boolean isStick);

	
	
	public PromotionProducts findByProductId(Long id,Promotion.Category category);

	/**
	 * 验证
	 * @param productId 商品id 
	 */
	boolean checkProduct(Long productId);

	/**
	 * 置顶
	 * @param orders 排序
	 * @return
	 */
	List<PromotionProducts> findIsStick(List<Order> orders);

	/**
	 * 天天特价
	 * @param pageable 分页信息
	 * @return
	 */
	Object findSpecialPage(Pageable pageable);

	/**
	 * 通过商品id查询
	 * @param productId
	 * @return
	 */
	PromotionProducts findByProductId(Long productId);
}
