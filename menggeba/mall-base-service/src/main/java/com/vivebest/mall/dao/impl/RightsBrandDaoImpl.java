package com.vivebest.mall.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RightsBrandDao;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.Rights.RightsType;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsCategory;
import com.vivebest.mall.entity.RightsTrade;

/**
 * RightsBrandDaoImpl - 权益品牌
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("rightsBrandDaoImpl")
public class RightsBrandDaoImpl extends BaseDaoImpl<RightsBrand, Long> implements RightsBrandDao {

	@Override
	public RightsBrand findById(Long id) {
		if(id != null){
			return entityManager.find(RightsBrand.class, id);
		}
		return null;
	}

	@Override
	public List<RightsBrand> findAllRightsBrand() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsBrand> criteriaQuery = criteriaBuilder.createQuery(RightsBrand.class);
		
		Root<RightsBrand> root = criteriaQuery.from(RightsBrand.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("status"), true));
		return findList(criteriaQuery, null, null, null, null);
	}

	@Override
	public Page<RightsBrand> findPage(Pageable pageable, RightsBrand rightsBrand, RightsCategory rightsCategory) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsBrand> criteriaQuery = criteriaBuilder.createQuery(RightsBrand.class);
		Root<RightsBrand> root = criteriaQuery.from(RightsBrand.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		if(rightsBrand.getIsTop() != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), rightsBrand.getIsTop()));
		}
		if(rightsBrand.getIsPopularity() != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isPopularity"), rightsBrand.getIsPopularity()));
		}
		if(rightsCategory!=null){
			if(rightsCategory.getName() != null){
				SetJoin<RightsBrand,RightsCategory> setJoin  = root.join(root.getModel().getSet("rightsCategories",RightsCategory.class), JoinType.INNER);
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(setJoin.get("name").as(String.class), rightsCategory.getName()));
			}
		}
		if(rightsBrand.getName() != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("name"), rightsBrand.getName()));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<RightsBrand> findList(Pageable pageable, RightsType type,Long area,RightsBrand rightBrand) {
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsBrand> criteriaQuery = criteriaBuilder.createQuery(RightsBrand.class);
		List<Predicate> list = new ArrayList<Predicate>();
		Root<RightsBrand> root = criteriaQuery.from(RightsBrand.class);
		criteriaQuery.select(root);
		SetJoin<RightsBrand,Rights> setJoin  = root.join(root.getModel().getSet("rights",Rights.class),JoinType.LEFT);
		Predicate p = criteriaBuilder.equal(setJoin.get("rightsType"), type);
		list.add(p);
		Predicate predicate = criteriaBuilder.equal(root.get("status"), true);
		list.add(predicate);
		if(rightBrand !=null){
			if(rightBrand.getRightsBrandType() != null){
				Predicate p2 = criteriaBuilder.equal(root.get("rightsBrandType").get("id"), rightBrand.getRightsBrandType().getId());
				list.add(p2);
			}
		}
		
		if(rightBrand !=null){
			if(!StringUtils.isEmpty(area)){
				SetJoin<RightsBrand,RightsTrade> tradeJoin  = root.join(root.getModel().getSet("rightTrade",RightsTrade.class),JoinType.LEFT);
				Predicate p3 = criteriaBuilder.equal(tradeJoin.get("area").get("id"), area);
				list.add(p3);
			}
		}
		
		criteriaQuery.where(list.toArray(new Predicate[list.size()]));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<RightsBrand> findPage(Pageable pageable, Member member) {
		if (member == null) {
			return new Page<RightsBrand>(Collections.<RightsBrand>emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsBrand> criteriaQuery = criteriaBuilder.createQuery(RightsBrand.class);
		Root<RightsBrand> root = criteriaQuery.from(RightsBrand.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.join("members"), member), criteriaBuilder.equal(root.get("status"), true));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<RightsBrand> findPage(Pageable pageable, Long rightsCategoryId,
			Long rightsBrandId, Long areaId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RightsBrand> criteriaQuery = criteriaBuilder.createQuery(RightsBrand.class);
		Root<RightsBrand> root = criteriaQuery.from(RightsBrand.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		if(rightsCategoryId != null){
			SetJoin<RightsBrand,RightsCategory> setJoin  = root.join(root.getModel().getSet("rightsCategories",RightsCategory.class), JoinType.INNER);
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(setJoin.get("id").as(Long.class), rightsCategoryId));
		}
		if(rightsBrandId != null){
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("id"), rightsBrandId));
		}
		if(areaId != null){
			SetJoin<RightsBrand,RightsTrade> setJoin  = root.join(root.getModel().getSet("rightTrade",RightsTrade.class), JoinType.INNER);
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.equal(setJoin.get("area").get("id").as(Long.class), areaId)
					, criteriaBuilder.equal(setJoin.get("area").get("parent").get("id").as(Long.class), areaId))
					, criteriaBuilder.equal(setJoin.get("area").get("parent").get("parent").get("id").as(Long.class), areaId));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}
