package com.vivebest.mall.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.RightsDao;
import com.vivebest.mall.entity.Rights;
import com.vivebest.mall.entity.RightsBrand;
import com.vivebest.mall.entity.RightsTrade;

/**
 * RightsDaoImpl - 权益
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("rightsDaoImpl")
public class RightsDaoImpl extends BaseDaoImpl<Rights, Long> implements RightsDao {
	
	private static final Pattern pattern = Pattern.compile("\\d*");

	
	
	
	/**
	 * 查询
	 */
	@Override
	public Page<Rights> findList(Rights rights,Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rights> criteriaQuery = criteriaBuilder.createQuery(Rights.class);
		
		Root<Rights> root = criteriaQuery.from(Rights.class);
		criteriaQuery.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();
 		//根据品牌名称查询权益
		if(!StringUtils.isEmpty(rights.getRightsBrand())){
			if(!StringUtils.isEmpty(rights.getRightsBrand().getName())){
				Join<Rights,RightsBrand> setJoin  = root.join(root.getModel().getSingularAttribute("rightsBrand",RightsBrand.class),JoinType.LEFT);
				Predicate p = criteriaBuilder.equal(setJoin.get("name").as(String.class), rights.getRightsBrand().getName());
				predicates.add(p);
			}//predicates.add(p);
		}
		
		//根据品牌id查询权益
		if(!StringUtils.isEmpty(rights.getRightsBrand())){
			if(!StringUtils.isEmpty(rights.getRightsBrand().getId())){
				Join<Rights,RightsBrand> setJoin  = root.join(root.getModel().getSingularAttribute("rightsBrand",RightsBrand.class),JoinType.LEFT);
				Predicate p = criteriaBuilder.equal(setJoin.get("id").as(Long.class), rights.getRightsBrand().getId());
				predicates.add(p);
			}//predicates.add(p);
		}
		
		//根据查询线下线上权益
		if(!StringUtils.isEmpty(rights.getRightsType())){
			Predicate p = criteriaBuilder.equal(root.get("rightsType"), rights.getRightsType());
			predicates.add(p);
		}
		
		//根据类别查询权益
		if(!StringUtils.isEmpty(rights.getRightsCategory())){
			if(!StringUtils.isEmpty(rights.getRightsCategory().getId())){
				Predicate p = criteriaBuilder.equal(root.get("rightsCategory").get("id"), rights.getRightsCategory().getId());
				predicates.add(p);
			}
		}
		
		//根据门店查询权益
		Set<RightsTrade> trades = rights.getRightsTrades();
		if(trades !=null && trades.size()>0){
			RightsTrade trade = trades.iterator().next();
			if(!StringUtils.isEmpty(trade.getName())){
				SetJoin<Rights,RightsTrade> setJoin  = root.join(root.getModel().getSet("rightsTrades",RightsTrade.class), JoinType.INNER);
				Predicate p = criteriaBuilder.equal(setJoin.get("name").as(String.class), trade.getName());
				predicates.add(p);
			}
		}
		
		//查询推荐权益
		if(!StringUtils.isEmpty(rights.getIsPopularity())){
			Predicate p = criteriaBuilder.equal(root.get("isPopularity"), rights.getIsPopularity());
			predicates.add(p);
		}
		
		if(rights.getIsTop()!=null){
			Predicate p = criteriaBuilder.equal(root.get("isTop"), rights.getIsTop());
			predicates.add(p);
		}
		
		predicates.add(criteriaBuilder.equal(root.get("rightsBrand").get("status"), true));
		//
		if(rights.getStatus()!=null){
			Predicate p = criteriaBuilder.equal(root.get("status"), rights.getStatus());
			predicates.add(p);
		}
		
		if(!StringUtils.isEmpty(pageable.getOrderProperty())){
			if(pageable.getOrderDirection().ordinal() == 1){
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(pageable.getOrderProperty()).as(BigDecimal.class))); 
			}else{
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(pageable.getOrderProperty()).as(BigDecimal.class))); 
			}
		}
		
		
		
		if(predicates.size()>0){
			criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		
		return super.findPage(criteriaQuery, pageable);
	}
	
	@Override
	public void setIsPopularity(Rights rights){
		if(rights != null && rights.getIsPopularity() !=null && rights.getId() !=null){
			
			Rights r = find(rights.getId());
			if(r!=null){
				r.setIsPopularity(rights.getIsPopularity());
				super.merge(r);
			}
		}
	}
	
	
	

	@Override
	public List<Rights> rightsHotList() {
		//String jpql = "select hotrights from (select rights from Rights rights order by rights.hits desc) hotrights limit 5";
		String jpql = "select rights from Rights rights where rights.status = 1 order by rights.saleNumber desc";
		TypedQuery<Rights> query = entityManager.createQuery(jpql, Rights.class).setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}
	
	/**
	 * 根据品牌查询权益列表
	 */
	@Override
	public List<Rights> findByBrandId(Long rightsBrandId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rights> criteriaQuery = criteriaBuilder.createQuery(Rights.class);
		Root<Rights> root = criteriaQuery.from(Rights.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (rightsBrandId != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("rightsBrand").get("id"),rightsBrandId));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		criteriaQuery.where(restrictions);
		TypedQuery<Rights> query = entityManager.createQuery(criteriaQuery)
				.setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

	@Override
	public List<Rights> rightsPopular(Boolean isHome, Boolean isPopularity) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Rights> criteriaQuery = criteriaBuilder.createQuery(Rights.class);
		Root<Rights> root = criteriaQuery.from(Rights.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(isHome != null){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isHome"),isHome));
		}
		if(isPopularity != null){
			restrictions = criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isPopularity"),isPopularity));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), true));
		criteriaQuery.where(restrictions);
		TypedQuery<Rights> query = entityManager.createQuery(criteriaQuery)
				.setFlushMode(FlushModeType.COMMIT);
		return query.getResultList();
	}

	@Override
	public boolean snExists(String sn) {
		if (sn == null) {
			return false;
		}
		String jpql = "select count(*) from Rights rights where lower(rights.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("sn", sn).getSingleResult();
		return count > 0;
	}
	
	
	@Override
	public Rights findByName(String name) {
		if (name == null) {
			return null;
		}
		String jpql = "select rights from Rights rights where (rights.name)  = :name";
		try {
			return entityManager.createQuery(jpql, Rights.class).setFlushMode(FlushModeType.COMMIT).setParameter("name", name).setFirstResult(0).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}




	

}
