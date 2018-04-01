package com.vivebest.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * 商品搭配分类  实体类
 * 
 * @author junly
 *
 */
@Entity
@Table(name = "gbm_tie_in_sale_catetory")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_tie_in_sale_catetory_sequence")
public class TieInSaleCatetory extends BaseEntity {
	private static final long serialVersionUID = -1307743303786909390L;
	/**
	 * 类别
	 * 
	 * @author junly
	 *
	 */
	public enum Category {

		// 0-推荐配件
		recommendAccessories,
		// 1-优惠套装
		preferentialSuit,
		// 2-最佳组合
		bestCombination

	}

	// 类别
	private Category category;
	// 类别名称
	private String name;
	// 备注
	private String remark;
	/**
	 * 商品搭配销售标题
	 */
	private Set<TieInSaleTitle> tieInSaleTitles = new HashSet<TieInSaleTitle>();

	/**
	 * 获取商品搭配销售标题
	 * 
	 * @return 商品搭配销售标题
	 */
	@OneToMany(mappedBy = "tieInSaleCatetory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@OrderBy("createDate asc")
	public Set<TieInSaleTitle> getTieInSaleTitles() {
		return tieInSaleTitles;
	}

	/**
	 * 设置商品搭配销售标题
	 * 
	 * @param tieInSaleTitles
	 *            商品搭配销售标题
	 */
	public void setTieInSaleTitles(Set<TieInSaleTitle> tieInSaleTitles) {
		this.tieInSaleTitles = tieInSaleTitles;
	}

	/**
	 * 获取类别
	 * 
	 * @return 类别
	 */
	@Column(nullable = false)
	public Category getCategory() {
		return category;
	}

	/**
	 * 设置类别
	 * 
	 * @param category
	 *            类别
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * 获取 类别名称
	 * 
	 * @return 类别名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 类别名称
	 * 
	 * @param name
	 *            类别名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 备注
	 * 
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
