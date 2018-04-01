/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 退货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_returns")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_returns_sequence")
public class Returns extends BaseEntity {

	private static final long serialVersionUID = -8019074120457087212L;

	/**
	 * 退货状态
	 */
	public enum Status {
		
		/** 待审核  */
		waitingApprove,
		
		/** 审核通过  */
		approved,
		
		/** 审核拒绝 */
		unapprove,
		
		/** 客户已发货 */
		delivered,
		
		/** 已确认收货  */
		received,
		
		/** 登记处理  */
		dealwith
	}
	
	/**
	 * 退货类型
	 */
	public enum ReturnType {
		
		/** 直接退款   */
		directlyRefund,
		
		/** 退货再退款   */
		returnToRefund,
		
		/**团购退款*/
		bookingRefund
		
	}
	
	/** 编号 */
	private String sn;

	/** 配送方式 */
	private String shippingMethod;

	/** 物流公司 */
	private String deliveryCorp;

	/** 运单号 */
	private String trackingNo;

	/** 物流费用 */
	private BigDecimal freight;

	/** 发货人 */
	private String shipper;

	/** 地区 */
	private String area;

	/** 地址 */
	private String address;

	/** 邮编 */
	private String zipCode;

	/** 电话 */
	private String phone;

	/** 操作员 */
	private String operator;

	/** 备注 */
	private String memo;

	/** 退货状态   */
	private Status status;

	/** 退货理由 */
	private String returnCause;
	
	/** 退货问题描述 */
	private String returnDesc;
	
	/** 退货日期  */
	private Date returnDate;
	
	/** 退货凭证  */
	private String returnVoucher;
	
	/** 审核日期  */
	private Date auditDate;
	
	/** 审核人  */
	private String auditApply;
	
	/** 审核描述  */
	private String auditDesc;
	
	/** 退货类型  */
	private ReturnType returnType;

	/** 订单 */
	private Order order;
	
	/** 订单项 */
	private OrderItem orderItem;
	
	/** 会员 */
	private Member member;

	/** 退货项 */
	private List<ReturnsItem> returnsItems = new ArrayList<ReturnsItem>();
	
	/** 退款单 */
	private List<Refunds> refunds = new ArrayList<Refunds>();
	

	/**
	 * 获取编号
	 * 
	 * @return 编号
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置编号
	 * 
	 * @param sn
	 *            编号
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取配送方式
	 * 
	 * @return 配送方式
	 */
	@Column(updatable = false)
	public String getShippingMethod() {
		return shippingMethod;
	}

	/**
	 * 设置配送方式
	 * 
	 * @param shippingMethod
	 *            配送方式
	 */
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	/**
	 * 获取物流公司
	 * 
	 * @return 物流公司
	 */
//	@Column(updatable = false)
	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	/**
	 * 设置物流公司
	 * 
	 * @param deliveryCorp
	 *            物流公司
	 */
	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	/**
	 * 获取运单号
	 * 
	 * @return 运单号
	 */
	@Length(max = 200)
//	@Column(updatable = false)
	public String getTrackingNo() {
		return trackingNo;
	}

	/**
	 * 设置运单号
	 * 
	 * @param trackingNo
	 *            运单号
	 */
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	/**
	 * 获取物流费用
	 * 
	 * @return 物流费用
	 */
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(updatable = false, precision = 21, scale = 6)
	public BigDecimal getFreight() {
		return freight;
	}

	/**
	 * 设置物流费用
	 * 
	 * @param freight
	 *            物流费用
	 */
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	/**
	 * 获取发货人
	 * 
	 * @return 发货人
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	public String getShipper() {
		return shipper;
	}

	/**
	 * 设置发货人
	 * 
	 * @param shipper
	 *            发货人
	 */
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@NotEmpty
	@Column(nullable = false, updatable = false)
	public String getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * 获取地址
	 * 
	 * @return 地址
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址
	 * 
	 * @param address
	 *            地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取邮编
	 * 
	 * @return 邮编
	 */
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮编
	 * 
	 * @param zipCode
	 *            邮编
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取电话
	 * 
	 * @return 电话
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置电话
	 * 
	 * @param phone
	 *            电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取操作员
	 * 
	 * @return 操作员
	 */
	@Column(nullable = false, updatable = false)
	public String getOperator() {
		return operator;
	}

