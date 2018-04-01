package com.vivebest.mall.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 团购预约
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_booking")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_booking_sequence")
public class Booking extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	/**预约类型*/
	public enum BookingType{
		
		/**团购预约*/
		groupPurchase
		
	}
	
	/**状态*/
	public enum Status{
		
		/**预约未付款*/
		nonPayment,
		
		/**已预约*/
		payment,
		
		/**已购买*/
		Purchased,
		
		/**直接购买未付款*/
		nondirectPurchase,
		
		/**直接购买*/
		directPurchase,
		
		/**已结束*/
		hasEnded
		
	}
	
	/**预约类型*/
	private BookingType bookingType;
	
	/**预约会员*/
	private Member member;
	
	/**预约活动*/
	private Promotion promotion;
	
	/**团购活动商品*/
	private PromotionProducts promotionProducts;
	
	/**预约商品*/
	private Product product;
	
	/**预约订金*/
	private BigDecimal amount;
	
	/**预约商品价格*/
	private BigDecimal price;
	
	/**预约订金的订单*/
	private String paymentOrder;
	
	/**购买商品的订单*/
	private Order orders;
	
	/**状态*/
	private Status status;
	
	/**备注*/
	private String remarks;
	
	/**预约数量*/
	private Integer quantity;
	/** 手机 */
	private String mobile;
	/**
	 * 获取手机
	 * 
	 * @return 手机
	 */
	@Length(max = 200)
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机
	 * 
	 * @param mobile
	 *            手机
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取预约类型
	 * @return
	 */
	public BookingType getBookingType() {
		return bookingType;
	}

	/**
	 * 设置预约类型
	 * @param bookingType
	 */
	public void setBookingType(BookingType bookingType) {
		this.bookingType = bookingType;
	}

	/**
	 * 获取预约会员
	 * @return
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置预约会员
	 * @param member
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取预约活动
	 * @return
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Promotion getPromotion() {
		return promotion;
	}

	/**
	 * 设置预约活动
	 * @param promotion
	 */
	public void setPromotionProducts(PromotionProducts promotionProducts) {
		this.promotionProducts = promotionProducts;
	}
	/**
	 * 获取预约活动
	 * @return
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public PromotionProducts getPromotionProducts() {
		return promotionProducts;
	}

	/**
	 * 设置预约活动
	 * @param promotion
	 */
	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
	/**
	 * 获取预约商品
	 * @return
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置预约商品
	 * @param product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取预约订金
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置预约订金
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取预约商品价格
	 * @return
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置预约商品价格
	 * @param price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取预约订金订单
	 * @return
	 */
	public String getPaymentOrder() {
		return paymentOrder;
	}

	/**
	 * 设置预约订金订单
	 * @param paymentOrder
	 */
	public void setPaymentOrder(String paymentOrder) {
		this.paymentOrder = paymentOrder;
	}

	/**
	 * 获取购买商品的订单
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders",nullable = false)
	public Order getOrders() {
		return orders;
	}

	/**
	 * 设置购买商品的订单
	 * @param order
	 */
	public void setOrders(Order orders) {
		this.orders = orders;
	}

	/**
	 * 获取状态
	 * @return
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 获取备注
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 设置备注
	 * @param remark
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 获取预约数量
	 * @return
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置预约数量
	 * @param quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
