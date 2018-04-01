package com.vivebest.mall.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Principal;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.common.SmsBusType;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.entity.EmailCheck;
import com.vivebest.mall.entity.EmailSafeKey;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.service.EmailCheckService;
import com.vivebest.mall.service.MailService;
import com.vivebest.mall.service.MemberService;
import com.vivebest.mall.service.SmsService;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * 短信验证码工具类
 * @author zhao.shsh
 * 
 */
public class CaptchaMobileUtils {
	
	/** CacheManager */
	private static final CacheManager cacheManager = CacheManager.create();
	/**
	 * 
	 * @param numberFlag  是否数字
	 * @param length  长度
	 * @return
	 */
	public static String createRandom(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);

		return retStr;
	}

	/**
	 * 检验短信验证码是否正确（add by zhao.shsh 2015/7/22）
	 * 
	 * @param req
	 * @param validateCode
	 * @return
	 */
	public static Map<String, Object> validateSmsCode(HttpServletRequest request, 
				String validateCode, String mobile, Integer type, String mobileTime) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		mobile = mobile.trim();
		String smsCode = "";
		String smsPhone = "";
		Long endTime = new Date().getTime();
		
		HttpSession session = request.getSession();
		Object time = session.getAttribute("smsTime");
		Long startTime = 0l;
		if(time != null){
			startTime = Long.parseLong(time.toString());
		}
//		Integer smsType = (Integer) session.getAttribute("smsType");
		
