/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 会员生日权益
 * 
 * @author ding.hy
 * @version 3.0
 */
@Entity
@Table(name = "gbm_member_birthday_rights")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_member_birthday_rights_sequence")
public class MemberBirthdayRigths extends BaseEntity {

	private static final long serialVersionUID = 1226367473764309997L;
	
	/**
	 * 会员
	 */
	private Member member = new Member();
	
	/**
	 * 权益生产日期
	 */
	private Date rightsDate;
	
	/**
	 * 权益值  共3位，第1位表示站内信，第2位表示邮件，第3位短信，0表示无权益，1-表示有权益，如101表示有站内信和短信通知
	 */
	private String rightsValue;
	
	/**
	 * 权益状态   0-未发送，1-已发送
	 */
	private boolean rightsStatus;
	
	/**
	 * 权益描述
	 */
	private String rightsDesc;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 获取会员
	 * @return
	 */
	@OneToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * @param member
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取权益生产日期
	 * @return
	 */
	public Date getRightsDate() {
		return rightsDate;
	}

	/**
	 * 设置权益生产日期
	 * @param rightsDate
	 */
	public void setRightsDate(Date rightsDate) {
		this.rightsDate = rightsDate;
	}

	/**
	 * 获取权益值
	 * @return
	 */
	public String getRightsValue() {
		return rightsValue;
	}

	/**
	 * 设置权益值
	 * @param rightsValue
	 */
	public void setRightsValue(String rightsValue) {
		this.rightsValue = rightsValue;
	}

	/**
	 * 获取权益状态
	 * @return
	 */
	public boolean getRightsStatus() {
		return rightsStatus;
	}

	/**
	 * 设置权益状态
	 * @param rightsStatus
	 */
	public void setRightsStatus(boolean rightsStatus) {
		this.rightsStatus = rightsStatus;
	}

	/**
	 * 获取权益描述
	 * @return
	 */
	public String getRightsDesc() {
		return rightsDesc;
	}

	/**
	 * 设置权益描述
	 * @param rightsDesc
	 */
	public void setRightsDesc(String rightsDesc) {
		this.rightsDesc = rightsDesc;
	}

	/**
	 * 获取权益备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置权益备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}