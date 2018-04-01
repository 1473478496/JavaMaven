package com.vivebest.mall.merchant.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.BalanceDao;
import com.vivebest.mall.merchant.entity.Balance;
import com.vivebest.mall.merchant.entity.Balance.Status;
import com.vivebest.mall.merchant.service.BalanceService;

@Service("balanceServiceImpl")
public class BalanceServiceImpl extends BaseServiceImpl<Balance,Long> implements BalanceService{
    @Resource(name = "balanceDaoImpl")
    public void setBaseDao(BalanceDao balanceDao) {
        super.setBaseDao(balanceDao);
    }
    @Resource(name="balanceDaoImpl")
    private BalanceDao balanceDao;
    @Override
    public Page<Balance> findPage(Balance balance, Integer status, String bDate, String eDate,
            Pageable pageable, Long tradeId) {
        
        List list = balanceDao.findPage(balance,status,bDate,eDate,pageable,tradeId);
        
        return balanceDao.findById(list, pageable);
    }

}
