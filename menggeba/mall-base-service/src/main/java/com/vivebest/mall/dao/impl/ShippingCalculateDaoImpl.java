package com.vivebest.mall.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.dao.ShippingCalculateDao;
import com.vivebest.mall.entity.Area;
import com.vivebest.mall.entity.ShippingCalculate;
import com.vivebest.mall.entity.ShippingCalculate.AreaType;
import com.vivebest.mall.entity.ShippingMethod;

/**
 * ShippingCalculate Dao的实现
 * 
 * @author junly
 *
 */
@Repository("shippingCalculateDaoImpl")
public class ShippingCalculateDaoImpl extends BaseDaoImpl<ShippingCalculate, Long>implements ShippingCalculateDao {

	public List<ShippingCalculate> findByShipAndAreaType(AreaType areaType, Area consigneeArea,
			ShippingMethod shippingMethod) {
		// String jpql = "select shippingCalculate from ShippingCalculate
		// changes where lower(ShippingCalculate.areaType) = lower(:areaType)and
		// lower(ShippingCalculate.shippingArea.parent) = lower(:shippingArea)
		// and lower(ShippingCalculate.consigneeArea.parent) =
		// lower(:receArea)";
		// List<ShippingCalculate> shippingCalculates =
		// entityManager.createQuery(jpql, ShippingCalculate.class)
		// .setFlushMode(FlushModeType.COMMIT).setParameter("areaType",
		// areaType)
		// .setParameter("shippingArea", shippingArea).setParameter("receArea",
		// receArea).getResultList();
		// if (null != shippingCalculates && shippingCalculates.isEmpty()) {
		// return null;
		// } else {
		// return shippingCalculates;
		// }
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ShippingCalculate> criteriaQuery = criteriaBuilder.createQuery(ShippingCalculate.class);
		Root<ShippingCalculate> root = criteriaQuery.from(ShippingCalculate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (areaType != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("areaType"), areaType));
		}
		if (consigneeArea != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("consigneeArea"), consigneeArea));
		}
		if (shippingMethod != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("shippingMethod"), shippingMethod));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("firstPrice")));
		return super.findList(criteriaQuery, null, null, null, null);
	}

}
