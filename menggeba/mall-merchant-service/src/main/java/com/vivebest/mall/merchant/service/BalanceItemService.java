package com.vivebest.mall.merchant.service;

import java.util.List;

import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.merchant.entity.BalanceItem;
/**
 * 结算明细
 * @author vnb team
 * @version 1.0
 *
 */
public interface BalanceItemService extends BaseService<BalanceItem,Long>{
    /**
     * 根据结算ID查找对象
     * @return
     */
    List<BalanceItem> findByBalanceId(Long BalanceId);
    /**
     * 根据订单id查找一个对象
     * @param id
     * @return
     */
    BalanceItem findByOrderId(Long orderId);

}
