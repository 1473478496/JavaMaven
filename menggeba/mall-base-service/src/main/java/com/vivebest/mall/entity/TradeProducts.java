package com.vivebest.mall.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.vivebest.mall.core.entity.BaseEntity;
/**
 * 商品类型
 * @author du
 *
 */
@Entity
@Table(name="gbm_trade_products")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "trade_products_sequence")
public class TradeProducts extends BaseEntity{

	private static final long serialVersionUID = 7861235212455428594L;
	
 
	/**商户id */
	private Long trade_id;
	
	/**  商品类别  id */
	private Long product_category_id;
	/** 备注*/
	private String  remark;
	
	public Long getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
	}
	public Long getProduct_category_id() {
		return product_category_id;
	}
	public void setProduct_category_id(Long product_category_id) {
		this.product_category_id = product_category_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
