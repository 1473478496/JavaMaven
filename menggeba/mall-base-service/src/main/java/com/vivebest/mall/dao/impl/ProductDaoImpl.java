/*
 * Copyright 2005-2013 www.mgb.cn. All rights reserved.
 * Support: http://www.mgb.cn
 * License: http://www.mgb.cn/license
 */
package com.vivebest.mall.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.vivebest.mall.core.common.Filter;
import com.vivebest.mall.core.common.Order;
import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.common.Setting;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.core.util.SettingUtils;
import com.vivebest.mall.dao.GoodsDao;
import com.vivebest.mall.dao.ProductDao;
import com.vivebest.mall.dao.SnDao;
import com.vivebest.mall.entity.Attribute;
import com.vivebest.mall.entity.Brand;
import com.vivebest.mall.entity.Goods;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.OrderItem;
import com.vivebest.mall.entity.Product;
import com.vivebest.mall.entity.Product.OrderType;
import com.vivebest.mall.entity.ProductCategory;
import com.vivebest.mall.entity.ProductImage;
import com.vivebest.mall.entity.Promotion;
import com.vivebest.mall.entity.Sn.Type;
import com.vivebest.mall.entity.SpecificationValue;
import com.vivebest.mall.entity.Tag;

/**
 * Dao - 商品
 * 
 * @author vnb shop Team
 * @version 3.0
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long>implements ProductDao {

	private static final Pattern pattern = Pattern.compile("\\d*");

	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	
	
	public boolean snExists(String sn) {
		if (sn == null) {
			return false;
		}
		String jpql = "select count(*) from Product product where lower(product.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

	public Product findBySn(String sn) {
		if (sn == null) {
			return null;
		}
		String jpql = "select product from Product product where lower(product.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Product> search(String keyword, Boolean isGift, Integer count) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.<Product> emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (pattern.matcher(keyword).matches()) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)),
							criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, null, count, null, null);
	}

	public List<Product> searchSuit(String keyword, Boolean suit, Integer count) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.<Product> emptyList();
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (pattern.matcher(keyword).matches()) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)),
							criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		}
		if (suit != null && !suit) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), suit));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, null, count, null, null);
	}

	public List<Product> findList(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock, Boolean isStockAlert,
			OrderType orderType, Integer count, List<Filter> filters, List<Order> orders, Boolean isSuit) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory),
							criteriaBuilder.like(root.get("productCategory").<String> get("treePath"),
									"%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId()
											+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"), promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory").join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join("promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.exists(subquery1),
					criteriaBuilder.exists(subquery2), criteriaBuilder.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), isSuit));
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock),
						criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock),
						criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder
						.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock,
								criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public List<Product> findList(ProductCategory productCategory, Date beginDate, Date endDate, Integer first,
			Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory),
							criteriaBuilder.like(root.get("productCategory").<String> get("treePath"),
									"%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId()
											+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findList(criteriaQuery, first, count, null, null);
	}

	public List<Product> findList(Goods goods, Set<Product> excludes) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (goods != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods"), goods));
		}
		if (excludes != null && !excludes.isEmpty()) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
	}

	public List<Object[]> findSalesList(Date beginDate, Date endDate, Boolean isPoint, Integer count) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Product> product = criteriaQuery.from(Product.class);
		Join<Product, OrderItem> orderItems = product.join("orderItems");
		Join<Product, ProductImage> productImages = product.join("productImages");
		Join<Product, com.vivebest.mall.entity.Order> order = orderItems.join("order");
		criteriaQuery.multiselect(product.get("id"), product.get("sn"), product.get("name"), product.get("fullName"),
				product.get("price"), productImages.get("medium"), product.get("pricePoint"),
				criteriaBuilder.sum(orderItems.<Integer> get("quantity")), criteriaBuilder.sum(criteriaBuilder
						.prod(orderItems.<Integer> get("quantity"), orderItems.<BigDecimal> get("price"))));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {

			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.greaterThanOrEqualTo(order.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.lessThanOrEqualTo(order.<Date> get("createDate"), endDate));
		}
		if (isPoint != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(product.get("isIntegral"), isPoint));
		}
		
		//排除下架的商品	
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(product.get("isMarketable"), true));

		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(order.get("orderStatus"), OrderStatus.completed),
				criteriaBuilder.equal(order.get("paymentStatus"), PaymentStatus.paid));
		criteriaQuery.where(restrictions);
		criteriaQuery.groupBy(product.get("id"), product.get("sn"), product.get("name"), product.get("fullName"),
				product.get("price"), product.get("image"));
		criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.sum(orderItems.<Integer> get("quantity"))));
		TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
		if (count != null && count >= 0) {
			query.setMaxResults(count);
		}
		return query.getResultList();
	}

	public Page<Product> findPage(ProductCategory productCategory, Brand brand, Promotion promotion, List<Tag> tags,
			Boolean isStore, Map<Attribute, String> attributeValue, BigDecimal startPrice, BigDecimal endPrice,
			Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean isNewFirst, OrderType orderType, Pageable pageable, Boolean suit) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory),
							criteriaBuilder.like(root.get("productCategory").<String> get("treePath"),
									"%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId()
											+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (promotion != null) {
			Subquery<Product> subquery1 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot1 = subquery1.from(Product.class);
			subquery1.select(subqueryRoot1);
			subquery1.where(criteriaBuilder.equal(subqueryRoot1, root),
					criteriaBuilder.equal(subqueryRoot1.join("promotions"), promotion));

			Subquery<Product> subquery2 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot2 = subquery2.from(Product.class);
			subquery2.select(subqueryRoot2);
			subquery2.where(criteriaBuilder.equal(subqueryRoot2, root),
					criteriaBuilder.equal(subqueryRoot2.join("productCategory").join("promotions"), promotion));

			Subquery<Product> subquery3 = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot3 = subquery3.from(Product.class);
			subquery3.select(subqueryRoot3);
			subquery3.where(criteriaBuilder.equal(subqueryRoot3, root),
					criteriaBuilder.equal(subqueryRoot3.join("brand").join("promotions"), promotion));

			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.exists(subquery1),
					criteriaBuilder.exists(subquery2), criteriaBuilder.exists(subquery3)));
		}
		if (tags != null && !tags.isEmpty()) {
			Subquery<Product> subquery = criteriaQuery.subquery(Product.class);
			Root<Product> subqueryRoot = subquery.from(Product.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot, root), subqueryRoot.join("tags").in(tags));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (attributeValue != null) {
			for (Entry<Attribute, String> entry : attributeValue.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.equal(root.get(propertyName), entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(new BigDecimal(0)) >= 0) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isStore != null && isStore) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.or(criteriaBuilder.isNull(root.get("stock")), criteriaBuilder.ge(root.<Number> get("stock"), 1)));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), suit));		
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock),
						criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock),
						criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder
						.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock,
								criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		if (isNewFirst != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isNewFirst"), isNewFirst));
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.scoreDesc) {
			orders.add(Order.desc("score"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Product> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return new Page<Product>(Collections.<Product> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.join("favoriteMembers"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member favoriteMember, Boolean isMarketable, Boolean isList, Boolean isTop, Boolean isGift,
			Boolean isOutOfStock, Boolean isStockAlert) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (favoriteMember != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.join("favoriteMembers"), favoriteMember));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isGift != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), isGift));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isOutOfStock != null) {
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock),
						criteriaBuilder.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock),
						criteriaBuilder.greaterThan(stock, allocatedStock)));
			}
		}
		if (isStockAlert != null) {
			Setting setting = SettingUtils.get();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.isNotNull(stock), criteriaBuilder
						.lessThanOrEqualTo(stock, criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.or(criteriaBuilder.isNull(stock), criteriaBuilder.greaterThan(stock,
								criteriaBuilder.sum(allocatedStock, setting.getStockAlertCount()))));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public boolean isPurchased(Member member, Product product) {
		if (member == null || product == null) {
			return false;
		}
		String jqpl = "select count(*) from OrderItem orderItem where orderItem.product = :product and orderItem.order.member = :member and orderItem.order.orderStatus = :orderStatus";
		Long count = entityManager.createQuery(jqpl, Long.class).setFlushMode(FlushModeType.COMMIT)
				.setParameter("product", product).setParameter("member", member)
				.setParameter("orderStatus", OrderStatus.completed).getSingleResult();
		return count > 0;
	}

	/**
	 * 设置值并保存
	 * 
	 * @param product
	 *            商品
	 */
	@Override
	public void persist(Product product) {
		Assert.notNull(product);

		setValue(product);
		super.persist(product);
	}

	/**
	 * 设置值并更新
	 * 
	 * @param product
	 *            商品
	 * @return 商品
	 */
	@Override
	public Product merge(Product product) {
		Assert.notNull(product);

		if (!product.getIsGift()) {
			String jpql = "delete from GiftItem giftItem where giftItem.gift = :product";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("product", product)
					.executeUpdate();
		}
		if (!product.getIsMarketable() || product.getIsGift()) {
			String jpql = "delete from CartItem cartItem where cartItem.product = :product";
			entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("product", product)
					.executeUpdate();
		}
		setValue(product);
		return super.merge(product);
	}

	@Override
	public void remove(Product product) {
		if (product != null) {
			Goods goods = product.getGoods();
			if (goods != null && goods.getProducts() != null) {
				goods.getProducts().remove(product);
				if (goods.getProducts().isEmpty()) {
					goodsDao.remove(goods);
				}
			}
		}
		super.remove(product);
	}

	/**
	 * 设置值
	 * 
	 * @param product
	 *            商品
	 */
	private void setValue(Product product) {
		if (product == null) {
			return;
		}
		if (StringUtils.isEmpty(product.getSn())) {
			String sn;
			do {
				sn = snDao.generate(Type.product);
			} while (snExists(sn));
			product.setSn(sn);
		}
		StringBuffer fullName = new StringBuffer(product.getName());
		if (product.getSpecificationValues() != null && !product.getSpecificationValues().isEmpty()) {
			List<SpecificationValue> specificationValues = new ArrayList<SpecificationValue>(
					product.getSpecificationValues());
			Collections.sort(specificationValues, new Comparator<SpecificationValue>() {
				public int compare(SpecificationValue a1, SpecificationValue a2) {
					return new CompareToBuilder().append(a1.getSpecification(), a2.getSpecification()).toComparison();
				}
			});
			fullName.append(Product.FULL_NAME_SPECIFICATION_PREFIX);
			int i = 0;
			for (Iterator<SpecificationValue> iterator = specificationValues.iterator(); iterator.hasNext(); i++) {
				if (i != 0) {
					fullName.append(Product.FULL_NAME_SPECIFICATION_SEPARATOR);
				}
				fullName.append(iterator.next().getName());
			}
			fullName.append(Product.FULL_NAME_SPECIFICATION_SUFFIX);
		}
		product.setFullName(fullName.toString());
	}

	@Override
	public Page<Product> findList(ProductCategory productCategory, Brand brand, Boolean integral, OrderType orderType,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory),
							criteriaBuilder.like(root.get("productCategory").<String> get("treePath"),
									"%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId()
											+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isIntegral"), integral));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		criteriaQuery.where(restrictions);
		if (orderType == OrderType.salesDesc) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
		} else if (orderType == OrderType.salesAsc) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sales")));
		}

		else if (orderType == OrderType.dateDesc) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		} else if (orderType == OrderType.dateAsc) {
			criteriaQuery.orderBy(criteriaBuilder.asc(root.get("createDate")));
		}

		return super.findPage(criteriaQuery, pageable);

	}

	@Override
	public Page<Product> searchIntegral(String keyword, Boolean integral, OrderType orderType, Pageable pageable) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(keyword)) {
			return new Page<Product>(Collections.<Product> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (pattern.matcher(keyword).matches()) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)),
							criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isIntegral"), integral));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		criteriaQuery.where(restrictions);

		if (orderType == OrderType.salesDesc) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
		} else if (orderType == OrderType.dateDesc) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
		} else
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")));
		return super.findPage(criteriaQuery, pageable);
	}

	public Page<Product> findIsNewFirstList(ProductCategory productCategory, Brand brand, Boolean isNewFirst,
			OrderType orderType, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory),
							criteriaBuilder.like(root.get("productCategory").<String> get("treePath"),
									"%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId()
											+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isNewFirst"), isNewFirst));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		}

		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<Product> searchIsNewFirst(String keyword, Boolean isNewFirst, OrderType orderType, Pageable pageable) {
		if (StringUtils.isEmpty(keyword)) {
			return new Page<Product>(Collections.<Product> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (pattern.matcher(keyword).matches()) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)),
							criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isNewFirst"), isNewFirst));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		criteriaQuery.where(restrictions);

		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<Product> searchAll(String keyword, OrderType orderType, Boolean isStore, Pageable pageable) {
		if (StringUtils.isEmpty(keyword)) {
			return new Page<Product>(Collections.<Product> emptyList(), 0, pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (pattern.matcher(keyword).matches()) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("id"), Long.valueOf(keyword)),
							criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("name"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("keyword"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		} else {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("name"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("keyword"), "%" + keyword + "%"),
							criteriaBuilder.like(root.<String> get("fullName"), "%" + keyword + "%")));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		Path<Integer> stock = root.get("stock");
		Path<Integer> allocatedStock = root.get("allocatedStock");
		if (isStore != null && isStore) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.isNull(stock),
					criteriaBuilder.greaterThan(stock, allocatedStock)));
		}
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	public List<Product> findListNoSuit(Integer count, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, null, orders);
	}
	
	public List<Product> findListBySaleCount(Integer count){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("suit"), false));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isMarketable"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isList"), true));
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isGift"), false));
		criteriaQuery.where(restrictions);
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")));
		return super.findList(criteriaQuery, null, count, null, null);
	}

	@Override
	public Product findById(Long id) {
		if (id == null) {
			return null;
		}
		String jpql = "select product from Product product where lower(product.id) = lower(:id)";
		try {
			return entityManager.createQuery(jpql, Product.class).setFlushMode(FlushModeType.COMMIT)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Page<Product> findPageByTradeId(Long tradeShopId, Pageable pageable ,String sn,ProductCategory productCategory, Brand brand,Boolean isMarketable,OrderType orderType) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if(sn!=null&&!sn.equals("")){
			restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("sn"), sn));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (productCategory != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.or(criteriaBuilder.equal(root.get("productCategory"), productCategory),
							criteriaBuilder.like(root.get("productCategory").<String> get("treePath"),
									"%" + ProductCategory.TREE_PATH_SEPARATOR + productCategory.getId()
											+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isMarketable"), isMarketable));
		}
		restrictions=criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("tradeShopId"), tradeShopId));
		criteriaQuery.where(restrictions);
		List<Order> orders = pageable.getOrders();
		if (orderType == OrderType.priceAsc) {
			orders.add(Order.asc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.priceDesc) {
			orders.add(Order.desc("price"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesAsc) {
			orders.add(Order.asc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.salesDesc) {
			orders.add(Order.desc("sales"));
			orders.add(Order.desc("createDate"));
		} else if (orderType == OrderType.dateDesc) {
			orders.add(Order.desc("createDate"));
		} else {
			orders.add(Order.desc("isTop"));
			orders.add(Order.desc("modifyDate"));
		}
		return super.findPage(criteriaQuery, pageable);
	}

}