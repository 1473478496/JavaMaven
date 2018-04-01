package com.vivebest.mall.merchant.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.merchant.dao.CommissionRateDao;
import com.vivebest.mall.merchant.entity.CommissionRate;
import com.vivebest.mall.merchant.service.CommissionRateService;
/**
 * serviceImpl 佣金比率
 * @author vnb team
 * @version 1.0
 *
 */
@Service("commissionRateServiceImpl")
public class CommissionRateServiceImpl extends BaseServiceImpl<CommissionRate,Long> implements CommissionRateService{
    @Resource(name = "commissionRateDaoImpl")
    public void setBaseDao(CommissionRateDao commissionRateDao) {
        super.setBaseDao(commissionRateDao);
    }
    @Resource(name="commissionRateDaoImpl")
    CommissionRateDao commissionRateDao;

    @Override
    public CommissionRate findCommissionRate(Short type, Integer status) {
         
         
        return commissionRateDao.findCommissionRate(type,status);
         
    }

}
