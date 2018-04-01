package com.vivebest.mall.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.PromotionProductsDao;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Promotion.Category;
import com.vivebest.mall.entity.PromotionProducts;

/**
 * DaoImpl - 促销商品
 * 
 * @author junly
 * @version 3.0
 */
@Repository("promotionProductsDaoImpl")
public class PromotionProductsDaoImpl extends BaseDaoImpl<PromotionProducts, Long>implements PromotionProductsDao {

	public Page<PromotionProducts> findPage(Product product, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PromotionProducts> criteriaQuery = criteriaBuilder.createQuery(PromotionProducts.class);
		Root<PromotionProducts> root = criteriaQuery.from(PromotionProducts.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (StringUtils.isNotEmpty(pageable.getSearchValue())) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.like(root.get(pageable.getSearchProperty()).<String> get("fullName"),
							"%" + pageable.getSearchValue() + "%"));
		}
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("promotion").<Category>get("category"), Promotion.Category.dailySpecial));
		criteriaQuery.where(restrictions);
		long total = count(criteriaQuery, null);
		int totalPages = (int) Math.ceil((double) total / (double) pageable.getPageSize());
		if (totalPages < pageable.getPageNumber()) {
			pageable.setPageNumber(totalPages);
		}
		TypedQuery<PromotionProducts> query = entityManager.createQuery(criteriaQuery)
				.setFlushMode(FlushModeType.COMMIT);
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		return new Page<PromotionProducts>(query.getResultList(), total, pageable);
	}

	@Override
	public Page<PromotionProducts> findPage(Promotion promotion, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PromotionProducts> criteriaQuery = criteriaBuilder.createQuery(PromotionProducts.class);
		Root<PromotionProducts> root = criteriaQuery.from(PromotionProducts.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (promotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotion"), promotion));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public List<PromotionProducts>findByProductId(Long id){
		String jpql = "select promotionProduct from PromotionProducts promotionProduct where lower(promotionProduct.product.id)=lower(:id)";
		try {
			return entityManager.createQuery(jpql, PromotionProducts.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<PromotionProducts> findPtionProdIsStick(Promotion  promotion,Boolean isCheap,Boolean isStick) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PromotionProducts> criteriaQuery = criteriaBuilder.createQuery(PromotionProducts.class);
		Root<PromotionProducts> root = criteriaQuery.from(PromotionProducts.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (promotion != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotion"), promotion));
		}
		if (isStick) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isStick"), isStick));
		}
		criteriaQuery.where(restrictions);
		if (isCheap) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("promotionPrice")));
		}
		return super.findList(criteriaQuery, null, null, null, null);
//		String jpql = "select promotionProduct from PromotionProducts promotionProduct where lower(promotionProduct.promotion.id)=lower(:id)and promotionProduct.isStick=true";
//		try {
//			return entityManager.createQuery(jpql, PromotionProducts.class).setFlushMode(FlushModeType.COMMIT)
//					.setParameter("id", id).getResultList();
//		} catch (Exception e) {
//			return null;
//		}
	}
	
	public List<PromotionProducts> findPtionProdByPtionId(Long id) {
		String jpql = "select promotionProduct from PromotionProducts promotionProduct where lower(promotionProduct.promotion.id)=lower(:id)";
		try {
			return entityManager.createQuery(jpql, PromotionProducts.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean checkProduct(Long productId) {
		if (productId == null) {
			return false;
		}
		String jpql = "select count(*) from PromotionProducts promotionProduct where promotionProduct.promotion.category=2 "
				+ "and lower(promotionProduct.product.id) = lower(:productId)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("productId", productId).getSingleResult();
		return count > 0;
	}

	@Override
	public List<PromotionProducts> findIsStick(List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PromotionProducts> criteriaQuery = criteriaBuilder.createQuery(PromotionProducts.class);
		Root<PromotionProducts> root = criteriaQuery.from(PromotionProducts.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotion").<Category>get("category"), Category.dailySpecial));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isStick"), true));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, null, null, orders);
	}

	@Override
	public Object findSpecialPage(Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PromotionProducts> criteriaQuery = criteriaBuilder.createQuery(PromotionProducts.class);
		Root<PromotionProducts> root = criteriaQuery.from(PromotionProducts.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("promotion").<Category>get("category"), Category.dailySpecial));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("promotion").get("beginDate").isNull(),
				criteriaBuilder.lessThanOrEqualTo(root.get("promotion").<Date> get("beginDate"), new Date())));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("promotion").get("endDate").isNull(),
				criteriaBuilder.greaterThanOrEqualTo(root.get("promotion").<Date> get("endDate"), new Date())));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
}
