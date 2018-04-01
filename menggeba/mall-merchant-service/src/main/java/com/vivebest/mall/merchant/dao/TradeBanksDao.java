package com.vivebest.mall.merchant.dao;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.merchant.entity.TradeBanks;

public interface TradeBanksDao extends BaseDao< TradeBanks, Long>{
    /**
     * 根据银行卡号查找对象
     * @param cardNo
     * @return
     */
    TradeBanks findByCardNo(String cardNo);

}
