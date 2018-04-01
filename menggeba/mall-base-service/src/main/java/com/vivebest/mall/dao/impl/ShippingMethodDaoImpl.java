/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ShippingMethodDao;
import com.vivebest.mall.entity.ShippingMethod;

/**
 * Dao - 配送方式
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("shippingMethodDaoImpl")
public class ShippingMethodDaoImpl extends BaseDaoImpl<ShippingMethod, Long> implements ShippingMethodDao {

	public ShippingMethod findByName(String name){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ShippingMethod> criteriaQuery = criteriaBuilder.createQuery(ShippingMethod.class);
		Root<ShippingMethod> root = criteriaQuery.from(ShippingMethod.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (name != null) {
			restrictions = criteriaBuilder.like(root.<String> get("name"), "%" + name + "%");
		}
		criteriaQuery.where(restrictions);
		
		if (super.findList(criteriaQuery, null, null, null, null).size()>0) {
			return super.findList(criteriaQuery, null, null, null, null).get(0);
		}else{
			return null;
		}
	}
	
}