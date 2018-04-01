/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.Returns.Status;
import com.vivebest.mall.entity.ReturnsItem;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.MessageService;
import com.vivebest.mall.service.ReturnsService;


/**
 * Controller - 退货单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminReturnsController")
@RequestMapping("/admin/returns")

public class ReturnsController extends BaseController {

	@Resource(name = "returnsServiceImpl")
	private ReturnsService returnsService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "messageServiceImpl")
	private MessageService messageService;

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		model.addAttribute("returns", returnsService.find(id));
		return "/admin/returns/view";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Status status, ReturnType returnType, com.vivebest.mall.entity.Refunds.Status refundStatus, Pageable pageable, ModelMap model) {
		model.addAttribute("status", status);
		model.addAttribute("returnType", returnType);
		model.addAttribute("refundStatus", refundStatus);
		model.addAttribute("page", returnsService.findPage(status, returnType, refundStatus, pageable));
		return "/admin/returns/list";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		returnsService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 审核
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		Returns returns=returnsService.find(id);
		model.addAttribute("returns", returns);
		List<ReturnsItem>returnsItems=returns.getReturnsItems();
		
		if (returnsItems!=null) {
			model.addAttribute("returnsItem", returnsItems.get(0));
		}else{
			model.addAttribute("returnsItem", null);
		}
		model.addAttribute("admin", adminService.getCurrent());
		return "/admin/returns/audit";
	}
	
	/**
	 * 审核通过
	 *
	 */
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public @ResponseBody Message approve(Long id,BigDecimal returnPrice, String auditDesc){
		Returns returns = returnsService.find(id);
		Admin admin = adminService.getCurrent();
		if(Status.waitingApprove.equals(returns.getStatus())){
			returnsService.approve(returns,returnPrice, admin, auditDesc);
			//审核通过退萌值。
			message(returns,"pass");
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	/**
	 * 审核拒绝
	 * 
	 */
	@RequestMapping(value = "/unapprove", method = RequestMethod.POST)
	public @ResponseBody Message unapprove(Long id, String auditDesc){
		Returns returns = returnsService.find(id);
		Admin admin = adminService.getCurrent();
		if(Status.waitingApprove.equals(returns.getStatus())){
			returns.setStatus(Status.unapprove);
			returns.setAuditApply(admin.getUsername());
			returns.setAuditDate(new Date());
			returns.setAuditDesc(auditDesc);
			
			returnsService.save(returns);
			message(returns,"notPass");
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	/**
	 * 确认收货
	 * 
	 */
	@RequestMapping(value = "/received", method = RequestMethod.GET)
	public String received(Long id, HttpServletRequest request, RedirectAttributes redirectAttributes){
		Returns returns = returnsService.find(id);
		Admin admin = adminService.getCurrent();
		
		if(Status.delivered.equals(returns.getStatus())){
			returnsService.recevied(returns, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
			return "redirect:list.jhtml";
		}
		return ERROR_VIEW;
	}

	/**
	 * 登记处理
	 * 
	 */
	@RequestMapping(value = "/dealwith", method = RequestMethod.GET)
	public String dealwith(Long id, HttpServletRequest request, RedirectAttributes redirectAttributes){
		Returns returns = returnsService.find(id);
		returns.getOrder().setOrderStatus(OrderStatus.closed);
		returns.setStatus(Status.dealwith);	
		//线下处理登记后修改退款状态为 退款成功！
		returns.getRefunds().get(0).setStatus(com.vivebest.mall.entity.Refunds.Status.refundSuccess);
		
		returns.setAuditDate(new Date());			
		returnsService.save(returns);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
		
	}
	
	public void message(Returns returns, String type){
		com.vivebest.mall.entity.Message message = new com.vivebest.mall.entity.Message();
		message.setIp("127.0.0.1");
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		message.setReceiver(returns.getMember());
		if("pass".equals(type)){
			message.setTitle("系统提示");
			if(ReturnType.directlyRefund.equals(returns.getReturnType())){
				message.setContent("订单编号为"+returns.getSn()+"的商品退款成功");
			}else if(ReturnType.returnToRefund.equals(returns.getReturnType())){
				message.setContent("订单编号为"+returns.getSn()+"的商品退货成功");
			}
		}else{
			message.setTitle("系统提示");
			if(ReturnType.directlyRefund.equals(returns.getReturnType())){
				message.setContent("订单编号为"+returns.getSn()+"的商品退款失败");
			}else if(ReturnType.returnToRefund.equals(returns.getReturnType())){
				message.setContent("订单编号为"+returns.getSn()+"的商品退货失败");
			}
		}
		messageService.save(message);
	}

}



