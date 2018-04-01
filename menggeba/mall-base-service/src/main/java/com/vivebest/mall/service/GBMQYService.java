package com.vivebest.mall.service;

import java.util.List;
import java.util.Map;

import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsCode;
import com.vivebest.mall.entity.RightsTrade;

/**
 * 权益扫码接口
 */
public interface GBMQYService {

	/**
	 * 权益服务商同步
	 * @param rightsTrade
	 * @return
	 */
	Map<String, Object> serviceSync(RightsTrade rightsTrade);

	/**
	 * 权益基础数据同步
	 * @param rights
	 * @return
	 */
	Map<String, Object> baseInfoSync(Rights rights);

	/**
	 * 成功购买权益数据同步
	 * @param rights
	 * @param rightsCodeList
	 * @param member
	 * @return
	 */
	Map<String, Object> purchaseSync(Rights rights, List<RightsCode> rightsCodeList, Member member);

}
