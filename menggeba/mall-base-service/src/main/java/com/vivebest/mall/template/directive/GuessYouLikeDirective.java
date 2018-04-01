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
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.core.util.WebUtils;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.service.ProductService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 猜你喜欢
 * 
 * @author ding.hy
 * @version 3.0
 */
@Component("guessYouLikeDirective")
public class GuessYouLikeDirective extends BaseDirective {
	
	private static Logger logger = Logger.getLogger(GuessYouLikeDirective.class);

	/** 变量名称 */
	private static final String LIKE_PRODUCT_NAME = "likeProducts";

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
			List<Product> products = null;
			String str = WebUtils.getCookie(request, "historyProduct");
			Integer count = getCount(params);
			List<Order> orders = getOrders(params);
			int num = 0;
			if (str != null) {
				String[] idStr = str.split(",");
				Long[] ids = idStr.length < count ? new Long[idStr.length] : new Long[count];
				num = idStr.length;
				for (int i = 0 ; i < ids.length ; i++) {
					try {
						ids[i] = Long.valueOf(idStr[i]);
					} catch (Exception e) {
						// TODO: handle exception
						logger.info("list exception:" + e);
					}
				}
				products = productService.findListNoSuit(ids);
			} 
			if (count - num > 0 && orders.size() > 0) {
				if (num == 0) {
					products = productService.findListNoSuit(count, orders);
				} else {
					products.addAll(productService.findListNoSuit(count - num, orders));
				}
			}
			setLocalVariable(LIKE_PRODUCT_NAME, products, env, body);
		} 
		
	}

}