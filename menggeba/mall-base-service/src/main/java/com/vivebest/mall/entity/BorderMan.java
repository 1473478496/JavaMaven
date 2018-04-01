package com.vivebest.mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 边民
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_member_borderman")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_member_bordermanSequence")
public class BorderMan extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 边民证号
	 */
	private String bmNum;
	/**
	 * 身份证号
	 */
	private String idCardNum;

	/**
	 * 边民的真实姓名
	 */
	private String trueName;
	/**
	 * 前往地区
	 */
	private String goArea;
	/**
	 * 发证机关
	 */
	private String issuedAgencies;
	/**
	 * 发证日期
	 */
	private Date issuedDate;
	/**
	 * 边民证有效期开始
	 */
	private Date startDate;
	/**
	 * 边民证有效期介绍
	 */
	private Date endDate;
	/**
	 * 备注
	 */
	private String comment;
	/**
	 * 审核状态： 00-待审核，10-审核通过， 20-冻结，
	 */
	private int checkState;
	/**
	 * 审核日期
	 */
	private Date checkDate;
	/**
	 * 审核人
	 */
	private String checkPerson;
	/**
	 * 审核描述
	 */
	private String checkDescription;

	/** 边民属性值0 */
	private String attributeValue0;

	/** 边民属性值1 */
	private String attributeValue1;

	/** 边民属性值2 */
	private String attributeValue2;

	/**
	 * 对应会员信息
	 */
	private Member member;

	/**
	 * 获取边民证
	 * 
	 * @return
	 */
	public String getBmNum() {
		return bmNum;
	}

	/**
	 * 设置边民证
	 * 
	 * @param bmNum
	 */
	public void setBmNum(String bmNum) {
		this.bmNum = bmNum;
	}

	/**
	 * 获取边民身份证号
	 * 
	 * @return
	 */
	public String getIdCardNum() {
		return idCardNum;
	}

	/**
	 * 设置边民身份证号
	 * 
	 * @param idCardNum
	 */
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	/**
	 * 获取边民真实姓名
	 * 
	 * @return
	 */
	public String getTrueName() {
		return trueName;
	}

	/**
	 * 设置边民真实姓名
	 * 
	 * @param trueName
	 */
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	/**
	 * 获取前往地区
	 * 
	 * @return
	 */
	public String getGoArea() {
		return goArea;
	}

	/**
	 * 设置前往地区
	 * 
	 * @param goArea
	 */
	public void setGoArea(String goArea) {
		this.goArea = goArea;
	}

	/**
	 * 获取发证机关
	 * 
	 * @return
	 */
	public String getIssuedAgencies() {
		return issuedAgencies;
	}

	/**
	 * 设置发证机关
	 * 
	 * @param issuedAgencies
	 */
	public void setIssuedAgencies(String issuedAgencies) {
		this.issuedAgencies = issuedAgencies;
	}

	/**
	 * 获取发证日期
	 * 
	 * @return
	 */
	public Date getIssuedDate() {
		return issuedDate;
	}

	/**
	 * 设置发证日期
	 * 
	 * @param issuedDate
	 */
	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	/**
	 * 获取有效期开始日期
	 * 
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置有效期开始日期
	 * 
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取有效期结束日期
	 * 
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置有效期结束日期
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取备注
	 * 
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置备注
	 * 
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 获取审核状态
	 * 
	 * @return
	 */
	public int getCheckState() {
		return checkState;
	}

	/**
	 * 设置审核状态
	 * 
	 * @param checkState
	 */
	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	/**
	 * 获取审核日期
	 * 
	 * @return
	 */
	public Date getCheckDate() {
		return checkDate;
	}

	/**
	 * 设置审核日期
	 * 
	 * @param checkDate
	 */
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 * 获取审核人
	 * 
	 * @return
	 */
	public String getCheckPerson() {
		return checkPerson;
	}

	/**
	 * 设置审核人
	 * 
	 * @param checkPerson
	 */
	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	/**
	 * 获取审核描述
	 * 
	 * @return
	 */
	public String getCheckDescription() {
		return checkDescription;
	}

	/**
	 * 设置审核描述
	 * 
	 * @param checkDescription
	 */
	public void setCheckDescription(String checkDescription) {
		this.checkDescription = checkDescription;
	}

	/**
	 * 获取边民属性0
	 * 
	 * @return
	 */
	public String getAttributeValue0() {
		return attributeValue0;
	}

	/**
	 * 设置边民属性0
	 * 
	 * @param attributeValue0
	 */
	public void setAttributeValue0(String attributeValue0) {
		this.attributeValue0 = attributeValue0;
	}

	/**
	 * 获取边民属性1
	 * 
	 * @return
	 */
	public String getAttributeValue1() {
		return attributeValue1;
	}

	/**
	 * 设置边民属性1
	 * 
	 * @param attributeValue1
	 */
	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	/**
	 * 获取边民属性2
	 * 
	 * @return
	 */
	public String getAttributeValue2() {
		return attributeValue2;
	}

	/**
	 * 设置边民属性2
	 * 
	 * @param attributeValue2
	 */
	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	
}
