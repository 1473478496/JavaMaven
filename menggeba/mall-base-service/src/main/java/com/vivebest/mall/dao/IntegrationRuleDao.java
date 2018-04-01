package com.vivebest.mall.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.IntegrationRule;

/**
 * dao-积分兑换 
 * 
 * @author   junly
 *
 */
public interface IntegrationRuleDao extends BaseDao<IntegrationRule, Long> {
	
	/**
	 * 根据币种查找查找积分兑换规则
	 * 
	 * @param ccy
	 *           币种
	 * @return
	 *        币种兑换规则对象
	 */
	IntegrationRule findByCcy(String ccy);
	
	/**
	 * 判断币种是否存在
	 * 
	 * @param ccy
	 *           
	 * @return 币种是否存在
	 */
	boolean ccyExists(String ccy);

}
