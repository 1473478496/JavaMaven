/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 广告
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_questionnare_answer")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_ad_sequence")
public class QuestionnareAnswer extends BaseEntity {

	private static final long serialVersionUID = -1307743303786909390L;

	private String mobile;
	
	private String name;
	
	private Integer score;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	
	
}