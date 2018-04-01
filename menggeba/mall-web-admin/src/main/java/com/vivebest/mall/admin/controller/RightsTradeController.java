package com.vivebest.mall.admin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsTrade;
import com.vivebest.mall.service.AreaService;
import com.vivebest.mall.service.GBMQYService;
import com.vivebest.mall.service.RightsBrandService;
import com.vivebest.mall.service.RightsTradeService;
/**
 * Controller - 商户
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminRightsTradeController")
@RequestMapping("/admin/rights_trade")
public class RightsTradeController extends BaseController{
	@Resource(name = "rightsTradeServiceImpl")
	private RightsTradeService rightsTradeService;
	@Resource(name = "rightsBrandServiceImpl")
	private RightsBrandService rightsBrandService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "apiGBMQYServiceImpl")
	private GBMQYService gbmqyService;
	
	/**
	 * 检查账户是否唯一
	 */
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody boolean checkSn(String previousRlogin, String rlogin) {
		if (StringUtils.isEmpty(rlogin)) {
			return false;
		}
		if (rightsTradeService.rloginUnique(previousRlogin, rlogin)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageabl,ModelMap model) {
		model.addAttribute("page", rightsTradeService.findPage(pageabl));
		return "/admin/rights_trade/list";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		List<RightsBrand> rightsBrands = rightsBrandService.findAll();
		model.addAttribute("rightsBrands", rightsBrands);
		model.addAttribute("trade", rightsTradeService.find(id));
		return "/admin/rights_trade/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(RightsTrade rightsTrade, Long rightsBrandId, Long areaId, RedirectAttributes redirectAttributes) {
		rightsTrade.setRightsBrand(rightsBrandService.find(rightsBrandId));
		rightsTrade.setArea(areaService.find(areaId));
		if (!isValid(rightsTrade)) {
			return ERROR_VIEW;
		}
		rightsTradeService.update(rightsTrade);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("rightsBrands", rightsBrandService.findAll());
		return "/admin/rights_trade/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RightsTrade rightsTrade, Long rightsBrandId, Long areaId, RedirectAttributes redirectAttributes) {
		rightsTrade.setRightsBrand(rightsBrandService.find(rightsBrandId));
		rightsTrade.setArea(areaService.find(areaId));
		if (!isValid(rightsTrade)) {
			return ERROR_VIEW;
		}
		rightsTradeService.save(rightsTrade);
		Map<String, Object> map = gbmqyService.serviceSync(rightsTrade);
		if(map == null){
			addFlashMessage(redirectAttributes, Message.warn("权益服务商同步失败"));
		}else{
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		}
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		/*List<RightsTrade> rightsTrades = rightsTradeService.findList(ids);
		if (rightsTrades.size() == 0) {
			return ERROR_MESSAGE;
		}
		for(RightsTrade rightsTrade:rightsTrades){
			Set<Rights> rightses = rightsTrade.getRightses();
			if (rightses != null && !rightses.isEmpty()) {
				return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
			}
		}*/
		rightsTradeService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
