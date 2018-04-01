package com.vivebest.mall.service.impl;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.vivebest.mall.core.common.SmsBusType;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.dao.SmsDao;
import com.vivebest.mall.entity.Booking;
import com.vivebest.mall.entity.MemberBirthdayRigths;
import com.vivebest.mall.entity.ProductNotify;
import com.vivebest.mall.entity.Sms;
import com.vivebest.mall.service.SmsService;

/**
 * 
 * @author liu.jch
 * 
 */
@Service("smsServiceImpl")
public class SmsServiceImpl extends BaseServiceImpl<Sms, Long>implements SmsService {
	private static Logger logger = Logger.getLogger(SmsServiceImpl.class);

	// private Client client;

	@Resource(name = "smsDaoImpl")
	public void setBaseDao(SmsDao smsDao) {
		super.setBaseDao(smsDao);
	}

	@Resource(name = "smsDaoImpl")
	private SmsDao smsDao;
	/**
	 * url
	 */
	@Value("${sms.url}")
	private String url;

	/**
	 * passagewayid
	 */
	@Value("${sms.passagewayid}")
	private String passagewayid;

	/**
	 * 用户名
	 */
	@Value("${sms.account}")
	private String account;

	/**
	 * 密码
	 */
	@Value("${sms.password}")
	private String password;

	/**
	 * 是否开发环境，如果是开发环境就不发送短信
	 */
	@Value("${sms.isDevelop}")
	private boolean isDevelop;

	/**
	 * 注册短信
	 */
	@Value("${sms.content.register}")
	private String msgRegister;

	/**
	 * 找回密码短信
	 */
	@Value("${sms.content.findPwd}")
	private String msgFindPwd;

	/**
	 * 到货通知短信
	 */
	@Value("${sms.content.notify}")
	private String msgNotify;

	/**
	 * 订单发货后通知短信
	 */
	@Value("${sms.content.shipping}")
	private String msgShipping;

	/**
	 * 团购购买通知短信
	 */
	@Value("${sms.content.bookingNotify}")
	private String msgBookingNotify;

	/**
	 * 发送权益码
	 */
	@Value("${sms.content.rights}")
	private String msgRights;

	/**
	 * 保存短信内容
	 */
	public void save(Sms sms) {
		Assert.notNull(sms);
		smsDao.persist(sms);
		// super.save(sms);
	}

