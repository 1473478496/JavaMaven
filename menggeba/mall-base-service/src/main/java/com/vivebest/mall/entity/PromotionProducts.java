package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

//import org.hibernate.search.annotations.Field;
//import org.hibernate.search.annotations.FieldBridge;
//import org.hibernate.search.annotations.Index;
//import org.hibernate.search.annotations.Indexed;
//import org.hibernate.search.annotations.NumericField;
//import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.common.BigDecimalNumericFieldBridge;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 促销商品
 * 
 * @author junly
 * @version 3.0
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
//@Indexed
//@Similarity(impl = IKSimilarity.class)
@Entity
@Table(name = "gbm_promotion_products")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_promotion_products_sequence")
public class PromotionProducts extends BaseEntity {

	private static final long serialVersionUID = -1307743303786909390L;
	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 10000;
	
	public enum GroupType{
		/**单品团*/
		single,
		
		/**组合团*/
		combination
	}
	// 促销价
	private BigDecimal promotionPrice;
	// 促销萌值
	private Long promotionPricePoint;
	// 最后销售时间
	private Date lastSaleTime;
	// 促销数量
	private Integer promotionQuantity;
	// 已销售数量
	private Integer saleQuantity;
	// 排序
	private Long orders;
	
	/**是否置顶*/
	private Boolean isStick;
	
	/**置顶图片*/
	private String stickImage;
	
	// 促销
	private Promotion promotion;
	// 商品
	private Product product;
	
	/** 订单项 */
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	
	/** 购物车项 */
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	/**一档价格*/
	private BigDecimal groupBuyingPrice1;
	
	/**一档数量*/
	private Long groupBuyingQuantity1;
	
	/**二挡价格*/
	private BigDecimal groupBuyingPrice2;
	
	/**二挡数量*/
	private Long groupBuyingQuantity2;
	
	/**三挡价格*/
	private BigDecimal groupBuyingPrice3;
	
	/**三挡数量*/
	private Long groupBuyingQuantity3;
	
	/**四挡价格*/
	private BigDecimal groupBuyingPrice4;
	
	/**四挡数量*/
	private Long groupBuyingQuantity4;
	
	/**五档价格*/
	private BigDecimal groupBuyingPrice5;
	
	/**五档数量*/
	private Long groupBuyingQuantity5;
	
	/**预约订金*/
	private BigDecimal groupBuyingDeposit;
	
	/**可预约的最大数量*/
	private Long groupBuyingDepositQuantity;
	
	/**团购类型*/
	private GroupType groupType;
	
	/**团购预约*/
	private Set<Booking> bookings = new HashSet<Booking>();
	
