package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.ShippingCalculate;
import com.vivebest.mall.entity.ShippingMethod;

/**
 * ShippingCalculate service
 * 
 * @author junly
 *
 */
public interface ShippingCalculateService extends BaseService<ShippingCalculate, Long> {

	/**
	 * 根据发货区域类型和发货地址查找运费计算类
	 * 
	 * @param areaType
	 *            区域类型
	 * @param shippingArea
	 *            发货地址
	 * @return
	 */
	public List<ShippingCalculate> findByShipAndAreaType(ShippingCalculate.AreaType areaType, Area receArea,ShippingMethod shippingMethod);
}