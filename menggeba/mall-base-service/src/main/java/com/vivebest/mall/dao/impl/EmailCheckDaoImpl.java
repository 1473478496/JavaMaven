/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.EmailCheckDao;
import com.vivebest.mall.entity.EmailCheck;

/**
 * Dao - email邮件绑定
 * 
 * @author vnb zhaoshoushan
 * @version 3.0
 */
@Repository("emailCheckDaoImpl")
public class EmailCheckDaoImpl extends BaseDaoImpl<EmailCheck, Long> implements EmailCheckDao {

	@Override
	public void updateEmailSure(String emailKey) {
		if (emailKey != null) {
			String jpql = "update EmailCheck emailCheck set emailCheck.isChecked=true where emailCheck.emailKey=:emailKey";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("emailKey", emailKey).executeUpdate();
		}
	}

	@Override
	public void deleteEmailCheck(String emailKey) {
		if (emailKey != null) {
			String jpql = "delete from EmailCheck emailCheck where emailCheck.emailKey=:emailKey";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("emailKey", emailKey).executeUpdate();
		}
	}

	@Override
	public EmailCheck findByEmailKey(String emailKey) {
		List<EmailCheck> emailCheckList = null;
		EmailCheck emailCheck = null;
		if (emailKey != null) {
			String jpql = "select emailCheck from EmailCheck emailCheck where emailCheck.emailKey=:emailKey order by emailCheck.createDate desc";
			emailCheckList = entityManager.createQuery(jpql, EmailCheck.class).setFlushMode(FlushModeType.COMMIT).setParameter("emailKey", emailKey).getResultList();
		}
		if(null != emailCheckList && !emailCheckList.isEmpty()){
			emailCheck = emailCheckList.get(0);
		}
		return emailCheck;
	}

}