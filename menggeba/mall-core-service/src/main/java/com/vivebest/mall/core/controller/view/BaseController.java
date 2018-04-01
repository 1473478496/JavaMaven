/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.core.controller.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.DateEditor;
import com.vivebest.mall.core.common.HtmlCleanEditor;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.template.directive.FlashMessageDirective;
import com.vivebest.mall.core.template.directive.WapFlashMessageDirective;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SpringUtils;

/**
 * Controller - 基类
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public class BaseController {

	protected static Logger logger = Logger.getLogger(BaseController.class);
	
	/** 错误视图 */
	protected static final String ERROR_VIEW = "/shop/common/error";
	
	/** wap端错误视图 */
	protected static final String WAP_ERROR_VIEW = "/wap/common/error";

	/** 错误消息 */
	protected static final Message ERROR_MESSAGE = Message.error("shop.message.error");

	/** 成功消息 */
	protected static final Message SUCCESS_MESSAGE = Message.success("shop.message.success");

	/** "验证结果"参数名称 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	protected static final String CHARSET = "UTF-8";
	
	@Resource(name = "validator")
	private Validator validator;

	@Value("${pay.manager.md5.key}")
	private String managerMd5Key;
	@Value("${pay.credit.md5.key}")
	private String creditMd5Key;
	@Value("${pay.acceptBizNo}")
	private String acceptBizNo;
	
	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		logger.info("initBinder ...");
		binder.registerCustomEditor(String.class, new HtmlCleanEditor(true, true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
		//binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false, false));
	}

	
	/**
	 * 数据验证
	 * 
	 * @param target
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		logger.info("isValid ...");
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		logger.info("isValid ...");
		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	/**
	 * 货币格式化
	 * 
	 * @param amount
	 *            金额
	 * @param showSign
	 *            显示标志
	 * @param showUnit
	 *            显示单位
	 * @return 货币格式化
	 */
	protected String currency(BigDecimal amount, boolean showSign, boolean showUnit) {
		Setting setting = SettingUtils.get();
		String price = setting.setScale(amount).toString();
		if (showSign) {
			price = setting.getCurrencySign() + price;
		}
		if (showUnit) {
			price += setting.getCurrencyUnit();
		}
		return price;
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}

	/**
	 * 添加瞬时消息
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @param message
	 *            消息
	 */
	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		logger.info("addFlashMessage...");
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	/**
	 * wap添加瞬时消息
	 * 
	 * @param redirectAttributes
	 *            RedirectAttributes
	 * @param message
	 *            消息
	 */
	protected void addWapFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		logger.info("addWapFlashMessage...");
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(WapFlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}

	public String getManagerMd5Key() {
		return managerMd5Key;
	}

	public String getCreditMd5Key() {
		return creditMd5Key;
	}

	public String getAcceptBizNo() {
		return acceptBizNo;
	}

	/**
	 * 通过servlet输出controller返回结果
	 * @param result
	 * @param response
	 * @throws IOException
	 */
	protected void writeResult(String result,HttpServletResponse response) throws IOException
	{
		logger.info("writeResult...");
	    response.setContentType("application/json; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
	    response.setCharacterEncoding("utf-8");  
	    PrintWriter out = response.getWriter();
	    out.write(result);
	    out.flush();
		out.close();
	}
}