/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
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
import com.vivebest.mall.entity.Role;
import com.vivebest.mall.service.RoleService;

/**
 * Controller - 角色
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminRoleController")
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add() {
		return "/admin/role/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Role role, RedirectAttributes redirectAttributes) {
		if (!isValid(role)) {
			return ERROR_VIEW;
		}
		role.setIsSystem(false);
		role.setAdmins(null);
		roleService.save(role);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("role", roleService.find(id));
		return "/admin/role/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Role role, RedirectAttributes redirectAttributes) {
		if (!isValid(role)) {
			return ERROR_VIEW;
		}
		Role pRole = roleService.find(role.getId());
		if (pRole == null || pRole.getIsSystem()) {
			return ERROR_VIEW;
		}
		roleService.update(role, "isSystem", "admins");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", roleService.findPage(pageable));
		return "/admin/role/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				Role role = roleService.find(id);
				if (role != null && (role.getIsSystem() || (role.getAdmins() != null && !role.getAdmins().isEmpty()))) {
					return Message.error("admin.role.deleteExistNotAllowed", role.getName());
				}
			}
			roleService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

}