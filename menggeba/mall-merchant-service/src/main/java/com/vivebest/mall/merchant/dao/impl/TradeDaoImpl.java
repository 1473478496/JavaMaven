package com.vivebest.mall.merchant.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.merchant.dao.TradeDao;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.Trade.Status;

@Repository("tradeDaoImpl")
public class TradeDaoImpl extends BaseDaoImpl<Trade, Long> implements TradeDao {

	@Override
	public Page findPage(Status status, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Trade> criteriaQuery = criteriaBuilder.createQuery(Trade.class);
		Root<Trade> root = criteriaQuery.from(Trade.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(status != null){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("status"), status));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery,pageable);
	}

	@Override
	public Trade findByTradeId(Long tradeId) {

	        String jpql = "select trade from Trade trade where lower(trade.id)=lower(:tradeId)";
			try{
				return entityManager.createQuery(jpql,Trade.class).setFlushMode(FlushModeType.COMMIT)
						.setParameter("tradeId", tradeId).getSingleResult();				
			}catch(NoResultException e){
				return null;
			}
	}

}
