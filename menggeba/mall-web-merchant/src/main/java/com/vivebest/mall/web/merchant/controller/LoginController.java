package com.vivebest.mall.web.merchant.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.common.AuthenticationToken;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.controller.view.BaseController;
import com.vivebest.mall.core.service.RSAService;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.WebUtils;
import com.vivebest.mall.merchant.entity.TradeAdmin;
import com.vivebest.mall.merchant.service.TradeAdminService;

/**
 * Controller - 会员登录
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("fcexLoginController")
@RequestMapping("/login")
public class LoginController extends BaseController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	
	@Autowired
	private TradeAdminService tradeAdminService;

	/**
	 * 登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index(String redirectUrl, HttpServletRequest request, ModelMap model) {
		Setting setting = SettingUtils.get();
		if (redirectUrl != null) {
			try {
				redirectUrl = URLDecoder.decode(redirectUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("URL转义失败：", e);
			}
		}

		if (redirectUrl != null && !redirectUrl.equalsIgnoreCase(setting.getSiteUrl())
				&& !redirectUrl.startsWith(request.getContextPath() + "/")
				&& !redirectUrl.startsWith(setting.getSiteUrl() + "/")) {
			redirectUrl = null;
		}

		model.addAttribute("redirectUrl", redirectUrl);
		return "/login/login";
	}


	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public @ResponseBody Message submintTrade(String captcha, String type, String username,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String password = request.getParameter("password");
		try {
			// 登录认证
			SecurityUtils.getSubject()
					.login(new AuthenticationToken(username, password, null, null, false, request.getRemoteAddr()));
		} catch (IncorrectCredentialsException e) {
			logger.info("密码错误");
			return Message.error("密码错误");
		} catch (UnknownAccountException e) {
			logger.info("账号不存在");
			return Message.error("账号不存在");
		} catch (LockedAccountException e) {
			logger.info("帐号已被锁定");
			return Message.error("帐号已被锁定");
		} catch (UnsupportedTokenException e) {
			logger.info("验证码错误");
			return Message.error("验证码错误");
		}
		TradeAdmin tradeAdmin = tradeAdminService.findByUsername(username);
		// 添加session
		session = request.getSession();
		session.setAttribute(TradeAdmin.class.getName(), new Principal(tradeAdmin.getId(), username));
		// 添加cookie
		WebUtils.addCookie(request, response, "username", username);
		return SUCCESS_MESSAGE;
	}

}
