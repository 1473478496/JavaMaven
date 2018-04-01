package com.vivebest.mall.merchant.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.impl.BaseServiceImpl;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
import com.vivebest.mall.merchant.dao.OrderDao;
import com.vivebest.mall.merchant.service.OrderService;

@Service("orderServiceImpl2")
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService {
    @Resource(name = "orderDaoImpl2")
    public void setBaseDao(OrderDao orderDao) {
        super.setBaseDao(orderDao);
    }
    @Resource(name="orderDaoImpl2")
    private OrderDao orderDao;

    @Override
    public Page<Order> findPage(Order order, String ssn, String psn, String pname, String mname,
            String bDate, String eDate, Integer orderStatue, Integer shippingStatue, Integer payStatue,
            Pageable pageable, Boolean hasExpired,Long tradeId) {

        List list = orderDao.findPage(order, ssn, psn, pname, mname, bDate, eDate, orderStatue,
                shippingStatue, payStatue, hasExpired,tradeId);
        return orderDao.findById(list, pageable);
    }
    @Transactional(readOnly = true)
    public List<Order> findList(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus,
                    Boolean hasExpired, Pageable pageable, Date bDate, Date eDate) {
            return orderDao.findList(orderStatus, paymentStatus, shippingStatus, hasExpired, pageable, bDate, eDate);
    }
    
    
    @Override
    public Page<Order> findPage(Order order, Integer orderStatus, String memberUsername, String memberName,
            String bDate, String eDate, Long tradeId,Pageable pageable) {
        List list=orderDao.findPage(order, orderStatus, memberUsername, memberName, bDate, eDate,tradeId);
        
        return orderDao.findById(list, pageable);
    }
    
    

}
