package com.vivebest.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.vivebest.mall.core.entity.BaseEntity;

 

/***
 * 合作商户
 * @author flycross
 *
 */
@Entity
@Table(name = "gbm_busniess_cooperate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_busniess_cooperate_sequence")
public class BusniessCooperate extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1788777632123023750L;
	
	
	private String busniessName; //商户名称
	
	/** 地区 */
	private Area area;
	
	
	private String busniessContents; //商品目录

	
	/** 联系人 */
	private String contacter;
	
	

	/** 手机 */
	private String mobile;
	
	/**
	 * 审核意见
	 */
	private String auditDesc;
	
	/**
	 * 状态
	 */
	public enum Status {

		/** 等待审核 */
		wait,

		/** 审核成功 */
		aproved,

		/** 审核失败 */
		reject
	}
	
	
	/** 状态 */
	private Status status;
	
	

	/** E-mail */
	private String email;


	@NotNull
	public String getBusniessName() {
		return busniessName;
	}



	public void setBusniessName(String busniessName) {
		this.busniessName = busniessName;
	}


	@NotNull
	public String getBusniessContents() {
		return busniessContents;
	}



	public void setBusniessContents(String busniessContents) {
		this.busniessContents = busniessContents;
	}


	@NotNull
	public String getContacter() {
		return contacter;
	}



	public void setContacter(String contacter) {
		this.contacter = contacter;
	}


	@NotNull
	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	@NotNull
	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}


   
	public void setArea(Area area) {
		this.area = area;
	}


	@Column(nullable = false)
	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}



	public String getAuditDesc() {
		return auditDesc;
	}



	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}
	
	

}
