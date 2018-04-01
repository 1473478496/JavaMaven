package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RightsTradeDao;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsTrade;
import com.vivebest.mall.entity.RightsTrade.TradeStatus;
@Repository("rightsTradeDaoImpl")
public class RightsTradeDaoImpl extends BaseDaoImpl<RightsTrade, Long>implements RightsTradeDao {

	@Override
	public List<RightsTrade> findAllRightsTrade(boolean limit) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsTrade> criteriaQuery = criteriaBuilder.createQuery(RightsTrade.class);
		Root<RightsTrade> root = criteriaQuery.from(RightsTrade.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(limit){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<TradeStatus>get("status"), TradeStatus.using));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.select(root);
		return findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public RightsTrade findByName(String name) {

		return entityManager.find(RightsTrade.class, name);
	}

	@Override
	public boolean snExists(String rlogin) {
		if (rlogin == null) {
			return false;
		}
		String jpql = "select count(*) from RightsTrade rightsTrade where lower(rightsTrade.rlogin) = lower(:rlogin)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("rlogin", rlogin).getSingleResult();
		return count > 0;
	}

	@Override
	public Page<RightsTrade> findPage(Pageable pageable, String areaName, String brandName) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsTrade> criteriaQuery = criteriaBuilder.createQuery(RightsTrade.class);
		Root<RightsTrade> root = criteriaQuery.from(RightsTrade.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(areaName != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("area").<String>get("fullName"), areaName));
		}
		if(brandName != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(root.get("rightsBrand").<String>get("name"), brandName));
		}
		
		criteriaQuery.where(restrictions);
		criteriaQuery.select(root);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<RightsTrade> findPage(Pageable pageable, Rights rights,
			Area area) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsTrade> criteriaQuery = criteriaBuilder.createQuery(RightsTrade.class);
		Root<RightsTrade> root = criteriaQuery.from(RightsTrade.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(rights.getId() != null){
			SetJoin<RightsTrade,Rights> setJoin  = root.join(root.getModel().getSet("rights",Rights.class), JoinType.INNER);
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(setJoin.get("id").as(Long.class), rights.getId()));
		}
		if(area != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(root.get("area").get("id").as(Long.class), area.getId())
					, criteriaBuilder.equal(root.get("area").get("parent").get("id").as(Long.class), area.getId()))
					, criteriaBuilder.equal(root.get("area").get("parent").get("parent").get("id").as(Long.class), area.getId()));
		}
		criteriaQuery.select(root);
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}
