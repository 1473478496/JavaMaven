package com.vivebest.mall.web.merchant.controller;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.view.BaseController;

@Controller("merIndexController")
@RequestMapping("/")
public class IndexController extends BaseController {

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Pageable pageable, ModelMap model) {
		return "/index";
		
//		HttpSession session = request.getSession();
//		Principal tradeInfo = (Principal) session.getAttribute(TradeAdmin.class.getName());
//		if (tradeInfo == null) {
//			// 未登录
//			return "/login/login";
//		}
//
//		if (tradeInfo.getUsername() != null && tradeInfo.getUsername().toString() != "") {
//			// 已登录
//			return "/index";
//		} else {
//			// 未登录
//			return "/login/login";
//		}
	}
}