	/**
	 * 获取团购预约
	 * @return
	 */
	@OneToMany(mappedBy = "promotionProducts", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	public Set<Booking> getBookings() {
		return bookings;
	}

	/**
	 * 设置团购预约
	 * @param bookings
	 */
	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
	
	/**
	 * 获取一档价格
	 * @return
	 */
	public BigDecimal getGroupBuyingPrice1() {
		return groupBuyingPrice1;
	}

	/**
	 * 设置一档价格
	 * @param groupBuyingPrice1
	 */
	public void setGroupBuyingPrice1(BigDecimal groupBuyingPrice1) {
		this.groupBuyingPrice1 = groupBuyingPrice1;
	}

	/**
	 * 获取一档数量
	 * @return
	 */
	public Long getGroupBuyingQuantity1() {
		return groupBuyingQuantity1;
	}

	/**
	 * 设置一档数量
	 * @param groupBuyingQuantity1
	 */
	public void setGroupBuyingQuantity1(Long groupBuyingQuantity1) {
		this.groupBuyingQuantity1 = groupBuyingQuantity1;
	}

	/**
	 * 获取二档价格
	 * @return
	 */
	public BigDecimal getGroupBuyingPrice2() {
		return groupBuyingPrice2;
	}

	/**
	 * 设置二档价格
	 * @param groupBuyingPrice2
	 */
	public void setGroupBuyingPrice2(BigDecimal groupBuyingPrice2) {
		this.groupBuyingPrice2 = groupBuyingPrice2;
	}

	/**
	 * 获取二挡数量
	 * @return
	 */
	public Long getGroupBuyingQuantity2() {
		return groupBuyingQuantity2;
	}

	/**
	 * 设置二挡数量
	 * @param groupBuyingQuantity2
	 */
	public void setGroupBuyingQuantity2(Long groupBuyingQuantity2) {
		this.groupBuyingQuantity2 = groupBuyingQuantity2;
	}

	/**
	 * 获取三挡价格
	 * @return
	 */
	public BigDecimal getGroupBuyingPrice3() {
		return groupBuyingPrice3;
	}

	/**
	 * 设置三挡价格
	 * @param groupBuyingPrice3
	 */
	public void setGroupBuyingPrice3(BigDecimal groupBuyingPrice3) {
		this.groupBuyingPrice3 = groupBuyingPrice3;
	}

	/**
	 * 获取三挡数量
	 * @return
	 */
	public Long getGroupBuyingQuantity3() {
		return groupBuyingQuantity3;
	}

	/**
	 * 设置三挡数量
	 * @param groupBuyingQuantity3
	 */
	public void setGroupBuyingQuantity3(Long groupBuyingQuantity3) {
		this.groupBuyingQuantity3 = groupBuyingQuantity3;
	}

	/**
	 * 获取四挡价格
	 * @return
	 */
	public BigDecimal getGroupBuyingPrice4() {
		return groupBuyingPrice4;
	}

	/**
	 * 设置四挡价格
	 * @param groupBuyingPrice4
	 */
	public void setGroupBuyingPrice4(BigDecimal groupBuyingPrice4) {
		this.groupBuyingPrice4 = groupBuyingPrice4;
	}

	/**
	 * 获取四挡数量
	 * @return
	 */
	public Long getGroupBuyingQuantity4() {
		return groupBuyingQuantity4;
	}

	/**
	 * 设置四挡数量
	 * @param groupBuyingQuantity4
	 */
	public void setGroupBuyingQuantity4(Long groupBuyingQuantity4) {
		this.groupBuyingQuantity4 = groupBuyingQuantity4;
	}

	/**
	 * 获取五档价格
	 * @return
	 */
	public BigDecimal getGroupBuyingPrice5() {
		return groupBuyingPrice5;
	}

	/**
	 * 设置五档价格
	 * @param groupBuyingPrice5
	 */
	public void setGroupBuyingPrice5(BigDecimal groupBuyingPrice5) {
		this.groupBuyingPrice5 = groupBuyingPrice5;
	}

	/**
	 * 获取五档数量
	 * @return
	 */
	public Long getGroupBuyingQuantity5() {
		return groupBuyingQuantity5;
	}

	/**
	 * 设置 五档数量
	 * @param groupBuyingQuantity5
	 */
	public void setGroupBuyingQuantity5(Long groupBuyingQuantity5) {
		this.groupBuyingQuantity5 = groupBuyingQuantity5;
	}

	/**
	 * 获取预约订金
	 * @return
	 */
	public BigDecimal getGroupBuyingDeposit() {
		return groupBuyingDeposit;
	}

	/**
	 * 设置预约订金
	 * @param groupBuyingDeposit
	 */
	public void setGroupBuyingDeposit(BigDecimal groupBuyingDeposit) {
		this.groupBuyingDeposit = groupBuyingDeposit;
	}

	/**
	 * 获取可预约的最大数量
	 * @return
	 */
	public Long getGroupBuyingDepositQuantity() {
		return groupBuyingDepositQuantity;
	}

	/**
	 * 设置可预约的最大数量
	 * @param groupBuyingDepositQuantity
	 */
	public void setGroupBuyingDepositQuantity(Long groupBuyingDepositQuantity) {
		this.groupBuyingDepositQuantity = groupBuyingDepositQuantity;
	}

	/**
	 * 获取团购类型
	 * @return
	 */
	public GroupType getGroupType() {
		return groupType;
	}

	/**
	 * 设置团购类型
	 * @param groupType
	 */
	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	/**
	 * 获取是否置顶
	 * @return
	 */
	public Boolean getIsStick() {
		return isStick;
	}

	/**
	 * 设置置顶1-置顶，0-否
	 * @param isStick
	 */
	public void setIsStick(Boolean isStick) {
		this.isStick = isStick;
	}

	/**
	 * 获取置顶图片路径
	 * @return
	 */
	public String getStickImage() {
		return stickImage;
	}

	/**
	 * 设置置顶图片路径
	 * @param stickImage
	 */
	public void setStickImage(String stickImage) {
		this.stickImage = stickImage;
	}

	/**
	 * 获取促销
	 * 
	 * @return
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion", nullable = false)
	public Promotion getPromotion() {
		return promotion;
	}

	/**
	 * 设置促销
	 * 
	 * @param promotion
	 *            促销
	 */
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	/**
	 * 获取商品
	 * 
	 * @return
	 */
	@JsonProperty
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
	 * 获取促销价格
	 * 
	 * @return
	 */
	@JsonProperty
//	@Field(store = Store.YES, index = Index.NO)
//	@NumericField
//	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	/**
	 * 设置促销价格
	 * 
	 * @param promotionPrice
	 *            促销价格
	 */
	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	/**
	 * 获取促销萌值
	 * 
	 * @return
	 */
	@JsonProperty
//	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	public Long getPromotionPricePoint() {
		return promotionPricePoint;
	}

