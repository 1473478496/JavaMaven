package com.vivebest.mall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

/**
 * Entity - 权益分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */

@Entity
@Table(name = "gbm_rights_category")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_rights_category_sequence")
public class RightsCategory extends OrderEntity {
	
	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/right/list";
	
	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";

	private static final long serialVersionUID = 1L;

	/** 权益分类名称 */
	private String name;
	
	/** 权益分类创建时间 */
	private Date crateTime;
	
	/** 权益分类修改时间 */
	private Date modifyTime;
	
	/** 权益分类备注*/
	private String remark;

	/**
	 * 权益分类
	 */
	private Set<RightsBrand> rightsBrands= new HashSet<RightsBrand>();
	
	/**
	 * @return 分类名称
	 */
	public String getName() {
		return name;
	}

	@Length(max = 200)
	@Column(nullable = false)
	public void setName(String name) {
		this.name = name;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取筛选品牌
	 * 
	 * @return 筛选品牌
	 */
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@JoinTable(name = "gbm_rights_category_brand",
	joinColumns = {@JoinColumn(name = "rights_categories", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "rights_brands",referencedColumnName = "id")})
	@OrderBy("createDate asc")
	public Set<RightsBrand> getRightsBrands() {
		return rightsBrands;
	}

	/**
	 * 设置筛选品牌
	 * 
	 * @param brands
	 *            筛选品牌
	 */
	public void setRightsBrands(Set<RightsBrand> rightsBrands) {
		this.rightsBrands = rightsBrands;
	}

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getId() != null) {
			return PATH_PREFIX + "/" + getId() + PATH_SUFFIX;
		}
		return null;
	}

}
