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

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.service.BrandService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 品牌列表
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("brandListDirective")
public class BrandListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "brands";

	@Resource(name = "brandServiceImpl")
	private BrandService brandService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<Brand> brands;
		boolean useCache = useCache(env, params);
		String cacheRegion = getCacheRegion(env, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Brand.class);
		List<Order> orders = getOrders(params);
		if (useCache) {
			//brands = brandService.findList(count, filters, orders, cacheRegion);
			Pageable pageable = new Pageable();
			if(count ==null){
				count =100;
			}
			pageable.setPageSize(count);
			pageable.setPageNumber(1);
			//pageable.setOrders(orders);
			brands = brandService.findList(new Brand(), pageable).getContent();
		} else {
			Pageable pageable = new Pageable();
			if(count ==null){
				count =100;
			}
			pageable.setPageSize(count);
			pageable.setPageNumber(1);
			//pageable.setOrders(orders);
			brands = brandService.findList(new Brand(), pageable).getContent();
		}
		setLocalVariable(VARIABLE_NAME, brands, env, body);
	}

}