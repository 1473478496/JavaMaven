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
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.service.ProductCategoryService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 
 * 
 * @junly
 * @version 3.0
 */
@Component("productCategoryIndexListDirective")
public class ProductCategoryIndexListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "productCategories";

	@Resource(name = "productCategoryIndexServiceImpl")
	private ProductCategoryService productCategoryIndexService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ProductCategory> productCategories;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
//			productCategories = productCategoryIndexService.;	
//		setLocalVariable(VARIABLE_NAME, productCategories, env, body);
	}

}