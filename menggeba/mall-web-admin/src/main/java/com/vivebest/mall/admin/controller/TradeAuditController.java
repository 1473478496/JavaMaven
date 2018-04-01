package com.vivebest.mall.admin.controller;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.merchant.entity.Trade;
import com.vivebest.mall.merchant.entity.Trade.Status;
import com.vivebest.mall.merchant.service.TradeService;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.MessageService;

@Controller("tradeApplyController")
@RequestMapping("/admin/tradeAudit")
public class TradeAuditController extends BaseController {
	
	@Resource(name="tradeServiceImpl")
	private TradeService tradeService;
    @Resource(name="adminServiceImpl")
	private AdminService adminService;
    @Resource(name = "messageServiceImpl")
	private MessageService messageService;
	
	/**
	 * 查看
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String view(Long id,ModelMap model){
		model.addAttribute("trade",tradeService.find(id));
		return "/admin/mechant/view";
	}
	
	/**
	 * 列表
	 * @param status
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET )
	public String list(Status status,Pageable pageable,ModelMap model){
		model.addAttribute("status",status);
		Page page = tradeService.findPage(status,pageable);
		model.addAttribute("page",page);
		return "/admin/mechant/list";
	}
	
	/**
	 * 审核
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/audit",method=RequestMethod.GET)
	public String edit(Long id,ModelMap model){
		Trade trade = tradeService.find(id);
		model.addAttribute("trade",trade);
		model.addAttribute("admin",adminService.getCurrent());
		return "/admin/mechant/audit";
	}
	
	/**
	 * 审核通过
	 * @param id
	 * @param auditDesc
	 * @return
	 */
	@RequestMapping(value="/approve",method=RequestMethod.POST)
	public @ResponseBody Message approve(Long id, String auditDesc){
		Trade trade = tradeService.find(id);
		Admin admin = adminService.getCurrent();
		if(Status.waitingApprove.equals(trade.getStatus())){
			tradeService.approve(trade,admin, auditDesc);
			
			message(trade, "pass");
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	/**
	 * 审核拒绝
	 * @param id
	 * @param auditDesc
	 * @return
	 */
	@RequestMapping(value = "/unapprove",method = RequestMethod.POST)
	public @ResponseBody Message unapprove(Long id,String auditDesc){
		Trade trade = tradeService.find(id);
		Admin admin = adminService.getCurrent();
		if(Status.waitingApprove.equals(trade.getStatus())){
			trade.setStatus(Status.unapprove);
			trade.setAuditApply(admin.getUsername());
			trade.setAuditDate(new Date());
			trade.setStatus_desc(auditDesc);
			
			tradeService.save(trade);
			message(trade,"notPass");
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	public void message(Trade trade, String type){
		com.vivebest.mall.entity.Message message = new com.vivebest.mall.entity.Message();
		message.setIp("127.0.0.1");
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		if("pass".equals(type)){
			message.setTitle("系统提示");
			message.setContent("商户编号为"+trade.getNo()+"的客户申请入驻成功");
		}else{
			message.setTitle("系统提示");
			message.setContent("商户编号为"+trade.getNo()+"的客户申请入驻失败");
		}
		messageService.save(message);
	}
}
