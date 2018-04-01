/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ProductCateIndexDao;
import com.vivebest.mall.entity.ProductCateIndex;


/**
 * Dao - 商品分类
 * 
 * @author ding.hy
 * @version 3.0
 */
@Repository("productCateIndexDaoImpl")
public class ProductCateIndexDaoImpl extends BaseDaoImpl<ProductCateIndex, Long> implements ProductCateIndexDao {

	public List<ProductCateIndex> findAll(Integer count) {

		//String jpql2 = "select p1 from Product p1 where p1.isTop=:isTop order by p1.modifyDate desc";
		//TypedQuery<Product> query2=entityManager.createNamedQuery(jpql2, Product.class).setParameter("isTop", isTop);

		String jpql = "select productCateIndex from ProductCateIndex productCateIndex order by productCateIndex.order asc";
		TypedQuery<ProductCateIndex> query = entityManager.createQuery(jpql, ProductCateIndex.class).setFlushMode(FlushModeType.COMMIT);
		if (count != null) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	@Override
	public ProductCateIndex find(Long id) {
		if(id == null){
			return null;
		}
		return super.find(id);
	}


	@Override
	public void remove(ProductCateIndex productCateIndex) {
		if(productCateIndex != null){
			super.remove(productCateIndex);
		}
	}
	
	@Override
	public void persist(ProductCateIndex productCateIndex) {
		if(productCateIndex != null){
			super.persist(productCateIndex);
		}
		
	}
	
	@Override
	public ProductCateIndex merge(ProductCateIndex productCateIndex) {
		if(productCateIndex == null){
			return null;
		}
		return super.merge(productCateIndex);
	}

	@Override
	public ProductCateIndex findByName(String name) {
		if(name == null){
			return null;
		}
		try {
			String jpql = "select productCateIndex from ProductCateIndex productCateIndex where lower(productCateIndex.name) = lower(:name)";
			return entityManager.createQuery(jpql, ProductCateIndex.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkUsername(String name) {
		if(name == null){
			return false;
		}
		String jpql = "select count(*) from ProductCateIndex productCateIndex where lower(productCateIndex.name) = lower(:name)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();
		return count > 0;
	}
	
}