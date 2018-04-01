package com.vivebest.mall.merchant.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.merchant.dao.TradeBanksDao;
import com.vivebest.mall.merchant.entity.BalanceItem;
import com.vivebest.mall.merchant.entity.TradeBanks;
@Repository("tradeBanksDaoImpl")
public class TradeBanksDaoImpl extends BaseDaoImpl<TradeBanks, Long> implements TradeBanksDao{

    @Override
    public TradeBanks findByCardNo(String cardNo) {
        
        if (cardNo == null){
            return null;
        }
        
        String jpql = "select tradeBanks from TradeBanks tradeBanks where lower(tradeBanks.card_no)=lower(:cardNo)";
        try {
            return entityManager.createQuery(jpql, TradeBanks.class).setFlushMode(FlushModeType.COMMIT)
                    .setParameter("cardNo", cardNo).getResultList().get(0);
        } catch (NoResultException e) {
            return null;
        }
    
    }

}
