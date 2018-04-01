package com.vivebest.mall.web.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.service.BrandService;
import com.vivebest.mall.service.ProductCategoryService;
import com.vivebest.mall.service.ProductService;

 

/**
 * 
 * 商品管理
 * @author Terry
 *
 */
@Controller("merchantProductController")
@RequestMapping("/merchant/product")
public class MerchantProductController extends BaseController{

	
	@Resource(name="productServiceImpl")
	private ProductService productService;
	
	@Resource(name="brandServiceImpl")
	private BrandService brandService;
	
	@Resource(name="productCategoryServiceImpl")
	private ProductCategoryService productCategoryService;
	/**
	 * 商品列表页
	 * 
	 */
	@RequestMapping(value="/list" ,method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model,HttpServletRequest request){
		Long tradeShopId= null;
		//初始化
		ProductCategory productCategory=null;
		Brand brand =null;
		Boolean isMarketable=null;
		//获取页面传过来的搜索参数
		String sn = request.getParameter("productId");//商品编号
		//商品分类
		String productCategoryId=request.getParameter("productCategory");
		if(productCategoryId!=null&&!productCategoryId.equals("")){
		  productCategory = productCategoryService.find(Long.parseLong(productCategoryId));
		}
		//商品品牌
		String brandId=request.getParameter("brand");
		if(brandId!=null&&!brandId.equals("")){
		  brand = brandService.find(Long.parseLong(brandId));
		}
		//上下架
		String isMarket=request.getParameter("isMarketable");
		if(isMarket!=null&&!isMarket.equals("")){
		  isMarketable = Boolean.parseBoolean(isMarket);
		}
		//搜索参数回显
		model.addAttribute("productId", sn);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isMarketable", isMarketable);
		model.addAttribute("productCategoryId", productCategoryId);
		model.addAttribute("page", productService.findPageByTradeId(tradeShopId, pageable,sn,productCategory,brand,isMarketable,null));
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return "/product/list";
	}
	/**
	 * 商品编辑页
	 * 
	 */
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model){
		model.addAttribute("brands", brandService.findAll());
		model.addAttribute("product", productService.find(id));
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		return"/product/edit";
	}
}
