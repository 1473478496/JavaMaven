package com.vivebest.mall.web.merchant.api;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.controller.api.BaseAPIController;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.merchant.service.OrderService;


@Controller("orderAPIController")
@RequestMapping("/api/order")
public class OrderAPIController extends BaseAPIController {

	@Autowired
	private OrderService orderService;

	/**
	 * 赠送优惠券接口
	 * 
	 * @param membername
	 * @param couponName
	 * @return
	 */
	@RequestMapping(value = "/getOrderInfo")
	public @ResponseBody Map<String, Object> getOrderInfo(HttpServletRequest request) {

		Map<String, Object> respParams = new HashMap<String, Object>();
		try {
			// 读取请求内容转成map
			Map<String, Object> reqParam = streamToMap(request.getInputStream());
			logger.info(reqParam);
			if (reqParam == null) {
				respParams.put("code", "700001");
				respParams.put("message", "订单号不存在");
				return respParams;
			}

			//验证签名
			if (!md5Verify(reqParam)) {
				logger.info("验签失败");
				respParams.put("code", "600002");
				respParams.put("message", "订单信息状态异常");
				return respParams;
			}

			// 取得订单ID
			String orderNo = (String) reqParam.get("orderNo");

			respParams.put("code", "0000");
			respParams.put("message", "成功");

			// 组装签名
			String sign = md5Sign(respParams);
			respParams.put("sign", sign);
			logger.info("返回报文: " + respParams);
			return respParams;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("strToJson exception:" + e);
			respParams.put("code", "600002");
			respParams.put("message", "订单信息状态异常");
			return respParams;
		}
	}
	@RequestMapping
	@ResponseBody
	public List<Order> queryOrderList(String orderStatus,Member member,String sn,Date date){
		
		
		return null;
		
	}

}
