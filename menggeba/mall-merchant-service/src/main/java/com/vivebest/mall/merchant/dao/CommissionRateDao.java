package com.vivebest.mall.merchant.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.merchant.entity.CommissionRate;
/**
 * dao 佣金比率
 * @author vnb team
 * @version 1.0
 *
 */
public interface CommissionRateDao extends BaseDao<CommissionRate,Long>{
    /**
     * 按类型状态查询佣金比率
     * @param type
     * @param status
     * @return
     */
    CommissionRate findCommissionRate(Short type, Integer status);


}
