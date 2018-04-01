/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.EmailCheck;

/**
 * Service - email绑定校验
 * 
 * @author vnb zhaoshoushan
 * @version 3.0
 */
public interface EmailCheckService extends BaseService<EmailCheck, Long> {
	/**
	 * 保存邮件绑定数据
	 */
	void save(EmailCheck emailCheck);

	/**
	 * 确认email
	 * @param emailKey
	 */
	void updateEmailSure(String emailKey);

	/**
	 * email校验完成，删除记录
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