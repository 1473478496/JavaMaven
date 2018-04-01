/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.AttributeDao;
import com.vivebest.mall.entity.Attribute;
import com.vivebest.mall.entity.Product;

/**
 * Dao - 属性
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("attributeDaoImpl")
public class AttributeDaoImpl extends BaseDaoImpl<Attribute, Long> implements AttributeDao {

	/**
	 * 设置propertyIndex并保存
	 * 
	 * @param attribute
	 *            属性
	 */
	@Override
	public void persist(Attribute attribute) {
		Assert.notNull(attribute);
		String jpql = "select attribute.propertyIndex from Attribute attribute where attribute.productCategory = :productCategory";
		List<Integer> propertyIndexs = entityManager.createQuery(jpql, Integer.class).setFlushMode(FlushModeType.COMMIT).setParameter("productCategory", attribute.getProductCategory()).getResultList();
		for (int i = 0; i < Product.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			if (!propertyIndexs.contains(i)) {
				attribute.setPropertyIndex(i);
				super.persist(attribute);
				break;
			}
		}
	}

	/**
	 * 清除商品属性值并删除
	 * 
	 * @param attribute
	 *            属性
	 */
	@Override
	public void remove(Attribute attribute) {
		if (attribute != null) {
			String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + attribute.getPropertyIndex();
			String jpql = "update Product product set product." + propertyName + " = null where product.productCategory = :productCategory";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("productCategory", attribute.getProductCategory()).executeUpdate();
			super.remove(attribute);
		}
	}

}