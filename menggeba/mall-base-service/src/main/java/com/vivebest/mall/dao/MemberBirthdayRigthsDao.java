package com.vivebest.mall.dao;

import java.util.Date;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.MemberBirthdayRigths;

/**
 * Dao - 生日权益
 * @author ding.hy
 * @version 3.0
 *
 */
public interface MemberBirthdayRigthsDao extends BaseDao<MemberBirthdayRigths, Long> {
	
	
	/**
	 * 按照权益日期查找
	 * @param beginDate
	 * 			日期起始
	 * @param endDate
	 * 			日期结束
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<MemberBirthdayRigths> findPage(Date beginDate, Date endDate, Pageable pageable);
}
