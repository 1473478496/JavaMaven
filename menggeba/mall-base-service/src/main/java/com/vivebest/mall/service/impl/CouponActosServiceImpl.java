package com.vivebest.mall.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.CouponActosDao;
import com.vivebest.mall.dao.impl.CouponActosDaoImpl;
import com.vivebest.mall.entity.CopounActors;
import com.vivebest.mall.entity.Coupon;
import com.vivebest.mall.service.CouponActosService;


@Service("couponActosServiceImpl")
public class CouponActosServiceImpl extends BaseServiceImpl<CopounActors, Long> implements CouponActosService {


	@Resource(name = "couponActosDaoImpl")
	private CouponActosDaoImpl couponActosDao;
	
	@Resource(name = "couponActosDaoImpl")
	private CouponActosDao actosDao;
	
	@Resource(name = "couponActosDaoImpl")
	public void setBaseDao(CouponActosDao couponActosDao){
		super.setBaseDao(couponActosDao);
	}
	
	public Boolean build(Coupon coupon, String Tel) {
		
		 if(coupon.getLimtNums()>0)
		 {
			long ownerCount= this.count(coupon, Tel);
			if(ownerCount>=coupon.getLimtNums()) //超过限额，就不允许再领取
			{
				 return false;
			} 
		 }
		 CopounActors entity=new CopounActors();
		 entity.setActor(Tel);
		 entity.setActorTel(Tel);
		 entity.setCoupon(coupon);
		 couponActosDao.persist(entity);
		 return true;
		 
	}

	@Override
	public Long count(Coupon coupon, String Tel) {
		// TODO Auto-generated method stub
		 return couponActosDao.count(coupon, Tel);
	}

	@Override
	public CopounActors findTel(String mobile) {
		return actosDao.findTel(mobile);
	}

	@Override
	public Boolean newActo(Coupon coupon, String Tel) {
		Boolean bln = couponActosDao.isReceive(Tel);
		if(bln) //超过限额，就不允许再领取
		{
			 return false;
		}
		CopounActors entity=new CopounActors();
		entity.setActor(Tel);
		entity.setActorTel(Tel);
		entity.setCoupon(coupon);
		couponActosDao.persist(entity);
		return true;
	}
	

	
}
