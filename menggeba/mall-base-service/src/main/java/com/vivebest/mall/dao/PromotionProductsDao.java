package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * dao 促销商品
 * 
 * @author junly
 *
 */
public interface PromotionProductsDao extends BaseDao<PromotionProducts, Long> {

	/**
	 * 通过促销id查询促销商品
	 * 
	 * @param id
	 * @return
	 */
	public List<PromotionProducts> findPtionProdByPtionId(Long id);

	/**
	 * @param product
	 * @param pageable
	 * @return
	 */
	Page<PromotionProducts> findPage(Product product, Pageable pageable);

	/**
	 * 根据促销查找促销商品分页
	 * 
	 * @param promotion
	 *            促销
	 * @param pageable
	 *            分页信息
	 * @return
	 */
	Page<PromotionProducts> findPage(Promotion promotion, Pageable pageable);

	/**
	 * 查找置顶促销商品
	 * 
	 * @param id
	 * @return
	 */
	public List<PromotionProducts> findPtionProdIsStick(Promotion  promotion,Boolean isCheap,Boolean isStick);
	
	/**
	 * 通过productId查找promotionProducts
	 * @param id 商品id
	 * @return
	 */
	List<PromotionProducts>findByProductId(Long id);

	/**
	 * 验证
	 * @param productId 商品id 
	 */
	public boolean checkProduct(Long productId);

	/**
	 * 置顶
	 * @param orders 排序
	 * @return
	 */
	public List<PromotionProducts> findIsStick(List<Order> orders);

	/**
	 * 天天特价
	 * @param pageable 分页信息
	 * @return
	 */
	public Object findSpecialPage(Pageable pageable);
	
	//public List<PromotionProducts> findPtionProdIsCheap(Long id);

}
