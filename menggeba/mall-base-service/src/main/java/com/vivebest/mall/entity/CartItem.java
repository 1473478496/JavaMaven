/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;
import com.vivebest.mall.entity.Booking.Status;

/**
 * Entity - 购物车项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_cart_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_cart_item_sequence")
public class CartItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2979296789363163144L;

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 10000;

	/** 数量 */
	private Integer quantity;

	/** 商品 */
	private Product product;

	/** 购物车 */
	private Cart cart;

	/** 临时商品价格 */
	private BigDecimal tempPrice;

	/** 临时赠送积分 */
	private Long tempPoint;

	/** 促销商品 */
	private PromotionProducts promotionProducts;
	
	/**类型*/
	private String cartType;

	/**
	 * 获取类型
	 * @return
	 */
	public String getCartType() {
		return cartType;
	}

	/**
	 * 设置类型
	 * @param cartType
	 */
	public void setCartType(String cartType) {
		this.cartType = cartType;
	}

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
	 * @param promotionProducts
	 *            促销商品
	 */
	public void setPromotionProducts(PromotionProducts promotionProducts) {
		this.promotionProducts = promotionProducts;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@JsonProperty
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
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
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
	 * 获取购物车
	 * 
	 * @return 购物车
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Cart getCart() {
		return cart;
	}

	/**
	 * 设置购物车
	 * 
	 * @param cart
	 *            购物车
	 */
	public void setCart(Cart cart) {
		this.cart = cart;
	}

	/**
	 * 获取临时商品价格
	 * 
	 * @return 临时商品价格
	 */
	@JsonProperty
	@Transient
	public BigDecimal getTempPrice() {
		if (tempPrice == null) {
			return getSubtotal();
		}
		return tempPrice;
	}

	/**
	 * 设置临时商品价格
	 * 
	 * @param tempPrice
	 *            临时商品价格
	 */
	@Transient
	public void setTempPrice(BigDecimal tempPrice) {
		this.tempPrice = tempPrice;
	}

	/**
	 * 获取临时赠送积分
	 * 
	 * @return 临时赠送积分
	 */
	@JsonProperty
	@Transient
	public Long getTempPoint() {
		if (tempPoint == null) {
			return getPoint();
		}
		return tempPoint;
	}

	/**
	 * 设置临时赠送积分
	 * 
	 * @param tempPoint
	 *            临时赠送积分
	 */
	@Transient
	public void setTempPoint(Long tempPoint) {
		this.tempPoint = tempPoint;
	}

	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@JsonProperty
	@Transient
	public long getPoint() {
		if (getProduct() != null && getProduct().getPoint() != null && getQuantity() != null) {
			return getProduct().getPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	/**
	 * 获取积分价格
	 * 
	 * @return 积分价格
	 */
	@JsonProperty
	@Transient
	public Long getPricePoint() {

		if (getPromotionProducts() != null && getPromotionProducts().getPromotionPricePoint() != null
				&& getPromotionProducts().getPromotion().getIsOnActivity()) {
			return getPromotionProducts().getPromotionPricePoint();
		} else {
			if(getPromotionProducts() != null && !getPromotionProducts().getBookings().isEmpty()){
				return getPromotionProducts().getPromotionPricePoint();
			}else if (getProduct() != null && getProduct().getPricePoint() != null && getQuantity() != null) {
				return getProduct().getPricePoint();
			} else {
				return 0L;
			}
		}
	}

	/**
	 * 获取积分总价格
	 * 
	 * @return 积分价格
	 */
	@JsonProperty
	@Transient
	public Long getSubPricePoint() {

		if (getPromotionProducts() != null && getPromotionProducts().getPromotionPricePoint() != null
				&& getPromotionProducts().getPromotion().getIsOnActivity()) {
			return getPromotionProducts().getPromotionPricePoint() * getQuantity();
		} else {
			if(getPromotionProducts() != null && !getPromotionProducts().getBookings().isEmpty()){
				return getPromotionProducts().getPromotionPricePoint() * getQuantity();
			}else if (getProduct() != null && getProduct().getPricePoint() != null && getQuantity() != null) {
				return getProduct().getPricePoint() * getQuantity();
			} else {
				return 0L;
			}
		}
	}

	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@JsonProperty
	@Transient
	public BigDecimal getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return getProduct().getWeight().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取价格
	 * 
	 * @return 价格
	 */
	@JsonProperty
	@Transient
	public BigDecimal getPrice() {

		if (getPromotionProducts() != null && getPromotionProducts().getPromotionPrice() != null
				&& getPromotionProducts().getPromotion().getIsOnActivity()) {
			return getPromotionProducts().getPromotionPrice();
		} else {
			if(getPromotionProducts() != null && !getPromotionProducts().getBookings().isEmpty()){
				BigDecimal price = new BigDecimal(0);
				Long count = 0L;
				for (Booking booking : getPromotionProducts().getBookings()) {
					if(booking.getStatus() != Status.nondirectPurchase && booking.getStatus() != Status.nonPayment){
						count++;
					}
				}
				if("booking".equals(getCartType())){
					if(count <= getPromotionProducts().getGroupBuyingQuantity1()){
						price = getPromotionProducts().getGroupBuyingPrice1().subtract(getPromotionProducts().getGroupBuyingDeposit());
					}else if(getPromotionProducts().getGroupBuyingQuantity1() < count && count <= getPromotionProducts().getGroupBuyingQuantity2()){
						price = getPromotionProducts().getGroupBuyingPrice2().subtract(getPromotionProducts().getGroupBuyingDeposit());
					}else if(getPromotionProducts().getGroupBuyingQuantity2() < count && count <= getPromotionProducts().getGroupBuyingQuantity3()){
						price = getPromotionProducts().getGroupBuyingPrice3().subtract(getPromotionProducts().getGroupBuyingDeposit());
					}else if(getPromotionProducts().getGroupBuyingQuantity3() < count && count <= getPromotionProducts().getGroupBuyingQuantity4()){
						price = getPromotionProducts().getGroupBuyingPrice4().subtract(getPromotionProducts().getGroupBuyingDeposit());
					}else if(getPromotionProducts().getGroupBuyingQuantity4() < count){
						price = getPromotionProducts().getGroupBuyingPrice5().subtract(getPromotionProducts().getGroupBuyingDeposit());
					}
				}else if("direct".equals(getCartType())){
					if(count <= getPromotionProducts().getGroupBuyingQuantity1()){
						price = getPromotionProducts().getGroupBuyingPrice1();
					}else if(getPromotionProducts().getGroupBuyingQuantity1() < count && count <= getPromotionProducts().getGroupBuyingQuantity2()){
						price = getPromotionProducts().getGroupBuyingPrice2();
					}else if(getPromotionProducts().getGroupBuyingQuantity2() < count && count <= getPromotionProducts().getGroupBuyingQuantity3()){
						price = getPromotionProducts().getGroupBuyingPrice3();
					}else if(getPromotionProducts().getGroupBuyingQuantity3() < count && count <= getPromotionProducts().getGroupBuyingQuantity4()){
						price = getPromotionProducts().getGroupBuyingPrice4();
					}else if(getPromotionProducts().getGroupBuyingQuantity4() < count){
						price = getPromotionProducts().getGroupBuyingPrice5();
					}
				}
				return price;
			}else if (getProduct() != null && getProduct().getPrice() != null) {
				// Setting setting = SettingUtils.get();
				// if (getCart() != null && getCart().getMember() != null
				// && getCart().getMember().getMemberRank() != null) {
				// MemberRank memberRank =
				// getCart().getMember().getMemberRank();
				// Map<MemberRank, BigDecimal> memberPrice =
				// getProduct().getMemberPrice();
				// if (memberPrice != null && !memberPrice.isEmpty()) {
				// if (memberPrice.containsKey(memberRank)) {
				// return setting.setScale(memberPrice.get(memberRank));
				// }
				// }
				// if (memberRank.getScale() != null) {
				// return setting
				// .setScale(getProduct().getPrice().multiply(new
				// BigDecimal(memberRank.getScale())));
				// }
				// }
				// return setting.setScale(getProduct().getPrice());
				return getProduct().getPrice();
			} else {
				return new BigDecimal(0);
			}
		}
	}

	//
	// /**
	// * 获取折扣
	// *
	// * @return 折扣
	// */
	// @Transient
	// public BigDecimal getDiscount() {
	// BigDecimal tempPrice = this.getTempPrice();
	// BigDecimal price = this.getPrice();
	// BigDecimal discount = new BigDecimal(0);
	//
	// if(getQuantity() != null&&tempPrice!=null){
	// //Setting setting = SettingUtils.get();
	// //discount = setting.setScale(originalPrice.subtract(currentPrice));
	// discount=(price.subtract(tempPrice)).multiply(new
	// BigDecimal(getQuantity()));
	//
	// }
	// return (discount.compareTo(new BigDecimal(0)) > 0 ? discount : new
	// BigDecimal(0));
	// }
	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@JsonProperty
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	/**
	 * 获取是否库存不足
	 * 
	 * @return 是否库存不足
	 */
	@JsonProperty
	@Transient
	public boolean getIsLowStock() {
		if (getQuantity() != null) {
			if (getPromotionProducts() != null && getPromotionProducts().getPromotion().getIsOnActivity()) {
				if (getQuantity()>getPromotionProducts().getSurplusCount()) {
					return true;
				}
			} else {
				if (getProduct() != null && getProduct().getStock() != null
						&& getQuantity() > getProduct().getAvailableStock()) {
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@JsonProperty
	@Transient
	public void add(int quantity) {
		if (quantity > 0) {
			if (getQuantity() != null) {
				setQuantity(getQuantity() + quantity);
			} else {
				setQuantity(quantity);
			}
		}
	}

}