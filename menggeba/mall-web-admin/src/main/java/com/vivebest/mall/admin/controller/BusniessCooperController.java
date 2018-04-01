package com.vivebest.mall.admin.controller;
 

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.BusniessCooperate;
import com.vivebest.mall.service.BusniessCooperateService;
 

/**
 * 商户合作
 * @author flycross
 *
 */
@Controller("shopBusniessCooperController")
@RequestMapping("/admin/busniesscooper")
public class BusniessCooperController extends  BaseController {
 
	@Resource(name = "busniessCooperateServiceImpl")
	private BusniessCooperateService busniessCooperateService;

 
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,ModelMap model) {	 
		model.addAttribute("page", busniessCooperateService.findPage(pageable));
		return "/admin/busniesscooper/list";
		 
	}
	
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
    public String AuditBCById(ModelMap model,Long Id)
    {
		BusniessCooperate bscooper= busniessCooperateService.find(Id);
		model.addAttribute("busniess",bscooper);
		return "/admin/busniesscooper/audit";
    }
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
    public String ViewBCById(ModelMap model,Long Id)
    {
		BusniessCooperate bscooper= busniessCooperateService.find(Id);
		model.addAttribute("busniess",bscooper);
		return "/admin/busniesscooper/view";
    }
	
	
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	@ResponseBody Message approve(Long Id,String auditDesc)
	{
		BusniessCooperate bscooper= busniessCooperateService.find(Id);
		bscooper.setStatus(com.vivebest.mall.entity.BusniessCooperate.Status.aproved);
		bscooper.setAuditDesc(auditDesc);
		busniessCooperateService.update(bscooper);
		return SUCCESS_MESSAGE;
		
	}
	
	@RequestMapping(value = "/unapprove", method = RequestMethod.POST)
	@ResponseBody Message unapprove(Long Id,String auditDesc)
	{
		BusniessCooperate bscooper= busniessCooperateService.find(Id);
		bscooper.setStatus(com.vivebest.mall.entity.BusniessCooperate.Status.reject);
		bscooper.setAuditDesc(auditDesc);
		busniessCooperateService.update(bscooper);
		return SUCCESS_MESSAGE;
		
	}

	
}
