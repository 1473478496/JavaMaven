package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.TieInSaleTitleDao;
import com.vivebest.mall.entity.TieInSaleTitle;
/**
 * 商品搭配销售标题   Dao的实现
 * @author junly
 *
 */
@Repository("tieInSaleTitleDaoImpl")
public class TieInSaleTitleDaoImpl extends BaseDaoImpl<TieInSaleTitle, Long>implements TieInSaleTitleDao {

	public List<TieInSaleTitle> findTieSaleTitleByTitle(String title) {
		String hpql = "select t from TieInSaleTitle t where lower(t.title)=lower(:title) order by modifyDate desc";
			try {
				List<TieInSaleTitle>tieInSaleTitles=entityManager.createQuery(hpql, TieInSaleTitle.class).setFlushMode(FlushModeType.COMMIT)
						.setParameter("title", title).getResultList();
				return tieInSaleTitles;
			} catch (Exception e) {
				return null;
			}
	}

	public List<TieInSaleTitle> findTieSaleTitleByCatetory(Long id) {
		String hpql = "select t from TieInSaleTitle t where t.tieInSaleCatetory.id=:id";
		try {
			List<TieInSaleTitle>tieInSaleTitles=entityManager.createQuery(hpql, TieInSaleTitle.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).getResultList();
			return tieInSaleTitles;
		} catch (Exception e) {
			return null;
		}
	}

	public List<TieInSaleTitle> findByCatetoryTitle(Long id, String title) {
		String hpql = "select t from TieInSaleTitle t where lower(t.tieInSaleCatetory.id)=lower(:id) and lower(t.title)=lower(:title) order by modifyDate desc";
		try {
			List<TieInSaleTitle>tieInSaleTitles=entityManager.createQuery(hpql, TieInSaleTitle.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).setParameter("title", title).getResultList();
			return tieInSaleTitles;
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
