package com.vivebest.mall.dao.impl;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.MemberBirthdayRigthsDao;
import com.vivebest.mall.entity.MemberBirthdayRigths;

/**
 * DaoImpl - 生日权益
 * @author ding.hy
 * @version 3.0
 *
 */
@Repository("memberBirthdayRigthsDaoImpl")
public class MemberBirthdayRigthsDaoImpl extends BaseDaoImpl<MemberBirthdayRigths, Long>
		implements MemberBirthdayRigthsDao {

	@Override
	public Page<MemberBirthdayRigths> findPage(Date beginDate, Date endDate, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MemberBirthdayRigths> criteriaQuery = criteriaBuilder.createQuery(MemberBirthdayRigths.class);
		Root<MemberBirthdayRigths> root = criteriaQuery.from(MemberBirthdayRigths.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("rightsDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("rightsDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	

}
