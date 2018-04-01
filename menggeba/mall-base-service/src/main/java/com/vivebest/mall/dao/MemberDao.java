/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao;

import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Member;


/**
 * Dao - 会员
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface MemberDao extends BaseDao<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断手机是否存在
	 * 
	 * @param mobile
	 *            
	 * @return mobile是否存在
	 */
	boolean mobileExists(String mobile);
	
	Member findByMobile(String moblie);
	
	/**
	 * 判断微信penId是否存在 
	 * 
	 * @param mobile
	 *            
	 * @return 微信penId是否存在
	 */
	boolean wxOpenIdExists(String wxOpenId);
	
	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);
	
	
	/**
	 * 通过会员SN查找Member
	 * @param username
	 * @return
	 */
	Member findUserBySn(String userSn);

	/**
	 * 根据E-mail查找会员
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByEmail(String email);

	/**
	 * 根据手机号查找会员
	 * 
	 * @param mobile
	 *            
	 * @return 会员，若不存在则返回null
	 */
	List<Member> findListByMobile(String mobile);
	
	/**
	 * 根据微信OpenId查找会员
	 * 
	 * @param wxOpenId
	 *            
	 * @return 会员，若不存在则返回null
	 */
	Member findByWxOpenId(String wxOpenId);
	
	/**
	 * 查找会员消费信息
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param count
	 *            数量
	 * @return 会员消费信息
	 */
	List<Object[]> findPurchaseList(Date beginDate, Date endDate, Integer count);

	/**
	 * 根据分页属性查找会员
	 * @param searchProperty
	 * @return
	 */
	
	Member findByToken(String token);
	
	List<Member>findBySearchProperty(Pageable pageable);
}