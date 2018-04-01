package com.vivebest.mall.merchant.dao;

import java.util.Date;
import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.dao.BaseDao;
import com.vivebest.mall.entity.Order;
import com.vivebest.mall.entity.Order.OrderStatus;
import com.vivebest.mall.entity.Order.PaymentStatus;
import com.vivebest.mall.entity.Order.ShippingStatus;
/**
 * dao 订单
 * @author vnb shop team
 * @version 1.0
 *
 */
public interface OrderDao extends BaseDao<Order,Long>{
    /**
     * 分页查找订单
     * @param order   订单
     * @param ssn     物流单号
     * @param psn     商品单号
     * @param pname    商品名称
     * @param mname    会员名称
     * @param bDate     起始时间
     * @param eDate     结束时间
     * @param orderStatue    订单状态
     * @param shippingStatue    物流状态
     * @param payStatue     支付状态
     * @param hasExpired    是否过期
     * @return
     */
    List findPage(Order order, String ssn ,String psn,String pname,String mname,String bDate,String eDate,Integer orderStatue,Integer shippingStatue,Integer payStatue,Boolean hasExpired,Long tradeId);

    /**
     * 根据id查询订单列表
     * @param list
     * @return
     */
    Page<Order> findById(List<Long> list,Pageable pageable);
    
    /**
     * 查找订单
     * 
     * @param orderStatus
     *            订单状态
     * @param paymentStatus
     *            支付状态
     * @param shippingStatus
     *            配送状态
     * @param hasExpired
     *            是否已过期
     * @param pageable
     *            分页信息
     * @return 商品分页
     */
    List<Order> findList(OrderStatus orderStatus, PaymentStatus paymentStatus, ShippingStatus shippingStatus, 
                    Boolean hasExpired, Pageable pageable,Date bDate, Date eDate);
    /**
     * 分页查找订单
     * @param order 订单
     * @param orderStatus  订单状态
     * @param memberUsername 买家帐号
     * @param memberName  买家姓名
     * @param bDate  开始时间
     * @param eDate  结束时间
     * @param tradeId  商户ID
     * @return
     */
    List findPage(Order order, Integer orderStatus, String memberUsername, String memberName, String bDate,
            String eDate, Long tradeId);


}
