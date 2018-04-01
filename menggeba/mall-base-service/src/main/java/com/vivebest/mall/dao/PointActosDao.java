package com.vivebest.mall.dao;
 
 
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.PointActors;

public interface PointActosDao  extends BaseDao<PointActors, Long>  {


	/**通过手机号查找
	 * @param mobile
	 * @return
	 */
	PointActors findTel(String mobile);
}
