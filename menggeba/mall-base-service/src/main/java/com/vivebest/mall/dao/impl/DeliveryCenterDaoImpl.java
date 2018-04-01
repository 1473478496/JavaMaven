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
import com.vivebest.mall.dao.DeliveryCenterDao;
import com.vivebest.mall.entity.DeliveryCenter;

/**
 * Dao - 发货点
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("deliveryCenterDaoImpl")
public class DeliveryCenterDaoImpl extends BaseDaoImpl<DeliveryCenter, Long> implements DeliveryCenterDao {

	public DeliveryCenter findDefault() {
		try {
			String jpql = "select deliveryCenter from DeliveryCenter deliveryCenter where deliveryCenter.isDefault = true";
			return entityManager.createQuery(jpql, DeliveryCenter.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * 处理默认并保存
	 * 
	 * @param deliveryCenter
	 *            发货点
	 */
	@Override
	public void persist(DeliveryCenter deliveryCenter) {
		Assert.notNull(deliveryCenter);
		if (deliveryCenter.getIsDefault()) {
			String jpql = "update DeliveryCenter deliveryCenter set deliveryCenter.isDefault = false where deliveryCenter.isDefault = true";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
		}
		super.persist(deliveryCenter);
	}

	/**
	 * 处理默认并更新
	 * 
	 * @param deliveryCenter
	 *            发货点
	 * @return 发货点
	 */
	@Override
	public DeliveryCenter merge(DeliveryCenter deliveryCenter) {
		Assert.notNull(deliveryCenter);
		if (deliveryCenter.getIsDefault()) {
			String jpql = "update DeliveryCenter deliveryCenter set deliveryCenter.isDefault = false where deliveryCenter.isDefault = true and deliveryCenter != :deliveryCenter";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("deliveryCenter", deliveryCenter).executeUpdate();
		}
		return super.merge(deliveryCenter);
	}

}