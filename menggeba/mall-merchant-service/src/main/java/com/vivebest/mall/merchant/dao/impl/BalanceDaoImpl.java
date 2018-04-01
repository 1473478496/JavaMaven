package com.vivebest.mall.merchant.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.merchant.dao.BalanceDao;
import com.vivebest.mall.merchant.entity.Balance;
import com.vivebest.mall.merchant.entity.Balance.Status;

@Repository("balanceDaoImpl")
public class BalanceDaoImpl extends BaseDaoImpl<Balance,Long> implements BalanceDao{

    @Override
    public List findPage(Balance balance, Integer status, String bDate, String eDate,
            Pageable pageable, Long tradeId) {
        

        StringBuffer sb = new StringBuffer();
        StringBuffer selectSql = new StringBuffer();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        selectSql.append(" SELECT b.id FROM gbm_trade_balance b WHERE 1=1 ");
        
        

        if (bDate != null && !"".equals(bDate)) {
            sb.append(" AND b.create_date >= ?1 ");
            map.put(1, bDate);
        }

        if (eDate != null && !"".equals(eDate)) {
            sb.append(" AND b.create_date <= ?2 ");
            map.put(2, eDate);
        }
        if (status != null) {
            sb.append(" AND b.status = ?3 ");
            map.put(3, status);
        }
       

        if (balance != null) {
            if (balance.getBalanceNo() != null && !"".equals(balance.getBalanceNo())) {
                sb.append(" AND b.balance_no = ?4 ");
                map.put(4, balance.getBalanceNo());
            }
            if (balance.getOperating() != null && !"".equals(balance.getOperating())) {
                sb.append(" AND b.operating = ?5 ");
                map.put(5, balance.getOperating());
            }
            if (balance.getCardName() != null && !"".equals(balance.getCardName())) {
                sb.append(" AND b.card_name= ?6 ");
                map.put(6, balance.getCardName());
            }
            if (balance.getCardNo() != null && !"".equals(balance.getCardNo())) {
                sb.append(" AND b.card_no= ?7 ");
                map.put(7, balance.getCardNo());
            }

           
        }
            if(tradeId!=null){
                sb.append(" AND b.trade_id = ?8 ");
                map.put(8, tradeId);
            }

        Query query = entityManager.createNativeQuery(selectSql.append(sb).toString());
        for (Integer key : map.keySet()) {
            query.setParameter(key, map.get(key));
        }

        List result = query.getResultList();
        return result;
    }
    
    @Override
    public Page<Balance> findById(List<Long> list, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Balance> criteriaQuery = criteriaBuilder.createQuery(Balance.class);
        Root<Balance> root = criteriaQuery.from(Balance.class);
        criteriaQuery.select(root);
        if (list.size() > 0) {
            Expression<String> exp = root.get("id");
            Predicate predicate = exp.in(list);
            criteriaQuery.where(predicate);
            return super.findPage(criteriaQuery, pageable);
        } else {

            return new Page<Balance>();
        }
    }

}
