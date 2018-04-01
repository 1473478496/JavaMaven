/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.BrandDao;
import com.vivebest.mall.entity.Brand;


/**
 * Dao - 品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {

	@Override
	public Brand findByName(String name) {
		// TODO Auto-generated method stub
		if (name == null) {
			return null;
		}
		String jpql = "select brand from Brand brand where (brand.name)  = :name";
		try {
			return entityManager.createQuery(jpql, Brand.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	

	@Override
	public Page<Brand> findList(Brand brand, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
		
		Root<Brand> root = criteriaQuery.from(Brand.class);
		criteriaQuery.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();
 		
		//根据分类查询品牌
		if(!StringUtils.isEmpty(brand.getParent())){
			if(!StringUtils.isEmpty(brand.getParent().getId())){
				Predicate p = criteriaBuilder.equal(root.get("parent").get("id"), brand.getParent().getId());
				predicates.add(p);
			}
		}
		
		//根据名字查询品牌
		if(!StringUtils.isEmpty(brand.getName())){
			Predicate p = criteriaBuilder.equal(root.get("name"), brand.getName());
			predicates.add(p);
		}
		
		//查询推荐品牌
		if(!StringUtils.isEmpty(brand.getIsPopularity())){
			Predicate p = criteriaBuilder.equal(root.get("isPopularity"), brand.getIsPopularity());
			predicates.add(p);
		}
		
		
		if(predicates.size()>0){
			criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isPopularity").as(Boolean.class)));
		
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sorts").as(Integer.class)));
		return super.findPage(criteriaQuery, pageable);
	}



	@Override
	public List<Brand> findParent() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
		
		Root<Brand> root = criteriaQuery.from(Brand.class);
		criteriaQuery.select(root);
		Predicate p = criteriaBuilder.isNull(root.get("parent").get("id"));
		criteriaQuery.where(p);
		return super.findList(criteriaQuery, 0, 100, null, null);
	}
	
	@Override
	public Brand setIsPopularity(Brand brand){
		if(brand != null && brand.getIsPopularity() !=null && brand.getId() !=null){
			Brand b = find(brand.getId());
			if(b!=null){
				b.setIsPopularity(brand.getIsPopularity());
				return super.merge(b);
			}
		}
		return null;
	}

}