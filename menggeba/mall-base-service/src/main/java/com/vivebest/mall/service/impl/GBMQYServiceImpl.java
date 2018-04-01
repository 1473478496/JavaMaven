package com.vivebest.mall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vivebest.mall.core.exception.ServiceException;
import com.vivebest.mall.core.util.DateUtil;
import com.vivebest.mall.core.util.HttpClientUtil;
import com.vivebest.mall.core.util.JsonUtils;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsCode;
import com.vivebest.mall.entity.RightsTrade;
import com.vivebest.mall.service.GBMQYService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 权益扫码接口
 */
@Service("apiGBMQYServiceImpl")
public class GBMQYServiceImpl implements GBMQYService {
	
	private static Logger logger = Logger.getLogger(GBMQYServiceImpl.class);
	
	private static final String acceptBizNo = "gbm";
	
	@Value("${rights.Services.Sync.url}")
	private String services;
	@Value("${rights.BaseInfo.Sync.url}")
	private String baseInfo;
	@Value("${rights.Purchase.Sync.url}")
	private String purchase;
	
	@Override
	public Map<String, Object> serviceSync(RightsTrade rightsTrade) {
		return this.rightsServicesSync(rightsTrade);
	}
	
	@Override
	public Map<String, Object> baseInfoSync(Rights rights) {
		return this.rightsBaseInfoSync(rights);
	}
	
	@Override
	public Map<String, Object> purchaseSync(Rights rights, List<RightsCode> rightsCodeList, Member member) {
		return this.rightsPurchaseSync(rights, rightsCodeList, member);
	}
	
	/**
	 * 权益服务商同步
	 */
	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> rightsServicesSync(RightsTrade rightsTrade){
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		//必输
		params.put("services_name", rightsTrade.getName());
		params.put("services_region", rightsTrade.getRemark());
		//必输
		params.put("services_account", rightsTrade.getRlogin());
		params.put("services_phone", rightsTrade.getPhone());
		params.put("createtime", DateUtil.dateToString(rightsTrade.getCreateDate()));
		// 必输
		params.put("txCode", "rightsServicesSyncProcess");
		// 必输
		params.put("acceptBizNo", acceptBizNo);
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		String result;
		try {
			logger.info("[权益服务商同步接口请求报文]:" + JsonUtils.toJson(params));
			result = HttpClientUtil.postJson(services, params, "UTF-8");
			logger.info("[权益服务商同步接口返回报文]:" + result);
			resultMap = JsonUtils.toObject(result, Map.class);
			if (!"00".equals(resultMap.get("txStatus"))) {
				return null;
			}
		} catch (JsonProcessingException e) {
			logger.error("权益服务商同步JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("权益服务商同步失败：", e);
			return null;
		}
		return resultMap;
	}
	
	/**
	 * 权益基础数据同步
	 */
	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> rightsBaseInfoSync(Rights rights){
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		StringBuffer name = new StringBuffer();
		for(RightsTrade rightsTrade: rights.getRightsTrades()){
			name.append(rightsTrade.getName()).append(",");
		}
		if(name.length() > 1){
			name.deleteCharAt(name.length() - 1);
		}
		params.put("rights_code", rights.getSn());//必输
		params.put("rights_name", rights.getName());//必输
		params.put("rights_services_name", name);//必输
		params.put("createdate", DateUtil.dateToString(rights.getCreateDate()));//必输
		// 必输
		params.put("txCode", "rightsBaseInfoSyncProcess");
		// 必输
		params.put("acceptBizNo", acceptBizNo);
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		String result;
		try {
			logger.info("[权益基础数据同步接口请求报文]:" + JsonUtils.toJson(params));
			result = HttpClientUtil.postJson(baseInfo, params, "UTF-8");
			logger.info("[权益基础数据同步接口返回报文]:" + result);
			resultMap = JsonUtils.toObject(result, Map.class);
			if (!"00".equals(resultMap.get("txStatus"))) {
				return null;
			}
		} catch (JsonProcessingException e) {
			logger.error("权益基础数据同步JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("权益基础数据同步失败：", e);
			return null;
		}
		return resultMap;
	}
	
	/**
	 * 成功购买权益数据同步
	 */
	@SuppressWarnings({"unchecked" })
	private Map<String, Object> rightsPurchaseSync(Rights rights, List<RightsCode> rightsCodeList, Member member){
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> resultMap = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		for (RightsCode code : rightsCodeList) {
			jsonObject.put("qr_code", code.getCode());
			if(code.getIsUsed()){
				jsonObject.put("qr_status", "1");
			}else{
				jsonObject.put("qr_status", "0");
			}
			jsonArray.add(jsonObject);
		}
		params.put("rights_code", rights.getSn());//必输
		params.put("member_no", member.getSn());//必输
		params.put("member_name", member.getUsername());//必输
		params.put("member_tel", member.getMobile());//必输
		params.put("qr_right_list", jsonArray.toString());//必输
		params.put("createdate", DateUtil.dateToString(rights.getCreateDate()));//必输
		params.put("validatedate", DateUtil.dateToString(rights.getEndDate()));//必输
		// 必输
		params.put("txCode", "rightsPurchaseSyncProcess");
		// 必输
		params.put("acceptBizNo", acceptBizNo);
		// 必输
		params.put("reqDate", DateUtil.dateToString(new Date(), "yyyyMMdd"));
		// 必输
		params.put("reqJrnNo", DateUtil.dateToString(new Date(), "yyyyMMddhhmmss"));
		// 必输
		params.put("reqTime", DateUtil.dateToString(new Date(), "hhmmss"));
		String result;
		try {
			logger.info("[成功购买权益数据同步接口请求报文]:" + JsonUtils.toJson(params));
			result = HttpClientUtil.postJson(purchase, params, "UTF-8");
			logger.info("[成功购买权益数据同步接口返回报文]:" + result);
			resultMap = JsonUtils.toObject(result, Map.class);
			if (!"00".equals(resultMap.get("txStatus"))) {
				return null;
			}
		} catch (JsonProcessingException e) {
			logger.error("成功购买权益数据同步JSON解析失败：", e);
			return null;
		} catch (ServiceException e) {
			logger.error("成功购买权益数据同步失败：", e);
			return null;
		}
		return resultMap;
	}

}
