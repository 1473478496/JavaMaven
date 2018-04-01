package com.vivebest.mall.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.ProductCateIndex;
import com.vivebest.mall.entity.ProductRecommond;
import com.vivebest.mall.service.AdService;
import com.vivebest.mall.service.ProductCateIndexService;
import com.vivebest.mall.service.ProductCategoryService;
import com.vivebest.mall.service.ProductRecommondService;
import com.vivebest.mall.service.ProductService;

/**
 * Controller - 首页显示
 * 
 * @author ding.hy 
 * @version 3.0
 */
@Controller("productCateIndexController")
@RequestMapping("/admin/product_cate_index")
public class ProductCateIndexController extends BaseController {
	
	@Resource(name = "productCateIndexServiceImpl")
	private ProductCateIndexService productCateIndexService;
	
	@Resource(name = "productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	
	@Resource(name = "adServiceImpl")
	private AdService adserviceImpl;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "productRecommondServiceImpl")
	private ProductRecommondService productRecommondService;
	
	/**
	 * 检查名称是否已存在
	 */
	@RequestMapping(value = "/check_name", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String name, Long id) {
		if (StringUtils.isEmpty(name)) {
			return false;
		}
		if(id != null){
			if(name.equals(productCateIndexService.find(id).getName())){
				return true;
			}
		}
		if(productCateIndexService.checkUsername(name)){
			return false;
		}
		return true;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {
		List <ProductCateIndex> productCateIndexs = productCateIndexService.findAll(null);
		model.addAttribute("productCateIndexs", productCateIndexs);
		model.addAttribute("ads", adserviceImpl.findAll());
		return "/admin/product_cate_index/list";
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		//model.addAttribute("productCategorys", productCategoryService.findTree());
		model.addAttribute("ads", adserviceImpl.findAll());
		return "/admin/product_cate_index/add";
	}
	
	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Long[] productIds, Long[] orders, ProductCateIndex productCateIndex, Long productCateId, Long adId, RedirectAttributes redirectAttributes) {
		if (!isValid(productCateIndex)) {
			return ERROR_VIEW;
		}
		/*if (productCateId == null){
			return ERROR_VIEW;
		}*/
		if(productIds == null){
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		if(adId != null){
			productCateIndex.setAd(adserviceImpl.find(adId));
		}
		productCateIndexService.save(productCateIndex);
		int i = 0;
		for (Product product : productService.findList(productIds)) {
			ProductRecommond productRecommond = new ProductRecommond();
			productRecommond.setOrders(orders[i]);
			productRecommond.setProduct(product);
			productRecommond.setProductCategoryIndex(productCateIndexService.findByName(productCateIndex.getName()));
			productRecommondService.save(productRecommond);
			i++;
		}
		//productCateIndex.setProductCategory(productCategoryService.find(productCateId));
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model){
		//model.addAttribute("productCategorys", productCategoryService.findTree());
		model.addAttribute("productCateIndex", productCateIndexService.find(id));
		model.addAttribute("ads", adserviceImpl.findAll());
		return "/admin/product_cate_index/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Long[] productIds, Long[] orders, ProductCateIndex productCateIndex, Long productCateId, Long adId, RedirectAttributes redirectAttributes){
		if (!isValid(productCateIndex)) {
			return ERROR_VIEW;
		}
		/*if (productCateId == null){
			return ERROR_VIEW;
		}*/
		//productCateIndex.setProductCategory(productCategoryService.find(productCateId));
		if(productIds == null){
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		if(adId != null){
			productCateIndex.setAd(adserviceImpl.find(adId));
		}
		ProductCateIndex cateIndex = productCateIndexService.update(productCateIndex);
		List<ProductRecommond> productRecommonds = cateIndex.getProductRecommonds();
		for (Iterator<ProductRecommond> iterator = productRecommonds.iterator(); iterator.hasNext();) {
			ProductRecommond productRecommond = (ProductRecommond) iterator.next();
			Boolean judge = false;
			for(int i = 0; i < productIds.length; i++){
				if(productIds[i] == productRecommond.getProduct().getId()){
					judge = true;
					break;
				}
			}
			if(!judge){
				productRecommondService.delete(productRecommond.getId());
			}
		}
		int j = 0;
		for (Product product : productService.findList(productIds)) {
			ProductRecommond productRecommond = productRecommondService.findByProductId(productIds[j]);
			if(productRecommond == null){
				productRecommond = new ProductRecommond();
				productRecommond.setOrders(orders[j]);
				productRecommond.setProduct(product);
				productRecommond.setProductCategoryIndex(productCateIndexService.findByName(productCateIndex.getName()));
				productRecommondService.save(productRecommond);
			}else{
				productRecommond.setOrders(orders[j]);
				productRecommond.setProduct(product);
				productRecommond.setProductCategoryIndex(productCateIndexService.findByName(productCateIndex.getName()));
				productRecommondService.update(productRecommond);
			}
			j++;
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long id){
		ProductCateIndex productCateIndex = productCateIndexService.find(id);
		if (productCateIndex == null) {
			return ERROR_MESSAGE;
		}
		productCateIndexService.delete(productCateIndex);
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
				if(productRecommondService.checkProduct(product.getId())){
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
