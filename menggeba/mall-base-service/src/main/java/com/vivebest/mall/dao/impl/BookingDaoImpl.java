package com.vivebest.mall.dao.impl;


import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.BookingDao;
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
@Repository("bookingDaoImpl")
public class BookingDaoImpl extends BaseDaoImpl<Booking, Long> implements BookingDao {

	public Page<Booking> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Booking>(Collections.<Booking> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteriaQuery.from(Booking.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<Booking> findPage(Long promotionId, String mobile, String username, Date beginDate, Date endDate,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteriaQuery.from(Booking.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(promotionId != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotion").<Long>get("id"), promotionId));
		}
		if(mobile != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member").<String>get("mobile"), mobile));
		}
		if(username != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member").<String>get("username"), username));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public List<Booking> findBooking(Member member, Promotion promotion, Product product,Status status) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteriaQuery.from(Booking.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(member != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if(promotion != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotion"), promotion));
		}
		if(product != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product"), product));
		}
		if (status!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	
	public List<Booking> findBookingByPromProduct(PromotionProducts promotionProducts) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
		Root<Booking> root = criteriaQuery.from(Booking.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (promotionProducts!=null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotionProducts"), promotionProducts));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, null);
	}

	public Booking findBySn(String paymentOrder) {
		if(paymentOrder == null){
			return null;
		}
		String jpql = "select booking from Booking booking where lower(booking.paymentOrder) = lower(:paymentOrder)";
		return entityManager.createQuery(jpql, Booking.class).setFlushMode(FlushModeType.COMMIT).setParameter("paymentOrder", paymentOrder).getSingleResult();
	}

}
