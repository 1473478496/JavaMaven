package com.vivebest.mall.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.MemberBirthdayRigths;

/**
 * Service - 生日权益
 * @author ding.hy
 * @version 3.0
 *
 */
public interface MemberBirthdayRigthsService extends BaseService<MemberBirthdayRigths, Long> {
	/**
	 * 发送通知
	 * 
	 * @param ids
	 *            ID
	 * @return 发送通知数
	 */
	int send(Long[] ids, HttpServletRequest request);
	
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
