package com.vivebest.mall.service;

import java.util.List;

import com.vivebest.mall.core.common.Page;
import com.vivebest.mall.core.common.Pageable;
import com.vivebest.mall.core.service.BaseService;
import com.vivebest.mall.entity.CouponCode;
import com.vivebest.mall.entity.Member;
import com.vivebest.mall.entity.RightOrder;
import com.vivebest.mall.entity.RightOrder.RightsOrderStatus;
import com.vivebest.mall.entity.Rights;



/**
 * RightOrderService - 权益订单
 * 
 * @author vnb shop Team
 * @version 3.0
 */
public interface RightOrderService extends BaseService<RightOrder, Long> {
	
	/**
	 * 创建权益订单
	 * @param rights
	 * @param code
	 * @param quantity
	 * @param member
	 * @return,
	 */
	RightOrder build(Rights rights, CouponCode code,String RightType,Long quantity,Member member);

	Page<RightOrder> findPage(RightsOrderStatus orderStatus,Boolean hasExpired, Pageable pageable);
	/**
	 * 根据订单编号查找订单
	 * @param sn 订单编号(忽略大小写)
	 * @return 若不存在则返回null
	 */
	RightOrder findBySn(String sn);
	/**
	 * 查找指定用户的权益订单列表
	 * 
	 * @param orderStatus
	 *            訂單狀態
	 * @param hasExpired
	 *            订单是否过期
	 * @param member
	 *            订单用户
	 * @param pageable
	 *            分页
	 * @return 权益订单集合
	 */
	Page<RightOrder> findPage(RightsOrderStatus orderStatus, Boolean hasExpired, Member member, Pageable pageable);

	/**
	 * 查询权益订单
	 * @param orderStatus
	 * 			权益订单状态
	 * @param hasExpired
	 * 			是否过期
	 * @param pageable
	 * 			分页
	 * @return 权益订单集合
	 */
	List<RightOrder> findList(RightsOrderStatus orderStatus,
			Boolean hasExpired, Pageable pageable);

	/**
	 * 过期取消订单
	 * @param pageNumber
	 * @param pageSize
	 */
	void cancelOrder(int pageNumber, int pageSize);
}
