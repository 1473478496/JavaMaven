/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.SignInRule;
import com.vivebest.mall.entity.SignInRule.RuleCategory;

/**
 * Service - 签名规则
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface SignInRuleService extends BaseService<SignInRule, Long> {

	/**
	 * 根据签名规则查找赠送萌值
	 * 
	 * @param ruleCategory
	 *            
	 * @return 若不存在则返回null
	 */
	Long getGiftByCategory(RuleCategory ruleCategory);
	
	/**
	 * 根据会员签到类型取得总的赠送萌值
	 * 
	 * @param aroundCount
	 *            
	 * @return 若不存在则返回null
	 */
	Long getAllGift(Long aroundCount);
}