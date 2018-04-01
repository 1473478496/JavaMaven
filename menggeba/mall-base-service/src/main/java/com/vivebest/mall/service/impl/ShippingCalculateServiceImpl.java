package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.ShippingCalculateDao;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.ShippingCalculate;
import com.vivebest.mall.entity.ShippingCalculate.AreaType;
import com.vivebest.mall.entity.ShippingMethod;
import com.vivebest.mall.service.ShippingCalculateService;

/**
 * ShippingCalculate Service 的实现
 * 
 * @author junly
 *
 */
@Service("shippingCalculateServiceImpl")
public class ShippingCalculateServiceImpl extends BaseServiceImpl<ShippingCalculate, Long>
		implements ShippingCalculateService {
	@Resource(name = "shippingCalculateDaoImpl")
	public void setBaseDao(ShippingCalculateDao shippingCalculateDao) {
		super.setBaseDao(shippingCalculateDao);
	}
	@Resource(name = "shippingCalculateDaoImpl")
	private ShippingCalculateDao shippingCalculateDao;

	@Override
	public List<ShippingCalculate> findByShipAndAreaType(AreaType areaType, Area receArea,ShippingMethod shippingMethod) {
		return shippingCalculateDao.findByShipAndAreaType(areaType, receArea,shippingMethod);
	}
}
