package com.vivebest.mall.admin.controller;

import java.util.List;

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
import com.vivebest.mall.entity.RightsBrand.Type;
import com.vivebest.mall.service.RightsBrandService;

/**
 * Controller - 品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminRightsBrandController")
@RequestMapping("/admin/rights_brand")
public class RightsBrandController extends BaseController{
	
	@Resource(name = "rightsBrandServiceImpl")
	private RightsBrandService rightsBrandService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable,ModelMap model) {
		model.addAttribute("page", rightsBrandService.findPage(pageable));
		return "/admin/rights_brand/list";
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("types", Type.values());
		model.addAttribute("brand", rightsBrandService.find(id));
		return "/admin/rights_brand/edit";
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(RightsBrand brand, RedirectAttributes redirectAttributes) {
		if (!isValid(brand)) {
			return ERROR_VIEW;
		}
		if (brand.getType() == Type.text) {
			brand.setLogo(null);
		} else if (StringUtils.isEmpty(brand.getLogo())) {
			return ERROR_VIEW;
		}
		rightsBrandService.update(brand,"rightsCategories","rights");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("types", Type.values());
		return "/admin/rights_brand/add";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RightsBrand brand, RedirectAttributes redirectAttributes) {
		if (!isValid(brand)) {
			return ERROR_VIEW;
		}
		if (brand.getType() == Type.text) {
			brand.setLogo(null);
		} else if (StringUtils.isEmpty(brand.getLogo())) {
			return ERROR_VIEW;
		}
		
		brand.setRightsCategories(null);
		 
		
		rightsBrandService.save(brand);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		List<RightsBrand> rightsBrands = rightsBrandService.findList(ids);
		if(rightsBrands.size() == 0){
			return ERROR_MESSAGE;
		}
		/*for (RightsBrand rightsBrand : rightsBrands) {
			Set<RightsCategory> rightsCategories = rightsBrand.getRightsCategories();
			if (rightsCategories != null && !rightsCategories.isEmpty()) {
				return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
			}
		 
			 
		}*/
		rightsBrandService.delete(ids);
		return SUCCESS_MESSAGE;
	}
}
