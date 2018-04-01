package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.ShippingCalculate;
import com.vivebest.mall.entity.ShippingMethod;

/**
 * ShippingCalculate Dao
 * 
 * @author junly
 *
 */
public interface ShippingCalculateDao extends BaseDao<ShippingCalculate, Long> {
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

//	/**
//	 * 根据发货区域类型查找运费计算类
//	 * 
//	 * @param areaType
//	 *            区域类型
//	 * @return
//	 */
//	public List<ShippingCalculate> findByAreaType(ShippingCalculate.AreaType areaType);

}
