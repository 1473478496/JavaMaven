package com.vivebest.mall.merchant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.AuthenticationToken;
import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.common.Setting.AccountLockType;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.merchant.dao.TradeAdminDao;
import com.vivebest.mall.merchant.entity.TradeAdmin;
import com.vivebest.mall.merchant.entity.TradeRole;

/**
 * 权限认证
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public class MerAuthenticationRealm extends AuthorizingRealm {

	private static Logger logger = Logger.getLogger(MerAuthenticationRealm.class);
	
	@Autowired
	private TradeAdminDao tradeAdminService;

	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Transactional
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String ip = authenticationToken.getHost();
		
		if (username != null && password != null) {
			TradeAdmin tradeAdmin = tradeAdminService.findByUsername(username);
			if (tradeAdmin == null) {
				logger.error("doGetAuthenticationInfo admin null");
				throw new UnknownAccountException();
			}
			Setting setting = SettingUtils.get();

			if (tradeAdmin.getIs_locked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.trade)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						logger.error("doGetAuthenticationInfo admin null");
						throw new LockedAccountException();
					}
					Date lockedDate = tradeAdmin.getLocked_date();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						tradeAdmin.setLogin_failure_count(0);
						tradeAdmin.setIs_locked(false);
						tradeAdmin.setLocked_date(null);
//						tradeAdminService.merge(tradeAdmin);
					} else {
						logger.error("doGetAuthenticationInfo locked Date");
						throw new LockedAccountException();
					}
				} else {
					tradeAdmin.setLogin_failure_count(0);
					tradeAdmin.setIs_locked(false);
					tradeAdmin.setLocked_date(null);
//					tradeAdminService.merge(tradeAdmin);
				}
			}

			if (!DigestUtils.md5Hex(password).equals(tradeAdmin.getPassword())) {
				int loginFailureCount = tradeAdmin.getLogin_failure_count() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					tradeAdmin.setIs_locked(true);
					tradeAdmin.setLocked_date(new Date());
				}
				tradeAdmin.setLogin_failure_count(loginFailureCount);
				//tradeAdminService.merge(tradeAdmin);
				logger.error("doGetAuthenticationInfo password error");
				throw new IncorrectCredentialsException();
			}
			tradeAdmin.setLogin_ip(ip);
			tradeAdmin.setLogin_date(new Date());
			tradeAdmin.setLogin_failure_count(0);
//			tradeAdminService.merge(tradeAdmin);
			return new SimpleAuthenticationInfo(new Principal(tradeAdmin.getId(), username), password, getName());
		}
		logger.error("doGetAuthenticationInfo unknown account error");
		throw new UnknownAccountException();
	}

	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		TradeAdmin admin = tradeAdminService.find(id);
		if (admin != null) {
			for (TradeRole role : admin.getTradeRoles()) {
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