package com.vivebest.mall.admin.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.ShippingCalculate;
import com.vivebest.mall.entity.ShippingCalculate.AreaType;
import com.vivebest.mall.service.AreaService;
import com.vivebest.mall.service.DeliveryCorpService;
import com.vivebest.mall.service.ShippingCalculateService;
import com.vivebest.mall.service.ShippingMethodService;

/**
 * ShippingCalculate controller
 * 
 * @author junly
 *
 */
@Controller("adminShippingCalculateController")
@RequestMapping("/admin/shippingCalculate")
public class ShippingCalculateController extends BaseController {
	private static Logger logger = Logger.getLogger(ShippingCalculateController.class);
	@Resource(name = "shippingCalculateServiceImpl")
	private ShippingCalculateService shippingCalculateService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String shippingCalculateList(Pageable pageable, ModelMap model) {
		model.addAttribute("page", shippingCalculateService.findPage(pageable));
		return "/admin/shipping_calculate/list";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		Area area=areaService.findByFullNa("广东省深圳市");
		model.addAttribute("area", area);
		return "/admin/shipping_calculate/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ShippingCalculate shippingCalculate, Long shippingMethodId, Long areaId, Long areaId2,
			RedirectAttributes redirectAttributes) {
		if (shippingCalculate == null) {
			return ERROR_VIEW;
		}
		Area area = null;
		if (areaId != null) {
			area = areaService.find(areaId);
			 if (area.getParent() == null) {
				String name=area.getFullName();
				if (name.lastIndexOf("市")==-1) {
					area =areaService.findByFullNa("广东省深圳市");
				}
			 }else{
				 if (area.getParent().getParent()!=null) {
					area=area.getParent();
				}
			 }
			 
		} else {
			area = areaService.findByFullNa("广东省深圳市");
		}
		shippingCalculate.setShippingArea(area);

		// 同城
		if (shippingCalculate.getAreaType().equals(AreaType.sameCity)) {
			shippingCalculate.setConsigneeArea(shippingCalculate.getShippingArea());
		}
		// 省内
		if (shippingCalculate.getAreaType().equals(AreaType.sameProvince)) {
			String str = shippingCalculate.getShippingArea().getTreePath();
			String[] strs = str.split(",");
			long[] longs = new long[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					longs[i] = (long) Integer.valueOf(strs[i]).intValue();
				} catch (NumberFormatException e) {
					logger.info("啦啦啦》》》》》》》》》》》》" + e);
				}
			}
			for (int i = 0; i < longs.length; i++) {
				if (longs[i] > 0) {
					area = areaService.find(longs[i]);
					if (area.getParent() == null) {
						break;
					}
				}
			}
			shippingCalculate.setConsigneeArea(area);
		}
		// 省外
		if (shippingCalculate.getAreaType().equals(AreaType.transProvince)) {
			if (areaId2 == null) {
				return ERROR_VIEW;
			} else {
				area = areaService.find(areaId2);
				String str = area.getTreePath();
				String[] strs = str.split(",");
				long[] longs = new long[strs.length];
				for (int i = 0; i < strs.length; i++) {
					try {
						longs[i] = (long) Integer.valueOf(strs[i]).intValue();
					} catch (NumberFormatException e) {
						logger.info("啦啦啦》》》》》》》》》》》》" + e);
					}
				}
				for (int i = 0; i < longs.length; i++) {
					if (longs[i] > 0) {
						Area area2 = areaService.find(longs[i]);
						if (area2.getParent() ==null) {
							area = area2;
							break;
						}
					}
				}
				shippingCalculate.setConsigneeArea(area);
			}
		}
		if (shippingMethodId == null || shippingMethodId == 0) {
			return ERROR_VIEW;
		} else {
			shippingCalculate.setShippingMethod(shippingMethodService.find(shippingMethodId));
		}
		try {
			shippingCalculateService.save(shippingCalculate);
		} catch (Exception e) {
			logger.info("-------------->>>" + shippingCalculate.toString());
		}
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("shippingCalculate", shippingCalculateService.find(id));
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		return "/admin/shipping_calculate/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(ShippingCalculate shippingCalculate, Long id, Long shippingMethodId, Long areaId, Long areaId2,
			RedirectAttributes redirectAttributes) {
		Area area = null;
		if (areaId != null) {
			area = areaService.find(areaId);
			 if (area.getParent() == null) {
				String name=area.getFullName();
				if (name.lastIndexOf("市")==-1) {
					area =areaService.findByFullNa("广东省深圳市");
				}
			 }else{
				 if (area.getParent().getParent()!=null) {
					area=area.getParent();
				}
			 }
			 
		} else {
			area = areaService.findByFullNa("广东省深圳市");
		}
		shippingCalculate.setShippingArea(area);
		// 同城
		if (shippingCalculate.getAreaType().equals(AreaType.sameCity)) {
			shippingCalculate.setConsigneeArea(shippingCalculate.getShippingArea());
		}
		// 省内
		if (shippingCalculate.getAreaType().equals(AreaType.sameProvince)) {
			String str = shippingCalculate.getShippingArea().getTreePath();
			String[] strs = str.split(",");
			long[] longs = new long[strs.length];
			for (int i = 0; i < strs.length; i++) {
				try {
					longs[i] = (long) Integer.valueOf(strs[i]).intValue();
				} catch (NumberFormatException e) {
					logger.info("啦啦啦》》》》》》》》》》》》" + e);
				}
			}
			for (int i = 0; i < longs.length; i++) {
				if (longs[i] > 0) {
					area = areaService.find(longs[i]);
					if (area.getParent() == null) {
						break;
					}
				}
			}
			shippingCalculate.setConsigneeArea(area);
		}
		// 省外
		if (shippingCalculate.getAreaType().equals(AreaType.transProvince)) {
			if (areaId2 == null) {
				return ERROR_VIEW;
			} else {
				area = areaService.find(areaId2);
				String str = area.getTreePath();
				String[] strs = str.split(",");
				long[] longs = new long[strs.length];
				for (int i = 0; i < strs.length; i++) {
					try {
						longs[i] = (long) Integer.valueOf(strs[i]).intValue();
					} catch (NumberFormatException e) {
						logger.info("啦啦啦》》》》》》》》》》》》" + e);
					}
				}
				for (int i = 0; i < longs.length; i++) {
					if (longs[i] > 0) {
						Area area2 = areaService.find(longs[i]);
						if (area2.getParent() == null) {
							area = area2;
							break;
						}
					}
				}
				shippingCalculate.setConsigneeArea(area);
			}
		}

		if (shippingMethodId == null || shippingMethodId == 0) {
			return ERROR_VIEW;
		} else {
			logger.info("-------------->>>" + shippingMethodService.find(shippingMethodId).getName());
			shippingCalculate.setShippingMethod(shippingMethodService.find(shippingMethodId));
		}
		// ShippingCalculate shippingCal=shippingCalculateService.find(id);
		shippingCalculateService.update(shippingCalculate);
		return "redirect:list.jhtml";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		shippingCalculateService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
