package com.vivebest.mall.merchant.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.merchant.entity.CommissionRate;
/**
 * service 佣金比率
 * @author vnb team
 * @version 1.0
 *
 */
public interface CommissionRateService extends BaseService<CommissionRate,Long>{
    /**
     * 按类型状态查询佣金比率
     * @param type
     * @param status
     * @return
     */
    CommissionRate findCommissionRate(Short type,Integer status);

}
