package com.vivebest.mall.merchant.dao;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.merchant.entity.Balance;
import com.vivebest.mall.merchant.entity.Balance.Status;
/**
 * dao 结算
 * @author vnb team
 * @version 1.0
 *
 */
public interface BalanceDao extends BaseDao<Balance,Long>{
    /**
     * 分页查找结算单
     * @param balance
     * @param status
     * @param bDate
     * @param eDate
     * @param pageable
     * @param tradeId
     * @return
     */
    List findPage(Balance balance, Integer status, String bDate, String eDate, Pageable pageable,
            Long tradeId);
    /**
     * 根据id查询订单列表
     * @param list
     * @return
     */
    Page<Balance> findById(List<Long> list,Pageable pageable);

}
