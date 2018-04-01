package com.vivebest.mall.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.IntegrationRule;

/**
 * service-积分兑换规则
 * 
 * @author  junly
 *
 */
public interface IntegrationRuleService extends
		BaseService<IntegrationRule, Long> {
	/**
	 * 判断币种是否存在
	 * 
	 * @param ccy
	 * 
	 * @return 币种是否存在
	 */
	boolean ccyExists(String ccy);

	/**
	 * 根据币种查找积分兑换规则
	 * 
	 * @param ccy
	 *            币种
	 * @return 币种兑换规则对象
	 */
	IntegrationRule findByCcy(String ccy);

}
