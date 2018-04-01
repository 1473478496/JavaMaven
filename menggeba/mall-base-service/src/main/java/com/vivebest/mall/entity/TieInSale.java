package com.vivebest.mall.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vivebest.mall.core.entity.BaseEntity;

/**
 * 商品搭配销售实体类
 * 
 * @author junly
 *
 */
@Entity
@Table(name = "gbm_tie_in_sale")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_tie_in_sale_sequence")
public class TieInSale extends BaseEntity {

	private static final long serialVersionUID = -1307743303786909390L;
	// 主商品
	private Product product;

	private Product  productSuit;
	// 搭配标题
	private TieInSaleTitle tieInSaleTitle;

	// 搭配商品
	private Product telIn;
	
	// 备注
	private String remark;

	/**
	 * 获取主商品
	 * 
	 * @return 主商品
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	public Product getProduct() {
		return product;
	}

	/**
	 * 获取优惠套装商品
	 * 
	 * @return 优惠套装商品
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	public Product getProductSuit() {
		return productSuit;
	}

	/**
	 * 设置优惠套装商品
	 * @param productSuit 优惠套装商品
	 */
	public void setProductSuit(Product productSuit) {
		this.productSuit = productSuit;
	}

	/**
	 * 设置主商品
	 * 
	 * @param product
	 *            主商品
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 获取搭配标题
	 * 
	 * @return 搭配标题
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tie_in_sale_title", nullable = false, updatable = false)
	public TieInSaleTitle getTieInSaleTitle() {
		return tieInSaleTitle;
	}

	/**
	 * 设置搭配标题
	 * 
	 * @param telInTitle
	 *            搭配标题
	 */
	public void setTieInSaleTitle(TieInSaleTitle tieInSaleTitle) {
		this.tieInSaleTitle = tieInSaleTitle;
	}

	/**
	 * 获取搭配商品
	 * 
	 * @return 搭配商品
	 */
	@JsonProperty
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	public Product getTelIn() {
		return telIn;
	}

	/**
	 * 设置搭配商品
	 * 
	 * @param telIn
	 *            搭配商品
	 */
	public void setTelIn(Product telIn) {
		this.telIn = telIn;
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
