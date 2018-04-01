package com.vivebest.mall.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.vivebest.mall.entity.GuessYouLike;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.service.GuessYouLikeService;
import com.vivebest.mall.service.ProductService;

/**
 * Controller - 友情链接
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminGuessYouLikeController")
@RequestMapping("/admin/guess_you_like")
public class GuessYouLikeController extends BaseController {
	
	@Resource(name = "guessYouLikeServiceImpl")
	private GuessYouLikeService guessYouLikeService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", guessYouLikeService.findPage(pageable));
		return "/admin/guess_you_like/list";
	}
	
	/**
	 * 新增页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model){
		return "/admin/guess_you_like/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(GuessYouLike guessYouLike,Long[] productIds, RedirectAttributes redirectAttributes){
		if (!isValid(guessYouLike)) {
			return ERROR_VIEW;
		}
		if(productIds == null){
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		for (Product product : productService.findList(productIds)) {
			GuessYouLike youLike = new GuessYouLike();
			youLike.setProduct(product);
			youLike.setSort(guessYouLike.getSort());
			youLike.setDescription(guessYouLike.getDescription());
			youLike.setGuessType(guessYouLike.getGuessType());
			guessYouLikeService.save(youLike);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("guessYouLike", guessYouLikeService.find(id));
		return "/admin/guess_you_like/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(GuessYouLike guessYouLike, RedirectAttributes redirectAttributes){
		if (!isValid(guessYouLike)) {
			return ERROR_VIEW;
		}
		guessYouLikeService.update(guessYouLike, "product");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		guessYouLikeService.delete(ids);
		return SUCCESS_MESSAGE;
	}
	
	/**
	 * 商品选择
	 */
	@RequestMapping(value = "/product_select", method = RequestMethod.GET)
	public @ResponseBody
	List<Map<String, Object>> productSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, false, 20);
			for (Product product : products) {
				if(guessYouLikeService.checkProduct(product.getId())){
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", product.getId());
				map.put("sn", product.getSn());
				map.put("fullName", product.getFullName());
				map.put("path", product.getPath());
				map.put("price", product.getPrice());
				map.put("mg", product.getPricePoint());
				data.add(map);
			}
		}
		return data;
	}
	
}
