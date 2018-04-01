package com.vivebest.mall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * 换货订单项 --实体类
 * 
 * @author junly
 *
 */
@Entity
@Table(name = "gbm_change_item")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_change_item_sequence")
public class ChangesItem extends BaseEntity {
	private static final long serialVersionUID = -4999926022604479334L;

	// 商品名称
	private String name;

	// 数量
	private Integer quantity;

	// 编号
	private String sn;

	// 换货单
	private Changes changes;

	/**
	 * 获取商品名称
	 * 
	 * @return 商品名称
	 */
	@JsonProperty
	@Column(nullable = false, updatable = false)
	public String getName() {
		return name;
	}

	/**
	 * 设置商品名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取数量
	 * 
	 * @return 数量
	 */
	@JsonProperty
	@NotNull
	@Min(1)
	@Max(10000)
	@Column(nullable = false)
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * 设置订单商品数量
	 * 
	 * @param quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * 获取商品编号
	 * 
	 * @return 商品编号
	 */
	@JsonProperty
	@NotEmpty
	@Column(nullable = false, updatable = false)
	public String getSn() {
		return sn;
	}

	/**
	 * 设置商品编号
	 * 
	 * @param sn
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 获取还货单
	 * 
	 * @return
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "changes", nullable = false, updatable = false)
	public Changes getChanges() {
		return changes;
	}

	/**
	 * 设置还货单
	 * 
	 * @param changes
	 */
	public void setChanges(Changes changes) {
		this.changes = changes;
	}

}
