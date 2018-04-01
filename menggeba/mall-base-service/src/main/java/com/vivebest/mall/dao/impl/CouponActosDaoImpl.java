package com.vivebest.mall.dao.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.CouponActosDao;
import com.vivebest.mall.entity.CopounActors;
import com.vivebest.mall.entity.Coupon;

@Repository("couponActosDaoImpl")
public class CouponActosDaoImpl extends BaseDaoImpl<CopounActors, Long> implements CouponActosDao  {
	
	private static Logger logger = Logger.getLogger(CouponActosDaoImpl.class);

	@Override
	public Long count(Coupon coupon, String Tel) {
		// TODO Auto-generated method stub	
		String jpql = "select count(*) from CopounActors  couponActos where couponActos.actorTel= :tel and couponActos.coupon= :coupon"  ;
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("tel", Tel).setParameter("coupon", coupon).getSingleResult();
		return count;
	}

	@Override
	public CopounActors findTel(String actorTel) {
		String jpql = "select couponActos from CopounActors couponActos where couponActos.actorTel= :actorTel"  ;
		try {
			return entityManager.createQuery(jpql, CopounActors.class).setFlushMode(FlushModeType.COMMIT).setParameter("actorTel", actorTel).getSingleResult();
		} catch (NoResultException nre) {
			logger.info(">>>>>"+nre);
			return null;
		} catch (NonUniqueResultException nure) {
			logger.info(">>>>>"+nure);
			return null;
		}
	}

	public Boolean isReceive(String tel) {
		if (tel == null) {
			return false;
		}
		String jpql = "select count(*) from CopounActors couponActos where couponActos.createDate>= :receiveDate and couponActos.actorTel = :actorTel";
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		Long count;
		try {
			count = entityManager.createQuery(jpql, Long.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("receiveDate", matter.parse(matter.format(dt)))
					.setParameter("actorTel", tel).getSingleResult();
			return count > 0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

}
