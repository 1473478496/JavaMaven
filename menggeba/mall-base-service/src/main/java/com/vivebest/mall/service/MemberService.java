/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.APIMessage;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Member;


/**
 * Service - 会员
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface MemberService extends BaseService<Member, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);
	
	/**
	 * 消费金额累计
	 * @param m
	 * @param paidAmt
	 */
	public void consumeCount(Member m,String paidAmt);

	/**
	 * 判断用户名是否禁用
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否禁用
	 */
	boolean usernameDisabled(String username);

	/**
	 * 判断E-mail是否存在
	 * 
	 * @param email
	 *            E-mail(忽略大小写)
	 * @return E-mail是否存在
	 */
	boolean emailExists(String email);

	/**
	 * 判断E-mail是否唯一
	 * 
	 * @param previousEmail
	 *            修改前E-mail(忽略大小写)
	 * @param currentEmail
	 *            当前E-mail(忽略大小写)
	 * @return E-mail是否唯一
	 */
	boolean emailUnique(String previousEmail, String currentEmail);

	
	/**
	 * 判断手机是否存在
	 * 
	 * @param mobile
	 *            
	 * @return mobile是否存在
	 */
	boolean mobileExists(String mobile);
	
	/**
	 * 判断微信penId是否存在 
	 * 
	 * @param mobile
	 *            
	 * @return 微信penId是否存在
	 */
	boolean wxOpenIdExists(String wxOpenId);
		
	/**
	 * 保存会员
	 * 
	 * @param member
	 *            会员
	 * @param operator
	 *            操作员
	 */
	void save(Member member, Admin operator);
	
	Member findByMobile(String moblie);

	/**
	 * 更新会员
	 * 
	 * @param member
	 *            会员
	 * @param modifyPoint
	 *            修改积分
	 * @param modifyBalance
	 *            修改余额
	 * @param depositMemo
	 *            修改余额备注
	 * @param operator
	 *            操作员
	 */
	void update(Member member, Integer modifyPoint, BigDecimal modifyBalance, String depositMemo, Admin operator);

	/**
	 * 根据用户名查找会员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 会员，若不存在则返回null
	 */
	Member findByUsername(String username);
	
	/**
	 * 根据用户sn查找会员
	 * @param usersn
	 * @return
	 */
	Member findUserBySn(String usersn);

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
	 * 判断会员是否登录
	 * 
	 * @return 会员是否登录
	 */
	boolean isAuthenticated();

	/**
	 * 获取当前登录会员
	 * 
	 * @return 当前登录会员，若不存在则返回null
	 */
	Member getCurrent();

	/**
	 * 获取当前登录用户名
	 * 
	 * @return 当前登录用户名，若不存在则返回null
	 */
	String getCurrentUsername();
	
	/**
	 * 读取导入EXCEL用户
	 * @param inp
	 * @return
	 */
	List<Member> readExcelMember(InputStream inp);
	
	/**
	 * 获取导入EXCEL相关错误
	 * @return
	 */
	String getInclusionErrList();
	
	/**
	 * 根据分页属性查找会员
	 * @param searchProperty
	 * @return
	 */
	List<Member>findBySearchProperty(Pageable pageable);
	
	APIMessage findByToken(String token);
	
	String createToken();

}