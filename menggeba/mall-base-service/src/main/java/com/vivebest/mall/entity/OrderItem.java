/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.Index;
//import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;
import com.vivebest.mall.entity.Promotion.Category;

/**
 * Entity - 订单项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_order_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_order_item_sequence")
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = -4999926022604479334L;

	/** 商品编号 */
	private String sn;

	/** 商品名称 */
	private String name;

	/** 商品全称 */
	private String fullName;

	/** 商品价格 */
	private BigDecimal price;

	/** 商品所需积分 */
	private Long pricePoint;
	/**支付金额*/
	private BigDecimal payPrice;
	/**支付萌值*/
	private Long payPricePoint;

	/** 商品重量 */
	private BigDecimal weight;

	/** 商品缩略图 */
	private String thumbnail;

	/** 是否为赠品 */
	private Boolean isGift;

	/** 数量 */
	private Integer quantity;

	/** 已发货数量 */
	private Integer shippedQuantity;

	/** 已退货数量 */
	private Integer returnQuantity;

	/** 商品 */
	private Product product;

	/** 订单 */
	private Order order;
	
	/** 促销商品 */
	private PromotionProducts promotionProducts;
	
	/** 优惠券分摊比例*/
	private BigDecimal  couponDivide;

	/**
	 * 获取促销商品
	 * 
	 * @return 促销商品
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	public PromotionProducts getPromotionProducts() {
		return promotionProducts;
	}

	/**
	 * 设置促销商品
	 * 
	 * @param promotion
	 *            促销商品
	 */
	public void setPromotionProducts(PromotionProducts promotionProducts) {
		this.promotionProducts = promotionProducts;
	}
	/**
	 * 获取商品编号
	 * 
	 * @return 商品编号
	 */
	@JsonProperty
	@NotEmpty
	@Column(nullable = false, updatable = false)
	public String getSn() {
		return sn;
	}

	/**
	 * 获取支付金额
	 * @return
	 */
	@JsonProperty
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getPayPrice() {
		return payPrice;
	}

	/**
	 * 设置支付金额
	 * @param payPrice
	 */
	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	/**
	 * 获取支付萌值
	 * @return
	 */
	@Min(0)
	@Column(name = "pay_price_point")
	public Long getPayPricePoint() {
		return payPricePoint;
	}

	/**
	 * 设置支付萌值
	 * @param payPricePoint
	 */
	
	public void setPayPricePoint(Long payPricePoint) {
		this.payPricePoint = payPricePoint;
	}

	/**
	 * 设置商品编号
	 * 
	 * @param sn
	 *            商品编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取商品名称
	 * 
	 * @return 商品名称
	 */
	@JsonProperty
	@Column(nullable = false, updatable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 *            商品名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取商品全称
	 * 
	 * @return 商品全称
	 */
	@JsonProperty
	@Column(nullable = false, updatable = false)
	public String getFullName() {
		return fullName;
	}

	/**
	 * 设置商品全称
	 * 
	 * @param fullName
	 *            商品全称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	@JsonProperty
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置商品价格
	 * 
	 * @param price
	 *            商品价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 商品所需积分
	 * 
	 * @return 购买积分
	 */
//	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	@Column(name = "price_point", nullable = false)
	public Long getPricePoint() {
		return pricePoint;
	}

	/**
	 * 设置商品所需积分
	 * 
	 * @param pricePoint
	 *            购买积分
	 */
	public void setPricePoint(Long pricePoint) {
		this.pricePoint = pricePoint;
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@JsonProperty
	@Column(updatable = false)
	public BigDecimal getWeight() {
		return weight;
	}

	/**
	 * 设置商品重量
	 * 
	 * @param weight
	 *            商品重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 获取商品缩略图
	 * 
	 * @return 商品缩略图
	 */
	@JsonProperty
	@Column(updatable = false)
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * 设置商品缩略图
	 * 
	 * @param thumbnail
	 *            商品缩略图
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 获取是否为赠品
	 * 
	 * @return 是否为赠品
	 */
	@JsonProperty
	@Column(nullable = false, updatable = false)
	public Boolean getIsGift() {
		return isGift;
	}

	/**
	 * 设置是否为赠品
	 * 
	 * @param isGift
	 *            是否为赠品
	 */
	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@JsonProperty
	@NotNull
	@Min(1)
	@Max(10000)
	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置数量
	 * 
	 * @param quantity
	 *            数量
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取已发货数量
	 * 
	 * @return 已发货数量
	 */
	@Column(nullable = false)
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	/**
	 * 设置已发货数量
	 * 
	 * @param shippedQuantity
	 *            已发货数量
	 */
	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	/**
	 * 获取已退货数量
	 * 
	 * @return 已退货数量
	 */
	@Column(nullable = false)
	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	/**
	 * 设置已退货数量
	 * 
	 * @param returnQuantity
	 *            已退货数量
	 */
	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders", nullable = false, updatable = false)
	public Order getOrder() {
		return order;
	}

	/**
	 * 设置订单
	 * 
	 * @param order
	 *            订单
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * 获取商品总重量
	 * 
	 * @return 商品总重量
	 */
	@JsonProperty
	@Transient
	public BigDecimal getTotalWeight() {
		if (getWeight() != null && getQuantity() != null) {
			return getWeight().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取小计：商品原价*购买数量
	 * 
	 * @return 小计
	 */
	@JsonProperty
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}
	/**
	 * 获取积分小计：商品原积分价*购买数量
	 * 
	 * @return 积分小计
	 */
	@JsonProperty
	@Transient
	public Long getSubtotalPoint() {
		if (getPricePoint() != null && getQuantity() != null) {
			return getPricePoint()*getQuantity();
		} else {
			return 0L;
		}
	}
	 /**
	  * 优惠券到每笔产品分摊价格
	  * @return
	  */
	public BigDecimal getCouponDivide() {
		return couponDivide;
	}

	public void setCouponDivide(BigDecimal couponDivide) {
		this.couponDivide = couponDivide;
	}
    

	
 
	@Transient
	/**
	 *  获取是否可参与活动的优惠券
	 * @return
	 */
	public 	Boolean  getPromotionCouponAllowed() {
	 
		Boolean isallowed= true;
				
		if (getPromotionProducts() != null&& getPromotionProducts().getPromotion()!=null) {
		 
			  Boolean  IsExpired=false;
			 if(getPromotionProducts().getPromotion().getCategory()==Category.booking) //团购活动取
			 {
			    IsExpired= getPromotionProducts().getPromotion().getEndDate()!= null && new Date().after(getPromotionProducts().getPromotion().getPurEndDate());
			 }
			 else
			 {
				 IsExpired= getPromotionProducts().getPromotion().getEndDate()!= null && new Date().after(getPromotionProducts().getPromotion().getEndDate());
			 }
			if(IsExpired) //过期表示，恢复原价不参与优惠活动
				 isallowed=true;			 
			else
			{
		        
				isallowed=getPromotionProducts().getPromotion().getIsCouponAllowed()!=null&&getPromotionProducts().getPromotion().getIsCouponAllowed(); //为空的情况，即为不可用
			}
			  
			
		}
		
		return isallowed;
	}
	 
	 

}