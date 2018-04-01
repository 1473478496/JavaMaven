package com.vivebest.mall.merchant.dao;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;

public interface TradeShopDao extends BaseDao<TradeShop, Long> {


	/**
	 * 分页
	 * @param status
	 * @param pageable
	 * @return
	 */
	Page findPage(Status status,PlatStatus platStatus, Pageable pageable);


	/**
	 * 根据商户Id查询店铺
	 * 
	 */
	TradeShop findByTrade(Long tradeId);
}
