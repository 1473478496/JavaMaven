/*
o * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.admin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vivebest.mall.core.common.Message;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.common.SmsBusType;
import com.vivebest.mall.core.controller.admin.BaseController;
import com.vivebest.mall.core.util.ExportUtil;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.core.util.SpringUtils;
import com.vivebest.mall.entity.Admin;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.DeliveryCorp;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Payment;
import com.vivebest.mall.entity.Payment.Status;
import com.vivebest.mall.entity.Payment.Type;
import com.vivebest.mall.entity.PaymentMethod;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Refunds;
import com.vivebest.mall.entity.Returns;
import com.vivebest.mall.entity.Returns.ReturnType;
import com.vivebest.mall.entity.ReturnsItem;
import com.vivebest.mall.entity.Shipping;
import com.vivebest.mall.entity.ShippingItem;
import com.vivebest.mall.entity.ShippingMethod;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.entity.TieInSale;
import com.vivebest.mall.merchant.entity.TradeShop;
import com.vivebest.mall.merchant.service.TradeShopService;
import com.vivebest.mall.service.AdminService;
import com.vivebest.mall.service.AreaService;
import com.vivebest.mall.service.DeliveryCorpService;
import com.vivebest.mall.service.OrderItemService;
import com.vivebest.mall.service.OrderService;
import com.vivebest.mall.service.PaymentMethodService;
import com.vivebest.mall.service.ProductService;
import com.vivebest.mall.service.ReturnsService;
import com.vivebest.mall.service.ShippingMethodService;
import com.vivebest.mall.service.ShippingService;
import com.vivebest.mall.service.SmsService;
import com.vivebest.mall.service.SnService;
import com.vivebest.mall.service.TieInSaleService;

/**
 * Controller - 订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Controller("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController extends BaseController {

	private static Logger logger = Logger.getLogger(OrderController.class);

	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	@Resource(name = "snServiceImpl")
	private SnService snService;
	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;

	@Resource(name = "returnsServiceImpl")
	private ReturnsService returnsService;

	@Resource(name = "smsServiceImpl")
	private SmsService smsService;
	@Resource(name = "tieInSaleServiceImpl")
	private TieInSaleService tieInSaleService;
	@Resource(name="tradeShopServiceImpl")
	private TradeShopService tradeShopService;

	/**
	 * 检查锁定
	 */
	@RequestMapping(value = "/check_lock", method = RequestMethod.POST)
	public @ResponseBody Message checkLock(Long id) {
		Order order = orderService.find(id);
		if (order == null) {
			return Message.warn("admin.common.invalid");
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			if (order.getOperator() != null) {
				return Message.warn("admin.order.adminLocked", order.getOperator().getUsername());
			} else {
				return Message.warn("admin.order.memberLocked");
			}
		} else {
			order.setLockExpire(DateUtils.addSeconds(order.getLockExpire(), 5));
			logger.info("[lock-->>getLockExpire()]:" + order.getLockExpire());
			order.setOperator(admin);
			orderService.update(order);
			return SUCCESS_MESSAGE;
		}
	}

	/**
	 * 查看
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Long id, ModelMap model) {
		// 判断订单是否存在退款的商品正在审核
		boolean flag = true;
		model.addAttribute("methods", Payment.Method.values());
		model.addAttribute("refundsMethods", Refunds.Method.values());
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("deliveryCorps", deliveryCorpService.findAll());
		Order order = orderService.find(id);
		TradeShop tradeShop = tradeShopService.find(order.getTradeShopId());
		List<OrderItem> orderItems = order.getOrderItems();
		Iterator<OrderItem> it = orderItems.iterator();
		Map<Long, List<TieInSale>> tieInSaleMap = new HashMap<Long, List<TieInSale>>();
		while (it.hasNext()) {
			OrderItem orderItem = it.next();

			if (orderItem.getProduct() != null && orderItem.getProduct().getSuit()) {
				tieInSaleMap.put(orderItem.getId(),
						tieInSaleService.findProductByPSuitId(orderItem.getProduct().getId()));
			}
			List<Returns> returns2 = returnsService.findByOrderItem(orderItem.getId());
			List<Returns> returnss=new ArrayList<Returns>();
			Returns returns=null;
			if (returns2 != null) {
				returnss.addAll(returns2);
				for (Returns ret : returnss) {
					if (ReturnType.bookingRefund.equals(ret.getReturnType())) {
						returns2.remove(ret);
					} else {
						returns = ret;
						break;
					}
				}
			}
			if (returns != null && returns.getStatus() != com.vivebest.mall.entity.Returns.Status.unapprove) {
				if (returns.getStatus() != com.vivebest.mall.entity.Returns.Status.waitingApprove) {
					it.remove();
				} else {
					flag = false;
				}
			}
		}
		model.addAttribute("tieInSaleMap", tieInSaleMap);
        model.addAttribute("tradeShop",tradeShop);
		model.addAttribute("flag", flag);
		model.addAttribute("orderItems", orderItems);
		model.addAttribute("order", order);
		return "/admin/order/view";
	}

	/**
	 * 确认
	 */
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String confirm(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && order.getOrderStatus() == OrderStatus.unpayment && !order.isLocked(admin)) {
			orderService.confirm(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 完成
	 */
	@RequestMapping(value = "/complete", method = RequestMethod.POST)
	public String complete(Long id, ModelMap model, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		// 判断订单是否存在退款的商品正在审核
		boolean flag = true;
		if (order != null && order.getOrderStatus() == OrderStatus.shipped && !order.isLocked(admin)) {
			orderService.complete(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else if (order != null && order.getOrderStatus() == OrderStatus.completed && !order.isLocked(admin)) {
			addFlashMessage(redirectAttributes, Message.warn("order.submit.tomany"));
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			List<Returns> returns2 = returnsService.findByOrderItem(orderItem.getId());
			List<Returns> returnss=new ArrayList<Returns>();
			Returns returns=null;
			if (returns2 != null) {
				returnss.addAll(returns2);
				for (Returns ret : returnss) {
					if (ReturnType.bookingRefund.equals(ret.getReturnType())) {
						returns2.remove(ret);
					} else {
						returns = ret;
						break;
					}
				}
			}
			if (returns != null && returns.getStatus() != com.vivebest.mall.entity.Returns.Status.unapprove) {
				if (returns.getStatus() != com.vivebest.mall.entity.Returns.Status.waitingApprove) {
					orderItems.remove(orderItem);
				} else {
					flag = false;
				}
			}
		}
		model.addAttribute("flag", flag);
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 取消
	 */
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(Long id, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(id);
		Admin admin = adminService.getCurrent();
		if (order != null && order.getOrderStatus() == OrderStatus.unpayment && !order.isLocked(admin)) {
			orderService.cancel(order, admin);
			addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		} else {
			addFlashMessage(redirectAttributes, Message.warn("admin.common.invalid"));
		}
		return "redirect:view.jhtml?id=" + id;
	}

	/**
	 * 支付
	 */
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String payment(Long orderId, Long paymentMethodId, Payment payment, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		payment.setOrder(order);
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		payment.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
		if (!isValid(payment)) {
			return ERROR_VIEW;
		}
		if (order.getOrderStatus() != OrderStatus.paymented) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != PaymentStatus.unpaid
				&& order.getPaymentStatus() != PaymentStatus.partialPayment) {
			return ERROR_VIEW;
		}
		if (payment.getAmount().compareTo(new BigDecimal(0)) <= 0
				|| payment.getAmount().compareTo(order.getAmountPayable()) > 0) {
			return ERROR_VIEW;
		}
		Member member = order.getMember();
		if (payment.getMethod() == Payment.Method.deposit && payment.getAmount().compareTo(member.getBalance()) > 0) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		payment.setSn(snService.generate(Sn.Type.payment));
		payment.setType(Type.payment);
		payment.setStatus(Status.success);
		payment.setFee(new BigDecimal(0));
		payment.setOperator(admin.getUsername());
		payment.setPaymentDate(new Date());
		payment.setPaymentPluginId(null);
		payment.setExpire(null);
		payment.setDeposit(null);
		payment.setMember(null);
		orderService.payment(order, payment, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 退款
	 */
	@RequestMapping(value = "/refunds", method = RequestMethod.POST)
	public String refunds(Long orderId, Long paymentMethodId, Refunds refunds, RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		refunds.setOrder(order);
		PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
		refunds.setPaymentMethod(paymentMethod != null ? paymentMethod.getName() : null);
		if (!isValid(refunds)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != OrderStatus.paymented) {
			return ERROR_VIEW;
		}
		if (order.getPaymentStatus() != PaymentStatus.paid && order.getPaymentStatus() != PaymentStatus.partialPayment
				&& order.getPaymentStatus() != PaymentStatus.partialRefunds) {
			return ERROR_VIEW;
		}
		if (refunds.getAmount().compareTo(new BigDecimal(0)) <= 0
				|| refunds.getAmount().compareTo(order.getAmountPaid()) > 0) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		refunds.setSn(snService.generate(Sn.Type.refunds));
		refunds.setOperator(admin.getUsername());
		orderService.refunds(order, refunds, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 发货
	 */
	@RequestMapping(value = "/shipping", method = RequestMethod.POST)
	public String shipping(Long orderId, Long shippingMethodId, Long deliveryCorpId, Long areaId, Shipping shipping,
			RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		if (order == null) {
			return ERROR_VIEW;
		}

		for (Iterator<ShippingItem> iterator = shipping.getShippingItems().iterator(); iterator.hasNext();) {
			ShippingItem shippingItem = iterator.next();
			if (shippingItem == null || StringUtils.isEmpty(shippingItem.getSn()) || shippingItem.getQuantity() == null
					|| shippingItem.getQuantity() <= 0) {
				iterator.remove();
				continue;
			}
			OrderItem orderItem = order.getOrderItem(shippingItem.getSn());
			if (orderItem == null || shippingItem.getQuantity() > orderItem.getQuantity()
					- orderItem.getShippedQuantity() - orderItem.getReturnQuantity()) {
				return ERROR_VIEW;
			}
			if (orderItem.getProduct() != null && orderItem.getProduct().getStock() != null
					&& shippingItem.getQuantity() > orderItem.getProduct().getStock()) {
				return ERROR_VIEW;
			}
			shippingItem.setName(orderItem.getFullName());
			shippingItem.setShipping(shipping);
		}
		shipping.setOrder(order);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		shipping.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
		DeliveryCorp deliveryCorp = deliveryCorpService.find(deliveryCorpId);
		shipping.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
		shipping.setDeliveryCorpUrl(deliveryCorp != null ? deliveryCorp.getUrl() : null);
		shipping.setDeliveryCorpCode(deliveryCorp != null ? deliveryCorp.getCode() : null);
		Area area = areaService.find(areaId);
		shipping.setArea(area != null ? area.getFullName() : null);
		if (!isValid(shipping)) {
			return ERROR_VIEW;
		}
		if (order.getOrderStatus() != OrderStatus.paymented) {
			return ERROR_VIEW;
		}
		if (order.getShippingStatus() != ShippingStatus.unshipped
				&& order.getShippingStatus() != ShippingStatus.partialShipment) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		shipping.setSn(snService.generate(Sn.Type.shipping));
		shipping.setOperator(admin.getUsername());
		orderService.shipping(order, shipping, admin);
		// 再发送短信通知用户
		// sendShippingSms(order.getMember().getMobile(),
		// shipping.getTrackingNo());
		sendShippingSms(order.getMember().getMobile(), shipping.getTrackingNo(), shipping.getDeliveryCorp());
		// addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		addFlashMessage(redirectAttributes, Message.success("admin.message.success.sms"));
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 退货
	 */
	@RequestMapping(value = "/returns", method = RequestMethod.POST)
	public String returns(Long orderId, Long shippingMethodId, Long deliveryCorpId, Long areaId, Returns returns,
			RedirectAttributes redirectAttributes) {
		Order order = orderService.find(orderId);
		if (order == null) {
			return ERROR_VIEW;
		}
		for (Iterator<ReturnsItem> iterator = returns.getReturnsItems().iterator(); iterator.hasNext();) {
			ReturnsItem returnsItem = iterator.next();
			if (returnsItem == null || StringUtils.isEmpty(returnsItem.getSn()) || returnsItem.getQuantity() == null
					|| returnsItem.getQuantity() <= 0) {
				iterator.remove();
				continue;
			}
			OrderItem orderItem = order.getOrderItem(returnsItem.getSn());
			if (orderItem == null
					|| returnsItem.getQuantity() > orderItem.getShippedQuantity() - orderItem.getReturnQuantity()) {
				return ERROR_VIEW;
			}
			returnsItem.setName(orderItem.getFullName());
			returnsItem.setReturns(returns);
		}
		returns.setOrder(order);
		ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
		returns.setShippingMethod(shippingMethod != null ? shippingMethod.getName() : null);
		DeliveryCorp deliveryCorp = deliveryCorpService.find(deliveryCorpId);
		returns.setDeliveryCorp(deliveryCorp != null ? deliveryCorp.getName() : null);
		Area area = areaService.find(areaId);
		returns.setArea(area != null ? area.getFullName() : null);
		if (!isValid(returns)) {
			return ERROR_VIEW;
		}
		if (order.isExpired() || order.getOrderStatus() != OrderStatus.paymented) {
			return ERROR_VIEW;
		}
		if (order.getShippingStatus() != ShippingStatus.shipped
				&& order.getShippingStatus() != ShippingStatus.partialShipment
				&& order.getShippingStatus() != ShippingStatus.partialReturns) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (order.isLocked(admin)) {
			return ERROR_VIEW;
		}
		returns.setSn(snService.generate(Sn.Type.returns));
		returns.setOperator(admin.getUsername());
		orderService.returns(order, returns, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:view.jhtml?id=" + orderId;
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("paymentMethods", paymentMethodService.findAll());
		model.addAttribute("shippingMethods", shippingMethodService.findAll());
		model.addAttribute("order", orderService.find(id));
		return "/admin/order/edit";
	}

	/**
	 * 订单项添加
	 */
	@RequestMapping(value = "/order_item_add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> orderItemAdd(String productSn) {
		Map<String, Object> data = new HashMap<String, Object>();
		Product product = productService.findBySn(productSn);
		if (product == null) {
			data.put("message", Message.warn("admin.order.productNotExist"));
			return data;
		}
		if (!product.getIsMarketable()) {
			data.put("message", Message.warn("admin.order.productNotMarketable"));
			return data;
		}
		if (product.getIsOutOfStock()) {
			data.put("message", Message.warn("admin.order.productOutOfStock"));
			return data;
		}
		data.put("sn", product.getSn());
		data.put("fullName", product.getFullName());
		data.put("price", product.getPrice());
		data.put("weight", product.getWeight());
		data.put("isGift", product.getIsGift());
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 计算
	 */
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> calculate(Order order, Long areaId, Long paymentMethodId,
			Long shippingMethodId) {
		Map<String, Object> data = new HashMap<String, Object>();
		for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
			OrderItem orderItem = iterator.next();
			if (orderItem == null || StringUtils.isEmpty(orderItem.getSn())) {
				iterator.remove();
			}
		}
		order.setArea(areaService.find(areaId));
		order.setPaymentMethod(paymentMethodService.find(paymentMethodId));
		order.setShippingMethod(shippingMethodService.find(shippingMethodId));
		if (!isValid(order)) {
			data.put("message", Message.warn("admin.common.invalid"));
			return data;
		}
		Order pOrder = orderService.find(order.getId());
		if (pOrder == null) {
			data.put("message", Message.error("admin.common.invalid"));
			return data;
		}
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.getId() != null) {
				OrderItem pOrderItem = orderItemService.find(orderItem.getId());
				if (pOrderItem == null || !pOrder.equals(pOrderItem.getOrder())) {
					data.put("message", Message.error("admin.common.invalid"));
					return data;
				}
				Product product = pOrderItem.getProduct();
				if (product != null && product.getStock() != null) {
					if (pOrder.getIsAllocatedStock()) {
						if (orderItem.getQuantity() > product.getAvailableStock() + pOrderItem.getQuantity()) {
							data.put("message", Message.warn("admin.order.lowStock"));
							return data;
						}
					} else {
						if (orderItem.getQuantity() > product.getAvailableStock()) {
							data.put("message", Message.warn("admin.order.lowStock"));
							return data;
						}
					}
				}
			} else {
				Product product = productService.findBySn(orderItem.getSn());
				if (product == null) {
					data.put("message", Message.error("admin.common.invalid"));
					return data;
				}
				if (product.getStock() != null && orderItem.getQuantity() > product.getAvailableStock()) {
					data.put("message", Message.warn("admin.order.lowStock"));
					return data;
				}
			}
		}
		Map<String, Object> orderItems = new HashMap<String, Object>();
		for (OrderItem orderItem : order.getOrderItems()) {
			orderItems.put(orderItem.getSn(), orderItem);
		}
		order.setFee(pOrder.getFee());
		order.setPromotionDiscount(pOrder.getPromotionDiscount());
		order.setCouponDiscount(pOrder.getCouponDiscount());
		order.setAmountPaid(pOrder.getAmountPaid());
		data.put("weight", order.getWeight());
		data.put("price", order.getPrice());
		data.put("quantity", order.getQuantity());
		data.put("amount", order.getAmount());
		data.put("orderItems", orderItems);
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Order order, Long areaId, Long paymentMethodId, Long shippingMethodId,
			RedirectAttributes redirectAttributes) {
		for (Iterator<OrderItem> iterator = order.getOrderItems().iterator(); iterator.hasNext();) {
			OrderItem orderItem = iterator.next();
			if (orderItem == null || StringUtils.isEmpty(orderItem.getSn())) {
				iterator.remove();
			}
		}
		order.setArea(areaService.find(areaId));
		order.setPaymentMethod(paymentMethodService.find(paymentMethodId));
		order.setShippingMethod(shippingMethodService.find(shippingMethodId));
		if (!isValid(order)) {
			return ERROR_VIEW;
		}
		Order pOrder = orderService.find(order.getId());
		if (pOrder == null) {
			return ERROR_VIEW;
		}
		if (pOrder.getOrderStatus() != OrderStatus.unpayment) {
			return ERROR_VIEW;
		}
		Admin admin = adminService.getCurrent();
		if (pOrder.isLocked(admin)) {
			return ERROR_VIEW;
		}
		if (!order.getIsInvoice()) {
			order.setInvoiceTitle(null);
			order.setTax(new BigDecimal(0));
		}
		for (OrderItem orderItem : order.getOrderItems()) {
			if (orderItem.getId() != null) {
				OrderItem pOrderItem = orderItemService.find(orderItem.getId());
				if (pOrderItem == null || !pOrder.equals(pOrderItem.getOrder())) {
					return ERROR_VIEW;
				}
				Product product = pOrderItem.getProduct();
				if (product != null && product.getStock() != null) {
					if (pOrder.getIsAllocatedStock()) {
						if (orderItem.getQuantity() > product.getAvailableStock() + pOrderItem.getQuantity()) {
							return ERROR_VIEW;
						}
					} else {
						if (orderItem.getQuantity() > product.getAvailableStock()) {
							return ERROR_VIEW;
						}
					}
				}
				BeanUtils.copyProperties(pOrderItem, orderItem, new String[] { "price", "quantity" });
				if (pOrderItem.getIsGift()) {
					orderItem.setPrice(new BigDecimal(0));
				}
			} else {
				Product product = productService.findBySn(orderItem.getSn());
				if (product == null) {
					return ERROR_VIEW;
				}
				if (product.getStock() != null && orderItem.getQuantity() > product.getAvailableStock()) {
					return ERROR_VIEW;
				}
				orderItem.setName(product.getName());
				orderItem.setFullName(product.getFullName());
				if (product.getIsGift()) {
					orderItem.setPrice(new BigDecimal(0));
				}
				orderItem.setWeight(product.getWeight());
				orderItem.setThumbnail(product.getThumbnail());
				orderItem.setIsGift(product.getIsGift());
				orderItem.setShippedQuantity(0);
				orderItem.setReturnQuantity(0);
				orderItem.setProduct(product);
				orderItem.setOrder(pOrder);
			}
		}
		order.setSn(pOrder.getSn());
		order.setOrderStatus(pOrder.getOrderStatus());
		order.setPaymentStatus(pOrder.getPaymentStatus());
		order.setShippingStatus(pOrder.getShippingStatus());
		order.setFee(pOrder.getFee());
		order.setPromotionDiscount(pOrder.getPromotionDiscount());
		order.setCouponDiscount(pOrder.getCouponDiscount());
		order.setAmountPaid(pOrder.getAmountPaid());
		order.setPromotion(pOrder.getPromotion());
		order.setExpire(pOrder.getExpire());
		order.setLockExpire(null);
		order.setIsAllocatedStock(pOrder.getIsAllocatedStock());
		order.setOperator(null);
		order.setMember(pOrder.getMember());
		order.setCouponCode(pOrder.getCouponCode());
		order.setCoupons(pOrder.getCoupons());
		order.setOrderLogs(pOrder.getOrderLogs());
		order.setDeposits(pOrder.getDeposits());
		order.setPayments(pOrder.getPayments());
		order.setRefunds(pOrder.getRefunds());
		order.setShippings(pOrder.getShippings());
		order.setReturns(pOrder.getReturns());
		orderService.update(order, admin);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Order order,OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
			Boolean hasExpired, Pageable pageable, ModelMap model, String bDate, String eDate,
			String shippingSn,String pname,String psn,String memberName,String shopName
			) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		if (bDate != null) {
			try {
				beginDate = format.parse(bDate);
			} catch (ParseException e) {
				logger.error("日期格式化失败", e);
				return ERROR_VIEW;
			}
			Calendar calendar = DateUtils.toCalendar(beginDate);
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			beginDate = calendar.getTime();
		}
		Date endDate = null;
		if (eDate != null) {
			try {
				endDate = format.parse(eDate);
			} catch (ParseException e) {
				logger.error("日期格式化失败", e);
				return ERROR_VIEW;
			}
			Calendar calendar = DateUtils.toCalendar(endDate);
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			endDate = calendar.getTime();
		}
		
	
		
		// 返回起始和结束时间
		model.addAttribute("beginDate", beginDate);
		model.addAttribute("endDate", endDate);

		model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("paymentStatus", paymentStatus);
		model.addAttribute("shippingStatus", shippingStatus);
		model.addAttribute("hasExpired", hasExpired);
		//model.addAttribute("page", orderService.findPage(beginDate, endDate, orderStatus, paymentStatus, shippingStatus,
		//		hasExpired, pageable));
		/*
		Order o = new Order();
		o.setOrderStatus(Order.OrderStatus.completed);
		o.setPaymentStatus(Order.PaymentStatus.paid);
		o.setShippingStatus(Order.ShippingStatus.shipped);
		o.setConsignee("54");
		o.setPhone("13527225052");
		String ssn = "201602189595";
		String pname = "%珊瑚绒%";
		String psn = "NQ21101";
		String mname="小黑";
		String bd = "2016-02-18 14:50:43";
		String ed = "2016-02-18 14:50:43";*/
		Integer os = null;
		Integer ss = null;
		Integer ps = null;
		if(orderStatus!=null){
			os = orderStatus.ordinal();
		}
		if(shippingStatus!=null){
			ss = shippingStatus.ordinal();
		}
		if(paymentStatus!=null){
			ps = paymentStatus.ordinal();
		}
		Page page = orderService.findPage(order,shippingSn,psn,pname,memberName,shopName,bDate,eDate,os,ss,ps,pageable,hasExpired);
		model.addAttribute("page", page);	    
		
		return "/admin/order/list";
	}
	

	/**
	 * 导出数据
	 */
	@RequestMapping(value = "/exportData", method = RequestMethod.POST)
	public void exportData(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
			Boolean hasExpired, Pageable pageable, String exportbDate, String exporteDate, HttpServletRequest request,
			HttpServletResponse response) {
		Locale locale = Locale.CANADA;
		SimpleDateFormat frm1 = new SimpleDateFormat("yyyy-MM-dd", locale);
		Date beginDate = null;
		if (exportbDate != null) {
			try {
				beginDate = frm1.parse(exportbDate);
			} catch (ParseException e) {
				logger.error("日期格式化失败", e);
			}
			Calendar calendar = DateUtils.toCalendar(beginDate);
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
			beginDate = calendar.getTime();
		}
		Date endDate = null;
		if (exporteDate != null) {
			try {
				endDate = frm1.parse(exporteDate);
			} catch (ParseException e) {
				logger.error("日期格式化失败", e);
			}
			Calendar calendar = DateUtils.toCalendar(endDate);
			calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
			endDate = calendar.getTime();
		}
		try {
			List<Order> orders = orderService.findList(orderStatus, paymentStatus, shippingStatus, hasExpired, pageable,
					beginDate, endDate);
			Setting setting = SettingUtils.get();
			String[] headers = { SpringUtils.getMessage("Order.sn", setting.getSiteName()),
					SpringUtils.getMessage("Order.member", setting.getSiteName()),
					SpringUtils.getMessage("Order.createDate", setting.getSiteName()),
					SpringUtils.getMessage("Order.Item.productName", setting.getSiteName()),
					SpringUtils.getMessage("Order.Item.productQuantity", setting.getSiteName()),
					SpringUtils.getMessage("Order.Item.productSpec", setting.getSiteName()),
					SpringUtils.getMessage("Order.amount", setting.getSiteName()),
					//导出优惠券 及优惠码
				
					//SpringUtils.getMessage("Order.member.name", setting.getSiteName()),
					SpringUtils.getMessage("Order.member.link", setting.getSiteName()),
					SpringUtils.getMessage("Order.member.area", setting.getSiteName()),
					SpringUtils.getMessage("Order.member.addr", setting.getSiteName()),
					SpringUtils.getMessage("Order.consignee", setting.getSiteName()),
					SpringUtils.getMessage("Order.consignee.tel", setting.getSiteName()),
					SpringUtils.getMessage("Order.consignee.addr", setting.getSiteName()),
					SpringUtils.getMessage("Order.orderStatus", setting.getSiteName()),
					SpringUtils.getMessage("Order.paymentStatus", setting.getSiteName()),
					SpringUtils.getMessage("Order.shippingStatus", setting.getSiteName()),
					SpringUtils.getMessage("Order.couponCode", setting.getSiteName()),
					SpringUtils.getMessage("Order.coupons", setting.getSiteName()),
					SpringUtils.getMessage("商户订单号", "")
			};
		
			        

			// 订单编号 会员 下单时间 产品名称 产品数量 型号 订单金额 会员姓名 出生年月日 联系方式 所属省 所属市 详细地址 收货人
			// 收货人电话 收货人地址 订单状态 支付状态 配送状态

			// String[] values = { "getSn", "getAmount",
			// "getMember.getUsername", "getConsignee", "getPaymentMethodName",
			// "getShippingMethodName", "getOrderStatus", "getPaymentStatus",
			// "getShippingStatus",
			// "getCreateDate", "getOrderItems" };
			// Map<String, String> enumMap = new HashMap<String, String>();
			// enumMap.put(Order.OrderStatus.class.toString(),
			// "Order.OrderStatus.");
			// enumMap.put(Order.PaymentStatus.class.toString(),
			// "Order.PaymentStatus.");
			// enumMap.put(Order.ShippingStatus.class.toString(),
			// "Order.ShippingStatus.");

			ExportUtil<Order> ex = new ExportUtil<Order>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CANADA);
			String filedisplay = SpringUtils.getMessage("Order.shippingExportName", setting.getSiteName())
					+ sdf.format(new Date()) + ".xls";
			String title = SpringUtils.getMessage("Order.shippingExportName", setting.getSiteName());
			// ex.exportExcel(request, response, filedisplay, title, headers,
			// values, orders, "yyyy-MM-dd", enumMap);
			orderExportExcel(request, response, filedisplay, title, headers, orders, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 利用正则表达式判断textValue是否全部由数字组成
	 * 
	 * @param workbook
	 * @param cell
	 * @param textValue
	 */
	private void setCellValues(Workbook workbook, Row row, CellStyle style2, String textValue, int sn) {
		// 利用正则表达式判断textValue是否全部由数字组成
		Cell cell = row.createCell(sn);
		cell.setCellStyle(style2);
		if (textValue != null) {
			Pattern p = Pattern.compile("^//d+(//.//d+)?$");
			Matcher matcher = p.matcher(textValue);
			if (matcher.matches()) {
				// 是数字当作double处理
				cell.setCellValue(Double.parseDouble(textValue));
			} else {
				HSSFRichTextString richString = new HSSFRichTextString(textValue);
				Font font3 = workbook.createFont();
				font3.setColor(HSSFColor.BLUE.index);
				richString.applyFont(font3);
				cell.setCellValue(richString);
			}
		}
	}

	/**
	 * 导出订单到excel
	 * @param request
	 * @param response
	 * @param filedisplay
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param pattern
	 * @throws Exception
	 */
	private void orderExportExcel(HttpServletRequest request, HttpServletResponse response, String filedisplay,
			String title, String[] headers, Collection<Order> dataset, String pattern) throws Exception {
		// 声明一个工作薄
		Workbook workbook = new HSSFWorkbook();

		// 生成一个表格
		Sheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		CellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		Font font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		CellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		Font font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 产生表格标题行
		Row row = sheet.createRow(0);
		for (short i = 0; i < headers.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 遍历集合数据，产生数据行
		Iterator<Order> it = dataset.iterator();
		int index = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		Setting setting = SettingUtils.get();
		while (it.hasNext()) {
			Order t = it.next();
			if (t != null) {
				Iterator<OrderItem> iOrderItem = t.getOrderItems().iterator();
				while (iOrderItem.hasNext()) {
					// 订单编号 会员 下单时间 产品名称 产品数量 型号 订单金额 会员姓名 出生年月日 联系方式 所属省 所属市
					// 详细地址 收货人
					// 收货人电话 收货人地址 订单状态 支付状态 配送状态6
					try {
						index++;
						OrderItem orderItem = iOrderItem.next();
						row = sheet.createRow(index);

						setCellValues(workbook, row, style2, t.getSn(), 0);

						if (t.getMember() != null)
							setCellValues(workbook, row, style2, t.getMember().getUsername(), 1);
						else
							setCellValues(workbook, row, style2, "", 1);

						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						setCellValues(workbook, row, style2, sdf.format(t.getCreateDate()), 2);
						setCellValues(workbook, row, style2, orderItem.getName(), 3);
						setCellValues(workbook, row, style2, orderItem.getQuantity().toString(), 4);
						if (orderItem.getProduct() != null) 
							setCellValues(workbook, row, style2, orderItem.getProduct().getSn(), 5);
						else
							setCellValues(workbook, row, style2, "", 5);
						if (t.getAmount() != null)
							setCellValues(workbook, row, style2, "￥" + df.format(t.getAmount()), 6);
																	
						if (t.getMember() != null) {
							/*if (t.getMember().getName() != null
									&& !StringUtils.isNotEmpty(t.getMember().getName()))
								setCellValues(workbook, row, style2, t.getMember().getName(), 7);
							else
								setCellValues(workbook, row, style2, "", 7);*/
							/*if (t.getMember().getBirth() != null
									&& !StringUtils.isNotEmpty(t.getMember().getBirth().toString()))
								setCellValues(workbook, row, style2, sdf.format(t.getMember().getBirth()), 8);
							else
								setCellValues(workbook, row, style2, "", 8);*/
							setCellValues(workbook, row, style2, t.getMember().getMobile(), 7);
							if (t.getAreaName() != null)
								setCellValues(workbook, row, style2, t.getAreaName(), 8);
							else
								setCellValues(workbook, row, style2, "", 8);
							if(t.getAddress()!=null)
							{
							setCellValues(workbook, row, style2, ascii2native(t.getAddress()), 9);
							}
							setCellValues(workbook, row, style2, ascii2native(t.getConsignee()), 10);
							setCellValues(workbook, row, style2, t.getPhone(), 11);
							if(t.getAddress()!=null)
							{
							setCellValues(workbook, row, style2, ascii2native(t.getAreaName() + t.getAddress()), 12);
							}
							setCellValues(workbook, row, style2, SpringUtils.getMessage(
									"Order.OrderStatus." + t.getOrderStatus().toString(), setting.getSiteName()), 13);
							setCellValues(workbook, row, style2, t.getPaymentMethodName().toString(), 14);
							setCellValues(workbook, row, style2, t.getShippingMethodName().toString(), 15);
							
						}
						if (t.getCouponCode() != null)
							setCellValues(workbook, row, style2, t.getCouponCode().getCode().toString(), 16);
						else
							setCellValues(workbook, row, style2, "", 16);
						
						if (t.getCouponCode() != null&&t.getCouponCode().getCoupon()!=null)
							setCellValues(workbook, row, style2, t.getCouponCode().getCoupon().getName(), 17);
						else
							setCellValues(workbook, row, style2, "", 17);
						if(t.getPayMerOrderNo()!=null){
							setCellValues(workbook, row, style2, t.getPayMerOrderNo().toString(), 18);
						}else {
							setCellValues(workbook, row, style2, "", 18);
						}
					} catch (SecurityException e) {
						logger.error(e);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/x-msdownload");
		// filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(filedisplay.getBytes(), "iso-8859-1"));
		OutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}

	/**
	 * 导出数据
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public @ResponseBody Message export(Long[] ids) {
		if (ids != null) {
			Admin admin = adminService.getCurrent();
			for (Long id : ids) {
				Order order = orderService.find(id);
				if (order != null && order.isLocked(admin)) {
					return Message.error("admin.order.deleteLockedNotAllowed", order.getSn());
				}
			}
			orderService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Long[] ids) {
		if (ids != null) {
			Admin admin = adminService.getCurrent();
			for (Long id : ids) {
				Order order = orderService.find(id);
				if (order != null && order.isLocked(admin)) {
					return Message.error("admin.order.deleteLockedNotAllowed", order.getSn());
				}
			}
			orderService.delete(ids);
		}
		return SUCCESS_MESSAGE;
	}

	/**
	 * 管理员发货后发短信通知用户
	 * 
	 * @param mobile
	 * @param trackingNo
	 * @return
	 */
	private boolean sendShippingSms(String mobile, String trackingNo, String deliveryCorp) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("code", deliveryCorp);
		paramMap.put("code2", trackingNo);
		int result = smsService.sendMessage(SmsBusType.SHIPPING, paramMap, mobile);
		if (result == 0)
			return true;
		else
			return false;
	}
	
	/**
	 * trackingNo修改
	 */
	@RequestMapping(value = "/trackingNo", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> trackingNo(Long id, String trackingNo) {
		Map<String, Object> data = new HashMap<String, Object>();
		Shipping shipping = shippingService.find(id);
		if(shipping == null){
			data.put("message", ERROR_MESSAGE);
			return data;
		}
		shipping.setTrackingNo(trackingNo);
		shippingService.update(shipping);
		data.put("message", SUCCESS_MESSAGE);
		return data;
	}
	
	 /***
	  * unicode转中文
	  * @param theString
	  * @return
	  */
	

	/***
	 * unicode转中文
	 * 
	 * @param theString
	 * @return
	 */

	public static String ascii2native(String ascii) {

		Pattern pattern = Pattern.compile("\\&\\#(\\d+)");
		ascii = ascii.replaceAll("&gt;", ">");
		ascii = ascii.replaceAll("&#", ";&#");
		StringBuilder sb = new StringBuilder();
		String[] childs = ascii.split(";");
		for (String child : childs) {
			if (child.contains("&#")) {
				Matcher m = pattern.matcher(child);
				while (m.find())
					sb.append((char) Integer.valueOf(m.group(1)).intValue());

			} else
				sb.append(child);
		}

		ascii = sb.toString();

		return ascii;

	}
	
}