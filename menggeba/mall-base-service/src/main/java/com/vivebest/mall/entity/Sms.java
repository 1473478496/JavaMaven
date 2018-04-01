/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * Entity - 短信
 * 
 * @author vnb shop Team
 * liu.jch
 */
@Entity
@Table(name = "gbm_sms")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_sms_sequence")
public class Sms extends OrderEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1791965656081688785L;

	/** 发送时间 */
	private Date sendTime;

	/** 业务类型，1-注册，2-验证，100-自定义 */
	private int busType;

	/** 手机号 */
	private String number;

	/** 内容 */
	private String content;

	/** 状态 ,0-发送成功，其它错误码*/
	private int status;

	@Length(max = 20)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Length(max = 512)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Length(max = 11)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getBusType() {
		return busType;
	}

	public void setBusType(int busType) {
		this.busType = busType;
	}
}