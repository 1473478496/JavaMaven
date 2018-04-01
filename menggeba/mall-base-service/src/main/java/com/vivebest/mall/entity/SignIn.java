/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 签到规则
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_sign_in")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_sign_in_sequence")
public class SignIn extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 会员 */
	private Member member;

	/** 赠送萌值 */
	private Long gift;

	/** 备注 */
	private String remark;

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param gift
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取赠送萌值
	 * 
	 * @return 赠送萌值
	 */
	@Column(nullable = false)
	public Long getGift() {
		return gift;
	}

	/**
	 * 设置赠送萌值
	 * 
	 * @param gift
	 *            赠送萌值
	 */
	public void setGift(Long gift) {
		this.gift = gift;
	}

	/**
	 * 获取签到规则备注
	 * 
	 * @return 签到规则备注
	 */
	@Column(nullable = false)
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置签到规则备注
	 * 
	 * @param remark
	 *            签到规则备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}