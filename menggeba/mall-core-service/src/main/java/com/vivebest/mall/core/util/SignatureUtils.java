package com.vivebest.mall.core.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


public class SignatureUtils {

	private static final Logger log = (Logger) LoggerFactory
			.getLogger(SignatureUtils.class);

	/**
	 * MD5签名实
	 * 
	 * @param merNo
	 * @param params
	 * @param key
	 * @param cert
	 * @pdOid 4f5d6701-b3fa-4819-86ca-945aee5102f4
	 */
	public static String md5GenerateSignature(String params, String key,
			String charset)

	{
		// 私钥加签
		log.debug("待签名字符串：{}", params);
		log.debug("待签名字符串编码：{}", charset);
		String sign =MD5.sign(params, key, charset);
		log.debug("返回签名结果：{}", sign);
		return sign;
	}

	/**
	 * MD5验签
	 * 
	 * @param merNo
	 * @param params
	 * @param sign
	 * @pdOid 7d181f3d-7815-4723-ac4f-628bec308cb1
	 */
	public static boolean md5ValidateSignature(String params, String sign,
			String key, String charset) {
		log.debug("待验签字符串：{}", params);
		log.debug("待验签字符串编码：{}", charset);
		log.debug("渠道传入签名串：{}", sign);
		boolean result = MD5.verify(params, sign, key, charset);
		log.debug("返回验签结果：{}", result);
		return result;

	}

	/**
	 * 字符编码转对象  
	 * @param charset
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Charset getCharset(String charset) {
		if (!StringUtils.hasText(charset)) {
			charset = "utf-8";
		}
		return Charset.forName(charset);
	}
	
	 /**
		 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
		 * 
		 * @param params
		 *            需要排序并参与字符拼接的参数组
		 * @return 拼接后字符串
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
	    public static String createLinkString(Map<String, Object> params) {

			List<String> keys = new ArrayList<String>(params.keySet());
			Collections.sort(keys);

			String prestr = "";

			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				Object obj = params.get(key);
				if (obj instanceof Map)
					prestr = prestr + key + "==" + createLinkString((Map) obj)
							+ "&&";
				else {
					String value = params.get(key).toString();
					if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
						prestr = prestr + key + "=" + value;
					} else {
						prestr = prestr + key + "=" + value + "&";
					}
				}
			}

			return prestr;
		}

		/**
		 * 除去签名参数
		 * 
		 * @param sArray
		 *            签名参数组
		 * @return 去掉空值的新签名参数组
		 */
		public static Map<String, Object> paraFilter(Map<String, Object> sArray) {

			Map<String, Object> result = new HashMap<String, Object>();

			if (sArray == null || sArray.size() <= 0) {
				return result;
			}

			for (String key : sArray.keySet()) {
				String strValue = null;
				Object value = sArray.get(key);
				if (key.equalsIgnoreCase("sign_type")
						|| key.equalsIgnoreCase("sign")
						|| key.equalsIgnoreCase("$sessionClass")
						|| key.equalsIgnoreCase("TransCode")
						|| key.equalsIgnoreCase("serviceCode")) {
					continue;
				}
				if (value != null) {
					strValue = value.toString().trim();
				}
				if (value == null) {
					strValue = "";
				}

				result.put(key, strValue);
			}

			return result;
		}
}
