package com.vivebest.mall.merchant.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.merchant.dao.BalanceItemDao;
import com.vivebest.mall.merchant.entity.BalanceItem;
import com.vivebest.mall.merchant.entity.CommissionRate;
@Repository("balanceItemDaoImpl")
public class BalanceItemDaoImpl extends BaseDaoImpl<BalanceItem,Long> implements BalanceItemDao{

    @Override
    public List<BalanceItem> findByBalanceId(Long balanceId) {
        
        if (balanceId == null) {
            return null;
        }
        

        String jpql = "select balanceItem from BalanceItem balanceItem where lower(balanceItem.balanceId)=lower(:balanceId)";
        try {
            return entityManager.createQuery(jpql, BalanceItem.class).setFlushMode(FlushModeType.COMMIT)
                    .setParameter("balanceId", balanceId).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    
    }

    @Override
    public BalanceItem findByOrderId(Long orderId) {
        if (orderId == null){
            return null;
        }
        
        String jpql = "select balanceItem from BalanceItem balanceItem where lower(balanceItem.orderId)=lower(:orderId)";
        try {
            return entityManager.createQuery(jpql, BalanceItem.class).setFlushMode(FlushModeType.COMMIT)
                    .setParameter("orderId", orderId).getResultList().get(0);
        } catch (NoResultException e) {
            return null;
        }
    }

}
