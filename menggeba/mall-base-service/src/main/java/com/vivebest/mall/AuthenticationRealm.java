/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.vivebest.mall.core.common.AuthenticationToken;
import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.common.Setting.AccountLockType;
import com.vivebest.mall.core.common.Setting.CaptchaType;
import com.vivebest.mall.core.service.CaptchaService;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.dao.AdminDao;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Role;

/**
 * 权限认证
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public class AuthenticationRealm extends AuthorizingRealm {

	private static Logger logger = Logger.getLogger(AuthenticationRealm.class);
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Autowired
	private AdminDao adminService;

	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
		if (!captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha)) {
			logger.error("doGetAuthenticationInfo captchaService.isValid: false");
			throw new UnsupportedTokenException();
		}
		if (username != null && password != null) {
			Admin admin = adminService.findByUsername(username);
			if (admin == null) {
				logger.error("doGetAuthenticationInfo admin null");
				throw new UnknownAccountException();
			}
			if (!admin.getIsEnabled()) {
				logger.error("doGetAuthenticationInfo admin isEnable: false");
				throw new DisabledAccountException();
			}
			Setting setting = SettingUtils.get();
			if (admin.getIsLocked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						throw new LockedAccountException();
					}
					Date lockedDate = admin.getLockedDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						admin.setLoginFailureCount(0);
						admin.setIsLocked(false);
						admin.setLockedDate(null);
//						adminService.merge(admin);
					} else {
						logger.error("doGetAuthenticationInfo locked Date");
						throw new LockedAccountException();
					}
				} else {
					admin.setLoginFailureCount(0);
					admin.setIsLocked(false);
					admin.setLockedDate(null);
//					adminService.merge(admin);
				}
			}
			if (!DigestUtils.md5Hex(password).equals(admin.getPassword())) {
				int loginFailureCount = admin.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					admin.setIsLocked(true);
					admin.setLockedDate(new Date());
				}
				admin.setLoginFailureCount(loginFailureCount);
				//adminService.merge(admin);
				logger.error("doGetAuthenticationInfo password error");
				throw new IncorrectCredentialsException();
			}
			admin.setLoginIp(ip);
			admin.setLoginDate(new Date());
			admin.setLoginFailureCount(0);
			//adminService.merge(admin);
			return new SimpleAuthenticationInfo(new Principal(admin.getId(), username), password, getName());
		}
		logger.error("doGetAuthenticationInfo unknown account error");
		throw new UnknownAccountException();
	}

	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		Admin admin = adminService.find(id);
		if (admin != null) {
			for (Role role : admin.getRoles()) {
				authorities.addAll(role.getAuthorities());
			}
		}
		return authorities;
	}

	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = findAuthorities(principal.getId());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}

}