package com.vivebest.mall.merchant.service;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.merchant.entity.TradeBanks;
/**
 * 
 * @author vnb team
 *
 */
public interface TradeBanksService extends BaseService<TradeBanks, Long> {
    /**
     * 根据银行卡号查找对象
     * @param cardNo
     * @return
     */
    TradeBanks findByCardNo(String cardNo);

}
