package com.vivebest.mall.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.vivebest.mall.core.entity.BaseEntity;

/**
 * 运费计算类
 * 
 * @author junly
 *
 */
@Entity
@Table(name = "gbm_shipping_calculate")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gbm_shipping_calculate_sequence")
public class ShippingCalculate extends BaseEntity {
	private static final long serialVersionUID = -6565967051825794561L;

	/**
	 * 区域类型
	 *
	 */
	public enum AreaType {
		// 0-同城
		sameCity,
		// 1-同省
		sameProvince,
		// 2-跨省
		transProvince
	}

	// 区域类型
	private AreaType areaType;
	
	// 配送方式
	private ShippingMethod shippingMethod;
	
	// 发货地区
	private Area shippingArea;
	
	// 收货地区
	private Area consigneeArea;
	
	// 首重费用
	private BigDecimal firstPrice;
	
	// 首重(公斤)
	private BigDecimal firstWeight;
	
	// 0.5公斤续重费用
	private BigDecimal nextHalfWeight;
	
	// 1公斤续重费用
	private BigDecimal nextWeight;
	
	// 备注
	private String memo;

	/**
	 * 获取区域类型
	 * 
	 * @return 区域类型
	 */
	@Column(nullable = false)
	public AreaType getAreaType() {
		return areaType;
	}

	/**
	 * 设置区域类型
	 * 
	 * @param areaType
	 *            区域类型
	 */
	public void setAreaType(AreaType areaType) {
		this.areaType = areaType;
	}

	/**
	 * 获取配送方式
	 * 
	 * @return 配送方式
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	/**
	 * 设置配送方式
	 * 
	 * @param shippingMethod
	 *            配送方式
	 */
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	/**
	 * 获取发货地区
	 * 
	 * @return 发货地区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getShippingArea() {
		return shippingArea;
	}

	/**
	 * 设置发货地区
	 * 
	 * @param shippingArea
	 *            发货地区
	 */
	public void setShippingArea(Area shippingArea) {
		this.shippingArea = shippingArea;
	}

	/**
	 * 获取收货地区
	 * 
	 * @return 收货地区
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	public Area getConsigneeArea() {
		return consigneeArea;
	}

	/**
	 * 设置收货地区
	 * 
	 * @param consigneeArea
	 *            收货地区
	 */
	public void setConsigneeArea(Area consigneeArea) {
		this.consigneeArea = consigneeArea;
	}

	/**
	 * 获取首重价格
	 * 
	 * @return 首重价格
	 */
	@NotNull
	@Min(0)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	/**
	 * 设置首重价格
	 * 
	 * @param firstPrice
	 *            首重价格
	 */
	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	/**
	 * 获取首重
	 * 
	 * @return 首重
	 */
	@NotNull
	@Min(0)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	/**
	 * 设置首重
	 * 
	 * @param firstWeight
	 *            首重
	 */
	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	/**
	 * 获取0.5公斤续重费用
	 * 
	 * @return 0.5公斤续重费用
	 */
	@NotNull
	@Min(0)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getNextHalfWeight() {
		return nextHalfWeight;
	}

	/**
	 * 设置0.5公斤续重费用
	 * 
	 * @param nextHalfWeight
	 *            0.5公斤续重费用
	 */
	public void setNextHalfWeight(BigDecimal nextHalfWeight) {
		this.nextHalfWeight = nextHalfWeight;
	}

	/**
	 * 获取1公斤续重费用
	 * 
	 * @return 1公斤续重费用
	 */
	
	@NotNull
	@Min(0)
	@Column(nullable = false, precision = 21, scale = 6)
	public BigDecimal getNextWeight() {
		return nextWeight;
	}

	/**
	 * 设置1公斤续重费用
	 * 
	 * @param nextWeight
	 *            1公斤续重费用
	 */
	public void setNextWeight(BigDecimal nextWeight) {
		this.nextWeight = nextWeight;
	}

	/**
	 * 获取备注
	 * 
	 * @return 备注
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置备注
	 * 
	 * @param memo
	 *            备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