//		if (type != null) {
//			if (type != smsType) {
//				session.setAttribute("smsValidateResult", false);
//				resultMap.put("check", "error");
//				resultMap.put("msg", Message.success("shop.validate.mobile.error"));
//			}
//		}

		if (session.getAttribute("smsCode") != null) {
			smsCode = (String) session.getAttribute("smsCode");
		}

		if (session.getAttribute("smsPhone") != null) {
			smsPhone = (String) session.getAttribute("smsPhone");
		}
		if(!((endTime-startTime) < Long.parseLong(mobileTime))){
			resultMap.put("check", "error");
			resultMap.put("msg", Message.warn("shop.validate.mobile.timeout"));
		}
		if (!smsCode.equals(validateCode) || !smsPhone.equals(mobile)) {
			session.setAttribute("smsValidateResult", false);
			resultMap.put("check", "error");
			resultMap.put("msg", Message.warn("shop.validate.mobile.error"));
		}
		session.setAttribute("smsValidateResult", true);

		if(null == resultMap.get("check")){
			resultMap.put("check", "success");
		}
		return resultMap;
	}

	/**
	 * 发送短信
	 * @param request
	 * @param type
	 * @param mobile
	 * @return
	 */
	public static Map<String, Object> sendSmsMes(HttpServletRequest request, int type, String mobile, SmsService smsService) {
		int result = 0;
		mobile = mobile.trim();
		String smsCode = CaptchaMobileUtils.createRandom(true, 6);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", smsCode);
		paramMap.put("sms.content.register","密码：@{code},勿泄露。");
		if (type == SmsBusType.REGISTER.getValue()) {
			//注册 1
			result = smsService.sendMessage(SmsBusType.REGISTER, paramMap, mobile);
		}
		if (type == SmsBusType.FIND_PWD.getValue()) {
			//找回密码2	
			result = smsService.sendMessage(SmsBusType.FIND_PWD, paramMap, mobile);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(result == 0){
			resultMap.put("success", true);
		}
		else{
			resultMap.put("success", false);
		}
		return resultMap;
	}

	/**
	 * 发送验证码短信
	 * @param request
	 * @param type
	 * @param mobile
	 * @return
	 */
	public static Map<String, Object> sendSms(HttpServletRequest request, int type, String mobile, SmsService smsService, String mobileTime) {
		int result = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		mobile = mobile.trim();

		Long endTime = new Date().getTime();

		HttpSession session = request.getSession();
		Object time = session.getAttribute("smsTime");
		Long startTime = 0l;
		String smsPhone = "";
		int typeCheck = 8;
		if (session.getAttribute("smsPhone") != null) {
			smsPhone = (String) session.getAttribute("smsPhone");
		}
		if (session.getAttribute("smsType") != null) {
			typeCheck = Integer.parseInt(session.getAttribute("smsType").toString());
		}
		if(time != null){
			startTime = Long.parseLong(time.toString());
		}
		if((endTime-startTime) < Long.parseLong(mobileTime) && smsPhone.equals(mobile) && typeCheck == type){
			resultMap.put("success", false);
			resultMap.put("mobileTime", mobileTime);
			return resultMap;
		}
		
		String smsCode = createRandom(true, 6);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", smsCode);
//		paramMap.put("msg","验证码：@{code},易提金融欢迎您的加入，请于120秒内输入，请勿泄露。");
		if (type == SmsBusType.REGISTER.getValue()) {
			// 取得用户登录信息
//			Member m = this.memberService.selectForMobile(mobile);
//			if (m != null) {
//				return null;
//			}
			//注册 1	
			result = smsService.sendMessage(SmsBusType.REGISTER, paramMap, mobile);
		}
		else if (type == SmsBusType.FIND_PWD.getValue()) {
			//找回密码 2	
			result = smsService.sendMessage(SmsBusType.FIND_PWD, paramMap, mobile);
		}
		else if (type == SmsBusType.OTHER.getValue()) {
			//其他100	
			result = smsService.sendMessage(SmsBusType.OTHER, paramMap, mobile);
		}
		if(result == 0){
			request.getSession().setAttribute("smsCode", smsCode);
			// mo.xf 20150311 短信增加保存手机号码
			request.getSession().setAttribute("smsPhone", mobile);
			request.getSession().setAttribute("smsTime", new Date().getTime());
			request.getSession().setAttribute("smsType", type);
			resultMap.put("success", true);
		}
		else{
			resultMap.put("success", false);
		}
		return resultMap;
	}

	/**
	 * 发送验证邮件
	 * @param request
	 * @param type
	 * @param mobile
	 * @return
	 */
	public static Map<String, Object> sendEmail(HttpServletRequest request, int type, String email, 
			MailService mailService, EmailCheckService emailCheckService, String emailUrl, MemberService memberService) {
		email = email.trim();
		HttpSession session = request.getSession();
		Principal principal = (Principal)session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);
		
		String emailKey = DigestUtils.md5Hex(email+principal.getId());
		
	//	String url = emailUrl + "/member/binding/suresubmit.do?emailKey=" + emailKey;


		Ehcache cache = cacheManager.getEhcache(Setting.CACHE_NAME);
		EmailCheck emailCheck = new EmailCheck();
		emailCheck.setEmailKey(emailKey);
		emailCheck.setCreateDate(new Date());
		emailCheck.setSendTime(new Date());
		emailCheck.setModifyDate(new Date());
		emailCheck.setIsChecked(false);
		cache.put(new Element(emailKey, emailCheck));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			emailCheckService.save(emailCheck);
			//发送邮件
			Setting setting = SettingUtils.get();
			EmailSafeKey safeKey = new EmailSafeKey();
			safeKey.setValue(UUID.randomUUID().toString() + DigestUtils.md5Hex(RandomStringUtils.randomAlphabetic(30)));
			safeKey.setExpire(setting.getSafeKeyExpiryTime() != 0 ? DateUtils.addMinutes(new Date(), setting.getSafeKeyExpiryTime()) : null);
			Member member = memberService.getCurrent();
			mailService.sendBindingMail(email, emailKey, safeKey);
			member.setCatchEmail(email);
			member.setEmailSafeKey(safeKey);
			memberService.update(member);
			resultMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("success", false);
		}
		return resultMap;
	}

	/**
	 * 确认邮箱
	 * @param request
	 * @param emailKey
	 * @return
	 */
	public static Message sureEmailCode(HttpServletRequest request, String emailKey, String key, 
			EmailCheckService emailCheckService, Member member, String emailTime) {
		Ehcache cache = cacheManager.getEhcache(Setting.CACHE_NAME);
		Element emailElement = cache.get(emailKey);
		Message message = null;
		EmailSafeKey safeKey = member.getEmailSafeKey();
		if(null == safeKey){
			message = Message.success("shop.validate.email.surefail");
		}
		else if((new Date()).getTime()-safeKey.getExpire().getTime()>Long.parseLong(emailTime)){
			//超时
			message = Message.error("shop.validate.email.timeout");
		}
		else{
			try {
				if(null != emailElement && safeKey != null && safeKey.getValue() != null && safeKey.getValue().equals(key)){
					EmailCheck emailCheck = (EmailCheck)emailElement.getObjectValue();
					emailCheck.setIsChecked(true);
					cache.put(new Element(emailKey, emailCheck));
					cache.remove(emailKey);
					emailCheckService.deleteEmailCheck(emailKey);
					message = Message.success("shop.validate.email.bindSuccess");
				}
				else{
					message = Message.success("shop.validate.email.surefail");
				}
				emailCheckService.updateEmailSure(emailKey);
			} catch (Exception e) {
				e.printStackTrace();
				message = Message.error("shop.validate.email.codeError");
			}
		}
		return message;
	}
	/**
	 * 校验是否已经确认邮件
	 * @param request
	 * @param email
	 * @param emailCheckService
	 * @return
	 */
	public static Map<String, Object> validateEmailCode(HttpServletRequest request, String email, 
			EmailCheckService emailCheckService, String emailTime) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Principal principal = (Principal)session.getAttribute(Member.PRINCIPAL_ATTRIBUTE_NAME);

		Ehcache cache = cacheManager.getEhcache(Setting.CACHE_NAME);
		String emailKey = DigestUtils.md5Hex(email+principal.getId());
		try {
			Element emailElement = cache.get(emailKey);
			EmailCheck emailCheck = emailCheckService.findByEmailKey(emailKey);
			EmailCheck emailCheckCache = (EmailCheck)emailElement.getObjectValue();
			if(null == emailCheck || null == emailCheckCache 
					|| !emailCheck.getIsChecked() || !emailCheckCache.getIsChecked()){
				//没有验证
				resultMap.put("check", Message.error("shop.validate.email.notValidate"));
				if((new Date()).getTime()-emailCheck.getSendTime().getTime()>Long.parseLong(emailTime)
						|| (new Date()).getTime()-emailCheckCache.getSendTime().getTime()>Long.parseLong(emailTime)){
					//超时
					resultMap.put("check", Message.error("shop.validate.email.timeout"));
				}
			}
			else {
				cache.remove(emailKey);
				emailCheckService.deleteEmailCheck(emailKey);
				resultMap.put("check", Message.success(""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("check", Message.error("shop.validate.email.codeError"));
		}
		return resultMap;
	}
}
