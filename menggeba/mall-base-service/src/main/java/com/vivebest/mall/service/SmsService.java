package com.vivebest.mall.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.SmsBusType;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.Booking;
import com.vivebest.mall.entity.MemberBirthdayRigths;
import com.vivebest.mall.entity.ProductNotify;
import com.vivebest.mall.entity.Sms;

/**
 * 
 * @author liu.jch
 * 
 */
@Service
public interface SmsService extends BaseService<Sms, Long>{

	/**
	 * 发送短信
	 * 
	 * @param smsBusType
	 * @param vars
	 * @param numbers
	 * @return
	 */
	public int sendMessage(SmsBusType smsBusType, Map<String, Object> vars,
			String... numbers);

	/**
	 * 发送到货通知短信
	 * @param productNotify
	 */
	public void sendProductNotifySms(ProductNotify productNotify);
	
	/**
	 * 发送生日权益通知
	 * @param memberBirthdayRigths
	 */
	public void sendMemberBirthdayRightsSms(MemberBirthdayRigths memberBirthdayRigths);
	
	/**
	 * 根据手机号与发送状态查找记录
	 * @param number
	 * @param status
	 */
	List<Sms> findByNumAndStatus(String number, int status);
	/**
	 * 发送团购商品购买提示信息
	 * @param booking
	 */
	public void sendBookingNotifySms(Booking booking);

	public List<Sms> findLikeContent(String mobile, String code);
}
