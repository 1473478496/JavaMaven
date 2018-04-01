package com.vivebest.mall.merchant.dao.impl;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.merchant.dao.TradeProxyDao;
import com.vivebest.mall.merchant.entity.TradeProxy;
@Repository("tradeProxyDaoImpl")
public class TradeProxyDaoImpl  extends BaseDaoImpl<TradeProxy, Long> implements TradeProxyDao{

}
