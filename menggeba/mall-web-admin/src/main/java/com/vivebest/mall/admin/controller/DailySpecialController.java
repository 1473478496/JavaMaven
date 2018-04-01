package com.vivebest.mall.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Filter.Operator;
import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.CartItem;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Promotion.Category;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.service.ProductService;
import com.vivebest.mall.service.PromotionProductsService;
import com.vivebest.mall.service.PromotionService;

/**
 * Controller - DailySpecial
 * 天天特价
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminDailySpecialController")
@RequestMapping("/admin/daily_special")
public class DailySpecialController extends BaseController {
	
	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductService;
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	@RequestMapping(value = "/check_sn", method = RequestMethod.GET)
	public @ResponseBody boolean checkSn(String previousSn, String sn,RedirectAttributes redirectAttributes) {
		return false;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Product product, Pageable pageable, ModelMap model) {
		/*try {
			model.addAttribute("page",promotionProductService.findPage(product, pageable));
		} catch (Exception e) {
			System.out.println("异常》》》》："+e);
		}*/
		Filter flp = new Filter("category", Operator.eq, Category.dailySpecial);
		List<Filter> flpList = new ArrayList<Filter>();
		flpList.add(flp);
		List<Promotion> promotionList = promotionService.findList(null, flpList, null);

		List<Filter> filtersAll = new ArrayList<Filter>();
		if (promotionList.size() > 0) {
			Filter promotion = new Filter("promotion", Operator.in, promotionList);
			filtersAll.add(promotion);
		}
		pageable.setFilters(filtersAll);
		model.addAttribute("page", promotionProductService.findPage(pageable));
		return "/admin/daily_special/list";
	}
	
	/**
	 * 新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model){
		model.addAttribute("promotions", promotionService.findByTimePoint(Promotion.Category.dailySpecial,null,null,null));
		return "/admin/daily_special/add";
	}
	
	/**
	 * 保存
	 * @param special
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PromotionProducts special,Long[] productIds, Long promotionId, RedirectAttributes redirectAttributes){
		if (!isValid(special)) {
			return ERROR_VIEW;
		}
		if(productIds == null){
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		for (Product product : productService.findList(productIds)) {
			PromotionProducts promotionProducts = new PromotionProducts();
			promotionProducts.setIsStick(special.getIsStick());
			promotionProducts.setLastSaleTime(special.getLastSaleTime());
			promotionProducts.setOrders(special.getOrders());
			promotionProducts.setPromotionPrice(special.getPromotionPrice());
			promotionProducts.setPromotionPricePoint(special.getPromotionPricePoint());
			promotionProducts.setPromotionQuantity(special.getPromotionQuantity());
			promotionProducts.setSaleQuantity(special.getSaleQuantity());
			promotionProducts.setStickImage(special.getStickImage());
			promotionProducts.setProduct(product);
			promotionProducts.setPromotion(promotionService.find(promotionId));
			if(promotionProducts.getIsStick()==null){
				promotionProducts.setIsStick(false);
			}
			promotionProductService.save(promotionProducts);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("promotions", promotionService.findByTimePoint(Promotion.Category.dailySpecial,null,null,null));
		model.addAttribute("special", promotionProductService.find(id));
		return "/admin/daily_special/edit";
	}
	
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(PromotionProducts special, Long promotionId, RedirectAttributes redirectAttributes) {
		if (!isValid(special)) {
			return ERROR_VIEW;
		}
		if(special.getIsStick()==null){
			special.setIsStick(false);
		}
		special.setPromotion(promotionService.find(promotionId));
		promotionProductService.update(special, "product");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		List<PromotionProducts> promotionProducts = promotionProductService.findList(ids);
		if(promotionProducts.size() == 0){
			return ERROR_MESSAGE;
		}
		for (PromotionProducts promotionProducts2 : promotionProducts) {
			Set<OrderItem> orderItems = promotionProducts2.getOrderItems();
			if (orderItems != null && !orderItems.isEmpty()) {
				return Message.error("存在下级订单项，无法删除！");
			}
			Set<CartItem> cartItems = promotionProducts2.getCartItems();
			if (cartItems != null && !cartItems.isEmpty()) {
				return Message.error("存在下级购物车项，无法删除！");
			}
		}
		promotionProductService.delete(ids);
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
				if(promotionProductService.checkProduct(product.getId())){
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