	/**
	 * 设置操作员
	 * 
	 * @param operator
	 *            操作员
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	@Length(max = 200)
//	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取订单
	 * 
	 * @return 订单
	 */
	@NotNull
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
	 * 获取退货项
	 * 
	 * @return 退货项
	 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "returns", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<ReturnsItem> getReturnsItems() {
		return returnsItems;
	}

	/**
	 * 设置退货项
	 * 
	 * @param returnsItems
	 *            退货项
	 */
	public void setReturnsItems(List<ReturnsItem> returnsItems) {
		this.returnsItems = returnsItems;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getReturnsItems() != null) {
			for (ReturnsItem returnsItem : getReturnsItems()) {
				if (returnsItem != null && returnsItem.getQuantity() != null) {
					quantity += returnsItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取退货状态 
	 * 
	 * @return 退货状态 
	 * 
	 */
	@Column(name = "status", nullable = false)
	@JsonProperty
	public Status getStatus() {
		return status;
	}
	
	/**
	 * 设置退货状态 
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 获取退货理由
	 * @return
	 */
	@Column(name = "return_cause")
	public String getReturnCause() {
		return returnCause;
	}

	/**
	 * 设置退货理由
	 * @param returnCause
	 */
	public void setReturnCause(String returnCause) {
		this.returnCause = returnCause;
	}

	/**
	 * 获取退货描述
	 * @return
	 */
	@Column(name = "return_desc")
	public String getReturnDesc() {
		return returnDesc;
	}

	/**
	 * 设置退货描述
	 * @param returnDesc
	 */
	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}

	/**
	 * 获取退货日期
	 * @return
	 */
	@Column(name = "return_date")
	public Date getReturnDate() {
		return returnDate;
	}

	/**
	 * 设置退货日期
	 * @param returnDate
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * 获取退货凭证
	 * @return
	 */
	@Column(name = "return_voucher")
	public String getReturnVoucher() {
		return returnVoucher;
	}

	/**
	 * 设置退货凭证
	 * @param returnVoucher
	 */
	public void setReturnVoucher(String returnVoucher) {
		this.returnVoucher = returnVoucher;
	}

	/**
	 * 获取审核日期
	 * @return
	 */
	@Column(name = "audit_date")
	public Date getAuditDate() {
		return auditDate;
	}

	/**
	 * 设置审核日期
	 * @param auditDate
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * 获取审核人
	 * @return
	 */
	@Column(name = "audit_apply")
	public String getAuditApply() {
		return auditApply;
	}

	/**
	 * 设置审核人
	 * @param auditApply
	 */
	public void setAuditApply(String auditApply) {
		this.auditApply = auditApply;
	}

	/**
	 * 获取审核描述
	 * @return
	 */
	@Column(name = "audit_desc")
	public String getAuditDesc() {
		return auditDesc;
	}

	/**
	 * 设置审核描述
	 * @param auditDesc
	 */
	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	/**
	 * 获取退货类型
	 * @return
	 */
	@Column(name = "return_type")
	@JsonProperty
	public ReturnType getReturnType() {
		return returnType;
	}

	/**
	 * 设置退货类型
	 * @param returnType
	 */
	public void setReturnType(ReturnType returnType) {
		this.returnType = returnType;
	}

	/**
	 * 获取会员信息
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member",nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员信息
	 * @param member
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取退款单
	 * @return
	 */
	@Valid
	@NotEmpty
	@OneToMany(mappedBy = "returns", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Refunds> getRefunds() {
		return refunds;
	}

	/**
	 * 设置退款单
	 * @param refunds
	 */
	public void setRefunds(List<Refunds> refunds) {
		this.refunds = refunds;
	}

	/**
	 *获取订单项
	 * @return
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_item",nullable = false, updatable = false)
	public OrderItem getOrderItem() {
		return orderItem;
	}

	/**
	 * 设置订单项
	 * @param orderItem
	 */
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	
}