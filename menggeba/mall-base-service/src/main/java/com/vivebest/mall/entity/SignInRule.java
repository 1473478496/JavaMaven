/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "gbm_sign_in_rule")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_sign_in_rule_sequence")
public class SignInRule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2821971554189391271L;

	/**
	 * 签到规则类型
	 */
	public enum RuleCategory {

		/** 0-正常签订 */
		normal,

		/** 1-7天连续签到 */
		around7,

		/** 2-15-29天连续签到 */
		around15,

		/** 3-30,31天连续签到 */
		around30
	}

	/** 签到规则类型 */
	private RuleCategory signRule;

	/** 赠送萌值 */
	private Long gift;

	/** 备注 */
	private String remark;

	/**
	 * 获取签到规则类型
	 * 
	 * @return 签到规则类型
	 */
	@Column(nullable = false)
	public RuleCategory getSignRule() {
		return signRule;
	}

	/**
	 * 设置签到规则类型
	 * 
	 * @param signRule
	 *            签到规则类型
	 */
	public void setSignRule(RuleCategory signRule) {
		this.signRule = signRule;
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