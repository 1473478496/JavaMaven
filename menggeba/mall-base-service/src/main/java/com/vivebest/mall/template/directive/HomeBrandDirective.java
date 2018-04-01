package com.vivebest.mall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.service.BrandService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component("homeBrandDirective")
/**
 * 热门品牌
 * @author wang.hai
 */
public class HomeBrandDirective extends BaseDirective{

	/** 变量名称 */
	private static final String WAP_HOME_BRAND = "brandList";
	
	@Resource(name = "brandServiceImpl")
	private BrandService brandService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		Pageable pageable = new Pageable();
		pageable.setPageSize(count);
		pageable.setPageNumber(1);
		pageable.setOrders(orders);
		List<Brand> brandList = brandService.findList(new Brand(), pageable).getContent();
		setLocalVariable(WAP_HOME_BRAND, brandList, env, body);
	}

}
