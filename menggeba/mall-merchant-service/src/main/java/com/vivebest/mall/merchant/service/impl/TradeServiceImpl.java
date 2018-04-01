package com.vivebest.mall.merchant.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.merchant.dao.TradeDao;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.Trade.Status;
import com.vivebest.mall.merchant.service.TradeService;

@Service("tradeServiceImpl")
public class TradeServiceImpl extends BaseServiceImpl<Trade, Long> implements TradeService {

	@Resource(name = "tradeDaoImpl")
	private TradeDao tradeDao;

	
	@Resource(name = "tradeDaoImpl")
	public void setBaseDao(TradeDao tradeDao) {
		super.setBaseDao(tradeDao);
	}


	@Override
	public void approve(Trade trade, Admin admin, String auditDesc) {
		Assert.notNull(trade);
		trade.setStatus(Status.approved);
		trade.setAuditApply(admin.getUsername());
		trade.setAuditDate(new Date());
		trade.setStatus_desc(auditDesc);
	    tradeDao.merge(trade);
	    tradeDao.flush();
	}

	@Transactional(readOnly=true)
	@Override
	public Page findPage(Status status, Pageable pageable) {
		return tradeDao.findPage(status,pageable);
	}

	@Override
	public Trade findByTradeId(Long tradeId) {
		return tradeDao.findByTradeId(tradeId);
	}
	
	
}
