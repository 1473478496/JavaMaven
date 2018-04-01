/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * Entity - 安全密钥
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Embeddable
public class SafeKey implements Serializable {

	private static final long serialVersionUID = -8536541568286987548L;

	/** 密钥 */
	private String value;

	/** 到期时间 */
	private Date expire;

	/**
	 * 获取密钥
	 * 
	 * @return 密钥
	 */
	@Column(name = "safe_key_value")
	public String getValue() {
		return value;
	}

	/**
	 * 设置密钥
	 * 
	 * @param value
	 *            密钥
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取到期时间
	 * 
	 * @return 到期时间
	 */
	@Column(name = "safe_key_expire")
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
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return getExpire() != null && new Date().after(getExpire());
	}

}