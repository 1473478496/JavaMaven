package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 权益商户
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_right_order_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_right_order_item_sequence")
public class RightOrderItem extends BaseEntity {
	
	private static final long serialVersionUID = -7519995206437156315L;
	
	public enum RightsUseStatus{
		
		/** 未使用*/
		unused,

		/** 已使用*/
		used
	}

 
 
	/**权益价格（现金）*/
	private BigDecimal price;
	
	/**权益价格（积分）*/
	private Long pricePoint;
	
	/**支付金额*/
	private BigDecimal payPrice;
	/**支付萌值*/
	private Long payPricePoint;
	
	

	
 
	
	/**权益退货状态*/
	private Boolean returnStatus;
	
	/**权益退货日期*/
	private Date returnDate;
	
	/**权益退货原因*/
	private String returnCase;
	
	/**权益退货描述*/
	private String returnDesc;
	
	/** 订单 */
	private RightOrder rightOrder;
	
	/** 权益 */
	private Rights rights;
	
	
	/** 权益码 */
	private RightsCode rightsCode;
	
	
	/** 优惠券分摊比例*/
	private BigDecimal  couponDivide;
	
	
	
 
	 

	 

	/**
	 * 获取权益价格（现金）
	 * @return
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置权益价格（现金）
	 * @param price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取权益价格（积分）
	 * @return
	 */
	public Long getPricePoint() {
		return pricePoint;
	}

	/**
	 * 设置权益价格（积分）
	 * @param pricePoint
	 */
	public void setPricePoint(Long pricePoint) {
		this.pricePoint = pricePoint;
	}

 
 

	/**
	 * 获取权益退货状态
	 * @return
	 */
	public Boolean getReturnStatus() {
		return returnStatus;
	}

	/**
	 * 设置权益退货状态
	 * @param returnStatus
	 */
	public void setReturnStatus(Boolean returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * 获取权益退货日期
	 * @return
	 */
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * 设置权益退货日期
	 * @param returnDate
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * 获取权益退货原因
	 * @return
	 */
	public String getReturnCase() {
		return returnCase;
	}

	/**
	 * 设置权益退货原因
	 * @param returnCase
	 */
	public void setReturnCase(String returnCase) {
		this.returnCase = returnCase;
	}

	/**
	 * 获取权益退货描述
	 * @return
	 */
	public String getReturnDesc() {
		return returnDesc;
	}

	/**
	 * 设置权益退货描述
	 * @param returnDesc
	 */
	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}
	
 
	/**
	 * 获取权益商品
	 * 
	 * @return 权益商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Rights getRights() {
		return rights;
	}
	/**
	 * 设置权益商品
	 * 
	 * @param rights
	 *            权益商品
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	public BigDecimal getCouponDivide() {
		return couponDivide;
	}

	public void setCouponDivide(BigDecimal couponDivide) {
		this.couponDivide = couponDivide;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public Long getPayPricePoint() {
		return payPricePoint;
	}

	public void setPayPricePoint(Long payPricePoint) {
		this.payPricePoint = payPricePoint;
	}

	
	 
	@OneToOne(fetch = FetchType.LAZY)
	public RightsCode getRightsCode() {
		return rightsCode;
	}

	public void setRightsCode(RightsCode rightsCode) {
		this.rightsCode = rightsCode;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name = "right_order", nullable = false, updatable = false)
	public RightOrder getRightOrder() {
		return rightOrder;
	}

	public void setRightOrder(RightOrder rightOrder) {
		this.rightOrder = rightOrder;
	}
	
}
