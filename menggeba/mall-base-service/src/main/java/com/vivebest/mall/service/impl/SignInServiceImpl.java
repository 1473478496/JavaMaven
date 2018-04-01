/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.SignInDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.SignIn;
import com.vivebest.mall.service.SignInService;

/**
 * Service - 签名规则
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("signInServiceImpl")
public class SignInServiceImpl extends BaseServiceImpl<SignIn, Long> implements
		SignInService {

	@Resource(name = "signInDaoImpl")
	private SignInDao signInDao;

	@Resource(name = "signInDaoImpl")
	public void setBaseDao(SignInDao signInDao) {
		super.setBaseDao(signInDao);
	}

	@Override
	@Transactional
	public void save(SignIn signIn) {
		super.save(signIn);
	}

	@Override
	@Transactional
	public SignIn update(SignIn signIn) {
		return super.update(signIn);
	}

	@Override
	@Transactional
	public SignIn update(SignIn signIn, String... ignoreProperties) {
		return super.update(signIn, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "signInDaoImpl", allEntries = true)
	public void delete(SignIn signIn) {
		super.delete(signIn);
	}

	/**
	 * 是否有签名
	 * 
	 * @param memberId
	 * 
	 * @return 有签到返回TRUE，没有返回FALSE
	 */
	@Override
	@Transactional
	public boolean isSignIn(Member member) {
		// TODO Auto-generated method stub
		return signInDao.isSignIn(member);
	}

	/**
	 * 功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime())
					/ (24 * 60 * 60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 取得连续签名日期次数
	 * 
	 * @param memberId
	 * 
	 * @return 返回签名次数，没有返回0
	 */
	@Override
	@Transactional
	public Map<String, Object> getAroundCount(Member member) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("isSignIn", false);
		List<SignIn> listSignIn = signInDao.findAllByMember(member);
		if (listSignIn == null)
			return null;
		Long count = 0L;
		Date curDate = new Date();
		DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		for (SignIn signIn : listSignIn) {
			if (signIn != null) {
				Long day = getDaySub(format.format(signIn.getCreateDate()),
						format.format(curDate));
				if (day != 1) {
					if (day == 0) {
						count++;
						data.put("isSignIn", true);
						continue;
					}
					break;
				}
				count++;
				curDate = signIn.getCreateDate();
			}
		}
		if (count >31) // 大于31天的周期，临界的32天，需要做特殊处理，更新前31天的记录
		{

			if (signInDao.updatePreDaySignIn(member))
				count = 0l;
		}
		data.put("count", count);
		return data;
	}

}