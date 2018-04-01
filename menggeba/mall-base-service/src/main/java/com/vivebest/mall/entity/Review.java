/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 评论
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_review")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_review_sequence")
public class Review extends BaseEntity {

	private static final long serialVersionUID = 8795901519290584100L;

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/review/content";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";

	/**
	 * 类型
	 */
	public enum Type {

		/** 好评 */
		positive,

		/** 中评 */
		moderate,

		/** 差评 */
		negative
	}

	/** 评分 */
	private Integer score;
	
	/** 商品评分*/
	private Integer prodScore;
	
	/** 物流评分*/
	private Integer tranScore;
	
	/** 客服评分*/
	private Integer custScore;

	/** 内容 */
	private String content;

	/** 是否显示 */
	private Boolean isShow;

	/** IP */
	private String ip;

	/** 会员 */
	private Member member;

	/** 商品 */
	private Product product;

	/** 订单编号*/
	private String sn;
	
	private String image_1;
	
	private String image_2;
	
	private String image_3;
	
	private String image_4;
	
	private String image_5;
	
	private Date tempDate;
	
	
	
	/**
	 * 获取评分
	 * 
	 * @return 评分
	 */
	@Column(nullable = true, updatable = false)
	public Integer getScore() {
		return score;
	}
	
 
	

	
	/**
	 * 获取评论图片1
	 * 
	 * @return 评分
	 */
	@Column(nullable = false)
	public String getImage_1() {
		return image_1;
	}

	public void setImage_1(String image_1) {
		this.image_1 = image_1;
	}
	
	/**
	 * 获取评论图片2
	 * 
	 * @return 评分
	 */
	@Column(nullable = false)
	public String getImage_2() {
		return image_2;
	}

	public void setImage_2(String image_2) {
		this.image_2 = image_2;
	}
	
	/**
	 * 获取评论图片3
	 * 
	 * @return 评分
	 */
	@Column(nullable = false)
	public String getImage_3() {
		return image_3;
	}

	public void setImage_3(String image_3) {
		this.image_3 = image_3;
	}
	
	/**
	 * 获取评论图片4
	 * 
	 * @return 评分
	 */
	@Column(nullable = false)
	public String getImage_4() {
		return image_4;
	}

	public void setImage_4(String image_4) {
		this.image_4 = image_4;
	}

	/**
	 * 获取评论图片5
	 * 
	 * @return 评分
	 */
	@Column(nullable = false)
	public String getImage_5() {
		return image_5;
	}

	public void setImage_5(String image_5) {
		this.image_5 = image_5;
	}

	/**
	 * 设置评分
	 * 
	 * @param score
	 *            评分
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false, updatable = false)
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取是否显示
	 * 
	 * @return 是否显示
	 */
	@Column(nullable = false)
	public Boolean getIsShow() {
		return isShow;
	}

	/**
	 * 设置是否显示
	 * 
	 * @param isShow
	 *            是否显示
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	/**
	 * 获取IP
	 * 
	 * @return IP
	 */
	@Column(nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP
	 * 
	 * @param ip
	 *            IP
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取会员
	 * 
	 * @return 会员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(updatable = false)
	public Member getMember() {
		return member;
	}

	/**
	 * 设置会员
	 * 
	 * @param member
	 *            会员
	 */
	public void setMember(Member member) {
		this.member = member;
	}

	/**
	 * 获取商品
	 * 
	 * @return 商品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, updatable = false)
	public Product getProduct() {
		return product;
	}

	/**
	 * 设置商品
	 * 
	 * @param product
	 *            商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	

	/**
	 * 获取订单编号
	 * @return
	 */
	@Length(max = 100)
	public String getSn() {
		return sn;
	}

	/**
	 * 获取订单编号
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getProduct() != null && getProduct().getId() != null) {
			return PATH_PREFIX + "/" + getProduct().getId() + PATH_SUFFIX;
		}
		return null;
	}

	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = false, updatable = false)
	public Integer getProdScore() {
		return prodScore;
	}

	public void setProdScore(Integer prodScore) {
		this.prodScore = prodScore;
	}
	
	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = false, updatable = false)
	public Integer getTranScore() {
		return tranScore;
	}

	public void setTranScore(Integer tranScore) {
		this.tranScore = tranScore;
	}

	@NotNull
	@Min(1)
	@Max(5)
	@Column(nullable = false, updatable = false)
	public Integer getCustScore() {
		return custScore;
	}

	public void setCustScore(Integer custScore) {
		this.custScore = custScore;
	}





	public Date getTempDate() {
		return tempDate;
	}





	public void setTempDate(Date tempDate) {
		this.tempDate = tempDate;
	}

	
	
}