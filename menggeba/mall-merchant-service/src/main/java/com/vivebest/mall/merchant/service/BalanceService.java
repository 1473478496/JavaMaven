package com.vivebest.mall.merchant.service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.merchant.entity.Balance;
import com.vivebest.mall.merchant.entity.Balance.Status;
/**
 * service 结算
 * @author vnb team
 * @version 1.0
 *
 */
public interface BalanceService extends BaseService<Balance,Long>{
    /**
     * 分页查找结算
     * @param balance
     * @param status
     * @param bDate
     * @param eDate
     * @param pageable
     * @param tradeId
     * @return
     */
    Page<Balance> findPage(Balance balance, Integer os, String bDate, String eDate, Pageable pageable, Long tradeId);


}
