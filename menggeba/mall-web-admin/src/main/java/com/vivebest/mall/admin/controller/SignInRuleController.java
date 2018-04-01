package com.vivebest.mall.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.SignInRule;
import com.vivebest.mall.entity.SignInRule.RuleCategory;
import com.vivebest.mall.service.SignInRuleService;

/**
 * Controller 签到规则
 * 
 * @author ding.hy
 *
 */
@Controller("signInRuleController")
@RequestMapping("/admin/signInRule")
public class SignInRuleController extends BaseController {
	
	@Resource(name = "signInRuleServiceImpl")
	private SignInRuleService signInRuleService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", signInRuleService.findPage(pageable));
		return "/admin/signInRule/list";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("signInRule", signInRuleService.find(id));
		model.addAttribute("RuleCategorys", RuleCategory.values());
		return "/admin/signInRule/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(SignInRule signInRule, RedirectAttributes redirectAttributes) {
		if (!isValid(signInRule)) {
			return ERROR_VIEW;
		}
		signInRuleService.update(signInRule);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("RuleCategorys", RuleCategory.values());
		return "/admin/signInRule/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SignInRule signInRule, RedirectAttributes redirectAttributes) {
		if (!isValid(signInRule)) {
			return ERROR_VIEW;
		}
		signInRuleService.save(signInRule);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		signInRuleService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
}
