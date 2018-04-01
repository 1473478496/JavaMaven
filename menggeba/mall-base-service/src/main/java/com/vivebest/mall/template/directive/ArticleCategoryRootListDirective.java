/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.core.util.FreemarkerUtils;
import com.vivebest.mall.entity.ArticleCategory;
import com.vivebest.mall.entity.Navigation;
import com.vivebest.mall.service.ArticleCategoryService;
import com.vivebest.mall.service.NavigationService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级文章分类列表
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("articleCategoryRootListDirective")
public class ArticleCategoryRootListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "articleCategories";
	
	/** "导航ID"参数名称 */
	private static final String NAVIGATION_ID_PARAMETER_NAME = "navigationId";

	@Resource(name = "articleCategoryServiceImpl")
	private ArticleCategoryService articleCategoryService;
	
	@Resource(name = "navigationServiceImpl")
	private NavigationService navigationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long navigationId = FreemarkerUtils.getParameter(NAVIGATION_ID_PARAMETER_NAME, Long.class, params);
		Navigation navigation = this.navigationService.find(navigationId);
		List<ArticleCategory> articleCategories;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		if (useCache) {
			if (navigation != null) {
				articleCategories = this.articleCategoryService.findRoots(navigation, count, cacheRegion);
			} else {
				articleCategories = articleCategoryService.findRoots(count, cacheRegion);
			}
		} else {
			if (navigation != null) {
				articleCategories = articleCategoryService.findRoots(navigation, count);
			} else {
				articleCategories = articleCategoryService.findRoots(count);
			}
		}
		setLocalVariable(VARIABLE_NAME, articleCategories, env, body);
	}

}