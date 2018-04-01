package com.vivebest.mall.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * CheckRegular - 使用正则表达式验证输入格式
 * <p>
 * @version 1.0.0,2015年7月23日
 * @author ding.hy
 * @since 1.0.0
 */
public final class CheckRegular {
	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobileNumber(String mobile) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(17[0-9])|(15([0-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobile);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 验证用户名
	 * 
	 * @param username
	 * @return
	 */
	public static boolean checkUserName(String username) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9_\u4E00-\u9FA5]{1,19}$");
			Matcher matcher = regex.matcher(username);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
