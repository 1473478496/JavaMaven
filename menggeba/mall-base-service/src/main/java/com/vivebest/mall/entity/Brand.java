/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_brand")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_brand_sequence")
public class Brand extends OrderEntity {

	private static final long serialVersionUID = -6109590619136943215L;

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/brand/content";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";

	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	}

	/** 名称 */
	private String name;

	/** 类型 */
	private Type type;

	/** logo */
	private String logo;
	
	/**
	 * 微商城URL
	 */
	private String murl;
	
	/**
	 * 公众号
	 */
	private String openurl;

	
	/**
	 * wap 品牌url
	 */
	private String wurl;
	
	/**
	 * pc 品牌url
	 */
	private String purl;

	private String color;
	
	/** picture */
	private String picture;

	/** 网址 */
	private String url;

	/** 介绍 */
	private String introduction;
	
	/** 是否推荐 */
	private Boolean isTop;
	
	/** 是否热门 */
	private Boolean isPopularity;
	
	private Integer sorts;
	
	
	/**
	 * 上级分类
	 */
	private Brand parent;
	
	/**
	 * 下级分类
	 */
	private Set<Brand> children = new HashSet<Brand>();

	/** 商品 */
	private Set<Product> products = new HashSet<Product>();

	/** 商品分类 */
	private Set<ProductCategory> productCategories = new HashSet<ProductCategory>();

	/** 促销 */
	private Set<Promotion> promotions = new HashSet<Promotion>();
	
	
	/** 优惠券*/
	private Set<Coupon> coupons = new HashSet<Coupon>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull
	@Column(nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * 获取logo
	 * 
	 * @return logo
	 */
	@Length(max = 200)
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置logo
	 * 
	 * @param logo
	 *            logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取picture
	 * 
	 * @return picture
	 */
	@Length(max = 200)
	public String getPicture() {
		return picture;
	}

	/**
	 * 设置picture
	 * 
	 * @param picture
	 *            
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * 获取网址
	 * 
	 * @return 网址
	 */
	@Length(max = 200)
	public String getUrl() {
		return url;
	}

	/**
	 * 设置网址
	 * 
	 * @param url
	 *            网址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取介绍
	 * 
	 * @return 介绍
	 */
	@Lob
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * 设置介绍
	 * 
	 * @param introduction
	 *            介绍
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
	@OrderBy("order asc")
	public Set<ProductCategory> getProductCategories() {
		return productCategories;
	}

	/**
	 * 设置商品分类
	 * 
	 * @param productCategories
	 *            商品分类
	 */
	public void setProductCategories(Set<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	/**
	 * 获取促销
	 * 
	 * @return 促销
	 */
	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
	public Set<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotions
	 *            促销
	 */
	public void setPromotions(Set<Promotion> promotions) {
		this.promotions = promotions;
	}
	
	
	/**
	 * 获取优惠券
	 * 
	 * @return 优惠券
	 */
	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
	public Set<Coupon> getCoupon() {
		return coupons;
	}

	/**
	 * 设置 优惠券
	 * 
	 * @param coupons
	 *           优惠券
	 */
	public void setCoupon(Set<Coupon> coupons) {
		this.coupons = coupons;
	}
	

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getId() != null) {
			return PATH_PREFIX + "/" + getId() + PATH_SUFFIX;
		}
		return null;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<Product> products = getProducts();
		if (products != null) {
			for (Product product : products) {
				product.setBrand(null);
			}
		}
		Set<ProductCategory> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategory productCategory : productCategories) {
				productCategory.getBrands().remove(this);
			}
		}
		Set<Promotion> promotions = getPromotions();
		if (promotions != null) {
			for (Promotion promotion : promotions) {
				promotion.getBrands().remove(this);
			}
		}
	}
	
	/**
	 * @return 上级分类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Brand getParent() {
		return parent;
	}

	public void setParent(Brand parent) {
		this.parent = parent;
	}
	/**
	 * @return 下级分类
	 */
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Brand> getChildren() {
		return children;
	}

	public void setChildren(Set<Brand> children) {
		this.children = children;
	}
	
	/**
	 * 获取是否推荐
	 * @return
	 */
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否推荐
	 * @param isTop
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取是否热门
	 * @return
	 */
	public Boolean getIsPopularity() {
		return isPopularity;
	}

	/**
	 * 设置是否热门
	 * @param isPopularity
	 */
	public void setIsPopularity(Boolean isPopularity) {
		this.isPopularity = isPopularity;
	}

	public String getMurl() {
		return murl;
	}

	public void setMurl(String murl) {
		this.murl = murl;
	}

	public String getOpenurl() {
		return openurl;
	}

	public void setOpenurl(String openurl) {
		this.openurl = openurl;
	}

	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
	public String getWurl() {
		return wurl;
	}

	public void setWurl(String wurl) {
		this.wurl = wurl;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	

}