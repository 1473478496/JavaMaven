/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.template.method;

import java.math.BigDecimal;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.util.SettingUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 模板方法 - 货币格式化
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Component("currencyMethod")
public class CurrencyMethod implements TemplateMethodModel {
	private static Logger logger = Logger.getLogger(CurrencyMethod.class);
	
	@SuppressWarnings("rawtypes")
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.get(0) != null && StringUtils.isNotEmpty(arguments.get(0).toString())) {
			boolean showSign = false;
			boolean showUnit = false;
			if (arguments.size() == 2) {
				if (arguments.get(1) != null) {
					showSign = Boolean.valueOf(arguments.get(1).toString());
				}
			} else if (arguments.size() > 2) {
				if (arguments.get(1) != null) {
					showSign = Boolean.valueOf(arguments.get(1).toString());
				}
				if (arguments.get(2) != null) {
					showUnit = Boolean.valueOf(arguments.get(2).toString());
				}
			}
			Setting setting = SettingUtils.get();
			BigDecimal amount = new BigDecimal(arguments.get(0).toString());
			logger.debug("[CurrencyMethod--->>>>amount]:" + amount);
			String price = setting.setScale(amount).toString();
			if (showSign) {
				price = setting.getCurrencySign() + price;
			}
			if (showUnit) {
				price += setting.getCurrencyUnit();
			}
			return new SimpleScalar(price);
		}
		return null;
	}

}