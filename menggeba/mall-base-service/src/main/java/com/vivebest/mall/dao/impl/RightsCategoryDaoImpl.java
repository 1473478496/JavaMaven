package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RightsCategoryDao;
import com.vivebest.mall.entity.RightsCategory;

/**
 * RightsCategoryDaoImpl - 权益分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("rightsCategoryDaoImpl")
public class RightsCategoryDaoImpl  extends BaseDaoImpl<RightsCategory, Long> implements RightsCategoryDao{

	@Override
	public RightsCategory findById(Long id) {
		if(id != null){
			return entityManager.find(RightsCategory.class, id);
		}
		return null;
	}

	@Override
	public List<RightsCategory> findAllRightsCategory() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsCategory> criteriaQuery = criteriaBuilder.createQuery(RightsCategory.class);
		criteriaQuery.select(criteriaQuery.from(RightsCategory.class));
		return findList(criteriaQuery, null, null, null, null);
	}
	
	@Override
	public RightsCategory findByName(String name) {
		if (name == null) {
			return null;
		}
		try {
			String jpql = "select rightsCategory from RightsCategory rightsCategory where (rightsCategory.name)  = :name";
			return entityManager.createQuery(jpql, RightsCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public RightsCategory findById(int id) {
		if (id == 0) {
			return null;
		}
		try {
			String jpql = "select rightsCategory from RightsCategory rightsCategory where (rightsCategory.id)  = :id";
			return entityManager.createQuery(jpql, RightsCategory.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", id).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<RightsCategory> findRoots(Integer count) {
		String jpql = "select rightsCategory from RightsCategory rightsCategory  order by rightsCategory.id asc";
		TypedQuery<RightsCategory> query = entityManager.createQuery(jpql, RightsCategory.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

}
