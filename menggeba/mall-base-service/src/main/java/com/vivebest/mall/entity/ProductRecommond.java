package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 首页楼层
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_product_recommond")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_product_recommond_sequence")
public class ProductRecommond extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**商品*/
	private Product product;
	
	/**排序*/
	private Long orders;
	
	/**描述*/
	private String description;
	
	private ProductCateIndex productCategoryIndex;

	/**
	 * 获取商品
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * @param product 商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取排序
	 * @return
	 */
	public Long getOrders() {
		return orders;
	}

	/**
	 * 设置排序
	 * @param orders 排序
	 */
	public void setOrders(Long orders) {
		this.orders = orders;
	}

	/**
	 * 获取描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public ProductCateIndex getProductCategoryIndex() {
		return productCategoryIndex;
	}

	public void setProductCategoryIndex(ProductCateIndex productCategoryIndex) {
		this.productCategoryIndex = productCategoryIndex;
	}
	
}
