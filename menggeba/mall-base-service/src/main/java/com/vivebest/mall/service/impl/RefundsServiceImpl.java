/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.core.util.JsonUtils;
import com.vivebest.mall.core.util.MD5;
import com.vivebest.mall.core.util.SignatureUtils;
import com.vivebest.mall.dao.OrderDao;
import com.vivebest.mall.dao.RefundsDao;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Refunds.Status;
import com.vivebest.mall.service.RefundsService;

/**
 * Service - 退款单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long>implements RefundsService {

	private static Logger logger = Logger.getLogger(RefundsServiceImpl.class);

	@Value("${pay.manager.md5.key}")
	private String managerMd5Key;
	@Value("${pay.acceptBizNo}")
	private String acceptBizNo;
	@Value("${pay.refund.url}")
	private String refundOrderUrl;
	@Value("${pay.query.refund.url}")
	private String queryRefundUrl;
	@Value("${pay.refundBack.url}")
	private String refundBackUrl;

	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

	@Resource(name = "refundsDaoImpl")
	public void setBaseDao(RefundsDao refundsDao) {
		super.setBaseDao(refundsDao);
	}

	@Transactional(readOnly = true)
	public Refunds findBySn(String sn) {
		return refundsDao.findBySn(sn);
	}

	@Transactional(readOnly = true)
	public Page<Refunds> findPage(Status status, Pageable pageable) {
		return refundsDao.findPage(status, pageable);
	}

	/**
	 * 定时任务批量退款
	 */
	public void refund(int pageNumber, int pageSize) {
		StringBuffer refundsSuccessStr = new StringBuffer();
		StringBuffer refundingStr = new StringBuffer();
		StringBuffer refundsFailStr = new StringBuffer();
		String refundResult = "";
		for (int i = pageNumber;; i++) {
			Page<Refunds> page = refundsDao.findPage(Status.refundSuccess, new Pageable(i, pageSize));
			List<Refunds> list = page.getContent();
			if (!(list != null && list.size() > 0)) {
				return;
			}
			for (Refunds refunds : list) {
				if (null != refunds && null != refunds.getSeqUuid()) {
					// 查询退款单状态，返回空不更新， 01 等待退款 00 退款成功 11 退款失败 03 退款超时 04 退款处理中
					refundResult = queryRefundStatus(refunds);
					if (!"".equals(refundResult)) {
						refunds.setStatus(Refunds.Status.refundSuccess);
						refundsSuccessStr.append(refunds.getSn()).append(",");
						// 退款
					} else {
						Order order = refunds.getOrder();
						Map<String, Object> map = getwayRefund(order, refunds);
						if (null != map) {
							refunds.setSeqUuid((String) map.get("seqUuid"));
							logger.info("[退款uuid]:" + (String) map.get("seqUuid"));
							refunds.setStatus(Refunds.Status.refunding);
							refunds.setType(Refunds.Type.onlyRefund);
							refundingStr.append(refunds.getSn()).append(",");
						} else {
							refunds.setStatus(Refunds.Status.refundFail);
							refunds.setType(Refunds.Type.onlyRefund);
							refundsFailStr.append(refunds.getSn()).append(",");
						}
					}
				} else {
					Order order = refunds.getOrder();
					Map<String, Object> map = getwayRefund(order, refunds);
					if (null != map) {
						logger.info("[退款uuid]:" + (String) map.get("seqUuid"));
						refunds.setSeqUuid((String) map.get("seqUuid"));
						refunds.setStatus(Refunds.Status.refunding);
						refunds.setType(Refunds.Type.onlyRefund);
						refundingStr.append(refunds.getSn()).append(",");
					}
				}
			}
			refundsDao.flush();

			if (list.size() < pageSize) {
				logger.info("[退款失败退款单号]:" + refundsFailStr.toString());
				logger.info("[退款中退款单号]:" + refundingStr.toString());
				logger.info("[退款成功退款单号]:" + refundsSuccessStr.toString());
				return;
			}
		}
	}

	/**
	 * 调用支付管家退款接口
	 * 
	 * @param order
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getwayRefund(Order order, Refunds refunds) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		params.put("acceptBizNo", acceptBizNo);
		params.put("ordNo", order.getSn());
		params.put("ordDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		params.put("ordTime", DateUtil.dateToString(new Date(), "HHmmss"));
		params.put("chnRemark", "");
		params.put("amt", refunds.getAmount().setScale(2, BigDecimal.ROUND_DOWN).toString());
		params.put("notifyUrl", refundBackUrl);
		params.put("refundNeedPwd", "N");
		// 必输
		params.put("ccy", "CNY");
		// 必输
		params.put("acceptBizJrnNo", System.currentTimeMillis());
		params.put("remark", "");
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		params.put("txCode", "gatewayRefundProcess");
		params.put("version", "1.0.1");
		params = SignatureUtils.paraFilter(params);
		String signStr = SignatureUtils.createLinkString(params);
		String sign = MD5.sign(signStr, managerMd5Key, "UTF-8");
		params.put("sign", sign);
		String result;
		try {
			logger.info("[支付管家退款接口请求报文]:" + JsonUtils.toJson(params));
			result = HttpClientUtil.postJson(refundOrderUrl, params, "UTF-8");
			resultMap = JsonUtils.toObject(result, Map.class);
			logger.info("[支付管家退款接口返回报文]:" + resultMap);
			// if (!"00".equals(resultMap.get("txStatus"))) {
			// return null;
			// }
		} catch (JsonProcessingException e) {
			logger.info("[支付管家退款接口返回报文]:" + resultMap);
			return null;
		} catch (ServiceException e) {
			return null;
		}
		return resultMap;
	}

	/**
	 * 退款查询接口
	 * 
	 * @param order
	 * @return 空不更新， 01 等待退款 00 退款成功 11 退款失败 03 退款超时 04 退款处理中
	 * 
	 */
	@SuppressWarnings("unchecked")
	private String queryRefundStatus(Refunds refunds) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		params.put("seqUuid", refunds.getSeqUuid());
		// 必输
		params.put("acceptBizNo", acceptBizNo);
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		params.put("txCode", "gatewayRefundGetResultProcess");
		params.put("version", "1.0.1");
		params = SignatureUtils.paraFilter(params);
		String signStr = SignatureUtils.createLinkString(params);
		String sign = MD5.sign(signStr, managerMd5Key, "UTF-8");
		params.put("sign", sign);
		String result;
		try {

			logger.info("[支付管家查询退款接口请求报文]:" + JsonUtils.toJson(params));

			result = HttpClientUtil.postJson(queryRefundUrl, params, "UTF-8");
			resultMap = JsonUtils.toObject(result, Map.class);
			logger.info("[支付管家查询退款接口返回报文]:" + resultMap);

			if ("00".equals(resultMap.get("txStatus"))) {
				return (String) resultMap.get("refundResult");
			}
		} catch (JsonProcessingException e) {
			logger.error("queryRefundStatus error: " + e);
		} catch (ServiceException e) {
			logger.error("queryRefundStatus error: " + e);
		}
		return "";
	}

	@Override
	public List<Refunds> findByOrderId(Long orderId) {
		return refundsDao.findByOrderId(orderId);
	}

	@Override
	public Long unRefundsCount(Refunds refunds) {
		return refundsDao.unRefundsCount(refunds);
	}

}