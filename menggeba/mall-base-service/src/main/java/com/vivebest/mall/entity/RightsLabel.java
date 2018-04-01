package com.vivebest.mall.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 权益分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */

@Entity
@Table(name = "gbm_rights_label")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_rights_label_sequence")
public class RightsLabel extends OrderEntity {
	
	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/right/label";
	
	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";

	private static final long serialVersionUID = 1L;

	/** 权益标签 */
	private String label;
	
	/** 权益分类创建时间 */
	private Date crateTime;
	
	/** 权益分类修改时间 */
	private Date modifyTime;
	
	/** 状态，0-支持，1-不支持 */
	private Boolean status;

	
	private Rights rights;


	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Rights getRights() {
		return rights;
	}

	public void setRights(Rights rights) {
		this.rights = rights;
	}
	
}
