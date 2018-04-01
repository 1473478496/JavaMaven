/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 权益码
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_right_code")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_right_code_sequence")
public class RightsCode extends BaseEntity {

	private static final long serialVersionUID = -1812874037224306719L;

	/** 号码 */
	private String code;

	/** 是否已使用 */
	private Boolean isUsed;

	/** 使用日期 */
	private Date usedDate;

	/** 权益 */
	private Rights rights;

	/** 会员 */
	private Member member;
	
	
	/**
	 * 权益订单
	 */
	private  RightOrder rightOrder;
	
	
	
	private RightOrderItem rightsOrderItem;

	 

	/**
	 * 获取号码
	 * 
	 * @return 号码
	 */
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	public String getCode() {
		return code;
	}

	/**
	 * 设置号码
	 * 
	 * @param code
	 *            号码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取是否已使用
	 * 
	 * @return 是否已使用
	 */
	@Column(nullable = false)
	public Boolean getIsUsed() {
		return isUsed;
	}

	/**
	 * 设置是否已使用
	 * 
	 * @param isUsed
	 *            是否已使用
	 */
	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 * 获取使用日期
	 * 
	 * @return 使用日期
	 */
	public Date getUsedDate() {
		return usedDate;
	}

	/**
	 * 设置使用日期
	 * 
	 * @param usedDate
	 *            使用日期
	 */
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	/**
	 * 获取权益
	 * 
	 * @return 权益
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rights", nullable = false, updatable = false)
	public Rights getRights() {
		return rights;
	}

	/**
	 * 设置优惠券
	 * 
	 * @param rights 权益
	 *            
	 */
	public void setRights(Rights rights) {
		this.rights = rights;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
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

	@OneToOne(mappedBy = "rightsCode", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "rightsOrderItem")
	public RightOrderItem getRightsOrderItem() {
		return rightsOrderItem;
	}

	public void setRightsOrderItem(RightOrderItem rightsOrderItem) {
		this.rightsOrderItem = rightsOrderItem;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false,updatable=true)
	public RightOrder getRightOrder() {
		return rightOrder;
	}

	public void setRightOrder(RightOrder rightOrder) {
		this.rightOrder = rightOrder;
	}

 

	 

 

}