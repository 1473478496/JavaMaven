package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_point_actors")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_point_actors_sequence")
public class PointActors extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2306496251203123635L;

	 
	 
	/**
	 * 参与者手机号
	 */
	private  String actorTel ;
	
	/**
	 * 参与者（备用）
	 */
	private  String actor ;
	
	/**临时积分
	 * 
	 */
	private  Integer point ;
	

	
	 

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

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

 
}
