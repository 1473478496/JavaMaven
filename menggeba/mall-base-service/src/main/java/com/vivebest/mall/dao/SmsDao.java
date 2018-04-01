/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Sms;

/**
 * Dao - 短信
 * 
 * @author liu.jch
 * @date 2015/07/22
 */
public interface SmsDao extends BaseDao<Sms, Long> {
	
	/**
	 * 根据手机号与发送状态查找记录
	 * @param number
	 * @param status
	 */
	List<Sms> findByNumAndStatus(String number, int status);

	List<Sms> findLikeContent(String mobile, String code);
}