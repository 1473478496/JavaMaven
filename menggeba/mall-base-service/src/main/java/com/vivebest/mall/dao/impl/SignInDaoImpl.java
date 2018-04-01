/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.SignInDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.SignIn;

/**
 * Dao - 签到规则类型
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("signInDaoImpl")
public class SignInDaoImpl extends BaseDaoImpl<SignIn, Long> implements
		SignInDao {

	/**
	 * 取得会员当天签名信息
	 * 
	 * @param memberId
	 * 
	 * @return 有则返回True，否则返回False
	 */
	@Override
	public boolean isSignIn(Member member) {
		// TODO Auto-generated method stub
		if (member == null) {
			return false;
		}
		String jpql = "select count(*) from SignIn sign where sign.createDate>= :signDate and sign.member = :member";
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		Long count;
		try {
			count = entityManager.createQuery(jpql, Long.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("signDate", matter.parse(matter.format(dt)))
					.setParameter("member", member).getSingleResult();
			return count > 0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 取得会员最近连续签名的30条信息
	 * 
	 * @param memberId
	 * 
	 * @return 返回最近连续签名的30条信息
	 */
	@Override
	public List<SignIn> findAllByMember(Member member) {
		// TODO Auto-generated method stub
		if (member == null) {
			return null;
		}

		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String jpql = "select sign from SignIn sign where sign.createDate < :signDate  and sign.remark=null  and sign.member = :member order by sign.createDate desc";
		TypedQuery<SignIn> query;
		try {
			query = entityManager
					.createQuery(jpql, SignIn.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("signDate", matter.parse(matter.format(dt)))
					.setParameter("member", member);
			
			return query.getResultList();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 连续签到满32天一个周期，更新前31一天的记录
	 * @param member
	 * @return
	 */
	@Override
	public boolean updatePreDaySignIn(Member member) {
		// TODO Auto-generated method stub
		if (member == null) {
			return false;
		} 
		String jpql = "update SignIn sign  set sign.remark='signover-31mday'  where  sign.createDate> :presigndate and sign.member = :member";
	
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);		
		try {
			   entityManager.createQuery(jpql)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("presigndate", matter.parse(matter.format(date.getTime()))) //更新前天的记录
					.setParameter("member", member)
                    .executeUpdate();
			   return true;
		 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
}