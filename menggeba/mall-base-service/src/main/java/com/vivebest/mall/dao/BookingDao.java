package com.vivebest.mall.dao;


import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Booking;
import com.vivebest.mall.entity.Booking.Status;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * Dao - 预约团购
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface BookingDao extends BaseDao<Booking, Long> {
	/**
	 * 
	  * 查找团购分页
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Booking> findPage(Member member, Pageable pageable);

	/**
	 * 查询分页
	 * @param promotionId
	 * 		活动编号
	 * @param mobile
	 * 		手机号
	 * @param username
	 * 		用户名
	 * @param beginDate
	 * 		开始时间
	 * @param endDate
	 * 		结束时间
	 * @param pageable
	 * 		分页信息
	 * @return
	 */
	Page<Booking> findPage(Long promotionId, String mobile, String username, Date beginDate, Date endDate,
			Pageable pageable);

	/**
	 * @param member 会员
	 * @param promotion 促销
	 * @param product 商品
	 * @return
	 */
	List<Booking> findBooking(Member member, Promotion promotion, Product product,Status status);
	
	List<Booking> findBookingByPromProduct(PromotionProducts promotionProducts);

	/**查询
	 * @param sn
	 * @return
	 */
	Booking findBySn(String sn);
	

}
