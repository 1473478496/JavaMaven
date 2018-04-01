/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.DeliveryTemplateDao;
import com.vivebest.mall.entity.DeliveryTemplate;

/**
 * Dao - 快递单模板
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("deliveryTemplateDaoImpl")
public class DeliveryTemplateDaoImpl extends BaseDaoImpl<DeliveryTemplate, Long> implements DeliveryTemplateDao {

	public DeliveryTemplate findDefault() {
		try {
			String jpql = "select deliveryTemplate from DeliveryTemplate deliveryTemplate where deliveryTemplate.isDefault = true";
			return entityManager.createQuery(jpql, DeliveryTemplate.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * 处理默认并保存
	 * 
	 * @param deliveryTemplate
	 *            快递单模板
	 */
	@Override
	public void persist(DeliveryTemplate deliveryTemplate) {
		Assert.notNull(deliveryTemplate);
		if (deliveryTemplate.getIsDefault()) {
			String jpql = "update DeliveryTemplate deliveryTemplate set deliveryTemplate.isDefault = false where deliveryTemplate.isDefault = true";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
		}
		super.persist(deliveryTemplate);
	}

	/**
	 * 处理默认并更新
	 * 
	 * @param deliveryTemplate
	 *            快递单模板
	 * @return 快递单模板
	 */
	@Override
	public DeliveryTemplate merge(DeliveryTemplate deliveryTemplate) {
		Assert.notNull(deliveryTemplate);
		if (deliveryTemplate.getIsDefault()) {
			String jpql = "update DeliveryTemplate deliveryTemplate set deliveryTemplate.isDefault = false where deliveryTemplate.isDefault = true and deliveryTemplate != :deliveryTemplate";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("deliveryTemplate", deliveryTemplate).executeUpdate();
		}
		return super.merge(deliveryTemplate);
	}

}