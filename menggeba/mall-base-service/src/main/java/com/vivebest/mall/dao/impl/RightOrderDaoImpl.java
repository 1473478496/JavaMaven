package com.vivebest.mall.dao.impl;

import java.util.Date;
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
import com.vivebest.mall.dao.RightOrderDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.RightOrder;
import com.vivebest.mall.entity.RightOrder.RightsOrderStatus;

/**
 * RightOrderDaoImpl - 权益订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("rightsOrderDaoImpl")
public class RightOrderDaoImpl extends BaseDaoImpl<RightOrder, Long> implements RightOrderDao {

	@Override
	public RightOrder findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select orders from RightOrder orders where lower(orders.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, RightOrder.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			throw e;
			//return null;
		}
	}

	@Override
	public Page<RightOrder> findPage(RightsOrderStatus orderStatus, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightOrder> criteriaQuery = criteriaBuilder.createQuery(RightOrder.class);
		Root<RightOrder> root = criteriaQuery.from(RightOrder.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("rightsOrderStatus"), orderStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<RightOrder> findPage(RightsOrderStatus orderStatus, Boolean hasExpired, Member member,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
		CriteriaQuery<RightOrder>criteriaQuery=criteriaBuilder.createQuery(RightOrder.class);
		Root<RightOrder>root=criteriaQuery.from(RightOrder.class);
		criteriaQuery.select(root);
		Predicate restrictions=criteriaBuilder.conjunction();
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("rightsOrderStatus"), orderStatus));
		}
		if (member!=null) {
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("member"),member));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<RightOrder> findList(RightsOrderStatus orderStatus,
			Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightOrder> criteriaQuery = criteriaBuilder.createQuery(RightOrder.class);
		Root<RightOrder> root = criteriaQuery.from(RightOrder.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (orderStatus != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("rightsOrderStatus"), orderStatus));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

}
