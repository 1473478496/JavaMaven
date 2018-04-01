package com.vivebest.mall.core.controller.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivebest.mall.core.controller.view.BaseController;
import com.vivebest.mall.core.util.JsonUtils;
import com.vivebest.mall.core.util.MD5;
import com.vivebest.mall.core.util.SignatureUtils;

import net.sf.json.JSONObject;

@Controller("baseAPIController")
@RequestMapping("/api")
public class BaseAPIController extends BaseController {

	@Value("${balance.md5.key}")
	private String balanceMd5Key;

	/**
	 * 把InputStream转为JSON对象
	 * 
	 * @param stream
	 * @return
	 */
	public JSONObject streamToJson(InputStream stream) {
		// 读取请求内容
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("接收报文: " + sb.toString());
			// 转换JSON对象
			return JSONObject.fromObject(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("strToJson exception:" + e);
			return null;
		}
	}

	/**
	 * 把InputStream转为JSON对象
	 * 
	 * @param stream
	 * @return
	 */
	public Map<String, Object> streamToMap(InputStream stream) {
		// 读取请求内容
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			logger.info("接收报文: " + sb.toString());
			// 转换JSON对象
			return JsonUtils.toObject(sb.toString(), Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("strToJson exception:" + e);
			return null;
		}
	}

	/**
	 * 验签
	 * 
	 * @param param
	 * @return
	 */
	public boolean md5Verify(Map<String, Object> param) {
		// 验签
		String sign = (String) param.get("sign");
		param = SignatureUtils.paraFilter(param);		
		String signStr = SignatureUtils.createLinkString(param);
		logger.info("Md5 Key： " + balanceMd5Key);
		logger.info("验签报文： " + param);
		return MD5.verify(signStr, sign, balanceMd5Key, CHARSET);
	}

	/**
	 * 签名
	 * 
	 * @param param
	 * @return
	 */
	public String md5Sign(Map<String, Object> param) {
		// 签名
		param = SignatureUtils.paraFilter(param);
		String signStr = SignatureUtils.createLinkString(param);
		
		logger.info("Md5 Key： " + balanceMd5Key);
		logger.info("签名报文： " + param);
		
		return MD5.sign(signStr, balanceMd5Key, CHARSET);
	}

}
