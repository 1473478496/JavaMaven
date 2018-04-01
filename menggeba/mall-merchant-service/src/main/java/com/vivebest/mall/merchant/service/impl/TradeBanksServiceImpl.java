package com.vivebest.mall.merchant.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.TradeBanksDao;
import com.vivebest.mall.merchant.dao.impl.TradeBanksDaoImpl;
import com.vivebest.mall.merchant.entity.TradeBanks;
import com.vivebest.mall.merchant.service.TradeBanksService;
@Service("tradeBanksServiceImpl")
public class TradeBanksServiceImpl extends BaseServiceImpl<TradeBanks, Long> implements TradeBanksService{

	
	@Resource(name="tradeBanksDaoImpl")
	private TradeBanksDao  tradeBanksDao;
	
	@Resource(name="tradeBanksDaoImpl")
	private void setBaseDao(TradeBanksDao  TradeBanksDao){
		super.setBaseDao(TradeBanksDao);
	}

    @Override
    public TradeBanks findByCardNo(String cardNo) {
        return tradeBanksDao.findByCardNo(cardNo);
    }
}
