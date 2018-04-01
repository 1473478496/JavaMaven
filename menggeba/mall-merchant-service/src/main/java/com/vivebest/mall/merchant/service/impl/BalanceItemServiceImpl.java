package com.vivebest.mall.merchant.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.BalanceItemDao;
import com.vivebest.mall.merchant.entity.BalanceItem;
import com.vivebest.mall.merchant.service.BalanceItemService;
/**
 * 
 * @author vnb team
 * @version 1.0
 *
 */
@Service("balanceItemServiceImpl")
public class BalanceItemServiceImpl extends BaseServiceImpl<BalanceItem,Long> implements BalanceItemService{
    @Resource(name = "balanceItemDaoImpl")
    public void setBaseDao(BalanceItemDao balanceItemDao) {
        super.setBaseDao(balanceItemDao);
    }
    @Resource(name="balanceItemDaoImpl")
    private BalanceItemDao balanceItemDao;
    
    @Override
    public List<BalanceItem> findByBalanceId(Long BalanceId) {
        
        return balanceItemDao.findByBalanceId(BalanceId);
    }

    @Override
    public BalanceItem findByOrderId(Long orderId) {
        
        return balanceItemDao.findByOrderId(orderId);
    }

}
