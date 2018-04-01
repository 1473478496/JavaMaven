/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service;

import java.util.Map;

import com.vivebest.mall.entity.EmailSafeKey;
import com.vivebest.mall.entity.MemberBirthdayRigths;
import com.vivebest.mall.entity.ProductNotify;
import com.vivebest.mall.entity.SafeKey;


/**
 * Service - 邮件
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface MailService {

	/**
	 * 发送邮件
	 * 
	 * @param smtpFromMail
	 *            发件人邮箱
	 * @param smtpHost
	 *            SMTP服务器地址
	 * @param smtpPort
	 *            SMTP服务器端口
	 * @param smtpUsername
	 *            SMTP用户名
	 * @param smtpPassword
	 *            SMTP密码
	 * @param toMail
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 * @param async
	 *            是否异步
	 */
	void send(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail, String subject, String templatePath, Map<String, Object> model, boolean async);

	/**
	 * 发送邮件
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 * @param async
	 *            是否异步
	 */
	void send(String toMail, String subject, String templatePath, Map<String, Object> model, boolean async);

	/**
	 * 发送邮件(异步)
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 * @param model
	 *            数据
	 */
	void send(String toMail, String subject, String templatePath, Map<String, Object> model);

	/**
	 * 发送邮件(异步)
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param subject
	 *            主题
	 * @param templatePath
	 *            模板路径
	 */
	void send(String toMail, String subject, String templatePath);

	/**
	 * 发送测试邮件
	 * 
	 * @param smtpFromMail
	 *            发件人邮箱
	 * @param smtpHost
	 *            SMTP服务器地址
	 * @param smtpPort
	 *            SMTP服务器端口
	 * @param smtpUsername
	 *            SMTP用户名
	 * @param smtpPassword
	 *            SMTP密码
	 * @param toMail
	 *            收件人邮箱
	 */
	void sendTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail);

	/**
	 * 发送找回密码邮件
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param username
	 *            用户名
	 * @param safeKey
	 *            安全密匙
	 */
	void sendFindPasswordMail(String toMail, String username, SafeKey safeKey);

	/**
	 * wap端发送找回密码邮件
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param username
	 *            用户名
	 * @param safeKey
	 *            安全密匙
	 */
	void wapFindPasswordMail(String toMail, String username, SafeKey safeKey);
	
	/**
	 * 发送绑定验证邮件
	 * 
	 * @param toMail
	 *            收件人邮箱
	 * @param username
	 *            用户名
	 * @param safeKey
	 *            安全密匙
	 */
	void sendBindingMail(String toMail, String emailKey, EmailSafeKey safeKey);

	/**
	 * 发送到货通知邮件
	 * 
	 * @param productNotify
	 *            到货通知
	 */
	void sendProductNotifyMail(ProductNotify productNotify);
	
	/**
	 * 发送生日权益通知邮件
	 * 
	 * @param memberBirthdayRigths
	 *            生日权益通知
	 */
	void sendMemberBirthdayRightsMail(MemberBirthdayRigths memberBirthdayRigths);

}