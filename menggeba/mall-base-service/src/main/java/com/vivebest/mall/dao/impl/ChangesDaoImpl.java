package com.vivebest.mall.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ChangesDao;
import com.vivebest.mall.entity.Changes;
import com.vivebest.mall.entity.Changes.Status;
import com.vivebest.mall.entity.Member;
@Repository("changesDaoImpl")
public class ChangesDaoImpl extends BaseDaoImpl<Changes, Long> implements ChangesDao{

	@Override
	public Changes findByOrderItem(Long id) {
		String jpql = "select changes from Changes changes where lower(changes.orderItem.id) = lower(:id)";
		List<Changes> changes=entityManager.createQuery(jpql,Changes.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", id).getResultList();
		if(null != changes && changes.isEmpty()){
			return null;
		}
		else{
			return changes.get(0);
		}
	}

	@Override
	public Changes findBySn(String sn) {
		String jpql = "select changes from Changes changes where lower(changes.sn) = lower(:sn)";
		return entityManager.createQuery(jpql,Changes.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
	}

	@Override
	public Page<Changes> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Changes>(Collections.<Changes> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Changes> criteriaQuery = criteriaBuilder.createQuery(Changes.class);
		Root<Changes> root = criteriaQuery.from(Changes.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Changes changes,Status [] statuses){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Changes> criteriaQuery = criteriaBuilder.createQuery(Changes.class);
		Root<Changes> root = criteriaQuery.from(Changes.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (changes != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("changes"), changes));
		}
		for (Status status : statuses) {
			if(status == Status.pending){
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), 0));
			}
			if(status == Status.delivered){
				restrictions = criteriaBuilder.or(restrictions, criteriaBuilder.equal(root.get("status"), 3));
			}

		}
		
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}
	
}
