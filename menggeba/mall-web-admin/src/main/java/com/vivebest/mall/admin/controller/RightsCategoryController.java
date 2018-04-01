package com.vivebest.mall.admin.controller;

import java.util.Date;
import java.util.HashSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;
import com.vivebest.mall.service.RightsBrandService;
import com.vivebest.mall.service.RightsCategoryService;
import com.vivebest.mall.service.RightsService;

/**
 * Controller - 权益商货品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminRightsCategoryController")
@RequestMapping("/admin/rights_category")
public class RightsCategoryController extends BaseController{
	
	@Resource(name = "rightsCategoryServiceImpl")
	private RightsCategoryService rightsCategoryService;
	@Resource(name = "rightsBrandServiceImpl")
	private RightsBrandService rightsBrandService;
	@Resource(name="rightsServiceImpl")
	private RightsService rightsService;
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		model.addAttribute("rightsCategoryTree", rightsCategoryService.findRoots());
		return "/admin/rights_category/list";
	}
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("rightsCategoryTree", rightsCategoryService.findRoots());
		model.addAttribute("brands", rightsBrandService.findAll());
		return "/admin/rights_category/add";
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(RightsCategory rightsCategory, Long[] brandIds, Long parentId, RedirectAttributes redirectAttributes) {
		rightsCategory.setRightsBrands(new HashSet<RightsBrand>(rightsBrandService.findList(brandIds)));
		if (!isValid(rightsCategory)) {
			return ERROR_VIEW;
		}
		Date date = new Date();
		//rightsCategory.setParent(rightsCategoryService.findById(parentId));
		rightsCategory.setCreateDate(date);
		rightsCategory.setModifyDate(date);
		rightsCategoryService.save(rightsCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id,ModelMap model) {
		model.addAttribute("rightsCategory", rightsCategoryService.find(id));
		model.addAttribute("brands", rightsBrandService.findAll());
		return "/admin/rights_category/edit";
	}
	
	/**
	 *删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id) {
		RightsCategory rightsCategory = rightsCategoryService.find(id);
		if (rightsCategory == null) {
			return ERROR_MESSAGE;
		}
		/*Set<RightsBrand> brands = rightsCategory.getRightsBrands();
		if (brands != null && !brands.isEmpty()) {
			return Message.error("admin.productCategory.deleteExistChildrenNotAllowed");
		}*/
		 
		rightsCategoryService.delete(id);
		return SUCCESS_MESSAGE;
	}

	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(RightsCategory rightsCategory, Long id, Long[] brandIds, Long parentId, RedirectAttributes redirectAttributes) {
		RightsCategory rightsCategory1 = rightsCategoryService.find(id);
		rightsCategory.setRightsBrands(new HashSet<RightsBrand>(rightsBrandService.findList(brandIds)));
		if (!isValid(rightsCategory)) {
			return ERROR_VIEW;
		}
		//rightsCategory.setParent(rightsCategoryService.findById(parentId));
		rightsCategory.setCreateDate(rightsCategory1.getCreateDate());
		rightsCategory.setModifyDate(new Date());
		rightsCategoryService.update(rightsCategory);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
}
