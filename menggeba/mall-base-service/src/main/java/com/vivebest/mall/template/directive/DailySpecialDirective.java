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
import com.vivebest.mall.core.common.Order.Direction;
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
 * 模板指令 - 天天特价
 * 
 * @author ding.hy
 *
 */
@Component("dailySpecialDirective")
public class DailySpecialDirective extends BaseDirective  {

	/** 变量名称 */
	private static final String DAILY_SPECIAL_DIRECTIVE = "specials";
	
	private static final String IS_STICK = "isStick";

	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductsService;

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		List<PromotionProducts> promotionProducts = null;
		// 数量
		Integer count = getCount(params);
		// 是否置顶
		//Boolean isStick = FreemarkerUtils.getParameter(IS_STICK, Boolean.class, params);
		// 根据 天天特价类型 查找promotion
		Filter flp = new Filter("category", Operator.eq, Category.dailySpecial);
		List<Filter> flpList = new ArrayList<Filter>();
		flpList.add(flp);
		// 在活动时间内搜索
		List<Promotion> promotionList = promotionService.findList(true, false, null, flpList, null);
		// 获取promotionId 及 是否置顶
		List<Filter> filters4 = new ArrayList<Filter>();
		if (promotionList.size() > 0) {
			// promotionId
			Filter promotion = new Filter("promotion", Operator.in, promotionList);
			filters4.add(promotion);
			// 是否置顶
			/*if (isStick != null && isStick == true) {
				Filter isStickF = new Filter("isStick", Operator.eq, true);
				filters4.add(isStickF);
			}*/
			// 按修改时间倒叙
			//Order order = new Order("modifyDate", Direction.desc);
			Order order = new Order("promotionPrice", Direction.asc);
			List<Order> orders = new ArrayList<Order>();
			orders.add(order);
			
			promotionProducts = promotionProductsService.findList(count, filters4, orders);
		}
		setLocalVariable(DAILY_SPECIAL_DIRECTIVE, promotionProducts, env, body);
	}

}
