package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.TieInSaleDao;
import com.vivebest.mall.entity.TieInSale;

/**
 * 商品搭配分类——dao的实现
 * 
 * @author junly
 *
 */
@Repository("tieInSaleDaoImpl")
public class TieInSaleDaoImpl extends BaseDaoImpl<TieInSale, Long>implements TieInSaleDao {

	@Override
	public List<TieInSale> findTieSaleByPId(Long id) {
		String jpql = "select tieInSale from  TieInSale tieInSale where tieInSale.product.id=:id ";
		try {
			return entityManager.createQuery(jpql, TieInSale.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TieInSale> findTieSaleByPSuitId(Long id) {
		String jpql = "select tieInSale from  TieInSale tieInSale where tieInSale.productSuit.id=:id ";
		try {
			return entityManager.createQuery(jpql, TieInSale.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<TieInSale> findByProductId(Long ProductId) {
		String jpql = "select tieInSale from  TieInSale tieInSale where tieInSale.product.id=:ProductId ";
		try {
			return entityManager.createQuery(jpql, TieInSale.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("ProductId", ProductId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
