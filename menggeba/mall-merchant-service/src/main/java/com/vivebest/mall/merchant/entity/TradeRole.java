package com.vivebest.mall.merchant.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.vivebest.mall.core.entity.BaseEntity;

@Entity
@Table(name = "gbm_trade_role")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_trade_role_sequence")
public class TradeRole extends BaseEntity {
	
	private static final long serialVersionUID = -6614052029623997372L;

	/** 名称 */
	private String name;

	/** 是否内置 */
	private Boolean isSystem;

	/** 描述 */
	private String description;

	/** 权限 */
	private List<String> authorities = new ArrayList<String>();

	/** 生活 */
	private Set<TradeAdmin> tradeAdmins = new HashSet<TradeAdmin>();

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取是否内置
	 * 
	 * @return 是否内置
	 */
	@Column(nullable = false, updatable = false)
	public Boolean getIsSystem() {
		return isSystem;
	}

	/**
	 * 设置是否内置
	 * 
	 * @param isSystem
	 *            是否内置
	 */
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	@Length(max = 200)
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 获取权限
	 * 
	 * @return 权限
	 */
	@ElementCollection
	@CollectionTable(name = "gbm_trade_role_authority")
	public List<String> getAuthorities() {
		return authorities;
	}

	/**
	 * 设置权限
	 * 
	 * @param authorities
	 *            权限
	 */
	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	/**
	 * 获取商户
	 * 
	 * @return 商户
	 */
	@ManyToMany(mappedBy = "tradeRoles", fetch = FetchType.LAZY)
	public Set<TradeAdmin> getTradeAdmins() {
		return tradeAdmins;
	}

	/**
	 * 设置商户
	 * 
	 * @param admins 商户
	 */
	public void setTradeAdmins(Set<TradeAdmin> tradeAdmins) {
		this.tradeAdmins = tradeAdmins;
	}

}
