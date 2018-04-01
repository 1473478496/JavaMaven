package com.vivebest.mall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivebest.mall.core.constants.CreditEventEnum;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.core.util.JsonUtils;
import com.vivebest.mall.service.CreditService;

/**
 * 赠送积分Service -
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("creditServiceImpl")
public class CreditServiceImpl implements CreditService {

	@Value("${pay.manager.md5.key}")
	private String managerMd5Key;
	@Value("${pay.credit.md5.key}")
	private String creditMd5Key;
	@Value("${pay.acceptBizNo}")
	private String acceptBizNo;

	@Value("${pay.integral.url}")
	private String integralUrl;
	@Value("${pay.accountVirAcOut.url}")
	private String accountVirAcOutUrl;
	@Value("${pay.engineMengTopUp.url}")
	private String engineMengTopUpUrl;

	private static Logger logger = Logger.getLogger(CreditServiceImpl.class);

	/**
	 * 赠送积分
	 * 
	 * @return
	 */
	private Map<String, Object> giftIntegral(String orderNo, Long point, String remark, CreditEventEnum eventType,
			String ruleType) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		// 必输
		params.put("merCustNo", orderNo);
		params.put("event", eventType.getValue() + "");
		params.put("ruleType", ruleType);
		params.put("txIntegral", Long.toString(point));
		params.put("txAmount", "");
		params.put("remark", remark);
		params.put("acceptBizNo", acceptBizNo);
		params.put("merOrderId", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));// orderId);
		params.put("custType", "P");
		params.put("bizType", "01");
		params.put("txType", "A0");
		// 必输
		params.put("acceptBizJrnNo", System.currentTimeMillis());
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		String result;
		try {
			logger.info("[电子账户赠送积分接口请求报文]:" + JsonUtils.toJson(params));
			result = HttpClientUtil.postJson(engineMengTopUpUrl, params, "UTF-8");
			logger.info("[电子账户赠送积分接口返回报文]:" + result);
			resultMap = JsonUtils.toObject(result, Map.class);
			if (!"00".equals(resultMap.get("txStatus"))) {
				return null;
			}
		} catch (JsonProcessingException e) {
			logger.error("赠送积分JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("赠送积分失败：", e);
			return null;
		}
		return resultMap;
	}

	/**
	 * @throws ServiceException
	 * 			@throws JsonProcessingException @Title:
	 *             invokEngine @Description: 调用积分引擎接口 @param @param request
	 *             设定文件 @return string 返回类型 @throws
	 */
	public Map<String, Object> engineMengTopUpByRule(String orderNo, String remark, CreditEventEnum eventType) {
		return giftIntegral(orderNo, 0L, remark, eventType, "1");
	}

	/**
	 * 赠送积分，无规则
	 * 
	 * @return
	 */
	public Map<String, Object> engineMengTopUp(String orderNo, Long point, String remark, CreditEventEnum eventType) {
		return giftIntegral(orderNo, point, remark, eventType, "2");
	}

	/**
	 * 萌值消费
	 * 
	 * @param order
	 *            订单对象
	 * @param userName
	 *            用户名
	 * @return
	 */
	public Map<String, Object> accountVirAcOut(String pricePoint, String orderId, String sn) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		params.put("acType", "11");
		// 必输
		params.put("merCustNo", sn);
		params.put("txType", "00");
		params.put("txAmt", pricePoint);
		params.put("ccy", "MG");
		params.put("remark", "");
		params.put("bizType", "11");
		params.put("acceptBizNo", acceptBizNo);
		// 必输
		params.put("acceptBizJrnNo", System.currentTimeMillis());
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		params.put("merOrderId", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));// orderId);
		params.put("custType", "P");
		params.put("bizType", "01");
		params.put("txType", "A0");
		String result;
		try {
			logger.info("[订单萌值消费接口请求报文]:" + JsonUtils.toJson(params));
			result = HttpClientUtil.postJson(accountVirAcOutUrl, params, "UTF-8");
			logger.info("[订单萌值消费接口返回报文]:" + result);
			resultMap = JsonUtils.toObject(result, Map.class);

		} catch (JsonProcessingException e) {
			// e.printStackTrace()
			logger.error("消费积分JSON解析失败：", e);
		} catch (ServiceException e) {
			logger.error("消费积分失败：", e);
		}
		return resultMap;
	}

}
