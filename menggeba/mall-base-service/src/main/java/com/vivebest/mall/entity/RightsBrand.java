package com.vivebest.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_rights_brand")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_rights_brand_sequence")
public class RightsBrand extends BaseEntity {

	private static final long serialVersionUID = -1873352352204814530L;

	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/rightsBrand/content";

	/** 访问路径后缀 */
	private static final String PATH_SUFFIX = ".do";
	
	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	}
	
	/**
	 * 品牌简称
	 */
	private String name;
	
	/**
	 * 品牌名称
	 */
	private String fullName;
	
	
	/** 类型 */
	private Type type;
	
	 
	
	/**
	 * 品牌logo
	 */
	private String logo;
	
	/**宣传图*/
	private String advertised;
	
	/**
	 * 网址
	 */
	private String url;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/** 是否推荐 */
	private Boolean isTop;
	
	/** 是否热门 */
	private Boolean isPopularity;
	
	/** 状态*/
	private Boolean status;
	
	/**
	 * 权益品牌类型
	 */
	private RightsBrandType rightsBrandType;
	
	/** 权益分类 */
	private Set<RightsCategory> rightsCategories = new HashSet<RightsCategory>();
	
	
	private Set<RightsTrade> rightTrade = new HashSet<RightsTrade>();
	
	private Set<Rights> rights = new HashSet<Rights>();
	
	private Set<Member> members = new HashSet<Member>();
 
	
	/**
	 * @return 品牌名称
	 */
	@Column(nullable = false, updatable = true, unique = true)
	public String getName() {
		return name;
	}

	/**
	 * @param name 品牌简称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 品牌名称
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName 品牌名称
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	 
	/**
	 * @return 品牌logo
	 */
	@Length(max = 200)
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo 品牌logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 获取宣传图
	 * @return
	 */
	public String getAdvertised() {
		return advertised;
	}

	/**
	 * 设置宣传图
	 * @param advertised
	 */
	public void setAdvertised(String advertised) {
		this.advertised = advertised;
	}

	@Length(max = 200)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	/**
	 * 获取类型
	 * 
	 * @return 类型
	 */
	@NotNull
	@Column(nullable = false)
	public Type getType() {
		return type;
	}

	/**
	 * 设置类型
	 * 
	 * @param type
	 *            类型
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * 获取是否推荐
	 * @return
	 */
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否推荐
	 * @param isTop
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取是否热门
	 * @return
	 */
	public Boolean getIsPopularity() {
		return isPopularity;
	}

	/**
	 * 设置是否热门
	 * @param isPopularity
	 */
	public void setIsPopularity(Boolean isPopularity) {
		this.isPopularity = isPopularity;
	}

	/**
	 * 获取商品分类
	 * 
	 * @return 商品分类
	 */
	@ManyToMany(mappedBy = "rightsBrands", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	@OrderBy("order asc")
	public Set<RightsCategory> getRightsCategories() {
		return rightsCategories;
	}

	/**
	 * 设置商品
	 * 
	 * @param products
	 *            商品
	 */
	public void setRightsCategories(Set<RightsCategory> rightsCategories) {
		this.rightsCategories = rightsCategories;
	}

	/**
	 * 获取关注用户
	 * @return
	 */
	@ManyToMany(mappedBy = "rightsBrands", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	public Set<Member> getMembers() {
		return members;
	}

	/**
	 * 设置关注用户
	 * @param members
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
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
	
	@OneToMany(mappedBy = "rightsBrand", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) 
	public Set<RightsTrade> getRightTrade() {
		return rightTrade;
	}

	public void setRightTrade(Set<RightsTrade> rightTrade) {
		this.rightTrade = rightTrade;
	}

	@OneToMany(mappedBy = "rightsBrand", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) 
	public Set<Rights> getRights() {
		return rights;
	}
	
	
	public void setRights(Set<Rights> rights) {
		this.rights = rights;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	public RightsBrandType getRightsBrandType() {
		return rightsBrandType;
	}

	public void setRightsBrandType(RightsBrandType rightsBrandType) {
		this.rightsBrandType = rightsBrandType;
	}

	/**
	 * 获取状态
	 * @return
	 */
	public Boolean getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * @param status
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}
