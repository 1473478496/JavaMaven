package com.vivebest.mall.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.core.util.ExportUtil;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SpringUtils;
import com.vivebest.mall.entity.RightOrder;
import com.vivebest.mall.entity.RightOrder.RightsOrderStatus;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.RightOrderItemService;
import com.vivebest.mall.service.RightOrderService;


/**
 * Controller - 权益订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminRightsOrderController")
@RequestMapping("/admin/rights_order")
public class RightsOrderController extends BaseController{
	
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "rightsOrderServiceImpl")
	private RightOrderService rightsOrderService;
	@Resource(name = "rightsOrderItemServiceImpl")
	private RightOrderItemService rightsOrderItemService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(RightsOrderStatus orderStatus,Boolean hasExpired,Pageable pageable,ModelMap model){
		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("hasExpired", hasExpired);
		model.addAttribute("page",
				rightsOrderService.findPage(orderStatus,hasExpired, pageable));
		return "/admin/rights_order/list";
	}
	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("order", rightsOrderService.find(id));
		return "/admin/rights_order/view";
	}
	/**
	 * 导出数据
	 */
	@RequestMapping(value = "/exportData", method = RequestMethod.POST)
	public void exportData(RightsOrderStatus orderStatus,Boolean hasExpired, Pageable pageable, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<RightOrder> orders = rightsOrderService.findList(orderStatus,hasExpired,pageable);
			Setting setting = SettingUtils.get();
			String[] headers = {	SpringUtils.getMessage("Order.sn", setting.getSiteName()), 
									SpringUtils.getMessage("Order.amount", setting.getSiteName()), 
									SpringUtils.getMessage("订单萌值", setting.getSiteName()), 
									SpringUtils.getMessage("Order.member", setting.getSiteName()), 
									SpringUtils.getMessage("手机号", setting.getSiteName()), 
									SpringUtils.getMessage("Order.orderStatus", setting.getSiteName()), 
									SpringUtils.getMessage("admin.common.createDate", setting.getSiteName()) 
								};
			String[] values = { "getSn", "getPrice", "getPricePoint", "getMember.getUsername", "getMember.getMobile", 
					 "getRightsOrderStatus", "getCreateDate" };
			Map<String, String> enumMap = new HashMap<String, String>();
			enumMap.put(RightOrder.RightsOrderStatus.class.toString(), "Order.OrderStatus.");
			
			ExportUtil<RightOrder> ex = new ExportUtil<RightOrder>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String filedisplay = SpringUtils.getMessage("Order.shippingExportName", setting.getSiteName()) + sdf.format(new Date()) + ".xls";
			String title = SpringUtils.getMessage("Order.shippingExportName", setting.getSiteName());
			ex.exportExcel(request, response, filedisplay, title, headers, values, orders, "yyyy-MM-dd", enumMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
