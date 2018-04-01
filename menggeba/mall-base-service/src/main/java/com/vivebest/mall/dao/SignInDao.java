/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.SignIn;

/**
 * Dao - 签名
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface SignInDao extends BaseDao<SignIn, Long> {

	/**
	 * 取得会员当天签名信息
	 * 
	 * @param memberId
	 *            
	 * @return 有则返回当天签到信息
	 */
	boolean isSignIn(Member member);
	
	/**
	 * 取得会员最近连续签名的30条信息
	 * 
	 * @param memberId
	 *            
	 * @return 返回最近连续签名的30条信息
	 */
	List<SignIn> findAllByMember(Member member);
	
	/**
	 * 连续签到满32天一个周期，更新前31一天的记录
	 * @param member
	 * @return
	 */
	 boolean updatePreDaySignIn(Member member);
}