/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 币种兑换规则
 * 
 * @author vnb shop Team
 *                     junly
 * @version 3.0
 */
@Entity
@Table(name = "gbm_integration_rule")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_integration_rule_sequence")
public class IntegrationRule extends BaseEntity {

	private static final long serialVersionUID = -4494144902110236826L;

	/** 币种 */
	private String ccy;

	/** 兑换规则 */
	private BigDecimal rule;

	/**
	 * 获取币种
	 * @return
	 */
	@Column(nullable = false, updatable = false,unique = true)
	public String getCcy() {
		return ccy;
	}

	/**
	 * 设计币种操作
	 * @param ccy
	 */
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	/**
	 * 获取积分兑换规则操作
	 * @return
	 */
	@Column(nullable = false)
	public BigDecimal getRule() {
		return rule;
	}

	/**
	 * 设计兑换规则操作
	 * @param rule
	 */
	public void setRule(BigDecimal rule) {
		this.rule = rule;
	}

}