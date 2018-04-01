/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.MemberRankDao;
import com.vivebest.mall.entity.MemberRank;
import com.vivebest.mall.entity.Product;

/**
 * Dao - 会员等级
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("memberRankDaoImpl")
public class MemberRankDaoImpl extends BaseDaoImpl<MemberRank, Long> implements MemberRankDao {
	
	public boolean nameExists(String name) {
		if (name == null) {
			return false;
		}
		String jpql = "select count(*) from MemberRank memberRank where lower(memberRank.name) = lower(:name)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();
		return count > 0;
	}

	public boolean amountExists(BigDecimal amount) {
		if (amount == null) {
			return false;
		}
		String jpql = "select count(*) from MemberRank memberRank where memberRank.amount = :amount";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("amount", amount).getSingleResult();
		return count > 0;
	}

	public MemberRank findDefault() {
		try {
			String jpql = "select memberRank from MemberRank memberRank where memberRank.isDefault = true";
			return entityManager.createQuery(jpql, MemberRank.class).setFlushMode(FlushModeType.COMMIT).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public MemberRank findByAmount(BigDecimal amount) {
		if (amount == null) {
			return null;
		}
		String jpql = "select memberRank from MemberRank memberRank where memberRank.isSpecial = false and memberRank.amount <= :amount order by memberRank.amount desc";
		return entityManager.createQuery(jpql, MemberRank.class).setFlushMode(FlushModeType.COMMIT).setParameter("amount", amount).setMaxResults(1).getSingleResult();
	}

	/**
	 * 处理默认并保存
	 * 
	 * @param memberRank
	 *            会员等级
	 */
	@Override
	public void persist(MemberRank memberRank) {
		Assert.notNull(memberRank);
		if (memberRank.getIsDefault()) {
			String jpql = "update MemberRank memberRank set memberRank.isDefault = false where memberRank.isDefault = true";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).executeUpdate();
		}
		super.persist(memberRank);
	}

	/**
	 * 处理默认并更新
	 * 
	 * @param memberRank
	 *            会员等级
	 * @return 会员等级
	 */
	@Override
	public MemberRank merge(MemberRank memberRank) {
		Assert.notNull(memberRank);
		if (memberRank.getIsDefault()) {
			String jpql = "update MemberRank memberRank set memberRank.isDefault = false where memberRank.isDefault = true and memberRank != :memberRank";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("memberRank", memberRank).executeUpdate();
		}
		return super.merge(memberRank);
	}

	/**
	 * 忽略默认、清除会员价并删除
	 * 
	 * @param memberRank
	 *            会员等级
	 */
	@Override
	public void remove(MemberRank memberRank) {
		if (memberRank != null && !memberRank.getIsDefault()) {
			String jpql = "select product from Product product join product.memberPrice memberPrice where index(memberPrice) = :memberRank";
			List<Product> products = entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT).setParameter("memberRank", memberRank).getResultList();
			/*String jpql2 = "select memberRankRigths from MemberRankRigths rights join rights.memberRank rank where index(rank) = :memberRank";
			List<MemberRankRigths> rigths = entityManager.createQuery(jpql2, MemberRankRigths.class).setFlushMode(FlushModeType.COMMIT).setParameter("membeRank", memberRank).getResultList();*/
			
			for (int i = 0; i < products.size(); i++) {
				Product product = products.get(i);
				product.getMemberPrice().remove(memberRank);
				if (i % 20 == 0) {
					super.flush();
					super.clear();
				}
			}
			/*for(int i=0; i<rigths.size(); i++){
				MemberRankRigths right = rigths.get(i);
				entityManager.remove(right);
			}*/
			super.remove(super.merge(memberRank));
		}
	}

	@Override
	public List<MemberRank> findListOrderByAmount() {
		String jpql = "select memberRank from MemberRank memberRank order by memberRank.amount asc";
		return entityManager.createQuery(jpql, MemberRank.class).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

}