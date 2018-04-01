package com.vivebest.mall.dao.impl;


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
import com.vivebest.mall.dao.BorderManDao;
import com.vivebest.mall.entity.BorderMan;

@Repository("borderManDaoImpl")
public class BorderManDaoImpl extends BaseDaoImpl<BorderMan, Long> implements BorderManDao {

	@Override
	public Page<BorderMan> findPage(int checkState, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BorderMan> criteriaQuery = criteriaBuilder.createQuery(BorderMan.class);
		Root<BorderMan> root = criteriaQuery.from(BorderMan.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(checkState != 0){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("checkState"), checkState));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery,pageable);
	}

	@Override
	public BorderMan findByTradeId(Long bmNum) {
		String jpql = "select borderMan from BorderMan borderMan where lower(borderMan.id)=lower(:bmNum)";
		try{
			return entityManager.createQuery(jpql,BorderMan.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("bmNum", bmNum).getSingleResult();				
		}catch(NoResultException e){
			return null;
		}
	}

	

}
