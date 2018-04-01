package com.vivebest.mall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.CouponCodeDao;
import com.vivebest.mall.dao.RightOrderDao;
import com.vivebest.mall.dao.RightOrderItemDao;
import com.vivebest.mall.dao.RightsCodeDao;
import com.vivebest.mall.dao.RightsDao;
import com.vivebest.mall.dao.SnDao;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.RightOrder;
import com.vivebest.mall.entity.RightOrder.PaymentStatus;
import com.vivebest.mall.entity.RightOrder.RightsOrderStatus;
import com.vivebest.mall.entity.RightOrderItem;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsCode;
import com.vivebest.mall.entity.Sn;
import com.vivebest.mall.service.RightOrderItemService;
import com.vivebest.mall.service.RightOrderService;
import com.vivebest.mall.service.RightsCodeService;

/**
 * RightOrderServiceImpl - 权益订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("rightsOrderServiceImpl")
public class RightOrderServiceImpl extends BaseServiceImpl<RightOrder, Long>implements RightOrderService {
	private static Logger logger = Logger.getLogger(RightOrderServiceImpl.class);
	
	@Resource(name = "rightsOrderDaoImpl")
	private RightOrderDao rightsOrderDao;
	@Resource(name = "rightsDaoImpl")
	private RightsDao rightsDao;
	
	@Resource(name = "rightsCodeDaoImpl")
	private RightsCodeDao rightsCodeDao;
	
	
	@Resource(name = "rightsCodeServiceImpl")
	private RightsCodeService rightsCodeService;
	
	@Resource(name = "rightsOrderItemServiceImpl")
	private RightOrderItemService rightOrderItemService;
	
	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;

	@Resource(name = "rightsOrderDaoImpl")
	public void setBaseDao(RightOrderDao rightsOrderDao) {
		super.setBaseDao(rightsOrderDao);
	}

	@Override
	public Page<RightOrder> findPage(RightsOrderStatus orderStatus, Boolean hasExpired, Pageable pageable) {

		return rightsOrderDao.findPage(orderStatus, hasExpired, pageable);
	}

	@Resource(name = "rightOrderItemDaoImpl")
	private RightOrderItemDao rightOrderItemDao;

	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	@Override
	public RightOrder build(Rights rights, CouponCode code, String RightType, Long quantity, Member member) {
		String base = "r0123456789";     
		Random random = new Random();     
		StringBuffer sb = new StringBuffer();     
		for (int i = 0; i < 4; i++) {     
		    int num = random.nextInt(base.length());     
		    sb.append(base.charAt(num));     
		}
		RightOrder rightOrder = new RightOrder();
	 
		rightOrder.setRightType(Boolean.valueOf(RightType));
		rightOrder.setRightsOrderStatus(RightsOrderStatus.unpayment);
		rightOrder.setPaymentStatus(PaymentStatus.unpaid);
	 
		rightOrder.setSn("r"+snDao.generate(Sn.Type.order)+sb.toString());
		rightOrder.setMember(member);
		rightOrder.setTradeId(rights.getRightsTrades().iterator().next().getId());
		
		logger.info("[权益订单创建订单编号:]" + rightOrder.getSn());
		rightOrder.setExpire(DateUtils.addMinutes(new Date(), 20));
		logger.info("[权益订单创建时间:]" + new Date());
		logger.info("[权益订单到期时间:]" + rightOrder.getExpire());
	 
		if (code != null&&rights.getIsCouponAllowed()) {
			couponCodeDao.lock(code, LockModeType.PESSIMISTIC_WRITE);
			if (!code.getIsUsed() && code.getCoupon() != null && rightOrder.isValid(code.getCoupon())) {
				BigDecimal couponDiscount =rightOrder.getSubPrice()
						.subtract(code.getCoupon().calculatePrice(quantity.intValue(), rights.getPrice()));
				couponDiscount = couponDiscount.compareTo(new BigDecimal(0)) > 0 ? couponDiscount : new BigDecimal(0);
				rightOrder.setCouponDiscount(couponDiscount);
				rightOrder.setCouponCode(code);
			}
			
		}
		List<RightOrderItem> orderItems = new ArrayList<RightOrderItem>();
		 
		rightsOrderDao.persist(rightOrder);
		rightOrder = rightsOrderDao.findBySn(rightOrder.getSn());
		
		List<RightsCode> codelist = rightsCodeDao.asignCode(rights.getId(), quantity.intValue());  //批量获取RightCodeList
		
		for (int i = 0; i < codelist.size(); i++) {
			RightOrderItem rightOrderItem = new RightOrderItem();
		 
			rightOrderItem.setPrice(rights.getPrice());
			rightOrderItem.setPricePoint(rights.getPricePoint());
			
			rightOrderItem.setPayPrice(rights.getPrice());  // wait for coupon calcu
			
			rightOrderItem.setPricePoint(rights.getPricePoint());
			
			RightsCode righscode=codelist.get(i); //获取RightCode
			rightOrderItem.setRightsCode(righscode);
			rightOrderItem.setRightOrder(rightOrder);
			rightOrderItem.setRights(rights);
			rightOrderItemDao.persist(rightOrderItem);
			orderItems.add(rightOrderItem);
			if(righscode!=null)
			{
				righscode.setRightOrder(rightOrder);
				rightsCodeService.update(righscode);
				 
			}
			 
		}
		rightOrder.setOrderItems(orderItems);
	 
		rightsDao.merge(rights);
		return rightOrder;
	}

	@Override
	public RightOrder findBySn(String sn) {
		// TODO Auto-generated method stub
		return rightsOrderDao.findBySn(sn);
	}

	public Page<RightOrder> findPage(RightOrder.RightsOrderStatus orderStatus, Boolean hasExpired, Member member,
			Pageable pageable) {
		return rightsOrderDao.findPage(orderStatus, hasExpired, member, pageable);
	}

	@Override
	public List<RightOrder> findList(RightsOrderStatus orderStatus,
			Boolean hasExpired, Pageable pageable) {
		// TODO Auto-generated method stub
		return rightsOrderDao.findList(orderStatus,
				 hasExpired, pageable);
	}
	
	/**
	 * 批量取消待支付订单
	 * 
	 * @param pageNumberOrderStatus
	 * @param pageSize
	 */
	@Override
	public void cancelOrder(int pageNumber, int pageSize) {
		StringBuffer orderCancelStr = new StringBuffer();
		for (int i = pageNumber;; i++) {
		 
			List<RightOrder> orderList =findList(RightsOrderStatus.unpayment,true,new Pageable(i, pageSize));
			if (null == orderList) {
				return;
			}
			for (RightOrder order : orderList) {			 
					order.setMemo("未支付，已取消");
					order.setRightsOrderStatus(RightsOrderStatus.cancelled);
					orderCancelStr.append(order.getSn()).append(",");	
					if(!order.getOrderItems().isEmpty()){
	    				for (RightOrderItem rightOrderItem : order.getOrderItems()) {
	    					RightsCode rightsCode=rightOrderItem.getRightsCode();
	    					rightsCode.setRightOrder(null);
	    					rightsCode.setMember(null);
	    					rightsCodeService.update(rightsCode);
	    					rightOrderItem.setRightsCode(null);
	    					rightOrderItemService.update(rightOrderItem);
	    				}
	    			}
					
			}
			

			rightsOrderDao.flush();

			if (orderList.size() < pageSize) {
				logger.info("[取消订单编号]:" + orderCancelStr.toString());
				return;
			}
		}
	}
	

}
