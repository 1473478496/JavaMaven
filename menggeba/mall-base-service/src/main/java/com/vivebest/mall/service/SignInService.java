/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.Map;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.SignIn;

/**
 * Service - 签名
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface SignInService extends BaseService<SignIn, Long> {

	/**
	 * 是否有签名
	 * 
	 * @param memberId
	 *            
	 * @return 有签到返回TRUE，没有返回FALSE
	 */
	boolean isSignIn(Member member);
	
	/**
	 * 取得连续签名日期次数
	 * 
	 * @param memberId
	 *            
	 * @return 返回签名次数，没有返回0
	 */
	Map<String, Object> getAroundCount(Member member);
}