	/**
	 * 
	 * 发送短信
	 * 
	 * @param smsBusType
	 *            业务类型
	 * @param content
	 *            短信内容
	 * @param numbers
	 *            目标号码
	 * @return
	 */
	// public int sendMessage(SmsBusType smsBusType, String contents, String...
	// numbers) {
	// logger.debug("sms type:" + smsBusType.getName());
	// logger.debug("content:" + contents);
	// logger.debug("numbers:" + StringUtils.arrayToDelimitedString(numbers,
	// ","));
	// int result = -1;
	// try {
	// if (!isDevelop) {
	// client = new Client(account, password);
	// result = client.sendSMS(new String[] {
	// StringUtils.arrayToDelimitedString(numbers, ",") }, contents, "",
	// 5);// 带扩展码
	// if (result == -1105 || result == -1107 || result == -1108) {
	// // 如是账号注册异常，则重新注册激活
	// result = client.registEx(password);
	// if (result == 0)
	// // 激活成功后再重新发一次短信
	// result = client.sendSMS(new String[] {
	// StringUtils.arrayToDelimitedString(numbers, ",") },
	// contents, "", 5);
	// }
	// System.out.println("testSendSMS=====" + result);
	// } else {
	// result = 0;
	// System.out.println("testSendSMS=====develop");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// Sms sms = new Sms();
	// sms.setBusType(smsBusType.getValue());
	// sms.setContent(contents);
	// sms.setStatus(result);
	// sms.setSendTime(new Date());
	// sms.setNumber(StringUtils.arrayToDelimitedString(numbers, ","));
	// try {
	// save(sms);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 
	 * 发送短信
	 * 
	 * @param smsBusType
	 *            业务类型
	 * @param content
	 *            短信内容
	 * @param numbers
	 *            目标号码
	 * @return
	 */
	public int sendMessage(SmsBusType smsBusType, String contents, String... numbers) {
		logger.debug("sms type:" + smsBusType.getName());
		logger.debug("content:" + contents);
		logger.debug("numbers:" + StringUtils.arrayToDelimitedString(numbers, ","));
		int result = -1;
		try {
			if (!isDevelop) {
				result = sendSms(url, account, password, StringUtils.arrayToDelimitedString(numbers, ","), contents,
						passagewayid, "");
			} else {
				result = 0;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		Sms sms = new Sms();
		sms.setBusType(smsBusType.getValue());
		sms.setContent(contents);
		sms.setStatus(result);
		sms.setSendTime(new Date());
		sms.setNumber(StringUtils.arrayToDelimitedString(numbers, ","));
		try {
			save(sms);
		} catch (Exception e) {
			logger.error(e);
		}
		
		// 返回结果说明：正数表示成功条数 如：100表示 成功提交100条
		// -100：没有订购该产品
		// -101：帐号和密码验证失败或是帐号被注销；
		// -102：手机号码为空或含有不合法的手机号码；
		// -103：内容为空或含有非法字符；
		// -104：账号余额不足；
		// -110：其他错误；
		if (result > 0) {
			//发送成功把返回结果置为0，业务表示发送成功
			result = 0;
		}
		return result;
	}

	/**
	 * 发送短信验证码到夜朗
	 * 
	 * @param url：url
	 * @param userName：夜朗短信用户名，xunxiaokeji
	 * @param password：夜朗短信密码，123456
	 * @param phoneList：手机号，多个用逗号号分开
	 * @param msg：发送消息
	 * @param passagewayid：订购产品编号号21
	 * @param sendDatetime，发送时间，空为立即发送
	 * @return 返回成功条数 如：100表示 成功提交100条 -100：没有订购该产品 -101：帐号和密码验证失败或是帐号被注销；
	 *         -102：手机号码为空或含有不合法的手机号码； -103：内容为空或含有非法字符； -104：账号余额不足； -110：其他错误；
	 */
	private int sendSms(String url, String userName, String password, String phoneList, String msg, String passagewayid,
			String sendDatetime) {
		try {
			logger.debug("sendSms url:" + url);
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", userName);
			map.put("password", password);
			map.put("phonelist", phoneList);
			map.put("msg", msg);
			map.put("Passagewayid", passagewayid);
			map.put("SendDatetime", sendDatetime);
			logger.debug("sendSms param:" + map);
			String resultXml = HttpClientUtil.post(url, map, "UTF-8");
			return getResult(resultXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
		}
		return -1;
	}

	/**
	 * 解析夜朗短信返回的报文状态
	 * 
	 * @param protocolXML
	 * @return 返回成功条数 如：100表示 成功提交100条 -100：没有订购该产品 -101：帐号和密码验证失败或是帐号被注销；
	 *         -102：手机号码为空或含有不合法的手机号码； -103：内容为空或含有非法字符； -104：账号余额不足； -110：其他错误；
	 */
	private int getResult(String protocolXML) {
		try {
			logger.debug("sendSms result:" + protocolXML);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(protocolXML)));
			Element root = doc.getDocumentElement();
			NodeList books = root.getChildNodes();
			if (books != null) {
				Node book = books.item(0);
				if (book != null) {
					logger.info("sendSms result code: " + book.getNodeValue());
					return Integer.parseInt(book.getNodeValue());
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return -1;
	}

	/**
	 * 
	 * @param smsBusType
	 * @param vars
	 * @param numbers
	 * @return
	 */
	@Override
	public int sendMessage(SmsBusType smsBusType, Map<String, Object> vars, String... numbers) {
		return this.sendMessage(smsBusType, this.getContent(smsBusType, vars), numbers);
	}

	/**
	 * 
	 * 根据短信的业务类型及模板变量加载模板内容
	 * 
	 * @param smsBusType
	 * @param vars
	 * @return
	 */
	private String getContent(SmsBusType smsBusType, Map<String, Object> vars) {
		logger.debug("sms type:" + smsBusType.getName());
		logger.debug("valus:" + vars);
		String code = null;
		if (vars.containsKey("code"))
			code = vars.get("code").toString();
		String content = "";
		if (smsBusType == SmsBusType.REGISTER)
			content = msgRegister.replace("@{code}", code);
		else if (smsBusType == SmsBusType.FIND_PWD)
			content = msgFindPwd.replace("@{code}", code);
		else if (smsBusType == SmsBusType.SHIPPING) {
			content = msgShipping.replace("@{code1}", code);
			if (vars.containsKey("code2"))
				content = content.replace("@{code2}", vars.get("code2").toString());
		} else if (smsBusType == SmsBusType.RECEIVE)
			content = msgNotify.replace("@{code}", code);
		else if (smsBusType == SmsBusType.RIGHTS) {
			content = msgRights.replace("@{code1}", code);
			if (vars.containsKey("code2"))
				content = content.replace("@{code2}", vars.get("code2").toString());
		}
		logger.info("content:" + content);

		return content;
	}

	public Integer getInterval(String phone) {
		// return this.smsMapper.getInterval(phone);
		return 0;
	}

	public boolean checkTimeOut(String phone) {
		Integer interval = getInterval(phone);
		if (interval == null) {
			return true;
		} else if (interval >= 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 发送到货通知短信
	 */
	@Override
	public void sendProductNotifySms(ProductNotify productNotify) {
		String content = msgNotify.replace("@{code1}", productNotify.getProduct().getName());
		content = content.replace("@{code2}", productNotify.getProduct().getWapPath());
		this.sendMessage(SmsBusType.RECEIVE, content, productNotify.getMember().getMobile());
	}

	/**
	 * 发送生日权益通知
	 * 
	 * @param memberBirthdayRigths
	 */
	public void sendMemberBirthdayRightsSms(MemberBirthdayRigths memberBirthdayRigths) {
		String content = "生日快乐";
		this.sendMessage(SmsBusType.OTHER, content, memberBirthdayRigths.getMember().getMobile());
	}

	@Override
	public List<Sms> findByNumAndStatus(String number, int status) {
		return smsDao.findByNumAndStatus(number, status);
	}

	@Override
	public void sendBookingNotifySms(Booking booking) {
		String content = msgBookingNotify.replace("@{code1}", booking.getProduct().getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String lastSaleTime = format.format(new Date());
		if (booking.getPromotion().getPurEndDate() != null) {
			lastSaleTime = format.format(booking.getPromotion().getPurEndDate());
		}
		content = content.replace("@{code2}", lastSaleTime);
		String mobile = booking.getMobile();
		if (null == mobile || "".equals(mobile)) {
			mobile = booking.getMember().getMobile();
		}
		this.sendMessage(SmsBusType.RECEIVE, content, mobile);
	}

	@Override
	public List<Sms> findLikeContent(String mobile, String code) {
		return smsDao.findLikeContent(mobile, code);
	}

}
