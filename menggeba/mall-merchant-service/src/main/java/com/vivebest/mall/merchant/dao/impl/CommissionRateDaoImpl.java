package com.vivebest.mall.merchant.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.merchant.dao.CommissionRateDao;
import com.vivebest.mall.merchant.entity.CommissionRate;

/**
 * daoImpl 佣金比率
 * 
 * @author vnb team
 * @version 1.0
 *
 */
@Repository("commissionRateDaoImpl")
public class CommissionRateDaoImpl extends BaseDaoImpl<CommissionRate, Long> implements CommissionRateDao {

    @Override
    public CommissionRate findCommissionRate(Short type, Integer status) {
        
        if (type == null) {
            return null;
        }
        if (status == null) {
            return null;
        }

        String jpql = "select commissionRate from CommissionRate commissionRate where lower(commissionRate.type)=lower(:type) AND lower(commissionRate.status)=lower(:status)";
        try {
            return entityManager.createQuery(jpql, CommissionRate.class).setFlushMode(FlushModeType.COMMIT)
                    .setParameter("type", type).setParameter("status", status).getResultList().get(0);
        } catch (NoResultException e) {
            return null;
        }
    }

}
