/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Article;
import com.vivebest.mall.entity.ArticleCategory;
import com.vivebest.mall.service.ArticleCategoryService;
import com.vivebest.mall.service.NavigationService;

/**
 * Controller - 文章分类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminArticleCategoryController")
@RequestMapping("/admin/article_category")
public class ArticleCategoryController extends BaseController {

	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	
	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("navigations", navigationService.findAll());
		return "/admin/article_category/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ArticleCategory articleCategory, Long parentId, RedirectAttributes redirectAttributes) {
		articleCategory.setNavigation(navigationService.find(articleCategory.getNavigationId()));
		articleCategory.setParent(articleCategoryService.find(parentId));
		if (!isValid(articleCategory)) {
			return ERROR_VIEW;
		}
		articleCategory.setTreePath(null);
		articleCategory.setGrade(null);
		articleCategory.setChildren(null);
		articleCategory.setArticles(null);
		articleCategoryService.save(articleCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		articleCategory.setNavigation(navigationService.find(articleCategory.getNavigationId()));
		model.addAttribute("navigations", navigationService.findAll());
		model.addAttribute("articleCategoryTree", articleCategoryService.findTree());
		model.addAttribute("articleCategory", articleCategory);
		model.addAttribute("children", articleCategoryService.findChildren(articleCategory));
		return "/admin/article_category/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ArticleCategory articleCategory, Long parentId, RedirectAttributes redirectAttributes) {
		
		articleCategory.setNavigation(navigationService.find(articleCategory.getNavigationId()));
		articleCategory.setParent(articleCategoryService.find(parentId));
		if (!isValid(articleCategory)) {
			return ERROR_VIEW;
		}
		if (articleCategory.getParent() != null) {
			ArticleCategory parent = articleCategory.getParent();
			if (parent.equals(articleCategory)) {
				return ERROR_VIEW;
			}
			List<ArticleCategory> children = articleCategoryService.findChildren(parent);
			if (children != null && children.contains(parent)) {
				return ERROR_VIEW;
			}
		}
		articleCategoryService.update(articleCategory, "treePath", "grade", "children", "articles");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<ArticleCategory> articleCategorys = articleCategoryService.findTree();
		model.addAttribute("articleCategoryTree", articleCategorys);
		model.addAttribute("navigations", navigationService.findAll());
		return "/admin/article_category/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		ArticleCategory articleCategory = articleCategoryService.find(id);
		if (articleCategory == null) {
			return ERROR_MESSAGE;
		}
		Set<ArticleCategory> children = articleCategory.getChildren();
		if (children != null && !children.isEmpty()) {
			return Message.error("admin.articleCategory.deleteExistChildrenNotAllowed");
		}
		Set<Article> articles = articleCategory.getArticles();
		if (articles != null && !articles.isEmpty()) {
			return Message.error("admin.articleCategory.deleteExistArticleNotAllowed");
		}
		articleCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

}