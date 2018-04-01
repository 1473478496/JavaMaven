package com.vivebest.mall.admin.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.service.MemberBirthdayRigthsService;

/**
 * Controller - 会员权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("memberBirthdayRightsController")
@RequestMapping("/admin/member_bir_rights")
public class MemberBirthdayRightsController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MemberBirthdayRightsController.class);
	
	@Resource(name = "memberBirthdayRigthsServiceImpl")
	private MemberBirthdayRigthsService memberBirthdayRigthsService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model, String bDate, String eDate) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		if (bDate != null) {
			try {
				beginDate = format.parse(bDate);
			} catch (ParseException e) {
				logger.error("日期格式化失败", e);
				return ERROR_VIEW;
			}
			Calendar calendar = DateUtils.toCalendar(beginDate);
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			beginDate = calendar.getTime();
		}
		Date endDate = null;
		if (eDate != null) {
			try {
				endDate = format.parse(eDate);
			} catch (ParseException e) {
				logger.error("日期格式化失败", e);
				return ERROR_VIEW;
			}
			Calendar calendar = DateUtils.toCalendar(endDate);
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			endDate = calendar.getTime();
		}
		//返回起始和结束时间
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("page", memberBirthdayRigthsService.findPage(beginDate, endDate, pageable));
		return "/admin/member_bir_rights/list";
	}
	
	/**
	 * 发送通知
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public @ResponseBody
	Message send(Long[] ids, HttpServletRequest request) {
		int count = memberBirthdayRigthsService.send(ids, request);
		return Message.success("admin.memberBirthdayRights.sentSuccess", count);
	}
	
	
}
