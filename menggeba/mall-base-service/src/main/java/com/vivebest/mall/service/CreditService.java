package com.vivebest.mall.service;

import java.util.Map;

import com.vivebest.mall.core.constants.CreditEventEnum;

/**
 * 赠送积分Service - 
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface CreditService{
	/**
	 * 赠送积分
	 * @param orderNo
	 * @param point
	 * @param remark
	 * @param eventType
	 * @param ruleType
	 * @return
	 */
	Map<String, Object> engineMengTopUpByRule(String orderNo, String remark, CreditEventEnum eventType);
	
	/**
	 * 赠送积分
	 * 
	 * @param order
	 * @param point
	 * @param Remark
	 * @param EventType
	 * @return
	 */
	Map<String, Object> engineMengTopUp(String orderNo, Long point, String remark, CreditEventEnum eventType) ;
	
	/**
	 * 萌值消费
	 * @param pricePoint
	 * @param orderId
	 * @param sn
	 * @return
	 */
	Map<String, Object> accountVirAcOut(String pricePoint, String orderId, String sn);

}
