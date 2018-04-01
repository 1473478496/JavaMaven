/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.web.merchant.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vivebest.mall.core.controller.view.BaseController;
import com.vivebest.mall.core.util.WebUtils;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.merchant.entity.TradeAdmin;

/**
 * Controller - 会员注销
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("fcexLogoutController")
public class LogoutController extends BaseController {

	/**
	 * 注销
	 */
	@RequestMapping(value = "/logout/trade", method = RequestMethod.GET)
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.removeAttribute(TradeAdmin.class.getName());
		WebUtils.removeCookie(request, response, "username");
		return "redirect:";
	}

}