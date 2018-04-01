package com.vivebest.mall.merchant.service;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.Trade.Status;

public interface TradeService extends BaseService<Trade, Long> {
	
	/**
	 * 查找商户入驻申请分页
	 * @param status 申请状态
	 * @param pageable 分布信息
	 * @return
	 */
	Page findPage(Status status, Pageable pageable);
	
	/**
	 * 批准商户入驻
	 * @param trade
	 * @param admin
	 * @param auditDesc
	 */
	void approve(Trade trade,Admin admin, String auditDesc);
	/**
	 * 根据商户id查询
	 * @param tradeId
	 * @return
	 */
	 Trade findByTradeId(Long tradeId);

}
