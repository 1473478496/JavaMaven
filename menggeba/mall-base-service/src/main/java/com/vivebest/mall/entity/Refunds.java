/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 退款单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_refunds")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_refunds_sequence")
public class Refunds extends BaseEntity {

	private static final long serialVersionUID = 354885216604823632L;

	/**
	 * 方式
	 */
	public enum Method {

		/** 在线支付 */
		online,

		/** 线下支付 */
		offline,

		/** 预存款支付 */
		deposit
	}
	
	public enum Status {
		
		/** 退款中 */
		refunding,
		
		/** 退款成功 */
		refundSuccess,
		
		/** 退款失败*/
		refundFail
	}
	/**
	 * 退款类型
	 * @author junly
	 *
	 */
	public enum Type {
		
		/** 仅退款 */
		onlyRefund,
		
		/** 退货退款 */
		refundAfterReturn,
		/**团购退款*/
		bookingRefund
	}
	/** 编号 */
	private String sn;

	/** 方式 */
	private Method method;

	/** 支付方式 */
	private String paymentMethod;

	/** 退款银行 */
	private String bank;

	/** 退款账号 */
	private String account;

	/** 退款金额 */
	private BigDecimal amount;

	/** 收款人 */
	private String payee;

	/** 操作员 */
	private String operator;

	/** 备注 */
	private String memo;

	/** 订单 */
	private Order order;
	
	/** 会员 */
	private Member member;
	
	/** 退货单 */
	private Returns returns;
	
	/** 退款状态 */
	private Status status;
	
	/** 退款类型  */
	private Type type;
	
	/**退款参考号*/
	private String seqUuid;

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
	 * 获取方式
	 * 
	 * @return 方式
	 */
	@NotNull
	@Column(nullable = false, updatable = false)
	public Method getMethod() {
		return method;
	}

	/**
	 * 设置方式
	 * 
	 * @param method
	 *            方式
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * 获取支付方式
	 * 
	 * @return 支付方式
	 */
	@Column(updatable = false)
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * 设置支付方式
	 * 
	 * @param paymentMethod
	 *            支付方式
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * 获取退款银行
	 * 
	 * @return 退款银行
	 */
	@Length(max = 200)
	@Column(updatable = false)
	public String getBank() {
		return bank;
	}

	/**
	 * 设置退款银行
	 * 
	 * @param bank
	 *            退款银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 获取退款账号
	 * 
	 * @return 退款账号
	 */
	@Length(max = 200)
	@Column(updatable = false)
	public String getAccount() {
		return account;
	}

	/**
	 * 设置退款账号
	 * 
	 * @param account
	 *            退款账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 获取退款金额
	 * 
	 * @return 退款金额
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 12, fraction = 3)
	@Column(nullable = false, updatable = false, precision = 21, scale = 6)
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置退款金额
	 * 
	 * @param amount
	 *            退款金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 获取收款人
	 * 
	 * @return 收款人
	 */
	@Length(max = 200)
	@Column(updatable = false)
	public String getPayee() {
		return payee;
	}

	/**
	 * 设置收款人
	 * 
	 * @param payee
	 *            收款人
	 */
	public void setPayee(String payee) {
		this.payee = payee;
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
	@Column(updatable = false)
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
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member",nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获得退款状态
	 * @return
	 */
	@Column(name = "status", nullable = false)
	@JsonProperty
	public Status getStatus() {
		return status;
	}

	/**
	 * 设置退款状态
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 获取退款类型
	 * @return
	 */
	@Column(name = "type", nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置退款类型
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * 获取退货单
	 * @return
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returns", nullable = false, updatable = false)
	public Returns getReturns() {
		return returns;
	}

	/**
	 * 设置退货单
	 * @param returns
	 */
	public void setReturns(Returns returns) {
		this.returns = returns;
	}

	/**
	 * 退款参考号
	 * @return
	 */
	@Column(name = "seqUuid")
	public String getSeqUuid() {
		return seqUuid;
	}

	public void setSeqUuid(String seqUuid) {
		this.seqUuid = seqUuid;
	}
	
}