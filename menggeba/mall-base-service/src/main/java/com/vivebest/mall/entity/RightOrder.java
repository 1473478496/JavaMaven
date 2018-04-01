package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;

import com.vivebest.mall.core.entity.BaseEntity;


/**
 * Entity - 权益商户
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_right_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_right_order_sequence")
public class RightOrder extends BaseEntity {
	
	
	private static Logger logger = Logger.getLogger(RightOrder.class);
	private static final long serialVersionUID = 5897733733593459159L;

	/**发票抬头*/
	private String invoiceTitle;
	
	/**权益来源，0-线下权益，1-线上权益*/
	private Boolean rightType;
	
	/**附言*/
	private String memo;
	
	/**订单状态*/
	public enum RightsOrderStatus {
		/** 已完成 */
		completed,

		/** 已取消 */
		cancelled,
		
		/** 待支付 */
		unpayment,

		/** 支付中 */
		payment,

		/** 已支付 */
		paymented,

		/** 已关闭 */
		closed
		
	}
	
	/** 订单状态 */
	private RightsOrderStatus rightsOrderStatus;
	
	
	/** 优惠码 */
	private CouponCode couponCode;
	
	/** 优惠券折扣 */
	private BigDecimal couponDiscount;
	
	/**短信数量控制*/
	private Long stock;
	
	/**
	 * 支付状态
	 */
	public enum PaymentStatus {

		/** 未支付 */
		unpaid,

		/** 部分支付 */
		partialPayment,

		/** 已支付 */
		paid,

	

		/** 已退款 */
		refunded
	}
	
	/**支付状态*/
	private PaymentStatus paymentStatus;
	
	
 
	
	 
	
	/**订单 编号*/
	private String sn;
	
	/** 会员 */
	private Member member;
	
	/** 订单项 */
	private List<RightOrderItem> orderItems = new ArrayList<RightOrderItem>();
	
	
	/** 到期时间 */
	private Date expire;

	/** 锁定到期时间 */
	private Date lockExpire;

	/** 是否已分配库存 */
	private Boolean isAllocatedStock;
	
	
	
	private Set<RightsCode> rightCode = new HashSet<RightsCode>();
	

	
	/**商户 id*/
	private Long tradeId;
	
	/** 取消订单原因 */
	private String cancelDesc;
	
	/**
	 * 下单电话号码
	 */
	private String phone;
	
	
	/**
	 * 获取锁定到期时间
	 * 
	 * @return 锁定到期时间
	 */
	public Date getLockExpire() {
		return lockExpire;
	}

	/**
	 * 设置锁定到期时间
	 * 
	 * @param lockExpire
	 *            锁定到期时间
	 */
	public void setLockExpire(Date lockExpire) {
		logger.info("[订单ID]:" + getId() + ",[订单编号]:" + getSn() + ",[设置锁定到期时间]:" + lockExpire);
		this.lockExpire = lockExpire;
	}
	
	
	/**
	 * 获取是否已分配库存
	 * 
	 * @return 是否已分配库存
	 */
	@Column(nullable = false)
	public Boolean getIsAllocatedStock() {
		return isAllocatedStock;
	}

	/**
	 * 设置是否已分配库存
	 * 
	 * @param isAllocatedStock
	 *            是否已分配库存
	 */
	public void setIsAllocatedStock(Boolean isAllocatedStock) {
		this.isAllocatedStock = isAllocatedStock;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return 到期时间
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 设置到期时间
	 * 
	 * @param expire
	 *            到期时间
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
	
	
	/**
	 * 获取发票抬头
	 * @return
	 */
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	/**
	 * 设置发票抬头
	 * @param invoiceTitle
	 */
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	/**
	 * 获取权益来源，0-线下权益，1-线上权益
	 * @return
	 */
	public Boolean getRightType() {
		return rightType;
	}

	/**
	 * 设置权益来源，0-线下权益，1-线上权益
	 * @param rightType
	 */
	public void setRightType(Boolean rightType) {
		this.rightType = rightType;
	}

	/**
	 * 获取附言
	 * @return
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置附言
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	
	/**
	 * 获取订单状态
	 * @return
	 */
	@Column(nullable = false)
	public RightsOrderStatus getRightsOrderStatus() {
		return rightsOrderStatus;
	}
	/**
	 * 设置订单状态
	 * @param paymentStatus
	 */
	public void setRightsOrderStatus(RightsOrderStatus rightsOrderStatus) {
		this.rightsOrderStatus = rightsOrderStatus;
	}
 
	 

	/**
	 * 获取支付状态
	 * @return
	 */
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置支付状态
	 * @param paymentStatus
	 */
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	 

	/**
	 * 获取订单编号
	 * @return
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * 设置订单编号
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取会员
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}
	/**
	 * 设置会员
	 * @param member
	 */
	public void setMember(Member member) {
		this.member = member;
	}

 

	/**
	 * 获取商户id
	 * @return
	 */
	public Long getTradeId() {
		return tradeId;
	}

	/**
	 * 设置商户id
	 * @param tradeId
	 */
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}
	/**
	 * 获取订单项
	 * 
	 * @return 订单项
	 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "rightOrder", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	public List<RightOrderItem> getOrderItems() {
		return orderItems;
	}
	/**
	 * 设置订单项
	 * 
	 * @param orderItems
	 *            订单项
	 */
	public void setOrderItems(List<RightOrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	/**
	 * 获取取消订单原因
	 * 
	 * @return
	 */
	public String getCancelDesc() {
		return cancelDesc;
	}

	/**
	 * 设置取消订单原因
	 * @param cancelDesc
	 */
	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc;
	}
	
	/**
	 * 获取优惠码
	 * 
	 * @return 优惠码
	 */
	@OneToOne(fetch = FetchType.LAZY)
	public CouponCode getCouponCode() {
		return couponCode;
	}

	/**
	 * 设置优惠码
	 * 
	 * @param couponCode
	 *            优惠码
	 */
	public void setCouponCode(CouponCode couponCode) {
		this.couponCode = couponCode;
	}
	
	
	/**
	 * 订单总金额
	 * @return
	 */
	@Transient
	public BigDecimal getSubPrice(){
		BigDecimal price=new BigDecimal(0);
		if (sn != null && getOrderItems() != null) {
			for (RightOrderItem orderItem : getOrderItems()) {
				price=price.add(orderItem.getPrice());
			}
		}
		return price;
	}
	/**
	 * 订单总金额
	 * @return
	 */
	@Transient
	public Long getSubPricePoint(){
		Long price=0L;
		if (sn != null && getOrderItems() != null) {
			for (RightOrderItem orderItem : getOrderItems()) {
				price=price+orderItem.getPricePoint();
			}
		}
		return price;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@OneToMany(mappedBy = "rightOrder", fetch = FetchType.LAZY) 
	public Set<RightsCode> getRightCode() {
		return rightCode;
	}

	public void setRightCode(Set<RightsCode> rightCode) {
		this.rightCode = rightCode;
	}
	/**
	 * 获取优惠券折扣
	 * 
	 * @return 优惠券折扣
	 */
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	/**
	 * 设置优惠券折扣
	 * 
	 * @param couponDiscount
	 *            优惠券折扣
	 */
	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
	
	/**
	 * 获取数量（为空没有发送短信）
	 * @return
	 */
	public Long getStock() {
		return stock;
	}

	/**
	 * 设置数量（为空没有发过短信）
	 * @param stock
	 */
	public void setStock(Long stock) {
		this.stock = stock;
	}

	/**
	 * 判断优惠券是否有效
	 * 
	 * @param coupon
	 *            优惠券
	 * @return 优惠券是否有效
	 */
	@Transient
	public boolean isValid(Coupon coupon) {
		if (coupon == null || !coupon.getIsEnabled() || !coupon.hasBegun() || coupon.hasExpired()) {
			return false;
		}
		 
		 if ((coupon.getMinimumPrice() != null && coupon.getMinimumPrice().compareTo(getSubPrice()) > 0)) {
			return false;
		}	
	 
		
		return true;
	}
	
	 
 
	 
}
