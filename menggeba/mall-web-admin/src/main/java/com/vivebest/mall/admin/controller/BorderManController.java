package com.vivebest.mall.admin.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.BorderMan;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.BorderManService;
import com.vivebest.mall.service.MessageService;
@Controller("borderPeopleController")
@RequestMapping("/admin/border")
public class BorderManController extends BaseController {
	@Resource(name="borderManServiceImpl")
	private BorderManService borderManService;
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
		model.addAttribute("trade",borderManService.findByTradeId(id));
		return "/admin/border/view";
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("checkState",new BorderMan().getCheckState());
		model.addAttribute("page", borderManService.findPage(pageable));
		return "/admin/border/list";
	}
	
	/**
	 * 审核
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/audit",method=RequestMethod.GET)
	public String edit(Long id,ModelMap model){
		BorderMan borderMan = borderManService.find(id);
		model.addAttribute("borderMan",borderMan);
		model.addAttribute("admin",adminService.getCurrent());
		return "/admin/border/audit";
	}
	
	/**
	 * 审核通过
	 * @param id
	 * @param checkDescription
	 * @return
	 */
	@RequestMapping(value="/approve",method=RequestMethod.POST)
	public @ResponseBody Message approve(Long id, String checkDescription){
		BorderMan borderMan = borderManService.find(id);
		Admin admin = adminService.getCurrent();
		if(00==borderMan.getCheckState()){
			borderManService.approve(borderMan,admin, checkDescription);
			message(borderMan, "pass");
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	/**
	 * 审核拒绝
	 * @param id
	 * @param checkDescription
	 * @return
	 */
	@RequestMapping(value = "/unapprove",method = RequestMethod.POST)
	public @ResponseBody Message unapprove(Long id,String checkDescription){
		BorderMan borderMan = borderManService.find(id);
		Admin admin = adminService.getCurrent();
		if(00==borderMan.getCheckState()){
			borderMan.setCheckState(00);
			borderMan.setCheckPerson(admin.getUsername());
			borderMan.setCheckDate(new Date());
			borderMan.setComment(checkDescription);
			borderManService.save(borderMan);
			message(borderMan,"notPass");
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	public void message(BorderMan borderMan, String type){
		com.vivebest.mall.entity.Message message = new com.vivebest.mall.entity.Message();
		message.setIp("127.0.0.1");
		message.setIsDraft(false);
		message.setSenderRead(true);
		message.setReceiverRead(false);
		message.setSenderDelete(false);
		message.setReceiverDelete(false);
		if("pass".equals(type)){
			message.setTitle("系统提示");
			message.setContent("边民证号为"+borderMan.getBmNum()+"的会员审核成功");
		}else{
			message.setTitle("系统提示");
			message.setContent("边民证号为"+borderMan.getBmNum()+"的会员审核失败");
		}
		messageService.save(message);
	}
}
