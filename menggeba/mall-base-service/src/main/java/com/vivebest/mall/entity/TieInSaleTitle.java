package com.vivebest.mall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * 商品搭配销售标题实体类
 * 
 * @author junly
 *
 */
@Entity
@Table(name = "gbm_tie_in_sale_title")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_tie_in_sale_title_sequence")
public class TieInSaleTitle extends BaseEntity {
	private static final long serialVersionUID = -1307743303786909390L;
	// 搭配商品分类
	private TieInSaleCatetory tieInSaleCatetory;
	// 子标题
	private String title;
	// 备注
	private String remark;
	
	/**
	 * 商品搭配销售
	 */
	private Set<TieInSale> tieInSales = new HashSet<TieInSale>();

	/**
	 * 获取商品搭配销售
	 * 
	 * @return 商品搭配销售
	 */
	@OneToMany(mappedBy = "tieInSaleTitle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("createDate asc")
	public Set<TieInSale> getTieInSales() {
		return tieInSales;
	}

	/**
	 * 设置 商品搭配销售
	 * 
	 * @param tieInSales
	 *            商品搭配销售
	 */
	public void setTieInSales(Set<TieInSale> tieInSales) {
		this.tieInSales = tieInSales;
	}


	/**
	 * 获取搭配商品分类ID
	 * 
	 * @return 搭配商品分类ID
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tie_in_sale_catetory", nullable = false, updatable = false)
	public TieInSaleCatetory getTieInSaleCatetory() {
		return tieInSaleCatetory;
	}

	/**
	 * 设置搭配商品分类ID
	 * 
	 * @param telInCatetoryId
	 *            搭配商品分类ID
	 */
	public void setTieInSaleCatetory(TieInSaleCatetory tieInSaleCatetory) {
		this.tieInSaleCatetory = tieInSaleCatetory;
	}

	/**
	 * 获取子标题
	 * 
	 * @return 子标题
	 */
	@JsonProperty
	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置子标题
	 * 
	 * @param title
	 *            子标题
	 */
	public void setTitle(String title) {
		this.title = title;
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
