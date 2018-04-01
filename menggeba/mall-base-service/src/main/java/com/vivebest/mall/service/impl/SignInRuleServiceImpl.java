/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.SignInRuleDao;
import com.vivebest.mall.entity.SignInRule;
import com.vivebest.mall.entity.SignInRule.RuleCategory;
import com.vivebest.mall.service.SignInRuleService;

/**
 * Service - 签名规则
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("signInRuleServiceImpl")
public class SignInRuleServiceImpl extends BaseServiceImpl<SignInRule, Long>
		implements SignInRuleService {

	@Resource(name = "signInRuleDaoImpl")
	public void setBaseDao(SignInRuleDao signInRuleDao) {
		super.setBaseDao(signInRuleDao);
	}

	@Override
	@Transactional
	public void save(SignInRule signInRule) {
		super.save(signInRule);
	}

	@Override
	@Transactional
	public SignInRule update(SignInRule signInRule) {
		return super.update(signInRule);
	}

	@Override
	@Transactional
	public SignInRule update(SignInRule signInRule, String... ignoreProperties) {
		return super.update(signInRule, ignoreProperties);
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
	public void delete(SignInRule signInRule) {
		super.delete(signInRule);
	}

	/**
	 * 根据签名规则查找赠送萌值
	 * 
	 * @param ruleCategory
	 * 
	 * @return 若不存在则返回null
	 */
	@Override
	@Transactional
	public Long getGiftByCategory(RuleCategory ruleCategory) {
		// TODO Auto-generated method stub
		if (ruleCategory == null) {
			return null;
		}
		Long gift = null;
		List<SignInRule> listSignInRule = super.findAll();
		for (SignInRule signInRule : listSignInRule) {
			if (ruleCategory == signInRule.getSignRule()) {
				gift = signInRule.getGift();
				break;
			}
		}
		return gift;
	}

	/**
	 * 根据连续签到次数，取得对应端送萌值
	 * 
	 * @param aroundCount
	 * @return
	 */
	private Long getGiftByAroundCount(Long aroundCount) {
		RuleCategory ruleCategory = null;

		if (aroundCount > 6 && aroundCount <= 14)
			ruleCategory = RuleCategory.around7;
		else if (aroundCount > 14 && aroundCount <= 29)
			ruleCategory = RuleCategory.around15;
		else if (aroundCount > 29 && aroundCount <= 31)
			ruleCategory = RuleCategory.around30;
		else
			ruleCategory = RuleCategory.normal;
		Long gift = getGiftByCategory(ruleCategory);
		if (gift == null)
			gift = 0L;
		return gift;
	}

	/**
	 * 根据会员签到类型取得总的赠送萌值
	 * 
	 * @param ruleCategory
	 * 
	 * @return 若不存在则返回null
	 */
	@Override
	@Transactional
	public Long getAllGift(Long aroundCount) {
		// TODO Auto-generated method stub
		// 取得正常赠送值
		/*
		 * Long gift = getGiftByCategory(RuleCategory.normal); if (aroundCount
		 * <= 0) return gift; // 取得额外赠送值 if (gift == null) gift =
		 * getGiftByAroundCount(aroundCount); else gift = gift +
		 * getGiftByAroundCount(aroundCount);
		 */

		Long gift = getGiftByAroundCount(aroundCount);

		if (gift == null)
			gift = 0L;
		return gift;
	}

}