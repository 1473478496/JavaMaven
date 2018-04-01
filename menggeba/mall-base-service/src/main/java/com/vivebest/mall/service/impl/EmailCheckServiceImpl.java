/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.EmailCheckDao;
import com.vivebest.mall.entity.EmailCheck;
import com.vivebest.mall.service.EmailCheckService;

/**
 * Service - email绑定校验
 * 
 * @author vnb zhaoshoushan
 * @version 3.0
 */
@Service("emailCheckServiceImpl")
public class EmailCheckServiceImpl extends BaseServiceImpl<EmailCheck, Long> implements EmailCheckService {

	@Resource(name = "emailCheckDaoImpl")
	private EmailCheckDao emailCheckDao;
	
	public void save(EmailCheck emailCheck) {
		emailCheckDao.merge(emailCheck);
	}

	@Override
	public void updateEmailSure(String emailKey) {
		emailCheckDao.updateEmailSure(emailKey);
	}

	@Override
	public void deleteEmailCheck(String emailKey) {
		emailCheckDao.deleteEmailCheck(emailKey);
	}

	@Override
	public EmailCheck findByEmailKey(String emailKey) {
		return emailCheckDao.findByEmailKey(emailKey);
	}
}