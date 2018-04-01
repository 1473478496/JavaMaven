package com.vivebest.mall.merchant.service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;

/**
 * 商户店铺
 * @author Terry
 *
 */
public interface TradeShopService extends BaseService<TradeShop, Long> {

	/**
	 * 分页
	 * @param status
	 * @param pageable
	 * @return
	 */
	Page findPage(Status status, PlatStatus platStatus, Pageable pageable);

	/**
	 * 关店
	 * @param tradeShop
	 */
	void close(TradeShop tradeShop);

	/**
	 * 开店
	 * @param tradeShop
	 */
	void open(TradeShop tradeShop);
	
	/**
	 * 根据商户Id查询店铺
	 * 
	 */
	TradeShop findByTrade(Long tradeId);
}
