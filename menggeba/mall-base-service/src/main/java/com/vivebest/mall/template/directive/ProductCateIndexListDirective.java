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
import com.vivebest.mall.entity.ProductCateIndex;
import com.vivebest.mall.service.ProductCateIndexService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 产品类型列表
 * 
 * @author junly
 * @version 3.0
 */
@Component("productCateIndexListDirective")
public class ProductCateIndexListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String PRODUCT_CATEGORY_NAME = "productCategoryIndexs";

	@Resource(name = "productCateIndexServiceImpl")
	private ProductCateIndexService productCateIndexService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<ProductCateIndex> productCateIndexs;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		if (useCache) {
			productCateIndexs = productCateIndexService.findAll(count, cacheRegion);
		} else {
			productCateIndexs = productCateIndexService.findAll(count);
		}
		setLocalVariable(PRODUCT_CATEGORY_NAME, productCateIndexs, env, body);
	}

}