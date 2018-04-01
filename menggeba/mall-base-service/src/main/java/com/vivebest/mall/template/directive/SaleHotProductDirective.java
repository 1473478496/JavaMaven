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
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.service.ProductService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 热销排行
 * 
 * @author ding.hy
 * @version 3.0
 */
@Component("saleHotProductDirective")
public class SaleHotProductDirective extends BaseDirective {

	/** 变量名称 */
	private static final String SALE_PRODUCT_NAME = "hotProducts";

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	/** 变量名称 */
	private static final String VARIABLE_ISPOINT = "isPoint";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<Product> product;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		product = productService.findListBySaleCount(20,cacheRegion);
		setLocalVariable(SALE_PRODUCT_NAME, product, env, body);
	}

}