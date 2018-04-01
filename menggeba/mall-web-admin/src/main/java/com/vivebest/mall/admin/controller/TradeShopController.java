package com.vivebest.mall.admin.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.entity.TradeShop.PlatStatus;
import com.vivebest.mall.merchant.entity.TradeShop.Status;
import com.vivebest.mall.merchant.service.TradeShopService;
@Controller("tradeShopController")
@RequestMapping("admin/tradeShop")
public class TradeShopController extends BaseController {
	
	@Resource(name="tradeShopServiceImpl")
	private TradeShopService tradeShopService;
	
	/**
	 * 列表
	 * @param status
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET )
	public String list(Status status,PlatStatus platStatus,Pageable pageable,ModelMap model){
		model.addAttribute("status",status);
		Page page = tradeShopService.findPage(status,platStatus,pageable);
		model.addAttribute("page",page);
		return "/admin/shop/list";
	}
	
	
	@RequestMapping(value="close",method=RequestMethod.POST)
	public @ResponseBody Message close(Long id) {
		TradeShop tradeShop = tradeShopService.find(id);
		if(PlatStatus.open.equals(tradeShop.getPlatStatus())){
			tradeShopService.close(tradeShop);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	
	
	@RequestMapping(value="open",method=RequestMethod.POST)
	public @ResponseBody Message open(Long id) {
		TradeShop tradeShop = tradeShopService.find(id);
		if(PlatStatus.close.equals(tradeShop.getPlatStatus())){
			tradeShopService.open(tradeShop);
			return SUCCESS_MESSAGE;
		}
		return ERROR_MESSAGE;
	}
	

}
