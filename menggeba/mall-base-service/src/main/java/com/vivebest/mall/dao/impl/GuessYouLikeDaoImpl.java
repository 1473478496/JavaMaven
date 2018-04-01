package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.GuessYouLikeDao;
import com.vivebest.mall.entity.GuessYouLike;

/**
 * Dao - 首页猜你喜欢
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("guessYouLikeDaoImpl")
public class GuessYouLikeDaoImpl extends BaseDaoImpl<GuessYouLike, Long> implements GuessYouLikeDao {

	@Override
	public boolean checkProduct(Long productId) {
		if (productId == null) {
			return false;
		}
		String jpql = "select count(*) from GuessYouLike guessYouLike where lower(guessYouLike.product.id) = lower(:productId)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("productId", productId).getSingleResult();
		return count > 0;
	}

	@Override
	public List<GuessYouLike> findList(Integer count, List<Filter> filters,
			List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<GuessYouLike> criteriaQuery = criteriaBuilder.createQuery(GuessYouLike.class);
		Root<GuessYouLike> root = criteriaQuery.from(GuessYouLike.class);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("product").<Boolean>get("isMarketable"), true));
		criteriaQuery.where(restrictions);
		criteriaQuery.select(root);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

}
