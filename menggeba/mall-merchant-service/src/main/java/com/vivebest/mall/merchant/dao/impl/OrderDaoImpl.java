package com.vivebest.mall.merchant.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.impl.BaseDaoImpl;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.merchant.dao.OrderDao;

@Repository("orderDaoImpl2")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

    @Override
    public List findPage(Order order, String ssn, String psn, String pname, String mname, String bDate,
            String eDate, Integer orderStatue, Integer shippingStatue, Integer payStatue, Boolean hasExpired,Long tradeId) {
        String sql = " SELECT o.id FROM gbm_order o WHERE o.id IN (SELECT i.orders FROM gbm_order_item i "
                + " WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE p.name like ?1 "
                + " AND p.sn = ?2)) " + " AND o.id IN (SELECT s.orders FROM gbm_shipping s WHERE s.sn = ?3) "
                + " AND o.member IN (SELECT m.id FROM gbm_member m WHERE m.NAME = ?4) "
                + " AND o.create_date >= ?5 " + " AND o.create_date <= ?6 " + " AND o.order_status = ?7 "
                + " AND o.payment_status = ?8 " + " AND o.shipping_status = ?9 " + " AND o.consignee = ?10 "
                + " AND o.phone = ?12 ";

        StringBuffer sb = new StringBuffer();
        StringBuffer countSql = new StringBuffer();
        StringBuffer selectSql = new StringBuffer();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        countSql.append(" SELECT count(id) FROM gbm_order o WHERE 1=1 ");
        selectSql.append(" SELECT o.id FROM gbm_order o WHERE 1=1 ");
        if (pname != null && !"".equals(pname) && psn != null && !"".equals(psn)) {
            sb.append(" AND o.id IN (SELECT i.orders FROM gbm_order_item i ");
            sb.append(" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE 1=1 AND p.name like ?1  AND p.sn = ?2)) ");
            map.put(1, pname);
            map.put(2, psn);
        } else {
            if (psn != null && !"".equals(psn)) {
                sb.append(" AND o.id IN (SELECT i.orders FROM gbm_order_item i ");
                sb.append(" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE 1=1 AND  p.sn = ?2)) ");
                map.put(2, psn);
            }
            if (pname != null && !"".equals(pname)) {
                sb.append(" AND o.id IN (SELECT i.orders FROM gbm_order_item i ");
                sb.append(" WHERE i.product IN (SELECT p.id FROM gbm_product p , gbm_order_item i WHERE 1=1 AND p.name like ?1)) ");
                map.put(1, pname);
            }
        }

        if (ssn != null && !"".equals(ssn)) {
            sb.append(" AND o.id IN (SELECT s.orders FROM gbm_shipping s WHERE s.tracking_no = ?3) ");
            map.put(3, ssn);
        }

        if (mname != null && !"".equals(mname)) {
            sb.append(" AND o.member IN (SELECT m.id FROM gbm_member m WHERE m.username = ?4) ");
            map.put(4, mname);
        }

        if (bDate != null && !"".equals(bDate)) {
            sb.append(" AND o.create_date >= ?5 ");
            map.put(5, bDate);
        }

        if (eDate != null && !"".equals(eDate)) {
            sb.append(" AND o.create_date <= ?6 ");
            map.put(6, eDate);
        }
        if (orderStatue != null) {
            sb.append(" AND o.order_status = ?7 ");
            map.put(7, orderStatue);
        }
        if (payStatue != null) {
            sb.append(" AND o.payment_status = ?8 ");
            map.put(8, payStatue);
        }
        if (shippingStatue != null) {
            sb.append(" AND o.shipping_status = ?9 ");
            map.put(9, shippingStatue);
        }

        if (order != null) {
            if (order.getConsignee() != null && !"".equals(order.getConsignee())) {
                sb.append(" AND o.consignee = ?10 ");
                map.put(10, order.getConsignee());
            }
            if (order.getPhone() != null && !"".equals(order.getPhone())) {
                sb.append(" AND o.phone = ?11 ");
                map.put(11, order.getPhone());
            }
            if (order.getSn() != null && !"".equals(order.getSn())) {
                sb.append(" AND o.sn= ?12 ");
                map.put(12, order.getSn());
            }

            if (hasExpired != null) {
                if (hasExpired) {
                    sb.append(" AND o.expire <= now()");
                } else {
                    sb.append(" AND o.expire >= now()");
                }
            }
        }
            if(tradeId!=null){
                sb.append(" AND o.trade_id = ?13 ");
                map.put(13, tradeId);
            }

        // System.out.println("----->"+sb);
        /*
         * Query cQuery = entityManager.createNativeQuery(countSql.append(sb).toString()); for
         * (Integer key : map.keySet()) { cQuery.setParameter(key, map.get(key)); }
         * 
         * long total = Long.parseLong(cQuery.getResultList().get(0).toString());
         */
        Query query = entityManager.createNativeQuery(selectSql.append(sb).toString());
        for (Integer key : map.keySet()) {
            query.setParameter(key, map.get(key));
        }

        // query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
        // query.setMaxResults(pageable.getPageSize());

        /*
         * query.setParameter(1, pname); query.setParameter(2, psn); query.setParameter(3, ssn);
         * query.setParameter(4, mname); query.setParameter(5, bDate); query.setParameter(6, eDate);
         * query.setParameter(7, 0); query.setParameter(8, 2); query.setParameter(9, 2);
         * query.setParameter(10, order.getConsignee()); query.setParameter(11, order.getPhone());
         * if(pageable == null){ pageable = new Pageable(); }
         */
        List result = query.getResultList();
        return result;
        // return new Page<Order>(result, total, pageable);
    }

    @Override
    public Page<Order> findById(List<Long> list, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        if (list.size() > 0) {
            Expression<String> exp = root.get("id");
            Predicate predicate = exp.in(list);
            criteriaQuery.where(predicate);
            return super.findPage(criteriaQuery, pageable);
        } else {

            return new Page<Order>();
        }
    }

    public List<Order> findList(OrderStatus orderStatus, PaymentStatus paymentStatus,
            ShippingStatus shippingStatus, Boolean hasExpired, Pageable pageable, Date bDate, Date eDate) {
        if (pageable == null) {
            pageable = new Pageable();
        }
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (orderStatus != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("orderStatus"), orderStatus));
        }
        if (paymentStatus != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus));
        }
        if (shippingStatus != null) {
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("shippingStatus"), shippingStatus));
        }
        if (hasExpired != null) {
            if (hasExpired) {
                restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(),
                        criteriaBuilder.lessThan(root.<Date> get("expire"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(
                        restrictions,
                        criteriaBuilder.or(root.get("expire").isNull(),
                                criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("expire"), new Date())));
            }
        }
        if (bDate != null) {
            restrictions = criteriaBuilder.and(
                    restrictions,
                    criteriaBuilder.or(root.get("createDate").isNull(),
                            criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), bDate)));
        }
        if (eDate != null) {
            restrictions = criteriaBuilder.and(
                    restrictions,
                    criteriaBuilder.or(root.get("createDate").isNull(),
                            criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), eDate)));
        }
        if (StringUtils.isNotEmpty(pageable.getSearchProperty())
                && StringUtils.isNotEmpty(pageable.getSearchValue())) {
            String searchProperty = pageable.getSearchProperty();
            if (searchProperty.contains("_")) {
                String[] searchPropertys = searchProperty.split("_");
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.like(
                        root.get(searchPropertys[0]).<String> get(searchPropertys[1]),
                        "%" + pageable.getSearchValue() + "%"));
            } else {
                restrictions = criteriaBuilder.and(
                        restrictions,
                        criteriaBuilder.like(root.<String> get(pageable.getSearchProperty()),
                                "%" + pageable.getSearchValue() + "%"));
            }
        }
        criteriaQuery.where(restrictions);
        return super.findList(criteriaQuery, null, null, null, null);
    }

    @Override
    public List findPage(Order order, Integer orderStatus, String memberUsername, String memberName,
            String bDate, String eDate, Long tradeId) {
        
              

        StringBuffer sb = new StringBuffer();
        StringBuffer selectSql = new StringBuffer();
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        selectSql.append(" SELECT o.id FROM gbm_order o WHERE 1=1 ");
        
       

        if (memberUsername != null && !"".equals(memberUsername)) {
            sb.append(" AND o.member IN (SELECT m.id FROM gbm_member m WHERE m.username = ?4) ");
            map.put(4, memberUsername);
        }
        if (memberName != null && !"".equals(memberName)) {
            sb.append(" AND o.member IN (SELECT m.id FROM gbm_member m WHERE m.name = ?2) ");
            map.put(2, memberName);
        }

        if (bDate != null && !"".equals(bDate)) {
            sb.append(" AND o.create_date >= ?5 ");
            map.put(5, bDate);
        }

        if (eDate != null && !"".equals(eDate)) {
            sb.append(" AND o.create_date <= ?6 ");
            map.put(6, eDate);
        }
        if (orderStatus != null) {
            sb.append(" AND o.order_status = ?7 ");
            map.put(7, orderStatus);
        }
       

        if (order != null) {
            if (order.getConsignee() != null && !"".equals(order.getConsignee())) {
                sb.append(" AND o.consignee = ?10 ");
                map.put(10, order.getConsignee());
            }
            if (order.getPhone() != null && !"".equals(order.getPhone())) {
                sb.append(" AND o.phone = ?11 ");
                map.put(11, order.getPhone());
            }
            if (order.getSn() != null && !"".equals(order.getSn())) {
                sb.append(" AND o.sn= ?12 ");
                map.put(12, order.getSn());
            }

           
        }
            if(tradeId!=null){
                sb.append(" AND o.trade_id = ?13 ");
                map.put(13, tradeId);
            }

        Query query = entityManager.createNativeQuery(selectSql.append(sb).toString());
        for (Integer key : map.keySet()) {
            query.setParameter(key, map.get(key));
        }

        List result = query.getResultList();
        return result;
        
    }

}
