package com.vivebest.mall.service;
 
 
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.PointActors;
 

public interface PointActosService extends BaseService<PointActors, Long>  {

	 

	/**
	 * 通过手机号查找
	 * @param mobile
	 * @return
	 */
	PointActors findTel(String mobile);

	/**
	 * 新用积分临时存储
	 * @param coupon
	 * @param mobile
	 * @return
	 */
	Boolean newActo(Integer point, String mobile);
}
