/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.entity.MemberRankRigths;
import com.vivebest.mall.service.MemberRankRigthsService;
import com.vivebest.mall.service.MemberRankService;

/**
 * Controller - 会员等级
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminMemberRankController")
@RequestMapping("/admin/member_rank")
public class MemberRankController extends BaseController {

	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "memberRankRigthsServiceImpl")
	private MemberRankRigthsService memberRankRigthsService;
	

	/**
	 * 检查名称是否唯一
	 */
	@RequestMapping(value = "/check_name", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkName(String previousName, String name) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		if (memberRankService.nameUnique(previousName, name)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查消费金额是否唯一
	 */
	@RequestMapping(value = "/check_amount", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkAmount(BigDecimal previousAmount, BigDecimal amount) {
		if (amount == null) {
			return false;
		}
		if (memberRankService.amountUnique(previousAmount, amount)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/member_rank/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(MemberRank memberRank, RedirectAttributes redirectAttributes, 
			Long priceConcessions, Long[] birthdayPrivilege, Long customerService, Long newTrialArea ) {
		if (!isValid(memberRank)) {
			return ERROR_VIEW;
		}
		if (memberRankService.nameExists(memberRank.getName())) {
			return ERROR_VIEW;
		}
		if (memberRank.getIsSpecial()) {
			memberRank.setAmount(null);
		} else if (memberRank.getAmount() == null || memberRankService.amountExists(memberRank.getAmount())) {
			return ERROR_VIEW;
		}
		memberRank.setMembers(null);
		memberRank.setPromotions(null);
		memberRankService.save(memberRank);
		memberRankRigthsService.updateMemberRankRigths(memberRank, priceConcessions, birthdayPrivilege, customerService, newTrialArea);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("memberRank", memberRankService.find(id));
		List<MemberRankRigths> mrrss = memberRankRigthsService.findByMemberRankId(id); 
		if (mrrss != null) {
			for (MemberRankRigths mrrs : mrrss) {
				model.addAttribute(mrrs.getRightsName().toString(), mrrs.getRightsValue().toString());
			}
		}
		return "/admin/member_rank/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MemberRank memberRank, RedirectAttributes redirectAttributes, 
			Long priceConcessions, Long[] birthdayPrivilege, Long customerService, Long newTrialArea
			) {
		if (!isValid(memberRank)) {
			return ERROR_VIEW;
		}
		MemberRank pMemberRank = memberRankService.find(memberRank.getId());
		if (pMemberRank == null) {
			return ERROR_VIEW;
		}
		if (!memberRankService.nameUnique(pMemberRank.getName(), memberRank.getName())) {
			return ERROR_VIEW;
		}
		if (pMemberRank.getIsDefault()) {
			memberRank.setIsDefault(true);
		}
		if (memberRank.getIsSpecial()) {
			memberRank.setAmount(null);
		} else if (memberRank.getAmount() == null || !memberRankService.amountUnique(pMemberRank.getAmount(), memberRank.getAmount())) {
			return ERROR_VIEW;
		}
		memberRankService.update(memberRank, "members", "promotions");
		memberRankRigthsService.updateMemberRankRigths(memberRank, priceConcessions, birthdayPrivilege, customerService, newTrialArea);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", memberRankService.findPage(pageable));
		model.addAttribute("memberRankRigths", memberRankRigthsService.findAll());
		return "/admin/member_rank/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				MemberRank memberRank = memberRankService.find(id);
				if (memberRank != null && memberRank.getMembers() != null && !memberRank.getMembers().isEmpty()) {
					return Message.error("admin.memberRank.deleteExistNotAllowed", memberRank.getName());
				}
				List<MemberRankRigths> rights = memberRankRigthsService.findByMemberRankId(id); 
				for(int i=0; i<rights.size(); i++){				
					memberRankRigthsService.delete(rights.get(i).getId());
				}			
			}
			long totalCount = memberRankService.count();
			if (ids.length >= totalCount) {
				return Message.error("admin.common.deleteAllNotAllowed");
			}
			memberRankService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}