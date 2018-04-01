/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity - 品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_email_check")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_email_check_sequence")
public class EmailCheck extends OrderEntity {

	private static final long serialVersionUID = -6109590619136943215L;

	/** email发送的key */
	private String emailKey;

	/** 发送时间 */
	private Date sendTime;

	/** 是否已验证 */
	private Boolean isChecked;
	
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getEmailKey() {
		return emailKey;
	}

	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Boolean getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

}