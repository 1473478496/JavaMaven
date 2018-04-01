package com.vivebest.mall.admin.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.admin.controller.SalesController.Type;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.IntegrationRule;
import com.vivebest.mall.service.IntegrationRuleService;

/**
 * Controller -积分兑换
 * 
 * @version 3.0*
 * @author junly
 *
 */
@Controller("integrationRuleController")
@RequestMapping("/admin/irule")
public class IntegrationRuleController extends BaseController {
	@Resource(name = "integrationRuleServiceImpl")
	private IntegrationRuleService integrationRuleService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("types", Type.values());
		model.addAttribute("integrationRuleService",
				integrationRuleService.findAll());
		return "/admin/irule/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(IntegrationRule integrationRule,
			RedirectAttributes redirectAttributes) {
		if (!isValid(integrationRule)) {
			return ERROR_VIEW;
		}
		if (integrationRuleService.ccyExists(integrationRule.getCcy())) {
			return ERROR_VIEW;
		}
		if (integrationRule.getRule() == null) {
			return ERROR_VIEW;
		}
		integrationRuleService.save(integrationRule);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 检查币种是否存在
	 */
	@RequestMapping(value = "/check_ccy", method = RequestMethod.GET)
	public @ResponseBody boolean checkUsername(String ccy) {
		if (StringUtils.isEmpty(ccy)) {
			return false;
		}
		if (integrationRuleService.ccyExists(ccy)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("integrationRule", integrationRuleService.find(id));
		return "/admin/irule/edit";
	}

	/**
	 * 更新
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(IntegrationRule integrationRule,
			RedirectAttributes redirectAttributes) {
		IntegrationRule inRule = integrationRuleService.find(integrationRule
				.getId());
		if (inRule == null) {
			return ERROR_VIEW;
		}
		if (integrationRule.getRule() == null) {
			return ERROR_VIEW;
		}
		integrationRuleService.update(integrationRule);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", integrationRuleService.findPage(pageable));
		return "/admin/irule/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		integrationRuleService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
