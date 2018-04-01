/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.Date;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.core.util.WebUtils;
import com.vivebest.mall.dao.CartDao;
import com.vivebest.mall.dao.CartItemDao;
import com.vivebest.mall.dao.MemberDao;
import com.vivebest.mall.entity.Cart;
import com.vivebest.mall.entity.CartItem;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.service.CartService;

/**
 * Service - 购物车
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("cartServiceImpl")
public class CartServiceImpl extends BaseServiceImpl<Cart, Long> implements CartService {

	@Resource(name = "cartDaoImpl")
	private CartDao cartDao;
	@Resource(name = "cartItemDaoImpl")
	private CartItemDao cartItemDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;

	@Resource(name = "cartDaoImpl")
	public void setBaseDao(CartDao cartDao) {
		super.setBaseDao(cartDao);
	}

	public Cart getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			Principal principal = (Principal) request.getSession().getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
			Member member = principal != null ? memberDao.find(principal.getId()) : null;
			if (member != null) {
				Cart cart = member.getCart();
				if (cart != null) {
					if (!cart.hasExpired()) {
						if (!DateUtils.isSameDay(cart.getModifyDate(), new Date())) {
							cart.setModifyDate(new Date());
							cartDao.merge(cart);
						}
						return cart;
					} else {
						cartDao.remove(cart);
					}
				}
			} else {
				String id = WebUtils.getCookie(request, Cart.ID_COOKIE_NAME);
				String key = WebUtils.getCookie(request, Cart.KEY_COOKIE_NAME);
				if (StringUtils.isNotEmpty(id) && StringUtils.isNumeric(id) && StringUtils.isNotEmpty(key)) {
					Cart cart = cartDao.find(Long.valueOf(id));
					if (cart != null && cart.getMember() == null && StringUtils.equals(cart.getKey(), key)) {
						if (!cart.hasExpired()) {
							if (!DateUtils.isSameDay(cart.getModifyDate(), new Date())) {
								cart.setModifyDate(new Date());
								cartDao.merge(cart);
							}
							return cart;
						} else {
							cartDao.remove(cart);
						}
					}
				}
			}
		}
		return null;
	}

	public void merge(Member member, Cart cart) {
		if (member != null && cart != null && cart.getMember() == null) {
			Cart memberCart = member.getCart();
			if (memberCart != null) {
				for (Iterator<CartItem> iterator = cart.getCartItems().iterator(); iterator.hasNext();) {
					CartItem cartItem = iterator.next();
					Product product = cartItem.getProduct();
					if (memberCart.contains(product)) {
						if (Cart.MAX_PRODUCT_COUNT != null && memberCart.getCartItems().size() > Cart.MAX_PRODUCT_COUNT) {
							continue;
						}
						CartItem item = memberCart.getCartItem(product);
						item.add(cartItem.getQuantity());
						cartItemDao.merge(item);
					} else {
						if (Cart.MAX_PRODUCT_COUNT != null && memberCart.getCartItems().size() >= Cart.MAX_PRODUCT_COUNT) {
							continue;
						}
						iterator.remove();
						cartItem.setCart(memberCart);
						memberCart.getCartItems().add(cartItem);
						cartItemDao.merge(cartItem);
					}
				}
				cartDao.remove(cart);
			} else {
				member.setCart(cart);
				cart.setMember(member);
				cartDao.merge(cart);
			}
		}
	}

	public void evictExpired() {
		cartDao.evictExpired();
	}

}