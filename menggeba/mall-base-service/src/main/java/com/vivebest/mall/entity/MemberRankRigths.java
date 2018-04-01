/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 会员等级权限
 * 
 * @author zhaoshoushan
 * @version 3.0
 */
@Entity
@Table(name = "gbm_member_rank_rights")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_member_rank_rights_sequence")
public class MemberRankRigths extends BaseEntity {

	private static final long serialVersionUID = 3599029355500655209L;


	/** 
	 * 对应权益，0-价格优惠，1-免邮特权，2-专属客服，3-后服务优先通道，4-会员活动专区，5-专属萌值兑换，6-新品试用区，7-神秘优惠券，8-生日特权，9-赠送积分游戏萌值，10-专属推送（新品等） 
	 */
	public enum RightsName {
		// 价格优惠
		priceConcessions,
		// 免邮特权
		freeMailing,
		// 专属客服
		customerService,
		// 后服务优先通道
		servicePriority,
		// 会员活动专区
		memberActivityArea,
		// 专属萌值兑换
		mengValueExchange,
		// 新品试用区
		newTrialArea,
		// 神秘优惠券
		secretCoupons,
		// 生日特权
		birthdayPrivilege,
		// 赠送积分游戏萌值
		bonusPointsGame,
		// 专属推送（新品等）
		exclusivePush
	}
	
	private RightsName rightsName;

	/** 权益值，当权益库对应权益，0-价格优惠，7-神秘优惠券，9-赠送积分游戏萌值时，为具体权益值
	 * 优惠价格为乘以100的折扣值（如：9折为90）
	 */
	private Long rightsValue;

	/** 是否启用，0-不启用，1-启用 */
	private Boolean isStatus;

	/** 备注 */
	private String name;
	
	/** 会员等级 */
	private MemberRank memberRank = new MemberRank();

	public Long getRightsValue() {
		return rightsValue;
	}

	public void setRightsValue(Long rightsValue) {
		this.rightsValue = rightsValue;
	}

	public Boolean getIsStatus() {
		return isStatus;
	}

	public void setIsStatus(Boolean isStatus) {
		this.isStatus = isStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取会员等级权限
	 * 
	 * @return 会员等级权限
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	public RightsName getRightsName() {
		return rightsName;
	}

	public void setRightsName(RightsName rightsName) {
		this.rightsName = rightsName;
	}

}