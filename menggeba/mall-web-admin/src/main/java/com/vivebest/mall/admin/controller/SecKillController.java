package com.vivebest.mall.admin.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
 * Controller - secKill 整点秒杀
 * 
 * @author chen.liang
 */
@Controller("adminSecKillController")
@RequestMapping("/admin/secKill")
public class SecKillController extends BaseController {
	private static Logger logger = Logger.getLogger(SecKillController.class);
	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;

	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 列表
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Pageable pageable, ModelMap model, String timePoint, Product product)
			throws UnsupportedEncodingException {
		Filter flp = new Filter("category", Operator.eq, Category.secKill);
		List<Filter> flpList = new ArrayList<Filter>();
		flpList.add(flp);
		List<Promotion> promotionList = promotionService.findList(null, flpList, null);

		List<Filter> filtersAll = new ArrayList<Filter>();
		if (promotionList.size() > 0) {
			Filter promotion = new Filter("promotion", Operator.in, promotionList);
			filtersAll.add(promotion);
		}
		pageable.setFilters(filtersAll);

		if (timePoint != null) {
			pageable.setSearchProperty("promotion_timePoint");
			pageable.setSearchValue(timePoint);
		}
		model.addAttribute("page", promotionProductService.findPage(pageable));
		return "/admin/secKill/list";
	}

	/**
	 * 新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		List<Promotion> promotions = promotionService.findByTimePoint(Promotion.Category.secKill, null, null,
				new Date());
		List<String> timePoint = new ArrayList<String>();
		// for (Promotion p : promotion) {
		// timePoint.add(p.getTimePoint());
		// }
		// HashSet<String> set = new HashSet<String>(timePoint);
		// timePoint.clear();
		// timePoint.addAll(set);
		// try {
		// model.addAttribute("timePoint", timePoint);
		// } catch (Exception e) {
		// System.out.println("异常》》》》：" + e);
		// }
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Promotion p : promotions) {
			timePoint.add(formatter.format(p.getBeginDate()));
		}
		model.addAttribute("timePoint", timePoint);
		return "/admin/secKill/add";
	}

	/**
	 * 保存
	 * 
	 * @param special
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(PromotionProducts special, Long[] productIds, String beginTime,
			RedirectAttributes redirectAttributes) {
		Date beginDay = null;
		if (!isValid(special)) {
			return ERROR_VIEW;
		}
		if (productIds == null) {
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		if (beginTime == null || "".equals(beginTime)) {
			addFlashMessage(redirectAttributes, Message.error("请选择秒杀时间"));
			return "redirect:add.jhtml";
		} else {
			try {
				beginDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beginTime);
			} catch (ParseException e) {
				logger.info("日期格式不对" + e);
			}
		}
		for (Product product : productService.findList(productIds)) {
			//
			for (Promotion pro : promotionService.findByTimePoint(Promotion.Category.secKill, beginDay, null,
					new Date())) {
				PromotionProducts promotionProducts = new PromotionProducts();
				Promotion promotion = new Promotion();
				promotion = pro;
				promotionProducts.setPromotion(promotion);
				promotionProducts.setIsStick(special.getIsStick());
				promotionProducts.setLastSaleTime(special.getLastSaleTime());
				promotionProducts.setOrders(special.getOrders());
				promotionProducts.setPromotionPrice(special.getPromotionPrice());
				promotionProducts.setPromotionPricePoint(special.getPromotionPricePoint());
				promotionProducts.setPromotionQuantity(special.getPromotionQuantity());
				promotionProducts.setSaleQuantity(special.getSaleQuantity());
				promotionProducts.setStickImage(special.getStickImage());
				promotionProducts.setProduct(product);
				if (promotionProducts.getIsStick() == null) {
					promotionProducts.setIsStick(false);
				}
				promotionProductService.save(promotionProducts);
			}
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	@RequestMapping(value = "/addPromotion", method = RequestMethod.POST)
	public String addPromotion(Promotion promotion, String includeDay, RedirectAttributes redirectAttributes) {

		int includeNum = 0;
		if (includeDay != null && Integer.parseInt(includeDay) > 0) {
			includeNum = Integer.parseInt(includeDay);
		} else {
			return ERROR_VIEW;
		}
		List<Promotion> promotions = promotionService.findByTimePoint(Category.secKill, null, promotion.getTimePoint(), null);
		List<Date> beginDates = new ArrayList<Date>();
		for (Promotion promo : promotions) {
			beginDates.add(promo.getBeginDate());
		}
		for (int i = 0; i < includeNum; i++) {
			Calendar cd = Calendar.getInstance();
			cd.setTime(promotion.getBeginDate());
			cd.add(Calendar.DATE, i);
			Date beginDay = cd.getTime();
			if (beginDates != null && beginDates.size() > 0 && beginDates.contains(beginDay)) {
				continue;
			}
			Promotion pro = new Promotion();
			pro.setBeginDate(beginDay);
			Calendar cd2 = Calendar.getInstance();
			cd2.setTime(promotion.getBeginDate());
			cd2.add(Calendar.DATE, i + 1);
			Date endDay = cd2.getTime();
			// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String nowDate = sdf.format(endDay);
			nowDate = nowDate + " 00:00:00";
			// 通过日历获取下一天日期
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(nowDate));
			} catch (ParseException e) {
				logger.info(e);
			}
			pro.setEndDate(cal.getTime());
			pro.setCategory(Promotion.Category.secKill);
			pro.setName("促销");
			pro.setTitle("整点秒杀");
			pro.setStatus(true);
			pro.setTimePoint(promotion.getTimePoint());
			promotionService.save(pro);

		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:add.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("special", promotionProductService.find(id));
		List<Promotion> promotions = promotionService.findByTimePoint(Promotion.Category.secKill, null, null,
				new Date());
		model.addAttribute("promotions", promotions);
		return "/admin/secKill/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(PromotionProducts special,Long promotionId, RedirectAttributes redirectAttributes) {
		if (!isValid(special)) {
			return ERROR_VIEW;
		}
		if (special.getIsStick() == null) {
			special.setIsStick(false);
		}
		Promotion promotion=null;
		if (promotionId!=null) {
			promotion=promotionService.find(promotionId);
		}
		special.setPromotion(promotion);
		promotionProductService.update(special, "product");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
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
	public @ResponseBody List<Map<String, Object>> productSelect(String q) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(q)) {
			List<Product> products = productService.search(q, false, 20);
			for (Product product : products) {
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
