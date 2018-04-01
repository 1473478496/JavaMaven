/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.core.util.WebUtils;
import com.vivebest.mall.entity.Cart;
import com.vivebest.mall.entity.CartItem;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.service.CartService;
import com.vivebest.mall.service.MemberService;

/**
 * Interceptor - 积分余额
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public class IntegralBalanceInterceptor extends HandlerInterceptorAdapter {

	private static Logger logger = Logger.getLogger(IntegralBalanceInterceptor.class);
	
	@Value("${pay.acceptBizNo}")
	private String acceptBizNo;

	@Value("${member.queryBudAccBal.url}")
	private String balance_url;

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cartServiceImpl")
	private CartService cartService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Member member = memberService.getCurrent();
		if(member != null){
			if (getBudAccBal(member.getSn(), balance_url) != null) {
				String pricePoint = String.valueOf(getBudAccBal(member.getSn(), balance_url).get("totalBal"));
				pricePoint = pricePoint.substring(0, pricePoint.lastIndexOf("."));
				WebUtils.addCookie(request, response, "pricePoint", pricePoint);
			} else {
				WebUtils.addCookie(request, response, "pricePoint", "0");
			}
			WebUtils.addCookie(request, response, "username", member.getUsername());
		}else{
			Cart cart = cartService.getCurrent();
			WebUtils.removeCookie(request, response, Member.USERNAME_COOKIE_NAME);
			WebUtils.removeCookie(request, response, "pricePoint");
			if (cart == null) {
				WebUtils.removeCookie(request, response, "quantity");
			}
		}
		Cart c = cartService.getCurrent();
		Integer quantity = 0;
		CartItem cartItem = null;
		if (c != null) {
			Set<CartItem> cartItems = c.getCartItems();
			Iterator<CartItem> it = cartItems.iterator();
			while (it.hasNext()) {
				cartItem = it.next();
				if("booking".equals(cartItem.getCartType()) || "direct".equals(cartItem.getCartType())){
					continue;
				}
				quantity += cartItem.getQuantity();
			}
		}
		WebUtils.addCookie(request, response, "quantity", quantity.toString());
		return true;
	}
	
	/**
	 * 调用积分引擎借口获取蒙值余额
	 * 
	 * @param username
	 *            会员名
	 * @param txCode
	 * @return
	 */
	private Map<String, Object> getBudAccBal(String sn, String txCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merCustNo", sn);
		params.put("acceptBizNo", acceptBizNo);
		String resMsg = null;
		try {
			resMsg = HttpClientUtil.postJson(txCode, params, "UTF-8");
		} catch (JsonProcessingException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("获取积分失败：", e);
			return null;
		}

		// 处理返回结果
		Map<String, Object> resMap = null;
		try {
			resMap = new ObjectMapper().readValue(resMsg, Map.class);
		} catch (JsonParseException e) {
			logger.error("JSON解析失败：", e);
			return null;
		} catch (JsonMappingException e) {
			logger.error("JSON映射失败：", e);
			return null;
		} catch (IOException e) {
			logger.error("获取蒙值失败：", e);
			return null;
		}
		List<Object> resList = (List<Object>) resMap.get("accBalList");

		Map<String, Object> temMap = null;
		if (resList != null) {
			Iterator<Object> it = resList.iterator();
			while (it.hasNext()) {
				temMap = (Map<String, Object>) it.next();
			}
		}
		return temMap;
	}
}