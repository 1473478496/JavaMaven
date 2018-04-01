package com.vivebest.mall.merchant.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.Trade.Status;

public interface TradeDao extends BaseDao<Trade, Long> {
	
	/**
	 * 查找商户入驻申请分页
	 * @param status 申请状态
	 * @param pageable 分页信息
	 * @return
	 */
	Page findPage(Status status, Pageable pageable);


	/**
	 * 根据商户Id查询
	 * 
	 */
	Trade findByTradeId(Long tradeId);
	
}
