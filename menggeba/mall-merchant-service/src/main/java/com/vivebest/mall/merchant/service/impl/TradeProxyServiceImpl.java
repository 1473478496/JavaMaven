package com.vivebest.mall.merchant.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.TradeProxyDao;
import com.vivebest.mall.merchant.entity.TradeProxy;
import com.vivebest.mall.merchant.service.TradeProxyService;
@Service("tradeProxyServiceImpl")
public class TradeProxyServiceImpl extends BaseServiceImpl<TradeProxy, Long> implements TradeProxyService{
	
	@Resource(name="tradeProxyDaoImpl")
	private TradeProxyDao tradeProxyDao;
	
	@Resource(name="tradeProxyDaoImpl")
	private void setBaseDao(TradeProxyDao tradeProxyDao){
		super.setBaseDao(tradeProxyDao);
	}
}
