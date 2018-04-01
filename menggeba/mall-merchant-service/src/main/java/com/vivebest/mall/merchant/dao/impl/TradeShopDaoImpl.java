package com.vivebest.mall.merchant.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.merchant.dao.TradeShopDao;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;

@Repository("tradeShopDaoImpl")
public class TradeShopDaoImpl extends BaseDaoImpl<TradeShop, Long> implements TradeShopDao {

	@Override
	public Page findPage(Status status, PlatStatus platStatus,Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<TradeShop> criteriaQuery = criteriaBuilder.createQuery(TradeShop.class);
		Root<TradeShop> root = criteriaQuery.from(TradeShop.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(status != null){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("status"), status));
		}
		if(platStatus != null){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("platSatus"), platStatus));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery,pageable);
	}

	@Override
	public TradeShop findByTrade(Long tradeId) {
        if(tradeId==null){
        	return null;
        }
        String jpql = "select tradeshop from TradeShop tradeshop where lower(tradeshop.trade.id) = lower(:tradeId)";
		try{
			return entityManager.createQuery(jpql, TradeShop.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("tradeId", tradeId).getSingleResult();
			
		}catch(NoResultException e){
			return null;
		}
        
	}
}
