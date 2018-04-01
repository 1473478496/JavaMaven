package com.vivebest.mall.dao.impl;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.TieInSaleCatetoryDao;
import com.vivebest.mall.entity.TieInSaleCatetory;

/**
 * 商品搭配分类 dao的实现
 * 
 * @author junly
 *
 */
@Repository("tieInSaleCatetoryDaoImpl")
public class TieInSaleCatetoryDaoImpl extends BaseDaoImpl<TieInSaleCatetory, Long>implements TieInSaleCatetoryDao {

	@Override
	public TieInSaleCatetory findTieCatetoryByCatetory(String name) {
		String hpql = "select tieInSaleCatetory from TieInSaleCatetory tieInSaleCatetory where lower(TieInSaleCatetory.name)=lower(:name)";
		try {
			return entityManager.createQuery(hpql, TieInSaleCatetory.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("name", name).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
