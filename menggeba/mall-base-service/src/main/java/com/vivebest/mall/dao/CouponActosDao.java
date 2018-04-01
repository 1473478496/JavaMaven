package com.vivebest.mall.dao;
 
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.CopounActors;
import com.vivebest.mall.entity.Coupon;

public interface CouponActosDao  extends BaseDao<CopounActors, Long>  {

	Long count(Coupon coupon,String Tel);

	/**通过手机号查找
	 * @param mobile
	 * @return
	 */
	CopounActors findTel(String mobile);
}
