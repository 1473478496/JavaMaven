package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_coupon_actors")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_coupon_actors_sequence")
public class CopounActors extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2306496251203123635L;

	/** 优惠券 */
	private Coupon coupon;
	 
	/**
	 * 参与者手机号
	 */
	private  String actorTel ;
	
	/**
	 * 参与者（备用）
	 */
	private  String actor ;
	

	
	/**
	 * 获取优惠券
	 * 
	 * @return 优惠券
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Coupon getCoupon() {
		return coupon;
	}

	/**
	 * 设置优惠券
	 * 
	 * @param coupon
	 *            优惠券
	 */
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public String getActorTel() {
		return actorTel;
	}

	public void setActorTel(String actorTel) {
		this.actorTel = actorTel;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

 
}
