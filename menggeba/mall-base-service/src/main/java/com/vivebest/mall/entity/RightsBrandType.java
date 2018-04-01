package com.vivebest.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_rights_brand_type")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_rights_brand_type_sequence")
public class RightsBrandType extends BaseEntity {

	private static final long serialVersionUID = -1873352352204814530L;

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/brand/content";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";
	
	private String name;
	
	private Set<RightsBrand> rightsBrand = new HashSet<RightsBrand>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "rightsBrandType", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) 
	public Set<RightsBrand> getRightsBrand() {
		return rightsBrand;
	}

	public void setRightsBrand(Set<RightsBrand> rightsBrand) {
		this.rightsBrand = rightsBrand;
	}
	
	
	
	
	 
}
