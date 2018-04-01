/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.CartItemDao;
import com.vivebest.mall.entity.CartItem;

/**
 * Dao - 购物车项
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

	public List<CartItem> findByCartId(Long id){
		String hsql="select cartItem from CartItem cartItem where lower(cartItem.cart.id)=lower(:id)";
		List<CartItem>cartItems=entityManager.createQuery(hsql, CartItem.class).setFlushMode(FlushModeType.COMMIT).setParameter("id", id).getResultList();
		if (null != cartItems && cartItems.isEmpty()) {
			return cartItems;
		}else{
			return null;
		}
	}
}