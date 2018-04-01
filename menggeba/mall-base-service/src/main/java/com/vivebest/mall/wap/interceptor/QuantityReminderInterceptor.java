/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.wap.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Filter.Operator;
import com.vivebest.mall.core.util.WebUtils;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.service.CouponCodeService;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.MessageService;
import com.vivebest.mall.service.ReceiverService;
import com.vivebest.mall.service.SignInService;

/**
 * Interceptor - 会员权限
 * 
 * @author vnb zhaoshoushan
 * @version 3.0
 */
public class QuantityReminderInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	@Resource(name = "receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name = "signInServiceImpl")
	private SignInService signInService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Member member = memberService.getCurrent();
		if(member != null){
			WebUtils.addCookie(request, response, "favoriteNum", String.valueOf(member.getFavoriteProducts().size()));
			WebUtils.addCookie(request, response, "couponNum", String.valueOf(couponCodeService.count(null, member, null, false, false)));
			WebUtils.addCookie(request, response, "rightsNum", String.valueOf(member.getRightsOrders().size()));
			WebUtils.addCookie(request, response, "messageNum", String.valueOf(messageService.count(member, false)));
			Filter filter = new Filter("member", Operator.eq, member);
			WebUtils.addCookie(request, response, "receiverNum", String.valueOf(receiverService.count(filter)));
			WebUtils.addCookie(request, response, "brandNum", String.valueOf(member.getRightsBrands().size()));
			WebUtils.addCookie(request, response, "isSignIn", String.valueOf(signInService.getAroundCount(member).get("isSignIn")));
		}
		return true;
	}

}