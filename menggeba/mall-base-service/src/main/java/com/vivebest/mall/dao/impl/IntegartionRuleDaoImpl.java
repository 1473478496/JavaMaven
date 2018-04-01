package com.vivebest.mall.dao.impl;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.IntegrationRuleDao;
import com.vivebest.mall.entity.IntegrationRule;

/**
 * dao-积分兑换规则
 * 
 * @author  junly
 *
 */
@Repository("integrationRuleDaoImpl")
public class IntegartionRuleDaoImpl extends BaseDaoImpl<IntegrationRule, Long>
		implements IntegrationRuleDao {

	public IntegrationRule findByCcy(String ccy) {
		String jpql = "select gir from IntegrationRule gir where lower(gir.ccy) = lower(:ccy)";
		return entityManager.createQuery(jpql, IntegrationRule.class)
				.setFlushMode(FlushModeType.COMMIT).setParameter("ccy", ccy)
				.getSingleResult();
	} 

	public boolean ccyExists(String ccy) {
		if (ccy == null) {
			return false;
		}
		String jpql = "select count(*) from IntegrationRule gir where lower(gir.ccy) = lower(:ccy)";
		Long count = entityManager.createQuery(jpql, Long.class)
				.setFlushMode(FlushModeType.COMMIT).setParameter("ccy", ccy)
				.getSingleResult();
		return count > 0;
	}

}
