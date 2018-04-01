package com.vivebest.mall.web.merchant.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.controller.view.BaseController;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.TradeAdmin;
import com.vivebest.mall.merchant.service.TradeAdminService;
@Controller("tradeCenterController")
@RequestMapping("/tradeCenter")
public class TradeCenterController extends BaseController{

    private static Logger logger = Logger.getLogger(TradeCenterController.class);
    
    @Resource(name = "tradeAdminServiceImpl")
    private TradeAdminService tradeAdminService;
    
    @RequestMapping(value = "/shopScan", method = RequestMethod.GET)
	public String shopScan(HttpServletRequest request,
			HttpServletResponse response, ModelMap model,HttpSession session)
			throws ServiceException, IOException {
		Principal principal = (Principal) session.getAttribute(TradeAdmin.class.getName());
		
		TradeAdmin tradeAdmin = tradeAdminService.findByUsername(principal.getUsername());
		Trade trade = tradeAdmin.getTrade();
		model.addAttribute("tradeAdmin",tradeAdmin);
		model.addAttribute("trade",trade);
		return "/index";
	}
}
