package com.vivebest.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * Entity - 权益商户
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Entity
@Table(name = "gbm_rights_trade")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_rights_trade_sequence")
public class RightsTrade extends BaseEntity {
	
	private static final long serialVersionUID = -4344947791512532491L;
	/**
	 * 账户状态
	 */
	public enum TradeStatus {
		/** 启用  */
		using,
		/** 停用  */
		unused
	}
	
	/**
	 * 商户登录账户
	 */
	private String rlogin;
	/**
	 * 商户名称
	 */
	private String name;
	
	/**
	 * 账户状态
	 */
	private TradeStatus status;
	
	 
	/** 地区 */
	private Area area;

	
	/**
	 * 商户地址
	 */
	private String addr;
	
	/**
	 * 商户电话
	 */
	private String phone;
	
	/**
	 * 商户logo
	 */
	private String logoUrl;
	
	/**
	 * 备注
	 */
	private String remark;
	
	
	/**
	 * 品牌
	 */
	private  RightsBrand rightsBrand;
	
	private Set<Rights> rights = new HashSet<Rights>();
	
	/**是否置顶*/
	private Boolean isTop;
	
	/**
	 * @return 获取商户账号
	 */
	public String getRlogin() {
		return rlogin;
	}
	/**
	 * @return 设置商户账号
	 */
	public void setRlogin(String rlogin) {
		this.rlogin = rlogin;
	}

	/**
	 * @return 商户名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 商户名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return 获取账户状态
	 */
	public TradeStatus getStatus() {
		return status;
	}
	/**
	 * @return 设置账户状态
	 */
	public void setStatus(TradeStatus status) {
		this.status = status;
	}
	/**
	 * @return 商户地址
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr 商户地址
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * 获取商户电话
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	
	/**
	 * 设置商户电话
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return 商户logo
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 * @param logoUrl 商户logo
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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
	
	
 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rightsBrand",  nullable = false)
	public RightsBrand getRightsBrand() {
		return rightsBrand;
	}
	public void setRightsBrand(RightsBrand rightbrand) {
		this.rightsBrand = rightbrand;
	}
	
	
	/**
	 * 获取地区
	 * 
	 * @return 地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getArea() {
		return area;
	}

	/**
	 * 设置地区
	 * 
	 * @param area
	 *            地区
	 */
	public void setArea(Area area) {
		this.area = area;
	}
	
	/**
	 * 获取是否置顶
	 * @return
	 */
	public Boolean getIsTop() {
		return isTop;
	}
	
	/**
	 * 设置是否置顶
	 * @param isTop
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}
	
	/**
	 * 获取权益
	 * @return
	 */
	@ManyToMany(mappedBy = "rightsTrades", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
	public Set<Rights> getRights() {
		return rights;
	}
	
	/**
	 * 设置权益
	 * @param rights
	 */
	public void setRights(Set<Rights> rights) {
		this.rights = rights;
	}
	
}
