package com.vivebest.mall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.ProductService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 商品收藏
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("favoriteProductDirective")
public class FavoriteProductDirective extends BaseDirective {
	
	private static Logger logger = Logger.getLogger(FavoriteProductDirective.class);
	
	/** 变量名称 */
	private static final String FAVORITE_PRODUCTS = "favoriteProducts";
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Member member = memberService.getCurrent();
		List<Product> products = null;
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		Pageable pageable = new Pageable();
		pageable.setOrders(orders);
		pageable.setPageSize(count);
		products = productService.findPage(member, pageable).getContent();
		setLocalVariable(FAVORITE_PRODUCTS, products, env, body);
	}

}
