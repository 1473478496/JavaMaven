package com.vivebest.mall.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.service.RightsService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * WAP首页兑换权益列表
 * @author wang.hai
 */
@Component("homeRightDirective")
public class HomeRightDirective extends BaseDirective{

	/** 变量名称 */
	private static final String WAP_HOME_RIGHT = "rightList";
	
	@Resource(name = "rightsServiceImpl")
	private RightsService rightsService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		Pageable pageable = new Pageable();
		pageable.setPageSize(count);
		pageable.setPageNumber(1);
		pageable.setOrders(orders);
		Rights r = new Rights();
		r.setIsTop(true);
		r.setStatus(true);
		List<Rights> rightList = rightsService.findList(r, pageable).getContent();
		setLocalVariable(WAP_HOME_RIGHT, rightList, env, body);
	}

}
