package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 转存
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_question_transfer")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_ad_sequence")
public class QuestionTransfer extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**手机号*/
	private String mobile;
	
	/**积分*/
	private Integer point;
	
	/**微信openId*/
	private String openId;

	/**
	 * 获取手机号
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机号
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取积分
	 * @return
	 */
	public Integer getPoint() {
		return point;
	}

	/**
	 * 设置积分
	 * @param point
	 */
	public void setPoint(Integer point) {
		this.point = point;
	}

	/**
	 * 获取openId
	 * @return
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * 设置openId
	 * @param openId
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
