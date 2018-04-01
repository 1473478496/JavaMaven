package com.vivebest.mall.merchant.dao;

import java.util.List;

import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.merchant.entity.BalanceItem;
/**
 * dao 结算单明细
 * @author vnb team
 * @version 1.0
 *
 */
public interface BalanceItemDao extends BaseDao<BalanceItem,Long>{
    /**
     * 根据结算ID查找对象
     * @param balanceId
     * @return
     */
    List<BalanceItem> findByBalanceId(Long balanceId);
    /**
     * 根据订单ID查找一个对象
     * @param orderId
     * @return
     */
    BalanceItem findByOrderId(Long orderId);

}
