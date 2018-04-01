package com.vivebest.mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.BookingDao;
import com.vivebest.mall.dao.OrderDao;
import com.vivebest.mall.dao.OrderItemDao;
import com.vivebest.mall.dao.SnDao;
import com.vivebest.mall.entity.Booking;
import com.vivebest.mall.entity.Booking.BookingType;
import com.vivebest.mall.entity.Booking.Status;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.PromotionProducts;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.service.BookingService;
import com.vivebest.mall.service.PromotionService;
import com.vivebest.mall.service.SmsService;

/**
 * Service - 预约团购
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("bookingServiceImpl")
public class BookingServiceImpl extends BaseServiceImpl<Booking, Long>implements BookingService {
	// private static Logger logger =
	// Logger.getLogger(BookingServiceImpl.class);
	@Resource(name = "bookingDaoImpl")
	private BookingDao bookingDao;
	@Resource(name = "smsServiceImpl")
	SmsService smsService;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	@Resource(name = "orderDaoImpl")
	private OrderDao orderDao;

	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;

	@Resource(name = "bookingDaoImpl")
	public void setBaseDao(BookingDao bookingDao) {
		super.setBaseDao(bookingDao);
	}

	public Page<Booking> findPage(Member member, Pageable pageable) {
		return bookingDao.findPage(member, pageable);
	}

	@Override
	public Page<Booking> findPage(Long promotionId, String mobile, String username, Date beginDate, Date endDate,
			Pageable pageable) {
		return bookingDao.findPage(promotionId, mobile, username, beginDate, endDate, pageable);
	}

	public List<Booking> findBooking(Member member, Promotion promotion, Product product,Status status) {
		return bookingDao.findBooking(member, promotion, product,status);
	}

	public Booking build(Booking booking1, Member member, PromotionProducts promotionProducts, Integer number, String mobile, Status status) {
		String base = "b0123456789";     
		Random random = new Random();     
		StringBuffer sb = new StringBuffer();     
		for (int i = 0; i < 4; i++) {     
		    int num = random.nextInt(base.length());     
		    sb.append(base.charAt(num));     
		}
		Booking booking = new Booking();
		if (booking1 == null) {
			// 预约订单
			booking.setBookingType(BookingType.groupPurchase);
			booking.setMember(member);
			booking.setPromotion(promotionProducts.getPromotion());
			booking.setProduct(promotionProducts.getProduct());
			booking.setAmount(promotionProducts.getGroupBuyingDeposit());
			booking.setPrice(promotionProducts.getProduct().getPrice());
			booking.setPaymentOrder("b"+snDao.generate(Sn.Type.order)+sb.toString());
			booking.setOrders(null);
			booking.setStatus(status);
			booking.setRemarks("预约");
			booking.setQuantity(number);
			booking.setPromotionProducts(promotionProducts);
			booking.setMobile(mobile);
			bookingDao.persist(booking);
		}/* else {
			Order order = new Order();
			order.setOrderType(OrderType.changeOrder);
			order.setAddress("预约金");
			order.setAmountPaid(BigDecimal.valueOf(0));
			order.setAreaName("预约金");
			order.setConsignee(member.getName());
			order.setCouponDiscount(BigDecimal.valueOf(0));
			order.setFee(BigDecimal.valueOf(0));
			order.setFreight(BigDecimal.valueOf(0));
			order.setIsAllocatedStock(false);
			order.setIsInvoice(false);
			order.setOffsetAmount(BigDecimal.valueOf(0));
			order.setOrderStatus(OrderStatus.unpayment);
			order.setPaymentMethodName("预约金");
			order.setPaymentStatus(PaymentStatus.unpaid);
			order.setPhone(member.getMobile());
			order.setPoint(0L);
			order.setPromotionDiscount(BigDecimal.valueOf(0));
			order.setShippingMethodName("预约金");
			order.setShippingStatus(ShippingStatus.unshipped);
			order.setSn(snDao.generate(Sn.Type.order));
			order.setTax(BigDecimal.valueOf(0));
			order.setMember(member);
			orderDao.persist(order);
			// 订单项
			OrderItem orderItem = new OrderItem();
			orderItem.setFullName("预约金");
			orderItem.setIsGift(false);
			orderItem.setName("预约金");
			Filter filter = new Filter("promotion", Operator.eq, promotionProducts.getPromotion());
			Filter filter1 = new Filter("product", Operator.eq, promotionProducts.getProduct());
			Long count = bookingDao.count(filter, filter1);
			if (promotionProducts.getGroupBuyingQuantity1() != null
					&& promotionProducts.getGroupBuyingQuantity1().compareTo(count) < 1) {
				orderItem.setPrice(promotionProducts.getGroupBuyingPrice1());
			} else if (promotionProducts.getGroupBuyingQuantity2() != null
					&& promotionProducts.getGroupBuyingQuantity2().compareTo(count) < 1
					&& promotionProducts.getGroupBuyingQuantity1().compareTo(count) > 0) {
				orderItem.setPrice(promotionProducts.getGroupBuyingPrice2());
			} else if (promotionProducts.getGroupBuyingQuantity3() != null
					&& promotionProducts.getGroupBuyingQuantity3().compareTo(count) < 1
					&& promotionProducts.getGroupBuyingQuantity2().compareTo(count) > 0) {
				orderItem.setPrice(promotionProducts.getGroupBuyingPrice3());
			} else if (promotionProducts.getGroupBuyingQuantity4() != null
					&& promotionProducts.getGroupBuyingQuantity4().compareTo(count) < 1
					&& promotionProducts.getGroupBuyingQuantity3().compareTo(count) > 0) {
				orderItem.setPrice(promotionProducts.getGroupBuyingPrice4());
			} else if (promotionProducts.getGroupBuyingQuantity5() != null
					&& promotionProducts.getGroupBuyingQuantity4().compareTo(count) > 0) {
				orderItem.setPrice(promotionProducts.getGroupBuyingPrice5());
			}
			orderItem.setQuantity(booking1.getQuantity());
			orderItem.setReturnQuantity(0);
			orderItem.setShippedQuantity(0);
			orderItem.setSn(promotionProducts.getProduct().getSn());
			orderItem.setOrder(order);
			orderItemDao.persist(orderItem);

			booking1.setOrders(order);
			booking1.setStatus(Status.payment);
			booking = bookingDao.merge(booking1);
		}*/
		return booking;
	}

	public int send(Long[] ids, HttpServletRequest request) {
		List<Promotion> promotions = new ArrayList<Promotion>();
		List<Booking>bookings=new ArrayList<Booking>();
		if (ids.length == 0) {
			return 0;
		} else {
			promotions = promotionService.findList(ids);
			for(Promotion promotion:promotions){
				bookings.addAll(bookingDao.findBooking(null, promotion, null,Status.payment));
			}
		}
		if (bookings!=null&&bookings.size()>0) {
			for (Booking booking:bookings) {
				smsService.sendBookingNotifySms(booking);
			}
			return bookings.size();
		}else{
			return 0;
		}
	}

	public Booking findBySn(String sn) {
		return bookingDao.findBySn(sn);
	}
}