	/**
	 * 设置促销萌值
	 * 
	 * @param promotionPricePoint
	 *            促销萌值
	 */
	public void setPromotionPricePoint(Long promotionPricePoint) {
		this.promotionPricePoint = promotionPricePoint;
	}

	/**
	 * 获取促销最后时间
	 * 
	 * @return
	 */
	@Column(nullable = false)
	public Date getLastSaleTime() {
		return lastSaleTime;
	}

	/**
	 * 设置促销最后时间
	 * 
	 * @param lastSaleTime
	 *            促销最后时间
	 */
	public void setLastSaleTime(Date lastSaleTime) {
		this.lastSaleTime = lastSaleTime;
	}

	/**
	 * 获取促销数量
	 * 
	 * @return
	 */
	@JsonProperty
	@NotNull
	/*@Min(1)*/
	@Column(nullable = false)
	public Integer getPromotionQuantity() {
		return promotionQuantity;
	}

	/**
	 * 设置促销数量
	 * 
	 * @param promotionQuantity
	 *            促销数量
	 */

	public void setPromotionQuantity(Integer promotionQuantity) {
		this.promotionQuantity = promotionQuantity;
	}

	/**
	 * 获取促销已卖出数量
	 * 
	 * @return
	 */
	@JsonProperty
//	@Field(store = Store.YES, index = Index.NO)
	@Min(0)
	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	/**
	 * 设置促销已卖出的数量
	 * 
	 * @param saleQuantity
	 *            卖出的数量
	 */
	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	/**
	 * 获取排序
	 * 
	 * @return
	 */
	public Long getOrders() {
		return orders;
	}

	/**
	 * 设置排序
	 * 
	 * @param orders
	 *            排序
	 */
	public void setOrders(Long orders) {
		this.orders = orders;
	}

	/**
	 * 获取购物车项
	 * 
	 * @return 购物车项
	 */
	@OneToMany(mappedBy = "promotionProducts", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * 设置购物车项
	 * 
	 * @param cartItems
	 *            购物车项
	 */
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	@OneToMany(mappedBy = "promotionProducts", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<CartItem> getCartItems() {
		return cartItems;
	}

	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	/**
	 * 判断促销商品是否销售完了
	 * 
	 * @return
	 */
	@JsonProperty
	@Transient
	public Boolean getIsSaleAll() {
		if (getPromotionQuantity() == null) {
			return false;
		}
		if (getSaleQuantity() != null) {
			return getSaleQuantity() >= getPromotionQuantity();
		} else {
			return false;
		}
	}

	/**
	 * 获取促销商品剩余数量
	 * 
	 * @param quantity
	 * @return
	 */
	@JsonProperty
	@Transient
	public Integer getSurplusCount() {
		if (getPromotionQuantity()!=null) {
			if (getSaleQuantity()!=null) {
				return (getPromotionQuantity() - getSaleQuantity());
			}else {
				return getPromotionQuantity();
			}
		}else {
			return MAX_QUANTITY;
		}
	}
}
