/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.dao.CouponCodeDao;
import com.vivebest.mall.dao.MemberDao;
import com.vivebest.mall.dao.ProductDao;
import com.vivebest.mall.entity.Coupon;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.service.CouponCodeService;

/**
 * Service - 优惠码
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Service("couponCodeServiceImpl")
public class CouponCodeServiceImpl extends BaseServiceImpl<CouponCode, Long> implements CouponCodeService {

	@Resource(name = "couponCodeDaoImpl")
	private CouponCodeDao couponCodeDao;
	@Resource(name = "memberDaoImpl")
	private MemberDao memberDao;
	
    @Resource(name = "productDaoImpl")
	private ProductDao productDao;
	

	@Resource(name = "couponCodeDaoImpl")
	public void setBaseDao(CouponCodeDao couponCodeDao) {
		super.setBaseDao(couponCodeDao);
	}

	@Transactional(readOnly = true)
	public boolean codeExists(String code) {
		return couponCodeDao.codeExists(code);
	}

	@Transactional(readOnly = true)
	public CouponCode findByCode(String code) {
		return couponCodeDao.findByCode(code);
	}
	
	@Override
	public CouponCode findValidCode(String code,Long ids[]) {
		CouponCode cc = couponCodeDao.findByCode(code);
		System.out.println(cc.getCoupon());
		System.out.println(cc.getCoupon().getName());
		System.out.println(cc.getCoupon().getProducts());
		System.out.println(cc.getCoupon().getProductCategories().size());
		Set<Product> set = cc.getCoupon().getProducts();
		//System.out.println(set.size());
		Set<ProductCategory> pCategories = cc.getCoupon().getProductCategories();
		if(set.size()>0){
			Iterator<Product> iter = set.iterator();
			while(iter.hasNext()){
				Product p = iter.next();
				System.out.println(ids.length);
				for (int i = 0; i < ids.length -1; i++) {
					if(ids[i] == null){
						continue;
					}
					System.out.println(p.getId()+"=="+ids[i]);
					if(p.getId().longValue()== ids[i].longValue()){
						return cc;
					}
					
					Product pro = productDao.findById(ids[i]);
					if(pro != null){
						System.out.println(pro.getId());
						Iterator<ProductCategory> pcIter = pCategories.iterator();
						while(pcIter.hasNext()){
							ProductCategory pc = pcIter.next();
							//System.out.println(pc.getId()+"==" +pro.getId());
							if(pc.getId().longValue() == pro.getProductCategory().getId().longValue()){
								return cc;
							}
						}
					}
				}
			}
			return null;
		}
	
		return cc;
	}

	public CouponCode build(Coupon coupon, Member member) {
		return couponCodeDao.build(coupon, member);
	}

	public List<CouponCode> build(Coupon coupon, Member member, Integer count) {
		return couponCodeDao.build(coupon, member, count);
	}

	public CouponCode exchange(Coupon coupon, Member member) {
		Assert.notNull(coupon);
		Assert.notNull(member);
		
		 if(coupon.getLimtNums()>0)
		 {
			long ownerCount= couponCodeDao.count(coupon, member, null, null, null);
			if(ownerCount>=coupon.getLimtNums()) //超过限额，就不允许再领取
			{
				 return null;
			}
		 }

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		//member.setPoint(member.getPoint() - coupon.getPoint()); 萌值消费不从数据表中更新
		memberDao.merge(member);

		return couponCodeDao.build(coupon, member);
	}

	@Transactional(readOnly = true)
	public Page<CouponCode> findPage(Member member, Pageable pageable) {
		return couponCodeDao.findPage(member, pageable);
	}
	
	@Transactional(readOnly = true)
	public Page<CouponCode> findPage(Member member,  Boolean hasBegun, Boolean hasExpired, Boolean isUsed, Pageable pageable) {
		return couponCodeDao.findPage(member, hasBegun, hasExpired, isUsed,pageable);
	}

	

	@Transactional(readOnly = true)
	public Long count(Coupon coupon, Member member, Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		return couponCodeDao.count(coupon, member, hasBegun, hasExpired, isUsed);
	}

	@Override
	public List<Object[]> FindValidCouponCode(Coupon coupon, Member member,
			Boolean hasBegun, Boolean hasExpired, Boolean isUsed) {
		// TODO Auto-generated method stub
		return couponCodeDao.findValidCouponCodeList(coupon, member, hasBegun, hasExpired, isUsed);
	}

	@Override
	public CouponCode receiveCoupon(Coupon coupon, Member member) {
		//long ownerCount= couponCodeDao.count(coupon, member, null, null, null);
		Boolean bln = couponCodeDao.isReceive(coupon, member);
		if(bln) //超过限额，就不允许再领取
		{
			 return null;
		 }

		memberDao.lock(member, LockModeType.PESSIMISTIC_WRITE);
		//member.setPoint(member.getPoint() - coupon.getPoint()); 萌值消费不从数据表中更新
		memberDao.merge(member);

		return couponCodeDao.build(coupon, member);
	}

}