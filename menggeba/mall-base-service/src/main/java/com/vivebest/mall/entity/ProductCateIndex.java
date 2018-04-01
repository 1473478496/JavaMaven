package com.vivebest.mall.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * ProductCateIndex - 首页显示商品
 * <p>
 * 
 * @version 1.0.0,2015年7月25日
 * @author junly
 * @since 1.0.0
 */
@Entity
@Table(name = "gbm_product_category_index")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_product_category_index_sequence")
public class ProductCateIndex extends OrderEntity {

	private static final long serialVersionUID = -6791965656081688785L;

	/** 商品分类 */
	private ProductCategory productCategory;

	/** 名称 */
	private String name;

	/** 描述 */
	private String description;

	/** 等级分类 */
	private String indexType;
	
	/** 广告*/
	private Ad ad;
	
	private List<ProductRecommond> productRecommonds = new ArrayList<ProductRecommond>();

	/** 获取名称 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/** 设置名称 */
	public void setName(String name) {
		this.name = name;
	}

	/** 获取描述 */
	public String getDescription() {
		return description;
	}

	/** 设置描述 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 获取等级分类 */
	public String getIndexType() {
		return indexType;
	}

	/** 设置等级分类 */
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	/** 设置商品分类 */
	@OneToOne(fetch = FetchType.LAZY)
	public ProductCategory getProductCategory() {
		return productCategory;
	}

	/** 设置商品分类 */
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	/** 获取广告*/
	@ManyToOne(fetch = FetchType.LAZY)
	public Ad getAd() {
		return ad;
	}

	/** 设置广告*/
	public void setAd(Ad ad) {
		this.ad = ad;
	}

	@OneToMany(mappedBy = "productCategoryIndex", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<ProductRecommond> getProductRecommonds() {
		return productRecommonds;
	}

	public void setProductRecommonds(List<ProductRecommond> productRecommonds) {
		this.productRecommonds = productRecommonds;
	}
	
}
