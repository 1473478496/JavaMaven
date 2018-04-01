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
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.RightsBrandService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("followBusinessDirective")
public class FollowBusinessDirective extends BaseDirective {

	private static Logger logger = Logger.getLogger(FollowBusinessDirective.class);
	
	/** 变量名称 */
	private static final String FOLLOW_BUSINESSES = "followBusinesses";
	
	@Resource(name = "rightsBrandServiceImpl")
	private RightsBrandService rightsBrandService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Member member = memberService.getCurrent();
		List<RightsBrand> rightsBrands = null;
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		Pageable pageable = new Pageable();
		pageable.setOrders(orders);
		pageable.setPageSize(count);
		rightsBrands = rightsBrandService.findPage(pageable, member).getContent();
		setLocalVariable(FOLLOW_BUSINESSES, rightsBrands, env, body);
	}
	
}
