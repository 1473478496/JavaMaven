package com.vivebest.mall.template.directive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Filter.Operator;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.template.directive.BaseDirective;
import com.vivebest.mall.core.util.FreemarkerUtils;
import com.vivebest.mall.entity.GuessYouLike;
import com.vivebest.mall.entity.GuessYouLike.GuessType;
import com.vivebest.mall.service.GuessYouLikeService;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 首页猜你喜欢
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("homeGuessYouFavoriteDirective")
public class HomeGuessYouFavoriteDirective extends BaseDirective {
	
	private static Logger logger = Logger.getLogger(GuessYouLikeDirective.class);

	/** 变量名称 */
	private static final String GUESS_YOU_LIKES = "guessYouLikes";
	
	@Resource(name = "guessYouLikeServiceImpl")
	private GuessYouLikeService guessYouLikeService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		List<GuessYouLike> guessYouLikes = null;
		Integer count = getCount(params);
		List<Order> orders = getOrders(params);
		//guessType类型
		String guessType = FreemarkerUtils.getParameter("guessType", String.class, params);
		try {
			Filter filter = new Filter("guessType", Operator.eq, Enum.valueOf(GuessType.class, guessType));
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(filter);
			guessYouLikes = guessYouLikeService.findList(count, filters, orders);
		} catch (IllegalArgumentException e) {
			logger.info("非法参数："+e.getMessage());
		}
		setLocalVariable(GUESS_YOU_LIKES, guessYouLikes, env, body);
	}

}
