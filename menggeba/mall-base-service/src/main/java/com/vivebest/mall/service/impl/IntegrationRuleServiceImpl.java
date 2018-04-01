package com.vivebest.mall.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.IntegrationRuleDao;
import com.vivebest.mall.entity.IntegrationRule;
import com.vivebest.mall.service.IntegrationRuleService;

/**
 * service-积分兑换规则
 * 
 * @author   junly
 *
 */
@Service("integrationRuleServiceImpl")
public class IntegrationRuleServiceImpl extends BaseServiceImpl<IntegrationRule, Long> implements IntegrationRuleService {
	
	@Resource(name = "integrationRuleDaoImpl")
	private IntegrationRuleDao integrationRuleDao;

	@Resource(name = "integrationRuleDaoImpl")
	public void setBaseDao(IntegrationRuleDao integrationRuleDao) {
		super.setBaseDao(integrationRuleDao);
	}

	@Transactional(readOnly = true)
	public IntegrationRule findByCcy(String ccy) {
		return integrationRuleDao.findByCcy(ccy);
	}


	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public IntegrationRule find(Long id) {
		// TODO Auto-generated method stub
		return super.find(id);
	}

	@Override
	@Transactional
	public List<IntegrationRule> findAll() {
		// TODO Auto-generated method stub
		return super.findAll();
	}

	@Override
	@Transactional
	public void delete(IntegrationRule integrationRule) {
		// TODO Auto-generated method stub
		super.delete(integrationRule);
	}

	@Override
	@Transactional
	public void save(IntegrationRule integrationRule) {
		// TODO Auto-generated method stub
		super.save(integrationRule);
	}

	@Override
	@Transactional
	public IntegrationRule update(IntegrationRule integrationRule) {
		// TODO Auto-generated method stub
		return super.update(integrationRule);
	}

	@Override
	public boolean ccyExists(String ccy) {
		// TODO Auto-generated method stub
		
		return integrationRuleDao.ccyExists(ccy);
	}

}
