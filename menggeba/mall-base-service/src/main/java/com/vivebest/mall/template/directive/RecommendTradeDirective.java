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
import com.vivebest.mall.entity.RightsTrade;
import com.vivebest.mall.service.RightsTradeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 推荐门店
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("recommendTradeDirective")
public class RecommendTradeDirective extends BaseDirective {
	
	/** 变量名称 */
	private static final String RECOMMEND_TRADES = "recommendTrades";
	
	@Resource(name = "rightsTradeServiceImpl")
	private RightsTradeService rightsTradeService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		List<RightsTrade> rightsTrades = null;
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		Filter filter = new Filter("isTop", Operator.eq, true);
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(filter);
		rightsTrades = rightsTradeService.findList(count, filters, orders);
		setLocalVariable(RECOMMEND_TRADES, rightsTrades, env, body);
	}

}
