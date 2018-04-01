package com.vivebest.mall.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Filter.Operator;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Promotion.Category;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.service.PromotionProductsService;
import com.vivebest.mall.service.PromotionService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 精选团购
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("choiceBookingDirective")
public class ChoiceBookingDirective extends BaseDirective {
	
	/** 变量名称 */
	private static final String CHOICE_BOOKINGS = "choiceBookings";
	
	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductsService;
	
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<PromotionProducts> bookings = null;
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		Filter flp = new Filter("category", Operator.eq, Category.booking);
		Filter flp1 = new Filter("status", Operator.eq, true);
		List<Filter> flpList = new ArrayList<Filter>();
		flpList.add(flp);
		flpList.add(flp1);
		List<Promotion> promotionList = promotionService.findList(true, false, null, flpList, null);
		List<Filter> filtersAll = new ArrayList<Filter>();
		if (promotionList.size() > 0) {
			Filter promotion = new Filter("promotion", Operator.in, promotionList);
			filtersAll.add(promotion);
			bookings = promotionProductsService.findList(count, filtersAll, orders);
		}
		setLocalVariable(CHOICE_BOOKINGS, bookings, env, body);
	}

}
