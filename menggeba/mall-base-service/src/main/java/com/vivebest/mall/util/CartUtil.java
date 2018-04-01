package com.vivebest.mall.util;

import java.util.Iterator;
import java.util.Set;

import com.vivebest.mall.entity.Cart;
import com.vivebest.mall.entity.CartItem;


/**
 * 购物车工具类
 * @author ding.hy
 *
 */
public class CartUtil {
	
	/**
	 * 判断购物车中是否含有缺货商品
	 * @param cart 购物车
	 * @return
	 */
	public static Boolean isOutOfStock(Cart cart){
		boolean isOutOfStock = false;
		if(cart != null){
			Set<CartItem> cartItems = cart.getCartItems();
			if(cart != null && !cartItems.isEmpty()) {
				cartItems = cart.getCartItems();
				Iterator<CartItem> it = cartItems.iterator();
				while(it.hasNext()) {
					isOutOfStock = it.next().getProduct().getIsOutOfStock();
					if(isOutOfStock){
						break;
					}
				}
			}
			return isOutOfStock;
		}
		return !isOutOfStock;
	}
}
