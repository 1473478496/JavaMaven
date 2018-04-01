/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.service.TemplateService;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SpringUtils;
import com.vivebest.mall.entity.EmailSafeKey;
import com.vivebest.mall.entity.MemberBirthdayRigths;
import com.vivebest.mall.entity.ProductNotify;
import com.vivebest.mall.entity.SafeKey;
import com.vivebest.mall.service.MailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Service - 邮件
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("mailServiceImpl")
public class MailServiceImpl implements MailService {

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "javaMailSender")
	private JavaMailSenderImpl javaMailSender;
	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;

	/**
	 * 添加邮件发送任务
	 * 
	 * @param mimeMessage
	 *            MimeMessage
	 */
	private void addSendTask(final MimeMessage mimeMessage) {
		try {
			taskExecutor.execute(new Runnable() {
				public void run() {
					javaMailSender.send(mimeMessage);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Assert.hasText(smtpFromMail);
		Assert.hasText(smtpHost);
		Assert.notNull(smtpPort);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(toMail);
		Assert.hasText(subject);
		Assert.hasText(templatePath);
		try {
			Setting setting = SettingUtils.get();
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			javaMailSender.setHost(smtpHost);
			javaMailSender.setPort(smtpPort);
			javaMailSender.setUsername(smtpUsername);
			javaMailSender.setPassword(smtpPassword);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
			mimeMessageHelper.setFrom(MimeUtility.encodeWord(setting.getSiteName()) + " <" + smtpFromMail + ">");
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(toMail);
			mimeMessageHelper.setText(text, true);
			if (async) {
				addSendTask(mimeMessage);
			} else {
				javaMailSender.send(mimeMessage);
			}
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void send(String toMail, String subject, String templatePath, Map<String, Object> model, boolean async) {
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), toMail, subject, templatePath, model, async);
	}

	public void send(String toMail, String subject, String templatePath, Map<String, Object> model) {
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), toMail, subject, templatePath, model, true);
	}

	public void send(String toMail, String subject, String templatePath) {
		Setting setting = SettingUtils.get();
		send(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), toMail, subject, templatePath, null, true);
	}

	public void sendTestMail(String smtpFromMail, String smtpHost, Integer smtpPort, String smtpUsername, String smtpPassword, String toMail) {
		Setting setting = SettingUtils.get();
		String subject = SpringUtils.getMessage("admin.setting.testMailSubject", setting.getSiteName());
		com.vivebest.mall.core.common.Template testMailTemplate = templateService.get("testMail");
		send(smtpFromMail, smtpHost, smtpPort, smtpUsername, smtpPassword, toMail, subject, testMailTemplate.getTemplatePath(), null, false);
	}

	public void sendFindPasswordMail(String toMail, String username, SafeKey safeKey) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("safeKey", safeKey);
		String subject = SpringUtils.getMessage("shop.password.mailSubject", setting.getSiteName());
		com.vivebest.mall.core.common.Template findPasswordMailTemplate = templateService.get("findPasswordMail");
		send(toMail, subject, findPasswordMailTemplate.getTemplatePath(), model);
	}

	public void wapFindPasswordMail(String toMail, String username, SafeKey safeKey) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("username", username);
		model.put("safeKey", safeKey);
		String subject = SpringUtils.getMessage("shop.password.mailSubject", setting.getSiteName());
		com.vivebest.mall.core.common.Template findPasswordMailTemplate = templateService.get("wapFindPasswordMail");
		send(toMail, subject, findPasswordMailTemplate.getTemplatePath(), model);
	}

	public void sendBindingMail(String toMail, String emailKey, EmailSafeKey safeKey) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("safeKey", safeKey);
		model.put("emailKey", emailKey);
		String subject = SpringUtils.getMessage("shop.password.mailBinding", setting.getSiteName());
		com.vivebest.mall.core.common.Template findPasswordMailTemplate = templateService.get("emainBinding");
		send(toMail, subject, findPasswordMailTemplate.getTemplatePath(), model);
	}

	public void sendProductNotifyMail(ProductNotify productNotify) {
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("productNotify", productNotify);
		String subject = SpringUtils.getMessage("admin.productNotify.mailSubject", setting.getSiteName());
		com.vivebest.mall.core.common.Template productNotifyMailTemplate = templateService.get("productNotifyMail");
		send(productNotify.getEmail(), subject, productNotifyMailTemplate.getTemplatePath(), model);
	}
	
	public void sendMemberBirthdayRightsMail(MemberBirthdayRigths memberBirthdayRigths){
		Setting setting = SettingUtils.get();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("memberBirthdayRigths", memberBirthdayRigths);
		String subject = SpringUtils.getMessage("admin.memberBirthdayRights.mailSubject", setting.getSiteName());
		com.vivebest.mall.core.common.Template memberBirthdayRightsMailTemplate = templateService.get("memberBirthdayRightsMail");
		send(memberBirthdayRigths.getMember().getEmail(), subject, memberBirthdayRightsMailTemplate.getTemplatePath(), model);
	}
	

}