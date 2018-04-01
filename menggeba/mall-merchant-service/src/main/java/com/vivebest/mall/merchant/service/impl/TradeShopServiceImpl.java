package com.vivebest.mall.merchant.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.TradeShopDao;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;
import com.vivebest.mall.merchant.service.TradeShopService;

@Service("tradeShopServiceImpl")
public class TradeShopServiceImpl extends BaseServiceImpl<TradeShop, Long> implements TradeShopService {

	@Resource(name = "tradeShopDaoImpl")
	private TradeShopDao tradeShopDao;

	
	@Resource(name = "tradeShopDaoImpl")
	public void setBaseDao(TradeShopDao tradeShopDao) {
		super.setBaseDao(tradeShopDao);
	}


	@Override
	public Page findPage(Status status, PlatStatus platStatus,Pageable pageable) {
		return tradeShopDao.findPage(status, platStatus,pageable);
	}


	@Override
	public void close(TradeShop tradeShop) {
		Assert.notNull(tradeShop);
		tradeShop.setPlatStatus(PlatStatus.close);
		tradeShop.setStatus(Status.close);
		tradeShopDao.merge(tradeShop);
		tradeShopDao.flush();
	}


	@Override
	public void open(TradeShop tradeShop) {
		Assert.notNull(tradeShop);
		tradeShop.setPlatStatus(PlatStatus.open);
		tradeShop.setStatus(Status.open);
		tradeShopDao.merge(tradeShop);
		tradeShopDao.flush();
	}


	@Override
	public TradeShop findByTrade(Long tradeId) {
		return tradeShopDao.findByTrade(tradeId);
	}
}
