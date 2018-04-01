package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.CopounActors;
import com.vivebest.mall.entity.Coupon;
 

public interface CouponActosService extends BaseService<CopounActors, Long>  {

	/**
	 * 生成临时券
	 * @param coupon
	 * @param Tel
	 * @return
	 */
	Boolean build(Coupon coupon, String Tel);
	
	/**
	 * 统计账户下券数目
	 * @param coupon
	 * @param Tel
	 * @return
	 */
	Long count(Coupon coupon,String Tel);

	/**
	 * 通过手机号查找
	 * @param mobile
	 * @return
	 */
	CopounActors findTel(String mobile);

	/**
	 * 新用领券户临时表
	 * @param coupon
	 * @param mobile
	 * @return
	 */
	Boolean newActo(Coupon coupon, String mobile);
}
