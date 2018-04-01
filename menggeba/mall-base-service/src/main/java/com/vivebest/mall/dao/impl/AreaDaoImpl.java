/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.AreaDao;
import com.vivebest.mall.entity.Area;

/**
 * Dao - 地区
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, Long> implements AreaDao {

	public List<Area> findRoots(Integer count) {
		String jpql = "select area from Area area where area.parent is null order by area.order asc";
		TypedQuery<Area> query = entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public Area findByFullName(String fullname) {
		// TODO Auto-generated method stub
		if (fullname == null) {
			return null;
		}
		String jpql = "select area from Area  area where area.fullName  like :name";
		try {
			return entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", "%"+fullname+"%").getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Area findByFullNa(String fullname) {
		if (fullname == null) {
			return null;
		}
		String jpql = "select area from Area  area where lower(area.fullName)=lower(:fullname)";
		try {
			return entityManager.createQuery(jpql, Area.class).setFlushMode(FlushModeType.COMMIT).setParameter("fullname", fullname).getResultList().get(0);
		} catch (NoResultException e) {
			return null;
		}
	}
}