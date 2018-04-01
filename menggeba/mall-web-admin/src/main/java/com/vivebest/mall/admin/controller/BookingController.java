package com.vivebest.mall.admin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.entity.Booking;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Promotion.Category;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.Returns.Status;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.BookingService;
import com.vivebest.mall.service.OrderService;
import com.vivebest.mall.service.ProductService;
import com.vivebest.mall.service.PromotionProductsService;
import com.vivebest.mall.service.PromotionService;
import com.vivebest.mall.service.ReturnsService;

/**
 * Controller - 团购预约
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("bookingController")
@RequestMapping("/admin/booking")
public class BookingController extends BaseController {
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "bookingServiceImpl")
	private BookingService bookingService;

	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;

	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "promotionProductsServiceImpl")
	private PromotionProductsService promotionProductsService;
	@Resource(name = "returnsServiceImpl")
	private ReturnsService returnsService;

	/**
	 * 添加
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		return "/admin/booking/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Long productIds[], Promotion promotion, PromotionProducts promotionProducts,
			RedirectAttributes redirectAttributes) {
		if (productIds == null || promotion == null || promotionProducts == null) {
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		promotion.setTitle(promotion.getName());
		promotion.setCategory(Category.booking);
		promotionService.save(promotion);
		for (Product product : productService.findList(productIds)) {
			PromotionProducts promotionProductsFinal = new PromotionProducts();
			promotionProductsFinal.setProduct(product);
			promotionProductsFinal.setPromotion(promotion);
			promotionProductsFinal.setGroupBuyingDeposit(promotionProducts.getGroupBuyingDeposit());
			promotionProductsFinal.setGroupBuyingDepositQuantity(promotionProducts.getGroupBuyingDepositQuantity());
			promotionProductsFinal.setGroupBuyingPrice1(promotionProducts.getGroupBuyingPrice1());
			promotionProductsFinal.setGroupBuyingQuantity1(promotionProducts.getGroupBuyingQuantity1());
			promotionProductsFinal.setGroupBuyingPrice2(promotionProducts.getGroupBuyingPrice2());
			promotionProductsFinal.setGroupBuyingQuantity2(promotionProducts.getGroupBuyingQuantity2());
			promotionProductsFinal.setGroupBuyingPrice3(promotionProducts.getGroupBuyingPrice3());
			promotionProductsFinal.setGroupBuyingQuantity3(promotionProducts.getGroupBuyingQuantity3());
			promotionProductsFinal.setGroupBuyingPrice4(promotionProducts.getGroupBuyingPrice4());
			promotionProductsFinal.setGroupBuyingQuantity4(promotionProducts.getGroupBuyingQuantity4());
			promotionProductsFinal.setGroupBuyingPrice5(promotionProducts.getGroupBuyingPrice5());
			promotionProductsFinal.setGroupBuyingQuantity5(promotionProducts.getGroupBuyingQuantity5());
			promotionProductsFinal.setGroupType(promotionProducts.getGroupType());
			if (promotionProducts.getIsStick() == null) {
				promotionProductsFinal.setIsStick(false);
			}
			promotionProductsService.save(promotionProductsFinal);
		}
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("promotion", promotionService.find(id));
		return "/admin/booking/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(Long productIds[], Long[] promotionProductsIds, Promotion promotion,
			PromotionProducts promotionProducts, RedirectAttributes redirectAttributes) {
		if (productIds == null || promotion == null || promotionProducts == null) {
			addFlashMessage(redirectAttributes, Message.error("至少选择一个商品"));
			return "redirect:add.jhtml";
		}
		promotion = promotionService.update(promotion);
		Set<PromotionProducts> list = promotion.getPromotionProducts();
		for (PromotionProducts promotionProducts2 : list) {
			Boolean judge = false;
			for (int i = 0; i < productIds.length; i++) {
				if(productIds[i].compareTo(promotionProducts2.getProduct().getId()) == 0){
					judge = true;
					break;
				}
			}
			/*for (int i = 0; i < promotionProductsIds.length; i++) {
				if (Long.compare(promotionProductsIds[i], promotionProducts2.getId()) == 0) {
					judge = true;
					break;
				}
			}*/
			if (!judge) {
				promotionProductsService.delete(promotionProducts2.getId());
			}
		}
		int i = 0;
		for (Product product : productService.findList(productIds)) {
			PromotionProducts promotionProductsFinal = promotionProductsService.findByProductId(productIds[i]);
			if (promotionProductsFinal == null) {
				promotionProductsFinal = new PromotionProducts();
				promotionProductsFinal.setProduct(product);
				promotionProductsFinal.setPromotion(promotion);
				promotionProductsFinal.setGroupBuyingDeposit(promotionProducts.getGroupBuyingDeposit());
				promotionProductsFinal.setGroupBuyingDepositQuantity(promotionProducts.getGroupBuyingDepositQuantity());
				promotionProductsFinal.setGroupBuyingPrice1(promotionProducts.getGroupBuyingPrice1());
				promotionProductsFinal.setGroupBuyingQuantity1(promotionProducts.getGroupBuyingQuantity1());
				promotionProductsFinal.setGroupBuyingPrice2(promotionProducts.getGroupBuyingPrice2());
				promotionProductsFinal.setGroupBuyingQuantity2(promotionProducts.getGroupBuyingQuantity2());
				promotionProductsFinal.setGroupBuyingPrice3(promotionProducts.getGroupBuyingPrice3());
				promotionProductsFinal.setGroupBuyingQuantity3(promotionProducts.getGroupBuyingQuantity3());
				promotionProductsFinal.setGroupBuyingPrice4(promotionProducts.getGroupBuyingPrice4());
				promotionProductsFinal.setGroupBuyingQuantity4(promotionProducts.getGroupBuyingQuantity4());
				promotionProductsFinal.setGroupBuyingPrice5(promotionProducts.getGroupBuyingPrice5());
				promotionProductsFinal.setGroupBuyingQuantity5(promotionProducts.getGroupBuyingQuantity5());
				promotionProductsFinal.setGroupType(promotionProducts.getGroupType());
				if (promotionProducts.getIsStick() == null) {
					promotionProductsFinal.setIsStick(false);
				}
				promotionProductsService.save(promotionProductsFinal);
			} else {
				promotionProductsFinal.setGroupBuyingDeposit(promotionProducts.getGroupBuyingDeposit());
				promotionProductsFinal.setGroupBuyingDepositQuantity(promotionProducts.getGroupBuyingDepositQuantity());
				promotionProductsFinal.setGroupBuyingPrice1(promotionProducts.getGroupBuyingPrice1());
				promotionProductsFinal.setGroupBuyingQuantity1(promotionProducts.getGroupBuyingQuantity1());
				promotionProductsFinal.setGroupBuyingPrice2(promotionProducts.getGroupBuyingPrice2());
				promotionProductsFinal.setGroupBuyingQuantity2(promotionProducts.getGroupBuyingQuantity2());
				promotionProductsFinal.setGroupBuyingPrice3(promotionProducts.getGroupBuyingPrice3());
				promotionProductsFinal.setGroupBuyingQuantity3(promotionProducts.getGroupBuyingQuantity3());
				promotionProductsFinal.setGroupBuyingPrice4(promotionProducts.getGroupBuyingPrice4());
				promotionProductsFinal.setGroupBuyingQuantity4(promotionProducts.getGroupBuyingQuantity4());
				promotionProductsFinal.setGroupBuyingPrice5(promotionProducts.getGroupBuyingPrice5());
				promotionProductsFinal.setGroupBuyingQuantity5(promotionProducts.getGroupBuyingQuantity5());
				promotionProductsFinal.setGroupType(promotionProducts.getGroupType());
				promotionProductsService.update(promotionProductsFinal);
			}
			i++;
		}
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Date beginDate, Date endDate, Boolean status, Pageable pageable, ModelMap model) {
		Filter flp = new Filter("category", Operator.eq, Category.booking);
		List<Filter> flpList = new ArrayList<Filter>();
		flpList.add(flp);
		pageable.setFilters(flpList);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("page", promotionService.findPage(beginDate, endDate, status, pageable));
		return "/admin/booking/list";
	}

	/**
	 * 搜索
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(Long promotionId, Date beginDate, Date endDate, Pageable pageable, ModelMap model) {
		model.addAttribute("promotionId", promotionId);
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);
		Page<Booking> page = bookingService.findPage(promotionId, null, null, beginDate, endDate, pageable);
		model.addAttribute("page", page);
		List<Booking> bookings = page.getContent();
		//预约人数
		Map<Long, Long> counts = new HashMap<Long, Long>();
		Map<Long,Double>promotionPrices=new HashMap<Long, Double>();
		for (Booking booking : bookings) {
			Filter filter = new Filter("promotionProducts", Operator.eq, booking.getPromotionProducts());
			Filter filter1 = new Filter("product", Operator.eq, booking.getProduct());
			Filter filter2 = new Filter("status", Operator.eq,com.vivebest.mall.entity.Booking.Status.nonPayment);
			Filter filter3 = new Filter("status", Operator.eq, com.vivebest.mall.entity.Booking.Status.nondirectPurchase);
			long count = bookingService.count(filter, filter1)-bookingService.count(filter, filter1,filter2)-bookingService.count(filter, filter1,filter3);
			counts.put(booking.getId(), count);
			BigDecimal price=new BigDecimal(0);
			if (count<=booking.getPromotionProducts().getGroupBuyingQuantity1()) {
				price=booking.getPromotionProducts().getGroupBuyingPrice1();
			}else {
				if (count<=booking.getPromotionProducts().getGroupBuyingQuantity2()) {
					price=booking.getPromotionProducts().getGroupBuyingPrice2();
				}else {
					if (count<=booking.getPromotionProducts().getGroupBuyingQuantity3()) {
						price=booking.getPromotionProducts().getGroupBuyingPrice3();
					}else {
						if (count<=booking.getPromotionProducts().getGroupBuyingQuantity4()) {
							price=booking.getPromotionProducts().getGroupBuyingPrice4();
						}else {
							price=booking.getPromotionProducts().getGroupBuyingPrice5();
						}
					}
				}
			}
			promotionPrices.put(booking.getId(), price.doubleValue());
		}
		model.addAttribute("counts", counts);
		model.addAttribute("promotionPrices", promotionPrices);
		return "/admin/booking/search";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		promotionService.delete(ids);
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/deleteCustom", method = RequestMethod.POST)
	public @ResponseBody Message deleteCustom(Long[] ids) {
		bookingService.delete(ids);
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
				if (promotionProductsService.checkProduct(product.getId())) {
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

	/**
	 * 申请退款
	 * 
	 * @param orderItemId
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/bookingReturn", method = RequestMethod.GET)
	public String afterApply(Long id,Double price, ModelMap model, RedirectAttributes redirectAttributes) {
		Booking booking = bookingService.find(id);
		if (booking == null) {
			return ERROR_VIEW;
		}
		Order order = booking.getOrders();
		OrderItem orderItem = null;
		if (order != null && order.getOrderItems().size() > 0) {
			orderItem = order.getOrderItems().get(0);
		} else {
			return ERROR_VIEW;
		}
		model.addAttribute("booking", booking);
		model.addAttribute("price", price);
		model.addAttribute("orderItem", orderItem);
		return "/admin/booking/bookingReturn";
	}

	/**
	 * 售后服务
	 */
	@RequestMapping(value = "/returnService", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> returnService(Long id, ModelMap model) {
		Map<String, Object> data = new HashMap<String, Object>();
		Booking booking = bookingService.find(id);
		Order order = booking.getOrders();
		OrderItem orderItem = null;
		Returns returns = null;
		Refunds refunds = null;
		if (order != null && order.getOrderItems().size() > 0) {
			orderItem = order.getOrderItems().get(0);
			List<Returns> returns2 = returnsService.findByOrderItem(orderItem.getId());
			List<Returns> returnss=new ArrayList<Returns>();
			if (returns2 != null) {
				returnss.addAll(returns2);
				for (Returns ret : returnss) {
					if (ReturnType.bookingRefund.equals(ret.getReturnType())) {
						returns = ret;
						break;
					}
				}
			}
			if (returns != null) {
				if (returns2.size() > 1) {
					data.put("message", Message.success("客户已申请退款，退款申请还要继续吗？"));
				}
				model.addAttribute("returns", returns);
				com.vivebest.mall.entity.Refunds.Status refundsStatus = null;
				if (returns.getRefunds().size() > 0) {
					refunds = returns.getRefunds().get(0);
					refundsStatus = refunds.getStatus();
				}
				if (refundsStatus == com.vivebest.mall.entity.Refunds.Status.refunding) {
					data.put("message", Message.warn("admin.refunds.refunding"));
				} else if (refundsStatus == com.vivebest.mall.entity.Refunds.Status.refundSuccess) {
					data.put("message", Message.warn("admin.refunds.success"));
				} else if (refundsStatus == com.vivebest.mall.entity.Refunds.Status.refundFail) {
					data.put("message", Message.warn("admin.refunds.faild"));
				}
				else if (returns.getStatus() == com.vivebest.mall.entity.Returns.Status.approved) {
					data.put("message", Message.warn("admin.returns.hasApprove.wirteInfo"));
				} else if (returns.getStatus() == com.vivebest.mall.entity.Returns.Status.unapprove) {
					data.put("returns", returns);
					data.put("message", Message.success("admin.returns.hasUnapprove"));
				} else if (returns.getStatus() == com.vivebest.mall.entity.Returns.Status.waitingApprove) {
					data.put("message", Message.warn("admin.returns.hasAudited"));
				} else if (returns.getStatus() == Status.delivered) {
					data.put("message", Message.warn("admin.returns.delivered"));
				} else if (returns.getStatus() == Status.received) {
					data.put("message", Message.warn("admin.returns.received"));
				}
			} else {
				if (returns2!=null) {
					data.put("message", Message.success("客户已申请退款，退款申请还要继续吗？"));
				} else {
					data.put("message", Message.success("admin.returns.toRefunds"));
				}
			}
		} else {
			data.put("message", Message.warn("admin.returns.orderItem.notExist"));
		}
		return data;
	}
	/**
	 * 生成退货单
	 */
	@RequestMapping(value = "/confirm_return", method = RequestMethod.POST)
	public String createReturns(Long id, ModelMap model,BigDecimal returnPrice, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		String returnCause = request.getParameter("returnCause");

		Order order = orderService.find(id);
	//	Admin admin = adminService.getCurrent();

		OrderItem orderItem=null;
		if (order!=null) {
			orderItem=order.getOrderItems().get(0);
		}
		List<Returns> returns = returnsService.findByOrderItem(id);
		List<Returns> returnss=new ArrayList<Returns>();
		Returns return2=null;
		if (returns != null) {
			returnss.addAll(returns);
			for (Returns ret : returnss) {
				if (ReturnType.bookingRefund.equals(ret.getReturnType())) {
					return2 = ret;
					break;
				}
			}
		}

		if (return2 == null) {
			// 初次申请
			return2 = returnsService.build(orderItem,returnPrice, ReturnType.bookingRefund, order.getMember(), returnCause, null, null);
			if (return2 == null) {
				return ERROR_VIEW;
			}
			addFlashMessage(redirectAttributes, Message.success("admin.message.success"));
			return "redirect:search.jhtml";
		} else if (Status.unapprove.equals(return2.getStatus())) {
			// 审核拒绝时可以再次申请
			returnsService.applyAgain(return2,returnPrice,ReturnType.bookingRefund, orderItem, order.getMember(), returnCause, null, null);
			addFlashMessage(redirectAttributes, Message.success("admin.message.success"));
			return "redirect:search.jhtml";
		} else {
			// 多次提交
			addFlashMessage(redirectAttributes, Message.error("admin.memeber.returns.error"));
			return "redirect:search.jhtml";
		}
	}
	/**
	 * 发送短信
	 */
	@RequestMapping(value = "/sendInfoCustom", method = RequestMethod.POST)
	public @ResponseBody
	Message send(Long[] ids, HttpServletRequest request) {
		int count = bookingService.send(ids, request);
		return Message.success("admin.bookingNotify.sentSuccess", count);
	}
}
