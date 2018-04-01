/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.EmailCheck;

/**
 * Dao - email邮件绑定
 * 
 * @author vnb zhaoshoushan
 * @version 3.0
 */
public interface EmailCheckDao extends BaseDao<EmailCheck, Long> {

	/**
	 * 确认邮箱
	 * @param emailKey
	 */
	void updateEmailSure(String emailKey);

	/**
	 * 邮箱确认完，删除数据
	 * @param emailKey
	 */
	void deleteEmailCheck(String emailKey);

	/**
	 * 根据emailKey查询邮件验证记录
	 * @param emailKey
	 * @return
	 */
	EmailCheck findByEmailKey(String emailKey);

}